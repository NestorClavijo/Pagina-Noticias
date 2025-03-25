package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comunidad")
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comunidad;

    @Column(name = "titulo",length=255 ,nullable=false)
    private String titulo;

    @Column(name = "tema",length=255 ,nullable=false)
    private String tema;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "comunidad_noticia",
            joinColumns = @JoinColumn(name = "id_comunidad"),
            inverseJoinColumns = @JoinColumn(name = "id_noticia")
    )
    private Set<Noticia> noticias;
}
