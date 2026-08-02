package com.mongia.razorpay.payment.config;

import com.mongia.razorpay.common.enums.PaymentMethod;
import com.mongia.razorpay.payment.gateway.PaymentAdapter;
import com.mongia.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.mongia.razorpay.payment.gateway.adapter.NetBankingPaymentAdapter;
import com.mongia.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentAdapterConfig {

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap(){
        return Map.of(
                PaymentMethod.CARD,new CardPaymentAdapter(),
                PaymentMethod.NET_BANKING,new NetBankingPaymentAdapter(),
                PaymentMethod.UPI,new UpiPaymentAdapter()
        );
    }
}
