package com.software.modsen.ratingservicecabaggregator.exception;

public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException(String msg){
        super(msg);
    }
}
