package com.example.operationservice.config;


import com.example.operationservice.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/registration", "/api/auth/token", "/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//
//        // Устанавливаем кастомный конвертер для ролей и дополнительных данных
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            // Извлекаем роли из токена
//            List<String> roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");
//            Collection<GrantedAuthority> authorities = roles.stream()
//                    .map(role -> "ROLE_" + role.toUpperCase())
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//
//            // Дополнительная логика обработки данных пользователя
//            String id = jwt.getClaimAsString("sub");
//            String username = jwt.getClaimAsString("preferred_username");
//            String firstName = jwt.getClaimAsString("given_name");
//            String lastName = jwt.getClaimAsString("family_name");
//            String email = jwt.getClaimAsString("email");
//
//            // Создаём CustomUserDetails
//            CustomUserDetails userDetails = new CustomUserDetails(id, username, firstName, lastName, email, authorities);
//
//            // Возвращаем авторизацию с объектом CustomUserDetails
//            return authorities;
//        });
//
//        return converter;
//    }

//
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    @Override
//    public void init(WebSecurity builder) throws Exception {
//
//    }
//
//    @Override
//    public void configure(WebSecurity builder) throws Exception {
//
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated();
//    }
}