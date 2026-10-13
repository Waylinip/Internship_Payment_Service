package org.example.internship_payment_service.repository;

import org.example.internship_payment_service.entity.Payment;
import org.example.internship_payment_service.entity.PaymentStatus;

import java.util.List;

public interface SearchPayments {
    List<Payment> findBy(Long userId, Long orderId, PaymentStatus status);
}
