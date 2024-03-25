package org.example.data.mapper;

import java.util.List;
import org.example.data.entity.Trainee;
import org.example.data.dto.TraineeDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {TrainerMapper.class, TrainingMapper.class})
@Component
public interface TraineeMapper {

    TraineeDto traineeToDto(Trainee trainee);

    Trainee dtoToTrainee(TraineeDto traineeDto);

    List<TraineeDto> traineesToDto(List<Trainee> trainees);
}
