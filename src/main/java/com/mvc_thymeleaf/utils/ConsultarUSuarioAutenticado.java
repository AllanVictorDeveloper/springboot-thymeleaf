package com.mvc_thymeleaf.utils;

import com.mvc_thymeleaf.entities.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ConsultarUSuarioAutenticado {

    public static String getNomeUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername(); // retorna login corretamente
            }
        }


        return null;  // Caso não haja um usuário autenticado ou o principal não seja do tipo Usuario
    }
}
