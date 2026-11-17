package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Asistente;
import java.util.List;

public interface AsistenteService {
    List<Asistente> listar();
    Asistente obtener(Integer id);
    Asistente guardar(Asistente asistente);
    void eliminar(Integer id);
}