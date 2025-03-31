package com.app.noticias.Service;

import com.app.noticias.model.Noticia;

import java.util.List;

public interface NoticiaService {
    Noticia crearNoticia(Long id_usuario, String titulo, String descripcion);
    List<Noticia> noticiasUsuario(Long id_usuario);
    Noticia actualizarNoticia(Long id_noticia, String titulo, String descripcion);
    void deleteNoticia(Long id_noticia);
}
