package com.mongia.razorpay.merchant.serviceImpl;

import com.mongia.razorpay.common.enums.AppUserRole;
import com.mongia.razorpay.common.enums.MerchantStatus;
import com.mongia.razorpay.common.exceptions.DuplicateResourceException;
import com.mongia.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.mongia.razorpay.merchant.dto.response.MerchantResponse;
import com.mongia.razorpay.merchant.entity.AppUser;
import com.mongia.razorpay.merchant.entity.Merchant;
import com.mongia.razorpay.merchant.mapper.MerchantMapper;
import com.mongia.razorpay.merchant.repository.AppUserRepository;
import com.mongia.razorpay.merchant.repository.MerchantRepository;
import com.mongia.razorpay.merchant.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    @Override
    @Transactional
    public @Nullable MerchantResponse signup(MerchantSignupRequest request) {
        ///  Check if the merchant with same email doesn't exist
        checkMerchantEmailExists(request.email());
        /// Create a new merchant
        Merchant merchant = createMerchant(request);

        /// Create a new app user
        /// add merchant to app user
        AppUser appUser = createAppUser(request, merchant);

        ///  Return response
        return merchantMapper.entityToResponse(merchant);

    }

    private AppUser createAppUser(MerchantSignupRequest request, Merchant merchant) {
        AppUser appUser= AppUser.builder()
                .name(request.name())
                .email(request.email())
                .role(AppUserRole.OWNER)
                .passwordHash(request.password()) // TODO: Encrupt the password
                .merchant(merchant)
                .build();

        return appUserRepository.save(appUser);
    }

    private Merchant createMerchant(MerchantSignupRequest request) {
        Merchant merchant=merchantMapper.signupRequestToEntity(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);
        return merchantRepository.save(merchant);
    }

    private void checkMerchantEmailExists(String email){
        if(merchantRepository.existsByEmail(email)){
            throw new DuplicateResourceException("DUPLICATE_NERCHANT_EMAIL","Merchant email already exists: "+ email);
        }
    }
}
