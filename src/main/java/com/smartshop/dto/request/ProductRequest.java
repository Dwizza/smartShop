package com.smartshop.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required.")
    private String nom;

    @NotBlank(message = "Le SKU du produit est obligatoire.")
    private String sku;

    @NotNull(message = "Unit price is required.")
    @DecimalMin(value = "0.01", message = "Price must be positive.")
    private BigDecimal prixUnitaire;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock must be greater than or equal to 0.")
    private Integer stockDisponible;

    @NotNull(message = "Deletion status is required.")
    private Boolean isDeleted; // Pour la suppression logique (true/false)
}