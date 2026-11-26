package com.vitalog.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Locale;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitalog.model.UserAccount;
import com.vitalog.repository.UserAccountRepository;

@Service
public class AuthService {

    private static final int BCRYPT_MAX_BYTES = 72;

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount register(String email, String password) {
        String normalizedEmail = normalizeEmail(email);
        if (userAccountRepository.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException();
        }
        if (password.getBytes(StandardCharsets.UTF_8).length > BCRYPT_MAX_BYTES) {
            throw new IllegalArgumentException("La contraseña supera el máximo seguro de 72 bytes UTF-8.");
        }

        UserAccount account = new UserAccount(
                normalizedEmail,
                passwordEncoder.encode(password),
                Instant.now());
        try {
            return userAccountRepository.save(account);
        } catch (DuplicateKeyException exception) {
            throw new UserAlreadyExistsException();
        }
    }

    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
