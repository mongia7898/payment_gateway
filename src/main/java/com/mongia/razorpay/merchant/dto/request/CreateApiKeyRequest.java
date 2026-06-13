package com.mongia.razorpay.merchant.dto.request;


import com.mongia.razorpay.common.enums.ApiKeyEnvironment;

public record CreateApiKeyRequest(
        ApiKeyEnvironment environment

) {
}
