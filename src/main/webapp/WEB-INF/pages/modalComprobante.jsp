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
		<div class="modal fade" id="modal-comprobante" role="dialog">
			<div class="modal-dialog" style="width: 900px;">

				<!-- Modal content-->
					<div class="modal-content" style="width: 900px;">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo"><%=etiqueta.DESGLOSE_COMPROBANTE%></span>
							</h4>
						</div>
						<!-- Fin Modal Header -->
						<!-- Modal Body -->
						<div class="modal-body deposito-body">
							<!-- Alertas -->
							<div id="errorMsg" style="display: none;"
								class="alert alert-warning">
								<strong> <span id="error-head-files"><%=errorHead%></span>
								</strong> <span id="error-body-files"><%=errorBody%></span>
							</div>
														
							<div style="display: none; margin: 0px;" id="comprobante-ok-alert" class="alert alert-success fade in">
								<strong> <span id="comprobante-ok-head"></span>
								</strong> <span id="comprobante-ok-body"></span>
							</div>
							
							<!-- Fin de alertas -->
							<div class="panel-body desglose-modal">
								
								<div class="row">
									<!-- Header -->
									<div class="col-xs-9"><label style="line-height: 44px;"><%=etiqueta.ADJUNTA_ARCHIVOS%>:</label></div>
									<div class="col-xs-3" id="guarda-comprobante" style="text-align:right"><button class="btn btn-primary" style="margin-bottom: 15px;">Guardar</button></div>
									<!-- Carga archivos -->
									<div class="col-xs-12">
										
										<div class="panel-body"  style="border: 1px solid#E2E2E2; padding: 0px;">
												<div id="form-info-fiscal" style="margin-left: 0px;" class="row">
											       <div class="row show-grid carga-files" style="margin: 0px;">
					                                <div style="margin: 4px; padding: 0px; border:0;" class="col-md-8">
					                                    <div class="col-md-12" style="background-color: white !important; border: none;">
					                                       <div class="form-group">
																<input id="tiene-comprobante" type="checkbox" style=" height: 13px;vertical-align: -3px;" /> <%=etiqueta.NO_COMPROBANTE%>
															</div>
					                                    </div>

					                                   
					                                </div>
					                                
					                                 <div style="margin: 4px; padding: 0px;" class="col-md-8" id="upload-comprobante">
					                                    <div class="col-md-6">
					                                     	<div class="form-group">
																<label><%=etiqueta.ADJUNTAR_ARCHIVO%></label> <input
																	id="file-comprobante" name="file" type="file">
																</div>
					                                    	</div>
					                                    	<div class="col-md-6">
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
									<div class="col-xs-12"><label><%=etiqueta.INGRESA_INFO_COMPROBANTE%>:</label></div>
									<div class="col-xs-12">
										<div class="panel-body"  style="border: 1px solid#E2E2E2; padding: 15px;">
										
											<!-- ROW 1 -->
											<div class="row">
												<div class="form-group col-xs-4">
													<label><%=etiqueta.COMERCIO%></label>
													<form:input id="comprobante-comercio" path="comprobacionAnticipoViajeDTO.fechaInicio" type="text" class="form-control" value=""/>
												</div>
											</div>
											
											<!-- ROW 2 -->
											<div class="row">
												<div class="form-group col-xs-2">
													<label><%=etiqueta.FOLIO%>:</label>
													<form:input id="comprobante-folio" path="comprobacionAnticipoViajeDTO.fechaInicio" type="text" class="form-control"  value=""/>
												</div>
											</div>
											
											<!-- ROW 3 -->
											<div class="row">
												<div class="form-group col-xs-2">
													<div id="fecha-sandbox" class="sandbox-container form-group-parse">
														<label><%=etiqueta.FECHA%>:</label>
														<form:input path="comprobacionAnticipoViajeDTO.fechaInicio" id="comprobante-fecha" class="form-control" readonly="readonly" type="text" value="" />
													</div>
												</div>
												
											</div>
											
											<!-- ROW 4 -->
											<div class="row">
												<div class="form-group col-xs-2">
													<label><%=etiqueta.SUBTOTAL%>:</label>
													<form:input  id="comprobante-subtotal" path="comprobacionAnticipoViajeDTO.numeroPersonas" type="text"
													class="form-control" style="width: 100%; text-align:right" value="" disabled="true"/>
												</div>
												
												<div class="form-group col-xs-2">
													<label><%=etiqueta.MONEDA%>:</label>
													<form:select  id="comprobante-moneda" path="comprobacionAnticipoViajeDTO.moneda.idMoneda"  
														class="form-control blockedInput">
														<c:forEach items="${monedaList}" var="monedas">
															<option ${monedas.idMoneda == '8' ? 'selected' : ''} value="${monedas.idMoneda}">${monedas.descripcion}</option>
														</c:forEach>
													</form:select>
												</div>
												
											</div>
											
											<!-- ROW 6 -->
											<div class="row">
												<div class="form-group col-xs-4">
													<label><%=etiqueta.TOTAL%>:</label>
													<div class="input-group">
														<span class="input-group-addon" style="font-size:10px;">$</span>
														<form:input  id="comprobante-total" path="comprobacionAnticipoViajeDTO.numeroPersonas" type="text" class="form-control importe-total"  value=""/>
													</div>
												</div>
											</div>
											
											
										</div> <!-- Fin panel body -->
									</div>
								</div>
											
							</div>
						</div>
					</div>

			</div>
		</div>
		
		<script>
		
			
			
		
		</script>
		
