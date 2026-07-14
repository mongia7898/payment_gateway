package com.mongia.razorpay.payment.mapper;

import com.mongia.razorpay.payment.dto.response.OrderResponse;
import com.mongia.razorpay.payment.entity.OrderRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse entityToResponse(OrderRecord orderRecord);
}
