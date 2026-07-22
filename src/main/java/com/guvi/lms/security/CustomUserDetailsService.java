package com.guvi.lms.security;

import com.guvi.lms.entity.User;
import com.guvi.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found"));
//        System.out.println("EMAIL = " + user.getEmail());
//        System.out.println("ROLE = " + user.getRole());
        return new CustomUserDetails(user);
    }
}