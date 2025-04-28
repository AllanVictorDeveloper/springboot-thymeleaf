package com.mvc_thymeleaf.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "TB_PAPEIS")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAP_ID")
    private Long id;

    @NotNull
    @Column(name = "PAP_NOME")
    private String papel;

    @ManyToMany(mappedBy = "papeis", fetch = FetchType.EAGER)
    private List<Usuario> usuarios;

    public Papel() {
    }

    public Papel(String papel) {
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
