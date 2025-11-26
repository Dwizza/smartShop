package com.smartshop.mapper;

import com.smartshop.dto.request.UserRequest;
import com.smartshop.dto.response.UserResponse;
import com.smartshop.entity.User;
import com.smartshop.entity.enums.UserRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {UserRole.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "userRole", expression = "java(UserRole.CLIENT)")
    User toEntity(UserRequest request);

    UserResponse toResponse(User entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "userRole", ignore = true) // ما كنبدلوش الدور هنا
    void updateEntity(@MappingTarget User entity, UserRequest request);
}
