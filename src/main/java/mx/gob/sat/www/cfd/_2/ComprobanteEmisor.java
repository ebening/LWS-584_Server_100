/**
 * ComprobanteEmisor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteEmisor  implements java.io.Serializable {
    /* Nodo requerido para precisar la información de ubicación del
     * domicilio fiscal del contribuyente emisor */
    private mx.gob.sat.www.cfd._2.T_UbicacionFiscal domicilioFiscal;

    /* Nodo opcional para precisar la información de ubicación del
     * domicilio en donde es emitido el comprobante fiscal en caso de que
     * sea distinto del domicilio fiscal del contribuyente emisor. */
    private mx.gob.sat.www.cfd._2.T_Ubicacion expedidoEn;

    private java.lang.String rfc;  // attribute

    private java.lang.String nombre;  // attribute

    public ComprobanteEmisor() {
    }

    public ComprobanteEmisor(
           mx.gob.sat.www.cfd._2.T_UbicacionFiscal domicilioFiscal,
           mx.gob.sat.www.cfd._2.T_Ubicacion expedidoEn,
           java.lang.String rfc,
           java.lang.String nombre) {
           this.domicilioFiscal = domicilioFiscal;
           this.expedidoEn = expedidoEn;
           this.rfc = rfc;
           this.nombre = nombre;
    }


    /**
     * Gets the domicilioFiscal value for this ComprobanteEmisor.
     * 
     * @return domicilioFiscal   * Nodo requerido para precisar la información de ubicación del
     * domicilio fiscal del contribuyente emisor
     */
    public mx.gob.sat.www.cfd._2.T_UbicacionFiscal getDomicilioFiscal() {
        return domicilioFiscal;
    }


    /**
     * Sets the domicilioFiscal value for this ComprobanteEmisor.
     * 
     * @param domicilioFiscal   * Nodo requerido para precisar la información de ubicación del
     * domicilio fiscal del contribuyente emisor
     */
    public void setDomicilioFiscal(mx.gob.sat.www.cfd._2.T_UbicacionFiscal domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }


    /**
     * Gets the expedidoEn value for this ComprobanteEmisor.
     * 
     * @return expedidoEn   * Nodo opcional para precisar la información de ubicación del
     * domicilio en donde es emitido el comprobante fiscal en caso de que
     * sea distinto del domicilio fiscal del contribuyente emisor.
     */
    public mx.gob.sat.www.cfd._2.T_Ubicacion getExpedidoEn() {
        return expedidoEn;
    }


    /**
     * Sets the expedidoEn value for this ComprobanteEmisor.
     * 
     * @param expedidoEn   * Nodo opcional para precisar la información de ubicación del
     * domicilio en donde es emitido el comprobante fiscal en caso de que
     * sea distinto del domicilio fiscal del contribuyente emisor.
     */
    public void setExpedidoEn(mx.gob.sat.www.cfd._2.T_Ubicacion expedidoEn) {
        this.expedidoEn = expedidoEn;
    }


    /**
     * Gets the rfc value for this ComprobanteEmisor.
     * 
     * @return rfc
     */
    public java.lang.String getRfc() {
        return rfc;
    }


    /**
     * Sets the rfc value for this ComprobanteEmisor.
     * 
     * @param rfc
     */
    public void setRfc(java.lang.String rfc) {
        this.rfc = rfc;
    }


    /**
     * Gets the nombre value for this ComprobanteEmisor.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this ComprobanteEmisor.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteEmisor)) return false;
        ComprobanteEmisor other = (ComprobanteEmisor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.domicilioFiscal==null && other.getDomicilioFiscal()==null) || 
             (this.domicilioFiscal!=null &&
              this.domicilioFiscal.equals(other.getDomicilioFiscal()))) &&
            ((this.expedidoEn==null && other.getExpedidoEn()==null) || 
             (this.expedidoEn!=null &&
              this.expedidoEn.equals(other.getExpedidoEn()))) &&
            ((this.rfc==null && other.getRfc()==null) || 
             (this.rfc!=null &&
              this.rfc.equals(other.getRfc()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre())));
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
        if (getDomicilioFiscal() != null) {
            _hashCode += getDomicilioFiscal().hashCode();
        }
        if (getExpedidoEn() != null) {
            _hashCode += getExpedidoEn().hashCode();
        }
        if (getRfc() != null) {
            _hashCode += getRfc().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteEmisor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Emisor"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("rfc");
        attrField.setXmlName(new javax.xml.namespace.QName("", "rfc"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_RFC"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("nombre");
        attrField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>Comprobante>Emisor>nombre"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domicilioFiscal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "DomicilioFiscal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_UbicacionFiscal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expedidoEn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "ExpedidoEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Ubicacion"));
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
