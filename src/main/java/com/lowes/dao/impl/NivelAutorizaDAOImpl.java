/**
 * 
 */
package com.lowes.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.NivelAutorizaDAO;
import com.lowes.entity.NivelAutoriza;
import com.lowes.service.NivelAutorizaService;
import com.lowes.util.HibernateUtil;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class NivelAutorizaDAOImpl implements NivelAutorizaDAO {

	public NivelAutorizaDAOImpl() {
		System.out.println("NivelAutorizaDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private NivelAutorizaService nivelAutorizaService;

	@Override
	public Integer createNivelAutoriza(NivelAutoriza nivelAutoriza) {
		return (Integer) hibernateUtil.create(nivelAutoriza);
	}

	@Override
	public NivelAutoriza updateNivelAutoriza(NivelAutoriza nivelAutoriza) {
		return hibernateUtil.update(nivelAutoriza);
	}

	@Override
	public void deleteNivelAutoriza(int idNivelAutoriza) {
		NivelAutoriza nivelAutoriza = nivelAutorizaService.getNivelAutoriza(idNivelAutoriza);
		hibernateUtil.delete(nivelAutoriza);
	}

	@Override
	public List<NivelAutoriza> getAllNivelAutoriza() {
		return hibernateUtil.fetchAll(NivelAutoriza.class);
	}

	@Override
	public NivelAutoriza getNivelAutoriza(int idNivelAutoriza) {
		return hibernateUtil.fetchById(idNivelAutoriza, NivelAutoriza.class);
	}

	@Override
	public List<NivelAutoriza> getNivelesAutorizaByMonto(BigDecimal monto, Integer idMoneda) {

		List<NivelAutoriza> nivelesAutorizacion = new ArrayList<>();

		if (idMoneda.equals(Etiquetas.ID_DOLARES)) {
			
			String queryString = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE DOLARES_LIMITE <= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteSuperior";

			String queryStringNext = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE DOLARES_LIMITE >= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteSuperior"
					+ " ORDER BY DOLARES_LIMITE";

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("monto", monto.toString());
			parameters.put("limiteSuperior", "0");

			String queryStringInferior = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE DOLARES_LIMITE <= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteInferior";

			Map<String, String> parametersInferior = new HashMap<String, String>();
			parametersInferior.put("monto", monto.toString());
			parametersInferior.put("limiteInferior", "1");

			nivelesAutorizacion.addAll(hibernateUtil.fetchAllHql(queryString, parameters));
			nivelesAutorizacion.addAll(hibernateUtil.fetchAllHql(queryStringInferior, parametersInferior));

			List<NivelAutoriza> nextLevel = hibernateUtil.fetchAllHql(queryStringNext, parameters);

			if (nextLevel.size() > 0) {
				nivelesAutorizacion.add(nextLevel.get(0));
			}

		} else if (idMoneda.equals(Etiquetas.ID_PESOS)) {
			String queryString = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE PESOS_LIMITE <= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteSuperior";

			String queryStringNext = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE PESOS_LIMITE >= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteSuperior"
					+ " ORDER BY PESOS_LIMITE";

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("monto", monto.toString());
			parameters.put("limiteSuperior", "0");

			String queryStringInferior = "FROM " + NivelAutoriza.class.getName()
					+ " WHERE PESOS_LIMITE <= :monto"
					+ " AND INFERIOR_SUPERIOR = :limiteInferior";

			Map<String, String> parametersInferior = new HashMap<String, String>();
			parametersInferior.put("monto", monto.toString());
			parametersInferior.put("limiteInferior", "1");

			nivelesAutorizacion.addAll(hibernateUtil.fetchAllHql(queryString, parameters));
			nivelesAutorizacion.addAll(hibernateUtil.fetchAllHql(queryStringInferior, parametersInferior));

			List<NivelAutoriza> nextLevel = hibernateUtil.fetchAllHql(queryStringNext, parameters);

			if (nextLevel.size() > 0) {
				nivelesAutorizacion.add(nextLevel.get(0));
			}
		}

		return nivelesAutorizacion;
	}

}