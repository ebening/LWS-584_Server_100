/**
 * BuzonFiscalCorpValidaCFDenLineaWSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS;

public class BuzonFiscalCorpValidaCFDenLineaWSLocator extends
		org.apache.axis.client.Service
		implements
		com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaWS {

	public BuzonFiscalCorpValidaCFDenLineaWSLocator() {
	}

	public BuzonFiscalCorpValidaCFDenLineaWSLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public BuzonFiscalCorpValidaCFDenLineaWSLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for BuzonFiscalCorpValidaCFDenLineaPort
	private java.lang.String BuzonFiscalCorpValidaCFDenLineaPort_address = "http://demonegocios.buzonfiscal.com/bfcorpvalidaenlineaws";

	public java.lang.String getBuzonFiscalCorpValidaCFDenLineaPortAddress() {
		return BuzonFiscalCorpValidaCFDenLineaPort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String BuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName = "BuzonFiscalCorpValidaCFDenLineaPort";

	public java.lang.String getBuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName() {
		return BuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName;
	}

	public void setBuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName(
			java.lang.String name) {
		BuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName = name;
	}

	public com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort getBuzonFiscalCorpValidaCFDenLineaPort()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(
					BuzonFiscalCorpValidaCFDenLineaPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getBuzonFiscalCorpValidaCFDenLineaPort(endpoint);
	}

	public com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort getBuzonFiscalCorpValidaCFDenLineaPort(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaBindingStub _stub = new com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaBindingStub(
					portAddress, this);
			_stub.setPortName(getBuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setBuzonFiscalCorpValidaCFDenLineaPortEndpointAddress(
			java.lang.String address) {
		BuzonFiscalCorpValidaCFDenLineaPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaBindingStub _stub = new com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaBindingStub(
						new java.net.URL(
								BuzonFiscalCorpValidaCFDenLineaPort_address),
						this);
				_stub.setPortName(getBuzonFiscalCorpValidaCFDenLineaPortWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("BuzonFiscalCorpValidaCFDenLineaPort".equals(inputPortName)) {
			return getBuzonFiscalCorpValidaCFDenLineaPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://www.buzonfiscal.com/ns/ws/CorpValidaCFDenLineaWS",
				"BuzonFiscalCorpValidaCFDenLineaWS");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://www.buzonfiscal.com/ns/ws/CorpValidaCFDenLineaWS",
					"BuzonFiscalCorpValidaCFDenLineaPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("BuzonFiscalCorpValidaCFDenLineaPort".equals(portName)) {
			setBuzonFiscalCorpValidaCFDenLineaPortEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
