package com.lh2z.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Public-Bereich
                .requestMatchers(
                    "/", "/login", "/error", "/access-denied",
                    "/webjars/**", "/css/**", "/js/**", "/images/**"
                ).permitAll()

                // Admin-Bereich
                .requestMatchers("/admin/countries/**").hasRole("ADMIN")
                .requestMatchers("/admin/emissions/**").hasAnyRole("ADMIN", "SCIENTIST")
                .requestMatchers("/admin/review/**").hasAnyRole("ADMIN", "REVIEWER")

                // Rest
                .anyRequest().authenticated()
            )

            // Loginseite, Weiterleitung auf Startseite
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )

            // 403 Fehler
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    // Testuser
    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin").password("{noop}admin").roles("ADMIN").build(),
            User.withUsername("scientist").password("{noop}scientist").roles("SCIENTIST").build(),
            User.withUsername("reviewer").password("{noop}reviewer").roles("REVIEWER").build(),
            User.withUsername("publisher").password("{noop}publisher").roles("PUBLISHER").build()
        );
    }
}
