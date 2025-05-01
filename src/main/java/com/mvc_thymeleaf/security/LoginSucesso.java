package com.mvc_thymeleaf.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSucesso extends SavedRequestAwareAuthenticationSuccessHandler {

    private final PersistentTokenRepository tokenRepository;

    public LoginSucesso(PersistentTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

//        String username = authentication.getName();
//
//        // 🔥 Remove tokens antigos desse usuário
//        tokenRepository.removeUserTokens(username);

        String redirectUrl = "/usuario/index";
        response.sendRedirect(redirectUrl);
    }


}