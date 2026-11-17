package edu.pe.cibertec.controller;

import edu.pe.cibertec.entity.Usuario;
import edu.pe.cibertec.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        Usuario u = new Usuario();
        u.setEstado(true);
        model.addAttribute("usuario", u);
        return "usuarios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.obtener(id));
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
                           @RequestParam(required = false) String passwordPlano,
                           Model model) {
        try {
            usuarioService.guardar(usuario, passwordPlano);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}