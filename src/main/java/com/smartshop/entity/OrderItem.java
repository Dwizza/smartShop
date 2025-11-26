package com.smartshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Order_Items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La quantité est obligatoire.")
    @Min(value = 1, message = "La quantité doit être au moins 1.")
    private Integer quantite;


    @NotNull(message = "Le total de la ligne est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le total de la ligne ne peut être négatif.")
    private BigDecimal totalLigne;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    @NotNull(message = "La ligne doit être liée à une commande.")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "La ligne doit être liée à un produit.")
    private Product product;
}
