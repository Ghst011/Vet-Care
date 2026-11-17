package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.CrearCitaRequest;
import edu.pe.cibertec.dto.DetalleCitaRequest;
import edu.pe.cibertec.entity.EstadoCita;
import edu.pe.cibertec.service.CitaService;
import edu.pe.cibertec.service.MascotaService;
import edu.pe.cibertec.service.ServicioService;
import edu.pe.cibertec.service.VeterinarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final MascotaService mascotaService;
    private final VeterinarioService veterinarioService;
    private final ServicioService servicioService;

    public CitaController(CitaService citaService, MascotaService mascotaService,
                           VeterinarioService veterinarioService, ServicioService servicioService) {
        this.citaService = citaService;
        this.mascotaService = mascotaService;
        this.veterinarioService = veterinarioService;
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "citas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        cargarListasFormulario(model);
        return "citas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Integer idMascota,
                           @RequestParam Integer idVeterinario,
                           @RequestParam LocalDate fecha,
                           @RequestParam LocalTime hora,
                           @RequestParam String motivo,
                           @RequestParam List<Integer> idsServicio,
                           @RequestParam List<Integer> cantidades,
                           Model model) {
        try {
            // Armamos el DTO a partir de los datos sueltos del formulario
            CrearCitaRequest request = new CrearCitaRequest();
            request.setIdMascota(idMascota);
            request.setIdVeterinario(idVeterinario);
            request.setFecha(fecha);
            request.setHora(hora);
            request.setMotivo(motivo);

            List<DetalleCitaRequest> detalles = new ArrayList<>();
            for (int i = 0; i < idsServicio.size(); i++) {
                Integer cantidad = (cantidades.size() > i) ? cantidades.get(i) : 0;
                detalles.add(new DetalleCitaRequest(idsServicio.get(i), cantidad));
            }
            request.setDetalles(detalles);

            citaService.registrarCita(request);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            cargarListasFormulario(model);
            return "citas/formulario";
        }
        return "redirect:/citas";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        model.addAttribute("cita", citaService.obtener(id));
        return "citas/detalle";
    }

    @PostMapping("/avanzar/{id}")
    public String avanzarEstado(@PathVariable Integer id, @RequestParam EstadoCita nuevoEstado,
                                 Authentication authentication, Model model) {
        boolean esAdmin = tieneRol(authentication, "ADMIN");
        boolean esVeterinario = tieneRol(authentication, "VETERINARIO");
        boolean esAsistente = tieneRol(authentication, "ASISTENTE");

        try {
        	if (nuevoEstado == EstadoCita.PAGADA) {
        	    if (!esAdmin && !esAsistente) {
        	        throw new RuntimeException("Solo un asistente puede registrar el pago");
        	    }
        	    citaService.registrarPago(id, authentication.getName());
        	} else {
        	    if (!esAdmin && !esVeterinario) {
        	        throw new RuntimeException("Solo un veterinario puede actualizar el estado de atención");
        	    }
        	    citaService.cambiarEstado(id, nuevoEstado);
        	}
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/citas/detalle/" + id;
    }

    private boolean tieneRol(Authentication authentication, String rol) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + rol));
    }

    private void cargarListasFormulario(Model model) {
        model.addAttribute("mascotas", mascotaService.listar());
        model.addAttribute("veterinarios", veterinarioService.listar().stream()
                .filter(v -> Boolean.TRUE.equals(v.getActivo())).collect(Collectors.toList()));
        model.addAttribute("servicios", servicioService.listar().stream()
                .filter(s -> Boolean.TRUE.equals(s.getActivo())).collect(Collectors.toList()));
    }
}