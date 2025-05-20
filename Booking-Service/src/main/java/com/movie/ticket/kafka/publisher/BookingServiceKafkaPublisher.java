package com.movie.ticket.kafka.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceKafkaPublisher {
    // write code to publish the kafka topic

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void pushBookingDetailsToPaymentRequestTopics(BookingDTO bookingDTO){
        log.info("Publishing the booking deatils {} to payment-service topic",bookingDTO);

        try{
            kafkaTemplate.send("payment-service",objectMapper.writeValueAsString(bookingDTO));
        }catch (Exception e){
            log.info("Error while publishing the data to payment-service topic");
        }
    }
}
