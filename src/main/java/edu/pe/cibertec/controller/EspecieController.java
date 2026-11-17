package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Especie;
import edu.pe.cibertec.service.EspecieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("especies", especieService.listar());
        return "especies/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        model.addAttribute("especie", new Especie());
        return "especies/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("especie", especieService.obtener(id));
        return "especies/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Especie especie) {
        especieService.guardar(especie);
        return "redirect:/especies";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        especieService.eliminar(id);
        return "redirect:/especies";
    }
}