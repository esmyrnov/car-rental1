package com.carrental.carrental.config;

import com.carrental.carrental.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepo;

    // ===== USER DETAILS =====
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username)
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getEmail(),
                        u.getPassword(),
                        u.getRoles()
                                .stream()
                                .map(r -> new SimpleGrantedAuthority(r.getName()))
                                .toList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // ===== PASSWORD ENCODER =====
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ===== SECURITY FILTER =====
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/catalog", "/cars/**",
                                "/auth/**", "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        .requestMatchers("/profile/**", "/booking/**", "/bookings/**")
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers("/admin/**")
                        .hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")

                        // 🔥 РЕДІРЕКТ ПО РОЛІ
                        .successHandler((HttpServletRequest request,
                                         HttpServletResponse response,
                                         org.springframework.security.core.Authentication auth) -> {

                            boolean isAdmin = auth.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            } else {
                                response.sendRedirect("/profile");
                            }
                        })

                        .failureUrl("/auth/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
