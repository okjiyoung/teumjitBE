
package com.example.backend.config;

import com.example.backend.service.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(
                    HttpMethod.POST, "/api/users"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.POST, "/api/users/login"
                ).permitAll()
                .requestMatchers(
                    "/api/email-verifications/**"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/healthData/user/*/inactive"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.POST,
                "/api/healthData"
                ).permitAll()
                .requestMatchers(
                    "/api/locationData/**",
                    "/api/environment/**"
                ).permitAll()
                .requestMatchers(
                    "/api/stretchings/**"
                ).permitAll()

                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
