package com.app.noticias.Service;

import com.app.noticias.model.Comentario;

import java.util.List;

public interface ComentarioService {
    Comentario crearComentario(Long id_usuario, Long id_noticia, String descripcion);
    List<Comentario> comentarioNoticia(Long id_noticia);
    Comentario actualizarcomentario(Long id_comentario, String descripcion);
    void deleteComentario(Long id_comentario);
}
