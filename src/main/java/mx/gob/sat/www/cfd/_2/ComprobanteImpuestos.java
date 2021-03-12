/**
 * ComprobanteImpuestos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteImpuestos  implements java.io.Serializable {
    /* Nodo opcional para capturar los impuestos retenidos aplicables */
    private mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencion[] retenciones;

    /* Nodo opcional para asentar o referir los impuestos trasladados
     * aplicables */
    private mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTraslado[] traslados;

    private java.math.BigDecimal totalImpuestosRetenidos;  // attribute

    private java.math.BigDecimal totalImpuestosTrasladados;  // attribute

    public ComprobanteImpuestos() {
    }

    public ComprobanteImpuestos(
           mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencion[] retenciones,
           mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTraslado[] traslados,
           java.math.BigDecimal totalImpuestosRetenidos,
           java.math.BigDecimal totalImpuestosTrasladados) {
           this.retenciones = retenciones;
           this.traslados = traslados;
           this.totalImpuestosRetenidos = totalImpuestosRetenidos;
           this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }


    /**
     * Gets the retenciones value for this ComprobanteImpuestos.
     * 
     * @return retenciones   * Nodo opcional para capturar los impuestos retenidos aplicables
     */
    public mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencion[] getRetenciones() {
        return retenciones;
    }


    /**
     * Sets the retenciones value for this ComprobanteImpuestos.
     * 
     * @param retenciones   * Nodo opcional para capturar los impuestos retenidos aplicables
     */
    public void setRetenciones(mx.gob.sat.www.cfd._2.ComprobanteImpuestosRetencionesRetencion[] retenciones) {
        this.retenciones = retenciones;
    }


    /**
     * Gets the traslados value for this ComprobanteImpuestos.
     * 
     * @return traslados   * Nodo opcional para asentar o referir los impuestos trasladados
     * aplicables
     */
    public mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTraslado[] getTraslados() {
        return traslados;
    }


    /**
     * Sets the traslados value for this ComprobanteImpuestos.
     * 
     * @param traslados   * Nodo opcional para asentar o referir los impuestos trasladados
     * aplicables
     */
    public void setTraslados(mx.gob.sat.www.cfd._2.ComprobanteImpuestosTrasladosTraslado[] traslados) {
        this.traslados = traslados;
    }


    /**
     * Gets the totalImpuestosRetenidos value for this ComprobanteImpuestos.
     * 
     * @return totalImpuestosRetenidos
     */
    public java.math.BigDecimal getTotalImpuestosRetenidos() {
        return totalImpuestosRetenidos;
    }


    /**
     * Sets the totalImpuestosRetenidos value for this ComprobanteImpuestos.
     * 
     * @param totalImpuestosRetenidos
     */
    public void setTotalImpuestosRetenidos(java.math.BigDecimal totalImpuestosRetenidos) {
        this.totalImpuestosRetenidos = totalImpuestosRetenidos;
    }


    /**
     * Gets the totalImpuestosTrasladados value for this ComprobanteImpuestos.
     * 
     * @return totalImpuestosTrasladados
     */
    public java.math.BigDecimal getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }


    /**
     * Sets the totalImpuestosTrasladados value for this ComprobanteImpuestos.
     * 
     * @param totalImpuestosTrasladados
     */
    public void setTotalImpuestosTrasladados(java.math.BigDecimal totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteImpuestos)) return false;
        ComprobanteImpuestos other = (ComprobanteImpuestos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.retenciones==null && other.getRetenciones()==null) || 
             (this.retenciones!=null &&
              java.util.Arrays.equals(this.retenciones, other.getRetenciones()))) &&
            ((this.traslados==null && other.getTraslados()==null) || 
             (this.traslados!=null &&
              java.util.Arrays.equals(this.traslados, other.getTraslados()))) &&
            ((this.totalImpuestosRetenidos==null && other.getTotalImpuestosRetenidos()==null) || 
             (this.totalImpuestosRetenidos!=null &&
              this.totalImpuestosRetenidos.equals(other.getTotalImpuestosRetenidos()))) &&
            ((this.totalImpuestosTrasladados==null && other.getTotalImpuestosTrasladados()==null) || 
             (this.totalImpuestosTrasladados!=null &&
              this.totalImpuestosTrasladados.equals(other.getTotalImpuestosTrasladados())));
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
        if (getRetenciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRetenciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRetenciones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTraslados() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTraslados());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTraslados(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalImpuestosRetenidos() != null) {
            _hashCode += getTotalImpuestosRetenidos().hashCode();
        }
        if (getTotalImpuestosTrasladados() != null) {
            _hashCode += getTotalImpuestosTrasladados().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteImpuestos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Impuestos"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("totalImpuestosRetenidos");
        attrField.setXmlName(new javax.xml.namespace.QName("", "totalImpuestosRetenidos"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("totalImpuestosTrasladados");
        attrField.setXmlName(new javax.xml.namespace.QName("", "totalImpuestosTrasladados"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retenciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Retenciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Impuestos>Retenciones>Retencion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Retencion"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("traslados");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Traslados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Impuestos>Traslados>Traslado"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Traslado"));
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
