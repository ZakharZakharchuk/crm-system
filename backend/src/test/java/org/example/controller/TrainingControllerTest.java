package org.example.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.data.dto.TrainingDto;
import org.example.data.dto.TrainingRequestDto;
import org.example.exceptions.TrainingProcessingException;
import org.example.service.TrainingService;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {

    private static final String TRAINING_ENDPOINT = "/training";
    private static final String FIND_BY_CRITERIA = TRAINING_ENDPOINT + "/trainings-by-criteria";
    @Mock
    private TrainingService trainingService;
    @InjectMocks
    private TrainingController trainingController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TrainingDto trainingDtoWithoutId;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
              .standaloneSetup(trainingController)
              .build();
        objectMapper = new ObjectMapper()
              .registerModule(new JavaTimeModule())
              .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        trainingDtoWithoutId = UnitTestExpectedDtoSupplier.createTrainingDtoWithoutId();
    }

    @Test
    public void testCreateTraining() throws Exception {
        doNothing().when(trainingService).createTraining(trainingDtoWithoutId);

        mockMvc
              .perform(post(TRAINING_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(trainingDtoWithoutId)))
              .andExpect(status().isNoContent());

        verify(trainingService).createTraining(trainingDtoWithoutId);
    }

    @Test
    public void findTrainingByCriteriaShouldReturnBadRequest1() throws Exception {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(-1);
        doThrow(TrainingProcessingException.class).when(trainingService)
              .getTrainingsByCriteria(trainingRequestDto);

        mockMvc
              .perform(post(FIND_BY_CRITERIA)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(trainingRequestDto)))
              .andExpect(status().isBadRequest());
        verify(trainingService).getTrainingsByCriteria(trainingRequestDto);
    }

    @Test
    public void findTrainingByCriteriaShouldReturnBadRequest2() throws Exception {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(1)
              .setSortOrder("wrong").setSortBy("date");
        doThrow(TrainingProcessingException.class).when(trainingService)
              .getTrainingsByCriteria(trainingRequestDto);

        mockMvc
              .perform(post(FIND_BY_CRITERIA)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(trainingRequestDto)))
              .andExpect(status().isBadRequest());
        
        verify(trainingService).getTrainingsByCriteria(trainingRequestDto);
    }

    @Test
    public void findTrainingByCriteriaShouldReturnBadRequest3() throws Exception {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto().setPage(1)
              .setSortOrder("asc").setSortBy("wrong");
        doThrow(TrainingProcessingException.class).when(trainingService)
              .getTrainingsByCriteria(trainingRequestDto);

        mockMvc
              .perform(post(FIND_BY_CRITERIA)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(trainingRequestDto)))
              .andExpect(status().isBadRequest());
        verify(trainingService).getTrainingsByCriteria(trainingRequestDto);
    }
}
