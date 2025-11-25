package com.smartshop.mapper;

import com.smartshop.dto.Request.CodePromoRequest;
import com.smartshop.dto.Response.CodePromoResponse;
import com.smartshop.entities.CodePromo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CodePromoMapper {

    @Mapping(target = "id", ignore = true)
    CodePromo toEntity(CodePromoRequest request);

    CodePromoResponse toResponse(CodePromo entity);

    void updateEntity(@MappingTarget CodePromo entity, CodePromoRequest request);
}

