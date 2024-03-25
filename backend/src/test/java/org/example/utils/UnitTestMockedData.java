package org.example.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UnitTestMockedData {

    Long EXIST_ID = 1L;
    Long NOT_EXIST_ID = -1L;
    String EMAIL = "adam@email.com";
    String FIRST_NAME = "Adam";
    String LAST_NAME = "Smith";
    Boolean IS_ACTIVE = true;
    String USERNAME = "Adam Smith";
    String PASSWORD = "Password12";
    LocalDate DATE_OF_BIRTH = LocalDate.parse("2004-01-01");
    String ADDRESS = "Street 9";
    String TRAINING_TYPE_NAME = "Fitness";
    Integer DURATION = 45;
    LocalDateTime TRAINING_DATE = LocalDateTime.parse("2004-01-01T12:00:00");
}
