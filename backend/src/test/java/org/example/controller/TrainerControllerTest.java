package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.example.data.dto.TrainerDto;
import org.example.exceptions.TrainerProcessingException;
import org.example.service.TrainerService;
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
public class TrainerControllerTest {

    private static final String TRAINER_ENDPOINT = "/trainer";
    private static final Long NOT_EXIST_ID = -1L;
    private static final String USERNAME = "Adam Smith";

    private static final String EMAIL = "adam@email.com";

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TrainerDto trainerDtoWithId;
    private TrainerDto trainerDtoWithIdUsername;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
              .standaloneSetup(trainerController)
              .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        trainerDtoWithId = UnitTestExpectedDtoSupplier.createTrainerDtoWithId();
        trainerDtoWithIdUsername = UnitTestExpectedDtoSupplier.createTrainerDtoWithIdUsername();
    }

    @Test
    public void updateTrainerShouldUpdateTrainer() throws Exception {
        String expected = objectMapper.writeValueAsString(trainerDtoWithIdUsername);

        when(trainerService.updateTrainer(trainerDtoWithId)).thenReturn(trainerDtoWithIdUsername);

        String actual = mockMvc.perform(
                    put(TRAINER_ENDPOINT)
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(objectMapper.writeValueAsString(trainerDtoWithId)))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(trainerService).updateTrainer(trainerDtoWithId);
    }

    @Test
    public void updateTrainerShouldReturnBadRequest() throws Exception {
        trainerDtoWithId.setId(NOT_EXIST_ID);
        doThrow(TrainerProcessingException.class).when(trainerService)
              .updateTrainer(trainerDtoWithId);

        mockMvc
              .perform(put(TRAINER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(trainerDtoWithId)))
              .andExpect(status().isBadRequest());
        verify(trainerService).updateTrainer(trainerDtoWithId);
    }

    @Test
    public void testFindTrainerByUsernameContaining() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(trainerDtoWithIdUsername));

        when(trainerService.findByUsernameContaining(USERNAME)).thenReturn(
              List.of(trainerDtoWithIdUsername));

        String actual = mockMvc
              .perform(get(TRAINER_ENDPOINT + "/{username}", USERNAME))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(trainerService).findByUsernameContaining(USERNAME);
    }

    @Test
    public void getByEmailShouldReturnTrainerDto() throws Exception {
        String expected = objectMapper.writeValueAsString(trainerDtoWithIdUsername);

        when(trainerService.getTrainerByEmail(EMAIL)).thenReturn(trainerDtoWithIdUsername);

        String actual = mockMvc
              .perform(post(TRAINER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(EMAIL))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);

        verify(trainerService).getTrainerByEmail(EMAIL);
    }

    @Test
    public void getByEmailShouldReturnBadRequest() throws Exception {
        String wrongEmail = "Wrong email";
        doThrow(TrainerProcessingException.class).when(trainerService)
              .getTrainerByEmail(wrongEmail);

        mockMvc
              .perform(post(TRAINER_ENDPOINT, NOT_EXIST_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(wrongEmail))
              .andExpect(status().isBadRequest());
        verify(trainerService).getTrainerByEmail(wrongEmail);
    }
}
