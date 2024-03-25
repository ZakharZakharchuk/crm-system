package org.example.data.mapper;

import org.example.data.entity.Training;
import org.example.data.dto.TrainingDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {TraineeMapper.class, TrainerMapper.class,
      TrainingTypeMapper.class})
@Component
public interface TrainingMapper {
    TrainingDto trainingToDto(Training training);

    Training dtoToTraining(TrainingDto trainingDto);

}
