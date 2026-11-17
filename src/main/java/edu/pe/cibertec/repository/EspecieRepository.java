package edu.pe.cibertec.repository;

import edu.pe.cibertec.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {
}