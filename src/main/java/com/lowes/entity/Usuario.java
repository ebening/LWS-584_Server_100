package com.lowes.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Josue Sanchez
 */

@Entity
@Table(name = "USUARIO", schema = "LWS584")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idUsuario;
	private Compania compania;
	private Locacion locacion;
	private Perfil perfil;
	private Puesto puesto;
	private Usuario usuarioJefe;
	private int numeroEmpleado;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correoElectronico;
	private String cuenta;
	private String contrasena;
	private short activo;
	private Date creacionFecha;
	private Integer creacionUsuario;
	private Integer modificacionUsuario;
	private Date modificacionFecha;
	private short esSolicitante;
	private short esFiguraContable;
	private short esAutorizador;
	private short especificaSolicitante;
	private short esBeneficiarioCajaChica;
	private String fotoPerfil;
	private Integer numeroProveedor;
	private String rfc;
	
	/*Ids de objetos*/
	
	private int idCompania;
	private int idLocacion;
	private int idPerfil;
	private int idPuesto;
	private int idUsuarioJefe;

	private String nombreCompletoUsuario;
	private String numeroNombreCompletoUsuario;
	
	private Boolean esSolicitanteB;
	private Boolean esFiguraContableB;
	private Boolean esAutorizadorB;
	private Boolean especificaSolicitanteB;
	private Boolean esBeneficiarioCajaChicaB;
	
	private Boolean tieneFotoPerfil; 
	
	public Usuario() {
	}
	
	public Usuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario(int idUsuario, Compania compania, Locacion locacion, Perfil perfil, Puesto puesto,
			int numeroEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
			String cuenta, String contrasena, short activo, Date creacionFecha, int creacionUsuario,
			short esSolicitante, short esFiguraContable, short esAutorizador, short especificaSolicitante,
			short esBeneficiarioCajaChica,String fotoPerfil) {
		this.idUsuario = idUsuario;
		this.compania = compania;
		this.locacion = locacion;
		this.perfil = perfil;
		this.puesto = puesto;
		this.numeroEmpleado = numeroEmpleado;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.correoElectronico = correoElectronico;
		this.cuenta = cuenta;
		this.contrasena = contrasena;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
		this.esSolicitante = esSolicitante;
		this.esFiguraContable = esFiguraContable;
		this.esAutorizador = esAutorizador;
		this.especificaSolicitante = especificaSolicitante;
		this.esBeneficiarioCajaChica = esBeneficiarioCajaChica;
		this.fotoPerfil = fotoPerfil;
	}

	public Usuario(int idUsuario, Compania compania, Locacion locacion, Perfil perfil, Puesto puesto, Usuario usuarioJefe,
			int numeroEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico,
			String cuenta, String contrasena, short activo, Date creacionFecha, int creacionUsuario,
			Date modificacionFecha, Integer modificacionUsuario, short esSolicitante, short esFiguraContable,
			short esAutorizador, short especificaSolicitante, short esBeneficiarioCajaChica,String fotoPerfil) {
		this.idUsuario = idUsuario;
		this.compania = compania;
		this.locacion = locacion;
		this.perfil = perfil;
		this.puesto = puesto;
		this.usuarioJefe = usuarioJefe;
		this.numeroEmpleado = numeroEmpleado;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.correoElectronico = correoElectronico;
		this.cuenta = cuenta;
		this.contrasena = contrasena;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
		this.modificacionFecha = modificacionFecha;
		this.modificacionUsuario = modificacionUsuario;
		this.esSolicitante = esSolicitante;
		this.esFiguraContable = esFiguraContable;
		this.esAutorizador = esAutorizador;
		this.especificaSolicitante = especificaSolicitante;
		this.esBeneficiarioCajaChica = esBeneficiarioCajaChica;
		this.fotoPerfil = fotoPerfil;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USUARIO", unique = true, nullable = false)
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_CAMPANIA", nullable = false)
	public Compania getCompania() {
		return this.compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_LOCACION", nullable = false)
	public Locacion getLocacion() {
		return this.locacion;
	}

	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_PERFIL", nullable = false)
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_PUESTO", nullable = false)
	public Puesto getPuesto() {
		return this.puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO_JEFE")
	public Usuario getUsuario() {
		return this.usuarioJefe;
	}

	public void setUsuario(Usuario usuarioJefe) {
		this.usuarioJefe = usuarioJefe;
	}
	

	@Column(name = "NUMERO_EMPLEADO", nullable = false)
	public int getNumeroEmpleado() {
		return this.numeroEmpleado;
	}

	public void setNumeroEmpleado(int numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	@Column(name = "NOMBRE", nullable = false, length = 40)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 30)
	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	@Column(name = "APELLIDO_MATERNO", nullable = false, length = 30)
	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Column(name = "CORREO_ELECTRONICO", nullable = false, length = 50)
	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@Column(name = "CUENTA", nullable = false, length = 50)
	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Column(name = "CONTRASENA", nullable = false, length = 100)
	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Column(name = "ACTIVO", nullable = false)
	public short getActivo() {
		return this.activo;
	}

	public void setActivo(short activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREACION_FECHA", nullable = false, length = 26)
	public Date getCreacionFecha() {
		return this.creacionFecha;
	}

	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}

	@Column(name = "CREACION_USUARIO", nullable = false)
	public int getCreacionUsuario() {
		return this.creacionUsuario;
	}

	public void setCreacionUsuario(int creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICACION_FECHA")
	public Date getModificacionFecha() {
		return this.modificacionFecha;
	}

	public void setModificacionFecha(Date modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}

	@Column(name = "MODIFICACION_USUARIO", length = 26)
	public Integer getModificacionUsuario() {
		return this.modificacionUsuario;
	}

	public void setModificacionUsuario(Integer modificacionUsuario) {
		this.modificacionUsuario = modificacionUsuario;
	}
	
	@Column(name = "ES_SOLICITANTE", nullable = false)
	public short getEsSolicitante() {
		return this.esSolicitante;
	}

	public void setEsSolicitante(short esSolicitante) {
		this.esSolicitante = esSolicitante;
	}

	@Column(name = "ES_FIGURA_CONTABLE", nullable = false)
	public short getEsFiguraContable() {
		return this.esFiguraContable;
	}

	public void setEsFiguraContable(short esFiguraContable) {
		this.esFiguraContable = esFiguraContable;
	}

	@Column(name = "ES_AUTORIZADOR", nullable = false)
	public short getEsAutorizador() {
		return this.esAutorizador;
	}

	public void setEsAutorizador(short esAutorizador) {
		this.esAutorizador = esAutorizador;
	}

	@Column(name = "ESPECIFICA_SOLICITANTE", nullable = false)
	public short getEspecificaSolicitante() {
		return this.especificaSolicitante;
	}

	public void setEspecificaSolicitante(short especificaSolicitante) {
		this.especificaSolicitante = especificaSolicitante;
	}
	
	@Column(name = "ES_BENEFICIARIO_CAJA_CHICA", nullable = false)
	public short getEsBeneficiarioCajaChica() {
		return this.esBeneficiarioCajaChica;
	}

	public void setEsBeneficiarioCajaChica(short esBeneficiarioCajaChica) {
		this.esBeneficiarioCajaChica = esBeneficiarioCajaChica;
	}
    
	@Transient
	public int getIdCompania() {
		return idCompania;
	}

	public void setIdCompania(int idCompania) {
		this.idCompania = idCompania;
	}
    
	@Transient
	public int getIdLocacion() {
		return idLocacion;
	}

	public void setIdLocacion(int idLocacion) {
		this.idLocacion = idLocacion;
	}
    
	@Transient
	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	@Transient
	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	@Transient
	public int getIdUsuarioJefe() {
		return idUsuarioJefe;
	}

	public void setIdUsuarioJefe(int idUsuarioJefe) {
		this.idUsuarioJefe = idUsuarioJefe;
	}
	@Transient
	public Boolean getEsSolicitanteB() {
		return esSolicitanteB;
	}

	public void setEsSolicitanteB(Boolean esSolicitanteB) {
		this.esSolicitanteB = esSolicitanteB;
	}

	@Transient
	public Boolean getEsFiguraContableB() {
		return esFiguraContableB;
	}

	public void setEsFiguraContableB(Boolean esFiguraContableB) {
		this.esFiguraContableB = esFiguraContableB;
	}

	@Transient
	public Boolean getEsAutorizadorB() {
		return esAutorizadorB;
	}

	public void setEsAutorizadorB(Boolean esAutorizadorB) {
		this.esAutorizadorB = esAutorizadorB;
	}

	@Transient
	public Boolean getEspecificaSolicitanteB() {
		return especificaSolicitanteB;
	}

	public void setEspecificaSolicitanteB(Boolean especificaSolicitanteB) {
		this.especificaSolicitanteB = especificaSolicitanteB;
	}

	@Transient
	public Boolean getEsBeneficiarioCajaChicaB() {
		return esBeneficiarioCajaChicaB;
	}

	public void setEsBeneficiarioCajaChicaB(Boolean esBeneficiarioCajaChicaB) {
		this.esBeneficiarioCajaChicaB = esBeneficiarioCajaChicaB;
	}
	
	@Column(name = "FOTO_PERFIL", length = 100)
	public String getFotoPerfil() {
		return this.fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	@Transient
	public String getNombreCompletoUsuario() {
		nombreCompletoUsuario = this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
		return nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}
	
	@Column(name = "NUMERO_PROVEEDOR")
	public Integer getNumeroProveedor() {
		return this.numeroProveedor;
	}

	public void setNumeroProveedor(Integer numeroProveedor) {
		this.numeroProveedor = numeroProveedor;
	}

	@Column(name = "RFC", length = 15)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@Transient
	public String getNumeroNombreCompletoUsuario() {
		numeroNombreCompletoUsuario = this.numeroEmpleado + " - " + this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
		return numeroNombreCompletoUsuario;
	}

	public void setNumeroNombreCompletoUsuario(String numeroNombreCompletoUsuario) {
		this.numeroNombreCompletoUsuario = numeroNombreCompletoUsuario;
	}

	@Transient
	public Boolean getTieneFotoPerfil() {
		return tieneFotoPerfil;
	}

	public void setTieneFotoPerfil(Boolean tieneFotoPerfil) {
		this.tieneFotoPerfil = tieneFotoPerfil;
	}
	
}