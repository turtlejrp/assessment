package com.kbtg.bootcamp.posttest.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests
                            .requestMatchers("/users/**").permitAll()
                            .requestMatchers("/lotteries/**").permitAll()
                            .anyRequest()).authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

}
