package com.smartshop.dto.response;

import com.smartshop.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

