/**
 * 
 */
package com.lowes.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.NivelAutorizaDAO;
import com.lowes.entity.NivelAutoriza;
import com.lowes.service.NivelAutorizaService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class NivelAutorizaServiceImpl implements NivelAutorizaService {

	public NivelAutorizaServiceImpl() {
		System.out.println("NivelAutorizaServiceImplConstruct()");
	}

	@Autowired
	private NivelAutorizaDAO nivelAutorizaDAO;

	@Override
	public Integer createNivelAutoriza(NivelAutoriza nivelAutoriza) {
		return nivelAutorizaDAO.createNivelAutoriza(nivelAutoriza);
	}

	@Override
	public NivelAutoriza updateNivelAutoriza(NivelAutoriza nivelAutoriza) {
		return nivelAutorizaDAO.updateNivelAutoriza(nivelAutoriza);
	}

	@Override
	public void deleteNivelAutoriza(int idNivelAutoriza) {
		nivelAutorizaDAO.deleteNivelAutoriza(idNivelAutoriza);
	}

	@Override
	public List<NivelAutoriza> getAllNivelAutoriza() {
		return nivelAutorizaDAO.getAllNivelAutoriza();
	}

	@Override
	public NivelAutoriza getNivelAutoriza(int idNivelAutoriza) {
		return nivelAutorizaDAO.getNivelAutoriza(idNivelAutoriza);
	}

	@Override
	public List<NivelAutoriza> getNivelesAutorizaByMonto(BigDecimal monto, Integer idMoneda) {
		return nivelAutorizaDAO.getNivelesAutorizaByMonto(monto, idMoneda);
	}
}
