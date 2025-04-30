package com.mvc_thymeleaf.controller;


import com.mvc_thymeleaf.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping("/")
    public ModelAndView index() {

        String redirectURL = this.usuarioService.autorizacaoHome();
        ModelAndView mv = new ModelAndView(redirectURL);

        if (redirectURL.equals("/index/publica-index"))
            mv.addObject("msnBemVindo", "Bem-vindo à biblioteca");

        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView entrar() {
        ModelAndView mv = new ModelAndView("login");

        return mv;
    }
}
