package com.smartshop.mapper;

import com.smartshop.dto.request.ProductRequest;
import com.smartshop.dto.response.ProductResponse;
import com.smartshop.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Product entity, ProductRequest request);
}
