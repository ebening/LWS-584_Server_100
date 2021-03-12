package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.FormaPagoDAO;
import com.lowes.entity.FormaPago;
import com.lowes.util.HibernateUtil;

@Repository
public class FormaPagoDAOImpl implements FormaPagoDAO{

	public FormaPagoDAOImpl(){
		System.out.println("FormaPagoDAOImpl()");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createFormaPago(FormaPago formaPago) {
		return (Integer) hibernateUtil.create(formaPago);
	}

	@Override
	public FormaPago updateFormaPago(FormaPago formaPago) {
		return hibernateUtil.update(formaPago);
	}

	@Override
	public void deleteFormaPago(Integer id) {
		FormaPago formaPago = getFormaPago(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(formaPago);
	}

	@Override
	public List<FormaPago> getAllFormaPago() {
		return hibernateUtil.fetchAll(FormaPago.class);
	}

	@Override
	public FormaPago getFormaPago(Integer id) {
		return hibernateUtil.fetchById(id, FormaPago.class);
	}

}
