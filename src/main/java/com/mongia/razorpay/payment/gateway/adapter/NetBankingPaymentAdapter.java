package com.mongia.razorpay.payment.gateway.adapter;

import com.mongia.razorpay.payment.gateway.PaymentAdapter;
import com.mongia.razorpay.payment.gateway.dto.PaymentRequest;
import com.mongia.razorpay.payment.gateway.dto.PaymentResult;

public class NetBankingPaymentAdapter implements PaymentAdapter {

    @Override
    public PaymentResult initiate(PaymentRequest request){
        return null;
    }
}
