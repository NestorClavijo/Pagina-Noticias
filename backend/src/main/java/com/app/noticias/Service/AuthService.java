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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails usuario= (UserDetails) usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(usuario);
        long id= ((Usuario) usuario).getId_usuario();
        return AuthResponse.builder()
                .token(token)
                .id(id)
                .build();
    }

    public AuthResponse register(RegisterRequest request){
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .apellido(request.getApellido())
                .descripcion(request.getDescripcion())
                .email(request.getEmail())
                .nombre(request.getNombre())
                .puntos_comentario((float) 0.0)
                .puntos_noticia((float) 0.0)
                .telefono(request.getTelefono())
                .role(Role.CLIENTE)
                .build();

        usuarioRepository.save(usuario);
        long id= usuario.getId_usuario();

        return AuthResponse.builder()
                .token(jwtService.getToken((UserDetails) usuario))
                .id(id)
                .build();

    }
}
