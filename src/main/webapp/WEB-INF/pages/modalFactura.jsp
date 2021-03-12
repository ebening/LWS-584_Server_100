
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    				

    				
  <style>
  // Ventana modal estilos 
#anexarDocumentoForm .modal-header .close {
  color: white;
  opacity: .8;
}
  
#capturaDeposito .modal-header {
  background-color: #004990; color: white;
}

#capturaDeposito {
  border: none;
  height: 28px;
  width: 185px;
  padding: 8px;
}

.fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
}
.fileUpload input.upload {
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
}
.inactivo {
	opacity:.4;
	cursor:default;
}
// Fin de ventana modal estilos
  </style>
    				
				<!-- Ventana modal Depósito -->
		<div class="modal fade" id="modal-factura" role="dialog">
			<div class="modal-dialog" style="width: 900px;">

				<!-- Modal content-->
				<%-- <form:form id="modalFactura" enctype="multipart/form-data"
					cssClass="form-horizontal" modelAttribute="singleSave"
					method="post" action="-"> --%>
					<div class="modal-content" style="width: 900px;">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo"><%=etiqueta.DESGLOSE_FACTURA%></span>
							</h4>
						</div>
						<!-- Fin Modal Header -->
						<!-- Modal Body -->
						<div class="modal-body deposito-body">
							<!-- Alertas -->
							<div style="display: none;" id="error-alert-modal" class="alert alert-danger fade in">
								<strong><span id="error-head-factura"><%=etiqueta.ERROR%></span></strong>
								<span id="error-body-factura"><%=etiqueta.COMPLETE%></span>
							</div>
					
							<div style="display: none;" id="ok-alert-modal" class="alert alert-success fade in">
								<strong> <span id="ok-head-factura"> </span>
								</strong> <span id="ok-body-factura"> <%=etiqueta.SOLICITUD_GUARDADA%> </span>
							</div>
							
							
							<!-- Fin de alertas -->

							<div class="panel-body desglose-modal">
								
								<div class="row">
									<!-- Header -->
									<div class="col-xs-9"><label style="line-height: 44px;"><%=etiqueta.ADJUNTA_ARCHIVOS%>:</label></div>
									<div class="col-xs-3" style="text-align:right"><button class="btn btn-primary"  id="guarda-factura" style="margin-bottom: 15px;">Guardar</button></div>
									<!-- Carga archivos -->
									<div class="col-xs-12">
										
										<div class="panel-body"  style="border: 1px solid#E2E2E2; padding: 0px;">
												<div id="form-info-fiscal" style="margin-left: 0px;" class="row">
											       <div class="row show-grid carga-files" style="margin: 0px;">
					                                <div style="margin: 4px; padding: 0px;" class="col-md-8 file-pdf">
					                                    <div class="col-md-4">
					                                       <div class="form-group">
																<label><%=etiqueta.ADJUNTAR_PDF%></label> 
																<input ${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''} id="pdf" name="file" type="file">
															</div>
					                                    </div>
					                                    <div class="col-md-4">
					                                    </div>
					                                    <div class="col-md-4"></div>
					                                </div>
					                                
					                                 <div style="margin: 4px; padding: 0px;" class="col-md-8 file-xml">
					                                    <div class="col-md-6">
					                                     	<div class="form-group">
																<label><%=etiqueta.ADJUNTAR_XML%></label>
																<input ${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''} id="file" name="file" type="file">
															</div>
					                                    </div>
					                                    <div class="col-md-6">
					                                     <button
																	${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
																	id="validarXMLb" onclick="valXML()"
																	style="margin-left: 0px; " type="button"
																	class="btn btn-default"><%=etiqueta.VALIDAR_XML%></button>
																	<span class="glyphicon glyphicon-ok-circle palomita" style="display:none; font-size: 24px;position: relative;top: 9px;color: #9fcc00;"></span>
																<img id="loaderXML" style="display: none;"
																	src="${pageContext.request.contextPath}/resources/images/loader.gif" />
					                                    </div>
					                                 
					                                </div>
					                                
					                            </div>
												</div>
											<!-- /.row (nested) -->
										</div>
										
										
									</div>
								</div>
								<div class="row" style="margin-top:12px">
									<!-- Header -->
									<div class="col-xs-12"><label><%=etiqueta.REVISA_LA_INFORMACION%>:</label></div>
									<div class="col-xs-12">
										<div class="panel-body"  style="border: 1px solid#E2E2E2; padding: 15px;">
										
											<!-- ROW 1 -->
											<div class="row">
												<div class="form-group col-xs-4 factura-comercio">
													<label><%=etiqueta.COMERCIO%>:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.fechaInicio" class="form-control" class="form-control" disabled/>
												</div>
											</div>
											
											<!-- ROW 2 -->
											<div class="row">
												<div class="form-group col-xs-2 factura-folio-fiscal">
													<label><%=etiqueta.FOLIO_FISCAL%>:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.fechaInicio" class="form-control" class="form-control" disabled />
												</div>
												
												<div class="form-group col-xs-2 factura-serie">
													<label><%=etiqueta.SERIE%>:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.fechaInicio" class="form-control" class="form-control" disabled />
												</div>
												
												<div class="form-group col-xs-2 factura-folio">
													<label><%=etiqueta.FOLIO%>:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.fechaInicio" class="form-control" class="form-control" disabled />
												</div>
												
											</div>
											
											<!-- ROW 3 -->
											<div class="row">
												<div class="form-group col-xs-2 factura-fecha">
													<div id="fecha-sandbox" class="sandbox-container form-group-parse">
														<label><%=etiqueta.FECHA%>:</label>
														<input type="text" path="comprobacionAnticipoViajeDTO.fechaInicio" id="fecha-factura" class="form-control" readonly="readonly" class="form-control" disabled />
													</div>
												</div>
												
											</div>
											
											<!-- ROW 4 -->
											<div class="row">
												<div class="form-group col-xs-2 factura-subtotal">
													<label><%=etiqueta.SUBTOTAL%>:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.importe" class="form-control" class="form-control currencyFormat" style="width: 100%;" disabled />
												</div>
												
												<div class="form-group col-xs-2 factura-moneda">
													<label><%=etiqueta.MONEDA%>:</label>
													<form:select  id="factura-moneda" path="comprobacionAnticipoViajeDTO.moneda.idMoneda"  
														class="form-control blockedInput">
														<c:forEach items="${monedaList}" var="monedas">
															<option  value="${monedas.idMoneda}">${monedas.descripcion}</option>
														</c:forEach>
													</form:select>
												</div>
												
											</div>
											
											<!-- ROW 5 -->
											<div class="row">
												<div class="form-group col-xs-2 factura-iva">
													<label>IVA:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.importe" class="form-control" class="form-control numberFormat" disabled/>
												</div>
												
												<div class="form-group col-xs-2 factura-eps">
													<label>EPS:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.importe" class="form-control" class="form-control numberFormat" disabled/>
												</div>
												
												<div class="form-group col-xs-2 factura-otros-impuestos">
													<label>Otros impuestos:</label>
													<input type="text" path="comprobacionAnticipoViajeDTO.importe" class="form-control" class="form-control currencyFormat" disabled/>
												</div>
												

													
												
											</div>
											
											<!-- ROW 6 -->
											<div class="row">
												<div class="form-group col-xs-4 factura-total">
													<label"><%=etiqueta.TOTAL%>:</label>
													<div class="input-group">
														<span class="input-group-addon" style="font-size:10px;">$</span>
														<input type="text" path="comprobacionAnticipoViajeDTO.importe" type="text" class="form-control importe-total currencyFormat" disabled/>
													</div>
												</div>
											</div>
											
											
										</div> <!-- Fin panel body -->
									</div>
								</div>
											
							</div>
						</div>
					</div>
				<%-- </form:form> --%>

			</div>
		</div>
		
		<script>
		
			
			
		
		</script>
		
