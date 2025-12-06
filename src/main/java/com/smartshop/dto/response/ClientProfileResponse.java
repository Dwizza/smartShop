package com.smartshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ClientProfileResponse {
    private String nom_complet;
    private String email;
    private String tier;
    private List<CommandeResponse> commandes;
}
