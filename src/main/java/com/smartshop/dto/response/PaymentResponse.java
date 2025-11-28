package com.smartshop.dto.response;

import com.smartshop.entity.enums.PaymentMethod;
import com.smartshop.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long commandeId;

    private Integer numeroPaiement;
    private BigDecimal montant;

    private LocalDateTime datePaiement;
    private LocalDateTime dateEncaissement;

    private PaymentStatus paymentStatus;
    private PaymentMethod methode;
}

