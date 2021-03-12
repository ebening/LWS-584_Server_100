package com.lowes.dao;

import java.util.List;

import com.lowes.entity.FormaPago;

public interface FormaPagoDAO {
	
	public Integer createFormaPago(FormaPago formaPago);
    public FormaPago updateFormaPago(FormaPago formaPago);
    public void deleteFormaPago(Integer id);
    public List<FormaPago> getAllFormaPago();
    public FormaPago getFormaPago(Integer id);

}