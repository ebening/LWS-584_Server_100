package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Aid;

public interface AidDAO {
	
	public Integer createAid(Aid aid);
    public Aid updateAid(Aid aid);
    public void deleteAid(Integer id);
    public List<Aid> getAllAid();
    public Aid getAid(Integer id);

}