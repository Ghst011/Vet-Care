package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listar();
    Cliente obtener(Integer id);
    Cliente guardar(Cliente cliente);
    void eliminar(Integer id);
}