package com.movie.ticket.booking.system.payment.service.payment.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.payment.service.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceKakfaListener {

    private final PaymentService paymentService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics ="payment-service", groupId = "payment-service-group")
    public void recieveDataFromPaymentRequestTopic(String bookingDetailsJson){

        log.info("Received Booking details {} from payment request  topic",bookingDetailsJson);

        try{
            this.paymentService.makePayment(objectMapper.readValue(bookingDetailsJson, BookingDTO.class));
        }catch(Exception e){
            log.info("Error while recieving the Booking details {} from payment request topic", bookingDetailsJson);
        }

    }
}
