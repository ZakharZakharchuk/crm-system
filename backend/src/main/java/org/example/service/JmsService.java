package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.data.entity.summary.TrainerWorkloadRequest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    public void sendToQueue(TrainerWorkloadRequest trainerWorkloadRequest, String queue) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String trainerWorkloadJson;
        try {
            trainerWorkloadJson = objectMapper.writeValueAsString(trainerWorkloadRequest);
            queueMessagingTemplate.send(queue,
                  MessageBuilder.withPayload(trainerWorkloadJson).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
