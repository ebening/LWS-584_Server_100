package com.lowes.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusquedaFacturaDTO {
	
	private Integer idCompaniaFiltro;
	private Integer idProveedorFiltro;
	private String facturaFiltro;
	private Integer idTipoSolicitudFiltro;
	private Integer idEstadoSolicitudFiltro;
	private Integer idLocacionFiltro;
	private Integer idUsuarioSolicitanteFiltro;
	private Integer idUsuarioAutorizadorFiltro;
	private String fechaFacturaInicialFiltro;
	private String fechaFacturaFinalFiltro;
	private String fechaPagoFacturaInicialFiltro;
	private String fechaPagoFacturaFinalFiltro;
	private BigDecimal importeInicialFiltro;
	private BigDecimal importeFinalFiltro;
	private String strImporteInicialFiltro;
	private String strImporteFinalFiltro;
	private Integer idMonedaFiltro;
	
	private Date fechaFacturaInicial;
	private Date fechaFacturaFinal;
	private Date fechaPagoFacturaInicial;
	private Date fechaPagoFacturaFinal;
	
	public BusquedaFacturaDTO() {
	}

	public Integer getIdCompaniaFiltro() {
		return idCompaniaFiltro;
	}

	public void setIdCompaniaFiltro(Integer idCompaniaFiltro) {
		this.idCompaniaFiltro = idCompaniaFiltro;
	}

	public Integer getIdProveedorFiltro() {
		return idProveedorFiltro;
	}

	public void setIdProveedorFiltro(Integer idProveedorFiltro) {
		this.idProveedorFiltro = idProveedorFiltro;
	}

	public String getFacturaFiltro() {
		return facturaFiltro;
	}

	public void setFacturaFiltro(String facturaFiltro) {
		this.facturaFiltro = facturaFiltro;
	}

	public Integer getIdTipoSolicitudFiltro() {
		return idTipoSolicitudFiltro;
	}

	public void setIdTipoSolicitudFiltro(Integer idTipoSolicitudFiltro) {
		this.idTipoSolicitudFiltro = idTipoSolicitudFiltro;
	}

	public Integer getIdEstadoSolicitudFiltro() {
		return idEstadoSolicitudFiltro;
	}

	public void setIdEstadoSolicitudFiltro(Integer idEstadoSolicitudFiltro) {
		this.idEstadoSolicitudFiltro = idEstadoSolicitudFiltro;
	}

	public Integer getIdLocacionFiltro() {
		return idLocacionFiltro;
	}

	public void setIdLocacionFiltro(Integer idLocacionFiltro) {
		this.idLocacionFiltro = idLocacionFiltro;
	}

	public Integer getIdUsuarioSolicitanteFiltro() {
		return idUsuarioSolicitanteFiltro;
	}

	public void setIdUsuarioSolicitanteFiltro(Integer idUsuarioSolicitanteFiltro) {
		this.idUsuarioSolicitanteFiltro = idUsuarioSolicitanteFiltro;
	}

	public Integer getIdUsuarioAutorizadorFiltro() {
		return idUsuarioAutorizadorFiltro;
	}

	public void setIdUsuarioAutorizadorFiltro(Integer idUsuarioAutorizadorFiltro) {
		this.idUsuarioAutorizadorFiltro = idUsuarioAutorizadorFiltro;
	}

	public String getFechaFacturaInicialFiltro() {
		return fechaFacturaInicialFiltro;
	}

	public void setFechaFacturaInicialFiltro(String fechaFacturaInicialFiltro) {
		this.fechaFacturaInicialFiltro = fechaFacturaInicialFiltro;
		
		if (!fechaFacturaInicialFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaFacturaInicialFiltro);
				this.setFechaFacturaInicial(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFechaFacturaFinalFiltro() {
		return fechaFacturaFinalFiltro;
	}

	public void setFechaFacturaFinalFiltro(String fechaFacturaFinalFiltro) {
		this.fechaFacturaFinalFiltro = fechaFacturaFinalFiltro;
		
		if (!fechaFacturaFinalFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaFacturaFinalFiltro);
				this.setFechaFacturaFinal(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getFechaPagoFacturaInicialFiltro() {
		return fechaPagoFacturaInicialFiltro;
	}

	public void setFechaPagoFacturaInicialFiltro(String fechaPagoFacturaInicialFiltro) {
		this.fechaPagoFacturaInicialFiltro = fechaPagoFacturaInicialFiltro;
		
		if (!fechaPagoFacturaInicialFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaPagoFacturaInicialFiltro);
				this.setFechaPagoFacturaInicial(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFechaPagoFacturaFinalFiltro() {
		return fechaPagoFacturaFinalFiltro;
	}

	public void setFechaPagoFacturaFinalFiltro(String fechaPagoFacturaFinalFiltro) {
		this.fechaPagoFacturaFinalFiltro = fechaPagoFacturaFinalFiltro;
		
		if (!fechaPagoFacturaFinalFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaPagoFacturaFinalFiltro);
				this.setFechaPagoFacturaFinal(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public BigDecimal getImporteInicialFiltro() {
		return importeInicialFiltro;
	}

	public void setImporteInicialFiltro(BigDecimal importeInicialFiltro) {
		this.importeInicialFiltro = importeInicialFiltro;
	}

	public BigDecimal getImporteFinalFiltro() {
		return importeFinalFiltro;
	}

	public void setImporteFinalFiltro(BigDecimal importeFinalFiltro) {
		this.importeFinalFiltro = importeFinalFiltro;
	}

	public String getStrImporteInicialFiltro() {
		return strImporteInicialFiltro;
	}

	public void setStrImporteInicialFiltro(String strImporteInicialFiltro) {
		this.strImporteInicialFiltro = strImporteInicialFiltro;
	}

	public String getStrImporteFinalFiltro() {
		return strImporteFinalFiltro;
	}

	public void setStrImporteFinalFiltro(String strImporteFinalFiltro) {
		this.strImporteFinalFiltro = strImporteFinalFiltro;
	}

	public Integer getIdMonedaFiltro() {
		return idMonedaFiltro;
	}

	public void setIdMonedaFiltro(Integer idMonedaFiltro) {
		this.idMonedaFiltro = idMonedaFiltro;
	}

	public Date getFechaFacturaInicial() {
		return fechaFacturaInicial;
	}

	public void setFechaFacturaInicial(Date fechaFacturaInicial) {
		this.fechaFacturaInicial = fechaFacturaInicial;
	}

	public Date getFechaFacturaFinal() {
		return fechaFacturaFinal;
	}

	public void setFechaFacturaFinal(Date fechaFacturaFinal) {
		this.fechaFacturaFinal = fechaFacturaFinal;
	}		
	
	public Date getFechaPagoFacturaInicial() {
		return fechaPagoFacturaInicial;
	}

	public void setFechaPagoFacturaInicial(Date fechaPagoFacturaInicial) {
		this.fechaPagoFacturaInicial = fechaPagoFacturaInicial;
	}

	public Date getFechaPagoFacturaFinal() {
		return fechaPagoFacturaFinal;
	}

	public void setFechaPagoFacturaFinal(Date fechaPagoFacturaFinal) {
		this.fechaPagoFacturaFinal = fechaPagoFacturaFinal;
	}
	
}