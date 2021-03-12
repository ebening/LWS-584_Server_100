package com.lowes.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowes.dao.FacturaDesgloseDAO;
import com.lowes.entity.FacturaDesglose;
import com.lowes.service.FacturaDesgloseService;


@Service
@Transactional
public class FacturaDesgloseServiceImpl implements FacturaDesgloseService{
	 
	@Autowired
	private FacturaDesgloseDAO facturaDesgloseDAO;


	@Override
	public Integer createFacturaDesglose(FacturaDesglose facturaDesglose) {
		return facturaDesgloseDAO.createFacturaDesglose(facturaDesglose);
	}

	@Override
	public FacturaDesglose updateFacturaDesglose(FacturaDesglose facturaDesglose) {
		return facturaDesgloseDAO.updateFacturaDesglose(facturaDesglose);
	}

	@Override
	public void deleteFacturaDesglose(Integer idFacturaDesglose) {
		facturaDesgloseDAO.deleteFacturaDesglose(idFacturaDesglose);
	}

	@Override
	public List<FacturaDesglose> getAllFacturaDesglose() {
		return facturaDesgloseDAO.getAllFacturaDesglose();
	}

	@Override
	public FacturaDesglose getFactura(Integer idFacturaDesglose) {
		return facturaDesgloseDAO.getFactura(idFacturaDesglose);
	}

	@Override
	public void deleteAllByIdFactura(Integer idFactura) {
      facturaDesgloseDAO.deleteAllByIdFactura(idFactura);		
	}

	
	
	
	
}
