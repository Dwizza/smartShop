package com.smartshop.mapper;

import com.smartshop.dto.Request.ClientRequest;
import com.smartshop.dto.Response.ClientResponse;
import com.smartshop.entities.Client;
import com.smartshop.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "tier", expression = "java(request.getTier() != null ? request.getTier() : com.smartshop.entities.Enums.CustomerTier.BASIC)")
    @Mapping(target = "totalOrders", constant = "0")
    @Mapping(target = "totalSpent", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "user", source = "user")
    Client toEntity(ClientRequest request, User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    ClientResponse toResponse(Client entity);

    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "user", expression = "java(user != null ? user : entity.getUser())")
    @Mapping(target = "totalOrders", ignore = true)
    @Mapping(target = "totalSpent", ignore = true)
    @Mapping(target = "tier", expression = "java(request.getTier() != null ? request.getTier() : entity.getTier())")
    void updateEntity(@MappingTarget Client entity, ClientRequest request, User user);
}

