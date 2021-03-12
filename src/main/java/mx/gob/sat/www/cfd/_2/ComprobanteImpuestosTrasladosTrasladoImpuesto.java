/**
 * ComprobanteImpuestosTrasladosTrasladoImpuesto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteImpuestosTrasladosTrasladoImpuesto implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ComprobanteImpuestosTrasladosTrasladoImpuesto(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _IVA = "IVA";
    public static final java.lang.String _IEPS = "IEPS";
    public static final ComprobanteImpuestosTrasladosTrasladoImpuesto IVA = new ComprobanteImpuestosTrasladosTrasladoImpuesto(_IVA);
    public static final ComprobanteImpuestosTrasladosTrasladoImpuesto IEPS = new ComprobanteImpuestosTrasladosTrasladoImpuesto(_IEPS);
    public java.lang.String getValue() { return _value_;}
    public static ComprobanteImpuestosTrasladosTrasladoImpuesto fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ComprobanteImpuestosTrasladosTrasladoImpuesto enumeration = (ComprobanteImpuestosTrasladosTrasladoImpuesto)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ComprobanteImpuestosTrasladosTrasladoImpuesto fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteImpuestosTrasladosTrasladoImpuesto.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>>Comprobante>Impuestos>Traslados>Traslado>impuesto"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
