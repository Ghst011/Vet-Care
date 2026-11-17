package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Mascota;
import edu.pe.cibertec.repository.MascotaRepository;
import edu.pe.cibertec.service.MascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    @Override
    public Mascota obtener(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
    }

    @Override
    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public void eliminar(Integer id) {
        mascotaRepository.deleteById(id);
    }

    @Override
    public List<Mascota> listarPorCliente(Integer idCliente) {
        return mascotaRepository.findByClienteIdPersona(idCliente);
    }
}