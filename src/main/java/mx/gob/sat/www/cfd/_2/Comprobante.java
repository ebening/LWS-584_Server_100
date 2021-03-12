/**
 * Comprobante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.sat.www.cfd._2;

public class Comprobante  implements java.io.Serializable {
    /* Nodo requerido para expresar la información del contribuyente
     * emisor del comprobante. */
    private mx.gob.sat.www.cfd._2.ComprobanteEmisor emisor;

    /* Nodo requerido para precisar la información del contribuyente
     * receptor del comprobante. */
    private mx.gob.sat.www.cfd._2.ComprobanteReceptor receptor;

    /* Nodo requerido para enlistar los conceptos cubiertos por el
     * comprobante. */
    private mx.gob.sat.www.cfd._2.ComprobanteConceptosConcepto[] conceptos;

    /* Nodo requerido para capturar los impuestos aplicables. */
    private mx.gob.sat.www.cfd._2.ComprobanteImpuestos impuestos;

    /* Nodo opcional donde se incluirán los nodos complementarios
     * determinados por el SAT, de acuerdo a las disposiciones particulares
     * a un sector o actividad especifica. */
    private mx.gob.sat.www.cfd._2.ComprobanteComplemento complemento;

    /* Nodo opcional para recibir las extensiones al presente formato
     * que sean de utilidad al contribuyente. Para las reglas de uso del
     * mismo, referirse al formato de origen. */
    private mx.gob.sat.www.cfd._2.ComprobanteAddenda addenda;

    private java.lang.String version;  // attribute

    private java.lang.String serie;  // attribute

    private java.lang.String folio;  // attribute

    private java.util.Calendar fecha;  // attribute

    private java.lang.String sello;  // attribute

    private java.math.BigInteger noAprobacion;  // attribute

    private java.math.BigInteger anoAprobacion;  // attribute

    private java.lang.String formaDePago;  // attribute

    private java.lang.String noCertificado;  // attribute

    private java.lang.String certificado;  // attribute

    private java.lang.String condicionesDePago;  // attribute

    private java.math.BigDecimal subTotal;  // attribute

    private java.math.BigDecimal descuento;  // attribute

    private java.lang.String motivoDescuento;  // attribute

    private java.math.BigDecimal total;  // attribute

    private java.lang.String metodoDePago;  // attribute

    private mx.gob.sat.www.cfd._2.ComprobanteTipoDeComprobante tipoDeComprobante;  // attribute

    public Comprobante() {
    }

    public Comprobante(
           mx.gob.sat.www.cfd._2.ComprobanteEmisor emisor,
           mx.gob.sat.www.cfd._2.ComprobanteReceptor receptor,
           mx.gob.sat.www.cfd._2.ComprobanteConceptosConcepto[] conceptos,
           mx.gob.sat.www.cfd._2.ComprobanteImpuestos impuestos,
           mx.gob.sat.www.cfd._2.ComprobanteComplemento complemento,
           mx.gob.sat.www.cfd._2.ComprobanteAddenda addenda,
           java.lang.String version,
           java.lang.String serie,
           java.lang.String folio,
           java.util.Calendar fecha,
           java.lang.String sello,
           java.math.BigInteger noAprobacion,
           java.math.BigInteger anoAprobacion,
           java.lang.String formaDePago,
           java.lang.String noCertificado,
           java.lang.String certificado,
           java.lang.String condicionesDePago,
           java.math.BigDecimal subTotal,
           java.math.BigDecimal descuento,
           java.lang.String motivoDescuento,
           java.math.BigDecimal total,
           java.lang.String metodoDePago,
           mx.gob.sat.www.cfd._2.ComprobanteTipoDeComprobante tipoDeComprobante) {
           this.emisor = emisor;
           this.receptor = receptor;
           this.conceptos = conceptos;
           this.impuestos = impuestos;
           this.complemento = complemento;
           this.addenda = addenda;
           this.version = version;
           this.serie = serie;
           this.folio = folio;
           this.fecha = fecha;
           this.sello = sello;
           this.noAprobacion = noAprobacion;
           this.anoAprobacion = anoAprobacion;
           this.formaDePago = formaDePago;
           this.noCertificado = noCertificado;
           this.certificado = certificado;
           this.condicionesDePago = condicionesDePago;
           this.subTotal = subTotal;
           this.descuento = descuento;
           this.motivoDescuento = motivoDescuento;
           this.total = total;
           this.metodoDePago = metodoDePago;
           this.tipoDeComprobante = tipoDeComprobante;
    }


    /**
     * Gets the emisor value for this Comprobante.
     * 
     * @return emisor   * Nodo requerido para expresar la información del contribuyente
     * emisor del comprobante.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteEmisor getEmisor() {
        return emisor;
    }


    /**
     * Sets the emisor value for this Comprobante.
     * 
     * @param emisor   * Nodo requerido para expresar la información del contribuyente
     * emisor del comprobante.
     */
    public void setEmisor(mx.gob.sat.www.cfd._2.ComprobanteEmisor emisor) {
        this.emisor = emisor;
    }


    /**
     * Gets the receptor value for this Comprobante.
     * 
     * @return receptor   * Nodo requerido para precisar la información del contribuyente
     * receptor del comprobante.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteReceptor getReceptor() {
        return receptor;
    }


    /**
     * Sets the receptor value for this Comprobante.
     * 
     * @param receptor   * Nodo requerido para precisar la información del contribuyente
     * receptor del comprobante.
     */
    public void setReceptor(mx.gob.sat.www.cfd._2.ComprobanteReceptor receptor) {
        this.receptor = receptor;
    }


    /**
     * Gets the conceptos value for this Comprobante.
     * 
     * @return conceptos   * Nodo requerido para enlistar los conceptos cubiertos por el
     * comprobante.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteConceptosConcepto[] getConceptos() {
        return conceptos;
    }


    /**
     * Sets the conceptos value for this Comprobante.
     * 
     * @param conceptos   * Nodo requerido para enlistar los conceptos cubiertos por el
     * comprobante.
     */
    public void setConceptos(mx.gob.sat.www.cfd._2.ComprobanteConceptosConcepto[] conceptos) {
        this.conceptos = conceptos;
    }


    /**
     * Gets the impuestos value for this Comprobante.
     * 
     * @return impuestos   * Nodo requerido para capturar los impuestos aplicables.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteImpuestos getImpuestos() {
        return impuestos;
    }


    /**
     * Sets the impuestos value for this Comprobante.
     * 
     * @param impuestos   * Nodo requerido para capturar los impuestos aplicables.
     */
    public void setImpuestos(mx.gob.sat.www.cfd._2.ComprobanteImpuestos impuestos) {
        this.impuestos = impuestos;
    }


    /**
     * Gets the complemento value for this Comprobante.
     * 
     * @return complemento   * Nodo opcional donde se incluirán los nodos complementarios
     * determinados por el SAT, de acuerdo a las disposiciones particulares
     * a un sector o actividad especifica.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteComplemento getComplemento() {
        return complemento;
    }


    /**
     * Sets the complemento value for this Comprobante.
     * 
     * @param complemento   * Nodo opcional donde se incluirán los nodos complementarios
     * determinados por el SAT, de acuerdo a las disposiciones particulares
     * a un sector o actividad especifica.
     */
    public void setComplemento(mx.gob.sat.www.cfd._2.ComprobanteComplemento complemento) {
        this.complemento = complemento;
    }


    /**
     * Gets the addenda value for this Comprobante.
     * 
     * @return addenda   * Nodo opcional para recibir las extensiones al presente formato
     * que sean de utilidad al contribuyente. Para las reglas de uso del
     * mismo, referirse al formato de origen.
     */
    public mx.gob.sat.www.cfd._2.ComprobanteAddenda getAddenda() {
        return addenda;
    }


    /**
     * Sets the addenda value for this Comprobante.
     * 
     * @param addenda   * Nodo opcional para recibir las extensiones al presente formato
     * que sean de utilidad al contribuyente. Para las reglas de uso del
     * mismo, referirse al formato de origen.
     */
    public void setAddenda(mx.gob.sat.www.cfd._2.ComprobanteAddenda addenda) {
        this.addenda = addenda;
    }


    /**
     * Gets the version value for this Comprobante.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this Comprobante.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the serie value for this Comprobante.
     * 
     * @return serie
     */
    public java.lang.String getSerie() {
        return serie;
    }


    /**
     * Sets the serie value for this Comprobante.
     * 
     * @param serie
     */
    public void setSerie(java.lang.String serie) {
        this.serie = serie;
    }


    /**
     * Gets the folio value for this Comprobante.
     * 
     * @return folio
     */
    public java.lang.String getFolio() {
        return folio;
    }


    /**
     * Sets the folio value for this Comprobante.
     * 
     * @param folio
     */
    public void setFolio(java.lang.String folio) {
        this.folio = folio;
    }


    /**
     * Gets the fecha value for this Comprobante.
     * 
     * @return fecha
     */
    public java.util.Calendar getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Comprobante.
     * 
     * @param fecha
     */
    public void setFecha(java.util.Calendar fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the sello value for this Comprobante.
     * 
     * @return sello
     */
    public java.lang.String getSello() {
        return sello;
    }


    /**
     * Sets the sello value for this Comprobante.
     * 
     * @param sello
     */
    public void setSello(java.lang.String sello) {
        this.sello = sello;
    }


    /**
     * Gets the noAprobacion value for this Comprobante.
     * 
     * @return noAprobacion
     */
    public java.math.BigInteger getNoAprobacion() {
        return noAprobacion;
    }


    /**
     * Sets the noAprobacion value for this Comprobante.
     * 
     * @param noAprobacion
     */
    public void setNoAprobacion(java.math.BigInteger noAprobacion) {
        this.noAprobacion = noAprobacion;
    }


    /**
     * Gets the anoAprobacion value for this Comprobante.
     * 
     * @return anoAprobacion
     */
    public java.math.BigInteger getAnoAprobacion() {
        return anoAprobacion;
    }


    /**
     * Sets the anoAprobacion value for this Comprobante.
     * 
     * @param anoAprobacion
     */
    public void setAnoAprobacion(java.math.BigInteger anoAprobacion) {
        this.anoAprobacion = anoAprobacion;
    }


    /**
     * Gets the formaDePago value for this Comprobante.
     * 
     * @return formaDePago
     */
    public java.lang.String getFormaDePago() {
        return formaDePago;
    }


    /**
     * Sets the formaDePago value for this Comprobante.
     * 
     * @param formaDePago
     */
    public void setFormaDePago(java.lang.String formaDePago) {
        this.formaDePago = formaDePago;
    }


    /**
     * Gets the noCertificado value for this Comprobante.
     * 
     * @return noCertificado
     */
    public java.lang.String getNoCertificado() {
        return noCertificado;
    }


    /**
     * Sets the noCertificado value for this Comprobante.
     * 
     * @param noCertificado
     */
    public void setNoCertificado(java.lang.String noCertificado) {
        this.noCertificado = noCertificado;
    }


    /**
     * Gets the certificado value for this Comprobante.
     * 
     * @return certificado
     */
    public java.lang.String getCertificado() {
        return certificado;
    }


    /**
     * Sets the certificado value for this Comprobante.
     * 
     * @param certificado
     */
    public void setCertificado(java.lang.String certificado) {
        this.certificado = certificado;
    }


    /**
     * Gets the condicionesDePago value for this Comprobante.
     * 
     * @return condicionesDePago
     */
    public java.lang.String getCondicionesDePago() {
        return condicionesDePago;
    }


    /**
     * Sets the condicionesDePago value for this Comprobante.
     * 
     * @param condicionesDePago
     */
    public void setCondicionesDePago(java.lang.String condicionesDePago) {
        this.condicionesDePago = condicionesDePago;
    }


    /**
     * Gets the subTotal value for this Comprobante.
     * 
     * @return subTotal
     */
    public java.math.BigDecimal getSubTotal() {
        return subTotal;
    }


    /**
     * Sets the subTotal value for this Comprobante.
     * 
     * @param subTotal
     */
    public void setSubTotal(java.math.BigDecimal subTotal) {
        this.subTotal = subTotal;
    }


    /**
     * Gets the descuento value for this Comprobante.
     * 
     * @return descuento
     */
    public java.math.BigDecimal getDescuento() {
        return descuento;
    }


    /**
     * Sets the descuento value for this Comprobante.
     * 
     * @param descuento
     */
    public void setDescuento(java.math.BigDecimal descuento) {
        this.descuento = descuento;
    }


    /**
     * Gets the motivoDescuento value for this Comprobante.
     * 
     * @return motivoDescuento
     */
    public java.lang.String getMotivoDescuento() {
        return motivoDescuento;
    }


    /**
     * Sets the motivoDescuento value for this Comprobante.
     * 
     * @param motivoDescuento
     */
    public void setMotivoDescuento(java.lang.String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
    }


    /**
     * Gets the total value for this Comprobante.
     * 
     * @return total
     */
    public java.math.BigDecimal getTotal() {
        return total;
    }


    /**
     * Sets the total value for this Comprobante.
     * 
     * @param total
     */
    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }


    /**
     * Gets the metodoDePago value for this Comprobante.
     * 
     * @return metodoDePago
     */
    public java.lang.String getMetodoDePago() {
        return metodoDePago;
    }


    /**
     * Sets the metodoDePago value for this Comprobante.
     * 
     * @param metodoDePago
     */
    public void setMetodoDePago(java.lang.String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }


    /**
     * Gets the tipoDeComprobante value for this Comprobante.
     * 
     * @return tipoDeComprobante
     */
    public mx.gob.sat.www.cfd._2.ComprobanteTipoDeComprobante getTipoDeComprobante() {
        return tipoDeComprobante;
    }


    /**
     * Sets the tipoDeComprobante value for this Comprobante.
     * 
     * @param tipoDeComprobante
     */
    public void setTipoDeComprobante(mx.gob.sat.www.cfd._2.ComprobanteTipoDeComprobante tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Comprobante)) return false;
        Comprobante other = (Comprobante) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emisor==null && other.getEmisor()==null) || 
             (this.emisor!=null &&
              this.emisor.equals(other.getEmisor()))) &&
            ((this.receptor==null && other.getReceptor()==null) || 
             (this.receptor!=null &&
              this.receptor.equals(other.getReceptor()))) &&
            ((this.conceptos==null && other.getConceptos()==null) || 
             (this.conceptos!=null &&
              java.util.Arrays.equals(this.conceptos, other.getConceptos()))) &&
            ((this.impuestos==null && other.getImpuestos()==null) || 
             (this.impuestos!=null &&
              this.impuestos.equals(other.getImpuestos()))) &&
            ((this.complemento==null && other.getComplemento()==null) || 
             (this.complemento!=null &&
              this.complemento.equals(other.getComplemento()))) &&
            ((this.addenda==null && other.getAddenda()==null) || 
             (this.addenda!=null &&
              this.addenda.equals(other.getAddenda()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.serie==null && other.getSerie()==null) || 
             (this.serie!=null &&
              this.serie.equals(other.getSerie()))) &&
            ((this.folio==null && other.getFolio()==null) || 
             (this.folio!=null &&
              this.folio.equals(other.getFolio()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.sello==null && other.getSello()==null) || 
             (this.sello!=null &&
              this.sello.equals(other.getSello()))) &&
            ((this.noAprobacion==null && other.getNoAprobacion()==null) || 
             (this.noAprobacion!=null &&
              this.noAprobacion.equals(other.getNoAprobacion()))) &&
            ((this.anoAprobacion==null && other.getAnoAprobacion()==null) || 
             (this.anoAprobacion!=null &&
              this.anoAprobacion.equals(other.getAnoAprobacion()))) &&
            ((this.formaDePago==null && other.getFormaDePago()==null) || 
             (this.formaDePago!=null &&
              this.formaDePago.equals(other.getFormaDePago()))) &&
            ((this.noCertificado==null && other.getNoCertificado()==null) || 
             (this.noCertificado!=null &&
              this.noCertificado.equals(other.getNoCertificado()))) &&
            ((this.certificado==null && other.getCertificado()==null) || 
             (this.certificado!=null &&
              this.certificado.equals(other.getCertificado()))) &&
            ((this.condicionesDePago==null && other.getCondicionesDePago()==null) || 
             (this.condicionesDePago!=null &&
              this.condicionesDePago.equals(other.getCondicionesDePago()))) &&
            ((this.subTotal==null && other.getSubTotal()==null) || 
             (this.subTotal!=null &&
              this.subTotal.equals(other.getSubTotal()))) &&
            ((this.descuento==null && other.getDescuento()==null) || 
             (this.descuento!=null &&
              this.descuento.equals(other.getDescuento()))) &&
            ((this.motivoDescuento==null && other.getMotivoDescuento()==null) || 
             (this.motivoDescuento!=null &&
              this.motivoDescuento.equals(other.getMotivoDescuento()))) &&
            ((this.total==null && other.getTotal()==null) || 
             (this.total!=null &&
              this.total.equals(other.getTotal()))) &&
            ((this.metodoDePago==null && other.getMetodoDePago()==null) || 
             (this.metodoDePago!=null &&
              this.metodoDePago.equals(other.getMetodoDePago()))) &&
            ((this.tipoDeComprobante==null && other.getTipoDeComprobante()==null) || 
             (this.tipoDeComprobante!=null &&
              this.tipoDeComprobante.equals(other.getTipoDeComprobante())));
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
        if (getEmisor() != null) {
            _hashCode += getEmisor().hashCode();
        }
        if (getReceptor() != null) {
            _hashCode += getReceptor().hashCode();
        }
        if (getConceptos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConceptos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConceptos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getImpuestos() != null) {
            _hashCode += getImpuestos().hashCode();
        }
        if (getComplemento() != null) {
            _hashCode += getComplemento().hashCode();
        }
        if (getAddenda() != null) {
            _hashCode += getAddenda().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getSerie() != null) {
            _hashCode += getSerie().hashCode();
        }
        if (getFolio() != null) {
            _hashCode += getFolio().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getSello() != null) {
            _hashCode += getSello().hashCode();
        }
        if (getNoAprobacion() != null) {
            _hashCode += getNoAprobacion().hashCode();
        }
        if (getAnoAprobacion() != null) {
            _hashCode += getAnoAprobacion().hashCode();
        }
        if (getFormaDePago() != null) {
            _hashCode += getFormaDePago().hashCode();
        }
        if (getNoCertificado() != null) {
            _hashCode += getNoCertificado().hashCode();
        }
        if (getCertificado() != null) {
            _hashCode += getCertificado().hashCode();
        }
        if (getCondicionesDePago() != null) {
            _hashCode += getCondicionesDePago().hashCode();
        }
        if (getSubTotal() != null) {
            _hashCode += getSubTotal().hashCode();
        }
        if (getDescuento() != null) {
            _hashCode += getDescuento().hashCode();
        }
        if (getMotivoDescuento() != null) {
            _hashCode += getMotivoDescuento().hashCode();
        }
        if (getTotal() != null) {
            _hashCode += getTotal().hashCode();
        }
        if (getMetodoDePago() != null) {
            _hashCode += getMetodoDePago().hashCode();
        }
        if (getTipoDeComprobante() != null) {
            _hashCode += getTipoDeComprobante().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Comprobante.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">Comprobante"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>version"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("serie");
        attrField.setXmlName(new javax.xml.namespace.QName("", "serie"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>serie"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("folio");
        attrField.setXmlName(new javax.xml.namespace.QName("", "folio"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>folio"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("fecha");
        attrField.setXmlName(new javax.xml.namespace.QName("", "fecha"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>fecha"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("sello");
        attrField.setXmlName(new javax.xml.namespace.QName("", "sello"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>sello"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("noAprobacion");
        attrField.setXmlName(new javax.xml.namespace.QName("", "noAprobacion"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>noAprobacion"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("anoAprobacion");
        attrField.setXmlName(new javax.xml.namespace.QName("", "anoAprobacion"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>anoAprobacion"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("formaDePago");
        attrField.setXmlName(new javax.xml.namespace.QName("", "formaDePago"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>formaDePago"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("noCertificado");
        attrField.setXmlName(new javax.xml.namespace.QName("", "noCertificado"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>noCertificado"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("certificado");
        attrField.setXmlName(new javax.xml.namespace.QName("", "certificado"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>certificado"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("condicionesDePago");
        attrField.setXmlName(new javax.xml.namespace.QName("", "condicionesDePago"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>condicionesDePago"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("subTotal");
        attrField.setXmlName(new javax.xml.namespace.QName("", "subTotal"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("descuento");
        attrField.setXmlName(new javax.xml.namespace.QName("", "descuento"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("motivoDescuento");
        attrField.setXmlName(new javax.xml.namespace.QName("", "motivoDescuento"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>motivoDescuento"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("total");
        attrField.setXmlName(new javax.xml.namespace.QName("", "total"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "t_Importe"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("metodoDePago");
        attrField.setXmlName(new javax.xml.namespace.QName("", "metodoDePago"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>metodoDePago"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("tipoDeComprobante");
        attrField.setXmlName(new javax.xml.namespace.QName("", "tipoDeComprobante"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>tipoDeComprobante"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Emisor"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receptor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Receptor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Receptor"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Conceptos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>>Comprobante>Conceptos>Concepto"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Concepto"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impuestos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Impuestos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Impuestos"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complemento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Complemento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Complemento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addenda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", "Addenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.sat.gob.mx/cfd/2", ">>Comprobante>Addenda"));
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
