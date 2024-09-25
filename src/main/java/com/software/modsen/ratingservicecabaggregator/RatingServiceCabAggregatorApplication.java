package com.software.modsen.ratingservicecabaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RatingServiceCabAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingServiceCabAggregatorApplication.class, args);
    }

}
