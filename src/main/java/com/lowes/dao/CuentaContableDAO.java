package com.lowes.dao;

import java.util.List;

import com.lowes.entity.CuentaContable;

public interface CuentaContableDAO {
	
	public Integer createCuentaContable(CuentaContable cuentaContable);
    public CuentaContable updateCuentaContable(CuentaContable cuentaContable);
    public void deleteCuentaContable(Integer id);
    public List<CuentaContable> getAllCuentaContable();
    public CuentaContable getCuentaContable(Integer id);
    public List<CuentaContable> getAllCuentaContableOrderByNumeroCuentaConable();
	public CuentaContable getCCByNumeroCuenta(String numCuentaContable);

}