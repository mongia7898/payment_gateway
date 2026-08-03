package com.mongia.razorpay.payment.processor.strategy;

import com.mongia.razorpay.payment.processor.PaymentProcessor;
import com.mongia.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.mongia.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
