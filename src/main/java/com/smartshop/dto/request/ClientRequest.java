package com.smartshop.dto.request;

import com.smartshop.entity.enums.CustomerTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {

    @NotBlank(message = "Le nom complet est obligatoire.")
    private String nom_complet;

    @NotNull(message = "L'utilisateur lié (userId) est obligatoire.")
    private Long userId;

    private CustomerTier tier;
}
