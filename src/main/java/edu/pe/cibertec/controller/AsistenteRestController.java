package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.AsistenteRequest;
import edu.pe.cibertec.dto.AsistenteResponse;
import edu.pe.cibertec.entity.Asistente;
import edu.pe.cibertec.service.AsistenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asistentes")
public class AsistenteRestController {

    private final AsistenteService asistenteService;

    public AsistenteRestController(AsistenteService asistenteService) {
        this.asistenteService = asistenteService;
    }

    @GetMapping
    public List<AsistenteResponse> listar() {
        return asistenteService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenteResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(asistenteService.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<AsistenteResponse> crear(@RequestBody AsistenteRequest request) {
        Asistente a = new Asistente();
        aplicarDatos(a, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(asistenteService.guardar(a)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsistenteResponse> actualizar(@PathVariable Integer id, @RequestBody AsistenteRequest request) {
        Asistente a = asistenteService.obtener(id);
        aplicarDatos(a, request);
        return ResponseEntity.ok(toResponse(asistenteService.guardar(a)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        asistenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicarDatos(Asistente a, AsistenteRequest request) {
        a.setNombre(request.getNombre());
        a.setApellido(request.getApellido());
        a.setDni(request.getDni());
        a.setTelefono(request.getTelefono());
        a.setFechaContratacion(request.getFechaContratacion());
        a.setSalario(request.getSalario());
        a.setActivo(request.getActivo());
    }

    private AsistenteResponse toResponse(Asistente a) {
        return new AsistenteResponse(a.getIdPersona(), a.getNombre(), a.getApellido(), a.getDni(), a.getTelefono(),
                a.getFechaContratacion(), a.getSalario(), a.getActivo());
    }
}