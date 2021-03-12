package com.lowes.service;

import java.util.List;

import com.lowes.entity.Aid;

public interface AidService {
	
	public Integer createAid(Aid aid);
    public Aid updateAid(Aid aid);
    public void deleteAid(Integer id);
    public List<Aid> getAllAid();
    public Aid getAid(Integer id);

}