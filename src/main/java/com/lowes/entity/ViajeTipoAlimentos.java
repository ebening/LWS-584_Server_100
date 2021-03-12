package com.lowes.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ViajeTipoAlimentos generated by hbm2java
 */

@Entity
@Table(name = "LWS584.VIAJE_TIPO_ALIMENTOS")
public class ViajeTipoAlimentos implements java.io.Serializable {

	private int idViajeTipoAlimentos;
	private String descripcion;
	private short activo;
	private Date creacionFecha;
	private int creacionUsuario;
	private Date modificacionFecha;
	private Integer modificacionUsuario;
	private Set<FacturaGastoViaje> facturaGastoViajes = new HashSet<FacturaGastoViaje>(0);
	private Set<FacturaDesglose> facturaDesgloses = new HashSet<FacturaDesglose>(0);

	public ViajeTipoAlimentos() {
	}
	
	public ViajeTipoAlimentos(int idViajeTipoAlimentos) {
		this.idViajeTipoAlimentos = idViajeTipoAlimentos;
	}

	public ViajeTipoAlimentos(int idViajeTipoAlimentos, String descripcion, short activo, Date creacionFecha,
			int creacionUsuario) {
		this.idViajeTipoAlimentos = idViajeTipoAlimentos;
		this.descripcion = descripcion;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
	}

	public ViajeTipoAlimentos(int idViajeTipoAlimentos, String descripcion, short activo, Date creacionFecha,
			int creacionUsuario, Date modificacionFecha, Integer modificacionUsuario,
			Set<FacturaGastoViaje> facturaGastoViajes, Set<FacturaDesglose> facturaDesgloses) {
		this.idViajeTipoAlimentos = idViajeTipoAlimentos;
		this.descripcion = descripcion;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
		this.modificacionFecha = modificacionFecha;
		this.modificacionUsuario = modificacionUsuario;
		this.facturaGastoViajes = facturaGastoViajes;
		this.facturaDesgloses = facturaDesgloses;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "ID_VIAJE_TIPO_ALIMENTOS", unique = true, nullable = false)
	public int getIdViajeTipoAlimentos() {
		return this.idViajeTipoAlimentos;
	}

	public void setIdViajeTipoAlimentos(int idViajeTipoAlimentos) {
		this.idViajeTipoAlimentos = idViajeTipoAlimentos;
	}

	@Column(name = "DESCRIPCION", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ACTIVO")
	public short getActivo() {
		return this.activo;
	}

	public void setActivo(short activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREACION_FECHA", length = 26)
	public Date getCreacionFecha() {
		return this.creacionFecha;
	}

	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}

	@Column(name = "CREACION_USUARIO")
	public int getCreacionUsuario() {
		return this.creacionUsuario;
	}

	public void setCreacionUsuario(int creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICACION_FECHA", length = 26)
	public Date getModificacionFecha() {
		return this.modificacionFecha;
	}

	public void setModificacionFecha(Date modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}

	@Column(name = "MODIFICACION_USUARIO")
	public Integer getModificacionUsuario() {
		return this.modificacionUsuario;
	}

	public void setModificacionUsuario(Integer modificacionUsuario) {
		this.modificacionUsuario = modificacionUsuario;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "viajeTipoAlimentos")
	public Set<FacturaGastoViaje> getFacturaGastoViajes() {
		return this.facturaGastoViajes;
	}

	public void setFacturaGastoViajes(Set<FacturaGastoViaje> facturaGastoViajes) {
		this.facturaGastoViajes = facturaGastoViajes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "viajeTipoAlimentos")
	public Set<FacturaDesglose> getFacturaDesgloses() {
		return this.facturaDesgloses;
	}

	public void setFacturaDesgloses(Set<FacturaDesglose> facturaDesgloses) {
		this.facturaDesgloses = facturaDesgloses;
	}

}