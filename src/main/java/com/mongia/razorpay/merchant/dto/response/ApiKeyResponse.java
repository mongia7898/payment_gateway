package com.mongia.razorpay.merchant.dto.response;

import com.mongia.razorpay.common.enums.ApiKeyEnvironment;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApiKeyResponse(
        UUID id,
        String keyId,
        ApiKeyEnvironment environment,
        Boolean enabled,
        LocalDateTime lastUsedAt
) {
}
