package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Asistente;
import edu.pe.cibertec.repository.AsistenteRepository;
import edu.pe.cibertec.service.AsistenteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AsistenteServiceImpl implements AsistenteService {

    private final AsistenteRepository asistenteRepository;

    public AsistenteServiceImpl(AsistenteRepository asistenteRepository) {
        this.asistenteRepository = asistenteRepository;
    }

    @Override
    public List<Asistente> listar() {
        return asistenteRepository.findAll();
    }

    @Override
    public Asistente obtener(Integer id) {
        return asistenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado con id: " + id));
    }

    @Override
    public Asistente guardar(Asistente asistente) {
        if (asistente.getActivo() == null) asistente.setActivo(true);
        if (asistente.getFechaContratacion() == null) asistente.setFechaContratacion(LocalDate.now());
        return asistenteRepository.save(asistente);
    }

    @Override
    public void eliminar(Integer id) {
        asistenteRepository.deleteById(id);
    }
}