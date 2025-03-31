package com.app.noticias.ServiceImpl;

import com.app.noticias.Service.NoticiaService;
import com.app.noticias.model.Noticia;
import com.app.noticias.model.Usuario;
import com.app.noticias.repository.NoticiaRepository;
import com.app.noticias.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;
    private UsuarioRepository usuarioRepository;

    @Override
    public Noticia crearNoticia(Long id_usuario, String titulo, String descripcion) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Noticia noticia = Noticia.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .fecha_hora(LocalDateTime.now())
                .usuario(usuario)
                .build();

        return noticiaRepository.save(noticia);
    }

    @Override
    public List<Noticia> noticiasUsuario(Long id_usuario) {
        return noticiaRepository.findByUsuarioId(id_usuario);
    }

    @Override
    public Noticia actualizarNoticia(Long id_noticia, String titulo, String descripcion) {
        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticia.setTitulo(titulo);
        noticia.setDescripcion(descripcion);
        noticia.setFecha_hora(LocalDateTime.now());

        return noticiaRepository.save(noticia);
    }

    @Override
    public void deleteNoticia(Long id_noticia) {
        if (noticiaRepository.existsById(id_noticia)) {
            noticiaRepository.deleteById(id_noticia);
        } else {
            throw new RuntimeException("Noticia no encontrada");
        }
    }
}
