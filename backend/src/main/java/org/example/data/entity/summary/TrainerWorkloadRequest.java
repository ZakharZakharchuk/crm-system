package org.example.data.entity.summary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerWorkloadRequest {

    private String email;
    private String firstname;
    private String lastname;
    private TrainingInfo trainingInfo;
    private ActionType actionType;
}

