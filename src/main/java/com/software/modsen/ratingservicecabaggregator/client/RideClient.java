package com.software.modsen.ratingservicecabaggregator.client;

import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.DriverResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.exception.RideNotFoundException;
import com.software.modsen.ratingservicecabaggregator.exception.ServiceUnavailableException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.software.modsen.ratingservicecabaggregator.util.ExceptionMessages.*;

@FeignClient(name = "${ride-service.name}", path = "${ride-service.path}")
public interface RideClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "rideService", fallbackMethod = "handleGetRideFallback")
    ResponseEntity<RideResponse> getRide(@PathVariable Long id);

    @PostMapping("/driver")
    @CircuitBreaker(name = "rideService", fallbackMethod = "handleUpdateRatingFallback")
    ResponseEntity<DriverResponse> updateRating(@RequestBody DriverRatingRequest driverRatingRequest);

    default ResponseEntity<RideResponse> handleGetRideFallback(Long id, Throwable throwable) {
        if (throwable instanceof FeignException && ((FeignException) throwable).status() == 404) {
            throw new RideNotFoundException(String.format(RIDE_NOT_FOUND, id));
        }
        if (throwable instanceof CallNotPermittedException) {
            throw new ServiceUnavailableException(String.format(CIRCUIT_BREAKER_IS_OPEN, "ride"));
        }
        throw new ServiceUnavailableException(String.format(SERVICE_UNAVAILABLE, "Ride"));
    }

    default ResponseEntity<DriverResponse> handleUpdateRatingFallback(DriverRatingRequest request, Throwable throwable) {
        if (throwable instanceof FeignException && ((FeignException) throwable).status() == 404) {
            throw new RideNotFoundException(String.format(RIDE_NOT_FOUND, request.getRideId()));
        }

        if (throwable instanceof CallNotPermittedException) {
            throw new ServiceUnavailableException(String.format(CIRCUIT_BREAKER_IS_OPEN, "ride"));
        }

        throw new ServiceUnavailableException(String.format(SERVICE_UNAVAILABLE, "Ride"));
    }
}