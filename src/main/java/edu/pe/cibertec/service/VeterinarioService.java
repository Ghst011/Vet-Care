package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Veterinario;
import java.util.List;

public interface VeterinarioService {
    List<Veterinario> listar();
    Veterinario obtener(Integer id);
    Veterinario guardar(Veterinario veterinario);
    void eliminar(Integer id);
}