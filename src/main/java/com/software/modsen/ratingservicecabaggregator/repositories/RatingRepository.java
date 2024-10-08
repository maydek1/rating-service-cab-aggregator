package com.software.modsen.ratingservicecabaggregator.repositories;

import com.software.modsen.ratingservicecabaggregator.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByRideId(Long rideID);
}
