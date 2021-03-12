package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ComprobacionDepositoDAO;
import com.lowes.entity.ComprobacionDeposito;
import com.lowes.service.ComprobacionDepositoService;

@Service
@Transactional
public class ComprobacionDepositoServiceImpl implements ComprobacionDepositoService {
	
	@Autowired
	private ComprobacionDepositoDAO comprobacionDepositoDAO;
	
	@Override
	public Integer createComprobacionDeposito(ComprobacionDeposito comprobacionDeposito) {
		return comprobacionDepositoDAO.createComprobacionDeposito(comprobacionDeposito);
	}

	@Override
	public ComprobacionDeposito updateComprobacionDeposito(ComprobacionDeposito comprobacionDeposito) {
		return comprobacionDepositoDAO.updateComprobacionDeposito(comprobacionDeposito);
	}

	@Override
	public void deleteComprobacionDeposito(Integer idComprobacionDeposito) {
		comprobacionDepositoDAO.deleteComprobacionDeposito(idComprobacionDeposito);
	}

	@Override
	public List<ComprobacionDeposito> getAllComprobacionDeposito() {
		return comprobacionDepositoDAO.getAllComprobacionDeposito();
	}

	@Override
	public List<ComprobacionDeposito> getAllComprobacionDepositoBySolicitud(Integer idSolicitud) {
		return comprobacionDepositoDAO.getAllComprobacionDepositoBySolicitud(idSolicitud);
	}

	@Override
	public ComprobacionDeposito getComprobacionDeposito(Integer idDocumento) {
		return comprobacionDepositoDAO.getComprobacionDeposito(idDocumento);
	}

}
