package com.smartshop.mapper;

import com.smartshop.dto.request.PaymentRequest;
import com.smartshop.dto.response.PaymentResponse;
import com.smartshop.entity.Commande;
import com.smartshop.entity.Payment;
import com.smartshop.entity.enums.PaymentStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {PaymentStatus.class})
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commande", source = "commande")
    @Mapping(target = "montant", source = "request.montant")
    @Mapping(target = "methode", source = "request.methode")
    Payment toEntity(PaymentRequest request, Commande commande);

    @Mapping(target = "commandeId", source = "commande.id")
    PaymentResponse toResponse(Payment entity);
}
