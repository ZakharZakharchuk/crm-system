package com.example.microservice.data;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TrainingInfo {
    private LocalDateTime trainingDate;
    private int trainingDuration;
}
