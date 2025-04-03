package com.app.noticias.controller;

import com.app.noticias.DTO.CrearComentarioRequest;
import com.app.noticias.Service.ComentarioService;
import com.app.noticias.model.Comentario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Comentario/")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @CrossOrigin(origins="*")
    @PostMapping("/crear")
    public ResponseEntity<Comentario> create(@RequestBody CrearComentarioRequest comentarioRequest) {

        return ResponseEntity.ok(comentarioService.crearComentario(
                comentarioRequest.getUsuarioId(),
                comentarioRequest.getNoticiaId(),
                comentarioRequest.getDescripcion()));
    }

    @CrossOrigin(origins="*")
    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<List<Comentario>> getComentariosByNoticia(@PathVariable Long noticiaId) {
        return ResponseEntity.ok(comentarioService.comentarioNoticia(noticiaId));
    }

    @CrossOrigin(origins="*")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Comentario> update(
            @PathVariable Long id,
            @RequestBody CrearComentarioRequest comentarioRequest) {

        return ResponseEntity.ok(comentarioService.actualizarcomentario(id,comentarioRequest.getDescripcion()));
    }

    @CrossOrigin(origins="*")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }
}
