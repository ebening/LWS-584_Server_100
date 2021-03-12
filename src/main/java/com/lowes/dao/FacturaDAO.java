package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Factura;

public interface FacturaDAO {
	
	public Integer createFactura(Factura factura);
    public Factura updateFactura(Factura factura);
    public void deleteFactura(Integer idfactura);
    public List<Factura> getAllFactura();
    public Factura getFactura(Integer idFactura);
    public List<Factura> getAllFacturaBySolicitud(Integer idSolicitud);
	public List<Factura> getFacturaByUUID(String UUID);
	public List<Factura> getFacturaByFolio(String folio);
	public void deleteFactura(Factura factura);
    
}