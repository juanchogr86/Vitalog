package com.vitalog.model;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "habits")
public class Habit {

    @Id
    private String id;

    private String userId;
    private String name;
    private int durationDays;
    private HabitFrequency frequency;
    private Set<String> weeklyDays;
    private LocalDate startDate;

    protected Habit() {
    }

    public Habit(
            String userId,
            String name,
            int durationDays,
            HabitFrequency frequency,
            Set<String> weeklyDays,
            LocalDate startDate) {
        this.userId = userId;
        this.name = name;
        this.durationDays = durationDays;
        this.frequency = frequency;
        this.weeklyDays = weeklyDays;
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public HabitFrequency getFrequency() {
        return frequency;
    }

    public Set<String> getWeeklyDays() {
        return weeklyDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
