package org.example.internship_payment_service.repository;

import org.example.internship_payment_service.entity.Payment;
import org.example.internship_payment_service.entity.PaymentStatus;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String>, SearchPayments {

    @Aggregation(pipeline = {
            "{ $match: { 'userId': ?0, 'timestamp': { $gte: ?1, $lte: ?2 } } }",
            "{ $group: { '_id': null, 'total': { $sum: '$paymentAmount' } } }"
    })
    BigDecimal sumPaymentsForUserInRange(Long userId, Instant from, Instant to);

    @Aggregation(pipeline = {
            "{ $match: { 'status': 'SUCCESS', 'timestamp': { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { '_id': null, 'total': { $sum: '$paymentAmount' } } }"
    })
    BigDecimal sumPaymentsInRange(Instant from, Instant to);


}
