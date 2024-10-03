package com.software.modsen.ratingservicecabaggregator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservicecabaggregator.RatingServiceCabAggregatorApplication;
import com.software.modsen.ratingservicecabaggregator.client.RideClient;
import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RatingServiceCabAggregatorApplication.class)
@AutoConfigureMockMvc
public class RatingIntegrationTest extends DataBaseContainerConfiguration{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideClient rideClient;

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
        RideResponse rideResponse = new RideResponse();
        rideResponse.setId(2L);

        when(rideClient.getRide(any())).thenReturn(ResponseEntity.ok(rideResponse));

        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setRate(4);
        ratingRequest.setRideId(2L);

        mockMvc.perform(post("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rate").value(4))
                .andExpect(jsonPath("$.rideId").value(2));

        verify(rideClient, times(1)).getRide(any());
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

    @Test
    public void testGetAllRatings() throws Exception {
        Rating rating1 = new Rating();
        rating1.setRate(5);
        rating1.setRideId(1L);
        ratingRepository.save(rating1);

        Rating rating2 = new Rating();
        rating2.setRate(4);
        rating2.setRideId(2L);
        ratingRepository.save(rating2);

        mockMvc.perform(get("/api/v1/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratings").isArray())
                .andExpect(jsonPath("$.ratings.length()").value(2))
                .andExpect(jsonPath("$.ratings[0].rate").value(4))
                .andExpect(jsonPath("$.ratings[1].rate").value(5));
    }
}
