package com.TicketBooking.demo.common;

public class HoldExpiredException extends RuntimeException{
    public HoldExpiredException(String message){
        super(message);
    }
}
