package org.example.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.data.dto.LoginDto;
import org.example.data.dto.TraineeDto;
import org.example.data.dto.TrainerDto;
import org.example.data.dto.TrainingDto;
import org.example.data.dto.TrainingRequestDto;
import org.example.data.dto.TrainingTypeDto;

public class UnitTestExpectedDtoSupplier implements UnitTestMockedData {

    private static final int PAGE_NUMBER = 1;

    public static TraineeDto createTraineeDtoWithoutIdUsername() {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setDateOfBirth(DATE_OF_BIRTH)
              .setAddress(ADDRESS)
              .setFirstname(FIRST_NAME)
              .setLastname(LAST_NAME)
              .setIsActive(IS_ACTIVE)
              .setEmail(EMAIL);
        return traineeDto;
    }

    public static TraineeDto createTraineeDtoWithId() {
        TraineeDto traineeDto = createTraineeDtoWithoutIdUsername();
        traineeDto.setId(EXIST_ID);
        return traineeDto;
    }

    public static TraineeDto createTraineeDtoWithIdUsername() {
        TraineeDto traineeDto = createTraineeDtoWithId();
        traineeDto.setUsername(USERNAME);
        return traineeDto;
    }

    public static TrainerDto createTrainerDtoWithoutIdUsername() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setTrainingType(createTrainingTypeDtoWith())
              .setFirstname(FIRST_NAME)
              .setLastname(LAST_NAME)
              .setIsActive(IS_ACTIVE)
              .setEmail(EMAIL);
        return trainerDto;
    }

    public static TrainerDto createTrainerDtoWithId() {
        TrainerDto trainerDto = createTrainerDtoWithoutIdUsername();
        trainerDto.setId(EXIST_ID);
        return trainerDto;
    }

    public static TrainerDto createTrainerDtoWithIdUsername() {
        TrainerDto trainerDto = createTrainerDtoWithId();
        trainerDto.setUsername(USERNAME);
        return trainerDto;
    }

    public static TrainingTypeDto createTrainingTypeDtoWith() {
        return new TrainingTypeDto().setId(EXIST_ID).setTypeName("Fitness");
    }

    public static TrainingDto createTrainingDtoWithoutId() {
        TraineeDto traineeDto = createTraineeDtoWithIdUsername();
        TrainerDto trainerDto = createTrainerDtoWithIdUsername();
        TrainingTypeDto trainingTypeDto = createTrainingTypeDtoWith();
        return new TrainingDto()
              .setTrainingName("SuperTraining")
              .setDate(LocalDateTime.parse("2004-01-01T13:45:00"))
              .setDuration(45)
              .setTrainee(traineeDto)
              .setTrainer(trainerDto)
              .setTrainingType(trainingTypeDto);
    }

    public static TrainingRequestDto createTrainingRequestDto() {
        return new TrainingRequestDto()
              .setDateFrom(LocalDate.parse("2004-01-01"))
              .setDateTo(LocalDate.parse("2004-01-03"))
              .setTraineeId(EXIST_ID)
              .setTrainerId(EXIST_ID)
              .setTrainingTypeId(EXIST_ID)
              .setPage(PAGE_NUMBER)
              .setSortBy("date")
              .setSortOrder("asc");
    }

    public static TrainingDto createTrainingDtoWithId() {
        return new TrainingDto()
              .setId(EXIST_ID)
              .setTrainingType(createTrainingTypeDtoWith())
              .setTrainer(createTrainerDtoWithIdUsername())
              .setTrainee(createTraineeDtoWithIdUsername())
              .setTrainingName("TrainingName")
              .setDate(LocalDateTime.parse("2004-01-01T12:00:00"))
              .setDuration(DURATION);
    }

    public static LoginDto createLoginDto() {
        return new LoginDto().setUsername(USERNAME).setPassword(PASSWORD);
    }

}
