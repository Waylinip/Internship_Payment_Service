package org.example.internship_payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private Long orderId;
    private Long userId;
    private String status;
    private LocalDateTime timestamp;
    private BigDecimal paymentAmount;
}
