package com.example.operationservice.context.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user-info")
    public UserInfoResponse getUserInfo() {
        // Получение текущей аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Приведение principal к CustomUserDetails
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

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
        public String getId() { return id; }
        public String getUsername() { return username; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getRoles() { return roles; }
    }
}