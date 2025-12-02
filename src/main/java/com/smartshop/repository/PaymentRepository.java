package com.smartshop.repository;

import com.smartshop.entity.Commande;
import com.smartshop.entity.Payment;
import com.smartshop.entity.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findById(long id);
    int countByCommandeId(Long commandeId);
    List<Payment> findPaymentsByCommandeAndMethode(Commande commande, PaymentMethod methode);
}

