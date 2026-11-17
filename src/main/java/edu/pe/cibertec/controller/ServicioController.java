package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Servicio;
import edu.pe.cibertec.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.listar());
        return "servicios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        Servicio s = new Servicio();
        s.setActivo(true);
        model.addAttribute("servicio", s);
        return "servicios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("servicio", servicioService.obtener(id));
        return "servicios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Servicio servicio, Model model) {
        try {
            servicioService.guardar(servicio);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "servicios/formulario";
        }
        return "redirect:/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return "redirect:/servicios";
    }
}