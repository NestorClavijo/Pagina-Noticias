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

/*
   Esta clase implementa la interfaz `ComentarioService` y proporciona la lógica de negocio necesaria
   para manejar las operaciones relacionadas con los comentarios, como la creación, actualización,
   obtención y eliminación de comentarios en el sistema.
*/
@Service // Anotación que indica que esta clase es un servicio de Spring
public class ComentarioServiceImpl implements ComentarioService {

    // Inyección de dependencias para interactuar con las entidades y repositorios relacionados
    @Autowired
    private final NoticiaRepository noticiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioRepository comentarioRepository;

    /* Constructor para inyectar los repositorios en la clase */
    public ComentarioServiceImpl(NoticiaRepository noticiaRepository, UsuarioRepository usuarioRepository, ComentarioRepository comentarioRepository){
        this.noticiaRepository = noticiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
    }

    /*
       Este método crea un nuevo comentario para una noticia específica realizado por un usuario.
       Recibe el ID del usuario, el ID de la noticia y la descripción del comentario.
       Devuelve el comentario recién creado.
    */
    @Override
    public Comentario crearComentario(Long id_usuario, Long id_noticia, String descripcion) {
        // Busca al usuario por su ID. Si no se encuentra, lanza una excepción.
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Busca la noticia por su ID. Si no se encuentra, lanza una excepción.
        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crea un nuevo comentario con los datos proporcionados.
        Comentario comentario = Comentario.builder()
                .descripcion(descripcion)
                .fecha_hora(LocalDateTime.now()) // Asigna la fecha y hora actuales al comentario
                .usuario(usuario)
                .noticia(noticia)
                .build();

        // Guarda el comentario en la base de datos y lo devuelve.
        return comentarioRepository.save(comentario);
    }

    /*
       Este método obtiene todos los comentarios asociados a una noticia específica.
       Recibe el ID de la noticia y devuelve una lista de comentarios asociados a esa noticia.
    */
    @Override
    public List<Comentario> comentarioNoticia(Long id_noticia) {
        return comentarioRepository.findByNoticiaId(id_noticia); // Devuelve la lista de comentarios para la noticia dada.
    }

    /*
       Este método actualiza la descripción de un comentario existente.
       Recibe el ID del comentario y la nueva descripción.
       Devuelve el comentario actualizado.
    */
    @Override
    public Comentario actualizarcomentario(Long id_comentario, String descripcion) {
        // Busca el comentario por su ID. Si no se encuentra, lanza una excepción.
        Comentario comentario = comentarioRepository.findById(id_comentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        // Actualiza la descripción y la fecha del comentario.
        comentario.setDescripcion(descripcion);
        comentario.setFecha_hora(LocalDateTime.now()); // Actualiza la fecha y hora al momento de la actualización.

        // Guarda el comentario actualizado y lo devuelve.
        return comentarioRepository.save(comentario);
    }

    /*
       Este método elimina un comentario existente.
       Recibe el ID del comentario a eliminar. Si el comentario no se encuentra, lanza una excepción.
    */
    @Override
    public void deleteComentario(Long id_comentario) {
        // Verifica si el comentario existe antes de intentar eliminarlo.
        if (comentarioRepository.existsById(id_comentario)) {
            comentarioRepository.deleteById(id_comentario); // Elimina el comentario por su ID.
        } else {
            throw new RuntimeException("Comentario no encontrado"); // Lanza una excepción si el comentario no existe.
        }
    }
}
