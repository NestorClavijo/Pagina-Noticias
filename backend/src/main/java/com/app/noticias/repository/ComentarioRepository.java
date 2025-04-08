package com.app.noticias.repository;

import com.app.noticias.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
   Este es el repositorio para la entidad `Comentario`. Extiende `JpaRepository`,
   lo que permite acceder a los métodos CRUD estándar (crear, leer, actualizar, eliminar)
   sin necesidad de implementar estas operaciones manualmente.
*/
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    /*
       Este método personalizado utiliza JPQL para obtener todos los comentarios asociados a una noticia
       específica, basándose en el ID de la noticia.
       Devuelve una lista de comentarios que están relacionados con la noticia indicada.
    */
    @Query("SELECT c FROM Comentario c WHERE c.noticia.id = :noticiaId")
    List<Comentario> findByNoticiaId(@Param("noticiaId") Long noticiaId); // Parámetro para el ID de la noticia.
}
