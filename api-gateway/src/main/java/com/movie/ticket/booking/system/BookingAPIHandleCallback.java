package com.movie.ticket.booking.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingAPIHandleCallback {

    @GetMapping("/booking-fallback")
    public String fallBack(){
        return "Booking Service is not available try after sometime.....";
    }
}
