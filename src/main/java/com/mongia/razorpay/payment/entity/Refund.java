package com.mongia.razorpay.payment.entity;

import com.mongia.razorpay.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

}
