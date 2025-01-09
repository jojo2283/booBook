package com.example.bookservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для REST API
                .cors(Customizer.withDefaults()) // Подключаем настройки CORS
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // Настраиваем OAuth2
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()); // Настройка доступа
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        // Настройка CORS
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Разрешаем куки
        config.addAllowedOrigin("http://localhost:3000"); // Разрешаем доступ с localhost
        config.addAllowedOrigin("https://boobook.dmitriy.space"); // Разрешаем доступ с вашего продакшена
        config.addAllowedHeader("*"); // Разрешаем все заголовки
        config.addAllowedMethod("*"); // Разрешаем все методы (GET, POST, PUT, DELETE и т.д.)

        // Применяем настройки для всех URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast).collect(Collectors.toList());
        });

        return converter;
    }
}
