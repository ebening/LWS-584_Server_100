<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>

  <style>

#anexarDocumentoForm .modal-header .close {
  color: white;
  opacity: .8;
}
  
#anexarDoc .modal-header {
  background-color: #004990; color: white;
}

#uploadFile {
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
.highlight { 
	border: 3px solid rgb(92, 184, 17);
}
  </style>

<!-- Modal No Mercancias -->
<div class="modal fade" id="comprobacionAsesor" role="dialog">
	<div style="width:70%" class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 style="float: left;">
					<span id="titulo-dialogo"><%=etiqueta.COMPROBACION_ANTICIPO_TITULO%></span>
				</h4>
			</div>
			
			<div class="modal-body">
				<div >
					<!-- Page Content -->
					<form:form method="post" id="conXMLForm" enctype="multipart/form-data" action="saveFacturaXML" modelAttribute="comprobacionAnticipoDTO">
					<form:hidden disabled="true" value="" path="idSolicitudAnticipoSession" />
					<form:hidden disabled="true" value="${comprobacionAnticipoDTO.idTipoSolicitud}" path="idTipoSolicitud"/>
				
					<div style="margin: 0;" id="page-wrapper">
						<div style="display: none;" id="error-alert-asesor" class="alert alert-danger fade in">
							<strong><span id="error-head-asesor"><%=etiqueta.ERROR%></span></strong>
							<span id="error-body-asesor"><%=etiqueta.COMPLETE%></span>
						</div>
				
						<div style="display: none;" id="ok-alert-asesor" class="alert alert-success fade in">
							<strong> <span id="ok-head-asesor"> </span>
							</strong> <span id="ok-body-asesor"> <%=etiqueta.SOLICITUD_GUARDADA%> </span>
						</div>
				
						<div class="panel panel-default">
							<!-- FORM FACTURAS -->
							<div style="height: 54px;" class="panel-heading">
								<h4><%=etiqueta.ADJUNTA_ARCHIVOS%>:</h4>
							</div>
				
							<div id=formFactura>
								<div id="cargaArchivos" class="">
									<div style="margin-left: 0px;" class="row">
										<div class="row show-grid">
											<div class="col-lg-6">
												<div style="margin: 5px 0;" class="row show-grid col-md-12">
													<div class=".col-xs-6">
														<div class="form-group">
															<label><%=etiqueta.CUENTA_CON_COMPROBANTE_FISCAL%></label>
															<div class="radio">
																<label><input id="compTrue" type="radio"
																	name="compradio" value="true" checked><%=etiqueta.SI%></label>
																<label><input id="compFalse" type="radio"
																	name="compradio" value="false"><%=etiqueta.NO%></label>
															</div>
														</div>
													</div>
												</div>
											</div>
											
											<div id="cargaComp" style="display: none" class="col-md-8">
												<div class="col-md-12">
													<div class="form-group">
														<label><%=etiqueta.ADJUNTAR_COMPROBANTE%></label> 
															<input style="width: 100%" id="comp" name="file" type="file">
													</div>
												</div>
												
												<div class="col-md-8"></div>
											</div>
												
											<div id="cargaDctos">
				                            	<div class="col-md-8">
				                                   	<div class="col-md-6">
				                                      		<div class="form-group">
															<label><%=etiqueta.ADJUNTAR_PDF%></label> 
															<input  id="pdf" name="file" type="file" style="width: 100%">
														</div>
				                                   	</div>
				                                   	
				                                   	<div class="col-md-4"></div>
				                                   	<div class="col-md-4"></div>
				                               	</div>
				                               
				                                <div class="col-md-8">
				                                   	<div class="col-md-6">
				                                   		<div class="form-group">
															<label><%=etiqueta.ADJUNTAR_XML%></label> 
															<input id="file" name="file" type="file" style="width: 100%">
														</div>
				                                   	</div>
				                                   	
				                                   	<div class="col-md-6">
				                                   		<button
															id="validarXMLb" onclick="valXML()"
															style="margin-left: 0px; float: left;" type="button"
															class="btn btn-default"><%=etiqueta.VALIDAR_XML%></button>
															<img id="loaderXML" style="display: none;"
															src="${pageContext.request.contextPath}/resources/images/loader.gif" />
				                                   	</div>
				                               	</div>
				                            </div>
											<div class="col-md-4"></div>
										</div>
									</div>
									<!-- /.row (nested) -->
								</div>
				
								<!-- /.panel-body -->
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4><%=etiqueta.REVISA_INFORMACION_FACTURA%>:</h4>
									</div>
									<div class="panel-body">
										<div class="row">
											<div class="col-lg-12">
												<div class="">
													<div class="row show-grid">
														<div id="fieldProveedor" class="col-xs-6 col-md-4">
															<div class="form-group-parse">
																<label id="proveedorLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.PROVEEDOR%>:</label>
																<form:select id="proveedor" path=""
																	cssClass="form-control noMercanciasTooltips" disabled="false">
																	<option value="-1"><%=etiqueta.SELECCIONE%></option>
																	<c:forEach items="${lstProveedores}" var="lstProveedores">
																		<option value="${lstProveedores.idProveedor}">${lstProveedores.descripcion}</option>
																	</c:forEach>
																</form:select>
															</div>
														</div>
														<div id="fieldRFC" class="col-md-8">
															<div class="col-md-4">
																<div class="form-group-parse">
																	<label><%=etiqueta.RFC%>:</label>
																	<form:input id="rfcEmisor" path=""
																		cssClass="form-control" disabled="false"
																		value="${reembolso.rfcEmisor}" />
																</div>
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
													</div>
													
													<div class="row show-grid">
														<div class="col-md-4">
															<div class="form-group-parse">
																<label><%=etiqueta.FOLIO%>:</label>
																<form:input id="folio" path=""
																	cssClass="form-control" disabled="false"
																	value="${reembolso.serie}" maxlength="10" />
															</div>
														</div>
														
														<div id="fieldFolFiscal" class="col-md-8">
															<div class="col-md-4">
																<div class="form-group-parse">
																	<label><%=etiqueta.SERIE%>:</label>
																	<form:input id="serie" path=""
																		cssClass="form-control" disabled="false"
																		value="${reembolso.serie}" />
																</div>
															</div>
															
															<div class="col-md-8">
																<div class="form-group-parse">
																	<label><%=etiqueta.FOLIO_FISCAL%>:</label>
																	<form:input id="folioFiscal" path=""
																		class="form-control" disabled="false"
																		value="${reembolso.folioFiscal}" />
																</div>
															</div>
															<div class="col-md-4"></div>
														</div>
													</div>
														
													<div class="row show-grid">
														<div class="col-md-4">
															<div id="fecha-sandbox" class="form-group-parse">
																<label><%=etiqueta.FECHA%>:</label>
																<form:input readonly="true" id="fecha" path=""
																	cssClass="form-control" disabled="false"
																	value="${reembolso.fecha}" />
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4"></div>
													</div>
				
													<div class="row show-grid">
														<div class="col-md-4">
															<label><%=etiqueta.SUBTOTAL%>:</label>
															<div class="form-group-parse input-group">
																<span class="input-group-addon">$</span>														
																<form:input id="subTotal" path=""
																	cssClass="form-control currencyFormat" disabled="true"
																	value="${reembolso.subTotal}" />
															</div>
														</div>
				
														<div class="col-xs-6 col-md-4">
															<div class="col-md-6">
																<div class="form-group-parse">
																	<label><%=etiqueta.MONEDA%>:</label>
																	<form:select path="moneda.idMoneda" disabled="true"
																		id="monedaDet" cssClass="form-control">
																		<option value="-1"><%=etiqueta.SELECCIONE%></option>
																		<c:forEach items="${monedaList}" var="monedaList">
																			<option value="${monedaList.idMoneda}">${monedaList.descripcion}</option>
																			</c:forEach>
																	</form:select>
																</div>
															</div>
														</div>
				
														<div id="fieldRetenciones" class="col-xs-6 col-md-4">
															<div style="margin-bottom: 0px; margin-top: 34px;"
																class="form-group-parse">
																<div class="checkbox">
																	<form:checkbox disabled="false" id="conRetenciones"
																		path="" value="" /><%=etiqueta.CON_RETENCIONES%>
																</div>
															</div>
														</div>
													</div>
												</div>
				
												<div id="fieldIVA" class="row show-grid">
													<div class="col-md-4">
														<label><%=etiqueta.IVA%>:</label>
														<div class="form-group-parse input-group">
															<span class="input-group-addon">$</span>
															<form:input class="form-control" id="iva" path=""
																cssClass="form-control currencyFormat" disabled="false"
																value="${reembolso.iva}" />
															<form:input type="hidden" id="tasaIva" path=""
																cssClass="form-control currencyFormat" disabled="false"
																value="${reembolso.tasaIva}" />
														</div>
													</div>
													<div class="col-xs-6 col-md-4"></div>
													<div class="col-xs-6 col-md-4">
														<label><%=etiqueta.IVA_RETENIDO%>:</label>
														<div class="form-group-parse input-group">
															<span class="input-group-addon">$</span>
															<form:input path="" id="iva_retenido"
																disabled="false" cssClass="form-control currencyFormat" value="" />
														</div>
													</div>
												</div>
				
												<div id="fieldIEPS" class="row show-grid">
													<div class="col-md-4">
														<label><%=etiqueta.IEPS%>:</label>
														<div class="form-group-parse input-group">													
															<span class="input-group-addon">$</span>
															<form:input path="" id="ieps" disabled="false"
																cssClass="form-control currencyFormat" value="${reembolso.ieps}" />
															<form:input type="hidden" path="" id="tasaIeps" disabled="false"
																cssClass="form-control" value="${reembolso.tasaIeps}" />
														</div>
													</div>
													
													<div class="col-xs-6 col-md-4"></div>
													<div class="col-xs-6 col-md-4">
															<label><%=etiqueta.ISR_RETENIDO%>:</label>
															<div class="form-group-parse input-group">
																<span class="input-group-addon">$</span>
																<form:input path="" id="isr_retenido"
																	disabled="false" cssClass="form-control currencyFormat" value="" />
															</div>
														</div>
												</div>
				
												<div class="row show-grid">
													<div class="col-md-4">
															<label><%=etiqueta.TOTAL%>:</label>
															<div class="form-group-parse input-group">
																<span class="input-group-addon">$</span>
																<form:input id="total" path="" class="form-control currencyFormat"
																	onblur="setSbt()" disabled="false" value="${reembolso.total}" />
															</div>
														</div>
													<div class="col-xs-6 col-md-4"></div>
													<div class="col-xs-6 col-md-4"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- FORM FACTURAS -->
									
							<!-- PANEL INFERIOR -->
							
							<form:input type="hidden" path="" id="selectedIndex" disabled="false"
																cssClass="form-control" value="" />
							
							<div style="border-color: #fff;" class="panel panel-default">
								<div style="height: 54px" class="panel-heading">
									<h4 style="float: left;"><%=etiqueta.INGRESE_INFORMACION_DESGLOSE_CONTABLE%>:</h4>
									<button id="btn_agregar" onclick="addRowToTable()"
										style="margin-left: 10px; float: right;" type="button"
										class="btn btn-primary"><%=etiqueta.AGREGAR%></button>
									<button id="btn_guardar" data-dismiss="modal" onclick="editRowToTable()"
										style="margin-left: 10px; float: right; display: none;" type="button"
										class="btn btn-primary"><%=etiqueta.GUARDAR%></button>
								</div>
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<table class="table table-striped table-bordered table-hover" id="tablaDesgloseCompAsesor">
											<thead>
												<tr>
													<th><%=etiqueta.LINEA%></th>
													<th><%=etiqueta.SUBTOTAL%></th>
													<th><%=etiqueta.LOCACION%></th>
													<th><%=etiqueta.CUENTA_CONTABLE%></th>
													<th><%=etiqueta.FACTURA_FOLIO%></th>
													<th><%=etiqueta.CONCEPTO%></th>
													<th><%=etiqueta.IVA%></th>
													<th><%=etiqueta.IEPS%></th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${comprobacionAnticipoDTO.lstFactDto}" var="lstFactDto" varStatus="status">
													<tr>
														<td align="center" class="linea">${status.count}</td>
														<td><form:input onchange="sumSbtComp()"
															cssClass="form-control subtotales currencyFormat"
															path="lstFactDto[${status.index}].subTotal"
															value="${lstFactDto.subTotal}" disabled="true"/></td>
				
														<td><form:select
															path="lstFactDto[${status.index}].locacion"
															cssClass="form-control" disabled="true">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${comprobacionAnticipoDTO.lcPermitidas}" var="llist">
																<option 
																	${comprobacionAnticipoDTO.locacion.idLocacion == llist.idLocacion ? 'selected' : ''}
																	value="${llist.idLocacion}">${llist.numeroDescripcionLocacion}
																</option>
															</c:forEach>
														</form:select></td>
				
														<td><form:select
															id="lstFactDtoCC${status.index}"
															onChange="ccOnChange.call(this)"
															name="lstFactDto[${status.index}].cuentaContable"
															path="lstFactDto[${status.index}].cuentaContable"
															disabled="${!comprobacionAnticipoDTO.lstFactDto[status.index].conCompFiscal}"
															cssClass="form-control ccontable">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															
															<c:if test="${comprobacionAnticipoDTO.lstFactDto[status.index].cuentaContable ne ccNoComprobante.idCuentaContable}">
																<c:forEach items="${comprobacionAnticipoDTO.ccPermitidas}" var="cclist">
																	<option 
																		${comprobacionAnticipoDTO.lstFactDto[status.index].cuentaContable == cclist.idCuentaContable ? 'selected' : ''}
																		value="${cclist.idCuentaContable}">${cclist.numeroDescripcionCuentaContable}
																	</option>
																</c:forEach>
															</c:if>
		
															<c:if test="${comprobacionAnticipoDTO.lstFactDto[status.index].cuentaContable eq ccNoComprobante.idCuentaContable}">
																<option selected
																	value="${ccNoComprobante.idCuentaContable}">${ccNoComprobante.numeroDescripcionCuentaContable}
																</option>
															</c:if>
														</form:select></td>
				
														<td>
															${lstFactDto.razonSocial} ${lstFactDto.serie} ${lstFactDto.folio}
														</td>
				
														<td><form:input id="lstFactDto[${status.index}].concepto" cssClass="form-control conceptogrid"
																onChange="conceptoOnChange.call(this)"
																name="lstFactDto[${status.index}].concepto"
																path="lstFactDto[${status.index}].concepto"
																value="${lstFactDto.concepto}" disabled="false" type="text" maxlength="500"/></td>
				
														<td><form:input onchange=""
																cssClass="form-control ivas currencyFormat"
																path="lstFactDto[${status.index}].IVA"
																value="${lstFactDto.IVA}" disabled="true"/></td>
															<td><form:input onchange=""
																cssClass="form-control ieps currencyFormat"
																path="lstFactDto[${status.index}].IEPS"
																value="${lstFactDto.IEPS}" disabled="true"/></td>
				
														<td>
															<button type="button" style="height: 22px;" class="btn btn-xs btn-danger removerFila"><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
											
										<table class="table table-striped table-hover" id="tblSumatorias">
											<tbody>
												<tr>
													<td style="width: 5%; padding-top: 17px;"><%=etiqueta.TOTALES%>:</td>
													<td>
														<input value="${comprobacionAnticipoDTO.subTotal}" class="form-control currencyFormat" id="sbt" type="text" disabled="disabled">
													</td>
													<td style="width: 50%"></td>
													<td>
														<input value="0.00" class="form-control currencyFormat" id="ivatot" type="text" disabled="disabled">
													</td>
													<td>
														<input value="0.00" class="form-control currencyFormat" id="iepstot" type="text" disabled="disabled">
													</td>
													
													<td style="width: 41px" ></td>
														
												</tr>
											</tbody>
										</table>
										
										<table class="table">
											<tbody>
												<tr>
												    <td style="width: 80%"></td>
													<td style="width: 5%; padding-top: 17px;"><%=etiqueta.TOTAL%>:</td>
													<td style="width: 15%">
														<form:input value="${comprobacionAnticipoDTO.montoTotal}"
														class="form-control currencyFormat" path="strMontoTotal" id="reembTot" type="text"
														disabled="false"/>
													</td>
													<td></td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- /.table-responsive -->
								</div>
							</div>
							<!-- /PANEL INFERIOR  -->
						</div>
					</div>
					<!-- /#page-wrapper -->
					</form:form>
					<!-- /Page Content -->
				</div>
			</div>
			
			<div class="modal-footer">
				<button data-dismiss="modal" style="margin-left: 10px; float: right;" 
				type="button" class="btn btn-primary"><%=etiqueta.CERRAR%></button>				
			</div>
		</div>
	</div>
</div>

