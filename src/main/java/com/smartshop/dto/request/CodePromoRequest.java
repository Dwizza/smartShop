package com.smartshop.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodePromoRequest {

    @NotBlank
    private String code;

    @NotNull
    @Min(1)
    @Max(90)
    private Integer discount;

    @NotNull
    private Boolean active;

    @NotNull
    private LocalDate expirationDate;
}

