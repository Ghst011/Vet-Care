package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.*;
import edu.pe.cibertec.entity.Cita;
import edu.pe.cibertec.entity.Pago;
import edu.pe.cibertec.entity.Veterinario;
import edu.pe.cibertec.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
public class PagoRestController {

    private final PagoService pagoService;

    public PagoRestController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoResponse> listar() {
        return pagoService.listar().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/cita/{idCita}")
    public ResponseEntity<?> obtenerPorCita(@PathVariable Integer idCita) {
        try {
            return ResponseEntity.ok(toResponse(pagoService.obtenerPorCita(idCita)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private PagoResponse toResponse(Pago p) {
        Cita c = p.getCita();

        ClienteResponse clienteResp = new ClienteResponse(
                c.getMascota().getCliente().getIdPersona(), c.getMascota().getCliente().getNombre(),
                c.getMascota().getCliente().getApellido(), c.getMascota().getCliente().getDni(),
                c.getMascota().getCliente().getTelefono(), c.getMascota().getCliente().getActivo()
        );
        EspecieResponse especieResp = new EspecieResponse(c.getMascota().getEspecie().getIdEspecie(), c.getMascota().getEspecie().getNombre());
        MascotaResponse mascotaResp = new MascotaResponse(c.getMascota().getIdMascota(), c.getMascota().getNombre(),
                c.getMascota().getFechaNacimiento(), c.getMascota().getPeso(), c.getMascota().getSexo(), clienteResp, especieResp);

        Veterinario v = c.getVeterinario();
        VeterinarioResponse veterinarioResp = new VeterinarioResponse(v.getIdPersona(), v.getNombre(), v.getApellido(),
                v.getDni(), v.getTelefono(), v.getEspecialidad(), v.getFechaContratacion(), v.getSalario(), v.getActivo());

        List<DetalleCitaResponse> detalles = c.getDetalles().stream()
                .map(d -> new DetalleCitaResponse(
                        new ServicioResponse(d.getServicio().getIdServicio(), d.getServicio().getNombre(),
                                d.getServicio().getDescripcion(), d.getServicio().getPrecio(), d.getServicio().getActivo()),
                        d.getCantidad(), d.getSubtotal()))
                .collect(Collectors.toList());

        CitaResponse citaResp = new CitaResponse(c.getIdCita(), c.getFecha(), c.getHora(), c.getMotivo(),
                c.getEstado().name(), c.getTotal(), mascotaResp, veterinarioResp, detalles);

        return new PagoResponse(p.getIdPago(), p.getFechaPago(), p.getMontoPagado(), p.getRegistradoPor(), citaResp);
    }
}