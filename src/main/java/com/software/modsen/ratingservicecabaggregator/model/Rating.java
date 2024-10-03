package com.software.modsen.ratingservicecabaggregator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rating{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rideId;
    private String comment;
    private int rate;

    public Rating(int rate, Long rideId) {
        this.rate = rate;
        this.rideId = rideId;
    }
}
