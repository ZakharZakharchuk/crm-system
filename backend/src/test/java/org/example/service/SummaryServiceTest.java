/*
package org.example.service;

import static org.mockito.Mockito.verify;

import org.example.data.dto.TrainingDto;
import org.example.data.entity.summary.TrainerWorkloadRequest;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {

    @Mock
    private JmsService jmsService;
    @InjectMocks
    private SummaryService summaryService;

    private TrainerWorkloadRequest trainerWorkloadRequest = UnitTestExpectedEntitySupplier.createTrainerWorkloadRequest();
    private TrainingDto trainingDto = UnitTestExpectedDtoSupplier.createTrainingDtoWithId();

    @Test
    public void testUpdateTrainingList() {
        summaryService.updateTrainingList(trainingDto);
        verify(jmsService).sendToQueue(trainerWorkloadRequest, "your-queue-name");
    }
}
*/
