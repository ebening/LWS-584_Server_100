package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.EncabezadoEbsDAO;
import com.lowes.entity.EncabezadoEbs;
import com.lowes.service.EncabezadoEbsService;

@Service
@Transactional
public class EncabezadoEbsServiceImpl implements EncabezadoEbsService {
	
	@Autowired
	private EncabezadoEbsDAO encabezadoEbsDAO;

	@Override
	public Integer createEncabezadoEbs(EncabezadoEbs encabezadoEbs) {
		return encabezadoEbsDAO.createEncabezadoEbs(encabezadoEbs);
	}

	@Override
	public EncabezadoEbs updateEncabezadoEbs(EncabezadoEbs encabezadoEbs) {
		return encabezadoEbsDAO.updateEncabezadoEbs(encabezadoEbs);
	}

	@Override
	public void deleteEncabezadoEbs(Integer id) {
		encabezadoEbsDAO.deleteEncabezadoEbs(id);
	}

	@Override
	public List<EncabezadoEbs> getAllEncabezadoEbs() {
		return encabezadoEbsDAO.getAllEncabezadoEbs();
	}

	@Override
	public EncabezadoEbs getEncabezadoEbs(Integer id) {
		return encabezadoEbsDAO.getEncabezadoEbs(id);
	}

	@Override
	public List<EncabezadoEbs> getEncabezadoEbsByInvoiceNumVendorNum(String invoiceNum, String vendorNum) {
		return encabezadoEbsDAO.getEncabezadoEbsByInvoiceNumVendorNum(invoiceNum, vendorNum);
	}

}
