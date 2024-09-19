package com.software.modsen.ratingservicecabaggregator.controller;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponseList;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        RatingResponse ratingResponse = ratingService.getRatingById(id);
        return ResponseEntity.ok(ratingResponse);
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest ratingRequest) {
        RatingResponse ratingResponse = ratingService.createRating(ratingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long id,
                                                       @RequestBody RatingRequest ratingRequest) {
        RatingResponse ratingResponse = ratingService.updateRating(id, ratingRequest);
        return ResponseEntity.ok(ratingResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatingResponse> deleteRatingById(@PathVariable Long id) {
        RatingResponse ratingResponse = ratingService.deleteRatingById(id);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping
    public ResponseEntity<RatingResponseList> getAllRatings() {
        RatingResponseList ratingResponses = ratingService.getAllRatings();
        return ResponseEntity.ok(ratingResponses);
    }
}
