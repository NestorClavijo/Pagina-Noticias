package com.app.noticias.model;

import com.app.noticias.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(name = "nombre",length=255 ,nullable=false)
    private String nombre;

    @Column(name = "apellido",length=255 ,nullable=false)
    private String apellido;

    @Column(name = "email",length=255 ,nullable=false, unique = true)
    private String email;

    @Column(name = "telefono",length=10 ,nullable=false)
    private String telefono;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "puntos_comentario")
    private Float puntos_comentario;

    @Column(name = "puntos_noticia")
    private Float puntos_noticia;

    @Column(name = "username",length=255 ,nullable=false,unique = true)
    private String username;

    @Column(name = "password",length=255 ,nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "Comunidad_Usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_comunidad")
    )
    private Set<Comunidad> comunidades;
}
