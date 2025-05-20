package com.movie.ticket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movie.ticket.dto.BookingDTO;
import com.movie.ticket.exception.BookingException;

import java.util.UUID;


public interface BookingService {
	
	public BookingDTO createBooking(BookingDTO bookingDTO) throws JsonProcessingException;

	public BookingDTO getBookingDetails(UUID bookingId) throws BookingException;

	public void processFinalBooknig(BookingDTO bookingDTO) throws BookingException;

}
