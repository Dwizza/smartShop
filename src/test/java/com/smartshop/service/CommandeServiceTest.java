package com.smartshop.service;

import com.smartshop.dto.request.CommandeRequest;
import com.smartshop.dto.request.OrderItemRequest;
import com.smartshop.dto.response.CommandeResponse;
import com.smartshop.entity.Client;
import com.smartshop.entity.Commande;
import com.smartshop.entity.OrderItem;
import com.smartshop.entity.Product;
import com.smartshop.exception.ValidationException;
import com.smartshop.mapper.CommandeMapper;
import com.smartshop.repository.ClientRepository;
import com.smartshop.repository.CommandeRepository;
import com.smartshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CommandeMapper commandeMapper;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private CommandeService commandeService;

    private Client client;
    private Product product;
    private OrderItem item;
    private OrderItemRequest itemReq;
    private CommandeRequest request;

    @BeforeEach
    void setUp() {

        client = Client.builder()
                .id(1L)
                .nom_complet("John Doe")
                .email("johndoe@gmail.com")
                .totalOrders(0)
                .totalSpent(BigDecimal.ZERO)
                .build();

        product = Product.builder()
                .id(1L)
                .nom("Produit A")
                .stockDisponible(10)
                .prixUnitaire(BigDecimal.valueOf(10))
                .build();

        item = new OrderItem();
        item.setProduct(product);
        item.setQuantite(2);
        item.setTotalLigne(BigDecimal.valueOf(20));

        itemReq = new OrderItemRequest();
        itemReq.setProductId(1L);
        itemReq.setQuantity(2);

        request = new CommandeRequest();
        request.setClientId(1L);
        request.setItems(List.of(itemReq));
    }


    @Test
    void createCommande_ShouldReject_WhenStockInsufficient() {

        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        when(orderItemService.buildItem(itemReq)).thenReturn(item);

        when(clientService.calculateFidelityDiscount(eq(client), any()))
                .thenReturn(BigDecimal.ZERO);

        product.setStockDisponible(1);

        // Act
        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> commandeService.CreateCommande(request)
        );

        // Assert
        assertEquals("Stock insuffisant pour un ou plusieurs produits.", ex.getMessage());

        verify(commandeRepository, times(1)).save(any(Commande.class));
    }

    @Test
    void createCommande_ShouldSave_WhenStockOK() {

        // A — Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(orderItemService.buildItem(itemReq)).thenReturn(item);

        when(clientService.calculateFidelityDiscount(eq(client), any()))
                .thenReturn(BigDecimal.ZERO);

        when(commandeRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        when(commandeMapper.toResponse(any()))
                .thenReturn(new CommandeResponse());

        // A — Act
        CommandeResponse response = commandeService.CreateCommande(request);

        // A — Assert
        assertNotNull(response);

        verify(productRepository, times(1)).save(any(Product.class));
        verify(commandeRepository, times(1)).save(any(Commande.class));
    }


}
