package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.entity.Usuario;
import edu.pe.cibertec.repository.UsuarioRepository;
import edu.pe.cibertec.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtener(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario guardar(Usuario usuario, String passwordPlano) {
        // Regla: username único
        Usuario existente = usuarioRepository.findByUsername(usuario.getUsername());
        if (existente != null && !existente.getId().equals(usuario.getId())) {
            throw new RuntimeException("Ya existe un usuario con ese username");
        }

        if (usuario.getId() == null) {
            // Usuario nuevo: la contraseña es obligatoria
            if (passwordPlano == null || passwordPlano.isBlank()) {
                throw new RuntimeException("La contraseña es obligatoria para un usuario nuevo");
            }
            usuario.setPassword(passwordEncoder.encode(passwordPlano));
        } else {
            // Usuario existente: si dejó la contraseña vacía, mantiene la anterior
            if (passwordPlano == null || passwordPlano.isBlank()) {
                Usuario actual = obtener(usuario.getId());
                usuario.setPassword(actual.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(passwordPlano));
            }
        }

        if (usuario.getEstado() == null) usuario.setEstado(true);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}