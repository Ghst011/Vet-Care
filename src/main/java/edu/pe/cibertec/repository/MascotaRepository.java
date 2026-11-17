package edu.pe.cibertec.repository;

import edu.pe.cibertec.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    List<Mascota> findByClienteIdPersona(Integer idCliente);
}