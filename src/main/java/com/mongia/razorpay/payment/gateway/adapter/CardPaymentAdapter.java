package com.mongia.razorpay.payment.gateway.adapter;

import com.mongia.razorpay.payment.gateway.PaymentAdapter;
import com.mongia.razorpay.payment.gateway.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardPaymentAdapter implements PaymentAdapter {

    @Override
    public void initiate(PaymentRequest request){

    }
}
