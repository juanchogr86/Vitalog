package com.vitalog.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vitalog.dto.CreateHabitRequest;
import com.vitalog.dto.HabitResponse;
import com.vitalog.model.Habit;
import com.vitalog.model.UserAccount;
import com.vitalog.repository.HabitRepository;
import com.vitalog.repository.UserAccountRepository;
import com.vitalog.service.HabitService;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;
    private final HabitRepository habitRepository;
    private final UserAccountRepository userAccountRepository;

    public HabitController(
            HabitService habitService,
            HabitRepository habitRepository,
            UserAccountRepository userAccountRepository) {
        this.habitService = habitService;
        this.habitRepository = habitRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitResponse create(
            @Valid @RequestBody CreateHabitRequest request,
            Authentication authentication) {
        return toResponse(habitService.create(request, authentication.getName()));
    }

    @GetMapping
    public List<HabitResponse> list(Authentication authentication) {
        UserAccount account = userAccountRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta autenticada no existe."));
        return habitRepository.findByUserIdOrderByStartDateDesc(account.getId()).stream()
                .map(HabitController::toResponse)
                .toList();
    }

    private static HabitResponse toResponse(Habit habit) {
        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDurationDays(),
                habit.getFrequency(),
                habit.getWeeklyDays(),
                habit.getStartDate());
    }
}
