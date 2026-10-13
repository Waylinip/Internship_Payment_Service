package org.example.internship_payment_service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    private String id;

    private Long orderId;
    private Long userId;
    private PaymentStatus status;
    private Instant timestamp;
    private BigDecimal paymentAmount;
}
