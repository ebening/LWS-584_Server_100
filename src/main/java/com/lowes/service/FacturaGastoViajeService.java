package com.lowes.service;

import java.util.List;

import com.lowes.entity.FacturaGastoViaje;

public interface FacturaGastoViajeService {
	public Integer createFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje);
    public FacturaGastoViaje updateFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje);
    public void deleteFacturaGastoViaje(Integer idfacturaGastoViaje);
    public List<FacturaGastoViaje> getAllFacturaGastoViaje();
    public FacturaGastoViaje getFacturaGastoViaje(Integer idFacturaGastoViaje);
    public FacturaGastoViaje getFacturaGastoViajeByIdFactura(Integer idFactura);
	public void deleteFacturaGastoViaje(FacturaGastoViaje facElim);
	public void deleteAllFacturaGastoViajeByIdFactura(Integer idFactura);
}