package com.mvc_thymeleaf.exceptionGlobal;

import com.mvc_thymeleaf.services.exceptions.LoginExisteException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginExisteException.class)
    public String handleCriarUsuarioException(LoginExisteException ex, Model model) {
        model.addAttribute("loginExiste", ex.getMessage());
        return "usuario/publica-criar-usuario";
    }
}

