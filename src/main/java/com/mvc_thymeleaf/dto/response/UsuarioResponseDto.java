package com.mvc_thymeleaf.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mvc_thymeleaf.entities.Papel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioResponseDto(
        Long id,
        String nome,

        String cpf,

        LocalDate dataNascimento,

        String email,

        String password,

        String login,

         boolean ativo,

        List<Papel> papeis


) {
}
