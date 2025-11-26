package com.smartshop.mapper;

import com.smartshop.dto.request.ProductRequest;
import com.smartshop.dto.response.ProductResponse;
import com.smartshop.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);

    List<ProductResponse> toResponseList(List<Product> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Product entity, ProductRequest request);

}
