/**
 * T_Ubicacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;


/**
 * Tipo definido para expresar domicilios o direcciones
 */
public class T_Ubicacion  implements java.io.Serializable {
    private java.lang.String calle;  // attribute

    private java.lang.String noExterior;  // attribute

    private java.lang.String noInterior;  // attribute

    private java.lang.String colonia;  // attribute

    private java.lang.String localidad;  // attribute

    private java.lang.String referencia;  // attribute

    private java.lang.String municipio;  // attribute

    private java.lang.String estado;  // attribute

    private java.lang.String pais;  // attribute

    private java.lang.String codigoPostal;  // attribute

    public T_Ubicacion() {
    }

    public T_Ubicacion(
           java.lang.String calle,
           java.lang.String noExterior,
           java.lang.String noInterior,
           java.lang.String colonia,
           java.lang.String localidad,
           java.lang.String referencia,
           java.lang.String municipio,
           java.lang.String estado,
           java.lang.String pais,
           java.lang.String codigoPostal) {
           this.calle = calle;
           this.noExterior = noExterior;
           this.noInterior = noInterior;
           this.colonia = colonia;
           this.localidad = localidad;
           this.referencia = referencia;
           this.municipio = municipio;
           this.estado = estado;
           this.pais = pais;
           this.codigoPostal = codigoPostal;
    }


    /**
     * Gets the calle value for this T_Ubicacion.
     * 
     * @return calle
     */
    public java.lang.String getCalle() {
        return calle;
    }


    /**
     * Sets the calle value for this T_Ubicacion.
     * 
     * @param calle
     */
    public void setCalle(java.lang.String calle) {
        this.calle = calle;
    }


    /**
     * Gets the noExterior value for this T_Ubicacion.
     * 
     * @return noExterior
     */
    public java.lang.String getNoExterior() {
        return noExterior;
    }


    /**
     * Sets the noExterior value for this T_Ubicacion.
     * 
     * @param noExterior
     */
    public void setNoExterior(java.lang.String noExterior) {
        this.noExterior = noExterior;
    }


    /**
     * Gets the noInterior value for this T_Ubicacion.
     * 
     * @return noInterior
     */
    public java.lang.String getNoInterior() {
        return noInterior;
    }


    /**
     * Sets the noInterior value for this T_Ubicacion.
     * 
     * @param noInterior
     */
    public void setNoInterior(java.lang.String noInterior) {
        this.noInterior = noInterior;
    }


    /**
     * Gets the colonia value for this T_Ubicacion.
     * 
     * @return colonia
     */
    public java.lang.String getColonia() {
        return colonia;
    }


    /**
     * Sets the colonia value for this T_Ubicacion.
     * 
     * @param colonia
     */
    public void setColonia(java.lang.String colonia) {
        this.colonia = colonia;
    }


    /**
     * Gets the localidad value for this T_Ubicacion.
     * 
     * @return localidad
     */
    public java.lang.String getLocalidad() {
        return localidad;
    }


    /**
     * Sets the localidad value for this T_Ubicacion.
     * 
     * @param localidad
     */
    public void setLocalidad(java.lang.String localidad) {
        this.localidad = localidad;
    }


    /**
     * Gets the referencia value for this T_Ubicacion.
     * 
     * @return referencia
     */
    public java.lang.String getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this T_Ubicacion.
     * 
     * @param referencia
     */
    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the municipio value for this T_Ubicacion.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this T_Ubicacion.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the estado value for this T_Ubicacion.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this T_Ubicacion.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the pais value for this T_Ubicacion.
     * 
     * @return pais
     */
    public java.lang.String getPais() {
        return pais;
    }


    /**
     * Sets the pais value for this T_Ubicacion.
     * 
     * @param pais
     */
    public void setPais(java.lang.String pais) {
        this.pais = pais;
    }


    /**
     * Gets the codigoPostal value for this T_Ubicacion.
     * 
     * @return codigoPostal
     */
    public java.lang.String getCodigoPostal() {
        return codigoPostal;
    }


    /**
     * Sets the codigoPostal value for this T_Ubicacion.
     * 
     * @param codigoPostal
     */
    public void setCodigoPostal(java.lang.String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof T_Ubicacion)) return false;
        T_Ubicacion other = (T_Ubicacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.calle==null && other.getCalle()==null) || 
             (this.calle!=null &&
              this.calle.equals(other.getCalle()))) &&
            ((this.noExterior==null && other.getNoExterior()==null) || 
             (this.noExterior!=null &&
              this.noExterior.equals(other.getNoExterior()))) &&
            ((this.noInterior==null && other.getNoInterior()==null) || 
             (this.noInterior!=null &&
              this.noInterior.equals(other.getNoInterior()))) &&
            ((this.colonia==null && other.getColonia()==null) || 
             (this.colonia!=null &&
              this.colonia.equals(other.getColonia()))) &&
            ((this.localidad==null && other.getLocalidad()==null) || 
             (this.localidad!=null &&
              this.localidad.equals(other.getLocalidad()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.pais==null && other.getPais()==null) || 
             (this.pais!=null &&
              this.pais.equals(other.getPais()))) &&
            ((this.codigoPostal==null && other.getCodigoPostal()==null) || 
             (this.codigoPostal!=null &&
              this.codigoPostal.equals(other.getCodigoPostal())));
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
        if (getCalle() != null) {
            _hashCode += getCalle().hashCode();
        }
        if (getNoExterior() != null) {
            _hashCode += getNoExterior().hashCode();
        }
        if (getNoInterior() != null) {
            _hashCode += getNoInterior().hashCode();
        }
        if (getColonia() != null) {
            _hashCode += getColonia().hashCode();
        }
        if (getLocalidad() != null) {
            _hashCode += getLocalidad().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getPais() != null) {
            _hashCode += getPais().hashCode();
        }
        if (getCodigoPostal() != null) {
            _hashCode += getCodigoPostal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(T_Ubicacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Ubicacion"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("calle");
        attrField.setXmlName(new javax.xml.namespace.QName("", "calle"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>calle"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("noExterior");
        attrField.setXmlName(new javax.xml.namespace.QName("", "noExterior"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>noExterior"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("noInterior");
        attrField.setXmlName(new javax.xml.namespace.QName("", "noInterior"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>noInterior"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("colonia");
        attrField.setXmlName(new javax.xml.namespace.QName("", "colonia"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>colonia"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("localidad");
        attrField.setXmlName(new javax.xml.namespace.QName("", "localidad"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>localidad"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("referencia");
        attrField.setXmlName(new javax.xml.namespace.QName("", "referencia"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>referencia"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("municipio");
        attrField.setXmlName(new javax.xml.namespace.QName("", "municipio"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>municipio"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("estado");
        attrField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>estado"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("pais");
        attrField.setXmlName(new javax.xml.namespace.QName("", "pais"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>pais"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("codigoPostal");
        attrField.setXmlName(new javax.xml.namespace.QName("", "codigoPostal"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">t_Ubicacion>codigoPostal"));
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
