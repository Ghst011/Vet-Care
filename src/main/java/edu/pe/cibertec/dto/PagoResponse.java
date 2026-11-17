package edu.pe.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoResponse {
    private Integer idPago;
    private LocalDate fechaPago;
    private BigDecimal montoPagado;
    private String registradoPor;
    private CitaResponse cita;

    public PagoResponse() {}

    public PagoResponse(Integer idPago, LocalDate fechaPago, BigDecimal montoPagado, String registradoPor, CitaResponse cita) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.montoPagado = montoPagado;
        this.registradoPor = registradoPor;
        this.cita = cita;
    }

    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }
    public String getRegistradoPor() { return registradoPor; }
    public void setRegistradoPor(String registradoPor) { this.registradoPor = registradoPor; }
    public CitaResponse getCita() { return cita; }
    public void setCita(CitaResponse cita) { this.cita = cita; }
}