package com.example.microservice.repository;

import com.example.microservice.data.TrainerMonthlySummary;
import java.util.List;
import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface TrainerMonthlySummaryRepository extends
      CrudRepository<TrainerMonthlySummary, String> {

    Optional<TrainerMonthlySummary> findByEmail(String email);

    List<TrainerMonthlySummary> findAll();

    TrainerMonthlySummary save(TrainerMonthlySummary entity);
}
