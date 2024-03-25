package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.data.dto.TrainingDto;
import org.example.data.dto.TrainingRequestDto;
import org.example.service.TrainingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/training")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @Operation(
          summary = "Create training",
          description = "Create training"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTraining(@RequestBody TrainingDto trainingDto) {
        trainingService.createTraining(trainingDto);
    }

    @Operation(
          summary = "Find trainings by criteria",
          description = "Returns page of trainings"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/trainings-by-criteria")
    public Page<TrainingDto> getTrainingsByCriteria(@Parameter(description = "TrainingRequest object in JSON format", required = true) @RequestBody TrainingRequestDto trainingRequestDto) {
        return trainingService.getTrainingsByCriteria(trainingRequestDto);
    }
}
