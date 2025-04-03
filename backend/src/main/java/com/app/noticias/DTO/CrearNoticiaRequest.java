package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearNoticiaRequest {
    private Long usuarioId;
    private String titulo;
    private String descripcion;
}
