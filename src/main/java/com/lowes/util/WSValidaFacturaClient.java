package com.lowes.util;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.buzonfiscal.www.ns.bf.conector._1.MessageType;
import com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaBindingStub;
import com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoType;
import com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoTypeTipo;
import com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoBasicaType;
import com.buzonfiscal.www.ns.xsd.bf.bfenviacfd._1.RequestEnviaCfdType;

@Repository
public class WSValidaFacturaClient {

	private static final Logger log = Logger.getLogger(WSValidaFacturaClient.class);
	
	private @Value("${ws.valida.factura.endpoint}") String endpoint;
	
	private BuzonFiscalCorpValidaCFDenLineaBindingStub stub;
	
	private static final DocumentoTypeTipo DOCUMENTO_TYPE_TIPO = DocumentoTypeTipo.XML; //Valor fijo XML
	private static final String SERVICE_VERSION = "3.2";
	
	public WSValidaFacturaClient() {
		
	}
	
	public List<WSFacturaMessageResponse> validarFactura (byte [] archivo, String nombreArchivo, String rfcEmisor, String rfcReceptor, String importeTotal, String token) {
		List<WSFacturaMessageResponse> messages = new ArrayList<WSFacturaMessageResponse>();
		
		try {
			validarParametros(archivo, nombreArchivo, rfcEmisor, rfcReceptor, importeTotal);
		} catch (IllegalStateException e) {
			messages.add(new WSFacturaMessageResponse(WSMessageType.PLATFORM_ERROR, -100, e.getMessage()));
			log.error(e.getMessage());
			return messages;
		}
		
		try {
			validarEndpoint();
		} catch (IllegalStateException e) {
			messages.add(new WSFacturaMessageResponse(WSMessageType.PLATFORM_ERROR, -101, e.getMessage()));
			log.error(e.getMessage());
			return messages;
		}
		
		try {
			stub = getStub();
		} catch (IllegalStateException e) {
			messages.add(new WSFacturaMessageResponse(WSMessageType.PLATFORM_ERROR, -102, e.getMessage()));
			log.error(e.getMessage());
			return messages;
		}
		
		StringBuilder parameters = new StringBuilder();
		parameters.append("\n").append("archivo (lenght)=")	.append(archivo.length);
		parameters.append("\n").append("nombreArchivo	=")	.append(nombreArchivo);
		parameters.append("\n").append("rfcEmisor		=")	.append(rfcEmisor);
		parameters.append("\n").append("rfcReceptor		=")	.append(rfcReceptor);
		parameters.append("\n").append("importeTotal	=")	.append(importeTotal);
		parameters.append("\n").append("token			=")	.append(token);
		parameters.append("\n").append("endpoint		=")	.append(endpoint);
		log.info(parameters);

		byte [] base64Encode = archivo;//convertirArchivoBase64(archivo);
		
		DocumentoType documento = getDocumento(base64Encode, nombreArchivo, DOCUMENTO_TYPE_TIPO, SERVICE_VERSION);
		
		InfoBasicaType infoBasica = getInfoBasicaType(rfcEmisor, rfcReceptor, importeTotal);
		
		RequestEnviaCfdType request = getRequest(documento, infoBasica, token);
		
		try {
			com.buzonfiscal.www.ns.bf.conector._1.MessageType[] response = stub.validaCFD(request);
			
			if (response != null && response.length > 0) {
				
				for (MessageType messageType : response) {
					messages.add(new WSFacturaMessageResponse(WSMessageType.WS_CONNECTION, messageType.getCode(), messageType.getMessage()));
				}
			} else {
				messages.add(new WSFacturaMessageResponse(WSMessageType.WS_CONNECTION, -103, "Respuesta vacia de Web Service"));
			}
		} catch (RemoteException e) {
			messages.add(new WSFacturaMessageResponse(WSMessageType.WS_CONNECTION, -104, e.getMessage()));
			log.error("Error en llamado a Web Service", e);
			return messages;
		}
		
		return messages;
	}
	
	
	

	private BuzonFiscalCorpValidaCFDenLineaBindingStub getStub() {
		try {
			stub = new BuzonFiscalCorpValidaCFDenLineaBindingStub( new URL(endpoint), null);
		} catch (AxisFault e) {
			log.error("Error en la creacion de Servicio Web", e);
		} catch (MalformedURLException e) {
			log.error("Error en la creacion de Servicio Web", e);
		}
		return stub;
	}

	private void validarEndpoint() throws IllegalStateException {
		if (endpoint == null || endpoint.isEmpty()) {
			throw new IllegalStateException("Endpoint no configurado");
		}
	}

	private RequestEnviaCfdType getRequest(DocumentoType documento,
			InfoBasicaType infoBasica, String token) {
		RequestEnviaCfdType request = new RequestEnviaCfdType();
		request.setDocumento(documento);
		request.setInfoBasica(infoBasica);
		request.setToken(token);
		return request;
	}


	private InfoBasicaType getInfoBasicaType(String rfcEmisor,
			String rfcReceptor, String importeTotal) {
		String serie = null;
		String folio = null;
		BigDecimal importe = new BigDecimal(importeTotal);
		String documentoId = null;
		String claveArea = null;
		String unidadNegocio = null; 
		
		return new InfoBasicaType(rfcEmisor, rfcReceptor, serie, folio, importe, documentoId, claveArea, unidadNegocio);
	}


	private DocumentoType getDocumento(byte[] archivo,
			String nombreArchivo, DocumentoTypeTipo tipo,
			String version) {
		return new DocumentoType(archivo, nombreArchivo, tipo, version); 
	}


	private byte[] convertirArchivoBase64(byte[] archivo) {
		//Archivo en Base64
		return Base64.encodeBase64(archivo); 
	}


	private void validarParametros(byte[] archivo, String nombreArchivo,
			String rfcEmisor, String rfcReceptor, 
			String importeTotal) throws IllegalStateException {
		
		if (archivo == null 
				|| (nombreArchivo == null || nombreArchivo.isEmpty()) 
				|| (rfcEmisor == null || rfcEmisor.isEmpty()) 
				|| (rfcReceptor == null || rfcReceptor.isEmpty())
				|| (importeTotal == null || importeTotal.isEmpty())) {
			throw new IllegalStateException("Parametros obligatorios");
		}
	}


//	public void setEndpoint(String endpoint) {
//		this.endpoint = endpoint;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
}
