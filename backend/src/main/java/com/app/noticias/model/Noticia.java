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
@Table(name = "noticia")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_noticia;

    @Column(name = "titulo",length=255 ,nullable=false)
    private String titulo;

    @Column(name = "texto_noticia",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_hora",nullable = false)
    private LocalDateTime fecha_hora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
