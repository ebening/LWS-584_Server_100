package com.lowes.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.lowes.dto.FacturaDTO;

public class XMLParser {
	
	private List<FacturaDTO> facturas  = null;
	private FacturaDTO selFactura = null; 
	
	private String agregarFacturaAuto(File fileXML) {
		String retVal = "";
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileXML);
			NodeList nList = doc.getElementsByTagName("cfdi:Comprobante");
			NodeList nListEmi = doc.getElementsByTagName("cfdi:Emisor");
			NodeList nListCom = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
			
			String rfcProv = "";
			for (int temp = 0; temp < nListEmi.getLength(); temp++) {

				Node nNode = nListEmi.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NamedNodeMap childNodes = eElement.getAttributes();
					Node ejem = childNodes.getNamedItem("xmlns:cfdi");
					rfcProv = childNodes.getNamedItem("rfc").getNodeValue();
					//LOG.info("RFC XML : " + rfcProv);

				}
			}
			
			String uuID = "";
			for (int temp = 0; temp < nListCom.getLength(); temp++) {

				Node nNode = nListCom.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NamedNodeMap childNodes = eElement.getAttributes();
					uuID = childNodes.getNamedItem("UUID").getNodeValue();
					//LOG.info("UUID : " + uuID);
					
				}
			}
			
			String folioFact = "";
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NamedNodeMap childNodes = eElement.getAttributes();

					if (facturas == null) {
						facturas = new ArrayList<FacturaDTO>();
					}
					
					selFactura = new FacturaDTO();
					
					SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					SimpleDateFormat sdfOut = new SimpleDateFormat("MM/dd/yyyy");
					
					String fol = childNodes.getNamedItem("folio") != null ? childNodes.getNamedItem("folio").getNodeValue() : null;
					String serie = childNodes.getNamedItem("serie") != null ? childNodes.getNamedItem("serie").getNodeValue() : null;
					//folioFact = fol == null || fol.trim().isEmpty() ? Utilities.generarCodigoAleatorio(2, true) : fol;
					folioFact = fol == null || fol.trim().isEmpty() ? "folio" : fol;
					//Concatenación de serie para folios repetidos
					if(serie !=null)
						folioFact=serie+folioFact;
					
//					selFactura.setFechaEmision(sdfOut.format(sdfIn.parse(childNodes.getNamedItem("fecha").getNodeValue())));
//					selFactura.setNumerofactura(folioFact);
//					selFactura.setCantidad(childNodes.getNamedItem("total").getNodeValue());
//					selFactura.setIdFactura(consecutivo--);
//					selFactura.setEditable(false);
//					selFactura.setIdEstatusFactura(idEstFactNoValida);
//					selFactura.setIncluyeIVA(true);
//					selFactura.setRfc(rfcProv);
					
//					idFacturaAsociar = selFactura.getIdFactura();
					
					facturas.add(selFactura);
//					bloquearFactura();
				}
            }
            
			retVal = folioFact + "@" + uuID;
            
        } catch (Exception e) {
            //LOG.error(e);
        }
		return retVal;
    }
	

}
