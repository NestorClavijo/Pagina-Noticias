package com.app.noticias.Service;

import com.app.noticias.model.Comentario;

import java.util.List;

/*
   Esta es una interfaz que define los métodos necesarios para manejar los comentarios dentro del sistema.
   Estos métodos serán implementados por una clase que manejará la lógica de negocio asociada a los comentarios.
*/
public interface ComentarioService {

    /*
       Este método crea un nuevo comentario.
       Recibe el ID del usuario que está creando el comentario, el ID de la noticia a la que está relacionado el comentario,
       y la descripción del comentario.
       Devuelve el comentario recién creado.
    */
    Comentario crearComentario(Long id_usuario, Long id_noticia, String descripcion);

    /*
       Este método devuelve todos los comentarios asociados a una noticia específica.
       Recibe el ID de la noticia y devuelve una lista de comentarios asociados a esa noticia.
    */
    List<Comentario> comentarioNoticia(Long id_noticia);

    /*
       Este método actualiza un comentario existente.
       Recibe el ID del comentario a actualizar y la nueva descripción que se desea asignar.
       Devuelve el comentario actualizado.
    */
    Comentario actualizarcomentario(Long id_comentario, String descripcion);

    /*
       Este método elimina un comentario existente.
       Recibe el ID del comentario que se desea eliminar.
       No devuelve nada (void).
    */
    void deleteComentario(Long id_comentario);
}
