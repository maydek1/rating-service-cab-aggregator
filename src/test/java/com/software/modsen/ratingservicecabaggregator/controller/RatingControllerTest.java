package com.software.modsen.ratingservicecabaggregator.controller;

import com.software.modsen.ratingservicecabaggregator.dto.request.RatingRequest;
import com.software.modsen.ratingservicecabaggregator.dto.response.RatingResponse;
import com.software.modsen.ratingservicecabaggregator.mapper.RatingMapper;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private RatingMapper ratingMapper;

    @Test
    public void createRating_shouldReturnCreatedRating() throws Exception {
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setRate(5);

        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRate(5);

        when(ratingMapper.ratingRequestToRating(any())).thenReturn(new Rating());
        when(ratingMapper.ratingToRatingResponse(any())).thenReturn(ratingResponse);
        when(ratingService.createRating(any())).thenReturn(new Rating());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rate\": 5}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"rate\": 5}"))
                .andDo(print());
    }

    @Test
    public void getRatingById_shouldReturnRating() throws Exception {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRate(5);

        when(ratingMapper.ratingToRatingResponse(any())).thenReturn(ratingResponse);
        when(ratingService.getRatingById(1L)).thenReturn(new Rating());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/ratings/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"rate\": 5}"))
                .andDo(print());
    }

    @Test
    public void deleteRatingById_shouldReturnRating() throws Exception {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRate(5);

        when(ratingMapper.ratingToRatingResponse(any())).thenReturn(ratingResponse);
        when(ratingService.deleteRatingById(1L)).thenReturn(new Rating());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/ratings/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"rate\": 5}"))
                .andDo(print());
    }

    @Test
    public void updateRating_shouldReturnRating() throws Exception {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRate(5);

        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setRate(5);

        when(ratingMapper.ratingToRatingResponse(any())).thenReturn(ratingResponse);
        when(ratingService.updateRating(anyLong(), any())).thenReturn(new Rating());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/ratings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rate\": 5}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"rate\": 5}"))
                .andDo(print());
    }

    @Test
    public void getAllRatings_shouldReturnRatingList() throws Exception {
        List<Rating> ratingList = new ArrayList<>();
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRate(5);
        ratingList.add(rating);

        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRate(5);

        when(ratingService.getAllRatings()).thenReturn(ratingList);
        when(ratingMapper.ratingToRatingResponse(any())).thenReturn(ratingResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/ratings")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"ratings\":[{\"rate\": 5}]}"))
                .andDo(print());
    }
}
