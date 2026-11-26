package com.vitalog.dto;

import java.time.LocalDate;
import java.util.Set;

import com.vitalog.model.HabitFrequency;

public record HabitResponse(
        String id,
        String name,
        int durationDays,
        HabitFrequency frequency,
        Set<String> weeklyDays,
        LocalDate startDate) {
}
