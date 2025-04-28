package com.mvc_thymeleaf.enums;

import lombok.Getter;

@Getter
public enum PapelUsuarioEnum {

    ADMIN("ADMIN"),
    USER("USER"),
    BIBLIOTECARIO("BIBLIOTECARIO");

    private final String role;

    PapelUsuarioEnum(String role) {
        this.role = role;
    }

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_BIBLIOTECARIO = "ROLE_BIBLIOTECARIO";
}
