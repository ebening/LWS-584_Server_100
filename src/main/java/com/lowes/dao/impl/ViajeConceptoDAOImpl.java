package com.lowes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ViajeConceptoDAO;
import com.lowes.entity.ViajeConcepto;
import com.lowes.util.HibernateUtil;

@Repository
public class ViajeConceptoDAOImpl implements ViajeConceptoDAO{
	
	public ViajeConceptoDAOImpl(){
		System.out.println("ViajeConceptoDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createViajeConcepto(ViajeConcepto viajeConcepto) {
		return (Integer) hibernateUtil.create(viajeConcepto);
	}

	@Override
	public ViajeConcepto updateViajeConcepto(ViajeConcepto viajeConcepto) {	
		return hibernateUtil.update(viajeConcepto);
	}

	@Override
	public void deleteViajeConcepto(Integer idViajeConcepto) {
		hibernateUtil.delete(getViajeConcepto(idViajeConcepto));
	}

	@Override
	public List<ViajeConcepto> getAllViajeConcepto() {
		List<ViajeConcepto> lstViajeConcepto = hibernateUtil.fetchAll(ViajeConcepto.class);
		ViajeConcepto otroConcepto = null;
		for(ViajeConcepto lst : lstViajeConcepto){
			lst.setIdViajeConceptoString((Character.toLowerCase(lst.getDescripcion().charAt(0)) + lst.getDescripcion().substring(1)).replaceAll("\\s",""));
			if(lst.getEsOtro() != 0){
				otroConcepto = lst;
			}
		}
		lstViajeConcepto.remove(otroConcepto);
		lstViajeConcepto.add(otroConcepto);
		return lstViajeConcepto;
		
	}
	
	@Override
	public List<ViajeConcepto> getAllViajeConceptoAnticipo() {
		//Obtiene los conceptos excepto la propina
		List<ViajeConcepto> lstViajeConcepto = hibernateUtil.fetchAll(ViajeConcepto.class);
		List<ViajeConcepto> excluye = new ArrayList<>();
		ViajeConcepto otroConcepto = null;
		for(ViajeConcepto lst : lstViajeConcepto){
			if(lst.getEsPropina() == 0 && lst.getEsTransporteAereo() == 0){
				lst.setIdViajeConceptoString((Character.toLowerCase(lst.getDescripcion().charAt(0)) + lst.getDescripcion().substring(1)).replaceAll("\\s",""));
				if(lst.getEsOtro() != 0){
					otroConcepto = lst;
				}
			}else
				excluye.add(lst);
		}
		for(ViajeConcepto exc : excluye){
			lstViajeConcepto.remove(exc);
		}
		lstViajeConcepto.remove(otroConcepto);
		lstViajeConcepto.add(otroConcepto);
		return lstViajeConcepto;
	}

	@Override
	public ViajeConcepto getViajeConcepto(Integer idViajeConcepto) {
		ViajeConcepto concepto = hibernateUtil.fetchById(idViajeConcepto, ViajeConcepto.class);
		if(concepto != null){
			concepto.setIdViajeConceptoString((Character.toLowerCase(concepto.getDescripcion().charAt(0)) + concepto.getDescripcion().substring(1)).replaceAll("\\s",""));
		}
		return concepto;
	}

}