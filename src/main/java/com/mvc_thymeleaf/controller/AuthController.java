package com.mvc_thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @RequestMapping("/login")
    public ModelAndView entrar() {
        ModelAndView mv = new ModelAndView("publica/login");

        return mv;
    }
}
