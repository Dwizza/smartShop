package com.smartshop.repository;

import com.smartshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCommandeIdOrderByNumeroPaiementAsc(Long commandeId);

    int countByCommandeId(Long commandeId);
}

