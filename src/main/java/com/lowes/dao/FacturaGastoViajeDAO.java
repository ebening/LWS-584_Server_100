package com.lowes.dao;

import java.util.List;

import com.lowes.entity.FacturaGastoViaje;

public interface FacturaGastoViajeDAO {
	
	public Integer createFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje);
    public FacturaGastoViaje updateFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje);
    public void deleteFacturaGastoViaje(Integer idFacturaGastoViaje);
    public List<FacturaGastoViaje> getAllFacturaGastoViaje();
    public FacturaGastoViaje getFacturaGastoViaje(Integer idFacturaGastoViaje);
    public FacturaGastoViaje getFacturaGastoViajeByIdFactura(Integer idFactura);
	public void deleteFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje);
	public void deleteAllFacturaGastoViajeByIdFactura(Integer idFactura);
}