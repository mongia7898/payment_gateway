package com.mongia.razorpay.payment.processor.config;

import com.mongia.razorpay.common.enums.PaymentMethod;
import com.mongia.razorpay.payment.processor.PaymentProcessor;
import com.mongia.razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.mongia.razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.mongia.razorpay.payment.processor.strategy.UpiPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap(){
        return Map.of(
                PaymentMethod.CARD, new CardPaymentProcessor(),
                PaymentMethod.NET_BANKING, new NetBankingPaymentProcessor(),
                PaymentMethod.UPI,new UpiPaymentProcessor()
        );
    }
}
