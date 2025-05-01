package com.mvc_thymeleaf.services;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.dto.response.UsuarioResponseDto;
import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.entities.Usuario;
import com.mvc_thymeleaf.enums.PapelUsuarioEnum;
import com.mvc_thymeleaf.repository.IPapelRepository;
import com.mvc_thymeleaf.repository.IUsuarioRepository;
import com.mvc_thymeleaf.services.exceptions.LoginExisteException;
import com.mvc_thymeleaf.services.mapper.UsuarioMapper;
import com.mvc_thymeleaf.utils.ConsultarUSuarioAutenticado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final IUsuarioRepository iUsuarioRepository;
    private final IPapelRepository iPapelRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(IUsuarioRepository iUsuarioRepository, IPapelRepository iPapelRepository, UsuarioMapper usuarioMapper) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.iPapelRepository = iPapelRepository;
        this.usuarioMapper = usuarioMapper;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void salvarUsuario(UsuarioRequestDto usuarioRequestDto) {

        var usuario = this.iUsuarioRepository.findByLogin(usuarioRequestDto.getLogin());

        if (usuario != null)
            throw new LoginExisteException("Login já cadastrado no sistema.");

        Papel papel = this.iPapelRepository.findByPapel(PapelUsuarioEnum.USER.getRole());

        List<Papel> papeis = new ArrayList<Papel>();
        papeis.add(papel);

        var usuarioEntidade = usuarioMapper.converterParaEntidade(usuarioRequestDto, papeis);

        this.iUsuarioRepository.save(usuarioEntidade);
    }


    public List<UsuarioResponseDto> buscarTodosUsuarios() {

        var listaUsuarios = this.iUsuarioRepository.findAll();

        List<UsuarioResponseDto> usuarioResponseDtoList = listaUsuarios
                .stream()
                .map(usuarios -> this.usuarioMapper.converterParaDto(usuarios))
                .toList();

        return usuarioResponseDtoList;

    }

    public UsuarioResponseDto buscarUsuarioPorId(Long id) {
        var usuario = this.iUsuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));

        return this.usuarioMapper.converterParaDto(usuario);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void apagarUsuario(Long id) {

        var usuario = this.usuarioMapper.converterUsuerioResponseParaEntidade(this.buscarUsuarioPorId(id));

        this.iUsuarioRepository.delete(usuario);

    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void editarUsuario(Long id, UsuarioRequestDto usuarioRequestDto) {

        var usuario = this.iUsuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));

        var usuarioEntidade = usuarioMapper.converterParaEntidadeEditar(usuario, usuarioRequestDto);

        this.iUsuarioRepository.saveAndFlush(usuarioEntidade);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void atribuirPapel(int[] pps, Long idUsuario, boolean ativo) {

        //Obtém a lista de papéis selecionada pelo usuário do banco
        List<Papel> papeis = new ArrayList<Papel>();
        for (int i = 0; i < pps.length; i++) {
            long idPapel = pps[i];
            Optional<Papel> papelOptional = this.iPapelRepository.findById(idPapel);
            if (papelOptional.isPresent()) {
                Papel papel = papelOptional.get();
                papeis.add(papel);
            }
        }
        var usuarioEntidade = this.usuarioMapper
                .converterUsuerioResponseParaEntidade(this.buscarUsuarioPorId(idUsuario));

        usuarioEntidade.setAtivo(ativo);

        if (usuarioEntidade != null) {
            Usuario usr = usuarioEntidade;
            usr.setPapeis(papeis); // relaciona papéis ao usuário
            this.iUsuarioRepository.save(usr);

        }

    }

    public Usuario buscarUsuarioPorLogin(String login) {
        return this.iUsuarioRepository.findByLogin(login);
    }

    public String autorizacao() {
        var login = ConsultarUSuarioAutenticado.getNomeUsuarioAutenticado();

        Usuario usuario = this.buscarUsuarioPorLogin(login);
        String redirectURL = "";
        if (this.temAutorizacao(usuario, "ADMIN")) {
            redirectURL = "/auth/admin/admin-index";
        } else if (this.temAutorizacao(usuario, "USER")) {
            redirectURL = "/auth/usuario/usuario-index";
        } else if (this.temAutorizacao(usuario, "BIBLIOTECARIO")) {
            redirectURL = "/auth/biblio/biblio-index";
        }
        return redirectURL;
    }


    public String autorizacaoHome() {

        var login = ConsultarUSuarioAutenticado.getNomeUsuarioAutenticado();

        String redirectURL = "";

        if (login != null) {

            Usuario usuario = this.buscarUsuarioPorLogin(login);

            if (this.temAutorizacao(usuario, "ADMIN")) {
                return redirectURL = "/auth/admin/admin-index";
            } else if (this.temAutorizacao(usuario, "USER")) {
                return redirectURL = "/auth/usuario/usuario-index";
            } else if (this.temAutorizacao(usuario, "BIBLIOTECARIO")) {
                return redirectURL = "/auth/biblio/biblio-index";
            }
        }

        return redirectURL = "/index/publica-index";

    }


    /**
     * Método que verifica qual papel o usuário tem na aplicação
     */
    public boolean temAutorizacao(Usuario usuario, String papel) {
        for (Papel pp : usuario.getPapeis()) {
            if (pp.getPapel().equals(papel)) {
                return true;
            }
        }
        return false;
    }

}
