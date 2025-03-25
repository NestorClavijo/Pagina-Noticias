package com.app.noticias.repository;

import com.app.noticias.model.CalificacionNoticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionNoticiaRepository extends JpaRepository<CalificacionNoticia, Long> {
}
