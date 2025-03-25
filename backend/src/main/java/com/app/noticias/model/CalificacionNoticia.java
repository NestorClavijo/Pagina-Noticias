package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calificacion_noticia")
public class CalificacionNoticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_calificacion;

    @Column(name = "valor",nullable=false)
    private Integer valor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "noticia_id")
    private Noticia noticia;
}
