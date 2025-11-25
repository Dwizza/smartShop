package com.smartshop.dto.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CodePromoResponse {

    private Long id;
    private String code;
    private BigDecimal pourcentage; // 0.0 .. 1.0
    private Boolean usageUnique;
    private Boolean used;
}

