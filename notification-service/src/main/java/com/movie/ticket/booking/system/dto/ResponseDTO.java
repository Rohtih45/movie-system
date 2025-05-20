package com.movie.ticket.booking.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.movie.ticket.booking.system.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class   ResponseDTO {

    private List<String> errorMessage;

    private String errorDiscription;

    private String statusCodeDescription;

    private BookingDTO bookingDTO;
}
