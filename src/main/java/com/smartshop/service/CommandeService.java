package com.smartshop.service;

import com.smartshop.dto.request.CommandeRequest;
import com.smartshop.dto.request.OrderItemRequest;
import com.smartshop.dto.response.CommandeResponse;
import com.smartshop.entity.*;
import com.smartshop.entity.enums.OrderStatus;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.exception.ValidationException;
import com.smartshop.mapper.CommandeMapper;
import com.smartshop.repository.ClientRepository;
import com.smartshop.repository.CodePromoRepository;
import com.smartshop.repository.CommandeRepository;
import com.smartshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CodePromoRepository codePromoRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final OrderItemService orderItemService;
    private final ClientService clientService;

    public CommandeResponse adminCreateCommande(CommandeRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        List<OrderItem> items = new ArrayList<>();
        BigDecimal sousTotal = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            OrderItem item = orderItemService.buildItem(itemReq);
            items.add(item);
            sousTotal = sousTotal.add(item.getTotalLigne());
        }


        BigDecimal remiseFidelite = clientService.calculateFidelityDiscount(client, sousTotal);

        BigDecimal remiseCodePromo = applyPromo(request.getCodePromoId(), sousTotal);

        BigDecimal totalHT = sousTotal.subtract(remiseFidelite).subtract(remiseCodePromo);

        BigDecimal TVA = totalHT.multiply(BigDecimal.valueOf(0.20));
        BigDecimal totalTTC = totalHT.add(TVA);

        Commande commande = Commande.builder()
                .client(client)
                .date(LocalDateTime.now())
                .sousTotal(sousTotal)
                .TVA(TVA)
                .totalRestant(totalTTC)
                .montantRestant(totalTTC)
                .orderStatus(OrderStatus.PENDING)
                .build();

        for (OrderItem item : items) {
            item.setCommande(commande);
        }
        commande.setOrderItems(items);

        updateStock(items);

        Commande saved = commandeRepository.save(commande);

        return commandeMapper.toResponse(saved);
    }


    private BigDecimal applyPromo(Long promoId, BigDecimal sousTotal) {

        if (promoId == null) {
            return BigDecimal.ZERO;
        }

        CodePromo promo = codePromoRepository.findById(promoId)
                .orElseThrow(() -> new ResourceNotFoundException("Promo code not found"));

        if (!promo.getActive()) {
            throw new ValidationException("Promo code inactive");
        }

        if (promo.getExpirationDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Promo code expired");
        }

        BigDecimal discountRate = BigDecimal.valueOf(promo.getDiscount())
                .divide(BigDecimal.valueOf(100));

        return sousTotal.multiply(discountRate);
    }


    private void updateStock(List<OrderItem> items) {

        for (OrderItem item : items) {
            Product p = item.getProduct();
            int newStock = p.getStockDisponible() - item.getQuantite();

            if (newStock < 0) {
                throw new ValidationException("Stock insuffisant pour: " + p.getNom());
            }

            p.setStockDisponible(newStock);
            productRepository.save(p);
        }
    }

    @Transactional
    public CommandeResponse adminConfirmCommande(Long commandeId) {

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));

        if (commande.getOrderStatus() != OrderStatus.PENDING) {
            throw new ValidationException("Cette commande ne peut plus être modifiée.");
        }

        if (commande.getMontantRestant().compareTo(BigDecimal.ZERO) > 0) {
            throw new ValidationException("La commande n'est pas totalement payée.");
        }

        commande.setOrderStatus(OrderStatus.CONFIRMED);

        Client client = commande.getClient();

        client.setTotalOrders(client.getTotalOrders() + 1);

        BigDecimal newTotalSpent = client.getTotalSpent()
                .add(commande.getTotalRestant());
        client.setTotalSpent(newTotalSpent);

        clientService.UpdateTier(client.getId(), client.getTotalSpent(), client.getTotalOrders());

        commandeRepository.save(commande);
        clientRepository.save(client);

        return commandeMapper.toResponse(commande);
    }



}

