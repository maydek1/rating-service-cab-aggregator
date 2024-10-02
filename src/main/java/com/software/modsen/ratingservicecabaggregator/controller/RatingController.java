package com.software.modsen.ratingservicecabaggregator.controller;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponseList;
import com.software.modsen.ratingservicecabaggregator.mapper.RatingMapper;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the rating",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Rating not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(ratingService.getRatingById(id)));
    }

    @Operation(summary = "Create a new rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponse.class))})
    })
    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingMapper.ratingToRatingResponse(ratingService.createRating(
                        ratingMapper.ratingRequestToRating(ratingRequest))));
    }

    @Operation(summary = "Update rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Rating not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long id, @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(
                ratingService.updateRating(id, ratingMapper.ratingRequestToRating(ratingRequest))));
    }

    @Operation(summary = "Delete rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Rating not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<RatingResponse> deleteRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.ratingToRatingResponse(ratingService.deleteRatingById(id)));
    }

    @Operation(summary = "Get all ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of ratings",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponseList.class))})
    })
    @GetMapping
    public ResponseEntity<RatingResponseList> getAllRatings() {
        return ResponseEntity.ok(new RatingResponseList(
                ratingService.getAllRatings().stream()
                        .map(ratingMapper::ratingToRatingResponse)
                        .collect(Collectors.toList())));
    }
}
