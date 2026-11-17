package edu.pe.cibertec.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.pe.cibertec.entity.Mascota;
import edu.pe.cibertec.repository.MascotaRepository;

class MascotaServiceImplTest {

															// JOSUE DANIEL VILLAR LIZA - CODIGO DE ESTUDIANTE: I202503361				
	@Mock
	private MascotaRepository mascotaRepository;

	@InjectMocks
	private MascotaServiceImpl mascotaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	void listarMascotasPorCliente() {
		
		// preparacion de datso:
				List<Mascota> mascotas = new ArrayList<>();

				Mascota m1 = new Mascota();
				m1.setIdMascota(1);
				m1.setNombre("Firulais");
				m1.setPeso(12.5);
				m1.setSexo("M");
				m1.setFechaNacimiento(LocalDate.of(2020, 5, 10));

				Mascota m2 = new Mascota();
				m2.setIdMascota(2);
				m2.setNombre("Michi");
				m2.setPeso(4.2);
				m2.setSexo("H");
				m2.setFechaNacimiento(LocalDate.of(2022, 3, 15));

				mascotas.add(m1);
				mascotas.add(m2);

				when(mascotaRepository.findByClienteIdPersona(1)).thenReturn(mascotas);
				
				// metodo que probaré
				List<Mascota> resultado = mascotaService.listarPorCliente(1);
				// cantidad de mascotas del cliente es 2
				assertEquals(2, resultado.size());
				// el nombre de la 1° mascota es Firulais
				assertEquals("Firulais", resultado.get(0).getNombre());
				// el nombre de la 2° mascota es Michi
				assertEquals("Michi", resultado.get(1).getNombre());
				
	}
	
}
