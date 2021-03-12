package com.lowes.entity;
// Generated 23/01/2016 02:54:35 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * VwSolicitudResumen generated by hbm2java
 */
@Entity
@Table(name = "VW_SOLICITUD_RESUMEN", schema = "LWS584")
public class VwSolicitudResumen implements java.io.Serializable {

private static final long serialVersionUID = 1L;
	
	private long idVwSolicitudResumen;
	private int idSolicitud;
	private Integer idUsuarioSolicita;
	private int idTipoSolicitud;
	private String tipoSolicitud;
	private int idEstadoSolicitud;
	private String estadoSolicitud;
	private Date creacionFecha;
	private String conceptoGasto;
	private String motivoRechazo;
	private Integer idTipoCriterio;
	private String tipoCriterio;
	private Integer idProveedor;
	private String proveedor;
	private String factura;
	private Date fechaFactura;
	private BigDecimal subtotal;
	private Integer idMoneda;
	private String moneda;
	private String colorSolicitud;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
//	private BigDecimal importeKm;
	private BigDecimal montoTotal;
	private Integer creacionUsuario;
	private Integer id_usuarioAsesor;
	private String  usuarioAsesor;
	private Integer idSolicitudAutorizacionAP;
	private String solicitudAutorizacionAP;
	private Integer numeroProveedor;
	private Integer numeroProveedorProveedor;
	private Integer numeroProveedorProveedorAsesor;
	private Integer tipoFactura;
	private Integer comprobada;
	private Integer idMonedaSolicitud;
	private String  monedaSolicitud;
	
	//Transients para comprobaciones
	private int idSolicitudComprobacion;
	private int idTipoSolicitudComprobacion;
	private String tipoSolicitudComprobacion;
	private String colorSolicitudComprobacion;
	private String resultadoViaje;

	
	

	public VwSolicitudResumen() {
	}

	public VwSolicitudResumen(long idVwSolicitudResumen, int idSolicitud, int idTipoSolicitud, String tipoSolicitud,
			int idEstadoSolicitud, String estadoSolicitud, Date creacionFecha, String factura, String colorSolicitud,String nombre, String apellidoPaterno, String apellidoMaterno) {
		this.idVwSolicitudResumen = idVwSolicitudResumen;
		this.idSolicitud = idSolicitud;
		this.idTipoSolicitud = idTipoSolicitud;
		this.tipoSolicitud = tipoSolicitud;
		this.idEstadoSolicitud = idEstadoSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.creacionFecha = creacionFecha;
		this.factura = factura;
		this.colorSolicitud = colorSolicitud;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
	}

	public VwSolicitudResumen(long idVwSolicitudResumen, int idSolicitud, Integer idUsuarioSolicita,
			int idTipoSolicitud, String tipoSolicitud, int idEstadoSolicitud, String estadoSolicitud,
			Date creacionFecha, String conceptoGasto, String motivoRechazo, Integer idTipoCriterio, String tipoCriterio,
			Integer idProveedor, String proveedor, String factura, Date fechaFactura, BigDecimal subtotal, Integer idMoneda,
			String moneda, String colorSolicitud, String nombre, String apellidoPaterno, String apellidoMaterno) {
		this.idVwSolicitudResumen = idVwSolicitudResumen;
		this.idSolicitud = idSolicitud;
		this.idUsuarioSolicita = idUsuarioSolicita;
		this.idTipoSolicitud = idTipoSolicitud;
		this.tipoSolicitud = tipoSolicitud;
		this.idEstadoSolicitud = idEstadoSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.creacionFecha = creacionFecha;
		this.conceptoGasto = conceptoGasto;
		this.motivoRechazo = motivoRechazo;
		this.idTipoCriterio = idTipoCriterio;
		this.tipoCriterio = tipoCriterio;
		this.idProveedor = idProveedor;
		this.proveedor = proveedor;
		this.factura = factura;
		this.fechaFactura = fechaFactura;
		this.subtotal = subtotal;
		this.idMoneda = idMoneda;
		this.moneda = moneda;
		this.colorSolicitud = colorSolicitud;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		
	}
	
	@Id
	@Column(name = "ID_VW_SOLICITUD_RESUMEN", nullable = false)
	public long getIdVwSolicitudResumen() {
		return this.idVwSolicitudResumen;
	}

	public void setIdVwSolicitudResumen(long idVwSolicitudResumen) {
		this.idVwSolicitudResumen = idVwSolicitudResumen;
	}

	@Column(name = "ID_SOLICITUD", nullable = false)
	public int getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	@Column(name = "ID_USUARIO_SOLICITA")
	public Integer getIdUsuarioSolicita() {
		return this.idUsuarioSolicita;
	}

	public void setIdUsuarioSolicita(Integer idUsuarioSolicita) {
		this.idUsuarioSolicita = idUsuarioSolicita;
	}

	@Column(name = "ID_TIPO_SOLICITUD", nullable = false)
	public int getIdTipoSolicitud() {
		return this.idTipoSolicitud;
	}

	public void setIdTipoSolicitud(int idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

	@Column(name = "TIPO_SOLICITUD", nullable = false, length = 100)
	public String getTipoSolicitud() {
		return this.tipoSolicitud;
	}

	
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	@Column(name = "COLOR_SOLICITUD", length = 15)
	public String getColorSolicitud() {
		return colorSolicitud;
	}

	public void setColorSolicitud(String colorSolicitud) {
		this.colorSolicitud = colorSolicitud;
	}
	
	@Column(name = "NOMBRE")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "ID_ESTADO_SOLICITUD", nullable = false)
	public int getIdEstadoSolicitud() {
		return this.idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(int idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}
	
	@Column(name = "APELLIDO_PATERNO")
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	@Column(name = "APELLIDO_MATERNO")
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Column(name = "ESTADO_SOLICITUD", nullable = false, length = 100)
	public String getEstadoSolicitud() {
		return this.estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	@Column(name = "CREACION_FECHA", nullable = false, length = 26)
	public Date getCreacionFecha() {
		return this.creacionFecha;
	}

	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}

	@Column(name = "CONCEPTO_GASTO", length = 500)
	public String getConceptoGasto() {
		return this.conceptoGasto;
	}

	public void setConceptoGasto(String conceptoGasto) {
		this.conceptoGasto = conceptoGasto;
	}

	@Column(name = "MOTIVO_RECHAZO", length = 500)
	public String getMotivoRechazo() {
		return this.motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	@Column(name = "ID_TIPO_CRITERIO")
	public Integer getIdTipoCriterio() {
		return this.idTipoCriterio;
	}

	public void setIdTipoCriterio(Integer idTipoCriterio) {
		this.idTipoCriterio = idTipoCriterio;
	}

	@Column(name = "TIPO_CRITERIO", length = 100)
	public String getTipoCriterio() {
		return this.tipoCriterio;
	}

	public void setTipoCriterio(String tipoCriterio) {
		this.tipoCriterio = tipoCriterio;
	}

	@Column(name = "ID_PROVEEDOR")
	public Integer getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	@Column(name = "PROVEEDOR", length = 100)
	public String getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	@Column(name = "FACTURA", length = 10)
	public String getFactura() {
		return this.factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	@Column(name = "FECHA_FACTURA", length = 10)
	public Date getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	@Column(name = "SUBTOTAL", precision = 17)
	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@Column(name = "ID_MONEDA")
	public Integer getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}

	@Column(name = "MONEDA", length = 100)
	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
//	@Column(name = "IMPORTE", precision = 17)
//	public BigDecimal getImporteKm() {
//		return importeKm;
//	}
//
//	public void setImporteKm(BigDecimal importeKm) {
//		this.importeKm = importeKm;
//	}
	
	@Column(name = "MONTO_TOTAL", precision = 17)
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	
	@Column(name = "CREACION_USUARIO")
	public Integer getCreacionUsuario() {
		return creacionUsuario;
	}

	public void setCreacionUsuario(Integer creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwSolicitudResumen))
			return false;
		VwSolicitudResumen castOther = (VwSolicitudResumen) other;

		return (this.getIdVwSolicitudResumen() == castOther.getIdVwSolicitudResumen())
				&& (this.getIdSolicitud() == castOther.getIdSolicitud())
				&& ((this.getIdUsuarioSolicita() == castOther.getIdUsuarioSolicita())
						|| (this.getIdUsuarioSolicita() != null && castOther.getIdUsuarioSolicita() != null
								&& this.getIdUsuarioSolicita().equals(castOther.getIdUsuarioSolicita())))
				&& (this.getIdTipoSolicitud() == castOther.getIdTipoSolicitud())
				&& ((this.getTipoSolicitud() == castOther.getTipoSolicitud())
						|| (this.getTipoSolicitud() != null && castOther.getTipoSolicitud() != null
								&& this.getTipoSolicitud().equals(castOther.getTipoSolicitud())))
				&& (this.getIdEstadoSolicitud() == castOther.getIdEstadoSolicitud())
				&& ((this.getEstadoSolicitud() == castOther.getEstadoSolicitud())
						|| (this.getEstadoSolicitud() != null && castOther.getEstadoSolicitud() != null
								&& this.getEstadoSolicitud().equals(castOther.getEstadoSolicitud())))
				&& ((this.getCreacionFecha() == castOther.getCreacionFecha())
						|| (this.getCreacionFecha() != null && castOther.getCreacionFecha() != null
								&& this.getCreacionFecha().equals(castOther.getCreacionFecha())))
				&& ((this.getConceptoGasto() == castOther.getConceptoGasto())
						|| (this.getConceptoGasto() != null && castOther.getConceptoGasto() != null
								&& this.getConceptoGasto().equals(castOther.getConceptoGasto())))
				&& ((this.getMotivoRechazo() == castOther.getMotivoRechazo())
						|| (this.getMotivoRechazo() != null && castOther.getMotivoRechazo() != null
								&& this.getMotivoRechazo().equals(castOther.getMotivoRechazo())))
				&& ((this.getIdTipoCriterio() == castOther.getIdTipoCriterio())
						|| (this.getIdTipoCriterio() != null && castOther.getIdTipoCriterio() != null
								&& this.getIdTipoCriterio().equals(castOther.getIdTipoCriterio())))
				&& ((this.getTipoCriterio() == castOther.getTipoCriterio())
						|| (this.getTipoCriterio() != null && castOther.getTipoCriterio() != null
								&& this.getTipoCriterio().equals(castOther.getTipoCriterio())))
				&& ((this.getIdProveedor() == castOther.getIdProveedor())
						|| (this.getIdProveedor() != null && castOther.getIdProveedor() != null
								&& this.getIdProveedor().equals(castOther.getIdProveedor())))
				&& ((this.getProveedor() == castOther.getProveedor()) || (this.getProveedor() != null
						&& castOther.getProveedor() != null && this.getProveedor().equals(castOther.getProveedor())))
				&& ((this.getFactura() == castOther.getFactura()) || (this.getFactura() != null
						&& castOther.getFactura() != null && this.getFactura().equals(castOther.getFactura())))
				&& ((this.getFechaFactura() == castOther.getFechaFactura())
						|| (this.getFechaFactura() != null && castOther.getFechaFactura() != null
								&& this.getFechaFactura().equals(castOther.getFechaFactura())))
				&& ((this.getSubtotal() == castOther.getSubtotal()) || (this.getSubtotal() != null
						&& castOther.getSubtotal() != null && this.getSubtotal().equals(castOther.getSubtotal())))
				&& ((this.getIdMoneda() == castOther.getIdMoneda())
						|| (this.getIdMoneda() != null && castOther.getIdMoneda() != null
								&& this.getIdMoneda().equals(castOther.getIdMoneda())))
				&& ((this.getMoneda() == castOther.getMoneda()) || (this.getMoneda() != null
						&& castOther.getMoneda() != null && this.getMoneda().equals(castOther.getMoneda())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getIdVwSolicitudResumen();
		result = 37 * result + this.getIdSolicitud();
		result = 37 * result + (getIdUsuarioSolicita() == null ? 0 : this.getIdUsuarioSolicita().hashCode());
		result = 37 * result + this.getIdTipoSolicitud();
		result = 37 * result + (getTipoSolicitud() == null ? 0 : this.getTipoSolicitud().hashCode());
		result = 37 * result + this.getIdEstadoSolicitud();
		result = 37 * result + (getEstadoSolicitud() == null ? 0 : this.getEstadoSolicitud().hashCode());
		result = 37 * result + (getCreacionFecha() == null ? 0 : this.getCreacionFecha().hashCode());
		result = 37 * result + (getConceptoGasto() == null ? 0 : this.getConceptoGasto().hashCode());
		result = 37 * result + (getMotivoRechazo() == null ? 0 : this.getMotivoRechazo().hashCode());
		result = 37 * result + (getIdTipoCriterio() == null ? 0 : this.getIdTipoCriterio().hashCode());
		result = 37 * result + (getTipoCriterio() == null ? 0 : this.getTipoCriterio().hashCode());
		result = 37 * result + (getIdProveedor() == null ? 0 : this.getIdProveedor().hashCode());
		result = 37 * result + (getProveedor() == null ? 0 : this.getProveedor().hashCode());
		result = 37 * result + (getFactura() == null ? 0 : this.getFactura().hashCode());
		result = 37 * result + (getFechaFactura() == null ? 0 : this.getFechaFactura().hashCode());
		result = 37 * result + (getSubtotal() == null ? 0 : this.getSubtotal().hashCode());
		result = 37 * result + (getIdMoneda() == null ? 0 : this.getIdMoneda().hashCode());
		result = 37 * result + (getMoneda() == null ? 0 : this.getMoneda().hashCode());
		return result;
	}
	
	
	@Column(name = "ID_USUARIO_ASESOR")
	public Integer getId_usuarioAsesor() {
		return id_usuarioAsesor;
	}

	public void setId_usuarioAsesor(Integer id_usuarioAsesor) {
		this.id_usuarioAsesor = id_usuarioAsesor;
	}
	
	@Column(name = "USUARIO_ASESOR", length = 102)
	public String getUsuarioAsesor() {
		return usuarioAsesor;
	}
	
	public void setUsuarioAsesor(String usuarioAsesor) {
		this.usuarioAsesor = usuarioAsesor;
	}
	
	@Column(name = "ID_SOLICITUD_AUTORIZACION_AP")
	public Integer getIdSolicitudAutorizacionAP() {
		return idSolicitudAutorizacionAP;
	}

	public void setIdSolicitudAutorizacionAP(Integer idSolicitudAutorizacionAP) {
		this.idSolicitudAutorizacionAP = idSolicitudAutorizacionAP;
	}
	
	@Column(name = "SOLICITUD_AUTORIZACION_AP")
	public String getSolicitudAutorizacionAP() {
		return solicitudAutorizacionAP;
	}

	public void setSolicitudAutorizacionAP(String solicitudAutorizacionAP) {
		this.solicitudAutorizacionAP = solicitudAutorizacionAP;
	}
	
	@Column(name = "NUMERO_PROVEEDOR")
	public Integer getNumeroProveedor() {
		return numeroProveedor;
	}

	public void setNumeroProveedor(Integer numeroProveedor) {
		this.numeroProveedor = numeroProveedor;
	}

	
	@Column(name = "NUMERO_PROVEEDOR_PROVEEDOR")
	public Integer getNumeroProveedorProveedor() {
		return numeroProveedorProveedor;
	}

	public void setNumeroProveedorProveedor(Integer numeroProveedorProveedor) {
		this.numeroProveedorProveedor = numeroProveedorProveedor;
	}

	@Column(name = "NUMERO_PROVEEDOR_ASESOR")
	public Integer getNumeroProveedorProveedorAsesor() {
		return numeroProveedorProveedorAsesor;
	}

	public void setNumeroProveedorProveedorAsesor(Integer numeroProveedorProveedorAsesor) {
		this.numeroProveedorProveedorAsesor = numeroProveedorProveedorAsesor;
	}

	@Column(name = "TIPO_FACTURA")
	public Integer getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(Integer tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	@Column(name = "COMPROBADA")
	public Integer getComprobada() {
		return comprobada;
	}

	public void setComprobada(Integer comprobada) {
		this.comprobada = comprobada;
	}

	@Column(name = "ID_MONEDA_SOLICITUD")
	public Integer getIdMonedaSolicitud() {
		return idMonedaSolicitud;
	}

	public void setIdMonedaSolicitud(Integer idMonedaSolicitud) {
		this.idMonedaSolicitud = idMonedaSolicitud;
	}
	
	@Column(name = "MONEDA_SOLICITUD")
	public String getMonedaSolicitud() {
		return monedaSolicitud;
	}

	public void setMonedaSolicitud(String monedaSolicitud) {
		this.monedaSolicitud = monedaSolicitud;
	}
	
	@Transient
	public String getResultadoViaje() {
		return resultadoViaje;
	}

	public void setResultadoViaje(String resultadoViaje) {
		this.resultadoViaje = resultadoViaje;
	}
	
	@Transient
	public int getIdSolicitudComprobacion() {
		return idSolicitudComprobacion;
	}

	public void setIdSolicitudComprobacion(int idSolicitudComprobacion) {
		this.idSolicitudComprobacion = idSolicitudComprobacion;
	}
	
	@Transient
	public int getIdTipoSolicitudComprobacion() {
		return idTipoSolicitudComprobacion;
	}

	public void setIdTipoSolicitudComprobacion(int idTipoSolicitudComprobacion) {
		this.idTipoSolicitudComprobacion = idTipoSolicitudComprobacion;
	}
	
	@Transient
	public String getTipoSolicitudComprobacion() {
		return tipoSolicitudComprobacion;
	}

	public void setTipoSolicitudComprobacion(String tipoSolicitudComprobacion) {
		this.tipoSolicitudComprobacion = tipoSolicitudComprobacion;
	}
	
	@Transient
	public String getColorSolicitudComprobacion() {
		return colorSolicitudComprobacion;
	}

	public void setColorSolicitudComprobacion(String colorSolicitudComprobacion) {
		this.colorSolicitudComprobacion = colorSolicitudComprobacion;
	}

}