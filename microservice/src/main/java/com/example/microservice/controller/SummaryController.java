package com.example.microservice.controller;

import com.example.microservice.data.TrainerMonthlySummary;
import com.example.microservice.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
@Log4j2
public class SummaryController {

    private final SummaryService summaryService;

    @Operation(
          summary = "Get summary",
          description = "Get summary for trainer by email"
    )
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Invalid parameters or other bad request"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{email}")
    public TrainerMonthlySummary getSummary(
          @Parameter(description = "Trainer's email", required = true) @PathVariable String email) {
        log.info("Get summary for trainer with email: {}", email);
        return summaryService.calculateMonthlySummary(email);
    }
}
