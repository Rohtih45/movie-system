package com.movie.ticket.services.impl;

import com.movie.ticket.Repositories.BookingRepository;
import com.movie.ticket.brokers.PaymentServiceBroker;
import com.movie.ticket.entites.BookingEntity;
import com.movie.ticket.enums.BookingStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.ticket.dto.BookingDTO;
import com.movie.ticket.services.BookingService;
@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PaymentServiceBroker paymentServiceBroker;

	@Override
	@Transactional
	public BookingDTO createBooking(BookingDTO bookingDTO) {
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
		BookingDTO bookingDTO1 = new BookingDTO();
		BeanUtils.copyProperties(bookingEntity, bookingDTO1);
		log.info("Calliing payment gateway to do payment for amount {} with booking id {}"
				,bookingDTO1.getBookingAmount(),bookingDTO1.getBookingId());
		// Call Payment Service
		BookingDTO bookingDTOResponse = paymentServiceBroker.makePayment(bookingDTO1);
		//System.out.println(msg);
		log.info("Payment successfull with booking id {}",bookingDTO1.getBookingId());
		bookingEntity.setBookingStatus(bookingDTOResponse.getBookingStatus());
		//bookingDTO1.setBookingStatus(bookingDTOResponse.getBookingStatus());

		return bookingDTO1;

	}

}
