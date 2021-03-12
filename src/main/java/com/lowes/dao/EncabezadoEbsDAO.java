package com.lowes.dao;

import java.util.List;

import com.lowes.entity.EncabezadoEbs;

public interface EncabezadoEbsDAO {

	public Integer createEncabezadoEbs(EncabezadoEbs encabezadoEbs);
    public EncabezadoEbs updateEncabezadoEbs(EncabezadoEbs encabezadoEbs);
    public void deleteEncabezadoEbs(Integer id);
    public List<EncabezadoEbs> getAllEncabezadoEbs();
    public EncabezadoEbs getEncabezadoEbs(Integer id);	
    public List<EncabezadoEbs> getEncabezadoEbsByInvoiceNumVendorNum(String invoiceNum, String vendorNum);
	
}