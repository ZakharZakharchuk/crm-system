package org.example.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.example.data.dto.TrainingDto;
import org.example.data.dto.TrainingRequestDto;
import org.example.data.entity.summary.ActionType;
import org.example.data.entity.summary.TrainerWorkloadRequest;
import org.example.data.entity.summary.TrainingInfo;
import org.example.data.mapper.TrainingMapper;
import org.example.exceptions.TrainingProcessingException;
import org.example.repository.TrainingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TrainingService {

    private static final int MINIMUM_PAGE_NUMBER = 1;
    protected static final String PAGE_NUMBER_ERROR_MESSAGE = "Page index must not be less than one!";
    protected static final int PAGE_SIZE_FOR_TRAININGS = 5;
    protected static final String SORT_BY_VALUES_ERROR_MESSAGE = "SortBy must be one of: date, "
          + "trainer, trainee, trainingType";
    protected static final String SORT_ORDER_ERROR_MESSAGE = "SortOrder must be asc or desc";
    private static final List<String> SORT_BY_VALUES = List.of("date", "trainer", "trainee",
          "trainingType");

    private TrainingRepository trainingRepository;
    private TrainingMapper trainingMapper;
    private SummaryService summaryService;

    //TODO transaction management for JMS and Spring(by default they are independent)
    @Transactional
    public void createTraining(TrainingDto trainingDto) {
        summaryService.updateTrainingList(trainingDto);
        trainingRepository.save(trainingMapper.dtoToTraining(trainingDto));
    }

    public Page<TrainingDto> getTrainingsByCriteria(TrainingRequestDto trainingRequestDto) {
        return trainingRepository.findTrainingsByCriteria(trainingRequestDto.getTraineeId(),
                    trainingRequestDto.getTrainerId(), trainingRequestDto.getTrainingTypeId(),
                    trainingRequestDto.getDateFrom(), trainingRequestDto.getDateTo(),
                    createPageRequest(trainingRequestDto.getPage(), trainingRequestDto.getSortOrder(),
                          trainingRequestDto.getSortBy()))
              .map(trainingMapper::trainingToDto);
    }

    private PageRequest createPageRequest(int page, String sortOrder, String sortBy) {
        if (page < MINIMUM_PAGE_NUMBER) {
            throw new TrainingProcessingException(PAGE_NUMBER_ERROR_MESSAGE);
        }
        if (!SORT_BY_VALUES.contains(sortBy)) {
            throw new TrainingProcessingException(SORT_BY_VALUES_ERROR_MESSAGE);
        }
        if (!(sortOrder.equals("asc") || sortOrder.equals("desc"))) {
            throw new TrainingProcessingException(SORT_ORDER_ERROR_MESSAGE);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        return PageRequest.of(page - 1, TrainingService.PAGE_SIZE_FOR_TRAININGS).withSort(sort);
    }

    private void updateSummaryInfo() {

    }

}
