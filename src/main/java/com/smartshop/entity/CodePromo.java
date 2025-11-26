package com.smartshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code_promo")
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le code promo est obligatoire.")
    @Column(unique = true)
    private String code;

    @NotNull(message = "Le pourcentage de réduction est obligatoire.")
    @Min(1)
    @Max(90)
    private Integer discount;

    @NotNull
    private Boolean active;

    @NotNull
    private LocalDate expirationDate;
}
