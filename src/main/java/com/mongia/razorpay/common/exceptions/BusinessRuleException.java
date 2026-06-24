package com.mongia.razorpay.common.exceptions;

public class BusinessRuleException extends RuntimeException {
    private String errorCode;
    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
