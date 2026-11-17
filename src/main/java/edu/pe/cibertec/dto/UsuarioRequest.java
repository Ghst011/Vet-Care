package edu.pe.cibertec.dto;

public class UsuarioRequest {
    private String username;
    private String password; // texto plano, se encripta en el service
    private String rol;
    private Boolean estado;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}