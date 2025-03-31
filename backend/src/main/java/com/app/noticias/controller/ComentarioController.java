package com.app.noticias.controller;

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

    @PostMapping("/crear")
    public ResponseEntity<Comentario> create(
            @RequestParam Long usuarioId,
            @RequestParam Long noticiaId,
            @RequestParam String descripcion) {

        return ResponseEntity.ok(comentarioService.crearComentario(usuarioId,noticiaId,descripcion));
    }

    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<List<Comentario>> getComentariosByNoticia(@PathVariable Long noticiaId) {
        return ResponseEntity.ok(comentarioService.comentarioNoticia(noticiaId));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Comentario> update(
            @PathVariable Long id,
            @RequestParam String descripcion) {

        return ResponseEntity.ok(comentarioService.actualizarcomentario(id,descripcion));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }
}
