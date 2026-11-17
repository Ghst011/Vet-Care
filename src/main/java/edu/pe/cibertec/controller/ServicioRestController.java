package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.ServicioRequest;
import edu.pe.cibertec.dto.ServicioResponse;
import edu.pe.cibertec.entity.Servicio;
import edu.pe.cibertec.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
public class ServicioRestController {

    private final ServicioService servicioService;

    public ServicioRestController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<ServicioResponse> listar() {
        return servicioService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(servicioService.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ServicioRequest request) {
        try {
            Servicio s = new Servicio();
            aplicarDatos(s, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(servicioService.guardar(s)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // regla #8: precio > 0
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ServicioRequest request) {
        try {
            Servicio s = servicioService.obtener(id);
            aplicarDatos(s, request);
            return ResponseEntity.ok(toResponse(servicioService.guardar(s)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicarDatos(Servicio s, ServicioRequest request) {
        s.setNombre(request.getNombre());
        s.setDescripcion(request.getDescripcion());
        s.setPrecio(request.getPrecio());
        s.setActivo(request.getActivo());
    }

    private ServicioResponse toResponse(Servicio s) {
        return new ServicioResponse(s.getIdServicio(), s.getNombre(), s.getDescripcion(), s.getPrecio(), s.getActivo());
    }
}