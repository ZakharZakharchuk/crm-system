package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.data.dto.TrainingDto;
import org.example.data.entity.summary.ActionType;
import org.example.data.entity.summary.TrainerWorkloadRequest;
import org.example.data.entity.summary.TrainingInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService {

    @Value("${aws.queue.name}")
    private String queueName;

    private final JmsService jmsService;

    public void updateTrainingList(TrainingDto trainingDto) {
        TrainerWorkloadRequest trainerWorkloadRequest = TrainerWorkloadRequest.builder()
              .email(trainingDto.getTrainer().getEmail())
              .firstname(trainingDto.getTrainer().getFirstname())
              .lastname(trainingDto.getTrainer().getLastname())
              .trainingInfo(
                    TrainingInfo.builder()
                          .trainingDate(trainingDto.getDate())
                          .trainingDuration(trainingDto.getDuration())
                          .build())
              .actionType(ActionType.ADD)
              .build();
        jmsService.sendToQueue(trainerWorkloadRequest, queueName);
    }
}
