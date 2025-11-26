package com.smartshop.dto.response;

import com.smartshop.entity.enums.CustomerTier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String username;
    private String nom;
    private String email;
    private CustomerTier niveau;
    private Integer totalOrders;
    private BigDecimal totalSpent;
}

