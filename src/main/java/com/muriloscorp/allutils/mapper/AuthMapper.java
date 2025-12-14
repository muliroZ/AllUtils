package com.muriloscorp.allutils.mapper;

import com.muriloscorp.allutils.dto.AuthResponse;
import com.muriloscorp.allutils.dto.BaseRegister;
import com.muriloscorp.allutils.model.Role;
import com.muriloscorp.allutils.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public AuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(BaseRegister request, Role role) {
        User user = new User();
        user.setNome(request.name());
        user.setSenha(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRoles(Set.of(role));

        return user;
    }
}
