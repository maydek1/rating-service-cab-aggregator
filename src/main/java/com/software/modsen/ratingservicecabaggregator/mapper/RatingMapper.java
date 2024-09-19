package com.software.modsen.ratingservicecabaggregator.mapper;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingResponse ratingToRatingResponse(Rating rating);

    Rating ratingRequestToRating(RatingRequest request);

}
