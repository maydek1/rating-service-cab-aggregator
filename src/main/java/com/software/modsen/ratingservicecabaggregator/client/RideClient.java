package com.software.modsen.ratingservicecabaggregator.client;

import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.DriverResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${ride-service.name}", path = "${ride-service.path}")
public interface RideClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "rideService", fallbackMethod = "handleGetRideFallback")
    ResponseEntity<RideResponse> getRide(@PathVariable Long id);

    @PostMapping("/driver")
    @CircuitBreaker(name = "driverService", fallbackMethod = "handleUpdateRatingFallback")
    ResponseEntity<DriverResponse> updateRating(@RequestBody DriverRatingRequest driverRatingRequest);

    default ResponseEntity<RideResponse> handleGetRideFallback(Long id, Throwable throwable) {
        throw new ServiceUnavailableException("Сервис поездок недоступен");
    }

    default ResponseEntity<DriverResponse> handleUpdateRatingFallback(DriverRatingRequest request, Throwable throwable) {
        throw new ServiceUnavailableException("Сервис водителей недоступен");
    }
}