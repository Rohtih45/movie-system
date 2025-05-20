package com.movie.ticket.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.Repositories.BookingRepository;
import com.movie.ticket.brokers.PaymentServiceBroker;
import com.movie.ticket.entites.BookingEntity;
import com.movie.ticket.enums.BookingStatus;
import com.movie.ticket.exception.BookingException;
import com.movie.ticket.kafka.publisher.BookingServiceKafkaPublisher;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.ticket.dto.BookingDTO;
import com.movie.ticket.services.BookingService;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PaymentServiceBroker paymentServiceBroker;

	@Autowired
	private BookingServiceKafkaPublisher bookingServiceKafkaPublisher;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@Transactional
	public BookingDTO createBooking(BookingDTO bookingDTO) throws JsonProcessingException {
		log.info("Requested Booking details Entered: {}",bookingDTO);
		// TODO Auto-generated method stub
		BookingEntity bookingEntity =   BookingEntity.builder()
				.userId(bookingDTO.getUserId())
				.movieId(bookingDTO.getMovieId())
				.seatsSelected(bookingDTO.getSeatsSelected())
				.showDate(bookingDTO.getShowDate())
				.showTime(bookingDTO.getShowTime())
				.bookingStatus(BookingStatus.PENDING)
				.bookingAmount(bookingDTO.getBookingAmount())
				.build();

		bookingRepository.save(bookingEntity);

		bookingDTO.setBookingId(bookingEntity.getBookingId());
		bookingDTO.setBookingStatus(BookingStatus.PENDING);

		bookingServiceKafkaPublisher.pushBookingDetailsToPaymentRequestTopics(bookingDTO);

		return bookingDTO;

	}

	@Override
	public BookingDTO getBookingDetails(UUID bookingId) throws  BookingException{
		BookingEntity bookingEntity = bookingRepository.findById(bookingId).orElseThrow(()-> new BookingException("Booking Details not Found" + bookingId));


		return BookingDTO.builder()
				.bookingId(bookingEntity.getBookingId())
				.bookingAmount(bookingEntity.getBookingAmount())
				.bookingStatus(bookingEntity.getBookingStatus())
				.userId(bookingEntity.getUserId())
				.movieId(bookingEntity.getMovieId())
				.showDate(bookingEntity.getShowDate())
				.showTime(bookingEntity.getShowTime())
				.seatsSelected(bookingEntity.getSeatsSelected())
				.build();
	}

	@Override
	@Transactional
	public void processFinalBooknig(BookingDTO bookingDTO) throws BookingException {

		BookingEntity bookingEntity = this.bookingRepository.findById(bookingDTO.getBookingId())
				.orElseThrow(() -> new BookingException("No Booking deatils found with Id: "+ bookingDTO.getBookingId()));

		bookingEntity.setBookingStatus(bookingDTO.getBookingStatus());
	}

}
