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
import com.mongia.razorpay.payment.repository.OrderRepository;
import com.mongia.razorpay.payment.repository.PaymentRepository;
import com.mongia.razorpay.payment.service.Orderservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements Orderservice {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
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
        return new OrderResponse(orderRecord.getId(),
                orderRecord.getMerchantId(),
                orderRecord.getAmount(),
                orderRecord.getReceipt(),
                orderRecord.getOrderStatus(),
                orderRecord.getAttempts(),
                orderRecord.getNotes(),
                orderRecord.getExpiresAt(),
                null);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->
                    new ResourceNotFoundException("Order not found", orderId)
                );
        return new OrderResponse(orderRecord.getId(),orderRecord.getMerchantId(),orderRecord.getAmount(),orderRecord.getReceipt(),orderRecord.getOrderStatus(), orderRecord.getAttempts(), orderRecord.getNotes(),orderRecord.getExpiresAt(),null);

    }

    @Override
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

        return new OrderResponse(orderRecord.getId(),orderRecord.getMerchantId(),orderRecord.getAmount(),orderRecord.getReceipt(),orderRecord.getOrderStatus(), orderRecord.getAttempts(), orderRecord.getNotes(),orderRecord.getExpiresAt(),null);
    }

    @Override
    public List<PaymentResponse> listPayments(UUID merchantId, UUID orderId) {
        orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()-> {
                    throw new ResourceNotFoundException("Order not found", orderId);
                });

        List<Payment> paymentList=paymentRepository.findByOrder_Id(orderId);

        paymentList.stream().map( payment-> payment).toList();
        return List.of();
    }
}
