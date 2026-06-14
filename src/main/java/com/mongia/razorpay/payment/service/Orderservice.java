package com.mongia.razorpay.payment.service;

import com.mongia.razorpay.payment.dto.request.CreateOrderRequest;
import com.mongia.razorpay.payment.dto.response.OrderResponse;

import java.util.UUID;

public interface Orderservice {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);
}
