package com.mongia.razorpay.payment.mapper;

import com.mongia.razorpay.payment.dto.response.PaymentResponse;
import com.mongia.razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mappings({
            @Mapping(target = "orderId", source = "order.id"),
    }
    )
    PaymentResponse entityToResponse(Payment payment);

    List<PaymentResponse> entityListToResponseList(List<Payment> paymentList);
}
