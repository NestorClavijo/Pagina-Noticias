package com.app.noticias.Service;

import com.app.noticias.model.Noticia;

import java.util.List;

/*
   Esta es una interfaz que define los métodos necesarios para manejar las noticias dentro del sistema.
   Estos métodos serán implementados por una clase que manejará la lógica de negocio asociada a las noticias.
*/
public interface NoticiaService {

    /*
       Este método crea una nueva noticia.
       Recibe el ID del usuario que está creando la noticia, el título y la descripción de la noticia.
       Devuelve la noticia recién creada.
    */
    Noticia crearNoticia(Long id_usuario, String titulo, String descripcion);

    /*
       Este método obtiene todas las noticias asociadas a un usuario específico.
       Recibe el ID del usuario y devuelve una lista de noticias publicadas por ese usuario.
    */
    List<Noticia> noticiasUsuario(Long id_usuario);

    /*
       Este método actualiza una noticia existente.
       Recibe el ID de la noticia a actualizar, y el nuevo título y descripción.
       Devuelve la noticia actualizada.
    */
    Noticia actualizarNoticia(Long id_noticia, String titulo, String descripcion);

    /*
       Este método elimina una noticia existente.
       Recibe el ID de la noticia que se desea eliminar.
       No devuelve nada (void).
    */
    void deleteNoticia(Long id_noticia);
}
