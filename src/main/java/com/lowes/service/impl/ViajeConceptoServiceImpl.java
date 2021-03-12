package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ViajeConceptoDAO;
import com.lowes.entity.ViajeConcepto;
import com.lowes.service.ViajeConceptoService;

@Service
@Transactional
public class ViajeConceptoServiceImpl implements ViajeConceptoService{
	
	public ViajeConceptoServiceImpl(){
		System.out.println("ViajeConceptoServiceImpl()");
	}

	@Autowired
	private ViajeConceptoDAO viajeConceptoDAO;
	
	@Override
	public Integer createViajeConcepto(ViajeConcepto viajeConcepto) {
		return viajeConceptoDAO.createViajeConcepto(viajeConcepto);
	}

	@Override
	public ViajeConcepto updateViajeConcepto(ViajeConcepto viajeConcepto) {
		return viajeConceptoDAO.updateViajeConcepto(viajeConcepto);
	}

	@Override
	public void deleteViajeConcepto(Integer idViajeConcepto) {
		viajeConceptoDAO.deleteViajeConcepto(idViajeConcepto);
	}

	@Override
	public List<ViajeConcepto> getAllViajeConcepto() {
		return viajeConceptoDAO.getAllViajeConcepto();
	}
	
	@Override
	public List<ViajeConcepto> getAllViajeConceptoAnticipo() {
		return viajeConceptoDAO.getAllViajeConceptoAnticipo();
	}

	@Override
	public ViajeConcepto getViajeConcepto(Integer idViajeConcepto) {
		return viajeConceptoDAO.getViajeConcepto(idViajeConcepto);
	}

}