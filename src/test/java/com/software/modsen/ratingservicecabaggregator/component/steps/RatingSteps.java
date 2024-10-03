package com.software.modsen.ratingservicecabaggregator.component.steps;

import com.software.modsen.ratingservicecabaggregator.client.RideClient;
import com.software.modsen.ratingservicecabaggregator.dto.response.RideResponse;
import com.software.modsen.ratingservicecabaggregator.exception.RideNotFoundException;
import com.software.modsen.ratingservicecabaggregator.model.Rating;
import com.software.modsen.ratingservicecabaggregator.repositories.RatingRepository;
import com.software.modsen.ratingservicecabaggregator.service.RatingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.software.modsen.ratingservicecabaggregator.util.ExceptionMessages.RIDE_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
public class RatingSteps {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RideClient rideClient;

    private Rating createdRating;
    private Rating existingRating;
    private Exception exception;

    @Given("a ride with ID {long} exists")
    public void aRideWithIDExists(long rideId) {
        RideResponse rideResponse = new RideResponse();
        rideResponse.setId(rideId);
        when(rideClient.getRide(rideId)).thenReturn(ResponseEntity.ok(rideResponse));
    }

    @When("I create a rating with rate {int} for ride ID {long}")
    public void iCreateARatingWithRateForRideID(int rate, long rideId) {
        createdRating = new Rating();
        createdRating.setRate(rate);
        createdRating.setRideId(rideId);

        RideResponse rideResponse = new RideResponse();
        rideResponse.setId(rideId);
        when(rideClient.getRide(any())).thenReturn(ResponseEntity.ok(rideResponse));

        when(ratingRepository.save(any())).thenReturn(createdRating);
        createdRating = ratingService.createRating(createdRating);
    }

    @Then("the rating should be created with rate {int} and ride ID {long}")
    public void theRatingShouldBeCreatedWithRateAndRideID(int rate, long rideId) {
        assertEquals(rate, createdRating.getRate());
        assertEquals(rideId, createdRating.getRideId());
    }

    @Given("a ride with ID {long} does not exist")
    public void aRideWithIDDoesNotExist(long rideId) {
        when(rideClient.getRide(rideId)).thenReturn(null);
    }

    @When("I try to create a rating with rate {int} for ride ID {long}")
    public void iTryToCreateARatingWithRateForRideID(int rate, long rideId) {
        createdRating = new Rating(rate, rideId);
        when(rideClient.getRide(any())).thenReturn(null);

        try {
            ratingService.createRating(createdRating);
        } catch (RideNotFoundException ex) {
            exception = ex;
        }
    }

    @Given("a rating with ID {long} exists with rate {int} and ride ID {long}")
    public void aRatingWithIDExistsWithRateAndRideID(long ratingId, int rate, long rideId) {
        existingRating = new Rating();
        existingRating.setId(ratingId);
        existingRating.setRate(rate);
        existingRating.setRideId(rideId);

        when(ratingRepository.findById(ratingId)).thenReturn(java.util.Optional.of(existingRating));
    }

    @When("I request the rating by ID {long}")
    public void iRequestTheRatingByID(long ratingId) {
        createdRating = ratingService.getRatingById(ratingId);
    }

    @Then("I should receive the rating with rate {int} and ride ID {long}")
    public void iShouldReceiveTheRatingWithRateAndRideID(int rate, long rideId) {
        assertEquals(rate, createdRating.getRate());
        assertEquals(rideId, createdRating.getRideId());
    }

    @When("I update the rating with ID {long} to rate {int}")
    public void iUpdateTheRatingWithIDToRate(long ratingId, int rate) {
        existingRating.setRate(rate);
        when(ratingRepository.findById(any())).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(any())).thenReturn(existingRating);
        createdRating = ratingService.updateRating(ratingId, existingRating);
    }

    @Then("the rating should be updated to rate {int}")
    public void theRatingShouldBeUpdatedToRate(int rate) {
        assertEquals(rate, createdRating.getRate());
    }

    @When("I delete the rating with ID {long}")
    public void iDeleteTheRatingWithID(long ratingId) {
        when(ratingRepository.findById(ratingId)).thenReturn(java.util.Optional.of(existingRating));
        createdRating = ratingService.deleteRatingById(ratingId);
    }

    @Then("the rating should be deleted successfully")
    public void theRatingShouldBeDeletedSuccessfully() {
        assertEquals(existingRating, createdRating);
        Mockito.verify(ratingRepository).deleteById(existingRating.getId());
    }

    @Then("I should receive a RideNotFoundException for ride ID {long}")
    public void iShouldReceiveARideNotFoundExceptionForRideID(long ratingId) {
        String expected = String.format(RIDE_NOT_FOUND, ratingId);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }
}
