package com.muriloscorp.allutils.dto;

public interface BaseRegister {
    String name();
    String password();
    String email();

    default void validate(String code) {}
}
