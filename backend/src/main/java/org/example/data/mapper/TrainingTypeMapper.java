package org.example.data.mapper;

import java.util.List;
import org.example.data.dto.TrainingTypeDto;
import org.example.data.entity.TrainingType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TrainingTypeMapper {

    TrainingTypeDto trainingTypeToDto(TrainingType trainingType);

    List<TrainingTypeDto> trainingTypesToDto(List<TrainingType> trainingTypes);
}
