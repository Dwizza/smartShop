package com.smartshop.mapper;

import com.smartshop.dto.Request.PaymentRequest;
import com.smartshop.dto.Response.PaymentResponse;
import com.smartshop.entities.Commande;
import com.smartshop.entities.Payment;
import org.mapstruct.*;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true) // MapsId sera géré côté service (id = commande.id)
    @Mapping(target = "commande", source = "commande")
    @Mapping(target = "numeroPaiement", source = "request.numeroPaiement")
    @Mapping(target = "montant", source = "request.montant")
    @Mapping(target = "datePaiement", expression = "java(request.getDatePaiement() != null ? request.getDatePaiement() : LocalDateTime.now())")
    @Mapping(target = "dateEncaissement", source = "request.dateEncaissement")
    @Mapping(target = "statut", expression = "java(request.getStatut() != null ? request.getStatut() : com.smartshop.entities.Enums.PaymentStatus.PENDING)")
    @Mapping(target = "methode", source = "request.methode")
    Payment toEntity(PaymentRequest request, Commande commande);

    @Mapping(target = "commandeId", source = "commande.id")
    PaymentResponse toResponse(Payment entity);
}

