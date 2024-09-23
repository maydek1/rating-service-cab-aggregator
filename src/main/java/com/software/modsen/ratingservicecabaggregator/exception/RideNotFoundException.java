package com.software.modsen.ratingservicecabaggregator.exception;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String msg){
        super(msg);
    }
}