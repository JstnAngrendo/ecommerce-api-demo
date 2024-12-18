package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.
//            authorizeHttpRequests(authorize -> authorize
//                    .requestMatchers("/api/v1/params").permitAll()
//                    .anyRequest().authenticated());
//        return http.build();
//    }
/*@Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf().disable() // Disable CSRF for testing purposes (re-enable for production).
                    .authorizeRequests()
                    .antMatchers("/insert").permitAll() // Allow unauthenticated access to /insert.
                    .anyRequest().authenticated(); // Require authentication for other endpoints.

            return http.build();
        }*/


}
