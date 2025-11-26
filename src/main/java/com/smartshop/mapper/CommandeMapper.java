package com.smartshop.mapper;

import com.smartshop.dto.request.CommandeRequest;
import com.smartshop.dto.response.CommandeResponse;
import com.smartshop.entity.Client;
import com.smartshop.entity.CodePromo;
import com.smartshop.entity.Commande;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.enums.OrderStatus;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {OrderStatus.class})
public interface CommandeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(request.getDate() != null ? request.getDate() : java.time.LocalDateTime.now())")
    @Mapping(target = "sousTotal", ignore = true)
    @Mapping(target = "TVA", ignore = true)
    @Mapping(target = "totalRestant", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "statut", expression = "java(request.getStatut() != null ? request.getStatut() : OrderStatus.PENDING)")
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
