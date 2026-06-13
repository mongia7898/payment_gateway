package com.mongia.razorpay.merchant.dto.response;

import com.mongia.razorpay.common.enums.ApiKeyEnvironment;

import java.util.UUID;

public record ApiKeyCreateResponse (
    UUID id,
    String keyId,
    String keySecret,
    ApiKeyEnvironment environment
){

}
