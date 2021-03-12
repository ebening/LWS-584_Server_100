package com.lowes.service;

import java.util.List;

import com.lowes.entity.ViajeConcepto;

public interface ViajeConceptoService {
	
	public Integer createViajeConcepto(ViajeConcepto viajeConcepto);
    public ViajeConcepto updateViajeConcepto(ViajeConcepto viajeConcepto);
    public void deleteViajeConcepto(Integer id);
    public List<ViajeConcepto> getAllViajeConcepto();
    public List<ViajeConcepto> getAllViajeConceptoAnticipo();
    public ViajeConcepto getViajeConcepto(Integer id);	

}