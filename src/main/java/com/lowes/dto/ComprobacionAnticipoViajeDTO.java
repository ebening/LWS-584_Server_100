package com.lowes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.ViajeDestino;
import com.lowes.entity.ViajeMotivo;
import com.lowes.entity.ViajeTipoAlimentos;

public class ComprobacionAnticipoViajeDTO {

	private Integer idUsuarioSolicita;
	private Compania compania;
	private Locacion locacion;
	private Moneda moneda;
	private FormaPago formaPago;
	private String fechaInicio;
	private String fechaRegreso;
	private String resultadoViaje;
	private BigDecimal importe;
	private String strImporte;
	private short motivoViaje;
	private short destinoViaje;
	private Integer numeroPersonas;
	private Boolean preguntaExtranjero1;
	private Boolean preguntaExtranjero2;
	private Integer idSolicitudSession;
	private Integer idSolicitudComprobacionSession;
	private Integer idSolicitudAnticipoSession;
	private Integer idViajeTipoAlimentos;
	private ViajeTipoAlimentos viajeTipoAlimentos;
	private Integer idTipoSolicitud;
	private ViajeMotivo viajeMotivo;
	private ViajeDestino viajeDestino;
	private String otroMotivo;
	private String otroDestino;
	private List<ComprobacionAnticipoViajeDesgloseDTO> comprobacionAntDesglose = new ArrayList<>();
	private boolean conAnticipo;
	
	private Integer idComprobacionDeposito;
	private String fecha_deposito;
	private String importeDeposito;

	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}
	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
	}
	
	
	public Compania getCompania() {
		return compania;
	}
	public void setCompania(Compania compania) {
		this.compania = compania;
	}
	
	
	public Locacion getLocacion() {
		return locacion;
	}
	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	
	
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	
	
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	
	
	
	
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaRegreso() {
		return fechaRegreso;
	}
	public void setFechaRegreso(String fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}
	
	
	public String getResultadoViaje() {
		return resultadoViaje;
	}
	public void setResultadoViaje(String resultadoViaje) {
		this.resultadoViaje = resultadoViaje;
	}
	
	
	public short getMotivoViaje() {
		return motivoViaje;
	}
	public void setMotivoViaje(short motivoViaje) {
		this.motivoViaje = motivoViaje;
	}

	
	public short getDestinoViaje() {
		return destinoViaje;
	}
	public void setDestinoViaje(short destinoViaje) {
		this.destinoViaje = destinoViaje;
	}
	
	
	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}
	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}
	

	public Boolean getPreguntaExtranjero1() {
		return preguntaExtranjero1;
	}
	public void setPreguntaExtranjero1(Boolean preguntaExtranjero1) {
		this.preguntaExtranjero1 = preguntaExtranjero1;
	}
	
	public Boolean getPreguntaExtranjero2() {
		return preguntaExtranjero2;
	}
	public void setPreguntaExtranjero2(Boolean preguntaExtranjero2) {
		this.preguntaExtranjero2 = preguntaExtranjero2;
	}
	
	public ViajeMotivo getViajeMotivo() {
		return viajeMotivo;
	}
	public void setViajeMotivo(ViajeMotivo viajeMotivo) {
		this.viajeMotivo = viajeMotivo;
	}
	public List<ComprobacionAnticipoViajeDesgloseDTO> getComprobacionAntDesglose() {
		return comprobacionAntDesglose;
	}
	public void setComprobacionAntDesglose(List<ComprobacionAnticipoViajeDesgloseDTO> comprobacionAntDesglose) {
		this.comprobacionAntDesglose = comprobacionAntDesglose;
	}	
	public Integer getIdSolicitudComprobacionSession() {
		return idSolicitudComprobacionSession;
	}
	public void setIdSolicitudComprobacionSession(Integer idSolicitudComprobacionSession) {
		this.idSolicitudComprobacionSession = idSolicitudComprobacionSession;
	}
	public Integer getIdSolicitudAnticipoSession() {
		return idSolicitudAnticipoSession;
	}
	public void setIdSolicitudAnticipoSession(Integer idSolicitudAnticipoSession) {
		this.idSolicitudAnticipoSession = idSolicitudAnticipoSession;
	}
	public Integer getIdTipoSolicitud() {
		return idTipoSolicitud;
	}
	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}
	public ViajeDestino getViajeDestino() {
		return viajeDestino;
	}
	public void setViajeDestino(ViajeDestino viajeDestino) {
		this.viajeDestino = viajeDestino;
	}
	public Integer getIdViajeTipoAlimentos() {
		return idViajeTipoAlimentos;
	}
	public void setIdViajeTipoAlimentos(Integer idViajeTipoAlimentos) {
		this.idViajeTipoAlimentos = idViajeTipoAlimentos;
	}
	public ViajeTipoAlimentos getViajeTipoAlimentos() {
		return viajeTipoAlimentos;
	}
	public void setViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		this.viajeTipoAlimentos = viajeTipoAlimentos;
	}
	public String getOtroMotivo() {
		return otroMotivo;
	}
	public void setOtroMotivo(String otroMotivo) {
		this.otroMotivo = otroMotivo;
	}
	public String getOtroDestino() {
		return otroDestino;
	}
	public void setOtroDestino(String otroDestino) {
		this.otroDestino = otroDestino;
	}
	public Integer getIdUsuarioSolicita() {
		return idUsuarioSolicita;
	}
	public void setIdUsuarioSolicita(Integer idUsuarioSolicita) {
		this.idUsuarioSolicita = idUsuarioSolicita;
	}
	public String getStrImporte() {
		return strImporte;
	}
	public void setStrImporte(String strImporte) {
		this.strImporte = strImporte;
	}
	public boolean isConAnticipo() {
		return conAnticipo;
	}
	public void setConAnticipo(boolean conAnticipo) {
		this.conAnticipo = conAnticipo;
	}
	public Integer getIdComprobacionDeposito() {
		return idComprobacionDeposito;
	}
	public void setIdComprobacionDeposito(Integer idComprobacionDeposito) {
		this.idComprobacionDeposito = idComprobacionDeposito;
	}
	public String getFecha_deposito() {
		return fecha_deposito;
	}
	public void setFecha_deposito(String fecha_deposito) {
		this.fecha_deposito = fecha_deposito;
	}
	public String getImporteDeposito() {
		return importeDeposito;
	}
	public void setImporteDeposito(String importeDeposito) {
		this.importeDeposito = importeDeposito;
	}

}
