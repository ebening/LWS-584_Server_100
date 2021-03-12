package com.lowes.dao;

import java.util.List;

import com.lowes.entity.KilometrajeRecorrido;

public interface KilometrajeRecorridoDAO {
	
	public Integer createKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido);
    public KilometrajeRecorrido updateKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido);
    public void deleteKilometrajeRecorrido(Integer id);
    public List<KilometrajeRecorrido> getAllKilometrajeRecorrido();
    public KilometrajeRecorrido getKilometrajeRecorrido(Integer id);

}