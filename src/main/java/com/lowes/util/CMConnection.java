/**
 * 
 */
package com.lowes.util;

//import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;

import org.jboss.logging.Logger;

import com.ibm.mm.sdk.common.DKAttrDefICM;
import com.ibm.mm.sdk.common.DKDatastoreDefICM;
import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.common.DKItemTypeRelationDefICM;
import com.ibm.mm.sdk.common.DKSequentialCollection;
import com.ibm.mm.sdk.common.dkIterator;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.lowes.dto.Atributo;

/**
 *
 * @author Guillermo Trejo
 */
public class CMConnection {

	private static final Logger logger = Logger.getLogger(CMConnection.class);
    private DKDatastoreICM dsICM = null;
    public static Hashtable<String,DKDatastoreICM> servers;
    private boolean connectionActive = false;
    public synchronized DKDatastoreICM getDataStoreCM() {
        return dsICM;
    }

    /**
     * Este metodo genera una conexion a Content Manager.
     *
     * @return true - Si la conexion fue exitosa.
     */
    
	 public synchronized boolean connectCM() {
	        
	        if (dsICM != null && dsICM.isConnected() == true) {
	        	logger.info("Conexion a Content Manager - Existente.");
	            return true;
	        }                      
			
			String icmServerFile = PropertyUtil.getProperty("icm.server.file");
			String dbConnectionLS = PropertyUtil.getProperty("icm.library");
	        String dbConnectionUser = PropertyUtil.getProperty("icm.admin");
	        String dbConnectionPassword = PropertyUtil.getProperty("icm.password");
	        String dbConnectionSchema = PropertyUtil.getProperty("icm.schema");
	        	    
	        	logger.info(icmServerFile);
	        	
	            logger.info("Conexion a Content Manager - Abierta.");
	            boolean connectionActive = false;
	            try {
	            	dsICM= new DKDatastoreICM(icmServerFile);
		            dsICM.connect(dbConnectionLS, dbConnectionUser, dbConnectionPassword, dbConnectionSchema);
		            connectionActive = true;
	            } catch (DKException e) {
	                logger.error(e);
	                connectionActive = false;
	            } catch (Exception e) {
	                logger.error(e);
	                connectionActive = false;
	            }
	            return connectionActive;
	    }    

    /**
     * Desconecta la conexion a Content Manager
     */
    public synchronized void disconectCM() {
        try {
            if (dsICM != null && dsICM.isConnected()) {
                dsICM.disconnect();
                dsICM.destroy();
            }
        } catch (DKException e) {
            logger.error(e);
        } catch (Exception e) {
        	logger.error(e);
        }
    }

    /**
     * Creates an item type relationship. This is used to select part types for
     * Document Model classified Item Types.
     *
     * @param dsICM - Connected DKDatastoreICM object.
     * @param sourceItemType - Name of a source item type
     * @param targetItemType - Name of a target item type
     * @param versionControl - Version Control setting
     * @param rmCode - Default RM Code (See SResourceMgrDefSetDefaultICM). Use
     * -1 to not set now.
     * @param smsCode - Default SMS Code (See SSMSCollectionDefSetDefaultICM).
     * Use -1 to not set now.
     */
    public static synchronized void createItemTypeRelation(DKDatastoreICM dsICM, String sourceItemType, String targetItemType, short versionControl, short rmCode, short smsCode) throws DKException, Exception {
        logger.info("Creating Item Type Relation from source item type '" + sourceItemType + "' to target item type '" + targetItemType + "'...");
        DKDatastoreDefICM dsDefICM = (DKDatastoreDefICM) dsICM.datastoreDef();  // Obtain the Datastore Definition object.

        // Create a new item type relation object
        DKItemTypeRelationDefICM itemTypeRel = new DKItemTypeRelationDefICM(dsICM);

        // Set the source item
        itemTypeRel.setSourceItemTypeID(dsDefICM.getEntityIdByName(sourceItemType));

        // Set the target item
        itemTypeRel.setTargetItemTypeID(dsDefICM.getEntityIdByName(targetItemType));

        // Set default values
        itemTypeRel.setDefaultACLCode(1);
        itemTypeRel.setVersionControl(versionControl);
        if (rmCode != -1) {
            itemTypeRel.setDefaultRMCode(rmCode);
        }
        if (smsCode != -1) {
            itemTypeRel.setDefaultCollCode(smsCode);
        }

        // Add the item relation to persistent data
        dsDefICM.add(itemTypeRel);
    }

    public static synchronized ArrayList<Atributo> getAtributosListByItemType(String itemType, DKDatastoreICM connCM)
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
}
