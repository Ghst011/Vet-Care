package edu.pe.cibertec.service;

import edu.pe.cibertec.entity.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario obtener(Long id);
    Usuario guardar(Usuario usuario, String passwordPlano);
    void eliminar(Long id);	
}