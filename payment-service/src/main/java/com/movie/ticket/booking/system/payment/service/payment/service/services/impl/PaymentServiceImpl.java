package com.movie.ticket.booking.system.payment.service.payment.service.services.impl;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingStatus;
import com.movie.ticket.booking.system.payment.service.payment.service.entities.PaymentEntity;
import com.movie.ticket.booking.system.payment.service.payment.service.entities.PaymentStatus;
import com.movie.ticket.booking.system.payment.service.payment.service.kafka.publisher.PaymentServiceKakfaPublisher;
import com.movie.ticket.booking.system.payment.service.payment.service.repositories.PaymentRepository;
import com.movie.ticket.booking.system.payment.service.payment.service.services.PaymentService;
import com.movie.ticket.booking.system.payment.service.payment.service.services.RazorApiPaymentGateway;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RazorApiPaymentGateway razorApiPaymentGateway;

    @Autowired
    private PaymentServiceKakfaPublisher paymentServiceKakfaPublisher;

    @Override
    @Transactional(rollbackOn = RazorpayException.class)
    public void makePayment(BookingDTO bookingDTO) {

        log.info("Entered into PaymentServiceImpl with payment amount {} for the booking"+ "id {}",bookingDTO.getBookingAmount()
                ,bookingDTO.getBookingId());

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .bookingId(bookingDTO.getBookingId())
                .paymentAmount(bookingDTO.getBookingAmount()).build();

        paymentRepository.save(paymentEntity);
        bookingDTO = razorApiPaymentGateway.razorpayClient(bookingDTO);
        paymentEntity.setPaymentDateTime(LocalDateTime.now());

        if(bookingDTO.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            paymentEntity.setPaymentStatus(PaymentStatus.APPROVED);

        }else {
            paymentEntity.setPaymentStatus(PaymentStatus.FAILED);

        }

        paymentServiceKakfaPublisher.pushBookingDetailsToPaymentResponseTopic(bookingDTO);

//        return bookingDTO1;
    }
}
