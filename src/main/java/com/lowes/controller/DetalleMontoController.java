package com.lowes.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Factura;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FacturaGastoViaje;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.entity.SolicitudAnticipoViajeAerolinea;
import com.lowes.entity.SolicitudAnticipoViajeConceptos;
import com.lowes.service.FacturaGastoViajeService;
import com.lowes.service.FacturaKilometrajeService;
import com.lowes.service.FacturaService;
import com.lowes.service.SolicitudAnticipoViajeAerolineaService;
import com.lowes.service.SolicitudAnticipoViajeConceptosService;
import com.lowes.service.SolicitudAnticipoViajeService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class DetalleMontoController {

	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private SolicitudAnticipoViajeService solicitudAnticipoViajeService;
	@Autowired
	private SolicitudAnticipoViajeAerolineaService solicitudAnticipoViajeAerolineaService;
	@Autowired
	private SolicitudAnticipoViajeConceptosService solicitudAnticipoViajeConceptosService;
	
	@Autowired
	private FacturaKilometrajeService facturaKilometrajeService;
	
	@Autowired
	private FacturaGastoViajeService facturaGastoViajeService;
	
	public DetalleMontoController() {
		logger.info("DetalleMontoController()");
	}

	@RequestMapping("/detalleMontoFacturas")
	public ModelAndView detalleMontoFacturas(@ModelAttribute Solicitud solicitud) {
		List<Solicitud> solicitudList = solicitudService.getAllSolicitud();
		return new ModelAndView("detalleMontoFacturas", "solicitudList", solicitudList);
	}

	@RequestMapping("/detalleMontoReembolso")
	public ModelAndView detalleMontoReembolso(@ModelAttribute Solicitud solicitud) {
		List<Solicitud> solicitudList = solicitudService.getAllSolicitud();
		return new ModelAndView("detalleMontoReembolso", "solicitudList", solicitudList);
	}

	// metodo ajax para la carga dinamica de informaci�n Factura Desglose
	@RequestMapping(value = "/getMontoFactura", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = null;
		List<Factura> facturaList = null;

		String json = null;
		ObjectMapper map = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tabla = new StringBuilder();
		BigDecimal totales = new BigDecimal(0.0);

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
			
			facturaList = solicitud.getFacturas();
			BigDecimal montoTotal = solicitud.getMontoTotal();

			if (facturaList != null && facturaList.size() > Etiquetas.CERO) {
				/*
				 * no mercancias obtener factura y desglose a partir de la
				 * solicitud
				 */
				Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);
				List<FacturaDesglose> fd = factura.getFacturaDesgloses();

				String par = (factura.getPar() != null) ? factura.getPar() : "";
				result.put("par", par);

				/*
				 * Validar que no sea null el desglose
				 * 
				 */
				Integer contadorlinea = Etiquetas.UNO;

				if (fd != null && fd.size() > Etiquetas.CERO) {
					for (FacturaDesglose f : fd) {
						tabla.append("<tr>");
						tabla.append("<td>").append(contadorlinea.toString()).append("</td>");
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(f.getSubtotal()).append("</span>").append("</td>");
						totales = totales.add(f.getSubtotal());
						tabla.append("<td>").append(f.getLocacion().getNumeroDescripcionLocacion()).append("</td>");
						tabla.append("<td>").append(f.getCuentaContable().getNumeroDescripcionCuentaContable()).append("</td>");
						tabla.append("<td>").append(f.getConcepto()).append("</td>");
						// track as asset
						String aid = (f.getAid() != null) ? f.getAid().getNumeroDescripcionAid() : "";
						tabla.append("<td>").append(aid).append("</td>");

						String categoriaMayor = (f.getCategoriaMayor() != null) ? f.getCategoriaMayor().getDescripcion()
								: "";
						tabla.append("<td>").append(categoriaMayor).append("</td>");

						String categoriaMenor = (f.getCategoriaMenor() != null) ? f.getCategoriaMenor().getDescripcion()
								: "";
						tabla.append("<td>").append(categoriaMenor).append("</td>");
						tabla.append("</tr>");

						contadorlinea++;
					}
					
					String importeRojo = "";
					if (fd.get(0).getFactura().getTipoFactura().getIdTipoFactura() == Etiquetas.DOS) importeRojo = "importe-rojo";
					
					tabla.append("<tr>");
					tabla.append("<th style=\"border: 0\">").append(etiqueta.TOTALES + ": ").append("</th>");
					tabla.append("<td style=\"border: none; text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(totales.toString()).append("</span> </td>");
					tabla.append("<td style=\"border: none;\"><span class=\"\" style=\"diplay:block; align:right\">").append("</span></td>");
					tabla.append("<td style=\"border: none\">").append("</td>");
					tabla.append("<td style=\"border: none\">").append("</td>");
					tabla.append("<td style=\"border: none\">").append("</td>");					
					tabla.append("<td style=\"border: none\">").append("</td>");
					tabla.append("<td style=\"border: none\">").append("</td>");
					tabla.append("</tr>");
					tabla.append("<tr>");
					tabla.append("<th colspan=\"7\" style=\"border: 0; text-align:right\">").append(etiqueta.IMPORTE_TOTAL + ": ").append("</th>");
					tabla.append("<td class=\"importe-total "+importeRojo+"\" style=\"border: 0; text-align:right\">").append("$").append("<span class=\"currencyFormat importe-total \">").append(montoTotal.toString()).append("</span>").append("</td>");
					tabla.append("</tr>");
				}
			}
		}
		
		Iterator<Factura> facturas = facturaList.iterator();
		while (facturas.hasNext()) {
				Factura f = facturas.next();
				totales = totales.add(f.getTotal());
			}
		
		result.put("tabla", tabla.toString());
		result.put("totales", totales.toString());
		result.put("trackAsset", String.valueOf(solicitud.getTrackAsset()));
		result.put("libroContable",solicitud.getCompania().getDescripcion());

		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
				System.out.println("Send Message  :::::::: : " + json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo ajax para la carga dinamica de informaci�n Reembolso
	@RequestMapping(value = "/getMontoReembolso", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getMontoReembolso(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = null;
		List<Factura> facturaList = null;

		String json = null;
		ObjectMapper map = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tabla = new StringBuilder();
		BigDecimal totales = new BigDecimal(0.0);
		BigDecimal iva = new BigDecimal(0.0);
		BigDecimal ieps = new BigDecimal(0.0);
		BigDecimal rTotal = new BigDecimal(0.0);
		String subtotal, locacion, cuentaContable, factura_folio, conceptoGasto, iiva, iieps;

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
			// facturaList = solicitud.getFacturas();
			facturaList = facturaService.getAllFacturaBySolicitud(Integer.parseInt(intxnId));

			if (facturaList != null && facturaList.size() > Etiquetas.CERO) {
				/*
				 * reembolso obtener factura a partir de la solicitud
				 */

				Integer contadorlinea = Etiquetas.UNO;
				for (Factura f : facturaList) {
					
					String razonSocial = "";
					String serie = "";
					String folio = "";
					
					if(f.getProveedor() != null) {
						razonSocial = f.getProveedor().getDescripcion()+" - ";
					} else if(f.getProveedorLibre() != null){
						razonSocial = f.getProveedorLibre().getDescripcion()+" - ";
					}
					
					serie = (f.getSerieFactura() != null) ? f.getSerieFactura()+" - " : "";
					folio = (f.getFactura() != null) ? f.getFactura() : "";
					
					String proveedor = razonSocial+serie+folio;
					
					
					
					tabla.append("<tr>");
					tabla.append("<td>").append(contadorlinea.toString()).append("</td>");

					subtotal = (f.getSubtotal() != null) ? f.getSubtotal().toString() : "";
					tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(subtotal).append("</span>").append("</td>");
					if (f.getSubtotal() != null)
						totales = totales.add(f.getSubtotal());

					locacion = (f.getLocacion() != null) ? f.getLocacion().getNumeroDescripcionLocacion() : "";
					tabla.append("<td>").append(locacion).append("</td>");

					cuentaContable = (f.getCuentaContable() != null) ? f.getCuentaContable().getNumeroDescripcionCuentaContable() : "";
					tabla.append("<td>").append(cuentaContable).append("</td>");

					factura_folio = (f.getProveedor() != null && f.getFactura() != null)
							? f.getProveedor().getDescripcion() + " - " + f.getFactura() : proveedor;
					tabla.append("<td>").append(factura_folio).append("</td>");

					conceptoGasto = (f.getConceptoGasto() != null) ? f.getConceptoGasto() : "";
					tabla.append("<td>").append(conceptoGasto).append("</td>");

					iiva = (f.getIva() != null) ? f.getIva().toString() : "";
					tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iiva).append("</td>");
					if (f.getIva() != null)
						iva = iva.add(f.getIva());

					iieps = (f.getIeps() != null) ? f.getIeps().toString() : "";
					tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iieps).append("</td>");
					if (f.getIeps() != null)
						ieps = ieps.add(f.getIeps());
					tabla.append("</tr>");

					contadorlinea++;
				}
			}
		}

		rTotal = rTotal.add(solicitud.getMontoTotal());
		result.put("tabla", tabla.toString());
		result.put("totales", totales.toString());
		result.put("iva", iva.toString());
		result.put("ieps", ieps.toString());
		result.put("rTotal", rTotal.toString());

		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	
	// metodo ajax para la carga dinamica de informaci�n Kilometraje
	@RequestMapping(value = "/getMontoKilometraje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getMontoKilometraje(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = null;
		List<FacturaKilometraje> facturasKilometrajes = null;

		String json = null;
		ObjectMapper map = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tabla = new StringBuilder();
		BigDecimal totales = BigDecimal.ZERO;
		Double km = new Double(0.0);
		BigDecimal rTotal = BigDecimal.ZERO;
		String fecha, motivo, origen, destino, viajes, kmRecorreido, importe;

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
			// facturaList = solicitud.getFacturas();
			facturasKilometrajes = facturaKilometrajeService.getAllFacturaKilometrajeByIdSolicitud(solicitud.getIdSolicitud());

			if (facturasKilometrajes != null && facturasKilometrajes.size() > Etiquetas.CERO) {
				/*
				 * kilometraje obtener facturakilometraje a partir de la solicitud
				 */

				Integer contadorlinea = Etiquetas.UNO;
				for (FacturaKilometraje f : facturasKilometrajes) {
					
					
					tabla.append("<tr>");
					tabla.append("<td>").append(contadorlinea.toString()).append("</td>");
					
					fecha = (f.getKilometrajeFecha() != null) ? Utilerias.convertDateFormat(f.getKilometrajeFecha()) : "";
					tabla.append("<td>").append(fecha).append("</td>");

					motivo = (f.getMotivo() != null) ? f.getMotivo() : "";
					tabla.append("<td>").append(motivo).append("</td>");

					origen = (f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdOrigen().getDescripcion() != null) ? f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdOrigen().getDescripcion() : "";
					tabla.append("<td>").append(origen).append("</td>");

					destino = (f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdDestino().getDescripcion() != null) ? f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdDestino().getDescripcion() : "";
					tabla.append("<td>").append(destino).append("</td>");

					viajes = (f.getNumeroViajes() != null) ? f.getNumeroViajes().toString() : "";
					tabla.append("<td>").append(viajes).append("</td>");

					kmRecorreido = (f.getkilometrajeRecorridoCalculado() != null) ? f.getkilometrajeRecorridoCalculado().toString() : "";
					tabla.append("<td style=\"text-align: center\">").append(kmRecorreido).append("</td>");
					if (f.getKilometrajeRecorrido().getNumeroKilometros() != null)
						km += f.getkilometrajeRecorridoCalculado();

					importe = (f.getImporte() != null) ? f.getImporte().toString() : "";
					tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(importe).append("</td>");
					if (f.getImporte() != null)
						totales = totales.add(f.getImporte());
					tabla.append("</tr>");

					contadorlinea++;
				}
			}
		}

		rTotal = rTotal.add(solicitud.getMontoTotal());
		result.put("tabla", tabla.toString());
		result.put("km", km.toString());
		result.put("importe", totales.toString());
		result.put("rTotal", rTotal.toString());

		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	// metodo ajax para la carga dinamica de informaci�n Anticipo
		@RequestMapping(value = "/getMontoAnticipo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> getMontoAnticipo(HttpSession session, @RequestParam String intxnId,
				HttpServletRequest request, HttpServletResponse response) {

			// objeto de respuesta
			Solicitud solicitud = null;

			String json = null;
			ObjectMapper map = new ObjectMapper();
			HashMap<String, String> result = new HashMap<String, String>();
			StringBuilder tabla = new StringBuilder();
			BigDecimal rTotal = BigDecimal.ZERO;
			String importe;

			// consulta
			if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
				solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));

				Integer contadorlinea = Etiquetas.UNO;
				tabla.append("<tr>");
				tabla.append("<td>").append(contadorlinea.toString()).append("</td>");
				importe = (solicitud.getMontoTotal() != null) ? solicitud.getMontoTotal().toString() : "";
				tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(importe).append("</td>");
				tabla.append("</tr>");
			}

			rTotal = rTotal.add(solicitud.getMontoTotal());
			result.put("tabla", tabla.toString());
			result.put("rTotal", rTotal.toString());

			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		
		@RequestMapping(value = "/getMontoComprobacionAnticipo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> getMontoComprobacionAnticipo(HttpSession session, @RequestParam String intxnId,
				HttpServletRequest request, HttpServletResponse response) {

			// objeto de respuesta
			Solicitud solicitud = null;
			List<Factura> facturaList = null;

			String json = null;
			ObjectMapper map = new ObjectMapper();
			HashMap<String, String> result = new HashMap<String, String>();
			StringBuilder tabla = new StringBuilder();
			BigDecimal totales = new BigDecimal(0.0);
			BigDecimal iva = new BigDecimal(0.0);
			BigDecimal importeTotal = new BigDecimal(0.0);
			BigDecimal rTotal = new BigDecimal(0.0);
			String subtotal, locacion, cuentaContable, factura_folio, conceptoGasto, iiva, total;

			// consulta
			if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
				solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
				facturaList = solicitud.getFacturas();

				if (facturaList != null && facturaList.size() > Etiquetas.CERO) {
					/*
					 * reembolso obtener factura a partir de la solicitud
					 */

					Integer contadorlinea = Etiquetas.UNO;
					for (Factura f : facturaList) {
						
						String razonSocial = "";
						String serie = "";
						String folio = "";
						
						if(f.getProveedor() != null) {
							razonSocial = f.getProveedor().getDescripcion()+" - ";
						} else if(f.getProveedorLibre() != null){
							razonSocial = f.getProveedorLibre().getDescripcion()+" - ";
						}
						
						serie = (f.getSerieFactura() != null) ? f.getSerieFactura()+" - " : "";
						folio = (f.getFactura() != null) ? f.getFactura() : "";
						
						String proveedor = razonSocial+serie+folio;
						
						
						
						tabla.append("<tr>");
						tabla.append("<td>").append(contadorlinea.toString()).append("</td>");

						conceptoGasto = (f.getConceptoGasto() != null) ? f.getConceptoGasto() : "";
						tabla.append("<td>").append(conceptoGasto).append("</td>");

						factura_folio = (f.getProveedor() != null && f.getFactura() != null)
								? f.getProveedor().getDescripcion() + " - " + f.getFactura() : proveedor;
						tabla.append("<td>").append(factura_folio).append("</td>");
						
						subtotal = (f.getSubtotal() != null) ? f.getSubtotal().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(subtotal).append("</span>").append("</td>");
						if (f.getSubtotal() != null)
							totales = totales.add(f.getSubtotal());
						
						iiva = (f.getIva() != null) ? f.getIva().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iiva).append("</td>");
						if (f.getIva() != null)
							iva = iva.add(f.getIva());

						total = (f.getTotal() != null) ? f.getTotal().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(total).append("</td>");
						if (f.getTotal() != null)
							importeTotal = importeTotal.add(f.getTotal());
						tabla.append("</tr>");

						contadorlinea++;
					}
				}
			}

			rTotal = rTotal.add(solicitud.getMontoTotal());
			result.put("tabla", tabla.toString());
			result.put("subtotales", totales.toString());
			result.put("iva", iva.toString());
			result.put("totales", importeTotal.toString());
			result.put("rTotal", rTotal.toString());

			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		@RequestMapping(value = "/getMontoAnticipoGastoDeViaje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> getMontoAnticipoGastoDeViaje(HttpSession session, @RequestParam String intxnId,
				HttpServletRequest request, HttpServletResponse response) {

			// objeto de respuesta
			Solicitud solicitud = null;

			String json = null;
			ObjectMapper map = new ObjectMapper();
			HashMap<String, String> result = new HashMap<String, String>();
			StringBuilder tabla = new StringBuilder();
			BigDecimal rTotal = BigDecimal.ZERO;
			String importe, descripcion;
			String motivo ="";

			// consulta
			if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
				solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
				SolicitudAnticipoViaje solicitudAnticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(solicitud.getIdSolicitud());
				if(solicitudAnticipoViaje != null){
					motivo = solicitudAnticipoViaje.getMotivoCotizacion();
					SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea = solicitudAnticipoViajeAerolineaService.getSolicitudAnticipoViajeAerolinea(solicitudAnticipoViaje.getIdSolicitudAerolineaSeleccionado());
					Integer contadorlinea = Etiquetas.CERO;
					if(solicitudAnticipoViajeAerolinea != null){
						contadorlinea = Etiquetas.UNO;
						tabla.append("<tr>");
						tabla.append("<td>").append(contadorlinea.toString()).append("</td>");
						descripcion = (solicitudAnticipoViajeAerolinea.getNombreAerolinea() != null) ? solicitudAnticipoViajeAerolinea.getNombreAerolinea() : "";
						tabla.append("<td>").append(descripcion).append("</td>");
						importe = solicitudAnticipoViajeAerolinea.getCostoAerolinea().toString();
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(importe).append("</td>");
						tabla.append("</tr>");
					}
					List<SolicitudAnticipoViajeConceptos> conceptos =  solicitudAnticipoViajeConceptosService.getAllSolicitudAnticipoViajeConceptoBySol(solicitudAnticipoViaje.getIdSolicitudAnticipoViaje());
					
					if (contadorlinea > 0)
						contadorlinea = Etiquetas.DOS;
					else
						contadorlinea = Etiquetas.UNO;
					
					for (SolicitudAnticipoViajeConceptos concepto : conceptos) {
						tabla.append("<tr>");
						tabla.append("<td>").append(contadorlinea.toString()).append("</td>");
						descripcion = (concepto.getViajeConcepto().getDescripcion() != null) ? concepto.getViajeConcepto().getDescripcion() : "";
						tabla.append("<td>").append(descripcion).append("</td>");
						importe = (concepto.getImporte()!= null) ? concepto.getImporte().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(importe).append("</td>");
						tabla.append("</tr>");
						contadorlinea ++;
					}
				}
			}
			rTotal = rTotal.add(solicitud.getMontoTotal());
			result.put("tabla", tabla.toString());
			result.put("rTotal", rTotal.toString());
			result.put("motivoCotizacion", motivo);
			

			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		
		@RequestMapping(value = "/getMontoAnticipoComprobacionGastoDeViaje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> getMontoAnticipoComprobacionGastoDeViaje(HttpSession session, @RequestParam String intxnId,
				HttpServletRequest request, HttpServletResponse response) {

			// objeto de respuesta
			Solicitud solicitud = null;
			List<Factura> facturaList = null;

			String json = null;
			ObjectMapper map = new ObjectMapper();
			HashMap<String, String> result = new HashMap<String, String>();
			StringBuilder tabla = new StringBuilder();
			BigDecimal totales = new BigDecimal(0.0);
			BigDecimal iva = new BigDecimal(0.0);
			BigDecimal importeTotal = new BigDecimal(0.0);
			BigDecimal totalOtrosImpuestos = new BigDecimal(0.0);
			BigDecimal ieps = new BigDecimal(0.0);
			BigDecimal otrosImpuestos = new BigDecimal(0.0);
			BigDecimal rTotal = new BigDecimal(0.0);
			String subtotal, locacion, cuentaContable, factura_folio, conceptoGasto, iiva, total, fechaFactura, fechaGasto, comercio, tipoFactura, ciudad, iieps, iOtrosImpuestos, numeroPersonas;

			// consulta
			if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
				solicitud = solicitudService.getSolicitud(Integer.parseInt(intxnId));
				facturaList = solicitud.getFacturas();
				if (facturaList != null && facturaList.size() > Etiquetas.CERO) {
					Integer contadorlinea = Etiquetas.UNO;
					for (Factura f : facturaList) {
						FacturaGastoViaje factDesgViaje = facturaGastoViajeService.getFacturaGastoViajeByIdFactura(f.getIdFactura());
						
						String razonSocial = "";
						String serie = "";
						String folio = "";
						
						if(f.getProveedor() != null) {
							razonSocial = f.getProveedor().getDescripcion()+" - ";
						} else if(f.getProveedorLibre() != null){
							razonSocial = f.getProveedorLibre().getDescripcion()+" - ";
						}
						
						serie = (f.getSerieFactura() != null) ? f.getSerieFactura()+" - " : "";
						folio = (f.getFactura() != null) ? f.getFactura() : "";
						
						String proveedor = razonSocial+serie+folio;
						
						
						tabla.append("<tr>");
						tabla.append("<td>").append(contadorlinea.toString()).append("</td>");

						tipoFactura = (f.getTipoFactura() != null) ? (f.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA) ? "Factura": "Comprobante" : "";
						tabla.append("<td>").append(tipoFactura).append("</td>");
						
						fechaFactura = (f.getFechaFactura() != null) ? Utilerias.convertDateFormat(f.getFechaFactura()) : "";
						tabla.append("<td>").append(fechaFactura).append("</td>");
						
						fechaGasto = (factDesgViaje.getFechaGasto() != null) ? Utilerias.convertDateFormat(factDesgViaje.getFechaGasto()) : "";
						tabla.append("<td>").append(fechaGasto).append("</td>");
						
						comercio = (factDesgViaje.getComercio() != null) ? factDesgViaje.getComercio() : "";
						tabla.append("<td>").append(comercio).append("</td>");
						
						
						conceptoGasto = (f.getConceptoGasto() != null) ? f.getConceptoGasto() : "";
						tabla.append("<td>").append(conceptoGasto).append("</td>");

//						factura_folio = (f.getProveedor() != null && f.getFactura() != null)
//								? f.getProveedor().getDescripcion() + " - " + f.getFactura() : proveedor;
//						tabla.append("<td>").append(factura_folio).append("</td>");
						ciudad = (factDesgViaje.getCiudad() != null) ? factDesgViaje.getCiudad() : "";
						tabla.append("<td>").append(ciudad).append("</td>");
						
						subtotal = (f.getSubtotal() != null) ? f.getSubtotal().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(subtotal).append("</span>").append("</td>");
						if (f.getSubtotal() != null)
							totales = totales.add(f.getSubtotal());
						
						iiva = (f.getIva() != null) ? f.getIva().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iiva).append("</td>");
						if (f.getIva() != null)
							iva = iva.add(f.getIva());

						iieps = (f.getIeps() != null) ? f.getIeps().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iieps).append("</td>");
						if (f.getIeps() != null)
							ieps = ieps.add(f.getIeps());
						
						iOtrosImpuestos = (factDesgViaje.getOtrosImpuestos() != null) ? factDesgViaje.getOtrosImpuestos().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(iOtrosImpuestos).append("</td>");
						if (factDesgViaje.getOtrosImpuestos() != null)
							otrosImpuestos = otrosImpuestos.add(factDesgViaje.getOtrosImpuestos());
						
						total = (f.getTotal() != null) ? f.getTotal().toString() : "";
						tabla.append("<td style=\"text-align: right\">").append("$").append("<span class=\"currencyFormat\">").append(total).append("</td>");
						if (f.getTotal() != null)
							importeTotal = importeTotal.add(f.getTotal());
						
						locacion = (f.getLocacion() != null) ? f.getLocacion().getNumeroDescripcionLocacion() : "";
						tabla.append("<td>").append(locacion).append("</td>");

						cuentaContable = (f.getCuentaContable() != null) ? f.getCuentaContable().getNumeroDescripcionCuentaContable() : "";
						tabla.append("<td>").append(cuentaContable).append("</td>");
						
						numeroPersonas = (factDesgViaje.getNumeroPersonas() != null) ? factDesgViaje.getNumeroPersonas().toString() : "";
						tabla.append("<td>").append(numeroPersonas).append("</td>");
						
						tabla.append("</tr>");

						contadorlinea++;
					}
				}
			}

			rTotal = rTotal.add(solicitud.getMontoTotal());
			result.put("tabla", tabla.toString());
			result.put("subtotales", totales.toString());
			result.put("iva", iva.toString());
			result.put("totales", importeTotal.toString());
			result.put("totalIeps", ieps.toString());
			result.put("totalOtrosImpuestos", totalOtrosImpuestos.toString());
			result.put("rTotal", rTotal.toString());

			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
}