package com.smartshop.dto.Response;

import com.smartshop.entities.Enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeResponse {

    private Long id;
    private LocalDateTime date;

    private BigDecimal sousTotal;
    private BigDecimal TVA;
    private BigDecimal totalRestant;
    private BigDecimal montantRestant;

    private OrderStatus statut;

    private Long clientId;
    private Long codePromoId; // nullable
    private Long paymentId;   // nullable

    private List<OrderItemResponse> items;
}

