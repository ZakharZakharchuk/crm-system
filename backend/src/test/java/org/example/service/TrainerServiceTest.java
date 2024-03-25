package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.data.dto.TrainerDto;
import org.example.data.entity.Trainer;
import org.example.data.mapper.TrainerMapper;
import org.example.exceptions.TrainerProcessingException;
import org.example.repository.TrainerRepository;
import org.example.utils.UnitTestExpectedDtoSupplier;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.example.utils.UnitTestMockedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest implements UnitTestMockedData {

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserService userService;
    @Mock
    private TrainerMapper trainerMapper;
    @InjectMocks
    private TrainerService trainerService;
    private Trainer trainerWithIdUsernamePassword;
    private TrainerDto trainerDtoWithId;
    private TrainerDto trainerDtoWithIdUsername;
    private Trainer trainerWithId;

    @BeforeEach
    public void setUp() {
        trainerWithId = UnitTestExpectedEntitySupplier.createTrainerWithId();
        trainerWithIdUsernamePassword = UnitTestExpectedEntitySupplier.createTrainerWithIdUsernamePassword();
        trainerDtoWithId = UnitTestExpectedDtoSupplier.createTrainerDtoWithId();
        trainerDtoWithIdUsername = UnitTestExpectedDtoSupplier.createTrainerDtoWithIdUsername();
    }

    @Test
    public void updateTrainerShouldReturnTrainerDto() {
        String username = trainerDtoWithId.getFirstname() + " " + trainerDtoWithId.getLastname();

        when(trainerRepository.findById(EXIST_ID)).thenReturn(
              Optional.of(trainerWithIdUsernamePassword));
        when(trainerMapper.dtoToTrainer(trainerDtoWithId)).thenReturn(trainerWithId);
        when(userService.generateUsername(trainerWithId)).thenReturn(username);
        when(trainerRepository.save(trainerWithIdUsernamePassword)).thenReturn(
              trainerWithIdUsernamePassword);
        when(trainerMapper.trainerToDto(trainerWithIdUsernamePassword)).thenReturn(
              trainerDtoWithIdUsername);

        TrainerDto actual = trainerService.updateTrainer(trainerDtoWithId);

        assertEquals(trainerDtoWithIdUsername, actual);

        verify(trainerRepository).findById(EXIST_ID);
        verify(trainerMapper).dtoToTrainer(trainerDtoWithId);
        verify(userService).generateUsername(trainerWithId);
        verify(trainerRepository).save(trainerWithIdUsernamePassword);
        verify(trainerMapper).trainerToDto(trainerWithIdUsernamePassword);
    }

    @Test
    public void updateTrainerShouldThrowTrainerProcessingException() {
        trainerDtoWithId.setId(NOT_EXIST_ID);

        when(trainerRepository.findById(NOT_EXIST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(TrainerProcessingException.class,
              () -> trainerService.updateTrainer(trainerDtoWithId));

        String expectedMessage = TrainerService.NOT_EXIST_TRAINER;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(trainerRepository).findById(NOT_EXIST_ID);
    }

    @Test
    public void findByUsernameContainingShouldReturnList() {
        String username = "Adam";

        when(trainerRepository.findByFullNameContainingAndIsActiveIsTrue(username)).thenReturn(
              List.of(trainerWithIdUsernamePassword));
        when(trainerMapper.trainersToDto(List.of(trainerWithIdUsernamePassword))).thenReturn(
              List.of(trainerDtoWithIdUsername));

        List<TrainerDto> actual = trainerService.findByUsernameContaining(username);

        assertEquals(List.of(trainerDtoWithIdUsername), actual);

        verify(trainerRepository).findByFullNameContainingAndIsActiveIsTrue(username);
        verify(trainerMapper).trainersToDto(List.of(trainerWithIdUsernamePassword));
    }

    @Test
    public void getTrainerByEmailShouldReturnTrainerDto() {
        when(trainerRepository.findByEmail(EMAIL)).thenReturn(
              Optional.of(trainerWithIdUsernamePassword));
        when(trainerMapper.trainerToDto(trainerWithIdUsernamePassword)).thenReturn(
              trainerDtoWithIdUsername);

        TrainerDto actual = trainerService.getTrainerByEmail(EMAIL);

        assertEquals(trainerDtoWithIdUsername, actual);

        verify(trainerRepository).findByEmail(EMAIL);
        verify(trainerMapper).trainerToDto(trainerWithIdUsernamePassword);
    }

    @Test
    public void getTrainerByEmailShouldThrowTrainerProcessingException() {
        String wrongEmail = "Wrong email";
        when(trainerRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TrainerProcessingException.class,
              () -> trainerService.getTrainerByEmail(wrongEmail));

        String expectedMessage = TrainerService.NOT_EXIST_TRAINER;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(trainerRepository).findByEmail(wrongEmail);
    }
}
