package com.mongia.razorpay.merchant.entity;

import com.mongia.razorpay.common.entity.BaseEntity;
import com.mongia.razorpay.common.enums.ApiKeyEnvironment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="api_key",indexes={
        @Index(name="idx_api_key_merchant_id",columnList = "merchant_id"),
        @Index(name="idx_api_key_merchant_env",columnList = "merchant_id,environment,enabled")
})
public class ApiKey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id")
    private Merchant merchant;

    @Column(nullable = false, length = 200,unique = true)
    private String keyId;

    @Column(nullable = false, length = 200)
    private String keySecretHash;

    @Column(length = 200)
    private String previousKeySecretHash;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ApiKeyEnvironment environment;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled=true;

    private LocalDateTime lastUsedAt;

    private LocalDateTime rotatedAt;

    private LocalDateTime gracePeriodExpiry;
}
