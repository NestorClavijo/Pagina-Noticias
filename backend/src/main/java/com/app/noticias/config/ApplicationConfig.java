package com.app.noticias.config;

import com.app.noticias.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* Esta es una clase de configuración que gestiona los aspectos relacionados con la seguridad,
 la autenticación de usuarios, la codificación de contraseñas y la serialización/deserialización de objetos.
 La anotación @Configuration indica que esta clase contiene beans de configuración para Spring. */
@Configuration
@RequiredArgsConstructor // Lombok genera automáticamente un constructor con los parámetros finales necesarios
public class ApplicationConfig {

    // Inyección de dependencia del repositorio de usuarios para acceder a la base de datos
    private final UsuarioRepository usuarioRepository;

    /* Este bean configura el AuthenticationManager, que es responsable de manejar la autenticación de los usuarios.
     AuthenticationManager se obtiene de la configuración de Spring Security. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Obtiene el AuthenticationManager de la configuración
    }

    /* Este bean configura un AuthenticationProvider que usa DaoAuthenticationProvider para la autenticación con base de datos.
     El AuthenticationProvider se encarga de verificar las credenciales del usuario. */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Se crea una instancia de DaoAuthenticationProvider, que se utiliza para la autenticación con base de datos
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService()); // Establece el servicio que carga los detalles del usuario
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas
        return authenticationProvider; // Devuelve el AuthenticationProvider configurado
    }

    /* Este bean configura el UserDetailsService que se usa para cargar los detalles del usuario durante el proceso de autenticación.
     El método busca el usuario en la base de datos y lanza una excepción si no lo encuentra. */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> (org.springframework.security.core.userdetails.UserDetails)
                usuarioRepository.findByUsername(username) // Busca el usuario en la base de datos
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); // Lanza excepción si no se encuentra el usuario
    }

    /* Este bean configura el PasswordEncoder, que se utiliza para codificar y verificar las contraseñas.
     El BCryptPasswordEncoder es un algoritmo seguro para la codificación de contraseñas. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Devuelve un codificador BCrypt
    }

    /* Este bean configura el ObjectMapper que se utiliza para convertir objetos Java a JSON y viceversa.
     Además, se registra un módulo JavaTimeModule para manejar las fechas y horas de Java 8. */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper(); // Crea una nueva instancia de ObjectMapper
        mapper.registerModule(new JavaTimeModule()); // Registra el módulo JavaTime para manejar fechas
        return mapper; // Devuelve el ObjectMapper configurado
    }
}

