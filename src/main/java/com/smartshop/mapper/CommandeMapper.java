package com.smartshop.mapper;

import com.smartshop.dto.Request.CommandeRequest;
import com.smartshop.dto.Response.CommandeResponse;
import com.smartshop.entities.*;
import org.mapstruct.*;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface CommandeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(request.getDate() != null ? request.getDate() : LocalDateTime.now())")
    @Mapping(target = "sousTotal", ignore = true)
    @Mapping(target = "TVA", ignore = true)
    @Mapping(target = "totalRestant", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "statut", expression = "java(request.getStatut() != null ? request.getStatut() : com.smartshop.entities.Enums.OrderStatus.PENDING)")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "codePromo", source = "codePromo")
    @Mapping(target = "paiement", ignore = true)
    @Mapping(target = "orderItems", source = "orderItems")
    Commande toEntity(CommandeRequest request, Client client, CodePromo codePromo, List<OrderItem> orderItems);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "codePromoId", source = "codePromo.id")
    @Mapping(target = "paymentId", source = "paiement.id")
    @Mapping(target = "items", source = "orderItems")
    CommandeResponse toResponse(Commande entity);
}

