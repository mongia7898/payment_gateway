package com.mongia.razorpay.merchant.mapper;

import com.mongia.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.mongia.razorpay.merchant.dto.response.MerchantResponse;
import com.mongia.razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

    Merchant signupRequestToEntity(MerchantSignupRequest merchantSignupRequest);
    MerchantResponse entityToResponse(Merchant merchant);
}
