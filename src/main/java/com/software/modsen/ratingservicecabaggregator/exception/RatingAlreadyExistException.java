package com.software.modsen.ratingservicecabaggregator.exception;

public class RatingAlreadyExistException extends RuntimeException {
    public RatingAlreadyExistException(String msg){
        super(msg);
    }
}
