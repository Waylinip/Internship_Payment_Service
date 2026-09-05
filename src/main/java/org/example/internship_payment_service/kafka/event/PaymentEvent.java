package org.example.internship_payment_service.kafka.event;

public record PaymentEvent(
        String eventType,
        Long orderId,
        String status
) {

}
