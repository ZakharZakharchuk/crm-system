package org.example.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TrainerDto extends UserDto {

    @NotNull(message = "TrainingType is required")
    private TrainingTypeDto trainingType;
}
