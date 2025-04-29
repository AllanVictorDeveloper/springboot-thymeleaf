package com.mvc_thymeleaf.security;

import com.mvc_thymeleaf.repository.IUsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final IUsuarioRepository iUsuarioRepository;
    private final LoginSucesso loginSucesso;

    public SecurityConfiguration(IUsuarioRepository iUsuarioRepository, LoginSucesso loginSucesso) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.loginSucesso = loginSucesso;
    }


    @Bean
    public BCryptPasswordEncoder gerarCriptografia() {
        BCryptPasswordEncoder criptografia = new BCryptPasswordEncoder();
        return criptografia;
    }


    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        DetalheUsuarioServico detalheDoUsuario = new DetalheUsuarioServico(iUsuarioRepository);
        return detalheDoUsuario;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/bootstrap-5.2.3-dist/**").permitAll()
                        .requestMatchers("/", "/login", "/usuario/novo", "/usuario/salvar").permitAll()
                        .requestMatchers("/auth/user/**").hasAnyRole("USER", "ADMIN", "BIBLIOTECARIO")
                        .requestMatchers("/auth/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auth/biblio/**").hasRole("BIBLIOTECARIO")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/admin/***").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(loginSucesso)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }


}