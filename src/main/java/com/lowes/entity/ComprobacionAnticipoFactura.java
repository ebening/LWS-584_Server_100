package com.lowes.entity;
// Generated 23/03/2016 12:31:20 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "COMPROBACION_ANTICIPO_FACTURA", schema = "LWS584")
public class ComprobacionAnticipoFactura implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idComprobacionAnticipoFactura;
	private Factura factura;
	private Solicitud solicitudByIdSolicitudAnticipo;
	private Solicitud solicitudByIdSolicitudComprobacion;
	private BigDecimal montoFactura;

	public ComprobacionAnticipoFactura() {
	}
	
	public ComprobacionAnticipoFactura(int idComprobacionAnticipoFactura) {
		this.idComprobacionAnticipoFactura = idComprobacionAnticipoFactura;
	}

	public ComprobacionAnticipoFactura(int idComprobacionAnticipoFactura, Factura factura, 
			Solicitud solicitudByIdSolicitudAnticipo, Solicitud solicitudByIdSolicitudComprobacion, 
			BigDecimal montoFactura) {
		this.idComprobacionAnticipoFactura = idComprobacionAnticipoFactura;
		this.factura = factura;
		this.solicitudByIdSolicitudAnticipo = solicitudByIdSolicitudAnticipo;
		this.solicitudByIdSolicitudComprobacion = solicitudByIdSolicitudComprobacion;
		this.montoFactura = montoFactura;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "ID_COMPROBACION_ANTICIPO_FACTURA", unique = true, nullable = false)
	public int getIdComprobacionAnticipoFactura() {
		return this.idComprobacionAnticipoFactura;
	}

	public void setIdComprobacionAnticipoFactura(int idComprobacionAnticipoFactura) {
		this.idComprobacionAnticipoFactura = idComprobacionAnticipoFactura;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_FACTURA", nullable = false)
	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_SOLICITUD_ANTICIPO", nullable = false)
	public Solicitud getSolicitudByIdSolicitudAnticipo() {
		return this.solicitudByIdSolicitudAnticipo;
	}

	public void setSolicitudByIdSolicitudAnticipo(Solicitud solicitudByIdSolicitudAnticipo) {
		this.solicitudByIdSolicitudAnticipo = solicitudByIdSolicitudAnticipo;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_SOLICITUD_COMPROBACION", nullable = false)
	public Solicitud getSolicitudByIdSolicitudComprobacion() {
		return this.solicitudByIdSolicitudComprobacion;
	}

	public void setSolicitudByIdSolicitudComprobacion(Solicitud solicitudByIdSolicitudComprobacion) {
		this.solicitudByIdSolicitudComprobacion = solicitudByIdSolicitudComprobacion;
	}

	@Column(name = "MONTO_FACTURA", nullable = false, precision = 17)
	public BigDecimal getMontoFactura() {
		return this.montoFactura;
	}

	public void setMontoFactura(BigDecimal montoFactura) {
		this.montoFactura = montoFactura;
	}

}
