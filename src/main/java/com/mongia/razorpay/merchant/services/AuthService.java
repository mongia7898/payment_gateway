package com.mongia.razorpay.merchant.services;

import com.mongia.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.mongia.razorpay.merchant.dto.response.MerchantResponse;


public interface AuthService {
     MerchantResponse signup(MerchantSignupRequest request);
}
