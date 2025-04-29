package com.mvc_thymeleaf.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TB_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USU_ID")
    private Long id;

    @NotNull
    @Size(min = 3, message = "O nome deve ter no minimo 3 caracteres")
    @Column(name = "USU_NOME")
    private String nome;

    @NotEmpty(message = "O CPF deve ser informado")
    @CPF(message = "CPF inválido")
    @Column(name = "USU_CPF", unique = true)
    private String cpf;

    @Basic
    @NotNull(message = "A data de nascimento deve ser informada")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "USU_DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @NotEmpty(message = "O email deve ser informado")
    @Email(message = "Email inválido")
    @Column(name = "USU_EMAIL", unique = true)
    private String email;

    @NotEmpty(message = "A senha deve ser informada")
    @Size(min = 5, message = "A senha deve ter no minimo 5 caracteres")
    @Column(name = "USU_PASSWORD")
    private String password;

    @NotEmpty(message = "O login deve ser informada")
    @Size(min = 5, message = "O login deve ter no minimo 5 caracteres")
    @Column(name = "USU_LOGIN", unique = true)
    private String login;

    @Column(name = "USU_ATIVO")
    private boolean ativo;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_usuario_papel",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "papel_id"))
    List<Papel> papeis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }
}
