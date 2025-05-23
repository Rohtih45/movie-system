package com.movie.ticket.booking.system.payment.service.payment.service.repositories;

import com.movie.ticket.booking.system.payment.service.payment.service.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
