package com.example.lambdareport;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerMonthlySummaryRepository extends
      CrudRepository<TrainerMonthlySummary, String> {

    List<TrainerMonthlySummary> findAll();
}
