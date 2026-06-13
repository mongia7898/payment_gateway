package com.mongia.razorpay.common.exceptions;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private  final String errorCode;
    private  final String message;

    public DuplicateResourceException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
        this.message=message;
    }
}
