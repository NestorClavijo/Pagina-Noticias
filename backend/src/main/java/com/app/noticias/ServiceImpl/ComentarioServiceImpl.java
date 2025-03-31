package com.app.noticias.ServiceImpl;

import com.app.noticias.Service.ComentarioService;
import com.app.noticias.model.Comentario;
import com.app.noticias.model.Noticia;
import com.app.noticias.model.Usuario;
import com.app.noticias.repository.ComentarioRepository;
import com.app.noticias.repository.NoticiaRepository;
import com.app.noticias.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private NoticiaRepository noticiaRepository;
    private UsuarioRepository usuarioRepository;
    private ComentarioRepository comentarioRepository;

    @Override
    public Comentario crearComentario(Long id_usuario, Long id_noticia, String descripcion) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comentario comentario = Comentario.builder()
                .descripcion(descripcion)
                .fecha_hora(LocalDateTime.now())
                .usuario(usuario)
                .noticia(noticia)
                .build();

        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> comentarioNoticia(Long id_noticia) {
        return comentarioRepository.findByNoticiaId(id_noticia);
    }

    @Override
    public Comentario actualizarcomentario(Long id_comentario, String descripcion) {
        Comentario comentario = comentarioRepository.findById(id_comentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        comentario.setDescripcion(descripcion);
        comentario.setFecha_hora(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }

    @Override
    public void deleteComentario(Long id_comentario) {
        if (comentarioRepository.existsById(id_comentario)) {
            comentarioRepository.deleteById(id_comentario);
        } else {
            throw new RuntimeException("Comentario no encontrado");
        }
    }
}
