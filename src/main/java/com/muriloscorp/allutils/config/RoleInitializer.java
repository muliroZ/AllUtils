package com.muriloscorp.allutils.config;

import com.muriloscorp.allutils.model.Role;
import com.muriloscorp.allutils.model.UserRole;
import com.muriloscorp.allutils.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (UserRole role : UserRole.values()) {
                roleRepository.findByNome(role)
                        .orElseGet(() -> roleRepository.save(new Role(null, role)));
            }
        };
    }
}
