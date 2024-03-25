package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.data.dto.TrainingTypeDto;
import org.example.service.TrainingTypeService;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {

    private static final String TRAINING_TYPE_ENDPOINT = "/training-type";

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TrainingTypeDto trainingTypeDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
              .standaloneSetup(trainingTypeController)
              .build();
        objectMapper = new ObjectMapper();
        trainingTypeDto = UnitTestExpectedDtoSupplier.createTrainingTypeDtoWith();
    }


    @Test
    public void testFindTrainingTypeByUsernameContaining() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(trainingTypeDto));

        when(trainingTypeService.getAllTrainings()).thenReturn(List.of(trainingTypeDto));

        String actual = mockMvc
              .perform(get(TRAINING_TYPE_ENDPOINT))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(trainingTypeService).getAllTrainings();
    }
}
