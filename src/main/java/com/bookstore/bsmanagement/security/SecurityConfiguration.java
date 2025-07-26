package com.bookstore.bsmanagement.security;
 import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (important for APIs)
            .authorizeHttpRequests(auth -> auth
                // Allow Swagger URLs
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()

                // Allow your API endpoints
                .requestMatchers("/api/**").permitAll()

                // All other endpoints require authentication
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
 

