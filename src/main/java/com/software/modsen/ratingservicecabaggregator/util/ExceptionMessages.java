package com.software.modsen.ratingservicecabaggregator.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public static final String RIDE_NOT_FOUND = "Not found ride with id: '%s'";
    public static final String RATING_NOT_FOUND = "Not found rating with id: '%s'";
    public static final String SERVICE_UNAVAILABLE = "'%s' service unavailable";
    public static final String CIRCUIT_BREAKER_IS_OPEN = "Circuit breaker is open for %s service";
}
