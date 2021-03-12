package com.lowes.service;

import java.util.List;

import com.lowes.entity.KilometrajeRecorrido;

public interface KilometrajeRecorridoService {
	
	public Integer createKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido);
    public KilometrajeRecorrido updateKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido);
    public void deleteKilometrajeRecorrido(Integer id);
    public List<KilometrajeRecorrido> getAllKilometrajeRecorrido();
    public KilometrajeRecorrido getKilometrajeRecorrido(Integer id);

}