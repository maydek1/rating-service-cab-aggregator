package com.software.modsen.ratingservicecabaggregator.component;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com.software.modsen.ratingservicecabaggregator.component.steps"
)
public class CucumberTest{
}

