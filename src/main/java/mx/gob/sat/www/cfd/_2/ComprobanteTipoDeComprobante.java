/**
 * ComprobanteTipoDeComprobante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteTipoDeComprobante implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ComprobanteTipoDeComprobante(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ingreso = "ingreso";
    public static final java.lang.String _egreso = "egreso";
    public static final java.lang.String _traslado = "traslado";
    public static final ComprobanteTipoDeComprobante ingreso = new ComprobanteTipoDeComprobante(_ingreso);
    public static final ComprobanteTipoDeComprobante egreso = new ComprobanteTipoDeComprobante(_egreso);
    public static final ComprobanteTipoDeComprobante traslado = new ComprobanteTipoDeComprobante(_traslado);
    public java.lang.String getValue() { return _value_;}
    public static ComprobanteTipoDeComprobante fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ComprobanteTipoDeComprobante enumeration = (ComprobanteTipoDeComprobante)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ComprobanteTipoDeComprobante fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ComprobanteTipoDeComprobante.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>tipoDeComprobante"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
