package com.lowes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.BitacoraDAO;
import com.lowes.entity.Bitacora;
import com.lowes.util.HibernateUtil;


@Repository
public class BitacoraDAOImpl implements BitacoraDAO {

	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer registrarBitacora(Bitacora bitacora) {
		return (Integer) hibernateUtil.create(bitacora);
	}

}
