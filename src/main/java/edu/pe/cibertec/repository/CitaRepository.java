package edu.pe.cibertec.repository;

import edu.pe.cibertec.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByMascotaIdMascotaOrderByFechaDesc(Integer idMascota);

    //para el listado general, ordenado por fecha y hora ascendente
    List<Cita> findAllByOrderByFechaAscHoraAsc();
}