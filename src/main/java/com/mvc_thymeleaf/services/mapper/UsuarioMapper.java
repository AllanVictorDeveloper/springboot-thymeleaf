package com.mvc_thymeleaf.services.mapper;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {


    public Usuario converterParaEntidade(UsuarioRequestDto requestDto) {

        Usuario usuario = new Usuario();

        usuario.setNome(requestDto.getNome());
        usuario.setCpf(requestDto.getCpf());
        usuario.setDataNascimento(requestDto.getDataNascimento());
        usuario.setEmail(requestDto.getEmail());
        usuario.setPassword(requestDto.getPassword());
        usuario.setLogin(requestDto.getLogin());

        return usuario;
    }
}
