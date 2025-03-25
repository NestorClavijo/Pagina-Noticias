package com.app.noticias.repository;

import com.app.noticias.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {
}
