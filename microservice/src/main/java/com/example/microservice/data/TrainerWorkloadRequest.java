package com.example.microservice.data;

import lombok.Data;

@Data
public class TrainerWorkloadRequest {

    private String email;
    private String firstname;
    private String lastname;
    private TrainingInfo trainingInfo;
    private ActionType actionType;
}

