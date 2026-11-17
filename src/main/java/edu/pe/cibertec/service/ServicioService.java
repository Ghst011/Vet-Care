package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> listar();
    Servicio obtener(Integer id);
    Servicio guardar(Servicio servicio);
    void eliminar(Integer id);
}