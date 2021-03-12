package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ViajeDestinoDAO;
import com.lowes.entity.ViajeDestino;
import com.lowes.util.HibernateUtil;

@Repository
public class ViajeDestinoDAOImpl implements ViajeDestinoDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;

	public ViajeDestinoDAOImpl(){
		System.out.println("ViajeDestinoDAOImpl()");
	}
	
	@Override
	public Integer createViajeDestino(ViajeDestino viajeDestino) {
		return (Integer) hibernateUtil.create(viajeDestino);
	}

	@Override
	public ViajeDestino updateViajeDestino(ViajeDestino viajeDestino) {
		return hibernateUtil.update(viajeDestino);
	}

	@Override
	public void deleteViajeDestino(Integer id) {
		hibernateUtil.delete(getViajeDestino(id));
	}

	@Override
	public List<ViajeDestino> getAllViajeDestino() {
		List<ViajeDestino> lstViajeDestino = hibernateUtil.fetchAll(ViajeDestino.class);
		ViajeDestino otroDestino = null;
		for(ViajeDestino vd : lstViajeDestino){
			if(vd.getEsOtro() != null){
				otroDestino = vd;
				lstViajeDestino.remove(vd);
				break;
			}else{
				vd.setEsOtro((short) 0);
			}
		}
		if(otroDestino != null)
			lstViajeDestino.add(otroDestino);
		return lstViajeDestino;
	}

	@Override
	public ViajeDestino getViajeDestino(Integer id) {
		return hibernateUtil.fetchById(id,ViajeDestino.class);
	}

}