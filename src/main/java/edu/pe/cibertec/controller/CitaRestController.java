package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.CitaResponse;
import edu.pe.cibertec.dto.ClienteResponse;
import edu.pe.cibertec.dto.CrearCitaRequest;
import edu.pe.cibertec.dto.DetalleCitaResponse;
import edu.pe.cibertec.dto.EspecieResponse;
import edu.pe.cibertec.dto.MascotaResponse;
import edu.pe.cibertec.dto.ServicioResponse;
import edu.pe.cibertec.dto.VeterinarioResponse;
import edu.pe.cibertec.entity.Cita;
import edu.pe.cibertec.entity.EstadoCita;
import edu.pe.cibertec.entity.Veterinario;
import edu.pe.cibertec.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class CitaRestController {

    private final CitaService citaService;

    public CitaRestController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<CitaResponse> listar() {
        return citaService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(citaService.obtener(id)));
    }

    @GetMapping("/mascota/{idMascota}")
    public List<CitaResponse> listarPorMascota(@PathVariable Integer idMascota) {
        return citaService.listarPorMascota(idMascota).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearCitaRequest request) {
        try {
            Cita cita = citaService.registrarCita(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(cita));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoCita nuevoEstado,
                                            Authentication authentication) {
        try {
            boolean esAdmin = tieneRol(authentication, "ADMIN");
            boolean esVeterinario = tieneRol(authentication, "VETERINARIO");
            boolean esAsistente = tieneRol(authentication, "ASISTENTE");

            Cita cita;
            if (nuevoEstado == EstadoCita.PAGADA) {
                if (!esAdmin && !esAsistente) throw new RuntimeException("Solo un asistente puede registrar el pago");
                cita = citaService.registrarPago(id, authentication.getName());
            } else {
                if (!esAdmin && !esVeterinario) throw new RuntimeException("Solo un veterinario puede actualizar el estado de atención");
                cita = citaService.cambiarEstado(id, nuevoEstado);
            }
            return ResponseEntity.ok(toResponse(cita));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean tieneRol(Authentication authentication, String rol) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + rol));
    }

    private CitaResponse toResponse(Cita c) {
        ClienteResponse clienteResp = new ClienteResponse(
                c.getMascota().getCliente().getIdPersona(), c.getMascota().getCliente().getNombre(),
                c.getMascota().getCliente().getApellido(), c.getMascota().getCliente().getDni(),
                c.getMascota().getCliente().getTelefono(), c.getMascota().getCliente().getActivo()
        );
        EspecieResponse especieResp = new EspecieResponse(
                c.getMascota().getEspecie().getIdEspecie(), c.getMascota().getEspecie().getNombre()
        );
        MascotaResponse mascotaResp = new MascotaResponse(
                c.getMascota().getIdMascota(), c.getMascota().getNombre(), c.getMascota().getFechaNacimiento(),
                c.getMascota().getPeso(), c.getMascota().getSexo(), clienteResp, especieResp
        );

        Veterinario v = c.getVeterinario();
        VeterinarioResponse veterinarioResp = new VeterinarioResponse(
                v.getIdPersona(), v.getNombre(), v.getApellido(), v.getDni(), v.getTelefono(),
                v.getEspecialidad(), v.getFechaContratacion(), v.getSalario(), v.getActivo()
        );

        List<DetalleCitaResponse> detalles = c.getDetalles().stream()
                .map(d -> new DetalleCitaResponse(
                        new ServicioResponse(d.getServicio().getIdServicio(), d.getServicio().getNombre(),
                                d.getServicio().getDescripcion(), d.getServicio().getPrecio(), d.getServicio().getActivo()),
                        d.getCantidad(), d.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new CitaResponse(c.getIdCita(), c.getFecha(), c.getHora(), c.getMotivo(), c.getEstado().name(), c.getTotal(),
                mascotaResp, veterinarioResp, detalles);
    }
}