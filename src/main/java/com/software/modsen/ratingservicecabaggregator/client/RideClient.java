package com.software.modsen.ratingservicecabaggregator.client;

import com.software.modsen.ratingservicecabaggregator.config.FeignConfig;
import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.DriverResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${ride-service.name}", path = "${ride-service.path}", configuration = FeignConfig.class)
public interface RideClient {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "rideService")
    ResponseEntity<RideResponse> getRide(@PathVariable Long id);

    @PostMapping("/driver")
    @CircuitBreaker(name = "rideService")
    ResponseEntity<DriverResponse> updateRating(@RequestBody DriverRatingRequest driverRatingRequest);
}
