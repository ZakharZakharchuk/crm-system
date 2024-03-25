package org.example.repository;

import java.util.List;
import org.example.data.entity.TrainingType;
import org.springframework.data.repository.CrudRepository;

public interface TrainingTypeRepository extends CrudRepository<TrainingType, Long> {

    List<TrainingType> findAll();
}
