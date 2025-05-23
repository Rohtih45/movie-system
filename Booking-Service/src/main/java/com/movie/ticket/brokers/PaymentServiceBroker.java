package com.movie.ticket.brokers;

import com.movie.ticket.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-servie")
public interface PaymentServiceBroker {

    @PostMapping
    public BookingDTO makePayment(@RequestBody BookingDTO bookingDTO);
}
//@FeignClient(name = "payment-servie", url = "http://payment-service:9091/payments")