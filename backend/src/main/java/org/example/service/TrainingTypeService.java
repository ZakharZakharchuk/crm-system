package org.example.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.TrainingTypeDto;
import org.example.data.mapper.TrainingTypeMapper;
import org.example.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    public List<TrainingTypeDto> getAllTrainings() {
        return trainingTypeMapper.trainingTypesToDto(trainingTypeRepository.findAll());
    }
}
