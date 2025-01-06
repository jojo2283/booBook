package com.example.operationservice.context.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails  implements UserDetails {

    private  String id;
    private  String username;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }


}
