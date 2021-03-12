package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.FormaPagoDAO;
import com.lowes.entity.FormaPago;
import com.lowes.service.FormaPagoService;

@Service
@Transactional
public class FormaPagoServiceImpl implements FormaPagoService{
	
	FormaPagoServiceImpl(){
		System.out.println("FormaPagoServiceImpl()");
	}
	
	@Autowired
	private FormaPagoDAO formaPagoDAO;
	
	@Override
	public Integer createFormaPago(FormaPago formaPago) {
		return formaPagoDAO.createFormaPago(formaPago);
	}

	@Override
	public FormaPago updateFormaPago(FormaPago formaPago) {
		return formaPagoDAO.updateFormaPago(formaPago);
	}

	@Override
	public void deleteFormaPago(Integer id) {
		formaPagoDAO.deleteFormaPago(id);
	}

	@Override
	public List<FormaPago> getAllFormaPago() {
		return formaPagoDAO.getAllFormaPago();
	}

	@Override
	public FormaPago getFormaPago(Integer id) {
		return formaPagoDAO.getFormaPago(id);
	}

}