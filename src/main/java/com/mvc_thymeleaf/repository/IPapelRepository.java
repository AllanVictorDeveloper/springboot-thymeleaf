package com.mvc_thymeleaf.repository;

import com.mvc_thymeleaf.entities.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPapelRepository extends JpaRepository<Papel, Long> {

    Papel findByPapel(String papel);
}
