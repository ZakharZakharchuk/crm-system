package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.example.data.dto.TrainingDto;
import org.example.data.dto.TrainingRequestDto;
import org.example.data.entity.Training;
import org.example.data.mapper.TrainingMapper;
import org.example.exceptions.TrainingProcessingException;
import org.example.repository.TrainingRepository;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    private static final int PAGE_NUMBER = 1;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private TrainingService trainingService;
    private TrainingDto trainingDtoWithoutId;
    private Training trainingWithoutId;
    private TrainingRequestDto trainingRequestDto;
    private TrainingDto trainingDtoWithId;
    private Training trainingWithId;

    @BeforeEach
    public void setUp() {
        trainingDtoWithoutId = UnitTestExpectedDtoSupplier.createTrainingDtoWithoutId();
        trainingWithoutId = UnitTestExpectedEntitySupplier.createTrainingWithoutId();
        trainingRequestDto = UnitTestExpectedDtoSupplier.createTrainingRequestDto();
        trainingDtoWithId = UnitTestExpectedDtoSupplier.createTrainingDtoWithId();
        trainingWithId = UnitTestExpectedEntitySupplier.createTrainingWithId();
    }

    @Test
    public void testCreateTraining() {
        when(trainingMapper.dtoToTraining(trainingDtoWithoutId)).thenReturn(trainingWithoutId);
        doNothing().when(summaryService).updateTrainingList(trainingDtoWithoutId);

        trainingService.createTraining(trainingDtoWithoutId);

        verify(trainingRepository).save(trainingWithoutId);
        verify(summaryService).updateTrainingList(trainingDtoWithoutId);
    }

    @Test
    public void getTrainingsByCriteriaShouldThrowTrainingProcessingException1() {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(-1);
        Exception exception = assertThrows(TrainingProcessingException.class,
              () -> trainingService.getTrainingsByCriteria(trainingRequestDto));

        String expectedMessage = TrainingService.PAGE_NUMBER_ERROR_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getTrainingsByCriteriaShouldThrowTrainingProcessingException2() {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(1)
              .setSortOrder("wrong").setSortBy("date");
        Exception exception = assertThrows(TrainingProcessingException.class,
              () -> trainingService.getTrainingsByCriteria(trainingRequestDto));

        String expectedMessage = TrainingService.SORT_ORDER_ERROR_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getTrainingsByCriteriaShouldThrowTrainingProcessingException3() {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(1)
              .setSortOrder("asc").setSortBy("wrong");
        Exception exception = assertThrows(TrainingProcessingException.class,
              () -> trainingService.getTrainingsByCriteria(trainingRequestDto));

        String expectedMessage = TrainingService.SORT_BY_VALUES_ERROR_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getTrainingsByCriteriaShouldReturnTrainings() {
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "date");
        Pageable pageable = PageRequest.of(PAGE_NUMBER - 1, TrainingService.PAGE_SIZE_FOR_TRAININGS)
              .withSort(sort);

        when(trainingRepository.findTrainingsByCriteria(trainingRequestDto.getTraineeId(),
              trainingRequestDto.getTrainerId(), trainingRequestDto.getTrainingTypeId(),
              trainingRequestDto.getDateFrom(), trainingRequestDto.getDateTo(),
              pageable)).thenReturn(new PageImpl<>(
              List.of(trainingWithId)));
        when(trainingMapper.trainingToDto(trainingWithId)).thenReturn(trainingDtoWithId);

        Page<TrainingDto> actual = trainingService.getTrainingsByCriteria(trainingRequestDto);

        assertEquals(new PageImpl<>(List.of(trainingDtoWithId)), actual);

        verify(trainingMapper).trainingToDto(trainingWithId);
    }
}
