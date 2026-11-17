package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Pago;
import java.util.List;

public interface PagoService {
    List<Pago> listar();
    Pago obtenerPorCita(Integer idCita);
}