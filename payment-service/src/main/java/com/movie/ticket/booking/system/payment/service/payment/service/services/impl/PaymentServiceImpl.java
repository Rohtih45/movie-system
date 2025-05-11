package com.movie.ticket.booking.system.payment.service.payment.service.services.impl;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingStatus;
import com.movie.ticket.booking.system.payment.service.payment.service.entities.PaymentEntity;
import com.movie.ticket.booking.system.payment.service.payment.service.entities.PaymentStatus;
import com.movie.ticket.booking.system.payment.service.payment.service.repositories.PaymentRepository;
import com.movie.ticket.booking.system.payment.service.payment.service.services.PaymentService;
import com.movie.ticket.booking.system.payment.service.payment.service.services.RazorApiPaymentGateway;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RazorApiPaymentGateway razorApiPaymentGateway;

    @Override
    @Transactional(rollbackOn = RazorpayException.class)
    public BookingDTO makePayment(BookingDTO bookingDTO) {
        PaymentEntity paymentEntity = PaymentEntity.builder().paymentStatus(PaymentStatus.PENDING)
                .bookingId(bookingDTO.getBookingId())
                .paymentAmount(bookingDTO.getBookingAmount()).build();

        paymentRepository.save(paymentEntity);
        BookingDTO bookingDTO1 = razorApiPaymentGateway.razorpayClient(bookingDTO);

        if(bookingDTO1.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            paymentEntity.setPaymentStatus(PaymentStatus.APPROVED);
            paymentEntity.setPaymentDateTime(LocalDateTime.now());
        }else {
            paymentEntity.setPaymentStatus(PaymentStatus.FAILED);
            paymentEntity.setPaymentDateTime(LocalDateTime.now());
        }

        return bookingDTO1;
    }
}
