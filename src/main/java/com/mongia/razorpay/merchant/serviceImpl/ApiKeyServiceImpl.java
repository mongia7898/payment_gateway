package com.mongia.razorpay.merchant.serviceImpl;

import com.mongia.razorpay.common.exceptions.ResourceNotFoundException;
import com.mongia.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.mongia.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.mongia.razorpay.merchant.entity.ApiKey;
import com.mongia.razorpay.merchant.entity.Merchant;
import com.mongia.razorpay.merchant.repository.ApiKeyRepository;
import com.mongia.razorpay.merchant.repository.MerchantRepository;
import com.mongia.razorpay.merchant.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;
    @Override
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant=merchantRepository.findById(merchantId).orElseThrow(
                ()->    new ResourceNotFoundException("merchant",merchantId));



        String keyId= "rzp_"+request.environment().name().toUpperCase()+"big_random_string";
        String rawSecret= "big_random_secret"; // TODO: replace with cryptography

        ApiKey apiKey=ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret) // TODO: encode with encoder BCRYPT
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(),keyId,rawSecret,apiKey.getEnvironment());
    }
}



