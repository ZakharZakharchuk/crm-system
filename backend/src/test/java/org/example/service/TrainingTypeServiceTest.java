package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.example.data.dto.TrainingTypeDto;
import org.example.data.entity.TrainingType;
import org.example.data.mapper.TrainingTypeMapper;
import org.example.repository.TrainingTypeRepository;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @InjectMocks
    private TrainingTypeService trainingTypeService;


    @Test
    public void testGetAllTrainings() {
        List<TrainingType> trainingTypes = List.of(
              UnitTestExpectedEntitySupplier.createTrainingTypeWithId());
        List<TrainingTypeDto> expectedTrainingTypes = List.of(
              UnitTestExpectedDtoSupplier.createTrainingTypeDtoWith());

        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        when(trainingTypeMapper.trainingTypesToDto(trainingTypes)).thenReturn(
              expectedTrainingTypes);

        List<TrainingTypeDto> actual = trainingTypeService.getAllTrainings();

        assertEquals(expectedTrainingTypes, actual);

        verify(trainingTypeRepository).findAll();
        verify(trainingTypeMapper).trainingTypesToDto(trainingTypes);
    }
}
