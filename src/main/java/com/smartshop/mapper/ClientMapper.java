package com.smartshop.mapper;

import com.smartshop.dto.request.ClientRequest;
import com.smartshop.dto.response.ClientProfileResponse;
import com.smartshop.dto.response.ClientResponse;
import com.smartshop.entity.Client;
import com.smartshop.entity.User;
import com.smartshop.entity.enums.CustomerTier;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {CustomerTier.class, BigDecimal.class})
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "nom_complet", source = "request.nom")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "tier", expression = "java(CustomerTier.BASIC)")
    @Mapping(target = "totalOrders", constant = "0")
    @Mapping(target = "totalSpent", expression = "java(BigDecimal.ZERO)")
    Client toEntity(ClientRequest request, User user);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "nom", source = "nom_complet")
    @Mapping(target = "niveau", source = "tier")
    ClientResponse toResponse(Client entity);

    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "user", expression = "java(user != null ? user : entity.getUser())")
    @Mapping(target = "nom_complet", source = "request.nom")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "tier", ignore = true)
    @Mapping(target = "totalOrders", ignore = true)
    @Mapping(target = "totalSpent", ignore = true)
    void updateEntity(@MappingTarget Client entity, ClientRequest request, User user);

    @Mapping(target = "nom_complet", source = "nom_complet")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "tier", source = "tier")
    @Mapping(target = "commandes", source = "commandes")
    ClientProfileResponse toResponseProfile(Client client);
}


