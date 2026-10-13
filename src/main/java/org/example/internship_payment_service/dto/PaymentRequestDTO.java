package org.example.internship_payment_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    @NotNull(message = "orderId must not be null")
    private Long orderId;

    @NotNull(message = "userId must not be null")
    private Long userId;

    @NotNull(message = "paymentAmount must not be null")
    @DecimalMin(value = "0.01", message = "paymentAmount must be greater than 0")
    private BigDecimal paymentAmount;
}
