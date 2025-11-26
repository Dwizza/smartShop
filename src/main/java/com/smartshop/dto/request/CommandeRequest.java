package com.smartshop.dto.request;

import com.smartshop.entity.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommandeRequest {

    @NotNull(message = "Le client est obligatoire.")
    private Long clientId;

    private Long codePromoId;

    private LocalDateTime date;

    private OrderStatus statut;

    @NotNull(message = "Les lignes de commande sont obligatoires.")
    @Size(min = 1, message = "Au moins une ligne de commande est requise.")
    @Valid
    private List<OrderItemRequest> items;
}

