package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Pago;
import edu.pe.cibertec.repository.PagoRepository;
import edu.pe.cibertec.service.PagoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }


    @Override
    public Pago obtenerPorCita(Integer idCita) {
        return pagoRepository.findByCitaIdCita(idCita)
                .orElseThrow(() -> new RuntimeException("Esta cita todavía no tiene un pago registrado"));
    }
}