package com.mongia.razorpay.payment.serviceImpl;

import com.mongia.razorpay.common.enums.OrderStatus;
import com.mongia.razorpay.common.enums.PaymentStatus;
import com.mongia.razorpay.common.exceptions.BusinessRuleException;
import com.mongia.razorpay.common.exceptions.ResourceNotFoundException;
import com.mongia.razorpay.payment.dto.request.PaymentInitRequestDto;
import com.mongia.razorpay.payment.dto.response.PaymentResponse;
import com.mongia.razorpay.payment.entity.OrderRecord;
import com.mongia.razorpay.payment.entity.Payment;
import com.mongia.razorpay.payment.gateway.PaymentGatewayRouter;
import com.mongia.razorpay.payment.gateway.dto.PaymentRequest;
import com.mongia.razorpay.payment.gateway.dto.PaymentResult;
import com.mongia.razorpay.payment.mapper.PaymentMapper;
import com.mongia.razorpay.payment.repository.OrderRepository;
import com.mongia.razorpay.payment.repository.PaymentRepository;
import com.mongia.razorpay.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final PaymentGatewayRouter paymentGatewayRouter;

    private final PaymentMapper paymentMapper;
    @Override
    @Transactional
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequestDto request) {
        OrderRecord order=orderRepository.findByIdAndMerchantId(request.orderId(),merchantId).orElseThrow(
                ()-> new ResourceNotFoundException("Order not found",request.orderId())
        );
        if(order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus()!=OrderStatus.ATTEMPTED){
            throw new BusinessRuleException("ORDER_NOT_PAYABLE", "Order canot accept payment in status: " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts()+1);

        Payment payment=Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .paymentMethod(request.paymentMethod())
                .methodDetails(request.methodDetails())
                .build();

        payment=paymentRepository.save(payment);

        PaymentRequest paymentRequest=new PaymentRequest(payment.getId(),request.orderId(),merchantId,order.getAmount(),request.paymentMethod(),request.methodDetails());

        PaymentResult result= paymentGatewayRouter.initiate(paymentRequest);


        switch (result){
            case PaymentResult.Pending pending -> payment.setProcessorReference((pending.registrationReference()));
            case PaymentResult.Failure failure ->{
                payment.setStatus(PaymentStatus.FAILED);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }

        }

        payment=paymentRepository.save(payment);
        order=orderRepository.save(order);
        return paymentMapper.entityToResponse(payment);
    }
}
