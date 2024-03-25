package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.example.data.dto.TraineeDto;
import org.example.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainee")
@AllArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @Operation(
          summary = "Update trainee",
          description = "Update trainee and return trainee with new fields"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping()
    public TraineeDto updateTrainee(@RequestBody TraineeDto traineeDto) {
        return traineeService.updateTrainee(traineeDto);
    }

    @Operation(
          summary = "Delete trainee",
          description = "Delete trainee by ID"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainee(
          @Parameter(description = "ID of the Trainee", required = true) @PathVariable Long id) {
        traineeService.deleteById(id);
    }

    @Operation(
          summary = "Find trainees",
          description = "Returns trainees whose username contains current symbols"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{username}")
    public List<TraineeDto> findTraineeByUsernameContaining(
          @Parameter(description = "Part of trainee's username", required = true) @PathVariable String username) {
        return traineeService.findByUsernameContaining(username);
    }

    @Operation(
          summary = "Get trainee",
          description = "Returns trainee by email"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public TraineeDto getByEmail(@RequestBody String email) {
        return traineeService.getTraineeByEmail(email);
    }
}
