package edu.pe.cibertec.repository;

import edu.pe.cibertec.entity.Pago;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByCitaIdCita(Integer idCita);

	
}