package com.app.noticias.Service;

import com.app.noticias.model.CalificacionNoticia;

import java.util.Optional;

/*
   Esta es una interfaz que define los métodos necesarios para manejar las calificaciones de las noticias dentro del sistema.
   Estos métodos serán implementados por una clase que manejará la lógica de negocio asociada a las calificaciones.
*/
public interface LikeService {

    /*
       Este método obtiene la calificación de una noticia por parte de un usuario específico.
       Recibe el ID de la noticia y el ID del usuario, y devuelve un `Optional<CalificacionNoticia>`,
       que puede estar vacío si no existe una calificación para esa noticia por parte de ese usuario.
    */
    Optional<CalificacionNoticia> obtenerCalificacion(Long id_noticia, Long id_usuario);

    /*
       Este método cambia la calificación de una noticia dada por un usuario.
       Recibe el ID de la calificación a actualizar y el nuevo valor de la calificación.
       Devuelve la calificación actualizada.
    */
    CalificacionNoticia cambiarCalificacion(Long id_comentario, Integer valor);

    /*
       Este método crea una nueva calificación para una noticia por parte de un usuario.
       Recibe el ID del comentario, el ID del usuario y el valor de la calificación.
       Devuelve la calificación recién creada.
    */
    CalificacionNoticia crearCalificacion(Long id_comentario, Long id_usuario, Integer valor);

    /*
       Este método elimina una calificación existente.
       Recibe el ID de la calificación a eliminar.
       No devuelve nada (void).
    */
    void deleteLike(Long id_like);
}