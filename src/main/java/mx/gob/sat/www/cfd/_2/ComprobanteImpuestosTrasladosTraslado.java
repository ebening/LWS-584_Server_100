/**
 * ComprobanteImpuestosTrasladosTraslado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteImpuestosTrasladosTraslado  implements java.io.Serializable {
    private mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTrasladoImpuesto impuesto;  // attribute

    private java.math.BigDecimal tasa;  // attribute

    private java.math.BigDecimal importe;  // attribute

    public ComprobanteImpuestosTrasladosTraslado() {
    }

    public ComprobanteImpuestosTrasladosTraslado(
           mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTrasladoImpuesto impuesto,
           java.math.BigDecimal tasa,
           java.math.BigDecimal importe) {
           this.impuesto = impuesto;
           this.tasa = tasa;
           this.importe = importe;
    }


    /**
     * Gets the impuesto value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @return impuesto
     */
    public mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTrasladoImpuesto getImpuesto() {
        return impuesto;
    }


    /**
     * Sets the impuesto value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @param impuesto
     */
    public void setImpuesto(mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTrasladoImpuesto impuesto) {
        this.impuesto = impuesto;
    }


    /**
     * Gets the tasa value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @return tasa
     */
    public java.math.BigDecimal getTasa() {
        return tasa;
    }


    /**
     * Sets the tasa value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @param tasa
     */
    public void setTasa(java.math.BigDecimal tasa) {
        this.tasa = tasa;
    }


    /**
     * Gets the importe value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @return importe
     */
    public java.math.BigDecimal getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this ComprobanteImpuestosTrasladosTraslado.
     * 
     * @param importe
     */
    public void setImporte(java.math.BigDecimal importe) {
        this.importe = importe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteImpuestosTrasladosTraslado)) return false;
        ComprobanteImpuestosTrasladosTraslado other = (ComprobanteImpuestosTrasladosTraslado) obj;
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
            ((this.tasa==null && other.getTasa()==null) || 
             (this.tasa!=null &&
              this.tasa.equals(other.getTasa()))) &&
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
        if (getTasa() != null) {
            _hashCode += getTasa().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteImpuestosTrasladosTraslado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Impuestos>Traslados>Traslado"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("impuesto");
        attrField.setXmlName(new javax.xml.namespace.QName("", "impuesto"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>>Comprobante>Impuestos>Traslados>Traslado>impuesto"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("tasa");
        attrField.setXmlName(new javax.xml.namespace.QName("", "tasa"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
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
