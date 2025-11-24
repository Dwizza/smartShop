package com.smartshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
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
@Table(name = "code_promo")
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le code promo est obligatoire.")
    @Column(unique = true)
    private String code;

    @NotNull(message = "Le pourcentage de remise est obligatoire.")
    @DecimalMin(value = "0.0", message = "Le pourcentage ne peut être négatif.")
    @DecimalMax(value = "1.0", message = "Le pourcentage ne peut dépasser 100% (1.0).")
    private BigDecimal pourcentage;
}
