package com.app.noticias.ServiceImpl;

import com.app.noticias.Service.LikeService;
import com.app.noticias.model.*;
import com.app.noticias.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private final CalificacionNoticiaRepository calificacionNoticiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final NoticiaRepository noticiaRepository;

    public LikeServiceImpl(CalificacionNoticiaRepository calificacionNoticiaRepository, UsuarioRepository usuarioRepository, NoticiaRepository noticiaRepository){
       this.calificacionNoticiaRepository = calificacionNoticiaRepository;
       this.usuarioRepository = usuarioRepository;
       this.noticiaRepository = noticiaRepository;
    }

    @Override
    public Optional<CalificacionNoticia> obtenerCalificacion(Long id_noticia, Long id_usuario) {
        return calificacionNoticiaRepository.findByUsuarioIdAndNoticiaId(id_usuario,id_noticia);
    }

    @Override
    public CalificacionNoticia cambiarCalificacion(Long id_calificacion, Integer valor) {
        Optional<CalificacionNoticia> optional = calificacionNoticiaRepository.findById(id_calificacion);
        if (optional.isPresent()) {
            CalificacionNoticia calificacion = optional.get();
            calificacion.setValor(valor);
            return calificacionNoticiaRepository.save(calificacion);
        }
        throw new RuntimeException("Calificación no encontrada");
    }

    @Override
    public CalificacionNoticia crearCalificacion(Long id_noticia, Long id_usuario, Integer valor) {

        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        CalificacionNoticia calificacion = new CalificacionNoticia();
        calificacion.setUsuario(usuario);
        calificacion.setNoticia(noticia);
        calificacion.setValor(valor);
        return calificacionNoticiaRepository.save(calificacion);
    }

    @Override
    public void deleteLike(Long id_like) {
        if (calificacionNoticiaRepository.existsById(id_like)) {
            calificacionNoticiaRepository.deleteById(id_like);
        } else {
            throw new RuntimeException("Calificación no encontrada");
        }
    }
}
