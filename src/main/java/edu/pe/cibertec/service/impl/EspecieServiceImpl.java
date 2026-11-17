package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Especie;
import edu.pe.cibertec.repository.EspecieRepository;
import edu.pe.cibertec.service.EspecieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieServiceImpl implements EspecieService {

    private final EspecieRepository especieRepository;

    public EspecieServiceImpl(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Override
    public List<Especie> listar() {
        return especieRepository.findAll();
    }

    @Override
    public Especie obtener(Integer id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada con id: " + id));
    }

    @Override
    public Especie guardar(Especie especie) {
        return especieRepository.save(especie);
    }

    @Override
    public void eliminar(Integer id) {
        especieRepository.deleteById(id);
    }
}