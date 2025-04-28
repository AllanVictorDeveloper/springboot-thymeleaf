package com.mvc_thymeleaf.services;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.entities.Usuario;
import com.mvc_thymeleaf.repository.IUsuarioRepository;
import com.mvc_thymeleaf.services.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

    private final UsuarioMapper usuarioMappers;

    public UsuarioService(IUsuarioRepository iUsuarioRepository, UsuarioMapper usuarioMappers) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.usuarioMappers = usuarioMappers;
    }


    public void salvarUsuario(UsuarioRequestDto usuarioRequestDto) {

      var usuarioEntidade =  usuarioMappers.converterParaEntidade(usuarioRequestDto);

        this.iUsuarioRepository.save(usuarioEntidade);
    }
}
