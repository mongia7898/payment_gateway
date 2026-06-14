package com.mongia.razorpay.merchant.services;

import com.mongia.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.mongia.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.mongia.razorpay.merchant.dto.response.ApiKeyResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request);

    List<ApiKeyResponse> getApiKeyByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);
}
