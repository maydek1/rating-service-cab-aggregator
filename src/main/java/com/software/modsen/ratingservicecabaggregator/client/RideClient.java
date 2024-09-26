package com.software.modsen.ratingservicecabaggregator.client;



import com.software.modsen.ratingservicecabaggregator.config.FeignConfig;
import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.DriverResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${ride-service.name}", url = "${ride-service.url}", configuration = FeignConfig.class)
public interface RideClient {

    @GetMapping("/{id}")
    ResponseEntity<RideResponse> getRide(@PathVariable Long id);

    @PostMapping("/driver")
    ResponseEntity<DriverResponse> updateRating(@RequestBody DriverRatingRequest driverRatingRequest);
}
