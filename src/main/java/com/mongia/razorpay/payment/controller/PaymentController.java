package com.mongia.razorpay.payment.controller;

import com.mongia.razorpay.payment.dto.request.PaymentInitRequestDto;
import com.mongia.razorpay.payment.dto.response.PaymentResponse;
import com.mongia.razorpay.payment.service.PaymentService;
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
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    UUID merchantID=UUID.fromString("6f5cd01f-776f-4746-847e-a572a79fbc86");
    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody PaymentInitRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.initiate(merchantID,request));

    }
}
