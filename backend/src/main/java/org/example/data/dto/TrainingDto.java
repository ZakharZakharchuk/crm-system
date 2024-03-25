package org.example.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TrainingDto {

    private Long id;
    @NotBlank(message = "Training name is required")
    @Size(max = 255, message = "Training name must be less than or equal to 255 characters")
    private String trainingName;
    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Duration must be at least 15 minutes")
    private Integer duration;
    @NotNull(message = "Trainee information is required")
    private TraineeDto trainee;
    @NotNull(message = "Trainer information is required")
    private TrainerDto trainer;
    @NotNull(message = "Training type is required")
    private TrainingTypeDto trainingType;

}
