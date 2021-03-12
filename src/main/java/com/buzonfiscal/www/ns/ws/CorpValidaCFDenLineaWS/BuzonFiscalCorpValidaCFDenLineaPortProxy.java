package com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS;

public class BuzonFiscalCorpValidaCFDenLineaPortProxy implements com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort {
  private String _endpoint = null;
  private com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort buzonFiscalCorpValidaCFDenLineaPort = null;
  
  public BuzonFiscalCorpValidaCFDenLineaPortProxy() {
    _initBuzonFiscalCorpValidaCFDenLineaPortProxy();
  }
  
  public BuzonFiscalCorpValidaCFDenLineaPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initBuzonFiscalCorpValidaCFDenLineaPortProxy();
  }
  
  private void _initBuzonFiscalCorpValidaCFDenLineaPortProxy() {
    try {
      buzonFiscalCorpValidaCFDenLineaPort = (new com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaWSLocator()).getBuzonFiscalCorpValidaCFDenLineaPort();
      if (buzonFiscalCorpValidaCFDenLineaPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)buzonFiscalCorpValidaCFDenLineaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)buzonFiscalCorpValidaCFDenLineaPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (buzonFiscalCorpValidaCFDenLineaPort != null)
      ((javax.xml.rpc.Stub)buzonFiscalCorpValidaCFDenLineaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.buzonfiscal.www.ns.ws.CorpValidaCFDenLineaWS.BuzonFiscalCorpValidaCFDenLineaPort getBuzonFiscalCorpValidaCFDenLineaPort() {
    if (buzonFiscalCorpValidaCFDenLineaPort == null)
      _initBuzonFiscalCorpValidaCFDenLineaPortProxy();
    return buzonFiscalCorpValidaCFDenLineaPort;
  }
  
  public com.buzonfiscal.www.ns.bf.conector._1.MessageType[] validaCFD(com.buzonfiscal.www.ns.xsd.bf.bfenviacfd._1.RequestEnviaCfdType parameters) throws java.rmi.RemoteException{
    if (buzonFiscalCorpValidaCFDenLineaPort == null)
      _initBuzonFiscalCorpValidaCFDenLineaPortProxy();
    return buzonFiscalCorpValidaCFDenLineaPort.validaCFD(parameters);
  }
  
  
}