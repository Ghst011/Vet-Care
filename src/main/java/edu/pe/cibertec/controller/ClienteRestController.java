package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.ClienteRequest;
import edu.pe.cibertec.dto.ClienteResponse;
import edu.pe.cibertec.entity.Cliente;
import edu.pe.cibertec.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(clienteService.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente();
        aplicarDatos(cliente, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(clienteService.guardar(cliente)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable Integer id, @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.obtener(id);
        aplicarDatos(cliente, request);
        return ResponseEntity.ok(toResponse(clienteService.guardar(cliente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicarDatos(Cliente cliente, ClienteRequest request) {
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        cliente.setActivo(request.getActivo());
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getIdPersona(), c.getNombre(), c.getApellido(), c.getDni(), c.getTelefono(), c.getActivo());
    }
}