package com.example.operationservice.config;

import com.example.operationservice.context.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTokenUtil {

    public static CustomUserDetails parseToken(String token)  {
        final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvKjA4XwQW+giaioOaw/HsKjGlgIML1ybySGQ/RZFH5zL3tY4IG5lJqbAFlKGWhnRIprEn2tM1ZQV/TudNjHHHoUfN2kJTohNOQ3G6KzQ1UWqVjkE5Jwl5eg9rPzzO4MjQQkaY63PVk2OBs9bY1GA/cLsIp1HGLiH+d03PR6GkDrzdv8zH8bxx2xRo6tNgBAmJWDbRqa/GU28NxcliX7QqsFLa9BMI7u9EZfx284HAnElndtz1wZP5q5R7fXKvfsVT7KbjAdfB6aHnPeYYAYZYx2N9H7wz/u8TYFqDS699sY+02XQnBqq9gy+j70uQZw+I6NZwsaViMKm4H1YHuixfwIDAQAB"; // Получите ключ от Keycloak

        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKeyNew = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             publicKeyNew = keyFactory.generatePublic(keySpec);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException ex ){
            throw  new RuntimeException();
        }
        Claims claims = Jwts.parser()
                .verifyWith(publicKeyNew)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String id = claims.get("sub", String.class);
        String username = claims.get("preferred_username", String.class);
        String firstName = claims.get("given_name", String.class);
        String lastName = claims.get("family_name", String.class);
        String email = claims.get("email", String.class);
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");


        List<String> roles = (List<String>) realmAccess.get("roles");


        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new CustomUserDetails(
                id,
                username,
                firstName,
                lastName,
                email,
                authorities
        );
    }
}