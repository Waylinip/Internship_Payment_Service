package org.example.internship_payment_service.repository;

import org.example.internship_payment_service.entity.Payment;
import org.example.internship_payment_service.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Query("""
            SELECT p FROM Payment p
            WHERE (:userId IS NULL OR p.userId = :userId)
              AND (:orderId IS NULL OR p.orderId = :orderId)
              AND (:status IS NULL OR p.status = :status)
            """)
    List<Payment> findBy( @Param("userId") Long userId,
                          @Param("orderId") Long orderId,
                          @Param("status") PaymentStatus status);

//    List<Payment> findByUserId(Long userId);
//
//    List<Payment> findByOrderId(Long orderId);
//
//    List<Payment> findByStatus(PaymentStatus status);

    @Query("""
            SELECT COALESCE(SUM(p.paymentAmount), 0)
            FROM Payment p
            WHERE p.userId = :userId
              AND p.timestamp BETWEEN :from AND :to
            """)
    BigDecimal sumPaymentsForUserInRange(
            @Param("userId") Long userId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            SELECT COALESCE(SUM(p.paymentAmount), 0)
            FROM Payment p
            WHERE p.timestamp BETWEEN :from AND :to
            """)
    BigDecimal sumPaymentsInRange(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
