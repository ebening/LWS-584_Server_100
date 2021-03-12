package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.lowes.dao.FacturaDesgloseDAO;
import com.lowes.entity.FacturaDesglose;
import com.lowes.util.HibernateUtil;

@Repository
public class FacturaDesgloseDAOimpl implements FacturaDesgloseDAO {
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createFacturaDesglose(FacturaDesglose facturaDesglose) {
		return (Integer) hibernateUtil.create(facturaDesglose);
	}

	@Override
	public FacturaDesglose updateFacturaDesglose(FacturaDesglose facturaDesglose) {
		return hibernateUtil.update(facturaDesglose);
	}

	@Override
	public void deleteFacturaDesglose(Integer idFacturaDesglose) {
		hibernateUtil.delete(hibernateUtil.fetchById(idFacturaDesglose, FacturaDesglose.class));
	}

	@Override
	public List<FacturaDesglose> getAllFacturaDesglose() {
		return hibernateUtil.fetchAll(FacturaDesglose.class);
	}

	@Override
	public FacturaDesglose getFactura(Integer idFacturaDesglose) {
		return hibernateUtil.fetchById(idFacturaDesglose, FacturaDesglose.class);
	}

	@Override
	public void deleteAllByIdFactura(Integer idFactura) {
		HashMap<String,String> parametros = new HashMap<>();
		parametros.put("idFactura", idFactura.toString());
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(FacturaDesglose.class.getName());
		query.append(" WHERE ID_FACTURA = :idFactura");
			
		hibernateUtil.fetchDeleteAndUpdateQuerys(query.toString(), parametros);
	}
	
}
