package com.smartshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CodePromoResponse {
    private Long id;
    private String code;
    private Integer discount;
    private Boolean active;
    private LocalDate expirationDate;
}

