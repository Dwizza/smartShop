package com.smartshop.dto.Response;

import com.smartshop.entities.Enums.CustomerTier;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClientResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email; // Information de l'entité User associée
    private CustomerTier tier;
    private Integer totalOrders;
    private BigDecimal totalSpent;
}
