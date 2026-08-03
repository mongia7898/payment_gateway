package com.mongia.razorpay.payment.processor;

import com.mongia.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.mongia.razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
