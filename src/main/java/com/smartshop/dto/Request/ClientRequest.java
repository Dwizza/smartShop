package com.smartshop.dto.Request;

import com.smartshop.entities.Enums.CustomerTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {

    // Nom complet du client, correspond au champ 'nom_complet' de l'entité Client
    @NotBlank(message = "Le nom complet est obligatoire.")
    private String nom_complet;

    // Identifiant de l'utilisateur associé (relation OneToOne obligatoire)
    @NotNull(message = "L'utilisateur lié (userId) est obligatoire.")
    private Long userId;

    // Optionnel: permet à un admin de fixer le tier manuellement. Sinon, géré par la logique métier.
    private CustomerTier tier;
}
