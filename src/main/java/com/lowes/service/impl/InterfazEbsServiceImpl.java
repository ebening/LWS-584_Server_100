package com.lowes.service.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.EncabezadoEbs;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudFechaPagoEbs;
import com.lowes.service.EncabezadoEbsService;
import com.lowes.service.InterfazEbsService;
import com.lowes.service.SolicitudFechaPagoEbsService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;

@Service
@Transactional
public class InterfazEbsServiceImpl implements InterfazEbsService{
	
	private static final Logger logger = Logger.getLogger(InterfazEbsServiceImpl.class);
	
	@Autowired
	private SolicitudFechaPagoEbsService solicitudFechaPagoEbsService;
	
	@Autowired
	private EncabezadoEbsService encabezadoEbsService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	
	@Scheduled(cron = "${cron.icm}")
	@Override
	public void actualizaSolicitudFechaPago(){
		logger.info("Interface EBS: Iniciando");
		
		EncabezadoEbs ebs = null;
		Solicitud solicitud;
		List<EncabezadoEbs> encabezadoEbs;
		String invoiceNum, vendorNum;
		
		
//		Obtener los anticipos depositados que est�n pendientes de actualizar desde EBS (estatus 0)
		List<SolicitudFechaPagoEbs> solicitudesNoActualizadas = solicitudFechaPagoEbsService.getSolicitudFechaPagoEbsNoActualizadas();
			
		if(!solicitudesNoActualizadas.isEmpty()){
			logger.info("Interface EBS info: Existen " + solicitudesNoActualizadas.size() + " solicitudes pendientes de actualizar.");
//			Por cada registro, obtener el registro de EBS por InvoiceNum&VendorNum
			for(SolicitudFechaPagoEbs solicitudNoActualizada : solicitudesNoActualizadas){
				invoiceNum= solicitudNoActualizada.getInvoiceNum();
				vendorNum= solicitudNoActualizada.getVendorNum();
				encabezadoEbs =encabezadoEbsService.getEncabezadoEbsByInvoiceNumVendorNum(invoiceNum, vendorNum);
				
				if(!encabezadoEbs.isEmpty()){
					if(encabezadoEbs.size()>1)
						logger.info("Interface EBS info: No es posible procesar el encabezado puesto que no contiene valores �nicos: " + invoiceNum + " - " + vendorNum);
					if(encabezadoEbs.size()==1){
						ebs = encabezadoEbs.get(0);
						
//						Actualizar la fecha de pago de la solicitud
						solicitud = solicitudService.getSolicitud(ebs.getSolicitudId());
						solicitud.setFechaPago(solicitudNoActualizada.getFechaPago());
						solicitud.setComprobada(Etiquetas.CERO);
						solicitud.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_PAGADA));
						solicitudService.updateSolicitud(solicitud);
						
//						Cambiar el estado a Actualizada en EBS
						solicitudNoActualizada.setActualizada(Etiquetas.UNO_S);
						solicitudFechaPagoEbsService.updateSolicitudFechaPagoEbs(solicitudNoActualizada);
						
						logger.info("Interface EBS info: Solicitud "+ solicitud.getIdSolicitud() + " actualizada: " + invoiceNum + " - " + vendorNum);
					}
				}else{
					logger.info("Interface EBS info: No se encontr� el registro en Encabezado EBS: " + invoiceNum + " - " + vendorNum);
				}			
			}
		}else
			logger.info("Interface EBS info: No se encontraron registros pendientes de actualizar.");		
		logger.info("Interface EBS: Terminado");
	}

}