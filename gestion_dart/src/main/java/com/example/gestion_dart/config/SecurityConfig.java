package com.example.gestion_dart.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                 .csrf(csrf -> csrf.disable())

                .authorizeRequests((requests) -> requests

                        .requestMatchers("/admin","/requests","/profileAdmin","/notificationAdmin","/usersAdmin","/les_par").hasRole("ADMIN")
                        .requestMatchers("/user","/mydartUser","/notificationUser","/profileUser").hasRole("USER")
                        .requestMatchers("/login", "/register", "/").permitAll()
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .successHandler((request, response, authentication) -> {
                            if (request.isUserInRole("ROLE_ADMIN")) {
                                response.sendRedirect("/admin");
                            } else if (request.isUserInRole("ROLE_USER")) {
                                response.sendRedirect("/user");
                            } else {
                                response.sendRedirect("/login-success");
                            }
                        })
                        .loginPage("/login")
                        .permitAll());


        return http.build();
    }
}
