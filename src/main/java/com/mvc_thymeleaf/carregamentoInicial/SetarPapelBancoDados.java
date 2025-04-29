package com.mvc_thymeleaf.carregamentoInicial;

import com.mvc_thymeleaf.entities.Papel;
import com.mvc_thymeleaf.entities.Usuario;
import com.mvc_thymeleaf.enums.PapelUsuarioEnum;
import com.mvc_thymeleaf.repository.IPapelRepository;
import com.mvc_thymeleaf.repository.IUsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SetarPapelBancoDados implements CommandLineRunner {

    String[] papeis = {"ADMIN", "USER", "BIBLIOTECARIO"};

    String login = "allan.dev";

    private final IPapelRepository iPapelRepository;
    private final BCryptPasswordEncoder criptografia;
    private final IUsuarioRepository iUsuarioRepository;
    private final Validator validator;

    public SetarPapelBancoDados(IPapelRepository iPapelRepository, BCryptPasswordEncoder criptografia, IUsuarioRepository iUsuarioRepository, Validator validator) {
        this.iPapelRepository = iPapelRepository;
        this.criptografia = criptografia;
        this.iUsuarioRepository = iUsuarioRepository;
        this.validator = validator;
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

        if (!this.iUsuarioRepository.existsUsuarioByLogin(login)) {

            Papel papel = this.iPapelRepository.findByPapel(PapelUsuarioEnum.ADMIN.getRole());
            List<Papel> papeis = new ArrayList<Papel>();
            papeis.add(papel);


            Usuario usuario = new Usuario();
            usuario.setEmail("allanvictor.developer@gmail.com");
            usuario.setCpf("50352152028");
            usuario.setLogin(login);
            usuario.setNome("Allan Victor");
            usuario.setPassword(this.criptografia.encode("Dev@llan73"));
            usuario.setPapeis(papeis);
            usuario.setDataNascimento(LocalDate.of(1991, 3, 12));
            usuario.setAtivo(true);

            // Validar o usuário antes de salvar
            Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<Usuario> violation : violations) {
                    sb.append(violation.getMessage()).append("\n");
                }
                throw new IllegalArgumentException("Erro de validação: " + sb.toString());
            }

            this.iUsuarioRepository.save(usuario);
        }

    }
}
