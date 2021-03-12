/**
 * ComprobanteImpuestosRetencionesRetencion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteImpuestosRetencionesRetencion  implements java.io.Serializable {
    private mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencionImpuesto impuesto;  // attribute

    private java.math.BigDecimal importe;  // attribute

    public ComprobanteImpuestosRetencionesRetencion() {
    }

    public ComprobanteImpuestosRetencionesRetencion(
           mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencionImpuesto impuesto,
           java.math.BigDecimal importe) {
           this.impuesto = impuesto;
           this.importe = importe;
    }


    /**
     * Gets the impuesto value for this ComprobanteImpuestosRetencionesRetencion.
     * 
     * @return impuesto
     */
    public mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencionImpuesto getImpuesto() {
        return impuesto;
    }


    /**
     * Sets the impuesto value for this ComprobanteImpuestosRetencionesRetencion.
     * 
     * @param impuesto
     */
    public void setImpuesto(mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencionImpuesto impuesto) {
        this.impuesto = impuesto;
    }


    /**
     * Gets the importe value for this ComprobanteImpuestosRetencionesRetencion.
     * 
     * @return importe
     */
    public java.math.BigDecimal getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this ComprobanteImpuestosRetencionesRetencion.
     * 
     * @param importe
     */
    public void setImporte(java.math.BigDecimal importe) {
        this.importe = importe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteImpuestosRetencionesRetencion)) return false;
        ComprobanteImpuestosRetencionesRetencion other = (ComprobanteImpuestosRetencionesRetencion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.impuesto==null && other.getImpuesto()==null) || 
             (this.impuesto!=null &&
              this.impuesto.equals(other.getImpuesto()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte())));
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
        if (getImpuesto() != null) {
            _hashCode += getImpuesto().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteImpuestosRetencionesRetencion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Impuestos>Retenciones>Retencion"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("impuesto");
        attrField.setXmlName(new javax.xml.namespace.QName("", "impuesto"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>>Comprobante>Impuestos>Retenciones>Retencion>impuesto"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("importe");
        attrField.setXmlName(new javax.xml.namespace.QName("", "importe"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
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
