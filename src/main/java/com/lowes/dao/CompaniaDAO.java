package com.lowes.dao;

import java.util.List;
import com.lowes.entity.Compania;

public interface CompaniaDAO {
	
	public Integer createCompania(Compania compania);
    public Compania updateCompania(Compania compania);
    public void deleteCompania(Integer id);
    public List<Compania> getAllCompania();
    public Compania getCompania(Integer id);
	public String getTokenCompania(String rfcReceptor);	

}