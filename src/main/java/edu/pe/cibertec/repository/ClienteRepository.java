package edu.pe.cibertec.repository;

import edu.pe.cibertec.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}