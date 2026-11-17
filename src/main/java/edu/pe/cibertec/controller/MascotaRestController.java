package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.ClienteResponse;
import edu.pe.cibertec.dto.EspecieResponse;
import edu.pe.cibertec.dto.MascotaRequest;
import edu.pe.cibertec.dto.MascotaResponse;
import edu.pe.cibertec.entity.Cliente;
import edu.pe.cibertec.entity.Especie;
import edu.pe.cibertec.entity.Mascota;
import edu.pe.cibertec.service.ClienteService;
import edu.pe.cibertec.service.EspecieService;
import edu.pe.cibertec.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaRestController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;
    private final EspecieService especieService;

    public MascotaRestController(MascotaService mascotaService, ClienteService clienteService, EspecieService especieService) {
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
        this.especieService = especieService;
    }

    @GetMapping
    public List<MascotaResponse> listar() {
        return mascotaService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(mascotaService.obtener(id)));
    }

    @GetMapping("/cliente/{idCliente}")
    public List<MascotaResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return mascotaService.listarPorCliente(idCliente).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MascotaRequest request) {
        try {
            Mascota m = new Mascota();
            aplicarDatos(m, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(mascotaService.guardar(m)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody MascotaRequest request) {
        try {
            Mascota m = mascotaService.obtener(id);
            aplicarDatos(m, request);
            return ResponseEntity.ok(toResponse(mascotaService.guardar(m)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicarDatos(Mascota m, MascotaRequest request) {
        Cliente cliente = clienteService.obtener(request.getIdCliente());
        Especie especie = especieService.obtener(request.getIdEspecie());

        m.setNombre(request.getNombre());
        m.setFechaNacimiento(request.getFechaNacimiento());
        m.setPeso(request.getPeso());
        m.setSexo(request.getSexo());
        m.setCliente(cliente);
        m.setEspecie(especie);
    }

    private MascotaResponse toResponse(Mascota m) {
        ClienteResponse clienteResp = new ClienteResponse(
                m.getCliente().getIdPersona(), m.getCliente().getNombre(), m.getCliente().getApellido(),
                m.getCliente().getDni(), m.getCliente().getTelefono(), m.getCliente().getActivo()
        );
        EspecieResponse especieResp = new EspecieResponse(m.getEspecie().getIdEspecie(), m.getEspecie().getNombre());

        return new MascotaResponse(m.getIdMascota(), m.getNombre(), m.getFechaNacimiento(), m.getPeso(), m.getSexo(),
                clienteResp, especieResp);
    }
}