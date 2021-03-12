package com.lowes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.BitacoraDAO;
import com.lowes.entity.Bitacora;
import com.lowes.service.BitacoraService;

@Service
@Transactional
public class BitacoraServiceImpl implements BitacoraService {

	@Autowired 
	private BitacoraDAO bitacoraDAO;
	
	@Override
	public Integer registrarBitacora(Bitacora bitacora) {
		return bitacoraDAO.registrarBitacora(bitacora);
	}

}
