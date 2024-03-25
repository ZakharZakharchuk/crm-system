package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.example.data.dto.TrainerDto;
import org.example.service.TrainerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @Operation(
          summary = "Update trainer",
          description = "Update trainer and return trainer with new fields"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping()
    public TrainerDto updateTrainer(@RequestBody TrainerDto trainerDto) {
        return trainerService.updateTrainer(trainerDto);
    }

    @Operation(
          summary = "Find trainers",
          description = "Returns trainers whose username contains current symbols"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{username}")
    public List<TrainerDto> findTrainerByUsernameContaining(
          @Parameter(description = "Part of trainer's username", required = true) @PathVariable String username) {
        return trainerService.findByUsernameContaining(username);
    }

    @Operation(
          summary = "Get trainer",
          description = "Returns trainer by email"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public TrainerDto getByEmail(@RequestBody String email) {
        return trainerService.getTrainerByEmail(email);
    }
}
