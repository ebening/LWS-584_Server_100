/**
 * ComprobanteReceptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteReceptor  implements java.io.Serializable {
    /* Nodo para la definición de la ubicación donde se da el domicilio
     * del receptor del comprobante fiscal. */
    private mx.gob.sat.www.cfd._2.T_Ubicacion domicilio;

    private java.lang.String rfc;  // attribute

    private java.lang.String nombre;  // attribute

    public ComprobanteReceptor() {
    }

    public ComprobanteReceptor(
           mx.gob.sat.www.cfd._2.T_Ubicacion domicilio,
           java.lang.String rfc,
           java.lang.String nombre) {
           this.domicilio = domicilio;
           this.rfc = rfc;
           this.nombre = nombre;
    }


    /**
     * Gets the domicilio value for this ComprobanteReceptor.
     * 
     * @return domicilio   * Nodo para la definición de la ubicación donde se da el domicilio
     * del receptor del comprobante fiscal.
     */
    public mx.gob.sat.www.cfd._2.T_Ubicacion getDomicilio() {
        return domicilio;
    }


    /**
     * Sets the domicilio value for this ComprobanteReceptor.
     * 
     * @param domicilio   * Nodo para la definición de la ubicación donde se da el domicilio
     * del receptor del comprobante fiscal.
     */
    public void setDomicilio(mx.gob.sat.www.cfd._2.T_Ubicacion domicilio) {
        this.domicilio = domicilio;
    }


    /**
     * Gets the rfc value for this ComprobanteReceptor.
     * 
     * @return rfc
     */
    public java.lang.String getRfc() {
        return rfc;
    }


    /**
     * Sets the rfc value for this ComprobanteReceptor.
     * 
     * @param rfc
     */
    public void setRfc(java.lang.String rfc) {
        this.rfc = rfc;
    }


    /**
     * Gets the nombre value for this ComprobanteReceptor.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this ComprobanteReceptor.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteReceptor)) return false;
        ComprobanteReceptor other = (ComprobanteReceptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.domicilio==null && other.getDomicilio()==null) || 
             (this.domicilio!=null &&
              this.domicilio.equals(other.getDomicilio()))) &&
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
        if (getDomicilio() != null) {
            _hashCode += getDomicilio().hashCode();
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
        new org.apache.axis.description.TypeDesc(ComprobanteReceptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Receptor"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("rfc");
        attrField.setXmlName(new javax.xml.namespace.QName("", "rfc"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_RFC"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("nombre");
        attrField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>Comprobante>Receptor>nombre"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domicilio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Domicilio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Ubicacion"));
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
