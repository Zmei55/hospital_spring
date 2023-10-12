package com.hospital_spring.users.repositories;

import com.hospital_spring.users.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findFirstByToken(String token);
}
