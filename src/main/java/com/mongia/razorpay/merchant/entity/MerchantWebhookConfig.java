package com.mongia.razorpay.merchant.entity;

import com.mongia.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="merchant_webhook_config",indexes = {
        @Index(name = "idx_merchant_webhook_config_merchant_id",columnList = "merchant_id,enabled")
})
public class MerchantWebhookConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "merchant_id",nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private String targetUrl;

    @Column(length = 255)
    private String webhookSecretHash;

    @Column(nullable = false)
    private Boolean enabled=true;

    ///  Comma separated list of events types to subscribe
    /// Question: Why not list of String?
    private String eventTypes;


}
