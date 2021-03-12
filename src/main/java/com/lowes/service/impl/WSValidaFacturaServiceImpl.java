package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.service.WSValidaFacturaService;
import com.lowes.util.WSFacturaMessageResponse;
import com.lowes.util.WSValidaFacturaClient;

@Service
@Transactional
public class WSValidaFacturaServiceImpl implements WSValidaFacturaService {

	@Autowired
	private WSValidaFacturaClient wsValidaFacturaClient; 
	
	public WSValidaFacturaServiceImpl() {

	}
	
	@Override
	public List<WSFacturaMessageResponse> validaFactura(byte[] archivo,
			String nombreArchivo, String rfcEmisor, String rfcReceptor,
			String importeTotal, String token) {
		return wsValidaFacturaClient.validarFactura(archivo, nombreArchivo, rfcEmisor, rfcReceptor, importeTotal, token);
	}

}
