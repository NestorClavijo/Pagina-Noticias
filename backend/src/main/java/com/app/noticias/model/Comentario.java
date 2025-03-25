package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comentario;

    @Column(name = "texto_comentario",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_hora",nullable = false)
    private LocalDateTime fecha_hora;

    @ManyToOne
    @JoinColumn(name = "noticia_id")
    private Noticia noticia;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
