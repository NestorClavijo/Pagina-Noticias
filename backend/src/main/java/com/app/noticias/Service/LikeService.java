package com.app.noticias.Service;

import com.app.noticias.model.CalificacionNoticia;

import java.util.Optional;

public interface LikeService {
    Optional<CalificacionNoticia> obtenerCalificacion(Long id_noticia, Long id_usuario);
    CalificacionNoticia cambiarCalificacion(Long id_comentario, Integer valor);
    CalificacionNoticia crearCalificacion(Long id_comentario, Long id_usuario , Integer valor);
    void deleteLike(Long id_like);
}
