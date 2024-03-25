package org.example.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.data.dto.TrainerDto;
import org.example.data.entity.Trainer;
import org.example.data.mapper.TrainerMapper;
import org.example.exceptions.TrainerProcessingException;
import org.example.repository.TrainerRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class TrainerService {

    protected static final String NOT_EXIST_TRAINER = "Such trainer does not exist";

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;


    public TrainerDto getTrainerByEmail(String email) {
        return trainerRepository.findByEmail(email).map(trainerMapper::trainerToDto)
              .orElseThrow(() -> new TrainerProcessingException(NOT_EXIST_TRAINER));
    }
    @Transactional
    public TrainerDto updateTrainer(TrainerDto trainerDto) {
        Optional<Trainer> trainer = trainerRepository.findById(trainerDto.getId());
        if (trainer.isPresent()) {
            Trainer preparedTrainer = prepareTrainerForUpdate(trainerDto);
            preparedTrainer.setPassword(trainer.get().getPassword());
            return trainerMapper.trainerToDto(trainerRepository.save(preparedTrainer));
        } else {
            log.info(NOT_EXIST_TRAINER);
            throw new TrainerProcessingException(NOT_EXIST_TRAINER);
        }
    }

    public List<TrainerDto> findByUsernameContaining(String username) {
        return trainerMapper.trainersToDto(
              trainerRepository.findByFullNameContainingAndIsActiveIsTrue(username));
    }

    private Trainer prepareTrainerForUpdate(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.dtoToTrainer(trainerDto);
        trainer.setFullName(userService.generateUsername(trainer));
        return trainer;
    }
}
