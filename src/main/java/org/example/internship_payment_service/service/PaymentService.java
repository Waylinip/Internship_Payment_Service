package org.example.internship_payment_service.service;

import org.example.internship_payment_service.client.RandomNumberClient;
import org.example.internship_payment_service.dto.PaymentRequestDTO;
import org.example.internship_payment_service.dto.PaymentResponseDTO;
import org.example.internship_payment_service.entity.Payment;
import org.example.internship_payment_service.entity.PaymentStatus;
import org.example.internship_payment_service.mapper.PaymentMapper;
import org.example.internship_payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RandomNumberClient randomNumberClient;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, RandomNumberClient randomNumberClient) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.randomNumberClient = randomNumberClient;
    }

    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Payment payment = paymentMapper.toEntity(request);

        payment.setStatus(PaymentStatus.PENDING);

        int n = randomNumberClient.getRandomNumber();
        if (n % 2 == 0) {
            payment.setStatus(PaymentStatus.SUCCESS);
        }else{
            payment.setStatus(PaymentStatus.FAILED);
        }

        payment.setTimestamp(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toResponseDTO(savedPayment);
    }

    public List<PaymentResponseDTO> getPayments(Long userId, Long orderId, PaymentStatus status) {

        validateFilters(userId, orderId, status);

        List<Payment> payments = paymentRepository.findBy(userId, orderId, status);

        return paymentMapper.toDtoList(payments);
    }

    public BigDecimal getTotalForUser(Long userId, LocalDateTime from, LocalDateTime to) {

        validateDateRange(from, to);

        return paymentRepository.sumPaymentsForUserInRange(userId, from, to);
    }

    public BigDecimal getTotalForAllUsers(LocalDateTime from, LocalDateTime to) {

        validateDateRange(from, to);

        return paymentRepository.sumPaymentsInRange(from, to);
    }

    private void validateFilters(Long userId, Long orderId, PaymentStatus status) {

        if (userId == null && orderId == null && status == null) {
            throw new IllegalArgumentException(
                    "At least one filter must be provided"
            );
        }
    }

    private void validateDateRange(LocalDateTime from, LocalDateTime to) {

        if (from == null || to == null) {
            throw new IllegalArgumentException(
                    "Both from and to dates must be provided"
            );
        }

        if (from.isAfter(to)) {
            throw new IllegalArgumentException(
                    "from must not be after to"
            );
        }
    }
}