package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.VeterinarioRequest;
import edu.pe.cibertec.dto.VeterinarioResponse;
import edu.pe.cibertec.entity.Veterinario;
import edu.pe.cibertec.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioRestController {

    private final VeterinarioService veterinarioService;

    public VeterinarioRestController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public List<VeterinarioResponse> listar() {
        return veterinarioService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(veterinarioService.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<VeterinarioResponse> crear(@RequestBody VeterinarioRequest request) {
        Veterinario v = new Veterinario();
        aplicarDatos(v, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(veterinarioService.guardar(v)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> actualizar(@PathVariable Integer id, @RequestBody VeterinarioRequest request) {
        Veterinario v = veterinarioService.obtener(id);
        aplicarDatos(v, request);
        return ResponseEntity.ok(toResponse(veterinarioService.guardar(v)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        veterinarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicarDatos(Veterinario v, VeterinarioRequest request) {
        v.setNombre(request.getNombre());
        v.setApellido(request.getApellido());
        v.setDni(request.getDni());
        v.setTelefono(request.getTelefono());
        v.setEspecialidad(request.getEspecialidad());
        v.setFechaContratacion(request.getFechaContratacion());
        v.setSalario(request.getSalario());
        v.setActivo(request.getActivo());
    }

    private VeterinarioResponse toResponse(Veterinario v) {
        return new VeterinarioResponse(v.getIdPersona(), v.getNombre(), v.getApellido(), v.getDni(), v.getTelefono(),
                v.getEspecialidad(), v.getFechaContratacion(), v.getSalario(), v.getActivo());
    }
}