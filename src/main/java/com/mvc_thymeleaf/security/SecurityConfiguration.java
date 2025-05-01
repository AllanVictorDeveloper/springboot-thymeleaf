package com.mvc_thymeleaf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final DataSource dataSource;


    public SecurityConfiguration(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {

        return new DetalheUsuarioServico();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
//         repo.setCreateTableOnStartup(true); // descomente se quiser que a tabela seja criada automaticamente
        return repo;
    }


    @Bean
    public LoginSucesso loginSucesso(
            PersistentTokenRepository tokenRepository,
            SessionAuthenticationStrategy sessionAuthenticationStrategy
    ) {
        return new LoginSucesso(tokenRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry);
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
                        .requestMatchers("/usuario/editarPapel/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
                .and()
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expirado") // URL para redirecionar se a sessão for expirada
                        .sessionRegistry(sessionRegistry())
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(loginSucesso(persistentTokenRepository(), sessionAuthenticationStrategy(sessionRegistry())))
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("@DY4524U2IY4653IID35435423D3FG35") // pode ser qualquer string, mas mantenha em segredo
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 7 dias
                        .userDetailsService(userDetailsServiceBean())
                        .tokenRepository(persistentTokenRepository())
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }


}