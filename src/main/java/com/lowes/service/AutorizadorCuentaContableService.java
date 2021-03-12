package com.lowes.service;

import java.util.List;

import com.lowes.entity.AutorizadorCuentaContable;

public interface AutorizadorCuentaContableService {
	public Integer createAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable);
    public AutorizadorCuentaContable updateAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable);
    public void deleteAutorizadorCuentaContable(Integer idAutorizadorCuentaContable);
    public List<AutorizadorCuentaContable> getAllAutorizadorCuentaContable();
    public AutorizadorCuentaContable getAutorizadorCuentaContable(Integer idAutorizadorCuentaContable);
    public  List<AutorizadorCuentaContable> getAutorizadorCuentaContableByCuentaContable(Integer idCuentaContable);
}
