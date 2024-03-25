package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.example.data.dto.TraineeDto;
import org.example.exceptions.TraineeProcessingException;
import org.example.service.TraineeService;
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
public class TraineeControllerTest {

    private static final String TRAINEE_ENDPOINT = "/trainee";
    private static final Long ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;
    private static final String USERNAME = "Adam Smith";
    private static final String EMAIL = "adam@email.com";

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TraineeDto traineeDtoWithId;
    private TraineeDto traineeDtoWithIdUsername;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
              .standaloneSetup(traineeController)
              .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        traineeDtoWithId = UnitTestExpectedDtoSupplier.createTraineeDtoWithId();
        traineeDtoWithIdUsername = UnitTestExpectedDtoSupplier.createTraineeDtoWithIdUsername();
    }

    @Test
    public void updateTraineeShouldUpdateTrainee() throws Exception {
        String expected = objectMapper.writeValueAsString(traineeDtoWithIdUsername);

        when(traineeService.updateTrainee(traineeDtoWithId)).thenReturn(traineeDtoWithIdUsername);

        String actual = mockMvc.perform(
                    put(TRAINEE_ENDPOINT)
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(objectMapper.writeValueAsString(traineeDtoWithId)))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(traineeService).updateTrainee(traineeDtoWithId);
    }

    @Test
    public void updateTraineeShouldReturnBadRequest() throws Exception {
        traineeDtoWithId.setId(NOT_EXIST_ID);
        doThrow(TraineeProcessingException.class).when(traineeService)
              .updateTrainee(traineeDtoWithId);

        mockMvc
              .perform(put(TRAINEE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(traineeDtoWithId)))
              .andExpect(status().isBadRequest());
        verify(traineeService).updateTrainee(traineeDtoWithId);
    }

    @Test
    public void deleteTraineeShouldDeleteTrainee() throws Exception {
        doNothing().when(traineeService).deleteById(ID);

        mockMvc
              .perform(delete(TRAINEE_ENDPOINT + "/{id}", ID))
              .andExpect(status().isNoContent());

        verify(traineeService).deleteById(ID);
    }

    @Test
    public void deleteTraineeShouldReturnBadRequest() throws Exception {
        doThrow(TraineeProcessingException.class).when(traineeService).deleteById(NOT_EXIST_ID);

        mockMvc
              .perform(delete(TRAINEE_ENDPOINT + "/{id}", NOT_EXIST_ID))
              .andExpect(status().isBadRequest());
        verify(traineeService).deleteById(NOT_EXIST_ID);
    }

    @Test
    public void testFindTraineeByUsernameContaining() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(traineeDtoWithIdUsername));

        when(traineeService.findByUsernameContaining(USERNAME)).thenReturn(
              List.of(traineeDtoWithIdUsername));

        String actual = mockMvc
              .perform(get(TRAINEE_ENDPOINT + "/{username}", USERNAME))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(traineeService).findByUsernameContaining(USERNAME);
    }

    @Test
    public void getByEmailShouldReturnTraineeDto() throws Exception {
        String expected = objectMapper.writeValueAsString(traineeDtoWithIdUsername);

        when(traineeService.getTraineeByEmail(EMAIL)).thenReturn(traineeDtoWithIdUsername);

        String actual = mockMvc
              .perform(post(TRAINEE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(EMAIL))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(traineeService).getTraineeByEmail(EMAIL);
    }

    @Test
    public void getByEmailShouldReturnBadRequest() throws Exception {
        String wrongEmail = "Wrong email";
        doThrow(TraineeProcessingException.class).when(traineeService)
              .getTraineeByEmail(wrongEmail);

        mockMvc
              .perform(post(TRAINEE_ENDPOINT, NOT_EXIST_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(wrongEmail))
              .andExpect(status().isBadRequest());
        verify(traineeService).getTraineeByEmail(wrongEmail);
    }
}
