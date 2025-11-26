package com.smartshop.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CodePromoRequest {

    @NotBlank(message = "Le code promo est obligatoire.")
    private String code;

    @NotNull(message = "Le pourcentage de remise est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le pourcentage ne peut être négatif.")
    @DecimalMax(value = "1.0", message = "Le pourcentage ne peut dépasser 100% (1.0).")
    private BigDecimal pourcentage;

    @NotNull(message = "Le statut de validité est obligatoire.")
    private Boolean usageUnique;

    @NotNull(message = "Le statut d'utilisation est obligatoire.")
    private Boolean used;
}

