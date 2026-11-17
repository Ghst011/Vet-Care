package edu.pe.cibertec.dto;

import java.time.LocalDate;

public class MascotaResponse {
    private Integer idMascota;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Double peso;
    private String sexo;
    private ClienteResponse cliente;
    private EspecieResponse especie;

    public MascotaResponse() {}

    public MascotaResponse(Integer idMascota, String nombre, LocalDate fechaNacimiento, Double peso, String sexo,
                            ClienteResponse cliente, EspecieResponse especie) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.sexo = sexo;
        this.cliente = cliente;
        this.especie = especie;
    }

    public Integer getIdMascota() { return idMascota; }
    public void setIdMascota(Integer idMascota) { this.idMascota = idMascota; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public ClienteResponse getCliente() { return cliente; }
    public void setCliente(ClienteResponse cliente) { this.cliente = cliente; }
    public EspecieResponse getEspecie() { return especie; }
    public void setEspecie(EspecieResponse especie) { this.especie = especie; }
}