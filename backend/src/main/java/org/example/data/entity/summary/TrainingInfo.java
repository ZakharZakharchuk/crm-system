package org.example.data.entity.summary;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingInfo {
    private LocalDateTime trainingDate;
    private int trainingDuration;
}
