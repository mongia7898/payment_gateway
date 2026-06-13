package com.mongia.razorpay.merchant.dto.request;

import com.mongia.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record MerchantSignupRequest(
        @NotBlank(message = "Name should not be blank")
        @Size(min=3, max=50, message="Name should be within 3 to 50 characters")
        String name,

        @Email(message = "Should be a valid email")
        @NotBlank
        String email,

        @NotBlank
        @Size(min=8, message = "Password should be 8 character long")
        String password,

        @NotBlank
        @Size(min=5, max=50, message = "Business Name should be between 5 to 50 characters")
        String businessName,

        BusinessType businessType
) {

}
