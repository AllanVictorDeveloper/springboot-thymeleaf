package com.mvc_thymeleaf.exceptionGlobal;

import org.springframework.security.core.AuthenticationException;

public class UsuarioInativoException extends AuthenticationException {

    public UsuarioInativoException(String msg) {
        super(msg);
    }

    public UsuarioInativoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}