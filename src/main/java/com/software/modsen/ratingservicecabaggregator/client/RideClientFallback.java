package com.software.modsen.ratingservicecabaggregator.client;

import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.DriverResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RideClientFallback implements RideClient {

    @Override
    public ResponseEntity<RideResponse> getRide(Long id) {
        RideResponse fallbackResponse = new RideResponse();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackResponse);
    }

    @Override
    public ResponseEntity<DriverResponse> updateRating(DriverRatingRequest driverRatingRequest) {
        DriverResponse fallbackResponse = new DriverResponse();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackResponse);
    }
}