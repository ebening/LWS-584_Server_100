package com.lowes.util;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import com.lowes.dto.FacturaDTO;

public class ParseFacturaConXML {

	private String uuID = null;

	public FacturaDTO getFactura(Document doc) throws DOMException, ParseException {

		FacturaDTO factura = new FacturaDTO();

		NodeList nList = doc.getElementsByTagName("cfdi:Comprobante");
		NodeList nListEmi = doc.getElementsByTagName("cfdi:Emisor");
		NodeList nListRec = doc.getElementsByTagName("cfdi:Receptor");
		NodeList conceptos = doc.getElementsByTagName("cfdi:Concepto");
		NodeList nListCom = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
		NodeList nListTras = doc.getElementsByTagName("cfdi:Traslado");
		NodeList nListAeroCargo = doc.getElementsByTagName("aerolineas:Cargo");
		NodeList nListRetenciones = doc.getElementsByTagName("cfdi:Retencion");
		NodeList nListAerolinea= doc.getElementsByTagName("aerolineas:Aerolineas");
		NodeList nListAeroOtrosCargos= doc.getElementsByTagName("aerolineas:OtrosCargos");
		NodeList nListTraslados= doc.getElementsByTagName("implocal:TrasladosLocales");
		
		/*
		 * Check conceptos TUA ISH YRI, Otros
		 */
		boolean incluyeTUA = false;
		boolean incluyeISH = false;
		boolean incluyeYRI = false;
		if (conceptos.getLength() > Etiquetas.CERO) {
			for (int r = 0; r < conceptos.getLength(); r++) {
				String impuesto = getValueFromNode(conceptos, "descripcion",r);
				if (impuesto != null && impuesto.toUpperCase().equals("TUA")){
					incluyeTUA = true;
					continue;
				} else if (impuesto != null && impuesto.toUpperCase().equals("ISH")){
					incluyeISH = true;
					continue;
				} else if (impuesto != null && impuesto.toUpperCase().equals("YRI")){
					incluyeYRI = true;
					continue;
				}
			}
		}
		
		if(getValueFromNodeSingle(nListAerolinea, "TUA") != null && !incluyeTUA)
			factura.setTua(new BigDecimal(getValueFromNodeSingle(nListAerolinea, "TUA")).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
		
		//if(getValueFromNodeSingle(nListTraslados, "Importe") != null)
			//factura.setIsh(new BigDecimal(getValueFromNodeSingle(nListTraslados, "Importe")).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
		
		/*
		 * ISH
		 */
		Integer numTraslados = nListTraslados.getLength();
		if (numTraslados > Etiquetas.CERO && !incluyeISH) {
			for (int r = 0; r < numTraslados; r++) {
				String impuesto = getValueFromNode(nListTraslados, "ImpLocTrasladado",r);
				String importe = getValueFromNode(nListTraslados, "Importe",r);
				if (impuesto != null && impuesto.toUpperCase().equals("ISH") || impuesto.toUpperCase().equals("IMPUESTO SOBRE HOSPEDAJE")) {
					factura.setIsh(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		
		/*
		 * YRI y otros.
		 */
		BigDecimal otrosCargos = new BigDecimal(0);
		Integer numAeroCargo = nListAeroCargo.getLength();
		if (numAeroCargo > Etiquetas.CERO) {
			for (int r = 0; r < numAeroCargo; r++) {
				String impuesto = getValueFromNode(nListAeroCargo, "CodigoCargo",r);
				String importe = getValueFromNode(nListAeroCargo, "Importe",r);
				if (impuesto != null && impuesto.toUpperCase().equals("YRI")) {
					if(!incluyeYRI){
						factura.setYri(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
						otrosCargos = otrosCargos.add(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
					}else
						continue;
				}else{
					otrosCargos = otrosCargos.add(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				}
			}
			factura.setOtrosCargos(otrosCargos.setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
		}
		
//		if(getValueFromNodeSingle(nListAeroOtrosCargos, "TotalCargos") != null && !incluyeOtrosImp)
//			factura.setOtrosCargos(new BigDecimal(getValueFromNodeSingle(nListAeroOtrosCargos, "TotalCargos")).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
		
		/*
		 * se extraen las translados(iva) y se marca como true el valor que
		 * indica que la factura cuenta con dichos valores
		 */
		Integer numTranslados = nListTras.getLength();
		if (numTranslados > Etiquetas.CERO) {
			// si cuenta con datos de translado entonces incluye iva.
			factura.setIncluyeIVA(Etiquetas.TRUE);
			for (int r = 0; r < numTranslados; r++) {
				String impuesto = getValueFromNode(nListTras, "impuesto",r);
				String importe = getValueFromNode(nListTras, "importe",r);
				String tasa = getValueFromNode(nListTras, "tasa",r);

				if (impuesto != null && impuesto.toUpperCase().equals("IVA")) {
					// toma el valor cuando la tasa sea mayor a cero.
					Double tasaD = Double.parseDouble(tasa);
					if (tasaD > Etiquetas.CERO) {
						factura.setIva(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
						factura.setTasaIva(new BigDecimal(tasa).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
					}
				} else if (impuesto != null && impuesto.toUpperCase().equals("IEPS")) {
					factura.setIeps(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
					factura.setTasaIeps(new BigDecimal(tasa).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				}
			}
		}

		/*
		 * se extraen las retenciones y se marca como true el valor que indica
		 * que la factura cuenta con dichos valores
		 */
		Integer numRetenciones = nListRetenciones.getLength();
		if (numRetenciones > Etiquetas.CERO) { 
			factura.setIncluyeRetenciones(Etiquetas.TRUE);
			for (int r = 0; r < numRetenciones; r++) {
				String impuesto = getValueFromNode(nListRetenciones, "impuesto",r);
				String importe = getValueFromNode(nListRetenciones, "importe",r);
				if (impuesto != null && impuesto.toUpperCase().equals("IVA")) {
					factura.setIvaRetenido(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				} else if (impuesto != null && impuesto.toUpperCase().equals("ISR")) {
					factura.setIsrRetenido(new BigDecimal(importe).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				}
			}
		}

		factura.setRfcEmisor(getValueFromNodeSingle(nListEmi, "rfc"));
		factura.setNombreEmisor(getValueFromNodeSingle(nListEmi, "nombre"));
		factura.setRfcReceptor(getValueFromNodeSingle(nListRec, "rfc"));
		
		
		getValueFromNodeSingle(conceptos, "descripcion");
		
		//extraer conceptos
		StringBuilder conceptosTodos = new StringBuilder();
		if (conceptos.getLength() > 0) {
			for (int concepto = 0; concepto < conceptos.getLength(); concepto++) {
				conceptosTodos.append(getValueFromNode(conceptos, "descripcion", concepto) + "\n");
			}
			// conceptos de la factura.
			factura.setConceptos(conceptosTodos.toString());
		}

		// extrae el uuid del timbre fiscal
		for (int temp = 0; temp < nListCom.getLength(); temp++) {

			Node nNode = nListCom.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				NamedNodeMap childNodes = eElement.getAttributes();
				uuID = childNodes.getNamedItem("UUID").getNodeValue();

			}
		}
		factura.setUuid(uuID);

		String folioFact = "";
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				NamedNodeMap childNodes = eElement.getAttributes();
				SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");

				String fol = childNodes.getNamedItem("folio") != null ? childNodes.getNamedItem("folio").getNodeValue()
						: null;
				String serie = childNodes.getNamedItem("serie") != null
						? childNodes.getNamedItem("serie").getNodeValue() : null;
				// folioFact = fol == null || fol.trim().isEmpty() ?
				// Utilities.generarCodigoAleatorio(2, true) : fol;
				folioFact = fol == null || fol.trim().isEmpty() ? "folio" : fol;

				factura.setFechaEmision(sdfOut.format(sdfIn.parse(childNodes.getNamedItem("fecha").getNodeValue())));
				factura.setFolio(fol);
				factura.setFolioFiscal(uuID);
				factura.setSubTotal(new BigDecimal(childNodes.getNamedItem("subTotal").getNodeValue()).setScale(Etiquetas.DOS,BigDecimal.ROUND_HALF_UP));
				factura.setTotal(new BigDecimal(childNodes.getNamedItem("total").getNodeValue()));
				factura.setSerie(serie);
				if(childNodes.getNamedItem("Moneda") != null){
					factura.setMoneda(childNodes.getNamedItem("Moneda").getNodeValue());
				}
				factura.setTipoDeComprobante(childNodes.getNamedItem("tipoDeComprobante").getNodeValue());
			}
		}

		return factura;
	}

	public String getValueFromNode(NodeList nodeList, String value, Integer elem) {

		String valor = null;
		
			Node nNode = nodeList.item(elem);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NamedNodeMap childNodes = eElement.getAttributes();
				valor = childNodes.getNamedItem(value).getNodeValue();
			}
			
		return valor;
	}
	
	
	public String getValueFromNodeSingle(NodeList nodeList, String value) {

		String valor = null;

		for (int temp = 0; temp < nodeList.getLength(); temp++) {

			Node nNode = nodeList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				NamedNodeMap childNodes = eElement.getAttributes();
				valor = childNodes.getNamedItem(value).getNodeValue();
			}
		}

		return valor;
	}
	

}
