package com.app.noticias.repository;

import com.app.noticias.model.CalificacionNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CalificacionNoticiaRepository extends JpaRepository<CalificacionNoticia, Long> {
    @Query("SELECT c FROM CalificacionNoticia c WHERE c.usuario.id = :usuarioId AND c.noticia.id = :noticiaId")
    Optional<CalificacionNoticia> findByUsuarioIdAndNoticiaId(@Param("usuarioId") Long usuarioId, @Param("noticiaId") Long noticiaId);
}
