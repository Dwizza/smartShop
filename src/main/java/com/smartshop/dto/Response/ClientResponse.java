package com.smartshop.dto.Response;

import com.smartshop.entities.Enums.CustomerTier;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClientResponse {

    private Long id;
    private String nom_complet;

    // Association User
    private Long userId;
    private String email;

    private CustomerTier tier;
    private Integer totalOrders;
    private BigDecimal totalSpent;
}
