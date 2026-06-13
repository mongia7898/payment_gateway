package com.mongia.razorpay.operations.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "settlement_payment")
@Data
public class SettlementPayment {

    @EmbeddedId
    private SettlementPaymentId id;

    @MapsId("settlementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id")
    private Settlement settlement;
}
