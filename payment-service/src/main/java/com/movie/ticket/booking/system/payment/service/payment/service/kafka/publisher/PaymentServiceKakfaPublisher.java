package com.movie.ticket.booking.system.payment.service.payment.service.kafka.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceKakfaPublisher {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void pushBookingDetailsToPaymentResponseTopic(BookingDTO bookingDTO){
      log.info("Publishing the Booking Details  {} to the payment response topic", bookingDTO);

      try{
          this.kafkaTemplate.send("payment-response",objectMapper.writeValueAsString(bookingDTO));
      }catch (Exception e){
          log.info("Error while publising the Booking Details {} to the payment-response topic", bookingDTO);
      }
    }
}
