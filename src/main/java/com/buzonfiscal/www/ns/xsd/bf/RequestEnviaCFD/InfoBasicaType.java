/**
 * InfoBasicaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD;

public class InfoBasicaType  implements java.io.Serializable {
    private java.lang.String rfcEmisor;  // attribute

    private java.lang.String rfcReceptor;  // attribute

    private java.lang.String serie;  // attribute

    private java.lang.String folio;  // attribute

    private java.math.BigDecimal importeTotal;  // attribute

    private java.lang.String documentoId;  // attribute

    private java.lang.String claveArea;  // attribute

    private java.lang.String unidadNegocio;  // attribute

    public InfoBasicaType() {
    }

    public InfoBasicaType(
           java.lang.String rfcEmisor,
           java.lang.String rfcReceptor,
           java.lang.String serie,
           java.lang.String folio,
           java.math.BigDecimal importeTotal,
           java.lang.String documentoId,
           java.lang.String claveArea,
           java.lang.String unidadNegocio) {
           this.rfcEmisor = rfcEmisor;
           this.rfcReceptor = rfcReceptor;
           this.serie = serie;
           this.folio = folio;
           this.importeTotal = importeTotal;
           this.documentoId = documentoId;
           this.claveArea = claveArea;
           this.unidadNegocio = unidadNegocio;
    }


    /**
     * Gets the rfcEmisor value for this InfoBasicaType.
     * 
     * @return rfcEmisor
     */
    public java.lang.String getRfcEmisor() {
        return rfcEmisor;
    }


    /**
     * Sets the rfcEmisor value for this InfoBasicaType.
     * 
     * @param rfcEmisor
     */
    public void setRfcEmisor(java.lang.String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }


    /**
     * Gets the rfcReceptor value for this InfoBasicaType.
     * 
     * @return rfcReceptor
     */
    public java.lang.String getRfcReceptor() {
        return rfcReceptor;
    }


    /**
     * Sets the rfcReceptor value for this InfoBasicaType.
     * 
     * @param rfcReceptor
     */
    public void setRfcReceptor(java.lang.String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }


    /**
     * Gets the serie value for this InfoBasicaType.
     * 
     * @return serie
     */
    public java.lang.String getSerie() {
        return serie;
    }


    /**
     * Sets the serie value for this InfoBasicaType.
     * 
     * @param serie
     */
    public void setSerie(java.lang.String serie) {
        this.serie = serie;
    }


    /**
     * Gets the folio value for this InfoBasicaType.
     * 
     * @return folio
     */
    public java.lang.String getFolio() {
        return folio;
    }


    /**
     * Sets the folio value for this InfoBasicaType.
     * 
     * @param folio
     */
    public void setFolio(java.lang.String folio) {
        this.folio = folio;
    }


    /**
     * Gets the importeTotal value for this InfoBasicaType.
     * 
     * @return importeTotal
     */
    public java.math.BigDecimal getImporteTotal() {
        return importeTotal;
    }


    /**
     * Sets the importeTotal value for this InfoBasicaType.
     * 
     * @param importeTotal
     */
    public void setImporteTotal(java.math.BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }


    /**
     * Gets the documentoId value for this InfoBasicaType.
     * 
     * @return documentoId
     */
    public java.lang.String getDocumentoId() {
        return documentoId;
    }


    /**
     * Sets the documentoId value for this InfoBasicaType.
     * 
     * @param documentoId
     */
    public void setDocumentoId(java.lang.String documentoId) {
        this.documentoId = documentoId;
    }


    /**
     * Gets the claveArea value for this InfoBasicaType.
     * 
     * @return claveArea
     */
    public java.lang.String getClaveArea() {
        return claveArea;
    }


    /**
     * Sets the claveArea value for this InfoBasicaType.
     * 
     * @param claveArea
     */
    public void setClaveArea(java.lang.String claveArea) {
        this.claveArea = claveArea;
    }


    /**
     * Gets the unidadNegocio value for this InfoBasicaType.
     * 
     * @return unidadNegocio
     */
    public java.lang.String getUnidadNegocio() {
        return unidadNegocio;
    }


    /**
     * Sets the unidadNegocio value for this InfoBasicaType.
     * 
     * @param unidadNegocio
     */
    public void setUnidadNegocio(java.lang.String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoBasicaType)) return false;
        InfoBasicaType other = (InfoBasicaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rfcEmisor==null && other.getRfcEmisor()==null) || 
             (this.rfcEmisor!=null &&
              this.rfcEmisor.equals(other.getRfcEmisor()))) &&
            ((this.rfcReceptor==null && other.getRfcReceptor()==null) || 
             (this.rfcReceptor!=null &&
              this.rfcReceptor.equals(other.getRfcReceptor()))) &&
            ((this.serie==null && other.getSerie()==null) || 
             (this.serie!=null &&
              this.serie.equals(other.getSerie()))) &&
            ((this.folio==null && other.getFolio()==null) || 
             (this.folio!=null &&
              this.folio.equals(other.getFolio()))) &&
            ((this.importeTotal==null && other.getImporteTotal()==null) || 
             (this.importeTotal!=null &&
              this.importeTotal.equals(other.getImporteTotal()))) &&
            ((this.documentoId==null && other.getDocumentoId()==null) || 
             (this.documentoId!=null &&
              this.documentoId.equals(other.getDocumentoId()))) &&
            ((this.claveArea==null && other.getClaveArea()==null) || 
             (this.claveArea!=null &&
              this.claveArea.equals(other.getClaveArea()))) &&
            ((this.unidadNegocio==null && other.getUnidadNegocio()==null) || 
             (this.unidadNegocio!=null &&
              this.unidadNegocio.equals(other.getUnidadNegocio())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRfcEmisor() != null) {
            _hashCode += getRfcEmisor().hashCode();
        }
        if (getRfcReceptor() != null) {
            _hashCode += getRfcReceptor().hashCode();
        }
        if (getSerie() != null) {
            _hashCode += getSerie().hashCode();
        }
        if (getFolio() != null) {
            _hashCode += getFolio().hashCode();
        }
        if (getImporteTotal() != null) {
            _hashCode += getImporteTotal().hashCode();
        }
        if (getDocumentoId() != null) {
            _hashCode += getDocumentoId().hashCode();
        }
        if (getClaveArea() != null) {
            _hashCode += getClaveArea().hashCode();
        }
        if (getUnidadNegocio() != null) {
            _hashCode += getUnidadNegocio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoBasicaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "InfoBasicaType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("rfcEmisor");
        attrField.setXmlName(new javax.xml.namespace.QName("", "RfcEmisor"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", ">InfoBasicaType>RfcEmisor"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("rfcReceptor");
        attrField.setXmlName(new javax.xml.namespace.QName("", "RfcReceptor"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", ">InfoBasicaType>RfcReceptor"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("serie");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Serie"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("folio");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Folio"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("importeTotal");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ImporteTotal"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", ">InfoBasicaType>ImporteTotal"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("documentoId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DocumentoId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("claveArea");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ClaveArea"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("unidadNegocio");
        attrField.setXmlName(new javax.xml.namespace.QName("", "UnidadNegocio"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
