package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.CuentaContableDAO;
import com.lowes.entity.CuentaContable;
import com.lowes.service.CuentaContableService;

@Service
@Transactional
public class CuentaContableServiceImpl implements CuentaContableService{
	
	public CuentaContableServiceImpl(){
		System.out.println("CuentaContableServiceImpl()");
	}
	
	@Autowired
	private CuentaContableDAO cuentaContableDAO;

	@Override
	public Integer createCuentaContable(CuentaContable cuentaContable) {
		return cuentaContableDAO.createCuentaContable(cuentaContable);
	}

	@Override
	public CuentaContable updateCuentaContable(CuentaContable cuentaContable) {
		return cuentaContableDAO.updateCuentaContable(cuentaContable);
	}

	@Override
	public void deleteCuentaContable(Integer id) {
		cuentaContableDAO.deleteCuentaContable(id);
	}

	@Override
	public List<CuentaContable> getAllCuentaContable() {
		return cuentaContableDAO.getAllCuentaContable();
	}

	@Override
	public CuentaContable getCuentaContable(Integer id) {
		return cuentaContableDAO.getCuentaContable(id);
	}

	@Override
	public List<CuentaContable> getAllCuentaContableOrderByNumeroCuentaConable() {
		return cuentaContableDAO.getAllCuentaContableOrderByNumeroCuentaConable();
	}
	
	@Override
	public CuentaContable getCCByNumeroCuenta(String numCuentaContable) {
		return cuentaContableDAO.getCCByNumeroCuenta(numCuentaContable);
	}

}