package com.movie.ticket.booking.system.payment.service.payment.service.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class BookingDTO {
	private UUID bookingId;
	@NotBlank(message = "User ID is Mandatory Not Blank")
	@NotNull(message = "User ID is Mandatory")
	private String userId;
	@NotNull(message = "Please provide a movie id")

	private Integer movieId;
	@NotNull(message = "You need to select at least one seat to create a booking")
	private List<String> seatsSelected;
	@NotNull(message = "Select the Show Date")
	private LocalDate showDate;
	@NotNull(message = "Select the Show Time")
	private LocalTime showTime;
	
	private BookingStatus bookingStatus;
	@NotNull(message = "Please provide Booking Amount")
	@Positive(message = "Please provide Booking Amount")
	private Double bookingAmount;

}
