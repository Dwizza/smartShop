package com.smartshop.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank(message = "Le nom est obligatoire.")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire.")
    private String prenom;

    // Le 'tier' est géré par la logique métier, souvent pas mis à jour par le client.
    // Si c'est une mise à jour manuelle par un Admin, il faudrait l'ajouter ici:
    // private CustomerTier tier;
}
