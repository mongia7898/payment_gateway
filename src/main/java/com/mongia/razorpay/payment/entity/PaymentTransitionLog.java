package com.mongia.razorpay.payment.entity;

import com.mongia.razorpay.common.enums.PaymentActor;
import com.mongia.razorpay.common.enums.PaymentEvent;
import com.mongia.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="payment_tranisiton_log")
public class PaymentTransitionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="payment_id")
    private Payment payment;


    @Enumerated(EnumType.STRING)
    private PaymentEvent event;

    @Enumerated(EnumType.STRING)
    private PaymentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus toStatus;

    @Enumerated(EnumType.STRING)
    private PaymentActor actor;


    @CreationTimestamp
    private LocalDateTime occuredAt;
}
