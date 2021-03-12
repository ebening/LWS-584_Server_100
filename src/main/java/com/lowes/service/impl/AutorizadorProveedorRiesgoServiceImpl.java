package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.AutorizadorProveedorRiesgoDAO;
import com.lowes.entity.AutorizadorProveedorRiesgo;
import com.lowes.service.AutorizadorProveedorRiesgoService;

@Service
@Transactional
public class AutorizadorProveedorRiesgoServiceImpl implements AutorizadorProveedorRiesgoService {
	
	public AutorizadorProveedorRiesgoServiceImpl(){
		
	}

	@Autowired
	private AutorizadorProveedorRiesgoDAO autorizadorProveedorRiesgoDAO;
	
	
	@Override
	public Integer createAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {
		return autorizadorProveedorRiesgoDAO.createAutorizadorProveedorRiesgo(autorizadorProveedorRiesgo);
	}

	@Override
	public AutorizadorProveedorRiesgo updateAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {
		return autorizadorProveedorRiesgoDAO.updateAutorizadorProveedorRiesgo(autorizadorProveedorRiesgo);
	}

	@Override
	public void deleteAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo) {
		autorizadorProveedorRiesgoDAO.deleteAutorizadorProveedorRiesgo(idAutorizadorProveedorRiesgo);

	}

	@Override
	public List<AutorizadorProveedorRiesgo> getAllAutorizadorProveedorRiesgo() {
		return autorizadorProveedorRiesgoDAO.getAllAutorizadorProveedorRiesgo();
	}

	@Override
	public AutorizadorProveedorRiesgo getAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo) {
		return autorizadorProveedorRiesgoDAO.getAutorizadorProveedorRiesgo(idAutorizadorProveedorRiesgo);
	}

	@Override
	public List<AutorizadorProveedorRiesgo> getAutorizadorProveedorRiesgoByProveedor(Integer idProveedor) {
		return autorizadorProveedorRiesgoDAO.getAutorizadorProveedorRiesgoByProveedor(idProveedor);
	}
	
}