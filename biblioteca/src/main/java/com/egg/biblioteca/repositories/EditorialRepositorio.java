package com.egg.biblioteca.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entities.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, UUID>{

    @Query("SELECT e FROM Editorial e WHERE e.id = :id")
    Editorial buscarPorTitulo(@Param("id") UUID id);
}
