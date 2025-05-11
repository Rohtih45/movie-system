package com.movie.ticket.booking.system.payment.service.payment.service.apis;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.payment.service.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public BookingDTO makePayment(@RequestBody BookingDTO bookingDTO){

        return paymentService.makePayment(bookingDTO);
    }
}
