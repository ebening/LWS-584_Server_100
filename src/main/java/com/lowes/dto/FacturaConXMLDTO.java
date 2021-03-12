package com.lowes.dto;

import java.util.List;

public class FacturaConXMLDTO {
	
	
	
	private double subtotal;
	private List<String> subtotalList;

	
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public List<String> getSubtotalList() {
		return subtotalList;
	}
	public void setSubtotalList(List<String> subtotalList) {
		this.subtotalList = subtotalList;
	}
	

	
	
}
