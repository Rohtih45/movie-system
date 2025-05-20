package com.movie.ticket.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.dto.BookingDTO;
import com.movie.ticket.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceKafkaListener {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;
    //
    @KafkaListener(topics ="payment-response", groupId = "payment-response-group")
    public void pullBookingDetailsFromPaymentResponse(String bookingsJson){
        log.info("Receiving the booking deatils {} from payment-response topic", bookingsJson);

        try{
            this.bookingService.processFinalBooknig(objectMapper.readValue(bookingsJson, BookingDTO.class));
        }catch (Exception e){
            log.info("Error while receiving the Booking details {} from payment-response topic ",bookingsJson);
        }
    }

}
