package com.mvc_thymeleaf.controller;

import com.mvc_thymeleaf.dto.request.UsuarioRequestDto;
import com.mvc_thymeleaf.entities.Usuario;import com.mvc_thymeleaf.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/novo")
    public ModelAndView adicionarUsuario() {
        ModelAndView mv = new ModelAndView("/usuario/publica-criar-usuario");

        mv.addObject("usuario", new UsuarioRequestDto());
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid UsuarioRequestDto usuarioRequestDto, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors())
            return "/usuario/publica-criar-usuario";

        usuarioService.salvarUsuario(usuarioRequestDto);

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        return "redirect:/usuario/novo";

    }

}
