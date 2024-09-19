package com.software.modsen.ratingservicecabaggregator.service.ServiceImpl;


import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponseList;
import com.software.modsen.ratingservicecabaggregator.exception.RatingNotFoundException;
import com.software.modsen.ratingservicecabaggregator.mapper.RatingMapper;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

import static com.software.modsen.ratingservicecabaggregator.util.ExceptionMessages.RATING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    @Override
    public RatingResponse getRatingById(Long id) {
        return getOrElseThrow(id);
    }

    @Override
    public RatingResponse deleteRatingById(Long id) {
        RatingResponse ratingResponse = getOrElseThrow(id);
        ratingRepository.deleteById(id);
        return ratingResponse;
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest ratingRequest) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(RATING_NOT_FOUND, id)));

        Rating updatedRating = ratingMapper.ratingRequestToRating(ratingRequest);
        updatedRating.setId(rating.getId());
        return ratingMapper.ratingToRatingResponse(ratingRepository.save(updatedRating));
    }

    @Override
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Rating rating = ratingMapper.ratingRequestToRating(ratingRequest);
        ratingRepository.save(rating);
        return ratingMapper.ratingToRatingResponse(rating);
    }

    @Override
    public RatingResponseList getAllRatings() {
        return new RatingResponseList(ratingRepository.findAll()
                .stream()
                .map(ratingMapper::ratingToRatingResponse)
                .sorted(Comparator.comparingInt(RatingResponse::getRate))
                .collect(Collectors.toList()));
    }

    private RatingResponse getOrElseThrow(Long id) {
        return ratingMapper.ratingToRatingResponse(ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(RATING_NOT_FOUND, id))));
    }
}