package com.app.noticias.repository;

import com.app.noticias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
   Este es el repositorio para la entidad `Usuario`. Extiende `JpaRepository`,
   lo que permite acceder a los métodos CRUD estándar (crear, leer, actualizar, eliminar)
   sin necesidad de implementar estas operaciones manualmente.
*/
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /*
       Este método personalizado busca un usuario por su nombre de usuario (username).
       Devuelve un `Optional<Usuario>`, lo que significa que podría no encontrar un usuario con el nombre proporcionado.
    */
    Optional<Usuario> findByUsername(String username); // Método para encontrar un usuario por su nombre de usuario.
}
