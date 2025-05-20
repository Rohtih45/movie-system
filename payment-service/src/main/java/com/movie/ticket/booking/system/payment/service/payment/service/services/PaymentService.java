package com.movie.ticket.booking.system.payment.service.payment.service.services;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;

public interface PaymentService {

    public void makePayment(BookingDTO bookingDTO);
}
