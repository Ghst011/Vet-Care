package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Mascota;
import edu.pe.cibertec.service.ClienteService;
import edu.pe.cibertec.service.EspecieService;
import edu.pe.cibertec.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;
    private final EspecieService especieService;

    public MascotaController(MascotaService mascotaService, ClienteService clienteService, EspecieService especieService) {
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
        this.especieService = especieService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mascotas", mascotaService.listar());
        return "mascotas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("especies", especieService.listar());
        return "mascotas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("mascota", mascotaService.obtener(id));
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("especies", especieService.listar());
        return "mascotas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Mascota mascota, Model model) {
        try {
            if (mascota.getCliente() == null) {
                throw new RuntimeException("Debes seleccionar un cliente válido de la lista de sugerencias");
            }
            mascotaService.guardar(mascota);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("clientes", clienteService.listar());
            model.addAttribute("especies", especieService.listar());
            return "mascotas/formulario";
        }
        return "redirect:/mascotas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        mascotaService.eliminar(id);
        return "redirect:/mascotas";
    }
}