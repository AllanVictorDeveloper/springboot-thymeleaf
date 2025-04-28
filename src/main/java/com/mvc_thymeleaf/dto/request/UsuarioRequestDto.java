package com.mvc_thymeleaf.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class UsuarioRequestDto {

    private Long id;

    @NotNull
    @Size(min = 3, message = "O nome deve ter no minimo 3 caracteres")
    private String nome;

    @NotEmpty(message = "O CPF deve ser informado")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "A data de nascimento deve ser informada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @NotEmpty(message = "O email deve ser informado")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "A senha deve ser informada")
    @Size(min = 5, message = "A senha deve ter no minimo 5 caracteres")
    private String password;

    @NotEmpty(message = "O login deve ser informada")
    @Size(min = 5, message = "O login deve ter no minimo 5 caracteres")
    private String login;


}
