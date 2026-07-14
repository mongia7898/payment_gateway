package com.mongia.razorpay.payment.entity;

import com.mongia.razorpay.common.entity.BaseEntity;
import com.mongia.razorpay.common.entity.Money;
import com.mongia.razorpay.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order_record",indexes = {
        @Index(name="idx_order_id_merchant_id",columnList = "id,merchant_id"),
        @Index(name="idx_order_record_receipt",columnList = "receipt"),
        @Index(name="idx_order_merchant_id",columnList = "merchant_id"),

})
public class OrderRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // no FK- cross service boundary
    @Column(name="merchant_id", nullable = false)
    private UUID merchantId;

    @Embedded
    private Money amount;

    @Column(length = 100)
    private String receipt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus=OrderStatus.CREATED;

    @Column(nullable = false)
    @Builder.Default
    private Integer attempts=0;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> notes;

    @Column(nullable = false)
    private LocalDateTime expiresAt;


}
