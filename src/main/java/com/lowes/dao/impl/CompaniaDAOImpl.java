package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.CompaniaDAO;
import com.lowes.entity.Compania;
import com.lowes.entity.Factura;
import com.lowes.util.HibernateUtil;

@Repository
public class CompaniaDAOImpl implements CompaniaDAO{
	
	public CompaniaDAOImpl(){
		System.out.println("CompaniaDAOImpl");
	}

	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createCompania(Compania compania) {
		return (Integer) hibernateUtil.create(compania);
	}

	@Override
	public Compania updateCompania(Compania compania) {
        return hibernateUtil.update(compania);
		//return hibernateUtil.update(getCompania(compania.getIdcompania())); // Obtener compañia por id y enviar el objeto para editar
	}

	@Override
	public void deleteCompania(Integer idCompania) {
		Compania compania = getCompania(idCompania); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(compania);
	}

	@Override
	public List<Compania> getAllCompania() {
		return hibernateUtil.fetchAll(Compania.class);
	}

	@Override
	public Compania getCompania(Integer idCompania) {
		return hibernateUtil.fetchById(idCompania, Compania.class);
	}

	@Override
	public String getTokenCompania(String rfcReceptor) {
		String queryString = "FROM " + Compania.class.getName()
				+ " WHERE RFC = :rfcReceptor";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("rfcReceptor", rfcReceptor);
		List<Compania> comp = hibernateUtil.fetchAllHql(queryString, parameters);
		return (comp != null && comp.isEmpty() == false) ? comp.get(0).getClaveValidacionFiscal() : null;
	}

}