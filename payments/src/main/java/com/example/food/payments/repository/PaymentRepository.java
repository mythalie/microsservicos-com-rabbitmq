package com.example.food.payments.repository;

import com.example.food.payments.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
