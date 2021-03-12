/**
 * ComprobanteConceptosConcepto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class ComprobanteConceptosConcepto  implements java.io.Serializable {
    /* Nodo opcional para introducir la información aduanera aplicable
     * cuando se trate de ventas de primera mano de mercancías importadas. */
    private mx.gob.sat.www.cfd._2.T_InformacionAduanera[] informacionAduanera;

    /* Nodo opcional para asentar el número de cuenta predial con
     * el que fue registrado el inmueble, en el sistema catastral de la entidad
     * federativa de que trate. */
    private mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoCuentaPredial cuentaPredial;

    /* Nodo opcional donde se incluirán los nodos complementarios
     * de extensión al concepto, definidos por el SAT, de acuerdo a disposiciones
     * particulares a un sector o actividad especifica. */
    private mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoComplementoConcepto complementoConcepto;

    /* Nodo opcional para expresar las partes o componentes que integran
     * la totalidad del concepto expresado en el comprobante fiscal digital */
    private mx.gob.sat.www.cfd._2.T_InformacionAduanera[][] parte;

    private java.math.BigDecimal cantidad;  // attribute

    private java.lang.String unidad;  // attribute

    private java.lang.String noIdentificacion;  // attribute

    private java.lang.String descripcion;  // attribute

    private java.math.BigDecimal valorUnitario;  // attribute

    private java.math.BigDecimal importe;  // attribute

    public ComprobanteConceptosConcepto() {
    }

    public ComprobanteConceptosConcepto(
           mx.gob.sat.www.cfd._2.T_InformacionAduanera[] informacionAduanera,
           mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoCuentaPredial cuentaPredial,
           mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoComplementoConcepto complementoConcepto,
           mx.gob.sat.www.cfd._2.T_InformacionAduanera[][] parte,
           java.math.BigDecimal cantidad,
           java.lang.String unidad,
           java.lang.String noIdentificacion,
           java.lang.String descripcion,
           java.math.BigDecimal valorUnitario,
           java.math.BigDecimal importe) {
           this.informacionAduanera = informacionAduanera;
           this.cuentaPredial = cuentaPredial;
           this.complementoConcepto = complementoConcepto;
           this.parte = parte;
           this.cantidad = cantidad;
           this.unidad = unidad;
           this.noIdentificacion = noIdentificacion;
           this.descripcion = descripcion;
           this.valorUnitario = valorUnitario;
           this.importe = importe;
    }


    /**
     * Gets the informacionAduanera value for this ComprobanteConceptosConcepto.
     * 
     * @return informacionAduanera   * Nodo opcional para introducir la información aduanera aplicable
     * cuando se trate de ventas de primera mano de mercancías importadas.
     */
    public mx.gob.sat.www.cfd._2.T_InformacionAduanera[] getInformacionAduanera() {
        return informacionAduanera;
    }


    /**
     * Sets the informacionAduanera value for this ComprobanteConceptosConcepto.
     * 
     * @param informacionAduanera   * Nodo opcional para introducir la información aduanera aplicable
     * cuando se trate de ventas de primera mano de mercancías importadas.
     */
    public void setInformacionAduanera(mx.gob.sat.www.cfd._2.T_InformacionAduanera[] informacionAduanera) {
        this.informacionAduanera = informacionAduanera;
    }

    public mx.gob.sat.www.cfd._2.T_InformacionAduanera getInformacionAduanera(int i) {
        return this.informacionAduanera[i];
    }

    public void setInformacionAduanera(int i, mx.gob.sat.www.cfd._2.T_InformacionAduanera _value) {
        this.informacionAduanera[i] = _value;
    }


    /**
     * Gets the cuentaPredial value for this ComprobanteConceptosConcepto.
     * 
     * @return cuentaPredial   * Nodo opcional para asentar el número de cuenta predial con
     * el que fue registrado el inmueble, en el sistema catastral de la entidad
     * federativa de que trate.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoCuentaPredial getCuentaPredial() {
        return cuentaPredial;
    }


    /**
     * Sets the cuentaPredial value for this ComprobanteConceptosConcepto.
     * 
     * @param cuentaPredial   * Nodo opcional para asentar el número de cuenta predial con
     * el que fue registrado el inmueble, en el sistema catastral de la entidad
     * federativa de que trate.
     */
    public void setCuentaPredial(mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoCuentaPredial cuentaPredial) {
        this.cuentaPredial = cuentaPredial;
    }


    /**
     * Gets the complementoConcepto value for this ComprobanteConceptosConcepto.
     * 
     * @return complementoConcepto   * Nodo opcional donde se incluirán los nodos complementarios
     * de extensión al concepto, definidos por el SAT, de acuerdo a disposiciones
     * particulares a un sector o actividad especifica.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoComplementoConcepto getComplementoConcepto() {
        return complementoConcepto;
    }


    /**
     * Sets the complementoConcepto value for this ComprobanteConceptosConcepto.
     * 
     * @param complementoConcepto   * Nodo opcional donde se incluirán los nodos complementarios
     * de extensión al concepto, definidos por el SAT, de acuerdo a disposiciones
     * particulares a un sector o actividad especifica.
     */
    public void setComplementoConcepto(mx.gob.sat.www.cfd._2.ComprobanteConceptosConceptoComplementoConcepto complementoConcepto) {
        this.complementoConcepto = complementoConcepto;
    }


    /**
     * Gets the parte value for this ComprobanteConceptosConcepto.
     * 
     * @return parte   * Nodo opcional para expresar las partes o componentes que integran
     * la totalidad del concepto expresado en el comprobante fiscal digital
     */
    public mx.gob.sat.www.cfd._2.T_InformacionAduanera[][] getParte() {
        return parte;
    }


    /**
     * Sets the parte value for this ComprobanteConceptosConcepto.
     * 
     * @param parte   * Nodo opcional para expresar las partes o componentes que integran
     * la totalidad del concepto expresado en el comprobante fiscal digital
     */
    public void setParte(mx.gob.sat.www.cfd._2.T_InformacionAduanera[][] parte) {
        this.parte = parte;
    }

    public mx.gob.sat.www.cfd._2.T_InformacionAduanera[] getParte(int i) {
        return this.parte[i];
    }

    public void setParte(int i, mx.gob.sat.www.cfd._2.T_InformacionAduanera[] _value) {
        this.parte[i] = _value;
    }


    /**
     * Gets the cantidad value for this ComprobanteConceptosConcepto.
     * 
     * @return cantidad
     */
    public java.math.BigDecimal getCantidad() {
        return cantidad;
    }


    /**
     * Sets the cantidad value for this ComprobanteConceptosConcepto.
     * 
     * @param cantidad
     */
    public void setCantidad(java.math.BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Gets the unidad value for this ComprobanteConceptosConcepto.
     * 
     * @return unidad
     */
    public java.lang.String getUnidad() {
        return unidad;
    }


    /**
     * Sets the unidad value for this ComprobanteConceptosConcepto.
     * 
     * @param unidad
     */
    public void setUnidad(java.lang.String unidad) {
        this.unidad = unidad;
    }


    /**
     * Gets the noIdentificacion value for this ComprobanteConceptosConcepto.
     * 
     * @return noIdentificacion
     */
    public java.lang.String getNoIdentificacion() {
        return noIdentificacion;
    }


    /**
     * Sets the noIdentificacion value for this ComprobanteConceptosConcepto.
     * 
     * @param noIdentificacion
     */
    public void setNoIdentificacion(java.lang.String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }


    /**
     * Gets the descripcion value for this ComprobanteConceptosConcepto.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this ComprobanteConceptosConcepto.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the valorUnitario value for this ComprobanteConceptosConcepto.
     * 
     * @return valorUnitario
     */
    public java.math.BigDecimal getValorUnitario() {
        return valorUnitario;
    }


    /**
     * Sets the valorUnitario value for this ComprobanteConceptosConcepto.
     * 
     * @param valorUnitario
     */
    public void setValorUnitario(java.math.BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }


    /**
     * Gets the importe value for this ComprobanteConceptosConcepto.
     * 
     * @return importe
     */
    public java.math.BigDecimal getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this ComprobanteConceptosConcepto.
     * 
     * @param importe
     */
    public void setImporte(java.math.BigDecimal importe) {
        this.importe = importe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComprobanteConceptosConcepto)) return false;
        ComprobanteConceptosConcepto other = (ComprobanteConceptosConcepto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.informacionAduanera==null && other.getInformacionAduanera()==null) || 
             (this.informacionAduanera!=null &&
              java.util.Arrays.equals(this.informacionAduanera, other.getInformacionAduanera()))) &&
            ((this.cuentaPredial==null && other.getCuentaPredial()==null) || 
             (this.cuentaPredial!=null &&
              this.cuentaPredial.equals(other.getCuentaPredial()))) &&
            ((this.complementoConcepto==null && other.getComplementoConcepto()==null) || 
             (this.complementoConcepto!=null &&
              this.complementoConcepto.equals(other.getComplementoConcepto()))) &&
            ((this.parte==null && other.getParte()==null) || 
             (this.parte!=null &&
              java.util.Arrays.equals(this.parte, other.getParte()))) &&
            ((this.cantidad==null && other.getCantidad()==null) || 
             (this.cantidad!=null &&
              this.cantidad.equals(other.getCantidad()))) &&
            ((this.unidad==null && other.getUnidad()==null) || 
             (this.unidad!=null &&
              this.unidad.equals(other.getUnidad()))) &&
            ((this.noIdentificacion==null && other.getNoIdentificacion()==null) || 
             (this.noIdentificacion!=null &&
              this.noIdentificacion.equals(other.getNoIdentificacion()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.valorUnitario==null && other.getValorUnitario()==null) || 
             (this.valorUnitario!=null &&
              this.valorUnitario.equals(other.getValorUnitario()))) &&
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
        if (getInformacionAduanera() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInformacionAduanera());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInformacionAduanera(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCuentaPredial() != null) {
            _hashCode += getCuentaPredial().hashCode();
        }
        if (getComplementoConcepto() != null) {
            _hashCode += getComplementoConcepto().hashCode();
        }
        if (getParte() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParte());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParte(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCantidad() != null) {
            _hashCode += getCantidad().hashCode();
        }
        if (getUnidad() != null) {
            _hashCode += getUnidad().hashCode();
        }
        if (getNoIdentificacion() != null) {
            _hashCode += getNoIdentificacion().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getValorUnitario() != null) {
            _hashCode += getValorUnitario().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComprobanteConceptosConcepto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>Comprobante>Conceptos>Concepto"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("cantidad");
        attrField.setXmlName(new javax.xml.namespace.QName("", "cantidad"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>cantidad"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("unidad");
        attrField.setXmlName(new javax.xml.namespace.QName("", "unidad"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>unidad"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("noIdentificacion");
        attrField.setXmlName(new javax.xml.namespace.QName("", "noIdentificacion"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>noIdentificacion"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("descripcion");
        attrField.setXmlName(new javax.xml.namespace.QName("", "descripcion"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>descripcion"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("valorUnitario");
        attrField.setXmlName(new javax.xml.namespace.QName("", "valorUnitario"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("importe");
        attrField.setXmlName(new javax.xml.namespace.QName("", "importe"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informacionAduanera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "InformacionAduanera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_InformacionAduanera"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaPredial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "CuentaPredial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>CuentaPredial"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complementoConcepto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "ComplementoConcepto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>ComplementoConcepto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Parte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>>Comprobante>Conceptos>Concepto>Parte"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
