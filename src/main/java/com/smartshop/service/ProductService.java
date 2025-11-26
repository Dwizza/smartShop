package com.smartshop.service;

import com.smartshop.dto.request.ProductRequest;
import com.smartshop.dto.response.ProductResponse;
import com.smartshop.entity.Product;
import com.smartshop.exception.ValidationException;
import com.smartshop.mapper.ProductMapper;
import com.smartshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest product){
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new ValidationException("SKU already exists");
        }
        Product p = productMapper.toEntity(product);
        Product savedProduct = productRepository.save(p);
        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Product not found"));
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findByIsDeletedFalse();
        return productMapper.toResponseList(products);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Product not found"));

        productMapper.updateEntity(existingProduct, productRequest);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Product not found"));
        existingProduct.setIsDeleted(true);
        productRepository.save(existingProduct);
    }

}
