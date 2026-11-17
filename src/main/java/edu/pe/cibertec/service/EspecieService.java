package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Especie;
import java.util.List;

public interface EspecieService {
    List<Especie> listar();
    Especie obtener(Integer id);
    Especie guardar(Especie especie);
    void eliminar(Integer id);
}