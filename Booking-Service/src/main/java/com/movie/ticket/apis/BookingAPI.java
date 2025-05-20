package com.movie.ticket.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movie.ticket.exception.BookingException;
import com.movie.ticket.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movie.ticket.dto.BookingDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.parser.Entity;
import java.util.UUID;

@RestController
@RequestMapping("bookings")
@Slf4j
public class BookingAPI {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private Environment environment;
	
	@PostMapping
	public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) throws JsonProcessingException {
		
		log.info("Requested  Booking Details Entered: {} ",bookingDTO.toString());
		BookingDTO bookingDTO1= bookingService.createBooking(bookingDTO);
		return new ResponseEntity<BookingDTO>(bookingDTO1, HttpStatus.CREATED);
	}

//	@GetMapping("/tracking")
//	public String getMsg(){
//		System.out.println(environment.getProperty("SERVER.PORT")+"test");
//		return "Booking API called";
//	}

	@GetMapping("/tracking/{bookingid}")
	public void getBookingDetails(@PathVariable UUID booking_id) throws BookingException {
		bookingService.getBookingDetails(booking_id);

	}

}
