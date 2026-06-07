package com.mongia.razorpay.common.enums;

public enum PaymentEvent {
    AUTHORIZE_ATTEMPT,
    AUTHORIZE_SUCCESS,
    AUTHORIZE_FAILED,
    CAPTURE_REQUEST,
    CAPTURE_SUCCESS,
    CAPTURE_FAILED,
    REFUND_INIT,
    REFUND_COMPLETE,
    REFUND_FAILED
}
