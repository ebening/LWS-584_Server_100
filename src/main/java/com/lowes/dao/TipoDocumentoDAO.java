package com.lowes.dao;

import java.util.List;

import com.lowes.entity.TipoDocumento;

public interface TipoDocumentoDAO {
    public List<TipoDocumento> getAllTipoDocumento();
    public TipoDocumento getTipoDocumento(Integer idTipoDocumento);
}
