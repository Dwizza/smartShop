package com.smartshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire.")
    private String nom;

    @NotNull(message = "Le prix unitaire est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être strictement positif.")
    private BigDecimal prixUnitaire;

    @NotNull(message = "La quantité en stock est obligatoire.")
    @DecimalMin(value = "0", inclusive = true, message = "La quantité en stock ne peut pas être négative.")
    private Integer stockDisponible;


    @NotNull(message = "Le statut de suppression est obligatoire.")
    private Boolean isDeleted;
}
