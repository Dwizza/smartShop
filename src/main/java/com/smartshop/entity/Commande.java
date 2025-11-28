package com.smartshop.entity;

import com.smartshop.entity.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "commandes")
@ToString(exclude = {"client", "orderItems", "payments"})
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de commande est obligatoire.")
    private LocalDateTime date;

    @NotNull(message = "Le sous-total est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le sous-total ne peut être négatif.")
    private BigDecimal sousTotal;

    @NotNull(message = "La TVA est obligatoire.")
    @DecimalMin(value = "0.0", message = "La TVA ne peut être négative.")
    private BigDecimal TVA;

    @NotNull(message = "Le montant total est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le montant total ne peut être négatif.")
    private BigDecimal totalRestant;

    @NotNull(message = "Le montant restant à payer est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le montant restant ne peut être négatif.")
    private BigDecimal montantRestant;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut de commande est obligatoire.")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "Un client est requis pour une commande.")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "code_promo_id")
    private CodePromo codePromo;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<Payment> payment = new ArrayList<>();
}
