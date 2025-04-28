package com.mvc_thymeleaf.carregamentoInicial;

import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.repository.IPapelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SetarPapelBancoDados implements CommandLineRunner {

    String[] papeis = {"ADMIN", "USER", "BIBLIOTECARIO"};

    private final IPapelRepository iPapelRepository;

    public SetarPapelBancoDados(IPapelRepository iPapelRepository) {
        this.iPapelRepository = iPapelRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (String papelString : papeis) {
            Papel papel = this.iPapelRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                this.iPapelRepository.save(papel);
            }
        }
    }
}
