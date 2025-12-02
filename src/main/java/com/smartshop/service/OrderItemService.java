package com.smartshop.service;

import com.smartshop.dto.request.OrderItemRequest;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.Product;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.exception.ValidationException;
import com.smartshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ProductRepository productRepository;

    public OrderItem buildItem(OrderItemRequest req) {

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

//        if (product.getStockDisponible() < req.getQuantity()) {
//            throw new ValidationException("Stock insuffisant pour: " + product.getNom());
//        }

        BigDecimal total = product.getPrixUnitaire()
                .multiply(BigDecimal.valueOf(req.getQuantity()));

        return OrderItem.builder()
                .product(product)
                .quantite(req.getQuantity())
                .totalLigne(total)
                .build();
    }
}

