package com.vitalog.dto;

import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.vitalog.model.HabitFrequency;

public record CreateHabitRequest(
        @NotBlank(message = "El nombre del hábito es obligatorio.")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres.")
        String name,

        @Min(value = 1, message = "La duración debe ser de al menos 1 día.")
        @Max(value = 3650, message = "La duración no puede superar 3650 días.")
        Integer durationDays,

        @NotNull(message = "Selecciona una frecuencia.")
        HabitFrequency frequency,

        Set<String> weeklyDays) {
}
