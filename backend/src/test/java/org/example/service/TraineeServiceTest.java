package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.data.dto.TraineeDto;
import org.example.data.entity.Trainee;
import org.example.data.mapper.TraineeMapper;
import org.example.exceptions.TraineeProcessingException;
import org.example.repository.TraineeRepository;
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
public class TraineeServiceTest implements UnitTestMockedData {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserService userService;
    @Mock
    private TraineeMapper traineeMapper;
    @InjectMocks
    private TraineeService traineeService;
    private Trainee traineeWithIdUsernamePassword;
    private TraineeDto traineeDtoWithId;
    private TraineeDto traineeDtoWithIdUsername;
    private Trainee traineeWithId;

    @BeforeEach
    public void setUp() {
        traineeWithId = UnitTestExpectedEntitySupplier.createTraineeWithId();
        traineeWithIdUsernamePassword = UnitTestExpectedEntitySupplier.createTraineeWithIdUsernamePassword();
        traineeDtoWithId = UnitTestExpectedDtoSupplier.createTraineeDtoWithId();
        traineeDtoWithIdUsername = UnitTestExpectedDtoSupplier.createTraineeDtoWithIdUsername();
    }

    @Test
    public void updateTraineeShouldReturnTraineeDto() {
        String username = traineeDtoWithId.getFirstname() + " " + traineeDtoWithId.getLastname();

        when(traineeRepository.findById(EXIST_ID)).thenReturn(
              Optional.of(traineeWithIdUsernamePassword));
        when(traineeMapper.dtoToTrainee(traineeDtoWithId)).thenReturn(traineeWithId);
        when(userService.generateUsername(traineeWithId)).thenReturn(username);
        when(traineeRepository.save(traineeWithIdUsernamePassword)).thenReturn(
              traineeWithIdUsernamePassword);
        when(traineeMapper.traineeToDto(traineeWithIdUsernamePassword)).thenReturn(
              traineeDtoWithIdUsername);

        TraineeDto actual = traineeService.updateTrainee(traineeDtoWithId);

        assertEquals(traineeDtoWithIdUsername, actual);

        verify(traineeRepository).findById(EXIST_ID);
        verify(traineeMapper).dtoToTrainee(traineeDtoWithId);
        verify(userService).generateUsername(traineeWithId);
        verify(traineeRepository).save(traineeWithIdUsernamePassword);
        verify(traineeMapper).traineeToDto(traineeWithIdUsernamePassword);
    }

    @Test
    public void updateTraineeShouldThrowTraineeProcessingException() {
        traineeDtoWithId.setId(NOT_EXIST_ID);

        when(traineeRepository.findById(NOT_EXIST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(TraineeProcessingException.class,
              () -> traineeService.updateTrainee(traineeDtoWithId));

        String expectedMessage = TraineeService.NOT_EXIST_TRAINEE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(traineeRepository).findById(NOT_EXIST_ID);
    }

    @Test
    public void deleteByIdShouldNotThrowException() {
        when(traineeRepository.existsById(EXIST_ID)).thenReturn(true);

        traineeService.deleteById(EXIST_ID);

        verify(traineeRepository).existsById(EXIST_ID);
        verify(traineeRepository).deleteById(EXIST_ID);
    }

    @Test
    public void deleteByIdShouldThrowTraineeProcessingException() {
        when(traineeRepository.existsById(NOT_EXIST_ID)).thenReturn(false);

        Exception exception = assertThrows(TraineeProcessingException.class,
              () -> traineeService.deleteById(NOT_EXIST_ID));

        String expectedMessage = TraineeService.NOT_EXIST_TRAINEE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(traineeRepository).existsById(NOT_EXIST_ID);
    }

    @Test
    public void findByUsernameContainingShouldReturnList() {
        String username = "Adam";

        when(traineeRepository.findByFullNameContainingAndIsActiveIsTrue(username)).thenReturn(
              List.of(traineeWithIdUsernamePassword));
        when(traineeMapper.traineesToDto(List.of(traineeWithIdUsernamePassword))).thenReturn(
              List.of(traineeDtoWithIdUsername));

        List<TraineeDto> actual = traineeService.findByUsernameContaining(username);

        assertEquals(List.of(traineeDtoWithIdUsername), actual);

        verify(traineeRepository).findByFullNameContainingAndIsActiveIsTrue(username);
        verify(traineeMapper).traineesToDto(List.of(traineeWithIdUsernamePassword));
    }

    @Test
    public void getTraineeByEmailShouldReturnTraineeDto() {
        when(traineeRepository.findByEmail(EMAIL)).thenReturn(
              Optional.of(traineeWithIdUsernamePassword));
        when(traineeMapper.traineeToDto(traineeWithIdUsernamePassword)).thenReturn(
              traineeDtoWithIdUsername);

        TraineeDto actual = traineeService.getTraineeByEmail(EMAIL);

        assertEquals(traineeDtoWithIdUsername, actual);

        verify(traineeRepository).findByEmail(EMAIL);
        verify(traineeMapper).traineeToDto(traineeWithIdUsernamePassword);
    }

    @Test
    public void getTraineeByEmailShouldThrowTraineeProcessingException() {
        String wrongEmail = "Wrong email";
        when(traineeRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TraineeProcessingException.class,
              () -> traineeService.getTraineeByEmail(wrongEmail));

        String expectedMessage = TraineeService.NOT_EXIST_TRAINEE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(traineeRepository).findByEmail(wrongEmail);
    }
}
