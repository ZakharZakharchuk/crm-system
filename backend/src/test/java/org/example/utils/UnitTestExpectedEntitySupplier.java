package org.example.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.config.security.AuthenticationRequest;
import org.example.data.entity.Trainee;
import org.example.data.entity.Trainer;
import org.example.data.entity.Training;
import org.example.data.entity.TrainingType;
import org.example.data.entity.User;
import org.example.data.entity.summary.ActionType;
import org.example.data.entity.summary.TrainerWorkloadRequest;
import org.example.data.entity.summary.TrainingInfo;

public class UnitTestExpectedEntitySupplier implements UnitTestMockedData {

    public static User createUserWithoutUsername() {
        return new User()
              .setId(EXIST_ID)
              .setFirstname(FIRST_NAME)
              .setLastname(LAST_NAME)
              .setIsActive(IS_ACTIVE);
    }

    public static User createUser() {
        return createUserWithoutUsername().setFullName(USERNAME);
    }

    public static Trainee createTraineeWithoutIdUsernamePassword() {
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(DATE_OF_BIRTH)
              .setAddress(ADDRESS)
              .setFirstname(FIRST_NAME)
              .setLastname(LAST_NAME)
              .setIsActive(IS_ACTIVE)
              .setEmail(EMAIL);
        return trainee;
    }

    public static Trainee createTraineeWithId() {
        Trainee trainee = createTraineeWithoutIdUsernamePassword();
        trainee.setId(EXIST_ID);
        return trainee;
    }

    public static Trainee createTraineeWithIdUsername() {
        Trainee trainee = createTraineeWithId();
        trainee.setFullName(USERNAME);
        return trainee;
    }

    public static Trainee createTraineeWithIdUsernamePassword() {
        Trainee trainee = createTraineeWithIdUsername();
        trainee.setPassword(PASSWORD);
        return trainee;
    }

    public static Trainer createTrainerWithoutIdUsernamePassword() {
        Trainer trainer = new Trainer();
        trainer.setTrainingType(createTrainingTypeWithId())
              .setId(EXIST_ID)
              .setFirstname(FIRST_NAME)
              .setLastname(LAST_NAME)
              .setIsActive(IS_ACTIVE);
        return trainer;
    }

    public static Trainer createTrainerWithId() {
        Trainer trainer = createTrainerWithoutIdUsernamePassword();
        trainer.setId(EXIST_ID);
        return trainer;
    }

    public static Trainer createTrainerWithIdUsername() {
        Trainer trainer = createTrainerWithId();
        trainer.setFullName(USERNAME);
        return trainer;
    }

    public static Trainer createTrainerWithIdUsernamePassword() {
        Trainer trainer = createTrainerWithoutIdUsernamePassword();
        trainer.setId(EXIST_ID).setFullName(USERNAME).setPassword(PASSWORD);
        return trainer;
    }

    public static TrainingType createTrainingTypeWithId() {
        return new TrainingType().setId(EXIST_ID).setTypeName(TRAINING_TYPE_NAME);
    }

    public static Training createTrainingWithoutId() {
        Trainee trainee = createTraineeWithIdUsername();
        Trainer trainer = createTrainerWithIdUsername();
        TrainingType trainingType = createTrainingTypeWithId();
        return new Training()
              .setTrainingName("SuperTraining")
              .setDate(LocalDateTime.parse("2004-01-01T12:00:00"))
              .setDuration(45)
              .setTrainee(trainee)
              .setTrainer(trainer)
              .setTrainingType(trainingType);
    }

    public static Training createTrainingWithId() {
        return createTrainingWithoutId().setId(EXIST_ID);
    }

    public static AuthenticationRequest createAuthenticationRequest() {
        return AuthenticationRequest.builder()
              .email(EMAIL)
              .password(PASSWORD)
              .build();
    }

    public static TrainerWorkloadRequest createTrainerWorkloadRequest() {
        return TrainerWorkloadRequest.builder()
              .email(EMAIL)
              .firstname(FIRST_NAME)
              .lastname(LAST_NAME)
              .trainingInfo(
                    TrainingInfo.builder()
                          .trainingDate(TRAINING_DATE)
                          .trainingDuration(DURATION)
                          .build())
              .actionType(ActionType.ADD)
              .build();
    }
}
