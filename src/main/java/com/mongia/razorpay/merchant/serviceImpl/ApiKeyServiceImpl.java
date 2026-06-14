package com.mongia.razorpay.merchant.serviceImpl;

import com.mongia.razorpay.common.exceptions.ResourceNotFoundException;
import com.mongia.razorpay.common.utils.RandomizerUtil;
import com.mongia.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.mongia.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.mongia.razorpay.merchant.dto.response.ApiKeyResponse;
import com.mongia.razorpay.merchant.entity.ApiKey;
import com.mongia.razorpay.merchant.entity.Merchant;
import com.mongia.razorpay.merchant.repository.ApiKeyRepository;
import com.mongia.razorpay.merchant.repository.MerchantRepository;
import com.mongia.razorpay.merchant.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant=merchantRepository.findById(merchantId).orElseThrow(
                ()->    new ResourceNotFoundException("merchant",merchantId));



        String keyId= "rzp_"+request.environment().name().toLowerCase() + "_"+RandomizerUtil.randomBase64(24);
        String rawSecret= RandomizerUtil.randomBase64(40);

        ApiKey apiKey=ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret) // TODO: encode with encoder BCRYPT
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(),keyId,rawSecret,apiKey.getEnvironment());
    }

    @Override
    @Transactional
    public List<ApiKeyResponse> getApiKeyByMerchant(UUID merchantId) {
        List<ApiKey> apiKeyList = apiKeyRepository.findByMerchant_Id(merchantId);
        return apiKeyList.stream().map(apiKey -> new ApiKeyResponse(
                apiKey.getId(),
                apiKey.getKeyId(),
                apiKey.getEnvironment(),
                apiKey.getEnabled(),
                apiKey.getLastUsedAt())).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey apiKey=apiKeyRepository.findById(keyId)
                .filter(k-> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()-> new ResourceNotFoundException("ApiKey",keyId));

        apiKey.setEnabled(false);
    }

    @Override
    public ApiKeyCreateResponse rotateKey(UUID merchantId, UUID keyId) {
        ApiKey apiKey=apiKeyRepository.findById(keyId)
                .filter(k-> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()-> new ResourceNotFoundException("ApiKey",keyId));
        if(apiKey.getEnabled()==false){
            throw new RuntimeException("Api key is disabled");
        }

        String newRawSecret=RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiry(LocalDateTime.now().plusHours(48)); // Expires after 2 day
        apiKey=apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(),apiKey.getKeyId(),
                newRawSecret,apiKey.getEnvironment());

    }

}



