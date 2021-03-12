package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoDocumentoDAO;
import com.lowes.entity.TipoDocumento;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoDocumentoDAOImpl implements TipoDocumentoDAO {
	
	public TipoDocumentoDAOImpl(){
		System.out.println("TipoCriterioDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public List<TipoDocumento> getAllTipoDocumento() {
		return hibernateUtil.fetchAll(TipoDocumento.class);
	}

	@Override
	public TipoDocumento getTipoDocumento(Integer idTipoDocumento) {
		return hibernateUtil.fetchById(idTipoDocumento, TipoDocumento.class);
	}

}
