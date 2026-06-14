package com.mongia.razorpay.payment.serviceImpl;

import com.mongia.razorpay.common.enums.OrderStatus;
import com.mongia.razorpay.common.exceptions.DuplicateResourceException;
import com.mongia.razorpay.payment.dto.request.CreateOrderRequest;
import com.mongia.razorpay.payment.dto.response.OrderResponse;
import com.mongia.razorpay.payment.entity.OrderRecord;
import com.mongia.razorpay.payment.repository.OrderRepository;
import com.mongia.razorpay.payment.service.Orderservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements Orderservice {
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
}
