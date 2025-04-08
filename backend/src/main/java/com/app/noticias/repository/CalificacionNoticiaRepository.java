package com.app.noticias.repository;

import com.app.noticias.model.CalificacionNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/*
   Este es el repositorio para la entidad `CalificacionNoticia`. Extiende `JpaRepository`,
   lo que permite acceder a los métodos CRUD estándar (crear, leer, actualizar, eliminar)
   sin necesidad de implementar estas operaciones manualmente.
*/
public interface CalificacionNoticiaRepository extends JpaRepository<CalificacionNoticia, Long> {

    /*
       Este método personalizado usa JPQL para buscar una calificación de noticia específica
       en función del ID de usuario y el ID de la noticia. Devuelve un `Optional<CalificacionNoticia>`.
    */
    @Query("SELECT c FROM CalificacionNoticia c WHERE c.usuario.id = :usuarioId AND c.noticia.id = :noticiaId")
    Optional<CalificacionNoticia> findByUsuarioIdAndNoticiaId(
            @Param("usuarioId") Long usuarioId,  // Parámetro para el ID del usuario.
            @Param("noticiaId") Long noticiaId   // Parámetro para el ID de la noticia.
    );
}
