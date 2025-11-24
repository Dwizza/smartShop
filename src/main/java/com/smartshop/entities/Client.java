package com.smartshop.entities;

import com.smartshop.entities.Enums.CustomerTier;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire.")
    private String nom_complet;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le niveau d'abonnement (tier) est obligatoire.")
    private CustomerTier tier;

    @NotNull(message = "Le total des commandes est obligatoire.")
    @Min(value = 0, message = "Le nombre de commandes ne peut être négatif.")
    private Integer totalOrders = 0;

    @NotNull(message = "Le total des dépenses est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le total des dépenses ne peut être négatif.")
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Le client doit être lié à un utilisateur.")
    private User user;

    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;
}