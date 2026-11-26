package com.vitalog.service;

import java.util.Locale;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vitalog.repository.UserAccountRepository;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public MongoUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userAccountRepository.findByEmail(email.trim().toLowerCase(Locale.ROOT))
                .map(account -> User.withUsername(account.getEmail())
                        .password(account.getPasswordHash())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }
}
