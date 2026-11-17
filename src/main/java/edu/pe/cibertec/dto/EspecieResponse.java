package edu.pe.cibertec.dto;

public class EspecieResponse {
    private Integer idEspecie;
    private String nombre;

    public EspecieResponse() {}

    public EspecieResponse(Integer idEspecie, String nombre) {
        this.idEspecie = idEspecie;
        this.nombre = nombre;
    }

    public Integer getIdEspecie() { return idEspecie; }
    public void setIdEspecie(Integer idEspecie) { this.idEspecie = idEspecie; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}