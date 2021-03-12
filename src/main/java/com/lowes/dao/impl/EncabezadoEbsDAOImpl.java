package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.EncabezadoEbsDAO;
import com.lowes.entity.EncabezadoEbs;
import com.lowes.util.HibernateUtil;

@Repository
public class EncabezadoEbsDAOImpl implements EncabezadoEbsDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createEncabezadoEbs(EncabezadoEbs encabezadoEbs) {
		return (Integer) hibernateUtil.create(encabezadoEbs);
	}

	@Override
	public EncabezadoEbs updateEncabezadoEbs(EncabezadoEbs encabezadoEbs) {
		return hibernateUtil.update(encabezadoEbs);
	}

	@Override
	public void deleteEncabezadoEbs(Integer id) {
		hibernateUtil.delete(new EncabezadoEbs(id));
	}

	@Override
	public List<EncabezadoEbs> getAllEncabezadoEbs() {
		return hibernateUtil.fetchAll(EncabezadoEbs.class);
	}

	@Override
	public EncabezadoEbs getEncabezadoEbs(Integer id) {
		return hibernateUtil.fetchById(id,EncabezadoEbs.class);
	}
	
	@Override
	public List<EncabezadoEbs> getEncabezadoEbsByInvoiceNumVendorNum(String invoiceNum, String vendorNum) {
		String queryString ="FROM " + EncabezadoEbs.class.getName()
				+ " WHERE INVOICE_NUM = :invoiceNum"
				+ " AND VENDOR_NUM = :vendorNum";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("invoiceNum", invoiceNum);
		parameters.put("vendorNum", vendorNum);
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
}
