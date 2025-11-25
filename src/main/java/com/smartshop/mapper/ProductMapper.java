package com.smartshop.mapper;

import com.smartshop.dto.Request.ProductRequest;
import com.smartshop.dto.Response.ProductResponse;
import com.smartshop.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);

    void updateEntity(@MappingTarget Product entity, ProductRequest request);
}

