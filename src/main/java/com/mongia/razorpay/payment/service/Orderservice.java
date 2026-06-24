package com.mongia.razorpay.payment.service;

import com.mongia.razorpay.payment.dto.request.CreateOrderRequest;
import com.mongia.razorpay.payment.dto.response.OrderResponse;
import com.mongia.razorpay.payment.dto.response.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface Orderservice {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancelOrder(UUID merchantId, UUID orderId);

    List<PaymentResponse> listPayments(UUID merchantId, UUID orderId);

}
