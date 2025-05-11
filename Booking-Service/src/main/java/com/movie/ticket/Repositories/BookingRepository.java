package com.movie.ticket.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.ticket.dto.BookingDTO;
import com.movie.ticket.entites.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID>{

}
