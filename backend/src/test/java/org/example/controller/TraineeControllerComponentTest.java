package org.example.controller;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = "org.example")
@SpringBootTest
public class TraineeControllerComponentTest {
    // Leave this class empty; it serves as the entry point for Cucumber
}
