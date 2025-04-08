package com.app.noticias.repository;

import com.app.noticias.model.CalificacionComentario;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   Este es el repositorio para la entidad `CalificacionComentario`. Extiende `JpaRepository`,
   lo que permite acceder a los métodos CRUD estándar (crear, leer, actualizar, eliminar)
   sin necesidad de implementar estas operaciones manualmente.
*/
public interface CalificacionComentarioRepository extends JpaRepository<CalificacionComentario, Long> {
    // No es necesario agregar métodos adicionales ya que JpaRepository ya proporciona todos los métodos básicos
}
