package com.smartshop.mapper;

import com.smartshop.dto.Request.OrderItemRequest;
import com.smartshop.dto.Response.OrderItemResponse;
import com.smartshop.entities.Commande;
import com.smartshop.entities.OrderItem;
import com.smartshop.entities.Product;
import org.mapstruct.*;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commande", source = "commande")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "totalLigne", ignore = true) // calculé côté @AfterMapping
    OrderItem toEntity(OrderItemRequest request, Product product, Commande commande);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productNom", source = "product.nom")
    @Mapping(target = "commandeId", source = "commande.id")
    OrderItemResponse toResponse(OrderItem entity);

    @AfterMapping
    default void computeTotalLigne(@MappingTarget OrderItem entity) {
        if (entity.getProduct() != null && entity.getQuantite() != null && entity.getProduct().getPrixUnitaire() != null) {
            entity.setTotalLigne(entity.getProduct().getPrixUnitaire().multiply(BigDecimal.valueOf(entity.getQuantite())));
        }
    }
}
