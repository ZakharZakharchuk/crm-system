package org.example.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.data.dto.TraineeDto;
import org.example.data.entity.Trainee;
import org.example.data.mapper.TraineeMapper;
import org.example.exceptions.TraineeProcessingException;
import org.example.repository.TraineeRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class TraineeService {

    protected static final String NOT_EXIST_TRAINEE = "Such trainee does not exist";

    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final UserService userService;


    public TraineeDto getTraineeByEmail(String email) {
        return traineeRepository.findByEmail(email).map(traineeMapper::traineeToDto)
              .orElseThrow(() -> new TraineeProcessingException(NOT_EXIST_TRAINEE));
    }

    @Transactional
    public TraineeDto updateTrainee(TraineeDto traineeDto) {
        Optional<Trainee> trainee = traineeRepository.findById(traineeDto.getId());
        if (trainee.isPresent()) {
            Trainee preparedTrainee = prepareTraineeForUpdate(traineeDto);
            preparedTrainee.setPassword(trainee.get().getPassword());
            return traineeMapper.traineeToDto(
                  traineeRepository.save(preparedTrainee));
        } else {
            log.info(NOT_EXIST_TRAINEE);
            throw new TraineeProcessingException(NOT_EXIST_TRAINEE);
        }
    }

    public List<TraineeDto> findByUsernameContaining(String username) {
        return traineeMapper.traineesToDto(
              traineeRepository.findByFullNameContainingAndIsActiveIsTrue(username));
    }

    @Transactional
    public void deleteById(Long id) {
        if (traineeRepository.existsById(id)) {
            traineeRepository.deleteById(id);
        } else {
            log.info(NOT_EXIST_TRAINEE);
            throw new TraineeProcessingException(NOT_EXIST_TRAINEE);
        }
    }

    private Trainee prepareTraineeForUpdate(TraineeDto traineeDto) {
        Trainee trainee = traineeMapper.dtoToTrainee(traineeDto);
        trainee.setFullName(userService.generateUsername(trainee));
        return trainee;
    }
}
