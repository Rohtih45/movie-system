package com.movie.ticket.handler;

import com.movie.ticket.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class BookingAPIHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseDTO> methodArgNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

		//log.info("Entered into BooingAPIHandler class with the exception"+methodArgumentNotValidException.getMessage());
		//methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage()

		List<ObjectError> errors = methodArgumentNotValidException.getBindingResult().getAllErrors();
		List<String> errMsgss = new ArrayList<>();
		for(ObjectError error:errors){
			errMsgss.add(error.getDefaultMessage());
		}

		//Lambda Expression
		//List<String> errMsgs = errors.stream()
		// .map((objectErr) -> objectErr.getDefaultMessage())
		// .collect(Collectors.toList());
		return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
				.errorDiscription(HttpStatus.BAD_REQUEST.getReasonPhrase()) // errorDiscription
				.errorMessage(errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()))
				.build(),HttpStatus.BAD_REQUEST);


	}


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseDTO> runtimeexception(RuntimeException runtimeException){
		return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
				.statusCodeDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.errorDiscription(runtimeException.getMessage()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}




}
