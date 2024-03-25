package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.data.entity.Trainer;
import org.example.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    Trainer save(Trainer trainer);


    Optional<Trainer> findByEmail(String email);

    List<Trainer> findByFullNameContainingAndIsActiveIsTrue(String fullName);

    void deleteById(Long id);
}
