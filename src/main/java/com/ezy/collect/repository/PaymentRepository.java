package com.ezy.collect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezy.collect.domain.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

