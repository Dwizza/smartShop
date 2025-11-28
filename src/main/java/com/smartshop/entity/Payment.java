package com.smartshop.entity;

import com.smartshop.entity.enums.PaymentMethod;
import com.smartshop.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
@ToString(exclude = {"commande"})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroPaiement;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant payé doit être positif.")
    private BigDecimal montant;

    @NotNull(message = "La date de paiement est obligatoire.")
    private LocalDateTime datePaiement;

    private LocalDateTime dateEncaissement;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut de paiement est obligatoire.")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le mode de paiement est obligatoire.")
    private PaymentMethod methode;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    @NotNull(message = "Le paiement doit être lié à une commande.")
    private Commande commande;
}
