package com.app.noticias.ServiceImpl;

import com.app.noticias.Service.NoticiaService;
import com.app.noticias.model.Noticia;
import com.app.noticias.model.Usuario;
import com.app.noticias.repository.NoticiaRepository;
import com.app.noticias.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
   Esta clase implementa la interfaz `NoticiaService` y proporciona la lógica de negocio necesaria
   para manejar las operaciones relacionadas con las noticias, como la creación, actualización,
   eliminación y obtención de noticias en el sistema.
*/
@Service // Anotación que indica que esta clase es un servicio de Spring
public class NoticiaServiceImpl implements NoticiaService {

    // Inyección de dependencias necesarias para interactuar con los repositorios relacionados con noticias y usuarios.
    @Autowired
    private final NoticiaRepository noticiaRepository;
    private final UsuarioRepository usuarioRepository;

    /* Constructor para inyectar los repositorios en la clase */
    public NoticiaServiceImpl(NoticiaRepository noticiaRepository, UsuarioRepository usuarioRepository){
        this.noticiaRepository = noticiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /*
       Este método crea una nueva noticia.
       Recibe el ID del usuario que está creando la noticia, el título y la descripción de la noticia.
       Devuelve la noticia recién creada.
    */
    @Override
    public Noticia crearNoticia(Long id_usuario, String titulo, String descripcion) {
        // Busca al usuario por su ID. Si no se encuentra, lanza una excepción.
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crea una nueva noticia con los datos proporcionados.
        Noticia noticia = Noticia.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .fecha_hora(LocalDateTime.now()) // Asigna la fecha y hora actuales a la noticia
                .usuario(usuario) // Asocia la noticia al usuario que la está creando
                .build();

        // Guarda la noticia en la base de datos y la devuelve.
        return noticiaRepository.save(noticia);
    }

    /*
       Este método obtiene todas las noticias asociadas a un usuario específico.
       Recibe el ID del usuario y devuelve una lista de noticias publicadas por ese usuario.
    */
    @Override
    public List<Noticia> noticiasUsuario(Long id_usuario) {
        return noticiaRepository.findByUsuarioId(id_usuario); // Devuelve las noticias asociadas al usuario.
    }

    /*
       Este método actualiza una noticia existente.
       Recibe el ID de la noticia a actualizar, el nuevo título y la nueva descripción.
       Devuelve la noticia actualizada.
    */
    @Override
    public Noticia actualizarNoticia(Long id_noticia, String titulo, String descripcion) {
        // Busca la noticia por su ID. Si no se encuentra, lanza una excepción.
        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        // Actualiza el título, la descripción y la fecha de la noticia.
        noticia.setTitulo(titulo);
        noticia.setDescripcion(descripcion);
        noticia.setFecha_hora(LocalDateTime.now()); // Actualiza la fecha y hora al momento de la actualización.

        // Guarda la noticia actualizada y la devuelve.
        return noticiaRepository.save(noticia);
    }

    /*
       Este método elimina una noticia existente.
       Recibe el ID de la noticia a eliminar. Si la noticia no se encuentra, lanza una excepción.
    */
    @Override
    public void deleteNoticia(Long id_noticia) {
        // Verifica si la noticia existe antes de intentar eliminarla.
        if (noticiaRepository.existsById(id_noticia)) {
            noticiaRepository.deleteById(id_noticia); // Elimina la noticia por su ID.
        } else {
            throw new RuntimeException("Noticia no encontrada"); // Lanza una excepción si la noticia no existe.
        }
    }
}
