package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.AutorizadorCuentaContableDAO;
import com.lowes.entity.AutorizadorCuentaContable;
import com.lowes.service.AutorizadorCuentaContableService;

@Service
@Transactional
public class AutorizadorCuentaContableServiceImpl implements AutorizadorCuentaContableService {
	
	public AutorizadorCuentaContableServiceImpl(){
		
	}

	@Autowired
	private AutorizadorCuentaContableDAO autorizadorCuentaContableDAO;
	
	
	@Override
	public Integer createAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable) {
		return autorizadorCuentaContableDAO.createAutorizadorCuentaContable(autorizadorCuentaContable);
	}

	@Override
	public AutorizadorCuentaContable updateAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable) {
		return autorizadorCuentaContableDAO.updateAutorizadorCuentaContable(autorizadorCuentaContable);
	}

	@Override
	public void deleteAutorizadorCuentaContable(Integer idAutorizadorCuentaContable) {
		autorizadorCuentaContableDAO.deleteAutorizadorCuentaContable(idAutorizadorCuentaContable);

	}

	@Override
	public List<AutorizadorCuentaContable> getAllAutorizadorCuentaContable() {
		return autorizadorCuentaContableDAO.getAllAutorizadorCuentaContable();
	}

	@Override
	public AutorizadorCuentaContable getAutorizadorCuentaContable(Integer idAutorizadorCuentaContable) {
		return autorizadorCuentaContableDAO.getAutorizadorCuentaContable(idAutorizadorCuentaContable);
	}

	@Override
	public List<AutorizadorCuentaContable> getAutorizadorCuentaContableByCuentaContable(Integer idCuentaContable) {
		return autorizadorCuentaContableDAO.getAutorizadorCuentaContableByCuentaContable(idCuentaContable);
	}

}
