package com.lowes.service;

import java.util.List;

import com.lowes.entity.TipoDocumento;

public interface TipoDocumentoService {
	
    public List<TipoDocumento> getAllTipoDocumento();
    public TipoDocumento getTipoDocumento(Integer idTipoDocumento);

}
