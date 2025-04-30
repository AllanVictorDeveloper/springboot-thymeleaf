package com.mvc_thymeleaf.security;

import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Papel papel : usuario.getPapeis()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + papel.getPapel()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    public String getNomeCompleto() {
        return usuario.getNome(); // Acesse o campo 'nome' da sua entidade Usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return usuario.isAtivo();
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.isAtivo();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.isAtivo();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
