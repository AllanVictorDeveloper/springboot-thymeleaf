package com.mvc_thymeleaf.services.mapper;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.dto.response.UsuarioResponseDto;
import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {


    public Usuario converterParaEntidade(UsuarioRequestDto requestDto, List<Papel> papeis) {

        Usuario usuario = new Usuario();

        usuario.setNome(requestDto.getNome());
        usuario.setCpf(requestDto.getCpf());
        usuario.setDataNascimento(requestDto.getDataNascimento());
        usuario.setEmail(requestDto.getEmail());
        usuario.setPassword(requestDto.getPassword());
        usuario.setLogin(requestDto.getLogin());
        usuario.setPapeis(papeis);

        return usuario;
    }

    public Usuario converterParaEntidadeEditar(Usuario usuario, UsuarioRequestDto requestDto) {

        usuario.setNome(requestDto.getNome());
        usuario.setCpf(requestDto.getCpf());
        usuario.setDataNascimento(requestDto.getDataNascimento());
        usuario.setEmail(requestDto.getEmail());
        usuario.setPassword(requestDto.getPassword());
        usuario.setLogin(requestDto.getLogin());

        return usuario;
    }

    public Usuario converterUsuerioResponseParaEntidade(UsuarioResponseDto requestDto) {

        Usuario usuario = new Usuario();

        usuario.setId(requestDto.id());
        usuario.setNome(requestDto.nome());
        usuario.setCpf(requestDto.cpf());
        usuario.setDataNascimento(requestDto.dataNascimento());
        usuario.setEmail(requestDto.email());
        usuario.setPassword(requestDto.password());
        usuario.setLogin(requestDto.login());

        return usuario;
    }


    public UsuarioResponseDto converterParaDto(Usuario usuario){

        UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .login(usuario.getLogin())
                .password(usuario.getPassword())
                .ativo(usuario.isAtivo())
                .papeis(usuario.getPapeis())
                .build();

        return usuarioResponseDto;
    }
}
