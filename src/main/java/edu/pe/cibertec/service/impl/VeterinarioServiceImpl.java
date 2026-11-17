package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Veterinario;
import edu.pe.cibertec.repository.VeterinarioRepository;
import edu.pe.cibertec.service.VeterinarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioServiceImpl(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Override
    public List<Veterinario> listar() {
        return veterinarioRepository.findAll();
    }

    @Override
    public Veterinario obtener(Integer id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado con id: " + id));
    }

    @Override
    public Veterinario guardar(Veterinario veterinario) {
        if (veterinario.getActivo() == null) veterinario.setActivo(true);
        if (veterinario.getFechaContratacion() == null) veterinario.setFechaContratacion(LocalDate.now());
        return veterinarioRepository.save(veterinario);
    }

    @Override
    public void eliminar(Integer id) {
        veterinarioRepository.deleteById(id);
    }
}