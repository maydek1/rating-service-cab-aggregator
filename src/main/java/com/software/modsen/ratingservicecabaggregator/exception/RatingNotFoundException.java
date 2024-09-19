package com.software.modsen.ratingservicecabaggregator.exception;

public class RatingNotFoundException extends RuntimeException{
    public RatingNotFoundException(String msg){
        super(msg);
    }
}
