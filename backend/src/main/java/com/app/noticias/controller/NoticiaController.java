package com.app.noticias.controller;

import com.app.noticias.Service.NoticiaService;
import com.app.noticias.model.Noticia;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Noticia/")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @PostMapping("/crear")
    public ResponseEntity<Noticia> create(
            @RequestParam Long usuarioId,
            @RequestParam String titulo,
            @RequestParam String descripcion) {

        return ResponseEntity.ok(noticiaService.crearNoticia(usuarioId, titulo, descripcion));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Noticia>> getNoticiasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(noticiaService.noticiasUsuario(usuarioId));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Noticia> update(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String descripcion) {

        return ResponseEntity.ok(noticiaService.actualizarNoticia(id, titulo, descripcion));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticiaService.deleteNoticia(id);
        return ResponseEntity.noContent().build();
    }
}
