package com.mongia.razorpay.payment.gateway;

import com.mongia.razorpay.payment.gateway.dto.PaymentRequest;
import com.mongia.razorpay.payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {

    PaymentResult initiate(PaymentRequest request);
}
