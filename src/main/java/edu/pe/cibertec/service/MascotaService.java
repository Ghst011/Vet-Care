package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Mascota;
import java.util.List;

public interface MascotaService {
    List<Mascota> listar();
    Mascota obtener(Integer id);
    Mascota guardar(Mascota mascota);
    void eliminar(Integer id);
    List<Mascota> listarPorCliente(Integer idCliente);
}