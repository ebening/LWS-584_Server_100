package com.lowes.dto;

import java.util.ArrayList;
import java.util.List;

import com.lowes.entity.KilometrajeUbicacion;

public class KilometrajeDesgloseDTO {
	
	private String fecha;
	private String motivo;
	private KilometrajeUbicacion origen;
	private KilometrajeUbicacion destino;
	private Integer viajes;
	private Double kilometros_recorridos;
	private String importe;
	private List<KilometrajeUbicacion> destinos = new ArrayList<>();
	
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public KilometrajeUbicacion getOrigen() {
		return origen;
	}
	public void setOrigen(KilometrajeUbicacion origen) {
		this.origen = origen;
	}
	public KilometrajeUbicacion getDestino() {
		return destino;
	}
	public void setDestino(KilometrajeUbicacion destino) {
		this.destino = destino;
	}
	public Integer getViajes() {
		return viajes;
	}
	public void setViajes(Integer viajes) {
		this.viajes = viajes;
	}
	public Double getKilometros_recorridos() {
		return kilometros_recorridos;
	}
	public void setKilometros_recorridos(Double kilometros_recorridos) {
		this.kilometros_recorridos = kilometros_recorridos;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public List<KilometrajeUbicacion> getDestinos() {
		return destinos;
	}
	public void setDestinos(List<KilometrajeUbicacion> destinos) {
		this.destinos.addAll(destinos);
	}

}
