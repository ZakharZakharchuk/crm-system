package org.example.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TrainingTypeDto {

    private Long id;
    private String typeName;
}
