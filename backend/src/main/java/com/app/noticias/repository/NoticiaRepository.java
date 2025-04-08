package com.app.noticias.repository;

import com.app.noticias.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
   Este es el repositorio para la entidad `Noticia`. Extiende `JpaRepository`,
   lo que permite acceder a los métodos CRUD estándar (crear, leer, actualizar, eliminar)
   sin necesidad de implementar estas operaciones manualmente.
*/
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    /*
       Este método personalizado utiliza JPQL para obtener todas las noticias asociadas a un usuario específico,
       basándose en el ID del usuario.
       Devuelve una lista de noticias que están asociadas con el usuario indicado.
    */
    @Query("SELECT n FROM Noticia n WHERE n.usuario.id = :usuarioId")
    List<Noticia> findByUsuarioId(@Param("usuarioId") Long usuarioId); // Parámetro para el ID del usuario.
}
