package com.mongia.razorpay.payment.entity;

import com.mongia.razorpay.common.enums.PaymentMethod;
import com.mongia.razorpay.common.enums.PaymentStatus;
import com.mongia.razorpay.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name="payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID merchant;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_id",nullable = false)
    private OrderRecord order;

    @Column(nullable = false)
    private String idempotencyKey;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String,Object> methodDetails;

    @Column(length = 100)
    private String bankReference;

    @Column(length = 100)
    private String errorCode;
    @Column(length = 500)
    private String errorDescription;
    private LocalDateTime authorizedAt;
    private LocalDateTime capturedAt;
    private LocalDateTime failedAt;
    private LocalDateTime refundedAt;
    private LocalDateTime settledAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
