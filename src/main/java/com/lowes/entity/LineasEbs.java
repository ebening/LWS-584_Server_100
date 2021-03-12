package com.lowes.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINEAS_EBS" , schema = "LWS584")
public class LineasEbs implements java.io.Serializable {

	private int id;
	private Integer invoiceId;
	private Integer facturaId;
	private Integer lineNumber;
	private String lineTypeLookupCode;
	private BigDecimal amount;
	private String description;
	private Integer taxCode;
	private String distCodeConcatenated;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private Integer orgId;
	private String assetsTrackingFlag;
	private String assetBookTypeCode;
	private String assetCategoryIdMajor;
	private String assetCategoryIdMinor;

	public LineasEbs() {
	}

	public LineasEbs(int id) {
		this.id = id;
	}

	public LineasEbs(int id, int facturaId, Integer invoiceId, Integer lineNumber, String lineTypeLookupCode,
			BigDecimal amount, String description, Integer taxCode, String distCodeConcatenated, String attribute3,
			String attribute4, String attribute5, Integer orgId, String assetsTrackingFlag, String assetBookTypeCode,
			String assetCategoryIdMajor, String assetCategoryIdMinor) {
		this.id = id;
		this.facturaId = facturaId;
		this.invoiceId = invoiceId;
		this.lineNumber = lineNumber;
		this.lineTypeLookupCode = lineTypeLookupCode;
		this.amount = amount;
		this.description = description;
		this.taxCode = taxCode;
		this.distCodeConcatenated = distCodeConcatenated;
		this.attribute3 = attribute3;
		this.attribute4 = attribute4;
		this.attribute5 = attribute5;
		this.orgId = orgId;
		this.assetsTrackingFlag = assetsTrackingFlag;
		this.assetBookTypeCode = assetBookTypeCode;
		this.assetCategoryIdMajor = assetCategoryIdMajor;
		this.assetCategoryIdMinor = assetCategoryIdMinor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "FACTURA_ID")
	public Integer getFacturaId() {
		return this.facturaId;
	}

	public void setFacturaId(Integer facturaId) {
		this.facturaId = facturaId;
	}
	

	@Column(name = "INVOICE_ID", unique = true)
	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "LINE_NUMBER")
	public Integer getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Column(name = "LINE_TYPE_LOOKUP_CODE", length = 25)
	public String getLineTypeLookupCode() {
		return this.lineTypeLookupCode;
	}

	public void setLineTypeLookupCode(String lineTypeLookupCode) {
		this.lineTypeLookupCode = lineTypeLookupCode;
	}

	@Column(name = "AMOUNT", precision = 17)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "DESCRIPTION", length = 240)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TAX_CODE")
	public Integer getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(Integer taxCode) {
		this.taxCode = taxCode;
	}

	@Column(name = "DIST_CODE_CONCATENATED", length = 250)
	public String getDistCodeConcatenated() {
		return this.distCodeConcatenated;
	}

	public void setDistCodeConcatenated(String distCodeConcatenated) {
		this.distCodeConcatenated = distCodeConcatenated;
	}

	@Column(name = "ATTRIBUTE3", length = 150)
	public String getAttribute3() {
		return this.attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name = "ATTRIBUTE4", length = 150)
	public String getAttribute4() {
		return this.attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name = "ATTRIBUTE5", length = 150)
	public String getAttribute5() {
		return this.attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name = "ORG_ID")
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ASSETS_TRACKING_FLAG", length = 1)
	public String getAssetsTrackingFlag() {
		return this.assetsTrackingFlag;
	}

	public void setAssetsTrackingFlag(String assetsTrackingFlag) {
		this.assetsTrackingFlag = assetsTrackingFlag;
	}

	@Column(name = "ASSET_BOOK_TYPE_CODE", length = 15)
	public String getAssetBookTypeCode() {
		return this.assetBookTypeCode;
	}

	public void setAssetBookTypeCode(String assetBookTypeCode) {
		this.assetBookTypeCode = assetBookTypeCode;
	}

	@Column(name = "ASSET_CATEGORY_ID_MAJOR", length = 100)
	public String getAssetCategoryIdMajor() {
		return this.assetCategoryIdMajor;
	}

	public void setAssetCategoryIdMajor(String assetCategoryIdMajor) {
		this.assetCategoryIdMajor = assetCategoryIdMajor;
	}

	@Column(name = "ASSET_CATEGORY_ID_MINOR", length = 100)
	public String getAssetCategoryIdMinor() {
		return this.assetCategoryIdMinor;
	}

	public void setAssetCategoryIdMinor(String assetCategoryIdMinor) {
		this.assetCategoryIdMinor = assetCategoryIdMinor;
	}

}
