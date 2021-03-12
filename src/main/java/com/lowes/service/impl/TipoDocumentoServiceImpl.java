package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoDocumentoDAO;
import com.lowes.entity.TipoDocumento;
import com.lowes.service.TipoDocumentoService;


@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	
	@Autowired
	private TipoDocumentoDAO tipoDocumentoDAO;

	@Override
	public List<TipoDocumento> getAllTipoDocumento() {
		return tipoDocumentoDAO.getAllTipoDocumento();
	}

	@Override
	public TipoDocumento getTipoDocumento(Integer idTipoDocumento) {
		return tipoDocumentoDAO.getTipoDocumento(idTipoDocumento);
	}
}
