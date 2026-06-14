package com.mongia.razorpay.payment.dto.response;

import com.mongia.razorpay.common.entity.Money;
import com.mongia.razorpay.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID merchantId,
        Money amount,
        String receipt,
        OrderStatus status,
        Integer attempts,
        Map<String,Object> notes,
        LocalDateTime expirestAt,
        LocalDateTime createdAt
) {
}
