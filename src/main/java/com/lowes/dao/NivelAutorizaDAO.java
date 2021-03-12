/**
 * 
 */
package com.lowes.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lowes.entity.NivelAutoriza;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface NivelAutorizaDAO {
	public Integer createNivelAutoriza(NivelAutoriza nivelAutoriza);
	public NivelAutoriza updateNivelAutoriza(NivelAutoriza nivelAutoriza);
	public void deleteNivelAutoriza(int idNivelAutoriza);
	public List<NivelAutoriza> getAllNivelAutoriza();
	public NivelAutoriza getNivelAutoriza(int idNivelAutoriza);
	public List<NivelAutoriza> getNivelesAutorizaByMonto(BigDecimal monto, Integer idMoneda);
	
}
