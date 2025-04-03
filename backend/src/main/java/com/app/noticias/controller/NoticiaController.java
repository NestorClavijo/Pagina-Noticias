package com.app.noticias.controller;

import com.app.noticias.DTO.CrearNoticiaRequest;
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

    @CrossOrigin(origins="*")
    @PostMapping("/crear")
    public ResponseEntity<Noticia> create(@RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.crearNoticia(
                noticiaRequest.getUsuarioId(),
                noticiaRequest.getTitulo(),
                noticiaRequest.getDescripcion()));
    }


    @CrossOrigin(origins="*")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Noticia>> getNoticiasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(noticiaService.noticiasUsuario(usuarioId));
    }

    @CrossOrigin(origins="*")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Noticia> update(
            @PathVariable Long id,
            @RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.actualizarNoticia(id, noticiaRequest.getTitulo(), noticiaRequest.getDescripcion()));
    }

    @CrossOrigin(origins="*")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticiaService.deleteNoticia(id);
        return ResponseEntity.noContent().build();
    }
}
