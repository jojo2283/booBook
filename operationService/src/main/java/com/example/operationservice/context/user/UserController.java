package com.example.operationservice.context.user;

import com.example.operationservice.config.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class UserController {
    private final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvKjA4XwQW+giaioOaw/HsKjGlgIML1ybySGQ/RZFH5zL3tY4IG5lJqbAFlKGWhnRIprEn2tM1ZQV/TudNjHHHoUfN2kJTohNOQ3G6KzQ1UWqVjkE5Jwl5eg9rPzzO4MjQQkaY63PVk2OBs9bY1GA/cLsIp1HGLiH+d03PR6GkDrzdv8zH8bxx2xRo6tNgBAmJWDbRqa/GU28NxcliX7QqsFLa9BMI7u9EZfx284HAnElndtz1wZP5q5R7fXKvfsVT7KbjAdfB6aHnPeYYAYZYx2N9H7wz/u8TYFqDS699sY+02XQnBqq9gy+j70uQZw+I6NZwsaViMKm4H1YHuixfwIDAQAB"; // Получите ключ от Keycloak


    @GetMapping("/user-info")
    public UserInfoResponse getUserInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Получение текущей аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Приведение principal к CustomUserDetails
            Jwt jwt = (Jwt) authentication.getPrincipal();
            CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue(), publicKey);


            // Создание ответа с данными пользователя
            return new UserInfoResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getEmail(),
                    userDetails.getAuthorities().toString()
            );
        }

        throw new RuntimeException("Пользователь не найден или не аутентифицирован");
    }

    public static class UserInfoResponse {
        private String id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String roles;

        public UserInfoResponse(String id, String username, String firstName, String lastName, String email, String roles) {
            this.id = id;
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.roles = roles;
        }

        // Getters and setters
        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getRoles() {
            return roles;
        }
    }
}