package com.smartshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodePromoResponse {

    private Long id;
    private String code;
    private BigDecimal pourcentage; // 0.0 .. 1.0
    private Boolean usageUnique;
    private Boolean used;
}

