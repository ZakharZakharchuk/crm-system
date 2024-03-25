package org.example.data.mapper;

import java.util.List;
import org.example.data.dto.TrainerDto;
import org.example.data.entity.Trainer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {TrainingTypeMapper.class})
@Component
public interface TrainerMapper {

    TrainerDto trainerToDto(Trainer trainer);

    Trainer dtoToTrainer(TrainerDto trainerDto);

    List<TrainerDto> trainersToDto(List<Trainer> trainers);
}
