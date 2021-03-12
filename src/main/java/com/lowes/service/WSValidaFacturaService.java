package com.lowes.service;

import java.util.List;

import com.lowes.util.WSFacturaMessageResponse;

public interface WSValidaFacturaService {

	public abstract List<WSFacturaMessageResponse> validaFactura(byte [] archivo, String nombreArchivo, String rfcEmisor, String rfcReceptor, String importeTotal, String token);
	
}
