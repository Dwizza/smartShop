package com.smartshop.mapper;

import com.smartshop.dto.Request.UserRequest;
import com.smartshop.dto.Response.UserResponse;
import com.smartshop.entities.Enums.UserRole;
import com.smartshop.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "userRole", expression = "java(UserRole.CLIENT)")
    User toEntity(UserRequest request);

    UserResponse toResponse(User entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "userRole", ignore = true) // rôle non modifié ici
    void updateEntity(@MappingTarget User entity, UserRequest request);
}
