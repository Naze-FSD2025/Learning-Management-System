package com.guvi.lms.config;

import com.guvi.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.
        web.builders.HttpSecurity;

import org.springframework.security.config.http.
        SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.
        SecurityFilterChain;

import org.springframework.security.web.authentication.
        UsernamePasswordAuthenticationFilter;

import com.guvi.lms.security.
        JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain
    securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/instructor/**")
                        .hasRole("INSTRUCTOR")

                        .requestMatchers("/student/**")
                        .hasRole("STUDENT")

                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/instructor/**")
                        .hasRole("INSTRUCTOR")

                        .requestMatchers("/api/student/**")
                        .hasRole("STUDENT")

                        .requestMatchers("/api/files/download/**")
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {

                            String role = authentication.getAuthorities()
                                    .iterator()
                                    .next()
                                    .getAuthority();

                            if (role.equals("ROLE_ADMIN")) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (role.equals("ROLE_INSTRUCTOR")) {
                                response.sendRedirect("/instructor/dashboard");
                            } else {
                                response.sendRedirect("/student/dashboard");
                            }
                        })
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    CommandLineRunner resetAdminPassword(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            userRepository.findByEmail("admin@gmail.com")
                    .ifPresent(admin -> {
                        admin.setPassword(
                                passwordEncoder.encode("admin123"));
                        userRepository.save(admin);

                        System.out.println(
                                "Admin password reset successfully");
                    });
        };
    }

}