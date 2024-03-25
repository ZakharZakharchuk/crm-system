package org.example.repository;

import java.time.LocalDate;
import org.example.data.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {

    Training save(Training training);

    @Query("SELECT t FROM Training t " +
          "WHERE (:dateFrom IS NULL OR t.date >= :dateFrom) " +
          "AND (:dateTo IS NULL OR t.date <= :dateTo) " +
          "AND (:trainerId IS NULL OR t.trainer = :trainerId) " +
          "AND (:traineeId IS NULL OR t.trainee = :traineeId) " +
          "AND (:trainingTypeId IS NULL OR t.trainingType = :trainingTypeId)")
    Page<Training> findTrainingsByCriteria(
          @Param("traineeId") Long traineeId,
          @Param("trainerId") Long trainerId,
          @Param("trainingTypeId") Long trainingTypeId,
          @Param("dateFrom") LocalDate dateFrom,
          @Param("dateTo") LocalDate dateTo,
          Pageable pageable);
}
