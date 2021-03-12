package com.lowes.dao;

import java.util.List;

import com.lowes.entity.FacturaDesglose;

public interface FacturaDesgloseDAO {
	
	public Integer createFacturaDesglose(FacturaDesglose facturaDesglose);
    public FacturaDesglose updateFacturaDesglose(FacturaDesglose facturaDesglose);
    public void deleteFacturaDesglose(Integer idFacturaDesglose);
    public List<FacturaDesglose> getAllFacturaDesglose();
    public FacturaDesglose getFactura(Integer idFacturaDesglose);
    public void deleteAllByIdFactura(Integer idFactura);

}
