package com.software.modsen.ratingservicecabaggregator.service;


import com.software.modsen.ratingservicecabaggregator.client.RideClient;
import com.software.modsen.ratingservicecabaggregator.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservicecabaggregator.exception.RatingNotFoundException;
import com.software.modsen.ratingservicecabaggregator.exception.RideNotFoundException;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.software.modsen.ratingservicecabaggregator.util.ExceptionMessages.RATING_NOT_FOUND;
import static com.software.modsen.ratingservicecabaggregator.util.ExceptionMessages.RIDE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RideClient rideClient;

    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(RATING_NOT_FOUND, id)));
    }

    public Rating deleteRatingById(Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(RATING_NOT_FOUND, id)));
        ratingRepository.deleteById(id);
        return rating;
    }

    public Rating updateRating(Long id, Rating rating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(RATING_NOT_FOUND, id)));

        rating.setId(existingRating.getId());
        return ratingRepository.save(rating);
    }

    public Rating createRating(Rating rating) {
        if (rideClient.getRide(rating.getRideId()) == null)
            throw new RideNotFoundException(String.format(RIDE_NOT_FOUND, rating.getRideId()));
        rideClient.updateRating(new DriverRatingRequest(rating.getRideId(), rating.getRate(), null));
        return ratingRepository.save(rating);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll(Sort.by(Sort.Direction.ASC, "rate"));
    }
}

