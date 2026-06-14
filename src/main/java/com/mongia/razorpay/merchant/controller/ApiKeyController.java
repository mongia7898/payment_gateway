package com.mongia.razorpay.merchant.controller;

import com.mongia.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.mongia.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.mongia.razorpay.merchant.dto.response.ApiKeyResponse;
import com.mongia.razorpay.merchant.services.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/api-key")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyCreateResponse> createApiKey(@PathVariable UUID merchantId,
                                                             @Valid @RequestBody CreateApiKeyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.create(merchantId,request));
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> getApiKeyByMerchant(@PathVariable UUID merchantId){
        return ResponseEntity.status(HttpStatus.OK).body(apiKeyService.getApiKeyByMerchant(merchantId));
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> revoke(@PathVariable UUID merchantId,@PathVariable UUID keyId){
        apiKeyService.revoke(merchantId,keyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotateKey(@PathVariable UUID merchantId,@PathVariable UUID keyId){
        return ResponseEntity.ok(apiKeyService.rotateKey(merchantId,keyId));
    }
}
