package com.mongia.razorpay.operations.entity;

import com.mongia.razorpay.common.enums.WebhookEventStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "webhook_entity")
@Data
public class WebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID merchantId;

    @Column(nullable = false,length = 100)
    private String eventType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String,Object> payload;

    @Column(nullable = false,length = 100)
    private String targetUrl;

    @Column(nullable = false)
    private Integer attempts=0;

    private String signature;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WebhookEventStatus status=WebhookEventStatus.PENDING;

    private LocalDateTime nextRetryAt;

    private LocalDateTime lastAttemptAt;

    private Integer lastResponseCode;

    @Column(length=1000)
    private String lastResponseBody;

    private LocalDateTime deliveredAt;
}
