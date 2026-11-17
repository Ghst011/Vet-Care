package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Asistente;
import edu.pe.cibertec.service.AsistenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asistentes")
public class AsistenteController {

    private final AsistenteService asistenteService;

    public AsistenteController(AsistenteService asistenteService) {
        this.asistenteService = asistenteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asistentes", asistenteService.listar());
        return "asistentes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        Asistente a = new Asistente();
        a.setActivo(true);
        model.addAttribute("asistente", a);
        return "asistentes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("asistente", asistenteService.obtener(id));
        return "asistentes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asistente asistente) {
        asistenteService.guardar(asistente);
        return "redirect:/asistentes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        asistenteService.eliminar(id);
        return "redirect:/asistentes";
    }
}