package com.software.modsen.ratingservicecabaggregator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
@ActiveProfiles("test")
public class RatingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        ratingRepository.deleteAll();
    }

    @Test
    public void testGetRatingById() throws Exception {
        Rating rating = new Rating();
        rating.setRate(5);
        rating.setRideId(1L);
        ratingRepository.save(rating);

        mockMvc.perform(get("/api/v1/ratings/{id}", rating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.rideId").value(1));
    }

    @Test
    public void testCreateRating() throws Exception {
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setRate(4);
        ratingRequest.setRideId(2L);

        mockMvc.perform(post("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rate").value(4))
                .andExpect(jsonPath("$.rideId").value(2));
    }

    @Test
    public void testUpdateRating() throws Exception {
        Rating rating = new Rating();
        rating.setRate(5);
        rating.setRideId(1L);
        ratingRepository.save(rating);

        RatingRequest updateRequest = new RatingRequest();
        updateRequest.setRate(3);
        updateRequest.setRideId(1L);

        mockMvc.perform(put("/api/v1/ratings/{id}", rating.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(3));
    }

    @Test
    public void testDeleteRating() throws Exception {
        Rating rating = new Rating();
        rating.setRate(5);
        rating.setRideId(1L);
        ratingRepository.save(rating);

        mockMvc.perform(delete("/api/v1/ratings/{id}", rating.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/ratings/{id}", rating.getId()))
                .andExpect(status().isNotFound());
    }
}
