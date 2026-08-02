package com.mongia.razorpay.payment.service;

import com.mongia.razorpay.payment.dto.request.PaymentInitRequestDto;
import com.mongia.razorpay.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse initiate(UUID merchantId,PaymentInitRequestDto request);
}
