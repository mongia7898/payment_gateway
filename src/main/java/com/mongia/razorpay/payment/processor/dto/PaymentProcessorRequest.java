package com.mongia.razorpay.payment.processor.dto;

import com.mongia.razorpay.common.entity.Money;
import com.mongia.razorpay.common.enums.PaymentMethod;

import java.util.Map;

public record PaymentProcessorRequest(
        PaymentMethod method,
        Money amount,
        String pan,
        String expiry,
        Map<String,Object> methodDetails
) {
}
