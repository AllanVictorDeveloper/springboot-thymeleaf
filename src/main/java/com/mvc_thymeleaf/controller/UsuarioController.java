package com.mvc_thymeleaf.controller;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.repository.IUsuarioRepository;
import com.mvc_thymeleaf.services.PapelUsuarioService;
import com.mvc_thymeleaf.services.UsuarioService;
import com.mvc_thymeleaf.services.exceptions.LoginExisteException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final IUsuarioRepository iUsuarioRepository;

    private final PapelUsuarioService papelUsuarioService;

    public UsuarioController(UsuarioService usuarioService, IUsuarioRepository iUsuarioRepository, PapelUsuarioService papelUsuarioService) {
        this.usuarioService = usuarioService;
        this.iUsuarioRepository = iUsuarioRepository;
        this.papelUsuarioService = papelUsuarioService;
    }

    @GetMapping("/index")
    public String index(Authentication authentication) {

        String redirectURL = this.usuarioService.autorizacao();
        ModelAndView mv = new ModelAndView(redirectURL);

        return redirectURL;
    }

    @GetMapping("/novo")
    public ModelAndView adicionarUsuario() {
        ModelAndView mv = new ModelAndView("publica/publica-criar-usuario");

        mv.addObject("usuario", new UsuarioRequestDto());
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("usuario") UsuarioRequestDto usuarioRequestDto,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "publica/publica-criar-usuario";
        }

        try {
            usuarioService.salvarUsuario(usuarioRequestDto);
        } catch (LoginExisteException e) {
            // Adiciona erro no campo específico 'login'
            result.rejectValue("login", "error.usuario", e.getMessage());
            return "publica/publica-criar-usuario";
        }

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        return "redirect:/usuario/novo";

    }


    @RequestMapping("/admin/listar")
    public ModelAndView listarUsuarios() {

        ModelAndView mv = new ModelAndView("auth/admin/admin-listar-usuario");

        mv.addObject("usuarios", this.usuarioService.buscarTodosUsuarios());

        return mv;

    }

    @GetMapping("/admin/apagar/{id}")
    public String apagarUsuario(@PathVariable Long id) {

        this.usuarioService.apagarUsuario(id);

        return "redirect:/usuario/admin/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView buscarEditarUsuario(@PathVariable Long id) {
        var usuario = this.usuarioService.buscarUsuarioPorId(id);

        ModelAndView mv = new ModelAndView("auth/admin/admin-alterar-usuario");

        mv.addObject("usuario", usuario);

        return mv;
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(
            @PathVariable Long id,
            @Valid @ModelAttribute("usuario") UsuarioRequestDto requestDto,
            BindingResult result,
            RedirectAttributes attributes
    ) {

        if (result.hasErrors()) {
            return "auth/admin/admin-alterar-usuario";
        }

        this.usuarioService.editarUsuario(id, requestDto);

        attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");

        return "redirect:/usuario/admin/listar";
    }

    @GetMapping("/editarPapel/{id}")
    public ModelAndView selecionarPapel(@PathVariable("id") Long id) {
        var usuario = this.usuarioService.buscarUsuarioPorId(id);
        var papeis = this.papelUsuarioService.buscarTodosPapeis();

        ModelAndView mv = new ModelAndView("auth/admin/admin-editar-papel-usuario");

        mv.addObject("usuario", usuario);
        mv.addObject("listaPapeis", papeis);

        return mv;
    }

    @PostMapping("/editarPapel/{id}")
    public String atribuirPapel(
            @PathVariable Long id,
            @RequestParam(value = "pps", required = false) int[] pps,
            @ModelAttribute("usuario") UsuarioRequestDto requestDto,
            RedirectAttributes attributes
    ) {

        if (pps == null) {
            requestDto.setId(id);
            attributes.addFlashAttribute("mensagem", "Pelo menos um papel deve ser informado");
            return "redirect:/usuario/editarPapel/" + id;
        }

        this.usuarioService.atribuirPapel(pps, id, requestDto.isAtivo());

        attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");

        return "redirect:/usuario/admin/listar";
    }

}
