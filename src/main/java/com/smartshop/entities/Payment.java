package com.smartshop.entities;

import com.smartshop.entities.Enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    private Long id;

    @NotNull(message = "Le numéro de paiement est obligatoire.")
    @Min(value = 1, message = "Le numéro de paiement doit être valide.")
    private Integer numeroPaiement;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant payé doit être positif.")
    private BigDecimal montant;

    @NotNull(message = "La date de paiement est obligatoire.")
    private LocalDateTime datePaiement;

    private LocalDateTime dateEncaissement;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut de paiement est obligatoire.")
    private PaymentStatus statut;

    private String reference;

    @OneToOne
    @MapsId
    @JoinColumn(name = "commande_id")
    @NotNull(message = "Le paiement doit être lié à une commande.")
    private Commande commande;
}
