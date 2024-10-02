package com.software.modsen.ratingservicecabaggregator.service;

import com.software.modsen.ratingservicecabaggregator.client.RideClient;
import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.exception.RatingNotFoundException;
import com.software.modsen.ratingservicecabaggregator.exception.RideNotFoundException;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RideClient rideClient;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setId(1L);
        rating.setRideId(1L);
        rating.setRate(5);
    }

    @Test
    void getRatingById_shouldReturnRating_whenRatingExists() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        Rating foundRating = ratingService.getRatingById(1L);

        assertEquals(rating.getId(), foundRating.getId());
    }

    @Test
    void getRatingById_shouldThrowRatingNotFoundException_whenRatingDoesNotExist() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingById(1L));
    }

    @Test
    void deleteRatingById_shouldDeleteRating_whenRatingExists() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        Rating deletedRating = ratingService.deleteRatingById(1L);

        assertEquals(rating.getId(), deletedRating.getId());
        verify(ratingRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRatingById_shouldThrowRatingNotFoundException_whenRatingDoesNotExist() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.deleteRatingById(1L));
    }

    @Test
    void updateRating_shouldReturnUpdatedRating_whenRatingExists() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        Rating updatedRating = new Rating();
        updatedRating.setId(1L);
        updatedRating.setRate(4);
        when(ratingRepository.save(any())).thenReturn(updatedRating);

        Rating result = ratingService.updateRating(1L, updatedRating);
        assertEquals(updatedRating.getRate(), result.getRate());

        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void updateRating_shouldThrowRatingNotFoundException_whenRatingDoesNotExist() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.updateRating(1L, rating));
    }

    @Test
    void createRating_shouldReturnSavedRating_whenRideExists() {
        RideResponse rideResponse = new RideResponse();
        when(rideClient.getRide(1L)).thenReturn(ResponseEntity.ok(rideResponse));
        when(ratingRepository.save(any())).thenReturn(rating);

        Rating createdRating = ratingService.createRating(rating);

        assertEquals(rating.getId(), createdRating.getId());
        verify(rideClient, times(1)).updateRating(any(DriverRatingRequest.class));
    }

    @Test
    void createRating_shouldThrowRideNotFoundException_whenRideDoesNotExist() {
        when(rideClient.getRide(1L)).thenReturn(null);
        assertThrows(RideNotFoundException.class, () -> ratingService.createRating(rating));
    }

    @Test
    void getAllRatings_shouldReturnRatings() {
        when(ratingRepository.findAll(any(Sort.class))).thenReturn(List.of(rating));

        List<Rating> foundRatings = ratingService.getAllRatings();

        assertEquals(1, foundRatings.size());
        assertEquals(rating.getId(), foundRatings.get(0).getId());
    }
}
