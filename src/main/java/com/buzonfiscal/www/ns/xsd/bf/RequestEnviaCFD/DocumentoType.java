/**
 * DocumentoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD;

public class DocumentoType  implements java.io.Serializable {
    private byte[] archivo;  // attribute

    private java.lang.String nombreArchivo;  // attribute

    private com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoTypeTipo tipo;  // attribute

    private java.lang.String version;  // attribute

    public DocumentoType() {
    }

    public DocumentoType(
           byte[] archivo,
           java.lang.String nombreArchivo,
           com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoTypeTipo tipo,
           java.lang.String version) {
           this.archivo = archivo;
           this.nombreArchivo = nombreArchivo;
           this.tipo = tipo;
           this.version = version;
    }


    /**
     * Gets the archivo value for this DocumentoType.
     * 
     * @return archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }


    /**
     * Sets the archivo value for this DocumentoType.
     * 
     * @param archivo
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }


    /**
     * Gets the nombreArchivo value for this DocumentoType.
     * 
     * @return nombreArchivo
     */
    public java.lang.String getNombreArchivo() {
        return nombreArchivo;
    }


    /**
     * Sets the nombreArchivo value for this DocumentoType.
     * 
     * @param nombreArchivo
     */
    public void setNombreArchivo(java.lang.String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }


    /**
     * Gets the tipo value for this DocumentoType.
     * 
     * @return tipo
     */
    public com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoTypeTipo getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this DocumentoType.
     * 
     * @param tipo
     */
    public void setTipo(com.buzonfiscal.www.ns.xsd.bf.RequestEnviaCFD.DocumentoTypeTipo tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the version value for this DocumentoType.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this DocumentoType.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoType)) return false;
        DocumentoType other = (DocumentoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.archivo==null && other.getArchivo()==null) || 
             (this.archivo!=null &&
              java.util.Arrays.equals(this.archivo, other.getArchivo()))) &&
            ((this.nombreArchivo==null && other.getNombreArchivo()==null) || 
             (this.nombreArchivo!=null &&
              this.nombreArchivo.equals(other.getNombreArchivo()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getArchivo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArchivo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArchivo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNombreArchivo() != null) {
            _hashCode += getNombreArchivo().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", "DocumentoType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("archivo");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Archivo"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("nombreArchivo");
        attrField.setXmlName(new javax.xml.namespace.QName("", "NombreArchivo"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("tipo");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Tipo"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.buzonfiscal.com/ns/xsd/bf/RequestEnviaCFD", ">DocumentoType>Tipo"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Version"));
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
