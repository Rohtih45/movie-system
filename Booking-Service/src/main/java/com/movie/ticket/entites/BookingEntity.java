package com.movie.ticket.entites;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.movie.ticket.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="booking")
public class BookingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID bookingId;
	@Column(name="user_id")
	private String userId;
	@Column(name="movie_id")
	private Integer movieId;
	@ElementCollection
	private List<String> seatsSelected;
	@Column(name="show_date")
	private LocalDate showDate;
	@Column(name="show_time")
	private LocalTime showTime;
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	@Column(name="booking_amount")
	private Double bookingAmount;

}
