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
import org.aspectj.weaver.patterns.IfPointcut;
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

        int n = paymentRepository.countByCommandeId(commande.getId());
//        List<Payment> paymentsExistants = paymentRepository.findPaymentsByCommandeAndMethode(commande, PaymentMethod.ESPESE);
//
//        BigDecimal totalPaiementEspece = request.getMontant();

//        for(Payment p : paymentsExistants) {
//            totalPaiementEspece = totalPaiementEspece.add(p.getMontant());
//        }
        if (request.getMontant().compareTo(BigDecimal.valueOf(20000))>0 && request.getMethode() == PaymentMethod.ESPESE) {
            throw new RuntimeException("Le montant total des paiements en espèces ne peut pas dépasser 20 000");
        }

        PaymentStatus status ;

        if (request.getMethode() == PaymentMethod.ESPESE) {

            commande.setMontantRestant(commande.getMontantRestant().subtract(request.getMontant()));

            status = PaymentStatus.PAID;

            commandeRepository.save(commande);
        }else {
            status = PaymentStatus.PENDING;
        }

        LocalDateTime dateEncaissement = LocalDateTime.now();

        Payment payment = Payment.builder()
                .commande(commande)
                .numeroPaiement(n + 1)
                .montant(request.getMontant())
                .datePaiement(LocalDateTime.now())
                .dateEncaissement(dateEncaissement)
                .paymentStatus(status)
                .methode(request.getMethode())
                .build();

        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }

    public PaymentResponse comfirmPayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Commande commande = commandeRepository.findById(payment.getCommande().getId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
            System.out.println("commande.getMontantRestant() = " + commande.getMontantRestant());
            System.out.println("payment.getMontant() = " + payment.getMontant());

        if (payment.getPaymentStatus() == PaymentStatus.PENDING) {

            commande.setMontantRestant(commande.getMontantRestant().subtract(payment.getMontant()));

            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setDateEncaissement(LocalDateTime.now());

            commandeRepository.save(commande);
            paymentRepository.save(payment);
        }else {
            throw new RuntimeException("Le paiement a déjà été confirmé");
        }

        return paymentMapper.toResponse(payment);
    }
}

