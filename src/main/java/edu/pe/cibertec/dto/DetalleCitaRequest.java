package edu.pe.cibertec.dto;

public class DetalleCitaRequest {

	private Integer idServicio;
	private Integer cantidad;
	
	public DetalleCitaRequest() {
		
	}
	
	public DetalleCitaRequest(Integer idServicio, Integer cantidad) {
		this.idServicio = idServicio;
		this.cantidad = cantidad;
	}
	
	public Integer getIdServicio() { return idServicio; }
	public void setIdServicio(Integer idServicio) {this.idServicio = idServicio; }
	
	public Integer getCantidad() { return cantidad; }
	public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
