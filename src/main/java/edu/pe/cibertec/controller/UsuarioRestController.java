package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.UsuarioRequest;
import edu.pe.cibertec.dto.UsuarioResponse;
import edu.pe.cibertec.entity.Usuario;
import edu.pe.cibertec.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioRequest request) {
        try {
            Usuario u = new Usuario();
            u.setUsername(request.getUsername());
            u.setRol(request.getRol());
            u.setEstado(request.getEstado());
            Usuario guardado = usuarioService.guardar(u, request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        try {
            Usuario u = usuarioService.obtener(id);
            u.setUsername(request.getUsername());
            u.setRol(request.getRol());
            u.setEstado(request.getEstado());
            Usuario actualizado = usuarioService.guardar(u, request.getPassword());
            return ResponseEntity.ok(toResponse(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getUsername(), u.getRol(), u.getEstado());
    }
}