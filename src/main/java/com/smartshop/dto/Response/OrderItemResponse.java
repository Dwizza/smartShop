package com.smartshop.dto.Response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productNom;
    private Integer quantite;
    private BigDecimal totalLigne;
    private Long commandeId;
}

