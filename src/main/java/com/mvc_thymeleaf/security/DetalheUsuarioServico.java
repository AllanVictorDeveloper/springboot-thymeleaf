package com.mvc_thymeleaf.security;

import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.entities.Usuario;
import com.mvc_thymeleaf.repository.IUsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

//@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

    private final IUsuarioRepository iUsuarioRepository;

    public DetalheUsuarioServico(IUsuarioRepository iUsuarioRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioRepository.findByLogin(username);

        if (usuario != null && usuario.isAtivo()) {
            Set<GrantedAuthority> papeisDoUsuario = new HashSet<GrantedAuthority>();
            for (Papel papel : usuario.getPapeis()) {
                GrantedAuthority pp = new SimpleGrantedAuthority("ROLE_" + papel.getPapel());
                papeisDoUsuario.add(pp);
            }
            User user = new User(usuario.getLogin(), usuario.getPassword(), papeisDoUsuario);
            return user;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

    }
}
