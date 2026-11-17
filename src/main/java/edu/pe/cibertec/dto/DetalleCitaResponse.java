package edu.pe.cibertec.dto;

import java.math.BigDecimal;

public class DetalleCitaResponse {
    private ServicioResponse servicio;
    private Integer cantidad;
    private BigDecimal subtotal;

    public DetalleCitaResponse() {}

    public DetalleCitaResponse(ServicioResponse servicio, Integer cantidad, BigDecimal subtotal) {
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public ServicioResponse getServicio() { return servicio; }
    public void setServicio(ServicioResponse servicio) { this.servicio = servicio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}