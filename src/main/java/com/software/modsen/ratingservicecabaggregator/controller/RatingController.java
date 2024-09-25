package com.software.modsen.ratingservicecabaggregator.controller;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponseList;
import com.software.modsen.ratingservicecabaggregator.mapper.RatingMapper;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(ratingService.getRatingById(id)));
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingMapper.ratingToRatingResponse(ratingService.createRating(
                        ratingMapper.ratingRequestToRating(ratingRequest))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long id, @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(
                ratingService.updateRating(id, ratingMapper.ratingRequestToRating(ratingRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatingResponse> deleteRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(ratingService.deleteRatingById(id)));
    }

    @GetMapping
    public ResponseEntity<RatingResponseList> getAllRatings() {
        return ResponseEntity.ok(new RatingResponseList(
                ratingService.getAllRatings().stream()
                        .map(ratingMapper::ratingToRatingResponse)
                        .collect(Collectors.toList())));
    }
}
