package org.example.internship_payment_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.internship_payment_service.dto.PaymentRequestDTO;
import org.example.internship_payment_service.dto.PaymentResponseDTO;
import org.example.internship_payment_service.entity.PaymentStatus;
import org.example.internship_payment_service.mapper.PaymentMapper;
import org.example.internship_payment_service.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO createPayment(@Valid @RequestBody PaymentRequestDTO requestDto) {
        return paymentService.createPayment(requestDto);
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal")
    public List<PaymentResponseDTO> searchPayments(@RequestParam(required = false) Long userId,
                                                   @RequestParam(required = false) Long orderId,
                                                   @RequestParam(required = false) PaymentStatus status) {
        return paymentService.getPayments(userId, orderId, status);
    }

    @GetMapping("/total/users/{userId}")
   // @PreAuthorize("hasRole('ADMIN')")
    public BigDecimal getTotalForUser(@PathVariable  Long userId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return paymentService.getTotalForUser(userId, from, to);
    }

    @GetMapping("/total/users")
    // @PreAuthorize("hasRole('ADMIN')")
    public BigDecimal getTotalForUsers( @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return paymentService.getTotalForAllUsers(from, to);

    }

}
