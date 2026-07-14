package com.mongia.razorpay.merchant.entity;

import com.mongia.razorpay.common.entity.BaseEntity;
import com.mongia.razorpay.common.enums.BusinessType;
import com.mongia.razorpay.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="merchant",indexes = {
        @Index(name="idx_merchant_status",columnList = "status")
})
public class Merchant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length=100,unique = true)
    private String email;

    @Column(length=10)
    private String contactNumber;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @Column(length = 50)
    private String businessName;

    @Column(length = 100)
    private String websiteUrl;

    @Column(length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status=MerchantStatus.PENDING;

    @Column(length = 20)
    private String gstId;

    @Column(length = 20)
    private String pan;

    @Column(length = 20)
    private String settlementBankAccount;
    @Column(length = 20)
    private String settlementBankIfsc;
    @Column(length = 50)
    private String settlementAccountHolderName;

}
