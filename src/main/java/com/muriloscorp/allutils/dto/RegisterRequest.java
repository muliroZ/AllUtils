package com.muriloscorp.allutils.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull(message = "Nome obrigatório") String name,
        @NotNull(message = "Senha obrigatória") @Size(min = 6) String password,
        @NotNull(message = "Email obrigatório") @Email(message = "Email inválido") String email
) implements BaseRegister {}
