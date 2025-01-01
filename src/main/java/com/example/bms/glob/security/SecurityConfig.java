package com.example.bms.glob.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/teams").hasRole("ROLE_ADMIN")
                        .requestMatchers("/users").hasRole("ROLE_ADMIN")
                        .requestMatchers("/bgt/itex").hasRole("ROLE_ADMIN")
                        .requestMatchers("/bgt/bdgt").hasRole("ROLE_ADMIN") //관리자로 필요로하는 URI를 추가해주면 된다.
                        .anyRequest().permitAll());
        return http.build();
    }
}
