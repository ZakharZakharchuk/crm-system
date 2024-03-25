package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.TraineeDto;
import org.example.service.TraineeService;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@RequiredArgsConstructor
public class TraineeControllerSteps {

    @MockBean // Mock the TraineeService
    private TraineeService traineeService;

    private final TraineeController traineeController;

    private TraineeDto response;
    private TraineeDto traineeDto;

    @Given("a Trainee with ID")
    public void givenATraineeWithID() {
        // Create a sample TraineeDto for testing
        traineeDto = UnitTestExpectedDtoSupplier.createTraineeDtoWithId();
    }

    @When("I update the Trainee information")
    public void whenIUpdateTheTraineeInformation() {
        // Mock the behavior of the traineeService.updateTrainee method
        // Here, we assume that the service will return the same TraineeDto as input for simplicity
        Mockito.when(traineeService.updateTrainee(Mockito.any(TraineeDto.class)))
              .thenReturn(traineeDto);

        // Make a PUT request to the TraineeController's updateTrainee endpoint
        response = traineeController.updateTrainee(traineeDto);
    }

    @Then("the Trainee information should be updated successfully")
    public void thenTheTraineeInformationShouldBeUpdatedSuccessfully() {
        // Assert that the response indicates a successful update (HTTP status code 200)

        // Assert that the response body matches the expected TraineeDto
        assertEquals(traineeDto, response);
    }
}

