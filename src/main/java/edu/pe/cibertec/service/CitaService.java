package edu.pe.cibertec.service;

import edu.pe.cibertec.dto.CrearCitaRequest;
import edu.pe.cibertec.entity.Cita;
import edu.pe.cibertec.entity.EstadoCita;

import java.util.List;

public interface CitaService {
    List<Cita> listar();
    Cita obtener(Integer id);
    Cita registrarCita(CrearCitaRequest request);
    Cita cambiarEstado(Integer idCita, EstadoCita nuevoEstado);
    Cita registrarPago(Integer idCita, String registradoPor);
    List<Cita> listarPorMascota(Integer idMascota);
}