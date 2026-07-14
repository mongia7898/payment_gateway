package com.mongia.razorpay.merchant.mapper;

import com.mongia.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.mongia.razorpay.merchant.dto.response.ApiKeyResponse;
import com.mongia.razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {


    ApiKeyResponse convertEntityToResponse(ApiKey apiKey);

    List<ApiKeyResponse> entityListToResponseList(List<ApiKey> apiKeyList);


}
