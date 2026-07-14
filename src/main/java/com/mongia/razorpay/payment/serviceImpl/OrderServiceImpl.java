package com.mongia.razorpay.payment.serviceImpl;

import com.mongia.razorpay.common.enums.OrderStatus;
import com.mongia.razorpay.common.exceptions.BusinessRuleException;
import com.mongia.razorpay.common.exceptions.DuplicateResourceException;
import com.mongia.razorpay.common.exceptions.ResourceNotFoundException;
import com.mongia.razorpay.payment.dto.request.CreateOrderRequest;
import com.mongia.razorpay.payment.dto.response.OrderResponse;
import com.mongia.razorpay.payment.dto.response.PaymentResponse;
import com.mongia.razorpay.payment.entity.OrderRecord;
import com.mongia.razorpay.payment.entity.Payment;
import com.mongia.razorpay.payment.mapper.OrderMapper;
import com.mongia.razorpay.payment.mapper.PaymentMapper;
import com.mongia.razorpay.payment.repository.OrderRepository;
import com.mongia.razorpay.payment.repository.PaymentRepository;
import com.mongia.razorpay.payment.service.Orderservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements Orderservice {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;
    @Override
    @Transactional
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        // check if the reciept already exists
        if(StringUtils.hasText(request.receipt()) && orderRepository.existsByMerchantIdAndReceipt(merchantId,request.receipt())){
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE","Order with reciept already exists");
        }

        OrderRecord orderRecord= OrderRecord.builder()
                .merchantId(merchantId)
                .receipt(request.receipt())
                .amount(request.amount())
                .notes(request.notes())
                .orderStatus(OrderStatus.CREATED)
                .expiresAt(request.expiresAt()!=null ? request.expiresAt()  :
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();
        orderRecord=orderRepository.save(orderRecord);


        // TODO: publish to kafka event
        return orderMapper.entityToResponse(orderRecord);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->
                    new ResourceNotFoundException("Order not found", orderId)
                );
        return orderMapper.entityToResponse(orderRecord);

    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Order not found", orderId)
                );

        if(OrderStatus.CANCELLED.equals(orderRecord.getOrderStatus()) || OrderStatus.PAID.equals(orderRecord.getOrderStatus())){
            throw new BusinessRuleException("ORDER_CANNOT_CANCEL",
                    "Cannot cancel order with status: "+orderRecord.getOrderStatus().name());
        }

        orderRecord.setOrderStatus(OrderStatus.CANCELLED);
        orderRecord=orderRepository.save(orderRecord);

        return orderMapper.entityToResponse(orderRecord);
    }

    @Override
    public List<PaymentResponse> listPaymentsForOrder(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Order not found", orderId);
                });

        List<Payment> paymentList=paymentRepository.findByOrder_Id(orderId);

        List<PaymentResponse> paymentResponseList = paymentList.stream().map(paymentMapper::entityToResponse).toList();
        return paymentResponseList;
    }
}
