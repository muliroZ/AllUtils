package com.muriloscorp.allutils.repository;

import com.muriloscorp.allutils.model.Role;
import com.muriloscorp.allutils.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByNome(UserRole nome);
}
