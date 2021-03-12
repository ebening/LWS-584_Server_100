package com.lowes.dao;

import java.util.List;

import com.lowes.entity.FacturaArchivo;

public interface FacturaArchivoDAO {

	public Integer createFacturaArchivo(FacturaArchivo facturaArchivo);
    public FacturaArchivo updateFacturaArchivo(FacturaArchivo facturaArchivo);
    public void deleteFacturaArchivo(Integer idFacturaArchivo);
    public List<FacturaArchivo> getAllFacturaArchivo();
    public FacturaArchivo getFacturaArchivo(Integer idFacturaArchivo);
    public List<FacturaArchivo> getAllFacturaArchivoByFacturaTipoDocumento(Integer idFactura, Integer idTipoDocumento);
	public List<FacturaArchivo> getAllFacturaArchivoByIdFactura(Integer idFactura);
	public void deleteFacturaArchivo(FacturaArchivo facturaArchivo);
	public void deleteAllFacturaArchivoByIdFactura(Integer idFactura);
	
}