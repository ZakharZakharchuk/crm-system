package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.data.entity.Trainee;
import org.example.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends CrudRepository<Trainee, Long> {

    Trainee save(Trainee trainee);

    List<Trainee> findByFullNameContainingAndIsActiveIsTrue(String fullName);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByEmail(String email);

    boolean existsById(Long id);

    void deleteById(Long id);
}
