package edu.pe.cibertec.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.pe.cibertec.repository.PagoRepository;

class PagoServiceImplTest {
														// JOSUE DANIEL VILLAR LIZA - CODIGO DE ESTUDIANTE: I202503361
	@Mock
	private PagoRepository pagoRepository;

	@InjectMocks
	private PagoServiceImpl pagoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void test() {
		//fail("Not yet implemented");
	}

	@Test
	void obtenerPorCitaSinPagoDebeLanzarExcepcion() {
		
		when(pagoRepository.findByCitaIdCita(99)).thenReturn(Optional.empty());
		
		RuntimeException ex = assertThrows(RuntimeException.class, 
				() -> pagoService.obtenerPorCita(99));
		
		//verifico
		assertEquals("Esta cita todavía no tiene un pago registrado", ex.getMessage());
		verify(pagoRepository, times(1)).findByCitaIdCita(99);
		
	}
	
}
