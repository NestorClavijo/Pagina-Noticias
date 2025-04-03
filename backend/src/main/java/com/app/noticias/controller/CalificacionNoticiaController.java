package com.app.noticias.controller;

import com.app.noticias.Service.LikeService;
import com.app.noticias.model.CalificacionNoticia;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/calificacionNoticia/")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class CalificacionNoticiaController {

    @Autowired
    private LikeService likeService;

    @CrossOrigin(origins="*")
    @PostMapping("/crear")
    public ResponseEntity<CalificacionNoticia> create(@RequestParam Long usuarioId, @RequestParam Long comentarioId, @RequestParam Integer valor) {
        return ResponseEntity.ok(likeService.crearCalificacion(usuarioId, comentarioId, valor));
    }

    @CrossOrigin(origins="*")
    @GetMapping("/buscar")
    public ResponseEntity<CalificacionNoticia> read(@RequestParam Long usuarioId, @RequestParam Long comentarioId) {
        Optional<CalificacionNoticia> calificacion = likeService.obtenerCalificacion(usuarioId, comentarioId);
        return calificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins="*")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CalificacionNoticia> update(@PathVariable Long id, @RequestParam Integer nuevoValor) {
        return ResponseEntity.ok(likeService.cambiarCalificacion(id, nuevoValor));
    }

    @CrossOrigin(origins="*")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }
}
