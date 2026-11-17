package edu.pe.cibertec.service.impl;

import edu.pe.cibertec.dto.CrearCitaRequest;
import edu.pe.cibertec.dto.DetalleCitaRequest;
import edu.pe.cibertec.entity.*;
import edu.pe.cibertec.repository.CitaRepository;
import edu.pe.cibertec.repository.MascotaRepository;
import edu.pe.cibertec.repository.ServicioRepository;
import edu.pe.cibertec.repository.VeterinarioRepository;
import edu.pe.cibertec.service.CitaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.pe.cibertec.repository.PagoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CitaServiceImpl implements CitaService {

    // Regla #13: solo se permite avanzar al siguiente estado del flujo
    private static final Map<EstadoCita, EstadoCita> SIGUIENTE_ESTADO = Map.of(
            EstadoCita.PENDIENTE, EstadoCita.EN_ATENCION,
            EstadoCita.EN_ATENCION, EstadoCita.FINALIZADA,
            EstadoCita.FINALIZADA, EstadoCita.PAGADA
    );

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final ServicioRepository servicioRepository;
    private final PagoRepository pagoRepository;

    public CitaServiceImpl(CitaRepository citaRepository, MascotaRepository mascotaRepository,
                            VeterinarioRepository veterinarioRepository, ServicioRepository servicioRepository, PagoRepository pagoRepository ) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.servicioRepository = servicioRepository;
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Cita> listar() {
        return citaRepository.findAllByOrderByFechaAscHoraAsc();
    }

    @Override
    public Cita obtener(Integer id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Cita registrarCita(CrearCitaRequest request) {

        // Regla #5: la mascota debe existir
        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new RuntimeException("La mascota no existe"));

        // Regla #4 y #6: el cliente debe existir y la mascota debe pertenecerle
        // -> se cumplen automáticamente porque la mascota ya trae su cliente asociado.

        // Reglas #2 y #3: el veterinario debe existir y estar activo
        Veterinario veterinario = veterinarioRepository.findById(request.getIdVeterinario())
                .orElseThrow(() -> new RuntimeException("El veterinario no existe"));
        if (!Boolean.TRUE.equals(veterinario.getActivo())) {
            throw new RuntimeException("El veterinario asignado no está activo");
        }

        // Regla #11: debe tener al menos un servicio
        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new RuntimeException("La cita debe tener al menos un servicio");
        }

        Cita cita = new Cita();
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMotivo(request.getMotivo());
        cita.setEstado(EstadoCita.PENDIENTE); // Regla #9 - corregido respecto al bug de AutoManager (ahí quedaba null)

        List<CitaServicio> detallesEntidad = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleCitaRequest item : request.getDetalles()) {
            if (item.getCantidad() == null || item.getCantidad() <= 0) continue;

            // Regla #7: solo servicios activos pueden agregarse
            Servicio servicio = servicioRepository.findById(item.getIdServicio())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            if (!Boolean.TRUE.equals(servicio.getActivo())) {
                throw new RuntimeException("El servicio '" + servicio.getNombre() + "' no está activo");
            }

            // Regla #12: el total se calcula en el backend
            BigDecimal subtotal = servicio.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));

            CitaServicio detalle = new CitaServicio();
            detalle.setServicio(servicio);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(subtotal);
            detalle.setCita(cita);
            detallesEntidad.add(detalle);

            total = total.add(subtotal);
        }

        if (detallesEntidad.isEmpty()) {
            throw new RuntimeException("La cita debe tener al menos un servicio con cantidad mayor a cero");
        }

        cita.setDetalles(detallesEntidad);
        cita.setTotal(total);

        return citaRepository.save(cita); // cascade guarda cita + detalles en una sola transacción
    }

    @Override
    @Transactional
    public Cita cambiarEstado(Integer idCita, EstadoCita nuevoEstado) {
        Cita cita = obtener(idCita);
        EstadoCita permitido = SIGUIENTE_ESTADO.get(cita.getEstado());

        // Regla #13 y #14: solo se puede avanzar al siguiente estado válido del flujo
        if (permitido == null || permitido != nuevoEstado) {
            throw new RuntimeException("No se puede cambiar de " + cita.getEstado() + " a " + nuevoEstado);
        }

        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarPorMascota(Integer idMascota) {
        return citaRepository.findByMascotaIdMascotaOrderByFechaDesc(idMascota);
    }

    @Override
    @Transactional
    public Cita registrarPago(Integer idCita, String registradoPor) {
        Cita cita = obtener(idCita);

        // Regla #14: solo una cita FINALIZADA puede iniciar el proceso de pago
        if (cita.getEstado() != EstadoCita.FINALIZADA) {
            throw new RuntimeException("Solo una cita FINALIZADA puede pasar a pagada");
        }

        Pago pago = new Pago();
        pago.setCita(cita);
        pago.setFechaPago(LocalDate.now());
        pago.setMontoPagado(cita.getTotal());
        pago.setRegistradoPor(registradoPor);
        pagoRepository.save(pago);

        cita.setEstado(EstadoCita.PAGADA);
        return citaRepository.save(cita);
    }
	
}