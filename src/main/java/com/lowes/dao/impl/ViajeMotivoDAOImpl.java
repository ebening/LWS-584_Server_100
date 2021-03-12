package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ViajeMotivoDAO;
import com.lowes.entity.ViajeMotivo;
import com.lowes.util.HibernateUtil;

@Repository
public class ViajeMotivoDAOImpl implements ViajeMotivoDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	public ViajeMotivoDAOImpl(){
		System.out.println("ViajeMotivoDAOImpl()");
	}

	@Override
	public Integer createViajeMotivo(ViajeMotivo viajeMotivo) {
		return (Integer) hibernateUtil.create(viajeMotivo);
	}

	@Override
	public ViajeMotivo updateViajeMotivo(ViajeMotivo viajeMotivo) {
		return hibernateUtil.update(viajeMotivo);
	}

	@Override
	public void deleteViajeMotivo(Integer id) {
		hibernateUtil.delete(getViajeMotivo(id));
	}

	@Override
	public List<ViajeMotivo> getAllViajeMotivo() {
		List<ViajeMotivo> lstViajeMotivo = hibernateUtil.fetchAll(ViajeMotivo.class);
		ViajeMotivo otroMotivo = null;
		for(ViajeMotivo vm : lstViajeMotivo){
			if(vm.getEsOtro() != null){
				otroMotivo = vm;
				lstViajeMotivo.remove(vm);
				break;
			}else{
				vm.setEsOtro((short) 0);
			}
		}
		if(otroMotivo != null)
			lstViajeMotivo.add(otroMotivo);
		return lstViajeMotivo;
	}

	@Override
	public ViajeMotivo getViajeMotivo(Integer id) {
		return hibernateUtil.fetchById(id,ViajeMotivo.class);
	}

}