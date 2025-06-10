package com.banking.core.userservice.repository;

import com.banking.core.userservice.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {

  public boolean existsByEmail(String email);

  public boolean existsByCitizenId(String citizenId);

  Optional<User> findByEmail(String email);
}

