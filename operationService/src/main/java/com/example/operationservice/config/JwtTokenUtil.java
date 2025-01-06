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

    public static CustomUserDetails parseToken(String token, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKeyNew = keyFactory.generatePublic(keySpec);
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