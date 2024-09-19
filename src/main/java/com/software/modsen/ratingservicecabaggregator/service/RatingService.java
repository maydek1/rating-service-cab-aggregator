package com.software.modsen.ratingservicecabaggregator.service;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponseList;

public interface RatingService {
    RatingResponse getRatingById(Long id);
    RatingResponse deleteRatingById(Long id);
    RatingResponse updateRating(Long id, RatingRequest ratingRequest);
    RatingResponse createRating(RatingRequest ratingRequest);
    RatingResponseList getAllRatings();
}
