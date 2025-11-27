package com.smartshop.mapper;

import com.smartshop.dto.request.OrderItemRequest;
import com.smartshop.dto.response.OrderItemResponse;
import com.smartshop.entity.Commande;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.Product;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.nom")
    @Mapping(target = "unitPrice", source = "product.prixUnitaire")
    OrderItemResponse toResponse(OrderItem item);

    List<OrderItemResponse> toResponses(List<OrderItem> items);
}

