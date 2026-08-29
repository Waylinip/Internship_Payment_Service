package org.example.internship_payment_service.mapper;

import org.example.internship_payment_service.dto.PaymentRequestDTO;
import org.example.internship_payment_service.dto.PaymentResponseDTO;
import org.example.internship_payment_service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "status", expression = "java(payment.getStatus().name())")
    PaymentResponseDTO toResponseDTO(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Payment toEntity(PaymentRequestDTO dto);

    List<PaymentResponseDTO> toDtoList(List<Payment> entities);

}
