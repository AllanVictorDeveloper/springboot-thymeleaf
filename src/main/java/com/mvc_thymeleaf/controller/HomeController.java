package com.mvc_thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index/publica-index");
        mv.addObject("msnBemVindo", "Bem-vindo à biblioteca");
        return mv;
    }
}
