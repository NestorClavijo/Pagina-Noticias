package com.app.noticias.repository;

import com.app.noticias.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia,Long> {
}
