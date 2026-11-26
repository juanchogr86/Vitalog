package com.vitalog.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vitalog.dto.CreateHabitRequest;
import com.vitalog.model.Habit;
import com.vitalog.model.HabitFrequency;
import com.vitalog.model.UserAccount;
import com.vitalog.repository.HabitRepository;
import com.vitalog.repository.UserAccountRepository;

@Service
public class HabitService {

    private static final Set<String> VALID_WEEK_DAYS = Set.of(
            "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");

    private final HabitRepository habitRepository;
    private final UserAccountRepository userAccountRepository;

    public HabitService(HabitRepository habitRepository, UserAccountRepository userAccountRepository) {
        this.habitRepository = habitRepository;
        this.userAccountRepository = userAccountRepository;
    }

    public Habit create(CreateHabitRequest request, String authenticatedEmail) {
        UserAccount account = userAccountRepository
                .findByEmail(authenticatedEmail.trim().toLowerCase(Locale.ROOT))
                .orElseThrow(() -> new IllegalArgumentException("La cuenta autenticada no existe."));

        String name = request.name().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("El nombre del hábito es obligatorio.");
        }

        int durationDays = request.durationDays() == null ? 66 : request.durationDays();
        if (durationDays < 1) {
            throw new IllegalArgumentException("La duración debe ser de al menos 1 día.");
        }

        Set<String> weeklyDays = Set.of();
        if (request.frequency() == HabitFrequency.WEEKLY) {
            if (request.weeklyDays() == null || request.weeklyDays().isEmpty()) {
                throw new IllegalArgumentException("Selecciona al menos un día para la frecuencia semanal.");
            }
            weeklyDays = request.weeklyDays().stream()
                    .map(day -> day == null ? "" : day.trim().toUpperCase(Locale.ROOT))
                    .collect(java.util.stream.Collectors.toUnmodifiableSet());
            if (!VALID_WEEK_DAYS.containsAll(weeklyDays) || weeklyDays.contains("")) {
                throw new IllegalArgumentException("Uno o más días seleccionados no son válidos.");
            }
        }

        Habit habit = new Habit(
                account.getId(),
                name,
                durationDays,
                request.frequency(),
                weeklyDays,
                LocalDate.now(ZoneOffset.UTC));
        return habitRepository.save(habit);
    }
}
