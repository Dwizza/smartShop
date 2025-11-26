package com.smartshop.dto.response;

import com.smartshop.entity.enums.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
