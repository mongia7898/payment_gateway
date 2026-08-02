package com.mongia.razorpay.payment.gateway;

import com.mongia.razorpay.payment.gateway.dto.PaymentRequest;

public interface PaymentAdapter {

    void initiate(PaymentRequest request);
}
