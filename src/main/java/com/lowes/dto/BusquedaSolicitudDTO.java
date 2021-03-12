package com.lowes.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusquedaSolicitudDTO {
	
	private Integer idCompaniaFiltro;
	private Integer idSolicitud;
	private Integer idTipoSolicitudFiltro;
	
	private String fechaInicialFiltro;
	private String fechaFinalFiltro;
	private String fechaInicialPagAnticipoFiltro;
	private String fechaFinalPagAnticipoFiltro;
	
	private Integer idEstadoSolicitudFiltro;
	
	private BigDecimal importeMenor;
	private BigDecimal importeMayor;
	private String strImporteMenor;
	private String strImporteMayor;

	private Integer idMonedaFiltro;
	private Integer idLocacionFiltro;
	private Integer idUsuarioSolicitanteFiltro;
	private Integer idUsuarioAutorizadorFiltro;
	
	private Date fechaInicial;
	private Date fechaFinal;
	private Date fechaInicialPagAnticipo;
	private Date fechaFinalPagAnticipo;
	private Integer idProveedorFiltro;
	private Boolean cuentaConDeposito;
	private Boolean sinComprobanteFiscal;
	
	
	public BusquedaSolicitudDTO() {
	}


	public Integer getIdCompaniaFiltro() {
		return idCompaniaFiltro;
	}


	public void setIdCompaniaFiltro(Integer idCompaniaFiltro) {
		this.idCompaniaFiltro = idCompaniaFiltro;
	}


	public Integer getIdSolicitud() {
		return idSolicitud;
	}


	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	public Integer getIdTipoSolicitudFiltro() {
		return idTipoSolicitudFiltro;
	}


	public void setIdTipoSolicitudFiltro(Integer idTipoSolicitudFiltro) {
		this.idTipoSolicitudFiltro = idTipoSolicitudFiltro;
	}


	public String getFechaInicialFiltro() {
		return fechaInicialFiltro;
	}


	public void setFechaInicialFiltro(String fechaInicialFiltro) {
		this.fechaInicialFiltro = fechaInicialFiltro;
		if (!fechaInicialFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaInicialFiltro);
				this.setFechaInicial(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}


	public String getFechaFinalFiltro() {
		return fechaFinalFiltro;
	}


	public void setFechaFinalFiltro(String fechaFinalFiltro) {
		this.fechaFinalFiltro = fechaFinalFiltro;
		
		if (!fechaFinalFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaFinalFiltro);
				this.setFechaFinal(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}


	public Integer getIdEstadoSolicitudFiltro() {
		return idEstadoSolicitudFiltro;
	}


	public void setIdEstadoSolicitudFiltro(Integer idEstadoSolicitudFiltro) {
		this.idEstadoSolicitudFiltro = idEstadoSolicitudFiltro;
	}
	
	
	public BigDecimal getImporteMenor() {
		return importeMenor;
	}


	public void setImporteMenor(BigDecimal importeMenor) {
		this.importeMenor = importeMenor;
	}


	public BigDecimal getImporteMayor() {
		return importeMayor;
	}


	public void setImporteMayor(BigDecimal importeMayor) {
		this.importeMayor = importeMayor;
	}


	public String getStrImporteMenor() {
		return strImporteMenor;
	}


	public void setStrImporteMenor(String strImporteMenor) {
		this.strImporteMenor = strImporteMenor;
	}


	public String getStrImporteMayor() {
		return strImporteMayor;
	}


	public void setStrImporteMayor(String strImporteMayor) {
		this.strImporteMayor = strImporteMayor;
	}


	public Integer getIdMonedaFiltro() {
		return idMonedaFiltro;
	}


	public void setIdMonedaFiltro(Integer idMonedaFiltro) {
		this.idMonedaFiltro = idMonedaFiltro;
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


	public Date getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	public Date getFechaFinal() {
		return fechaFinal;
	}


	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Integer getIdProveedorFiltro() {
		return idProveedorFiltro;
	}


	public void setIdProveedorFiltro(Integer idProveedorFiltro) {
		this.idProveedorFiltro = idProveedorFiltro;
	}
	
	public String getFechaInicialPagAnticipoFiltro() {
		return fechaInicialPagAnticipoFiltro;
	}


	public void setFechaInicialPagAnticipoFiltro(String fechaInicialPagAnticipoFiltro) {
		this.fechaInicialPagAnticipoFiltro = fechaInicialPagAnticipoFiltro;
		if (!fechaInicialPagAnticipoFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaInicialPagAnticipoFiltro);
				this.setFechaInicialPagAnticipo(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getFechaFinalPagAnticipoFiltro() {
		return fechaFinalPagAnticipoFiltro;
	}


	public void setFechaFinalPagAnticipoFiltro(String fechaFinalPagAnticipoFiltro) {
		this.fechaFinalPagAnticipoFiltro = fechaFinalPagAnticipoFiltro;
		if (!fechaFinalPagAnticipoFiltro.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			try {

				Date fecha = formatter.parse(fechaFinalPagAnticipoFiltro);
				this.setFechaFinalPagAnticipo(fecha);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Date getFechaInicialPagAnticipo() {
		return fechaInicialPagAnticipo;
	}


	public void setFechaInicialPagAnticipo(Date fechaInicialPagAnticipo) {
		this.fechaInicialPagAnticipo = fechaInicialPagAnticipo;
	}
	
	public Date getFechaFinalPagAnticipo() {
		return fechaFinalPagAnticipo;
	}


	public void setFechaFinalPagAnticipo(Date fechaFinalPagAnticipo) {
		this.fechaFinalPagAnticipo = fechaFinalPagAnticipo;
	}
	
	public Boolean getCuentaConDeposito() {
		return cuentaConDeposito;
	}


	public void setCuentaConDeposito(Boolean cuentaConDeposito) {
		this.cuentaConDeposito = cuentaConDeposito;
	}


	public Boolean getSinComprobanteFiscal() {
		return sinComprobanteFiscal;
	}


	public void setSinComprobanteFiscal(Boolean sinComprobanteFiscal) {
		this.sinComprobanteFiscal = sinComprobanteFiscal;
	}
}
