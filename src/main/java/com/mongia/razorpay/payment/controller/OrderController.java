package com.mongia.razorpay.payment.controller;

import com.mongia.razorpay.payment.dto.request.CreateOrderRequest;
import com.mongia.razorpay.payment.dto.response.OrderResponse;
import com.mongia.razorpay.payment.service.Orderservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final Orderservice orderservice;

    // TODO: replace with merchant Id from Api key
    UUID merchantId= UUID.fromString("bc9fcf46-9b44-45f9-b222-035960fac445");
    @PostMapping
    public ResponseEntity<?> createOrder (@RequestBody  @Valid CreateOrderRequest request){

        return new ResponseEntity<>(orderservice.create(merchantId,request), HttpStatus.CREATED);
    }

}
