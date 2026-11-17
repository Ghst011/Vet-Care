package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Servicio;
import edu.pe.cibertec.repository.ServicioRepository;
import edu.pe.cibertec.service.ServicioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtener(Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        // Regla de negocio #8: el precio de un servicio debe ser mayor a cero
        if (servicio.getPrecio() == null || servicio.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precio del servicio debe ser mayor a cero");
        }
        return servicioRepository.save(servicio);
    }

    @Override
    public void eliminar(Integer id) {
        servicioRepository.deleteById(id);
    }
}