package com.example.microservice.service;

import com.example.microservice.data.TrainerMonthlySummary;
import com.example.microservice.data.TrainerWorkloadRequest;
import com.example.microservice.exception.SummaryProcessingException;
import com.example.microservice.repository.TrainerMonthlySummaryRepository;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final static String SUMMARY_EXCEPTION = "Trainer with such email doesn't exist";
    private final TrainerMonthlySummaryRepository summaryRepository;

    public void updateWorkload(TrainerWorkloadRequest request) {
        String email = request.getEmail();
        int trainingDuration = request.getTrainingInfo().getTrainingDuration();
        int year = request.getTrainingInfo().getTrainingDate().getYear();
        Month month = request.getTrainingInfo().getTrainingDate().getMonth();

        Optional<TrainerMonthlySummary> optionalTrainerSummary = summaryRepository.findByEmail(
              email);

        TrainerMonthlySummary trainerSummary = optionalTrainerSummary.orElseGet(
              () -> TrainerMonthlySummary.builder()
                    .email(email)
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .yearMonthSummaries(Map.of())
                    .build());

        Map<Integer, Map<Month, Integer>> yearMonthSummaries = new HashMap<>(
              trainerSummary.getYearMonthSummaries());
        yearMonthSummaries.computeIfAbsent(year, k -> new HashMap<>());
        yearMonthSummaries.get(year).merge(month, trainingDuration, Integer::sum);
        trainerSummary.setYearMonthSummaries(yearMonthSummaries);
        summaryRepository.save(trainerSummary);
    }

    public TrainerMonthlySummary calculateMonthlySummary(String email) {
        return summaryRepository.findByEmail(email)
              .orElseThrow(() -> new SummaryProcessingException(SUMMARY_EXCEPTION));
    }

}
