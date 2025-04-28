package com.mvc_thymeleaf.services;


import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.repository.IPapelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PapelUsuarioService {

    private final IPapelRepository iPapelRepository;


    public PapelUsuarioService(IPapelRepository iPapelRepository) {
        this.iPapelRepository = iPapelRepository;
    }


    public List<Papel> buscarTodosPapeis(){
        return this.iPapelRepository.findAll();
    }
}
