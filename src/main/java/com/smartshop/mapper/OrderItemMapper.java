package com.smartshop.mapper;

import com.smartshop.dto.request.OrderItemRequest;
import com.smartshop.dto.response.OrderItemResponse;
import com.smartshop.entity.Commande;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.Product;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commande", source = "commande")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "totalLigne", ignore = true)
    OrderItem toEntity(OrderItemRequest request, Product product, Commande commande);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productNom", source = "product.nom")
    @Mapping(target = "commandeId", source = "commande.id")
    OrderItemResponse toResponse(OrderItem entity);

    @AfterMapping
    default void computeTotalLigne(@MappingTarget OrderItem entity) {
        if (entity.getProduct() != null && entity.getQuantite() != null && entity.getProduct().getPrixUnitaire() != null) {
            entity.setTotalLigne(
                    entity.getProduct().getPrixUnitaire().multiply(BigDecimal.valueOf(entity.getQuantite()))
            );
        }
    }
}
