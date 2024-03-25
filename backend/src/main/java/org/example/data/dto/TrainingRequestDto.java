package org.example.data.dto;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TrainingRequestDto {

    private Long traineeId;
    private Long trainerId;
    private Long trainingTypeId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Min(value = 0, message = "Page must be greater than or equal to 0")
    private int page;
    private String sortOrder;
    private String sortBy;
}
