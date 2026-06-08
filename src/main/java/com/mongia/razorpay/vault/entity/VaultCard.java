package com.mongia.razorpay.vault.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "vault_card")
public class VaultCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 4,nullable = false)
    private String lastFour;

    @Column(nullable = false,length = 50)
    private String brand;

    ///  First 6 digits of the card
    @Column(length = 6,nullable = false)
    private String bin;

    @Column(nullable = false)
    private byte[] encryptedPan;

    ///  String to encrypt the PAN
    @Column(nullable = false)
    private byte[] encrytpedDek;

    @Column(length = 2,nullable = false)
    private String expiryMonth;

    @Column(length = 4,nullable = false)
    private String expiryYear;

    @Column(nullable = false)
    private String cardHolderName;

    private LocalDateTime deletedAt;

}
