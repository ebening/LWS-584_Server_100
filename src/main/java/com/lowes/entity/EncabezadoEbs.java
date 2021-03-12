package com.lowes.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "ENCABEZADO_EBS", schema = "LWS584")
public class EncabezadoEbs implements java.io.Serializable {

	private int invoiceId;
	private int solicitudId;
	private String invoiceNum;
	private String invoiceTypeLookupCode;
	private Date invoiceDate;
	private String vendorNum;
	private BigDecimal invoiceAmount;
	private String invoiceCurrencyCode;
	private String description;
	private String attribute15;
	private String status;
	private String source;
	private String paymentMethodLookupCode;
	private Integer orgId;
	private String prepayNum;
	private BigDecimal prepayApplyAmount;

	public EncabezadoEbs() {
	}
	
	public EncabezadoEbs(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public EncabezadoEbs(int invoiceId, int solicitudId) {
		this.invoiceId = invoiceId;
		this.solicitudId = solicitudId;
	}

	public EncabezadoEbs(int invoiceId, int solicitudId, String invoiceNum, String invoiceTypeLookupCode,
			Date invoiceDate, String vendorNum, BigDecimal invoiceAmount, String invoiceCurrencyCode,
			String description, String attribute15, String status, String source, String paymentMethodLookupCode,
			Integer orgId) {
		this.invoiceId = invoiceId;
		this.solicitudId = solicitudId;
		this.invoiceNum = invoiceNum;
		this.invoiceTypeLookupCode = invoiceTypeLookupCode;
		this.invoiceDate = invoiceDate;
		this.vendorNum = vendorNum;
		this.invoiceAmount = invoiceAmount;
		this.invoiceCurrencyCode = invoiceCurrencyCode;
		this.description = description;
		this.attribute15 = attribute15;
		this.status = status;
		this.source = source;
		this.paymentMethodLookupCode = paymentMethodLookupCode;
		this.orgId = orgId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INVOICE_ID", unique = true, nullable = false)
	public int getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "SOLICITUD_ID", nullable = false)
	public int getSolicitudId() {
		return this.solicitudId;
	}

	public void setSolicitudId(int solicitudId) {
		this.solicitudId = solicitudId;
	}

	@Column(name = "INVOICE_NUM", length = 50)
	public String getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	@Column(name = "INVOICE_TYPE_LOOKUP_CODE", length = 25)
	public String getInvoiceTypeLookupCode() {
		return this.invoiceTypeLookupCode;
	}

	public void setInvoiceTypeLookupCode(String invoiceTypeLookupCode) {
		this.invoiceTypeLookupCode = invoiceTypeLookupCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INVOICE_DATE", length = 10)
	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "VENDOR_NUM", length = 30)
	public String getVendorNum() {
		return this.vendorNum;
	}

	public void setVendorNum(String vendorNum) {
		this.vendorNum = vendorNum;
	}

	@Column(name = "INVOICE_AMOUNT", precision = 17)
	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	@Column(name = "INVOICE_CURRENCY_CODE", length = 3)
	public String getInvoiceCurrencyCode() {
		return this.invoiceCurrencyCode;
	}

	public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
		this.invoiceCurrencyCode = invoiceCurrencyCode;
	}

	@Column(name = "DESCRIPTION", length = 240)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ATTRIBUTE15", length = 150)
	public String getAttribute15() {
		return this.attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	@Column(name = "STATUS", length = 25)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "SOURCE", length = 80)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "PAYMENT_METHOD_LOOKUP_CODE", length = 25)
	public String getPaymentMethodLookupCode() {
		return this.paymentMethodLookupCode;
	}

	public void setPaymentMethodLookupCode(String paymentMethodLookupCode) {
		this.paymentMethodLookupCode = paymentMethodLookupCode;
	}

	@Column(name = "ORG_ID")
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	@Column(name = "PREPAY_APPLY_AMOUNT", precision = 17)
	public BigDecimal getPrepayApplyAmount() {
		return prepayApplyAmount;
	}

	public void setPrepayApplyAmount(BigDecimal prepayApplyAmount) {
		this.prepayApplyAmount = prepayApplyAmount;
	}
	
	@Column(name = "PREPAY_NUM", length = 25)
	public String getPrepayNum() {
		return prepayNum;
	}

	public void setPrepayNum(String prepayNum) {
		this.prepayNum = prepayNum;
	}
}
