package com.example.microservice.service;

import com.example.microservice.data.TrainerWorkloadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class JmsService {

    private final SummaryService summaryService;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Scheduled(fixedDelay = 5000)
    public void receiveAndForwardMessageFromQueue() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String message = queueMessagingTemplate.receiveAndConvert(
              "https://sqs.eu-west-3.amazonaws.com/408288715728/gymCrmQueue",
              String.class);

        TrainerWorkloadRequest trainerWorkloadRequest;
        try {
            if (message != null) {
                trainerWorkloadRequest = objectMapper.readValue(message,
                      TrainerWorkloadRequest.class);
                summaryService.updateWorkload(trainerWorkloadRequest);
                System.out.println("Received message: " + trainerWorkloadRequest);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
