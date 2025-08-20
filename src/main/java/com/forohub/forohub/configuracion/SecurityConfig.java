package com.forohub.forohub.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Deshabilita CSRF para permitir solicitudes PUT y DELETE desde postman
        http
                .csrf(csrf -> csrf.disable())
                // Permite todas las solicitudes a cualquier endpoint
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll()
                );
        return http.build();
    }
}
