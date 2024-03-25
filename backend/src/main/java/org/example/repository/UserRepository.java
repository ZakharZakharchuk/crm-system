package org.example.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.example.data.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Long countByFullNameStartingWith(String fullName);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email,
          @Param("newPassword") String newPassword);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User save(User user);
}
