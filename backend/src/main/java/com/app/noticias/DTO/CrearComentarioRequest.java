package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearComentarioRequest {
    private Long usuarioId;
    private Long noticiaId;
    private String descripcion;
}
