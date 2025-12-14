package com.muriloscorp.allutils.service;

import com.muriloscorp.allutils.dto.AuthResponse;
import com.muriloscorp.allutils.dto.LoginRequest;
import com.muriloscorp.allutils.dto.RegisterRequest;
import com.muriloscorp.allutils.exception.UserAlreadyExistsException;
import com.muriloscorp.allutils.mapper.AuthMapper;
import com.muriloscorp.allutils.model.Role;
import com.muriloscorp.allutils.model.User;
import com.muriloscorp.allutils.model.UserRole;
import com.muriloscorp.allutils.repository.RoleRepository;
import com.muriloscorp.allutils.repository.UserRepository;
import com.muriloscorp.allutils.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;

    public AuthService(JwtUtil jwtUtil, AuthenticationManager authManager, UserRepository userRepository, RoleRepository roleRepository, AuthMapper authMapper) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authMapper = authMapper;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.senha()
                )
        );
        User user = (User) auth.getPrincipal();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtil.generateToken(user.getEmail(), role);

        return new AuthResponse(token);
    }

    public void register(RegisterRequest request) {
        registerWithRole(request, UserRole.BASIC);
    }

    private void registerWithRole(RegisterRequest request, UserRole userRole) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("O usuário já existe!");
        }

        Role role = roleRepository.findByNome(userRole)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        userRepository.save(authMapper.toEntity(request, role));
    }
}
