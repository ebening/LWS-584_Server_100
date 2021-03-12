package com.lowes.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "COMPANIA" , schema = "LWS584")
public class Compania implements java.io.Serializable {

	private static final long serialVersionUID = 5089189230780496969L;
	
	private int idcompania;
	private String descripcion;
	private short activo;
	private Date creacionfecha;
	private int creacionusuario;
	private Date modificacionfecha;
	private Integer modificacionusuario;
	private String numeroCompania;
	private String rfc;
	private String claveValidacionFiscal;
	private Integer idOrganizacion;
	private String descripcionEbs;
	private Short esContable;

	public Compania() {
		//System.out.println("CompaniaEntity()");
	}
	
	public Compania(int idcompania) {
		this.idcompania = idcompania;
	}

	public Compania(int idcompania, String descripcion, short activo, Date creacionfecha, int creacionusuario, String numeroCompania, String rfc) {
		this.idcompania = idcompania;
		this.descripcion = descripcion;
		this.activo = activo;
		this.creacionfecha = creacionfecha;
		this.creacionusuario = creacionusuario;
		this.numeroCompania = numeroCompania;
		this.rfc = rfc;
	}

	public Compania(int idcompania, String descripcion, short activo, Date creacionfecha, int creacionusuario,
			Date modificacionfecha, Integer modificacionusuario, String numeroCompania, String rfc,
			String claveValidacionFiscal, Integer idOrganizacion, String descripcionEbs) {
		this.idcompania = idcompania;
		this.descripcion = descripcion;
		this.activo = activo;
		this.creacionfecha = creacionfecha;
		this.creacionusuario = creacionusuario;
		this.modificacionfecha = modificacionfecha;
		this.modificacionusuario = modificacionusuario;
		this.numeroCompania = numeroCompania;
		this.rfc = rfc;
		this.claveValidacionFiscal = claveValidacionFiscal;
		this.idOrganizacion = idOrganizacion;
		this.descripcionEbs = descripcionEbs;
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_COMPANIA", nullable = false, unique=true)
	public int getIdcompania() {
		return this.idcompania;
	}

	public void setIdcompania(int idcompania) {
		this.idcompania = idcompania;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ACTIVO", nullable = false)
	public short getActivo() {
		return this.activo;
	}

	public void setActivo(short activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREACION_FECHA", length = 26)
	public Date getCreacionfecha() {
		return this.creacionfecha;
	}

	public void setCreacionfecha(Date creacionfecha) {
		this.creacionfecha = creacionfecha;
	}

	@Column(name = "CREACION_USUARIO")
	public int getCreacionusuario() {
		return this.creacionusuario;
	}

	public void setCreacionusuario(int creacionusuario) {
		this.creacionusuario = creacionusuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICACION_FECHA")
	public Date getModificacionfecha() {
		return this.modificacionfecha;
	}

	public void setModificacionfecha(Date modificacionfecha) {
		this.modificacionfecha = modificacionfecha;
	}
	
	@Column(name = "MODIFICACION_USUARIO")
	public Integer getModificacionusuario() {
		return this.modificacionusuario;
	}

	public void setModificacionusuario(Integer modificacionusuario) {
		this.modificacionusuario = modificacionusuario;
	}
	
	@Column(name = "NUMERO_COMPANIA", nullable = false, length = 2)
	public String getNumeroCompania() {
		return this.numeroCompania;
	}

	public void setNumeroCompania(String numeroCompania) {
		this.numeroCompania = numeroCompania;
	}

	@Column(name = "RFC", nullable = false, length = 15)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	@Column(name = "CLAVE_VALIDACION_FISCAL", length = 36)
	public String getClaveValidacionFiscal() {
		return this.claveValidacionFiscal;
	}

	public void setClaveValidacionFiscal(String claveValidacionFiscal) {
		this.claveValidacionFiscal = claveValidacionFiscal;
	}

	@Column(name = "ID_ORGANIZACION")
	public Integer getIdOrganizacion() {
		return this.idOrganizacion;
	}

	public void setIdOrganizacion(Integer idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}
	
	@Column(name = "DESCRIPCION_EBS", length = 20)
	public String getDescripcionEbs() {
		return this.descripcionEbs;
	}

	public void setDescripcionEbs(String descripcionEbs) {
		this.descripcionEbs = descripcionEbs;
	}
	
	@Column(name = "ES_CONTABLE")
	public Short getEsContable() {
		return esContable;
	}

	public void setEsContable(Short esContable) {
		this.esContable = esContable;
	}
	
	
	
}