package com.lowes.service;

import java.util.List;

import com.lowes.entity.FormaPago;

public interface FormaPagoService {
	
	public Integer createFormaPago(FormaPago formaPago);
    public FormaPago updateFormaPago(FormaPago formaPago);
    public void deleteFormaPago(Integer id);
    public List<FormaPago> getAllFormaPago();
    public FormaPago getFormaPago(Integer id);

}