package com.vitalog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vitalog.model.Habit;

public interface HabitRepository extends MongoRepository<Habit, String> {
    List<Habit> findByUserIdOrderByStartDateDesc(String userId);

    void deleteByUserId(String userId);
}
