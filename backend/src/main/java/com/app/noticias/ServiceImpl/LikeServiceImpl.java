package com.app.noticias.ServiceImpl;

import com.app.noticias.Service.LikeService;
import com.app.noticias.model.*;
import com.app.noticias.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
   Esta clase implementa la interfaz `LikeService` y proporciona la lógica de negocio para manejar las calificaciones
   de noticias por parte de los usuarios, como la creación, actualización, eliminación y obtención de calificaciones.
*/
@Service // Anotación que indica que esta clase es un servicio de Spring
public class LikeServiceImpl implements LikeService {

    // Inyección de dependencias para interactuar con los repositorios relacionados con las calificaciones y las entidades.
    @Autowired
    private final CalificacionNoticiaRepository calificacionNoticiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final NoticiaRepository noticiaRepository;

    /* Constructor para inyectar los repositorios en la clase */
    public LikeServiceImpl(CalificacionNoticiaRepository calificacionNoticiaRepository, UsuarioRepository usuarioRepository, NoticiaRepository noticiaRepository){
        this.calificacionNoticiaRepository = calificacionNoticiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.noticiaRepository = noticiaRepository;
    }

    /*
       Este método obtiene la calificación de una noticia por parte de un usuario específico.
       Recibe el ID de la noticia y el ID del usuario, y devuelve un `Optional<CalificacionNoticia>`,
       que puede estar vacío si no existe una calificación para esa noticia por parte de ese usuario.
    */
    @Override
    public Optional<CalificacionNoticia> obtenerCalificacion(Long id_noticia, Long id_usuario) {
        return calificacionNoticiaRepository.findByUsuarioIdAndNoticiaId(id_usuario, id_noticia); // Llama al repositorio para obtener la calificación.
    }

    /*
       Este método cambia el valor de una calificación existente.
       Recibe el ID de la calificación y el nuevo valor.
       Si la calificación existe, la actualiza y la guarda de nuevo.
    */
    @Override
    public CalificacionNoticia cambiarCalificacion(Long id_calificacion, Integer valor) {
        Optional<CalificacionNoticia> optional = calificacionNoticiaRepository.findById(id_calificacion);
        if (optional.isPresent()) {
            CalificacionNoticia calificacion = optional.get(); // Recupera la calificación existente.
            calificacion.setValor(valor); // Actualiza el valor de la calificación.
            return calificacionNoticiaRepository.save(calificacion); // Guarda la calificación actualizada.
        }
        throw new RuntimeException("Calificación no encontrada"); // Si no se encuentra la calificación, lanza una excepción.
    }

    /*
       Este método crea una nueva calificación para una noticia por parte de un usuario.
       Recibe el ID de la noticia, el ID del usuario y el valor de la calificación.
       Devuelve la calificación recién creada.
    */
    @Override
    public CalificacionNoticia crearCalificacion(Long id_noticia, Long id_usuario, Integer valor) {
        // Busca al usuario por su ID. Si no se encuentra, lanza una excepción.
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Busca la noticia por su ID. Si no se encuentra, lanza una excepción.
        Noticia noticia = noticiaRepository.findById(id_noticia)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        // Crea una nueva calificación con los datos proporcionados.
        CalificacionNoticia calificacion = new CalificacionNoticia();
        calificacion.setUsuario(usuario); // Asigna el usuario que califica.
        calificacion.setNoticia(noticia); // Asigna la noticia que está siendo calificada.
        calificacion.setValor(valor); // Asigna el valor de la calificación.

        // Guarda la calificación en la base de datos y la devuelve.
        return calificacionNoticiaRepository.save(calificacion);
    }

    /*
       Este método elimina una calificación existente.
       Recibe el ID de la calificación a eliminar. Si la calificación no se encuentra, lanza una excepción.
    */
    @Override
    public void deleteLike(Long id_like) {
        // Verifica si la calificación existe antes de intentar eliminarla.
        if (calificacionNoticiaRepository.existsById(id_like)) {
            calificacionNoticiaRepository.deleteById(id_like); // Elimina la calificación por su ID.
        } else {
            throw new RuntimeException("Calificación no encontrada"); // Lanza una excepción si la calificación no existe.
        }
    }
}
