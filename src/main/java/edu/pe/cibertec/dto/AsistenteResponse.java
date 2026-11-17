package edu.pe.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AsistenteResponse {
    private Integer idPersona;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private LocalDate fechaContratacion;
    private BigDecimal salario;
    private Boolean activo;

    public AsistenteResponse() {}

    public AsistenteResponse(Integer idPersona, String nombre, String apellido, String dni, String telefono,
                              LocalDate fechaContratacion, BigDecimal salario, Boolean activo) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
        this.activo = activo;
    }

    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}