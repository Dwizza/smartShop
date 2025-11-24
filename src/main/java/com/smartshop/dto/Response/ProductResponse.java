package com.smartshop.dto.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String nom;
    private BigDecimal prixUnitaire;
    private String description;
}
