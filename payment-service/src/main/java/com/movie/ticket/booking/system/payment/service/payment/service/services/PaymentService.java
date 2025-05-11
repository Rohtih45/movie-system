package com.movie.ticket.booking.system.payment.service.payment.service.services;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;

public interface PaymentService {

    public BookingDTO makePayment(BookingDTO bookingDTO);
}
