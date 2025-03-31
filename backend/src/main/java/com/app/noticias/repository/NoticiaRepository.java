package com.app.noticias.repository;

import com.app.noticias.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia,Long> {
    @Query("SELECT n FROM Noticia n WHERE n.usuario.id = :usuarioId")
    List<Noticia> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
