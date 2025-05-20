package com.movie.ticket.booking.system.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.dto.BookingDTO;
import com.movie.ticket.booking.system.enums.BookingStatus;
import com.movie.ticket.booking.system.utills.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceKafkaListener {

    private final ObjectMapper objectMapper;

    private final EmailNotificationService emailUtills;
    //
    @KafkaListener(topics ="payment-response", groupId = "payment-response-group-1")
    public void pullBookingDetailsFromPaymentResponse(String bookingsJson){
        log.info("Receiving the booking deatils {} from payment-response topic", bookingsJson);

        try{
            // sending email
            BookingDTO bookingDTO = objectMapper.readValue(bookingsJson, BookingDTO.class);
            String subject = "";
            String body = "<h1>Booking Id <h1/>"+ bookingDTO.getBookingId()
                    + "<h1>Booking Status <h1/>" + bookingDTO.getBookingStatus()
                    + "<h1>Selected Seats <h1/>" + bookingDTO.getSeatsSelected()
                    + "<h1>Show Date <h1/>" + bookingDTO.getShowDate()
                    + "<h1>Show Time <h1/>" + bookingDTO.getShowTime()
                    + "<h1>Ticket Amount <h1/>" + bookingDTO.getBookingAmount();
            String to = "sreerohithpeddi@gmail.com";
            if(bookingDTO.getBookingStatus().equals(BookingStatus.CONFIRMED)){
                subject = "Payment Successful for Movie ";
            }else{
                subject = "Payment Cancelled for Movie";
            }

            emailUtills.sendEmail(subject, body, to);

        }catch (Exception e){
            log.info("Error while receiving the Booking details {} from payment-response topic ",bookingsJson);
        }
    }

}
