package com.smartshop.mapper;

import com.smartshop.dto.request.CommandeRequest;
import com.smartshop.dto.response.CommandeResponse;
import com.smartshop.dto.response.OrderItemResponse;
import com.smartshop.entity.Client;
import com.smartshop.entity.CodePromo;
import com.smartshop.entity.Commande;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.enums.OrderStatus;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderItemMapper.class})
public interface CommandeMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "promoCode", source = "codePromo.code")
    @Mapping(target = "items", ignore = true)
    CommandeResponse toResponse(Commande commande);
}


