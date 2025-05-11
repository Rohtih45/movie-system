package com.movie.ticket.apis;

import com.movie.ticket.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.ticket.dto.BookingDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("bookings")
@Slf4j
public class BookingAPI {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
		
		log.info("Requested  Booking Details Entered: {} ",bookingDTO.toString());
		BookingDTO bookingDTO1= bookingService.createBooking(bookingDTO);
		return new ResponseEntity<BookingDTO>(bookingDTO1, HttpStatus.CREATED);
	}

}
