package com.smartshop.dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required.")
    private String nom;

    @NotNull(message = "Unit price is required.")
    @DecimalMin(value = "0.01", message = "Price must be positive.")
    private BigDecimal prixUnitaire;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock must be greater than or equal to 0.")
    private Integer stockDisponible;

    @NotNull(message = "Deletion status is required.")
    private Boolean isDeleted; // Pour la suppression logique (true/false)
}