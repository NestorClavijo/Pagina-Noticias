package com.app.noticias.Service;

import com.app.noticias.DTOsecurity.AuthResponse;
import com.app.noticias.DTOsecurity.LoginRequest;
import com.app.noticias.DTOsecurity.RegisterRequest;
import com.app.noticias.config.JwtService;
import com.app.noticias.enums.Role;
import com.app.noticias.model.Usuario;
import com.app.noticias.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
   Este servicio maneja las operaciones de autenticación, tanto para el inicio de sesión como para el registro de nuevos usuarios.
   Utiliza el `JwtService` para generar tokens JWT y el `UsuarioRepository` para interactuar con la base de datos.
*/
@Service // Anotación que indica que esta clase es un servicio de Spring
@RequiredArgsConstructor // Lombok genera automáticamente un constructor con las dependencias necesarias
public class AuthService {

    /*
       Dependencias inyectadas mediante el constructor.
       Estas son necesarias para el manejo de usuarios, generación de tokens y autenticación.
    */
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /*
       Este método maneja el inicio de sesión (login) de un usuario.
       Se autentica al usuario, genera un token JWT y devuelve una respuesta con el token y el ID del usuario.
    */
    public AuthResponse login(LoginRequest request) {
        // Autentica al usuario usando el AuthenticationManager de Spring Security.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Recupera al usuario desde el repositorio utilizando el nombre de usuario proporcionado.
        UserDetails usuario = (UserDetails) usuarioRepository.findByUsername(request.getUsername()).orElseThrow();

        // Genera un token JWT para el usuario autenticado.
        String token = jwtService.getToken(usuario);

        // Obtiene el ID del usuario y devuelve una respuesta con el token y el ID.
        long id = ((Usuario) usuario).getId_usuario();

        return AuthResponse.builder()
                .token(token)
                .id(id)
                .build();
    }

    /*
       Este método maneja el registro de un nuevo usuario.
       Crea un nuevo usuario con los datos proporcionados, encripta la contraseña,
       genera un token JWT y devuelve una respuesta con el token y el ID del nuevo usuario.
    */
    public AuthResponse register(RegisterRequest request) {
        // Crea un nuevo objeto Usuario con los datos proporcionados en el request.
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña
                .apellido(request.getApellido())
                .descripcion(request.getDescripcion())
                .email(request.getEmail())
                .nombre(request.getNombre())
                .puntos_comentario((float) 0.0) // Asigna puntos iniciales a los comentarios
                .puntos_noticia((float) 0.0) // Asigna puntos iniciales a las noticias
                .telefono(request.getTelefono())
                .role(Role.CLIENTE) // Asigna el rol de CLIENTE al nuevo usuario
                .build();

        // Guarda el nuevo usuario en la base de datos.
        usuarioRepository.save(usuario);

        // Obtiene el ID del nuevo usuario.
        long id = usuario.getId_usuario();

        // Genera un token JWT para el nuevo usuario.
        return AuthResponse.builder()
                .token(jwtService.getToken((UserDetails) usuario))
                .id(id)
                .build();
    }
}
