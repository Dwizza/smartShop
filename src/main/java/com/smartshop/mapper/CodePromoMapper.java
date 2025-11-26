package com.smartshop.mapper;

import com.smartshop.dto.request.CodePromoRequest;
import com.smartshop.dto.response.CodePromoResponse;
import com.smartshop.entity.CodePromo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CodePromoMapper {

    @Mapping(target = "id", ignore = true)
    CodePromo toEntity(CodePromoRequest request);

    CodePromoResponse toResponse(CodePromo entity);

    void updateEntity(@MappingTarget CodePromo entity, CodePromoRequest request);
}
