package edu.pe.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CitaResponse {
    private Integer idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;
    private BigDecimal total;
    private MascotaResponse mascota;
    private VeterinarioResponse veterinario;
    private List<DetalleCitaResponse> detalles;

    public CitaResponse() {}

    public CitaResponse(Integer idCita, LocalDate fecha, LocalTime hora, String motivo, String estado, BigDecimal total,
                         MascotaResponse mascota, VeterinarioResponse veterinario, List<DetalleCitaResponse> detalles) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.total = total;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.detalles = detalles;
    }

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public MascotaResponse getMascota() { return mascota; }
    public void setMascota(MascotaResponse mascota) { this.mascota = mascota; }
    public VeterinarioResponse getVeterinario() { return veterinario; }
    public void setVeterinario(VeterinarioResponse veterinario) { this.veterinario = veterinario; }
    public List<DetalleCitaResponse> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCitaResponse> detalles) { this.detalles = detalles; }
}