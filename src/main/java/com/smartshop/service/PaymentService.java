package com.smartshop.service;

import com.smartshop.dto.request.PaymentRequest;
import com.smartshop.dto.response.PaymentResponse;
import com.smartshop.entity.Commande;
import com.smartshop.entity.Payment;
import com.smartshop.entity.enums.OrderStatus;
import com.smartshop.entity.enums.PaymentMethod;
import com.smartshop.entity.enums.PaymentStatus;
import com.smartshop.mapper.PaymentMapper;
import com.smartshop.repository.CommandeRepository;
import com.smartshop.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CommandeRepository commandeRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (commande.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Cette commande ne peut plus recevoir de paiements");
        }

        if (request.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Montant invalide");
        }

        if (request.getMontant().compareTo(commande.getMontantRestant()) > 0) {
            throw new RuntimeException("Le montant dépasse le montant restant à payer");
        }

        int n = paymentRepository.countByCommandeId(commande.getId()) + 1;

        BigDecimal nouveauReste = commande.getMontantRestant()
                .subtract(request.getMontant());
        commande.setMontantRestant(nouveauReste);

        PaymentStatus status = PaymentStatus.PAID;

        LocalDateTime dateEncaissement = LocalDateTime.now();


        Payment payment = Payment.builder()
                .commande(commande)
                .numeroPaiement(n)
                .montant(request.getMontant())
                .datePaiement(LocalDateTime.now())
                .dateEncaissement(dateEncaissement)
                .paymentStatus(status)
                .methode(request.getMethode())
                .build();

        commandeRepository.save(commande);
        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }
}

