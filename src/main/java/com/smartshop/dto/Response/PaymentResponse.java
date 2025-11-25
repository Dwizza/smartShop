package com.smartshop.dto.Response;

import com.smartshop.entities.Enums.PaymentMethod;
import com.smartshop.entities.Enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private Long id; // même que commandeId via MapsId
    private Long commandeId;

    private Integer numeroPaiement;
    private BigDecimal montant;

    private LocalDateTime datePaiement;
    private LocalDateTime dateEncaissement; // nullable

    private PaymentStatus statut;
    private PaymentMethod methode;
}

