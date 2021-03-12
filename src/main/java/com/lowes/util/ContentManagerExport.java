/**
 * 
 */
package com.lowes.util;

import java.util.ArrayList;

import org.jboss.logging.Logger;

/**
 * @author Adinfi
 *
 */
import com.ibm.mm.sdk.common.DKAttrDefICM;
import com.ibm.mm.sdk.common.DKConstant;
import com.ibm.mm.sdk.common.DKConstantICM;
import com.ibm.mm.sdk.common.DKDDO;
import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.common.DKLobICM;
import com.ibm.mm.sdk.common.DKNVPair;
import com.ibm.mm.sdk.common.DKParts;
import com.ibm.mm.sdk.common.DKRetrieveOptionsICM;
import com.ibm.mm.sdk.common.DKSequentialCollection;
import com.ibm.mm.sdk.common.DKTextICM;
import com.ibm.mm.sdk.common.DKUsageError;
import com.ibm.mm.sdk.common.dkIterator;
import com.ibm.mm.sdk.common.dkResultSetCursor;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.lowes.dto.Atributo;
import com.lowes.dto.CMDeposito;
import com.lowes.dto.CMDocumento;
import com.lowes.dto.CMDocumentoSoporte;
import com.lowes.dto.Documento;
import com.lowes.entity.Solicitud;

public class ContentManagerExport {

	private static final Logger logger = Logger.getLogger(ContentManagerExport.class);

	private static final int CONNECTION_PERCENT = 20;
	private CMConnection connection = null;

	public ContentManagerExport() {
		connection = new CMConnection();
	}

	public synchronized int connectToServer() {
		int percent = -1;
		boolean connect = connection.connectCM();
		if (connect == true) {
			percent = CONNECTION_PERCENT;
		}

		return percent;
	}

	public synchronized void disconnect() {
		if (connection != null) {
			connection.disconectCM();
		}
	}

	public void fillAttributesDKFolder(Solicitud sol, DKDatastoreICM connCM) {
		String itemType = PropertyUtil.getProperty("cm.itemType.1");
		try {
			ArrayList<Atributo> list = null;
			try {
				list = getAtributosListByItemType(itemType, connCM);
			} catch (Exception ex) {
				logger.info(ex);
			}
			if (list == null) {
				return;
			}
			DKDDO ddoFolder = connCM.createDDO(itemType, DKConstant.DK_CM_FOLDER);
			for (Atributo a : list) {
				if (a.getName().equals("LWS_ID_SOLICITUD") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_ID_SOLICITUD"),
							String.valueOf(sol.getIdSolicitud()));
				} else if (a.getName().equals("LWS_TIPO_SOLICITUD_DESCRIPCION") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_TIPO_SOLICITUD_DESCRIPCION"),
							String.valueOf(sol.getTipoSolicitud().getDescripcion()));
				} else if (a.getName().equals("LWS_SOLICITANTE_NUMERO_EMPLEADO") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_SOLICITANTE_NUMERO_EMPLEADO"),
							String.valueOf(sol.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado()));
				} else if (a.getName().equals("LWS_LOCACION_DESCRIPCION") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_LOCACION_DESCRIPCION"),
							(sol.getLocacion() != null) ? String.valueOf(sol.getLocacion().getNumeroDescripcionLocacion()) : "N/A");
				} else if (a.getName().equals("LWS_COMPANIA_DESCRIPCION") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_COMPANIA_DESCRIPCION"),
							String.valueOf(sol.getCompania().getDescripcion()));
				} else if (a.getName().equals("LWS_ESTADO_SOLICITUD") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_ESTADO_SOLICITUD"),
							String.valueOf(sol.getEstadoSolicitud().getEstadoSolicitud()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_MONTO_TOTAL"), sol.getMontoTotal());
				} else if (a.getName().equals("LWS_MONTO_TOTAL2") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_MONTO_TOTAL2"), sol.getMontoTotal());
				} else if (a.getName().equals("LWS_MONEDA_DESCRIPCION_CORTA") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_MONEDA_DESCRIPCION_CORTA"),
							String.valueOf(sol.getMoneda().getDescripcionCorta()));
				}

				else if (a.getName().equals("LWS_CREACION_FECHA") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_CREACION_FECHA"),
							new java.sql.Date(sol.getCreacionFecha().getTime()));
				} else if (a.getName().equals("LWS_CREACION_FECHA2") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_CREACION_FECHA2"),
							new java.sql.Date(sol.getCreacionFecha().getTime()));
				}

				else if (a.getName().equals("LWS_FORMA_PAGO") == true) {
					ddoFolder.setData((short) ddoFolder.dataId("LWS_FORMA_PAGO"),
							String.valueOf(sol.getFormaPago().getDescripcion()));
				} else {
					logger.info("Tipo no soportado en Atributo de CM = " + a.getName());
				}
			}
			ddoFolder.add();
		} catch (Exception ex) {
			logger.info(ex);
		}
	}

	private boolean existsFolder(DKDatastoreICM dsICM, int idSolicitud) {
		boolean exists = false;
		dkResultSetCursor cursor = null;
		try {

			String query = "/LWS_SOLICITUD[@LWS_ID_SOLICITUD=\"" + idSolicitud + "\"]";
			// Always Specify Retrieve Options
			DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(dsICM);
			dkRetrieveOptions.baseAttributes(true);

			// Specify Search / Query Options
			DKNVPair options[] = new DKNVPair[3];
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1"); // No
																				// Maximum
																				// (Default)
																				// //
																				// Specify
																				// max
																				// using
																				// a
																				// string
																				// value.
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, dkRetrieveOptions); // Always
																							// specify
																							// desired
																							// Retrieve
																							// Options.
			options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null); // Must
																		// mark
																		// the
																		// end
																		// of
																		// the
																		// NVPair

			cursor = dsICM.execute(query, DKConstantICM.DK_CM_XQPE_QL_TYPE, options);
			exists = cursor.fetchNext() != null;
		} catch (Exception ex) {
			logger.info(ex);
		} finally {
			if (cursor != null) {
				try {
					cursor.destroy();
				} catch (Exception ex) {
					logger.info(ex);
				}
			}
		}
		return exists;
	}

	public synchronized String importDocument(CMDocumento d) {
		if (d == null) {
			logger.info("Profile nulo");
			return null;
		}
		DKDatastoreICM dataStore = null;
		try {
			dataStore = connection.getDataStoreCM();
		} catch (Exception e) {
			logger.info("Error en conexion a CM");
			return null;
		}
		Documento doc = new Documento();
		doc.setUrl(d.getRuta());
		String itemType = PropertyUtil.getProperty("cm.itemType.2");
		doc.setNombre(itemType);

		ArrayList<Atributo> lista = null;
		try {
			lista = getAtributosListByItemType(itemType, dataStore);
		} catch (Exception ex) {
			logger.info(ex);
			return null;
		}

		lista = fillAttributes(lista, d);
		try {
			doc.setAtributos(lista);
		} catch (Exception e) {
			logger.info("Error al agregar la lista de atributos al documento CM");
			return null;
		}
		DKDDO documento = importDocument(doc, dataStore, itemType, d);
		if (documento == null) {
			logger.info("Error al importar documento CM");
			return null;
		}
		return documento.getPidObject().pidString();
	}

	public synchronized String importDocument(CMDocumentoSoporte ds) {
		if (ds == null) {
			logger.info("Profile nulo");
			return null;
		}
		DKDatastoreICM dataStore = null;
		try {
			dataStore = connection.getDataStoreCM();
		} catch (Exception e) {
			logger.info("Error en conexion a CM");
			return null;
		}
		Documento doc = new Documento();
		doc.setUrl(ds.getRuta());
		String itemType = PropertyUtil.getProperty("cm.itemType.3");
		doc.setNombre(itemType);

		ArrayList<Atributo> lista = null;
		try {
			lista = getAtributosListByItemType(itemType, dataStore);
		} catch (Exception ex) {
			logger.info(ex);
			return null;
		}

		lista = fillAttributes(lista, ds);
		try {
			doc.setAtributos(lista);
		} catch (Exception e) {
			logger.info("Error al agregar la lista de atributos al documento CM");
			return null;
		}
		DKDDO documento = importDocument(doc, dataStore, itemType, ds);
		if (documento == null) {
			logger.info("Error al importar documento CM");
			return null;
		}
		return documento.getPidObject().pidString();
	}
	
	public synchronized String importDocument(CMDeposito ddep) {
		if (ddep == null) {
			logger.info("Profile nulo");
			return null;
		}
		DKDatastoreICM dataStore = null;
		try {
			dataStore = connection.getDataStoreCM();
		} catch (Exception e) {
			logger.info("Error en conexion a CM");
			return null;
		}
		Documento doc = new Documento();
		doc.setUrl(ddep.getRuta());
		String itemType = PropertyUtil.getProperty("cm.itemType.4");
		doc.setNombre(itemType);

		ArrayList<Atributo> lista = null;
		try {
			lista = getAtributosListByItemType(itemType, dataStore);
		} catch (Exception ex) {
			logger.info(ex);
			return null;
		}

		lista = fillAttributes(lista, ddep);
		try {
			doc.setAtributos(lista);
		} catch (Exception e) {
			logger.info("Error al agregar la lista de atributos al documento CM");
			return null;
		}
		DKDDO documento = importDocument(doc, dataStore, itemType, ddep);
		if (documento == null) {
			logger.info("Error al importar documento CM");
			return null;
		}
		return documento.getPidObject().pidString();
	}

	public synchronized ArrayList<Atributo> fillAttributes(ArrayList<Atributo> list, Solicitud sol) {
		try {
			for (Atributo a : list) {
				if (a.getName().equals("LWS_ID_SOLICITUD") == true) {
					a.setValor(String.valueOf(sol.getIdSolicitud()));
				} else if (a.getName().equals("LWS_TIPO_SOLICITUD_DESCRIPCION") == true) {
					a.setValor(String.valueOf(sol.getTipoSolicitud().getDescripcion()));
				} else if (a.getName().equals("LWS_SOLICITANTE_NUMERO_EMPLEADO") == true) {
					a.setValor(String.valueOf(sol.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado()));
				} else if (a.getName().equals("LWS_LOCACION_DESCRIPCION") == true) {
					a.setValor(String.valueOf(sol.getLocacion().getNumeroDescripcionLocacion()));
				} else if (a.getName().equals("LWS_COMPANIA_DESCRIPCION") == true) {
					a.setValor(String.valueOf(sol.getCompania().getDescripcion()));
				} else if (a.getName().equals("LWS_ESTADO_SOLICITUD") == true) {
					a.setValor(String.valueOf(sol.getEstadoSolicitud()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL") == true) {
					a.setValor(String.valueOf(sol.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL2") == true) {
					a.setValor(String.valueOf(sol.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONEDA_DESCRIPCION_CORTA") == true) {
					a.setValor(String.valueOf(sol.getMoneda().getDescripcionCorta()));
				} else if (a.getName().equals("LWS_CREACION_FECHA") == true) {
					a.setValor(String.valueOf(sol.getCreacionFecha()));
				} else if (a.getName().equals("LWS_CREACION_FECHA2") == true) {
					a.setValor(String.valueOf(sol.getCreacionFecha()));
				} else if (a.getName().equals("LWS_FORMA_PAGO") == true) {
					a.setValor(String.valueOf(sol.getFormaPago()));
				} else {
					logger.info("Tipo no soportado en Atributo de CM = " + a.getName());
				}
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
		return list;
	}

	public synchronized ArrayList<Atributo> fillAttributes(ArrayList<Atributo> list, CMDocumentoSoporte ds) {
		try {
			for (Atributo a : list) {
				if (a.getName().equals("LWS_ID_SOLICITUD") == true) {
					a.setValor(String.valueOf(ds.getIdSolicitud()));
				} else if (a.getName().equals("LWS_TIPO_DOCUMENTO") == true) {
					a.setValor(String.valueOf(ds.getTipoDocumento()));
				} else if (a.getName().equals("LWS_TIPO_SOLICITUD_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ds.getTipoSolDescripcion()));
				} else if (a.getName().equals("LWS_SOLICITANTE_NUMERO_EMPLEADO") == true) {
					a.setValor(String.valueOf(ds.getNumEmpleado()));
				} else if (a.getName().equals("LWS_LOCACION_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ds.getLocacionDesc()));
				} else if (a.getName().equals("LWS_COMPANIA_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ds.getCompaniaDesc()));
				} else if (a.getName().equals("LWS_ID_DOCUMENTO") == true) {
					a.setValor(String.valueOf(ds.getIdDocumento()));
				} else {
					logger.info("Tipo no soportado en Atributo de CM = " + a.getName());
				}
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
		return list;
	}

	public synchronized ArrayList<Atributo> fillAttributes(ArrayList<Atributo> list, CMDocumento d) {
		try {
			for (Atributo a : list) {
				if (a.getName().equals("LWS_ID_SOLICITUD") == true) {
					a.setValor(String.valueOf(d.getIdSolicitud()));
				} else if (a.getName().equals("LWS_FACTURA") == true) {
					a.setValor(String.valueOf(d.getIdFactura()));
				} else if (a.getName().equals("LWS_COMPANIA_DESCRIPCION") == true) {
					a.setValor(String.valueOf(d.getCompDescripcion()));
				} else if (a.getName().equals("LWS_SERIE") == true) {
					a.setValor(String.valueOf(d.getSerie()));
				} else if (a.getName().equals("LWS_TIPO_SOLICITUD_DESCRIPCION") == true) {
					a.setValor(String.valueOf(d.getTipoSolDescripcion()));
				} else if (a.getName().equals("LWS_TIPO_FACTURA") == true) {
					a.setValor(String.valueOf(d.getTipoFactura()));
				} else if (a.getName().equals("LWS_TIPO_DOCUMENTO") == true) {
					a.setValor(String.valueOf(d.getTipoDocumento()));
				} else if (a.getName().equals("LWS_DESCRIPCION_PROVEEDOR") == true) {
					a.setValor(String.valueOf(d.getDescProveedor()));
				} else if (a.getName().equals("LWS_RFC_PROVEEDOR") == true) {
					a.setValor(String.valueOf(d.getRfcProveedor()));
				} else if (a.getName().equals("LWS_NUMERO_PROVEEDOR") == true) {
					a.setValor(String.valueOf(d.getNumProveedor()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL") == true) {
					a.setValor(String.valueOf(d.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL2") == true) {
					a.setValor(String.valueOf(d.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONEDA_DESCRIPCION_CORTA") == true) {
					a.setValor(String.valueOf(d.getMonDescCorta()));
				} else if (a.getName().equals("LWS_FECHA_FACTURA") == true) {
					a.setValor(String.valueOf(d.getFechaFactura()));
				} else if (a.getName().equals("LWS_FECHA_FACTURA2") == true) {
					a.setValor(String.valueOf(d.getFechaFactura()));
				} else if (a.getName().equals("LWS_FECHA_PAGO_FACTURA") == true) {
					a.setValor(String.valueOf(d.getFechaPagoFactura()));
				} else if (a.getName().equals("LWS_FECHA_PAGO_FACTURA2") == true) {
					a.setValor(String.valueOf(d.getFechaPagoFactura()));
				} else if (a.getName().equals("LWS_PAR") == true) {
					a.setValor(String.valueOf(d.getPar()));
				} else if (a.getName().equals("LWS_ID_DOCUMENTO") == true) {
					a.setValor(String.valueOf(d.getIdDocumento()));
				} else {
					logger.info("Tipo no soportado en Atributo de CM = " + a.getName());
				}
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
		return list;
	}
	
	public synchronized ArrayList<Atributo> fillAttributes(ArrayList<Atributo> list, CMDeposito ddep) {
		try {
			for (Atributo a : list) {
				if (a.getName().equals("LWS_ID_SOLICITUD") == true) {
					a.setValor(String.valueOf(ddep.getIdSolicitud()));
				} else if (a.getName().equals("LWS_TIPO_DOCUMENTO") == true) {
					a.setValor(String.valueOf(ddep.getTipoDocumento()));
				} else if (a.getName().equals("LWS_TIPO_SOLICITUD_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ddep.getTipoSolDescripcion()));
				} else if (a.getName().equals("LWS_SOLICITANTE_NUMERO_EMPLEADO") == true) {
					a.setValor(String.valueOf(ddep.getSolicitanteNumEmpleado()));
				} else if (a.getName().equals("LWS_LOCACION_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ddep.getLocacionDesc()));
				} else if (a.getName().equals("LWS_COMPANIA_DESCRIPCION") == true) {
					a.setValor(String.valueOf(ddep.getCompaniaDesc()));
				} else if (a.getName().equals("LWS_DESCRIPCION_PROVEEDOR") == true) {
					a.setValor(String.valueOf(ddep.getDescProveedor()));
				} else if (a.getName().equals("LWS_RFC_PROVEEDOR") == true) {
					a.setValor(String.valueOf(ddep.getRfcProveedor()));
				} else if (a.getName().equals("LWS_NUMERO_PROVEEDOR") == true) {
					a.setValor(String.valueOf(ddep.getNumProveedor()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL") == true) {
					a.setValor(String.valueOf(ddep.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONTO_TOTAL2") == true) {
					a.setValor(String.valueOf(ddep.getMontoTotal()));
				} else if (a.getName().equals("LWS_MONEDA_DESCRIPCION_CORTA") == true) {
					a.setValor(String.valueOf(ddep.getMonDescCorta()));
				} else if (a.getName().equals("LWS_CREACION_FECHA") == true) {
					a.setValor(String.valueOf(ddep.getCreacionFecha()));
				} else if (a.getName().equals("LWS_CREACION_FECHA2") == true) {
					a.setValor(String.valueOf(ddep.getCreacionFecha()));
				}
				else if (a.getName().equals("LWS_ID_DOCUMENTO") == true) {
					String compDesc = String.valueOf(ddep.getIdDocumento());
					a.setValor(compDesc);
				} else {
					logger.info("Tipo no soportado en Atributo de CM = " + a.getName());
				}
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
		return list;
	}

	public synchronized ArrayList<Atributo> getAtributosListByItemType(String itemType, DKDatastoreICM connCM)
			throws DKException, Exception {
		ArrayList<Atributo> atributos = new ArrayList<Atributo>();

		DKSequentialCollection attrColl = (DKSequentialCollection) connCM.listEntityAttrs(itemType);

		if (attrColl != null) {
			dkIterator iter = attrColl.createIterator();

			while (iter.more()) {
				DKAttrDefICM attr = (DKAttrDefICM) iter.next();
				Atributo atributo = new Atributo();
				atributo.setName(attr.getName());
				atributo.setDescription(attr.getDescription());
				atributo.setTipo(attr.getType());
				atributo.setLonguitud(attr.getMax());
				atributo.setRequerido(!attr.isNullable());
				atributo.setRepresentativo(attr.isRepresentative());
				atributos.add(atributo);

			}
		}
		return atributos;
	}

	public synchronized DKDDO importDocument(Documento item, DKDatastoreICM connCM, String itemTypeStr,
			CMDocumentoSoporte ds) {

		DKDDO ddoDocument = null;
		try {
			String nombreItemType = item.getNombre();
			short itemType = item.getItemType();
			if (itemType == DKConstant.DK_CM_DOCUMENT) {
				boolean existFolder = existsFolder(connCM, ds.getIdSolicitud());
				if (existFolder == false) {
					fillAttributesDKFolder(ds.getSolicitud(), connCM);
				}
				if (existsDocument(connCM, ds) == false) {
					ddoDocument = connCM.createDDO(nombreItemType, itemType);

					ddoDocument = fillDocumentAttrs(item.getAtributos(), ddoDocument);

					ddoDocument = fillParts(ddoDocument, item, connCM);

					ddoDocument.add();

				} else {
					logger.info(" Ya existe el documento ");
				}
			}
		} catch (DKException e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		} catch (Exception e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		}
		return ddoDocument;
	}

	public synchronized DKDDO importDocument(Documento item, DKDatastoreICM connCM, String itemTypeStr, CMDocumento d) {

		DKDDO ddoDocument = null;
		try {
			String nombreItemType = item.getNombre();
			short itemType = item.getItemType();
			if (itemType == DKConstant.DK_CM_DOCUMENT) {
				boolean existFolder = existsFolder(connCM, d.getIdSolicitud());
				if (existFolder == false) {
					fillAttributesDKFolder(d.getSolicitud(), connCM);
				}

				if (existsDocument(connCM, d) == false) {
					ddoDocument = connCM.createDDO(nombreItemType, itemType);

					ddoDocument = fillDocumentAttrs(item.getAtributos(), ddoDocument);

					ddoDocument = fillParts(ddoDocument, item, connCM);

					ddoDocument.add();

				} else {
					logger.info(" Ya existe el documento ");
				}
			}
		} catch (DKException e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		} catch (Exception e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		}
		return ddoDocument;
	}
	
	public synchronized DKDDO importDocument(Documento item, DKDatastoreICM connCM, String itemTypeStr, CMDeposito ddep) {

		DKDDO ddoDocument = null;
		try {
			String nombreItemType = item.getNombre();
			short itemType = item.getItemType();
			if (itemType == DKConstant.DK_CM_DOCUMENT) {
				boolean existFolder = existsFolder(connCM, ddep.getIdSolicitud());
				if (existFolder == false) {
					fillAttributesDKFolder(ddep.getSolicitud(), connCM);
				}

				if (existsDocument(connCM, ddep) == false) {
					ddoDocument = connCM.createDDO(nombreItemType, itemType);

					ddoDocument = fillDocumentAttrs(item.getAtributos(), ddoDocument);

					ddoDocument = fillParts(ddoDocument, item, connCM);

					ddoDocument.add();

				} else {
					logger.info(" Ya existe el documento ");
				}
			}
		} catch (DKException e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		} catch (Exception e) {
			logger.info("Ha ocurrido un error en la importacion: del Documento " + e.getMessage());
			ddoDocument = null;
		}
		return ddoDocument;
	}

	private synchronized DKDDO fillParts(DKDDO ddoDocument, Documento doc, DKDatastoreICM dsICM) {
		try {
			DKTextICM base = (DKTextICM) dsICM.createDDO("ICMBASETEXT", DKConstantICM.DK_ICM_SEMANTIC_TYPE_BASE);
			((DKTextICM) base).setFormat("");
			((DKTextICM) base).setLanguageCode("");

			String mimeType = new MimeType().getCMMimeTypeByUrl(doc.getUrl());

			if (mimeType == null) {
				logger.info("Mimetype=null" + doc.getPid());
				return ddoDocument;
			}

			/**
			 * /TODO mejorar si no existe extension mimetype no agregar a base
			 **/
			base.setMimeType(mimeType);

			if (doc.getUrl() != null && doc.getUrl().isEmpty() == false) {
				base.setContentFromClientFile(doc.getUrl());
				DKParts dkParts = (DKParts) ddoDocument
						.getData(ddoDocument.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, DKConstant.DK_CM_DKPARTS));
				dkParts.addElement(base);
			}

		} catch (Exception ex) {
			logger.info(ex);
		}
		return ddoDocument;
	}

	private synchronized DKDDO fillDocumentAttrs(ArrayList<Atributo> atributos, DKDDO ddoDocument) {

		for (Atributo attr : atributos) {
			String attrName = attr.getName();
			Short attrType = attr.getTipo();
			String attrValue = attr.getValor();

			boolean addAttr = false;
			if (attrValue != null) {
				if (!attrValue.isEmpty()) {
					addAttr = true;
				}
			}

			try {
				if (addAttr) {
					if (attrType.equals(DKConstant.DK_DATE)) {
						if(attr.getValor() != "null"){
						ddoDocument.setData(ddoDocument.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, attr.getName()),
								Utilerias.parserSqlDate(attr.getValor()));
						}
					} else if (attrType.equals(DKConstant.DK_LONG)) {
						ddoDocument.setData(ddoDocument.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, attr.getName()),
								Utilerias.convertToInteger(attr.getValor()));
					} else if (attrType.equals(DKConstant.DK_DECIMAL)) {
						ddoDocument.setData(ddoDocument.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, attr.getName()),
								Utilerias.convertToBigDecimal(attr.getValor()));
					} else if (attrType.equals(DKConstant.DK_FSTRING) || attrType.equals(DKConstant.DK_VSTRING)) {
						ddoDocument.setData(ddoDocument.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, attr.getName()),
								attr.getValor());
					} else {
						throw new Exception("No se ha identificado el tipo de dato para el atributo " + attrName);
					}
				}
			} catch (DKUsageError ex) {
				logger.error(ex);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		return ddoDocument;
	}

	private synchronized boolean existsDocument(DKDatastoreICM dsICM, CMDocumentoSoporte ds) {
		boolean exists = false;
		try {

			String query = "/LWS_DOCUMENTO_SOPORTE[@LWS_ID_DOCUMENTO =\"" + String.valueOf(ds.getIdDocumento()) + "\"]";

			DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(dsICM);

			dkRetrieveOptions.baseAttributes(true);

			DKNVPair options[] = new DKNVPair[3];
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1");
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, dkRetrieveOptions);
			options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);

			dkResultSetCursor cursor = dsICM.execute(query, DKConstantICM.DK_CM_XQPE_QL_TYPE, options);

			DKDDO ddo = cursor.fetchNext();
			if (ddo != null) {
				exists = true;
			} else {
				exists = false;
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return exists;
	}

	private synchronized boolean existsDocument(DKDatastoreICM dsICM, CMDocumento d) {
		boolean exists = false;
		try {

			String query = "/LWS_DOCUMENTO[@LWS_ID_DOCUMENTO =\"" + String.valueOf(d.getIdDocumento()) + "\"]";

			DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(dsICM);

			dkRetrieveOptions.baseAttributes(true);

			DKNVPair options[] = new DKNVPair[3];
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1");
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, dkRetrieveOptions);
			options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);

			dkResultSetCursor cursor = dsICM.execute(query, DKConstantICM.DK_CM_XQPE_QL_TYPE, options);

			DKDDO ddo = cursor.fetchNext();
			if (ddo != null) {
				exists = true;
			} else {
				exists = false;
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return exists;
	}
	
	private synchronized boolean existsDocument(DKDatastoreICM dsICM, CMDeposito ddep) {
		boolean exists = false;
		try {

			String query = "/LWS_DOCUMENTO_DEPOSITO[@LWS_ID_DOCUMENTO =\"" + String.valueOf(ddep.getIdDocumento()) + "\"]";

			DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(dsICM);

			dkRetrieveOptions.baseAttributes(true);

			DKNVPair options[] = new DKNVPair[3];
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1");
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, dkRetrieveOptions);
			options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);

			dkResultSetCursor cursor = dsICM.execute(query, DKConstantICM.DK_CM_XQPE_QL_TYPE, options);

			DKDDO ddo = cursor.fetchNext();
			if (ddo != null) {
				exists = true;
			} else {
				exists = false;
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return exists;
	}

	public String procesoExtraerUrlDocumentoPorPID(String pid) {
		boolean procesoExitoso = false;
		boolean seGeneroExcepcion = false;

		String urlDocumento = null;
		try {
			DKDatastoreICM dataStore = null;
			try {
				dataStore = connection.getDataStoreCM();
			} catch (Exception e) {
				logger.info("Error en conexion a CM");
			}

			urlDocumento = getUrlDocumentoByPID(pid, dataStore);
			procesoExitoso = true;

		} catch (DKUsageError e) {
			logger.error("Error :", e);
		} catch (DKException e) {
			logger.error("Error :", e);
		} catch (Exception e) {
			logger.error("Error :", e);
		}

		this.disconnect();

		return urlDocumento;
	}

	private static DKRetrieveOptionsICM getRetrieveOptions(DKDatastoreICM dataStore) throws DKUsageError, Exception {

		DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(dataStore);
		dkRetrieveOptions.baseAttributes(true);
		dkRetrieveOptions.partsAttributes(true);
		dkRetrieveOptions.partsList(true);
		dkRetrieveOptions.linksOutbound(true);
		dkRetrieveOptions.linksTypeFilter(DKConstantICM.DK_ICM_LINKTYPENAME_DKFOLDER);
		dkRetrieveOptions.resourceContent(false);

		return dkRetrieveOptions;
	}

	public static String getUrlDocumentoByPID(String pid, DKDatastoreICM connCM)
			throws DKUsageError, DKException, Exception {
		String url = "";

		DKDDO doc = connCM.createDDOFromPID(pid);
		doc.retrieve(getRetrieveOptions(connCM).dkNVPair());
		short tipoObjeto = (Short) doc.getPropertyByName(DKConstant.DK_CM_PROPERTY_ITEM_TYPE);

		if (tipoObjeto != DKConstant.DK_CM_FOLDER) {
			short dkPartsId = doc.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, DKConstant.DK_CM_DKPARTS);

			if (dkPartsId > 0) {
				DKParts parts = (DKParts) doc.getData(dkPartsId);
				dkIterator iterator = parts.createIterator();

				while (iterator.more()) {
					DKLobICM lobICM = (DKLobICM) iterator.next();
					int versionOption = DKConstant.DK_CM_VERSION_LATEST;
					int offset = -1;
					int length = -1;
					int replicaOption = DKConstantICM.DK_ICM_GETINITIALRMURL;

					// setMimeType(lobICM.getMimeType());
					String urls[] = lobICM.getContentURLs(versionOption, offset, length, replicaOption);

					url = urls[0];
					break;
				}
			}
		}
		return url;
	}
}