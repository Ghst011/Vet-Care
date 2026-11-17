package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.EspecieRequest;
import edu.pe.cibertec.dto.EspecieResponse;
import edu.pe.cibertec.entity.Especie;
import edu.pe.cibertec.service.EspecieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especies")
public class EspecieRestController {

    private final EspecieService especieService;

    public EspecieRestController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @GetMapping
    public List<EspecieResponse> listar() {
        return especieService.listar().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(especieService.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<EspecieResponse> crear(@RequestBody EspecieRequest request) {
        Especie especie = new Especie();
        especie.setNombre(request.getNombre());
        Especie guardada = especieService.guardar(especie);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponse> actualizar(@PathVariable Integer id, @RequestBody EspecieRequest request) {
        Especie especie = especieService.obtener(id);
        especie.setNombre(request.getNombre());
        Especie actualizada = especieService.guardar(especie);
        return ResponseEntity.ok(toResponse(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        especieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EspecieResponse toResponse(Especie e) {
        return new EspecieResponse(e.getIdEspecie(), e.getNombre());
    }
}