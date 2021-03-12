/**
 * ComprobanteConceptosConceptoCuentaPredial.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteConceptosConceptoCuentaPredial  implements java.io.Serializable {
    private java.lang.String numero;  // attribute

    public ComprobanteConceptosConceptoCuentaPredial() {
    }

    public ComprobanteConceptosConceptoCuentaPredial(
           java.lang.String numero) {
           this.numero = numero;
    }


    /**
     * Gets the numero value for this ComprobanteConceptosConceptoCuentaPredial.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this ComprobanteConceptosConceptoCuentaPredial.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteConceptosConceptoCuentaPredial)) return false;
        ComprobanteConceptosConceptoCuentaPredial other = (ComprobanteConceptosConceptoCuentaPredial) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero())));
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
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteConceptosConceptoCuentaPredial.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>CuentaPredial"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("numero");
        attrField.setXmlName(new javax.xml.namespace.QName("", "numero"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>>Comprobante>Conceptos>Concepto>CuentaPredial>numero"));
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
