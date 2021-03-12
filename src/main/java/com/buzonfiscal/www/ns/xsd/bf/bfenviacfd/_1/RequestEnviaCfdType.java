/**
 * RequestEnviaCfdType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.buzonfiscal.www.ns.xsd.bf.bfenviacfd._1;

public class RequestEnviaCfdType  implements java.io.Serializable {
    private com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoType documento;

    private mx.gob.sat.www.cfd._2.Comprobante comprobante;

    private com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoBasicaType infoBasica;

    private com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType[] infoAdicional;

    private java.lang.String refID;  // attribute

    private java.lang.String token;  // attribute

    public RequestEnviaCfdType() {
    }

    public RequestEnviaCfdType(
           com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoType documento,
           mx.gob.sat.www.cfd._2.Comprobante comprobante,
           com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoBasicaType infoBasica,
           com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType[] infoAdicional,
           java.lang.String refID,
           java.lang.String token) {
           this.documento = documento;
           this.comprobante = comprobante;
           this.infoBasica = infoBasica;
           this.infoAdicional = infoAdicional;
           this.refID = refID;
           this.token = token;
    }


    /**
     * Gets the documento value for this RequestEnviaCfdType.
     * 
     * @return documento
     */
    public com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoType getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this RequestEnviaCfdType.
     * 
     * @param documento
     */
    public void setDocumento(com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoType documento) {
        this.documento = documento;
    }


    /**
     * Gets the comprobante value for this RequestEnviaCfdType.
     * 
     * @return comprobante
     */
    public mx.gob.sat.www.cfd._2.Comprobante getComprobante() {
        return comprobante;
    }


    /**
     * Sets the comprobante value for this RequestEnviaCfdType.
     * 
     * @param comprobante
     */
    public void setComprobante(mx.gob.sat.www.cfd._2.Comprobante comprobante) {
        this.comprobante = comprobante;
    }


    /**
     * Gets the infoBasica value for this RequestEnviaCfdType.
     * 
     * @return infoBasica
     */
    public com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoBasicaType getInfoBasica() {
        return infoBasica;
    }


    /**
     * Sets the infoBasica value for this RequestEnviaCfdType.
     * 
     * @param infoBasica
     */
    public void setInfoBasica(com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoBasicaType infoBasica) {
        this.infoBasica = infoBasica;
    }


    /**
     * Gets the infoAdicional value for this RequestEnviaCfdType.
     * 
     * @return infoAdicional
     */
    public com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType[] getInfoAdicional() {
        return infoAdicional;
    }


    /**
     * Sets the infoAdicional value for this RequestEnviaCfdType.
     * 
     * @param infoAdicional
     */
    public void setInfoAdicional(com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType[] infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType getInfoAdicional(int i) {
        return this.infoAdicional[i];
    }

    public void setInfoAdicional(int i, com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.InfoAdicionalType _value) {
        this.infoAdicional[i] = _value;
    }


    /**
     * Gets the refID value for this RequestEnviaCfdType.
     * 
     * @return refID
     */
    public java.lang.String getRefID() {
        return refID;
    }


    /**
     * Sets the refID value for this RequestEnviaCfdType.
     * 
     * @param refID
     */
    public void setRefID(java.lang.String refID) {
        this.refID = refID;
    }


    /**
     * Gets the token value for this RequestEnviaCfdType.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this RequestEnviaCfdType.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestEnviaCfdType)) return false;
        RequestEnviaCfdType other = (RequestEnviaCfdType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documento==null && other.getDocumento()==null) || 
             (this.documento!=null &&
              this.documento.equals(other.getDocumento()))) &&
            ((this.comprobante==null && other.getComprobante()==null) || 
             (this.comprobante!=null &&
              this.comprobante.equals(other.getComprobante()))) &&
            ((this.infoBasica==null && other.getInfoBasica()==null) || 
             (this.infoBasica!=null &&
              this.infoBasica.equals(other.getInfoBasica()))) &&
            ((this.infoAdicional==null && other.getInfoAdicional()==null) || 
             (this.infoAdicional!=null &&
              java.util.Arrays.equals(this.infoAdicional, other.getInfoAdicional()))) &&
            ((this.refID==null && other.getRefID()==null) || 
             (this.refID!=null &&
              this.refID.equals(other.getRefID()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken())));
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
        if (getDocumento() != null) {
            _hashCode += getDocumento().hashCode();
        }
        if (getComprobante() != null) {
            _hashCode += getComprobante().hashCode();
        }
        if (getInfoBasica() != null) {
            _hashCode += getInfoBasica().hashCode();
        }
        if (getInfoAdicional() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInfoAdicional());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInfoAdicional(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRefID() != null) {
            _hashCode += getRefID().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestEnviaCfdType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/bfenviacfd/1", "RequestEnviaCfdType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("refID");
        attrField.setXmlName(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "RefID"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/referenceID/v1", "refIDType"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("token");
        attrField.setXmlName(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "Token"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "Documento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "DocumentoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comprobante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Comprobante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">Comprobante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoBasica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "InfoBasica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "InfoBasicaType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/bfenviacfd/1", "InfoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "InfoAdicionalType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
