package com.movie.ticket.booking.system.payment.service.payment.service.services;

import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.payment.service.dtos.BookingStatus;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RazorApiPaymentGateway {

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    public BookingDTO razorpayClient(BookingDTO bookingDTO)  {

        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
            JSONObject orderReq = new JSONObject();
            //Map<String, Object> chargeParam = new HashMap<>(); -- For Stripe Payment Gateway
            orderReq.put("amount", (int)Math.round(bookingDTO.getBookingAmount())*100);

            orderReq.put("currency", "INR");
            orderReq.put("receipt", "txn_123456");

            Order order = razorpayClient.orders.create(orderReq);

            bookingDTO.setBookingStatus(BookingStatus.CONFIRMED);
            return bookingDTO;

        }catch (RazorpayException e){
            bookingDTO.setBookingStatus(BookingStatus.CANCELLED);
            return bookingDTO;

        }


    }



}
