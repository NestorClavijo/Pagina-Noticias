package com.app.noticias.repository;

import com.app.noticias.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {
    @Query("SELECT c FROM Comentario c WHERE c.noticia.id = :noticiaId")
    List<Comentario> findByNoticiaId(@Param("noticiaId") Long noticiaId);
}
