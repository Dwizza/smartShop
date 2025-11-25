package com.smartshop.dto.Request;

import com.smartshop.entities.Enums.PaymentMethod;
import com.smartshop.entities.Enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequest {

    @NotNull(message = "La commande liée est obligatoire.")
    private Long commandeId;

    @NotNull(message = "Le numéro de paiement est obligatoire.")
    @Min(value = 1, message = "Le numéro de paiement doit être valide.")
    private Integer numeroPaiement;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le montant doit être positif.")
    private BigDecimal montant;

    // Optionnel: le service peut mettre la date actuelle si non fournie
    private LocalDateTime datePaiement;

    private LocalDateTime dateEncaissement; // optionnel

    @NotNull(message = "La méthode de paiement est obligatoire.")
    private PaymentMethod methode;

    // Optionnel: si non fourni -> PENDING
    private PaymentStatus statut;
}

