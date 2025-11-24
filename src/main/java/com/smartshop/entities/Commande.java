package com.smartshop.entities;

import com.smartshop.entities.Enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commande")
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
    private OrderStatus statut;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "Un client est requis pour une commande.")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "code_promo_id")
    private CodePromo codePromo;

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment paiement;
}
