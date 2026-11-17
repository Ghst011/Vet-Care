package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Veterinario;
import edu.pe.cibertec.service.VeterinarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("veterinarios", veterinarioService.listar());
        return "veterinarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        Veterinario v = new Veterinario();
        v.setActivo(true);
        model.addAttribute("veterinario", v);
        return "veterinarios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("veterinario", veterinarioService.obtener(id));
        return "veterinarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Veterinario veterinario) {
        veterinarioService.guardar(veterinario);
        return "redirect:/veterinarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        veterinarioService.eliminar(id);
        return "redirect:/veterinarios";
    }
}