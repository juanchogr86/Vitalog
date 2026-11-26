package com.vitalog.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vitalog.model.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

    Optional<UserAccount> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
