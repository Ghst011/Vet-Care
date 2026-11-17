package edu.pe.cibertec.dto;

public class UsuarioResponse {
    private Long id;
    private String username;
    private String rol;
    private Boolean estado;

    public UsuarioResponse() {}

    public UsuarioResponse(Long id, String username, String rol, Boolean estado) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}