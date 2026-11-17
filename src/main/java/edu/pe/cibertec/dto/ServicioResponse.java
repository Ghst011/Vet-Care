package edu.pe.cibertec.dto;

import java.math.BigDecimal;

public class ServicioResponse {
    private Integer idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean activo;

    public ServicioResponse() {}

    public ServicioResponse(Integer idServicio, String nombre, String descripcion, BigDecimal precio, Boolean activo) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.activo = activo;
    }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}