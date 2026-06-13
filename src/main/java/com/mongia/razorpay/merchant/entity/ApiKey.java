package com.mongia.razorpay.merchant.entity;

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
@Table(name="api_key")
public class ApiKey {
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

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.ORDINAL)
    private ApiKeyEnvironment environment;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled=true;

    private LocalDateTime lastUsedAt;

    private LocalDateTime rotatedAt;

    private LocalDateTime gracePeriodExpiry;
}
