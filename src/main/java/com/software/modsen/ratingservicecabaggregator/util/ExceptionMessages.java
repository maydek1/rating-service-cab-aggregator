package com.software.modsen.ratingservicecabaggregator.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public static final String RIDE_NOT_FOUND = "Not found ride with id: '%s'";
    public static final String RATING_NOT_FOUND = "Not found rating with id: '%s'";
    public static final String SERVICE_UNAVAILABLE = "'%s' service unavailable";
    public static final String CIRCUIT_BREAKER_IS_OPEN = "Circuit breaker is open for %s service";
    public static final String RATING_ALREADY_EXIST = "Rating for ride with id: '%s' already exist";
    public static final String BAD_RATING_REQUEST = "Rating must be between 1 and 5. Your rating is %s";
}
