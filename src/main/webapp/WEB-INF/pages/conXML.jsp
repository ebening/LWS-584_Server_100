<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	Etiquetas etiqueta = new Etiquetas("es");
%>
<%
	String errorHead = request.getParameter("errorHead");
%>
<%
	String errorBody = request.getParameter("errorBody");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<jsp:include page="template/head.jsp" />

<style>
.panel-body {
	padding: 0px;
}

.show-grid {
	margin: 0px 0;
}

.hackCss {
	width: 100px;
	margin-left: 78px;
	margin-top: 13px;
}

.show-grid [class^=col-] {
	padding-top: 0;
	padding-bottom: 0px;
	border: 1px solid #FFF;
	background-color: #FFF !important;
}

.form-group {
	margin-bottom: 3px;
}

.trackAsset {
	
}

.removerFila {
	
}

.linea {
	font-weight: bold;
}

.locaciones {
	
}

.aidAll{
}

.ccontable {
	
}

.conceptogrid {
	
}

.formatNumber {
	
}

.numerico {
	
}

.sbtGrid{}

.deleterow {
	cursor: pointer
}
.checkbox {
	line-height: 31px;
}


</style>



</head>

<body>



	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<form:form method="post" 
			id="conXMLForm"
			enctype="multipart/form-data"
			action="saveFacturaXML" 
			modelAttribute="facturaConXML">
			<form:hidden disabled="true" value="" path="idSolicitudSession" />
			<form:hidden disabled="true" value="${tipoSolicitud}" path="tipoSolicitud" />
			<form:hidden disabled="true" value="" path="tipoFactura" />
			<form:hidden id="tasaIVA" value="" path="tasaIVA" />
			
				
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<c:if test="${tipoSolicitud eq 1}">
									<%=etiqueta.CONXML%>
								</c:if>
								<c:if test="${tipoSolicitud eq 2}">
									<%=etiqueta.SINXML%>
								</c:if>
							</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->

				<div style="display: none;" id="error-alert"
					class="alert alert-danger fade in">
					<strong><span id="error-head"><%=etiqueta.ERROR%></span></strong>
					<span id="error-body"><%=etiqueta.COMPLETE%></span>
				</div>


				<div style="display: none;" id="ok-alert"
					class="alert alert-success fade in">
					<strong> <span id="ok-head"> </span>
					</strong> <span id="ok-body"> <%=etiqueta.SOLICITUD_GUARDADA%>
					</span>
				</div>

				<div class="panel panel-default">
					<div style="height: 54px;" class="panel-heading">

                        <c:if test="${estadoSolicitud != 7}">
                        
                        <c:if test="${estadoSolicitud eq 1 or estadoSolicitud eq 4}">
						<button id="adjuntar_archivo" style="margin-left: 10px; float: right;"
							type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#anexarDoc">
							<%=etiqueta.DOCUMENTO_SOPORTE%></button>
						</c:if>	
							
							
                        <c:if test="${estadoSolicitud > 1 && estadoSolicitud != 7}">
                        
						<button onclick="verDetalleEstatus(${facturaConXML.idSolicitudSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>		
							
						<c:if test="${estadoSolicitud eq 1 or estadoSolicitud eq 4}">
						<button onclick="enviarSolicitudProceso()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%></button>
						</c:if>	
							
						
						
						<c:if test="${estadoSolicitud eq 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							${facturaConXML.idSolicitudSession > 0 ? '' : 'disabled'}
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%></button>
						</c:if>


					    <c:if test="${estadoSolicitud eq 0 or estadoSolicitud eq  1 or estadoSolicitud eq 4}">
							<button id="save_button" onclick="return valTodo();"
								style="margin-left: 10px; float: right;" type="submit"
								class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
							
						</c:if>
					</div>
					<div style="margin:4px;" class="panel-body">
					   <c:if test="${isSolicitante eq 1}">
					   

							<c:choose>
								<c:when	test="${facturaConXML.idSolicitanteJefe ne usuarioSession.idUsuario}">
								
									<div class="row show-grid col-md-3">
										<div class="col-xs-12 col-md-12">
											<div class="col-xs-12">
												<div class="col-xs-12">
													<div class="form-group">
														<div class="checkbox fix-check" style="margin-bottom: 18px; bottom: 0px; margin-left: 0px;">
															<form:checkbox id="solicitante" path="solicitante"
																value="" /><%=etiqueta.ESPECIFICA_SOLICITANTE%>
														</div>
													</div>
												</div>
												<div class="col-xs-6"></div>
											</div>
											<div class="col-xs-6"></div>
										</div>
										<div class="col-xs-6 col-md-4"></div>
									</div>
								</c:when>
								</c:choose>
								
							         <div class="row">
							         	<div class="col-md-6" style="position: relative; top: 6px;">
								         	<label class="col-md-2" style="padding-right: 0px;top: 7px;"><%=etiqueta.SOLICITANTE%>:</label>
										    <div class="form-group col-md-8">
												<form:select path="idSolicitanteJefe"
													cssClass="form-control" disabled="true">
													<option title="<%=etiqueta.SELECCIONE%>" value="-1"><%=etiqueta.SELECCIONE%></option>
													<c:if test="${fn:length(lstUsuariosJefe) > 0}">
														<c:forEach items="${lstUsuariosJefe}"
															var="lstUsuariosJefe">
															<option
																${facturaConXML.idSolicitanteJefe == lstUsuariosJefe.idUsuario ? 'selected' : ''}
																value="${lstUsuariosJefe.idUsuario}">${lstUsuariosJefe.nombre}
																${lstUsuariosJefe.apellidoPaterno}
																${lstUsuariosJefe.apellidoMaterno}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</div>
							         	</div>
									</div>
                        </c:if>
					</div>
					<div class="panel-body">
						<c:if test="${tipoSolicitud eq 1}">
							<div id="form-info-fiscal" style="margin-left: 0px; border-top: 1px solid #ddd; padding-top: 8px;" class="row">
						       <div class="row show-grid">
                                <div style="margin: 4px; width: 308px;" class="col-md-8">
                                    <div class="col-md-12">
                                       <div class="form-group">
												<label><%=etiqueta.ADJUNTAR_PDF%></label> 
												<input	${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''} id="pdf" name="file" type="file">
												<label class="file-selected"></label>
											</div>
                                    </div>
                                    <div class="col-md-4">
                                    </div>
                                    <div class="col-md-4"></div>
                                </div>
                                
                                 <div style="margin: 4px;" class="col-md-8">
                                    <div class="col-md-6">
                                     	<div class="form-group input-file">
												<label><%=etiqueta.ADJUNTAR_XML%></label> <input onchange="limpiarFactura();"
													${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
													id="file" name="file" type="file">
												<label class="file-selected"></label>
											</div>
                                    </div>
                                    <div class="col-md-6">
                                     <button
												${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
												id="validarXMLb" onclick="valXML()"
												style="margin-left: 0px; float: left;" type="button"
												class="btn btn-default"><%=etiqueta.VALIDAR_XML%></button>
											<img id="loaderXML" style="display: none;"
												src="${pageContext.request.contextPath}/resources/images/loader.gif" />
                                    </div>
                                 
                                </div>
                                
                                <div style="margin-left: 41px;" class="col-md-12">
                                   <div id="wrapCambiarXML" class="form-group">
										<div class="checkbox">
											<form:checkbox id="cambiarxml" path="cambiarxml" value="" /><%=etiqueta.CAMBIAR_FACTURA%>
										</div>
									</div>
                                </div>
                            </div>
							</div>
						</c:if>
						<!-- /.row (nested) -->
					</div>


					<!-- /.panel-body -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<c:if test="${tipoSolicitud eq 1}">
							<h4><%=etiqueta.REVISA_INFORMACION_FACTURA%>:</h4>
							</c:if>
							<c:if test="${tipoSolicitud eq 2}">
							<h4><%=etiqueta.INGRESE_INFORMACION_FACTURA%>:</h4>
							</c:if>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<div class="panel-body">
										<div class="row show-grid">
											<div class="col-md-4">
												<div class="form-group">
													<label id="companiasLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.COMPANIA%>:</label>
													<form:select path="compania.idcompania" id="compania"
														disabled="false" cssClass="form-control noMercanciasTooltips">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${lstCompanias}" var="lstCompanias">
															<option
																${facturaConXML.compania.idcompania == lstCompanias.idcompania ? 'selected' : ''}
																value="${lstCompanias.idcompania}">${lstCompanias.descripcion}
															</option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											<div class="col-xs-6 col-md-4">
												<div title="titlss" class="form-group">
													<label id="proveedorLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.PROVEEDOR%>:</label>
													<form:select  title="<%=etiqueta.SELECCIONE%>"  path="proveedor.idProveedor" id="proveedor"
														cssClass="form-control noMercanciasTooltips" disabled="false">
														<option   value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${lstProveedores}" var="lstProveedores">
															<option
																${facturaConXML.proveedor.idProveedor == lstProveedores.idProveedor ? 'selected' : ''}
																value="${lstProveedores.idProveedor}">${lstProveedores.descripcion}
															</option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											<div class="col-xs-6 col-md-2">
												<div class="form-group">
															<label><%=etiqueta.RFC%>:</label>
															<form:input readonly="true" id="rfcEmisor" path="rfcEmisor"
																cssClass="form-control" disabled="false" style="width:130px"
																value="${facturaConXML.rfcEmisor}" />
														</div>
											</div>
										</div>
										
										<div class="row show-grid">
											<div class="col-xs-5 col-md-2">
												<div class="form-group">
													<label><%=etiqueta.FOLIO%>:</label>
													<form:input maxlength="10" id="folio" path="folio" cssClass="form-control"
														style="min-width:120px"
														disabled="false" value="${facturaConXML.folio}" />
												</div>
											</div>
											
											<div class="col-xs-1 col-md-2">
												<c:if test="${tipoSolicitud eq 1}">
													<div class="form-group">
														<label><%=etiqueta.SERIE%>:</label>
														<form:input id="serie" path="serie"
															cssClass="form-control" disabled="false"
															
															value="${facturaConXML.serie}" />
													</div>
												</c:if>
											</div>
											
											<div class="col-xs-4 col-md-2">
												<div id="sandbox-container" class="form-group">
													<label><%=etiqueta.FECHA%>:</label>
													<form:input readonly="true" id="fecha" path="fecha_factura"
														cssClass="form-control" disabled="false"
														style="text-align:center"
														value="${facturaConXML.fecha_factura}" />
												</div>
											</div>
											
											

										</div>
										<div class="row show-grid">
											
												
												<div class="col-xs-6 col-md-4">
													<c:if test="${tipoSolicitud eq 1}">
														<div class="form-group">
															<label><%=etiqueta.FOLIO_FISCAL%>:</label>
															<form:input id="folioFiscal" path="folioFiscal"
																class="form-control" disabled="false"
																value="${facturaConXML.folioFiscal}" />

														</div>
													</c:if>
												</div>
												

												
										</div>

										<div class="row show-grid">
											<div class="col-xs-12 col-md-2">
											<label><%=etiqueta.SUBTOTAL%>:</label>
													<div class="form-group input-group">
														<span class="input-group-addon">$</span>
														<form:input onblur="actualizarSubtotal();" id="strSubTotal" path="strSubTotal"
															cssClass="form-control numberFormat" disabled="false"
															style=""
															value="${facturaConXML.strSubTotal}" />
													</div>
											</div>
											
											<c:if test="${tipoSolicitud eq 1}">
											<div class="col-xs-12 col-md-2" style="">
												<label><%=etiqueta.IVA%>:</label>
												<div class="form-group input-group">													
													<span class="input-group-addon">$</span>
													<form:input id="iva" path="strIva"
														cssClass="form-control numerico" disabled="false"

														value="${facturaConXML.strIva}" />
												</div>
											</div>
							                </c:if>
											
											<c:if test="${tipoSolicitud eq 1}">
											<div class="col-xs-12 col-md-2" style="">
												<label><%=etiqueta.IEPS%>:</label>
												<div class="form-group input-group">													
													<span class="input-group-addon">$</span>
													<form:input path="strIeps" id="ieps" disabled="false"
														cssClass="form-control numerico"

														value="${facturaConXML.strIeps}" />
												</div>
											</div>
											</c:if>
											
											
											<div class="col-xs-12 col-md-2">
												<label><%=etiqueta.MONEDA%>:</label>
												<div class="form-group">
													<form:select id="moneda" path="moneda.idMoneda" style="height: 24px;"
														cssClass="form-control" disabled="false">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${lstMoneda}" var="lstMoneda">
															<option
																${facturaConXML.moneda.idMoneda == lstMoneda.idMoneda ? 'selected' : ''}
																value="${lstMoneda.idMoneda}">${lstMoneda.descripcion}
															</option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											
											
										</div>
										
										
										<c:if test="${tipoSolicitud eq 1}">
											<div class="row show-grid">
											
												<div class="col-xs-12 col-md-2">
													<div style="margin-bottom: 0px; margin-top: 34px;"
														class="form-group">
														<div class="checkbox fix-check">
															<form:checkbox id="conRetenciones" path="conRetenciones"
																value="" /><label><%=etiqueta.CON_RETENCIONES%></label>
														</div>
													</div>
												</div>
												
						                    <div class="col-xs-3 col-md-2">
												<label><%=etiqueta.IVA_RETENIDO%>:</label>
												<div class="form-group input-group">													
													<span class="input-group-addon">$</span>
													<form:input path="strIva_retenido" id="iva_retenido"
														disabled="false" cssClass="form-control numerico"
														style=""
														value="${facturaConXML.strIva_retenido}" />
												</div>
											</div>
											
											
											<div class="col-xs-3 col-md-2" style="">
												<label><%=etiqueta.ISR_RETENIDO%>:</label>
												<div class="form-group input-group">
													<span class="input-group-addon">$</span>
													<form:input path="strIsr_retenido" id="isr_retenido"
														disabled="false" cssClass="form-control numerico"
														style=""
														value="${facturaConXML.strIsr_retenido}" />
												</div>
											</div>
												
												
											</div>
										</c:if>

										<div class="row show-grid">
											
											<div class="col-xs-6 col-md-4"></div>
											
										</div>

										<div class="row show-grid" style="border-top: 1px dotted #ddd; padding-top: 12px;">
											<div class="col-xs-12 col-md-offset-10 col-md-2">
											<label><%=etiqueta.TOTAL%>:</label>
												<div class="form-group input-group">
													<span class="input-group-addon">$</span>
													<form:input onblur="cloneTotalAmount()" id="total" path="strTotal"
														class="form-control numerico importe-total" disabled="false"
														style="width:100%;"
														value="${facturaConXML.strTotal}" />
												</div>
											</div>
											<div class="col-xs-6 col-md-4"></div>
											<div class="col-xs-6 col-md-4"></div>
										</div>


										<div class="row show-grid">
											<div class="col-md-12">
												<div class="form-group">
													<label style="margin-top: 5px;" for="comment"><%=etiqueta.CONCEPTO_DEL_GASTO%>:</label>
													<form:textarea maxlength="500" path="concepto" style="margin-bottom:10px;"
														value="${facturaConXML.concepto}" cssClass="form-control"
														rows="5" />
												</div>
											</div>
											<div class="col-md-4"></div>
										</div>

									</div>
								</div>
							</div>
							<!-- /.row (nested) -->
						</div>
					</div>

					<!-- PANEL INFERIOR -->

					<div style="border-color: #fff;" class="panel panel-default">
						<c:if test="${tipoSolicitud eq 1}">
							<div class="panel-body">

								<div style="margin-left: 20px; margin-bottom: 10px;" class="row">
									<div class="col-md-2">
										<div class="form-group">
											<div class="checkbox">
												<form:checkbox id="track_asset" path="track_asset" value="" style="line-height: 31px;"/><%=etiqueta.TRACK_ASSET%>
											</div>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label id="libroContableLabel" data-toggle="tooltip"
												data-trigger="hover" data-placement="top" title=""><%=etiqueta.LIBRO_CONTABLE%>:</label>
											<form:select path="id_compania_libro_contable.idcompania"
												id="id_compania_libro_contable"
												cssClass="form-control noMercanciasTooltips"
												disabled="true">
												<option value="-1"><%=etiqueta.SELECCIONE%></option>
												<c:forEach items="${lstCompanias}" var="lstCompanias">
												 <c:if test="${lstCompanias.esContable == 1}">
													<option
														${facturaConXML.id_compania_libro_contable.idcompania == lstCompanias.idcompania ? 'selected' : ''}
														value="${lstCompanias.idcompania}">${lstCompanias.descripcion}
													</option>
												 </c:if>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label><%=etiqueta.PAR%>:</label>
											<form:input onblur="checkPar();" maxlength="6" id="par" path="par"
												cssClass="form-control trackAsset" disabled="true" value="" />
										</div>
									</div>
								</div>
							</div>
						</c:if>

						<div style="height: 54px" class="panel-heading">
							<h4 style="float: left;"><%=etiqueta.INGRESE_INFORMACION_DESGLOSE_FACTURA%>:</h4>
							<c:if test="${estadoSolicitud eq 0 or estadoSolicitud eq  1 or estadoSolicitud eq 4}">
								<button onclick="addRowToTable()"
									style="margin-left: 10px; float: right;" type="button"
									class="btn btn-primary"><%=etiqueta.AGREGAR%></button>
							</c:if>

						</div>
						<div class="panel-body tableGradient">

							<div class="dataTable_wrapper scrollable">

								<table class="table table-striped table-bordered table-hover"
									id="tablaDesglose">
									<thead>
										<tr>
											<th><%=etiqueta.LINEA%></th>
											<th><%=etiqueta.SUBTOTAL%></th>
											<th><%=etiqueta.LOCACION%></th>
											<th><%=etiqueta.CUENTA_CONTABLE_LBL%></th>
											<th><%=etiqueta.CONCEPTO%></th>
											<c:if test="${tipoSolicitud eq 1}">
												<th><%=etiqueta.AID%></th>
												<th><%=etiqueta.CATEGORIA_MAYOR%></th>
												<th><%=etiqueta.CATEGORIA_MENOR%></th>
											</c:if>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${facturaConXML.facturaDesgloseList}"
											var="facturaDesgloseList" varStatus="status">
											<tr>
												<td class="linea" align="center">${status.count}</td>
												<td>
													<form:input step="any"
														onblur="sumSbt()" 
														cssClass="form-control subtotales currencyFormat sbtGrid"
														path="facturaDesgloseList[${status.index}].strSubTotal"
														value="${facturaDesgloseList.strSubTotal}" />
												</td>

												<td><form:select
												        onchange="actualizarCuentas(this.id)"
														path="facturaDesgloseList[${status.index}].locacion.idLocacion"
														cssClass="form-control locaciones">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${lcPermitidas}" var="llist">
															<option
																${facturaDesgloseList.locacion.idLocacion == llist.idLocacion ? 'selected' : ''}
																value="${llist.idLocacion}">${llist.numeroDescripcionLocacion}
															</option>
														</c:forEach>
													</form:select></td>

												<td><form:select
														path="facturaDesgloseList[${status.index}].cuentaContable.idCuentaContable"
														cssClass="form-control ccontable">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:if test="${facturaDesgloseList.cuentaContable.idCuentaContable ne ccNoComprobante.idCuentaContable}">
															<c:forEach items="${ccPermitidas}" var="cclist">
															<option
																${facturaDesgloseList.cuentaContable.idCuentaContable == cclist.idCuentaContable ? 'selected' : ''}
																value="${cclist.idCuentaContable}">${cclist.numeroDescripcionCuentaContable}</option>
															</c:forEach>
														</c:if>
	
														<c:if test="${facturaDesgloseList.cuentaContable.idCuentaContable eq ccNoComprobante.idCuentaContable}">
															<option selected
																value="${ccNoComprobante.idCuentaContable}">${ccNoComprobante.numeroDescripcionCuentaContable}
															</option>
														</c:if>
													</form:select></td>

												<td><form:input maxlength="500" cssClass="form-control conceptogrid"
														path="facturaDesgloseList[${status.index}].concepto"
														value="${facturaDesgloseList.concepto}" /></td>

												<c:if test="${tipoSolicitud eq 1}">
													<td><form:select
													        onchange="alSeleccionarAID(this.id)"
															path="facturaDesgloseList[${status.index}].aid.idAid"
															cssClass="form-control trackAsset">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${listAids}" var="aidlist">
																<option
																	${facturaDesgloseList.aid.idAid == aidlist.idAid ? 'selected' : ''}
																	value="${aidlist.idAid}">${aidlist.numeroDescripcionAid}
																</option>
															</c:forEach>
														</form:select></td>

													<td><form:select
															path="facturaDesgloseList[${status.index}].categoriaMayor.idCategoriaMayor"
															cssClass="form-control trackAsset">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${listCatMayor}" var="listCatMayor">
																<option
																	${facturaDesgloseList.categoriaMayor.idCategoriaMayor == listCatMayor.idCategoriaMayor ? 'selected' : ''}
																	value="${listCatMayor.idCategoriaMayor}">${listCatMayor.descripcion}
																</option>
															</c:forEach>
														</form:select></td>

													<td><form:select
															path="facturaDesgloseList[${status.index}].categoriaMenor.idCategoriaMenor"
															cssClass="form-control trackAsset">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${listCatMenor}" var="listCatMenor">
																<option
																	${facturaDesgloseList.categoriaMenor.idCategoriaMenor == listCatMenor.idCategoriaMenor ? 'selected' : ''}
																	value="${listCatMenor.idCategoriaMenor}">${listCatMenor.descripcion}
																</option>
															</c:forEach>
														</form:select></td>
												</c:if>

												<td><button type="button"
														class="btn btn-danger removerFila"><%=etiqueta.REMOVER%></button></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
								<table style="width: 40%;"
									class="table table-striped table-bordered table-hover col-xs-0  col-md-4 col-md-offset-6"
									id="tablaDesgloseValores">
									<thead>
										<tr>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="width: 50%"><%=etiqueta.SALDO_COMPROBADO%>:</td>
											<td>
												<input value="0.00" class="form-control currencyFormat importe-total" id="sbt"
													type="text" disabled="disabled">
											</td>
										</tr>

										<tr>
											<td style="width: 50%"><%=etiqueta.SALDO_PENDIENTE%>:</td>
											<td><input value="0.00" class="form-control currencyFormat importe-total"
												id="restante" type="text" disabled="disabled"></td>
										</tr>

 										<tr style="display:none">
											<td style="width: 50%"><%=etiqueta.SUBTOTAL%>:</td>
											<td><input value="${facturaConXML.strSubTotal}"
												class="form-control currencyFormat importe-total" id="sppc" type="text"
												disabled="disabled"></td>
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

		<!-- Modal Warning Eliminar -->
		<div id="modal-solicitud" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 style="float: left;">
							<span id="titulo-dialogo"><%=etiqueta.ATENCION%></span>
						</h4>
					</div>
					<div class="modal-body">

						<p id="mensaje-dialogo">
							<%=etiqueta.MENSAJE_DIALOGO_CON_XML%>
						</p>
					</div>
					<div class="modal-footer">

						<a style="display: none;" href="#" id="cambiarxml_button"
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO%></a>
						<a style="display: none;" href="#" id="cancelar_button"
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO_CANCELAR%>
						</a> <a style="display: none;" href="#"
							id="cambiar_solicitante_button" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_SOLICITANTE%></a> <a href="#"
							id="cambiarxml_button_cancelar" class="btn btn-default"
							role="button"><%=etiqueta.CANCELAR%></a> <a href="#"
							style="display: none;" id="solicitud_button_cancelar"
							class="btn btn-default" role="button"><%=etiqueta.CANCELAR%></a>

					</div>
				</div>

			</div>
		</div>

		    	<!-- Modal Documentos de soporte -->
    	<jsp:include page="modalArchivosSolicitud.jsp" />


    <jsp:include page="modalConsultarAutorizacion.jsp" />

	</div>

	<jsp:include page="template/includes.jsp" />

	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/locales/bootstrap-datepicker.es.min.js"
		charset="UTF-8"></script>

	<script>
		
		var idSolicitudGlobal = '${facturaConXML.idSolicitudSession}';
		var tipoSolicitudGlobal = '${tipoSolicitud}';
		var idStatus = '${estadoSolicitud}';
		var hasmod = false;
		var especificaSol = '${isSolicitante}';


		
		</script>
	<script src="${pageContext.request.contextPath}/resources/js/jqueryFormat.js"></script>

	<script>
	
		   function valTodo(){
			   $(window).unbind('beforeunload');
			   var error = false;
			   var pdfError = false;
			   var trackAsAssetError = false;
			   var idSolicitud = '${facturaConXML.idSolicitudSession}';
			   if(idSolicitudGlobal == 1){
			     $("#par").removeClass("errorx");
			     $("#id_compania_libro_contable").removeClass("errorx");
		        }

			   var length = $("#tablaDesglose > tbody > tr").length;
			   
			   if(length > 0){
				   
			    $('.subtotales').each(function() {
			    	if($(this).val() == "" || $(this).val() == 0){
			    		$(this).addClass("errorx");
			    		error = true;
			    	}else{
			    		$(this).removeClass("errorx");
			    	}
				});
			    
			    $('.locaciones').each(function() {
			    	if($(this).val() == -1){
			    		$(this).addClass("errorx");
			    		error = true;
			    	}else{
			    		$(this).removeClass("errorx");
			    	}
				});
			    
			    
			    $('.ccontable').each(function() {
			    	if($(this).val() == -1){
			    		$(this).addClass("errorx");
			    		error = true;
			    	}else{
			    		$(this).removeClass("errorx");
			    	}
				});
			    
			    $('.conceptogrid').each(function() {
			    	if($(this).val() == ""){
			    		$(this).addClass("errorx");
			    		error = true;
			    	}else{
			    		$(this).removeClass("errorx");
			    	}
				});
			    
			   }
			   
			   //VALIDACIONES.
			   if($("#solicitante").is(":checked")){
				   if($("#idSolicitanteJefe").val() == -1){
					   $("#idSolicitanteJefe").addClass("errorx");
					   error = true;
				   }else{
					   $("#idSolicitanteJefe").removeClass("errorx");
				   }
			   }
			   
			   if($("#conRetenciones").is(":checked")){
				   
				   if($("#isr_retenido").val() == ""){
					   $("#isr_retenido").addClass("errorx");
					    error = true;
				   }else{
					   $("#isr_retenido").removeClass("errorx");
				   }
				   
				   if($("#iva_retenido").val() == ""){
					   $("#iva_retenido").addClass("errorx");
					    error = true;
				   }else{
					   $("#iva_retenido").removeClass("errorx");
				   }
			   }else{
				   $("#isr_retenido").removeClass("errorx");
				   $("#iva_retenido").removeClass("errorx");
			   }
			   
			   if($("#moneda").val() == -1){
				   $("#moneda").addClass("errorx");
				   error = true;
			   }else{
				   $("#moneda").removeClass("errorx");
			   }
			   
			   if(tipoSolicitudGlobal == 0){
				   if(document.getElementById("file").files.length == 0){
					   $("#file").addClass("errorx");
					   error = true;
				   }else{
					   $("#file").removeClass("errorx");
				   }
				   
				   
				   if(document.getElementById("pdf").files.length == 0){
					   $("#pdf").addClass("errorx");
					   error = true;
				   }else{
					   $("#pdf").removeClass("errorx");
				   }
		      }
			   
			   
			   if($("#compania").val() == -1){
				   $("#validarXMLb").addClass("errorx");
				   error = true;
			   }else{
				   $("#validarXMLb").removeClass("errorx");
			   }
			   
			   //validarPDF
			  /*  if(validarPDF() == false){
				   
				   $("#pdf").addClass("errorx");
			   }else{
				   $("#pdf").removeClass("errorx");
			   } */
		
			   
			   if(tipoSolicitudGlobal == 1){
				if(idSolicitudGlobal == 0){   
					var ext = $('#pdf').val().split('.').pop().toLowerCase();
					if(ext != "pdf"){
					   error = true;
					   pdfError = true;
					   $("#pdf").addClass("errorx");
					}else{
					   $("#pdf").removeClass("errorx");
					}
				}
				   
			   if($("#track_asset").is(":checked")){
				   if($("#id_compania_libro_contable").val() == -1){
					   $("#id_compania_libro_contable").addClass("errorx");
					   error = true;
				   }else{
					   if($("#id_compania_libro_contable").val() != $("#compania").val()){
						   $("#id_compania_libro_contable").addClass("errorx");
						   error = true;
						   trackAsAssetError = true;
					   }else{
						   $("#id_compania_libro_contable").removeClass("errorx");
					   }
				   }
				   
				   if($("#par").val() == ""){
					   $("#par").addClass("errorx");
					   error = true;
				   }else{
					   $("#par").removeClass("errorx");
				   }
			   }
			   }
			   
			   //validaciones sin
			   if(tipoSolicitudGlobal == 2){
				   
				 if($("#compania").val() == - 1){
					  $("#compania").addClass("errorx");
					   error = true;
				 }else{
					   $("#compania").removeClass("errorx");
				 }  
				 
				 if($("#proveedor").val() == - 1){
					 $("#proveedor").addClass("errorx");
					   error = true;
				 }else{
					 $("#proveedor").removeClass("errorx");
				 }
				 
				 if($("#rfcEmisor").val() == ""){
					 $("#rfcEmisor").addClass("errorx");
					   error = true;
				 }else{
					 $("#rfcEmisor").removeClass("errorx");
				 }
				 
				 if($("#folio").val() == ""){
					 $("#folio").addClass("errorx");
					   error = true;
				 }else{
					 $("#folio").removeClass("errorx");
				 }
				 
				 if($("#fecha").val() == ""){
					 $("#fecha").addClass("errorx");
					   error = true;
				 }else{
					 $("#fecha").removeClass("errorx");
				 }
				 
				 if($("#strSubTotal").val() == ""){
					 $("#strSubTotal").addClass("errorx");
					   error = true;
				 }else{
					 $("#strSubTotal").removeClass("errorx");
				 }

				 if($("#total").val() == ""){
					 $("#total").addClass("errorx");
					   error = true;
				 }else{
					 $("#total").removeClass("errorx");
				 }
			   }
			   
			   if(error){
				   $("#error-head").text(ERROR);
				   if(pdfError){
					   $("#error-body").text(VALIDA_ARCHIVO_PDF);
				   }else if(trackAsAssetError){
					   $("#error-body").text(VALIDA_LIBRO_CONTABLE);
				   }else{
					   $("#error-body").text(COMPLETE);
				   }
				   error_alert();
				   return false;
			   }else{
				   $("#enviarSolicitud").prop("disabled", false);
				   disabledEnabledFields(false);
				   return true;
			   }
			   
		   }
		   
		
		   
		   function sumSbt(){
		 	   var sum = 0;			   
			   console.log("----- suma");
				  $('.subtotales').each(function(){
					  if($.isNumeric($(this).val())){				  
				      sum += parseFloat($(this).val());
				      var rest = parseFloat($("#restante").val());
				      var sppc = parseFloat($("#sppc").val());
				      var restante = sppc - sum;
				      $("#restante").val(restante);
				      $("#sbt").val(sum);
				      $('#sbt').number( true, 2 );
				      $('#sppc').number( true, 2 );
							  }else{
						  $(this).val(0);
					  }
				  }); 
		   }
		   
		   function addRowToTable(){
			   var idSolicitud = '${facturaConXML.idSolicitudSession}';
			   var length = $("#tablaDesglose > tbody > tr").length;
			   var estaEnEdicion = $("#cambiarxml").is(":checked");
			   var solicitante = $("#solicitante").is(":checked");
			   
			   var solicitanteSeleccionado = true;
			   if(solicitante){
				  if($("#idSolicitanteJefe").val() == -1){
					  solicitanteSeleccionado = false;
				  } 
			   }
			   
			  if(solicitanteSeleccionado){
			    $("#idSolicitanteJefe").removeClass("errorx");
		      if(idSolicitud == 1){
				   if(idSolicitud == 0 && estaEnEdicion == false){
					   valFilesFiscales();
				   }else if(idSolicitud > 0 && estaEnEdicion == false){
					   callRow(length);
				   }else if(idSolicitud > 0 && estaEnEdicion == true){
					   valFilesFiscales()
				   }
		     }else{
		    	 if($("#strSubTotal").val() == ""){
		    		  $("#strSubTotal").addClass("errorx");
					  $("#error-head").text(ERROR);
					  $("#error-body").text(CAPTURE_SUBTOTAL);
		    		  error_alert();

		    	 }else{
					   callRow(length);
		    	 }
		     }
		   }else{
			   // especifico solicitante pero no selecciono ninguno.
			   $("#error-head").text(ERROR);
			   $("#error-body").text(ESPECIFICA_SOLICITANTE);
			   $("#idSolicitanteJefe").addClass("errorx");
	    	   error_alert();

		   }
			   
		   }//adrowtable
		   
		   function valFilesFiscales(){
			   var length = $("#tablaDesglose > tbody > tr").length;

			   if(document.getElementById("file").files.length != 0 && $("#strSubTotal").val() != ""){
				   callRow(length);
			   }  else{
					  $("#file").addClass("errorx");
					  $("#error-alert").show();
					  $("#error-head").text(ERROR);
					  $("#error-body").text(COMPLETE);
			   }
		   }
		   
		 
		   function callRow(length){
			   
			   var idSolicitante = -1;
			   if(especificaSol == '1'){
				  idSolicitante = $('#idSolicitanteJefe option:selected').val();
			   }
			   
			   var conceptoFactura = $("#concepto").val();
			   
			   var tipoSolicitud = '${tipoSolicitud}';
			   loading(true);
				$.ajax({
					type : "GET",
					cache : false,
					url : "${pageContext.request.contextPath}/addRowConXML",
					async : true,
					data : "numrows=" + length + "&idSolicitud=" + tipoSolicitudGlobal + "&idSolicitante=" + idSolicitante + "&tipoSolicitud=" + tipoSolicitud + "&concepto=" + encodeURIComponent(conceptoFactura) ,
    				success : function(response) {
    					loading(false);
						$("#tablaDesglose").append(response);
						valTrackAsset();
						calculateLine();
						if(tipoSolicitudGlobal == 2){
							$(".ccontable").prop('disabled', true);
						}
						
						$(".subtotales").last().number( true, 2 );
					},
					error : function(e) {
						loading(false);
						$("#error-head").text(ERROR);
						$("#error-body").text("No fue posible agregar desglose, contacte al administrador.");
						error_alert();
						console.log('Error: ' + e);
					},
				});
		   }
		   

		  $(document).ready(function(){
			  
			  $('#sandbox-container input').datepicker({
				  format: 'dd/mm/yyyy',
				  language: 'es',
				  endDate: '+0d',
				  autoclose: true,
				  todayHighlight: true
			  });
			  
			  console.log("tipo solicitud: "+tipoSolicitudGlobal);
			  console.log("estado solicitud: "+idStatus);
			  console.log("id solicitud: "+idSolicitudGlobal);

			  
			  $('#proveedor').on('change', function() {
		    	    getRFC(this.value);
				});
			  
				  
			  $('.noMercanciasTooltips').on('change', function() {
		    	    refreshTooltip();
				});
			  
			  
			  $('.currencyFormat').number( true, 2 );
			  $('#strSubTotal').number( true, 2 );
			  $('#iva').number( true, 2 );
			  $('#iva_retenido').number( true, 2 );
			  $('#ieps').number( true, 2 );
			  $('#isr_retenido').number( true, 2 );
			  $('#total').number( true, 2 );
			
			 	$('.sbtGrid').on('blur', function() {
				 $("#sppc").val($('#strSubTotal').val());
				 sumSbt();
				});	
			  
			  $( ".currencyFormat" ).keyup(function() {
				  sumSbt();
				});
			  			 			   
			   sumSbt();
			   valTrackAsset();
			   
				  $("#track_asset").click(function(){
					  valTrackAsset();
				  });
			    
			   
			   var idSolicitud = '${facturaConXML.idSolicitudSession}';
			   
			   if(idSolicitud > 0){
				   $("#wrapCambiarXML").show();
			   }else{
				   $("#wrapCambiarXML").hide();
			   }
				
			   if(hasmod == true){
				   console.log("hasmod: "+hasmod);
				   $(window).bind('beforeunload', function(){
						return PERDERA_INFORMACION;
					}); 
			   }
			  
			   $('#tablaDesglose').on('click', ".removerFila", function(){
				    $(this).closest ('tr').remove ();
				    sumSbt();
				    calculateLine();
				}); 
			   
			   if(tipoSolicitudGlobal == 2){
					  disabledEnabledFields(false);
					  $("#idSolicitanteJefe").prop('disabled',true);
				  }else{
			          disabledEnabledFields(true);
			          console.log("disabledEnabledFields(true)");
				  }
			 
				  $("#cambiarxml").click(function(){
					  $("#mensaje-dialogo").text(MENSAJE_DIALOGO_CON_XML);
					  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
					  $("#cambiarxml_button").show();
				  });
				 
				  $("#cambiarxml_button").click(function(){
					 limpiarFactura();
				  });
				  
				  $("#cancelar_button").click(function(){
					  
					  var id = '${facturaConXML.idSolicitudSession}';
					  	loading(true);
					     $.ajax({
					    		type : "GET",
					    		cache : false,
					    		url : "${pageContext.request.contextPath}/cancelarSolicitud",
					    		async : false,
					    		data : "idSolicitud=" + id,
					    		success : function(response) {
					    			loading(false);
					    	        if(response.resultado == 'true'){
					    				$(window).unbind('beforeunload');
					    	        	location.reload(true)
					    	        } 
					    		},
					    		error : function(e) {
					    			loading(false);
					    			console.log('Error: ' + e);
					    		},
					    	});  
				  });
				  
				 
				  
				  $("#cambiarxml_button_cancelar").click(function(){
					  $("#cambiarxml").prop("checked",false);
					  $("#modal-solicitud").modal("hide");
				  });
				  
				  
				  var isModificacion = '${facturaConXML.modificacion}';
				  var isCreacion = '${facturaConXML.creacion}';
				  
				  if(isModificacion == 'true'){
					  $("#ok-head").text(ACTUALIZACION);
					  $("#ok-body").text(INFORMACION_ACTUALIZADA);
					  ok_alert();
				  }
				  
				  if(isCreacion == 'true'){
					  $("#ok-head").text(NUEVA_SOLICITUD);
					  $("#ok-body").text(SOLICITUD_CREADA);
					  ok_alert();
				  }
				  
				  
				  $('#modal-solicitud').on('hidden.bs.modal', function () {
					   $("#cambiarxml_button").hide();
					   $("#cancelar_button").hide();
					   $("#cambiar_solicitante_button").hide();
					})
					  
				  
				  /*PARA activar y refrescar tooltip*/
				  refreshTooltip();
				  activeToolTip();
				  
				  
				  $('.aidAll').on('change', function() {
			    	    console.log($(this).attr('id'));
					});
				  
				   
				   $("#isr_retenido").prop("disabled",true);
				   $("#iva_retenido").prop("disabled",true);
				   $("#conRetenciones").click(function(){
					   if(tipoSolicitudGlobal == 2){
					   $("#isr_retenido").prop("disabled",!$("#conRetenciones").is(":checked"));
					   $("#iva_retenido").prop("disabled",!$("#conRetenciones").is(":checked"));
					   }
				   });
				   
				   if(tipoSolicitudGlobal == 2){
						$(".ccontable").prop('disabled', true);
						$("#fecha").addClass("backBlank");
					}else{
						$(".ccontable").prop('disabled', false);
					}
				   
				   setStatusScreen(idStatus);

					
				   fileUploadedName('#pdf');
				   fileUploadedName('#file');
				   $("#strSubTotal").prop('disabled', true);
	
				   
				}); 
		  
		  function valTrackAsset(){
			  if($("#track_asset").is(":checked")){
				    $("#cuenta_contable").prop('disabled', false);
				    $("#par").prop('disabled', false);
				    $("#id_compania_libro_contable").prop('disabled', false);
					$(".trackAsset").prop('disabled',false);
			  }else{
					$("#cuenta_contable").prop('disabled', true);
					$("#par").prop('disabled', true);
					$("#id_compania_libro_contable").prop('disabled', true);
					$(".trackAsset").prop('disabled',true);
					$("#id_compania_libro_contable").val(-1);
					$("#par").val("");
					$("#id_compania_libro_contable").removeClass("errorx");
					$("#par").removeClass("errorx");
					$('select.trackAsset').each(function() {
						$( this ).val(-1);
					});					
			  }
			  
		  }
		  
		  function cancelar(){
			  $("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
			  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
			  $("#cancelar_button").show();
		  }
		  
		

		  function valXML(){

			  if(document.getElementById("file").files.length != 0){
				  $("#error-alert").hide();
				  
				  var data = new FormData();
				  jQuery.each(jQuery('#file')[0].files, function(i, file) {
				      data.append('file-'+i, file);
				  });
				  
				  loading(true);
				  if(data){
					  jQuery.ajax({
						  
				    		url : "${pageContext.request.contextPath}/resolverXML",
						    data: data,
						    cache: false,
						    contentType: false,
						    processData: false,
						    type: 'POST',
						    success: function(data){
						    	
						    	loading(false);
						    	
						    	if(data.faltaProveedor == 'false'){
								$("#loaderXML").hide();
					      	    $("#file").removeClass("errorx");

						        if(data.validxml == "true"){
						        	
						        	$("#compania").val(data.idCompania);
						        	$("#proveedor").val(data.idProveedor);
						        	
						        	if(data.idMoneda == "null"){
						        		$("#moneda").prop("disabled",false);
									}else{
						        	  $("#moneda").val(data.idMoneda);
									}
						        	
							        $("#folioFiscal").val(data.folioFiscal);
							        $("#total").val(data.total);
							        $("#strSubTotal").val(data.subTotal);
							        $("#sppc").val(data.subTotal);
							        $("#rfcEmisor").val(data.rfcEmisor);
							        $("#serie").val(data.serie);
							        $("#folio").val(data.folio);
							        $("#iva").val(data.iva);
							        $("#ieps").val(data.ieps);
							        //$("#concepto").val(data.concepto);
							        $("#tipoFactura").val(data.tipoFactura);
							        $("#tasaIVA").val(data.tasaIva);
							        console.log(data);
							        
							        if(data.incluyeRetenciones == 'true'){
							          $("#conRetenciones").prop("checked",true);	
							        }
							        
							        $("#iva_retenido").val(data.ivaRetenido);
							        $("#isr_retenido").val(data.isrRetenido);
							        $("#fecha").val(data.fechaEmision);
							        
							        if(data.wsmensaje != null){
							        	if(data.wscode == 0){
							        		$("#ok-head").text(FACTURA_VALIDA);
										    $("#ok-body").text(data.wsmensaje);
							        		ok_alert();
							        	}else{
							        	    $("#error-head").text(ERROR);
											$("#error-body").text(data.wsmensaje);
							        		error_alert();
							        	}
							        }
							        
							        
							        refreshTooltip();
							        
						        }else{
						      	    $("#file").addClass("errorx");
								    $("#error-alert").show();
								    $("#error-head").text(ERROR);
								    $("#error-body").text(data.wsmensaje);
								    $("#file").val(null);
						        }
						      }else{
						    	  $("#file").addClass("errorx");
								    $("#error-alert").show();
								    $("#error-head").text(ERROR);
								    $("#error-body").text(NOPROVEEDOR);
								    $("#file").val(null);
						      }
						    }
						});
				  }
				  
			  }else{
				  $("#loaderXML").hide();
  				  $("#file").addClass("errorx");
				  $("#error-alert").show();
				  $("#error-head").text(ERROR);
				  $("#error-body").text(COMPLETE);
			  }
		  }
		  
		  
					function calculateLine() {
						$('#tablaDesglose tr').each(function(i, row) {
							if (i > 0) {
								var nlinea = $(this).find(".linea").text(i);
							}
						});
					}
				
					function disabledEnabledFields(state){
					    $("#compania").prop('disabled', state);
					    $("#proveedor").prop('disabled', state);
					    $("#rfcEmisor").prop('disabled', state);
					    $("#folioFiscal").prop('disabled', state);
					    $("#serie").prop('disabled', state);
					    $("#folio").prop('disabled', state);
					    $("#fecha").prop('disabled', state);
					    $("#strSubTotal").prop('disabled', state);
					    $("#moneda").prop('disabled', state);
					    $("#iva").prop('disabled', state);
					    $("#ieps").prop('disabled', state);
					    $("#total").prop('disabled', state);
					    $("#conRetenciones").prop('disabled', state);
					    $("#iva_retenido").prop('disabled', state);
					    $("#isr_retenido").prop('disabled', state);
					    $("#tipoSolicitud").prop('disabled',state);
					    $("#idSolicitanteJefe").prop('disabled',state);
						$(".ccontable").prop('disabled', state);
						$("#tipoFactura").prop('disabled', state);

						 
					    if($("#track_asset").is(":checked")){
					    	//$("#par").prop('disabled',state);
					    	//$("#id_compania_libro_contable").prop('disabled',state);
					    	
					    }
					}
					
	          function enviarSolicitudProceso(){
						 var idSolicitud = '${facturaConXML.idSolicitudSession}';
						if(validaEnvioProceso()){
							if(hasmod == false){
						 	 if(tipoSolicitudGlobal == 2){
						 			loading(true);
								    $.ajax({
							    		type : "GET",
							    		cache : false,
							    		url : "${pageContext.request.contextPath}/revisarArchivosEnSolicitud",
							    		async : false,
							    		data : "idSolicitud=" + idSolicitud,
							    		success : function(response) {
							    			 loading(false);
							    			 console.log(response);
							    	         if(response.respuesta == 'true'){
							    	        	 enviaProceso(idSolicitud);
							    	         }else{
							    	        	 $("#error-head").text(ERROR);
												 $("#error-body").text(DOCUMENTO_SOPORTE_ANEXAR);
											     error_alert();
							    	         }
							    		},
							    		error : function(e) {
							    			loading(false);
							    			$("#error-head").text(ERROR);
											 $("#error-body").text(NO_SE_ENVIO);
										     error_alert();
							    			
							    		},
							    	}); 
							 }else{
								 enviaProceso(idSolicitud);
							 } 
							 
						}else{
							$("#error-head").text(ERROR);
							 $("#error-body").text(GUARDE_ENVIAR);
						     error_alert();
						}
						}
					 }
					
					function getRFC(idProveedor){
						if(idProveedor != -1){
							 var idSolicitud = '${facturaConXML.idSolicitudSession}';
							 loading(true);
				    	     $("#rfcEmisor").val(null);
						     $.ajax({
						    		type : "GET",
						    		cache : false,
						    		url : "${pageContext.request.contextPath}/getRFC",
						    		async : false,
						    		data : "idProveedor=" + idProveedor,
						    		success : function(response) {
						    			loading(false);
						    			$("#rfcEmisor").val(response);
						    		},
						    		error : function(e) {
						    			loading(false);
						    			console.log('Error: ' + e);
						    		},
						    	}); 
						   }else{
							   $("#rfcEmisor").val(null);
						   }
					 }
					
					
					function validaEnvioProceso(){
						
						var valido = true;
						var msj = null;
						
						var length = $("#tablaDesglose > tbody > tr").length;
						if(length > 0){
							if(validaGrid() == false){
							   if($("#restante").val() == 0){
								 $("#sppc").removeClass("errorx");
							     return true;
							   }else{
								   $("#error-head").text(ERROR);
								    $("#error-body").text(SALDO_PENDIENTE_CERO);
								    $("#restante").addClass("errorx");
									error_alert();
									hasmod = true;
									return false; 
							   }
							}else{
								$("#error-head").text(ERROR);
							    $("#error-body").text(COMPLETE);
								error_alert();
								hasmod = true;
								return false;
							}
						}else{
							$("#error-head").text(ERROR);
						    $("#error-body").text(DESGLOSE_MINIMO);
							error_alert();
							hasmod = true;
							return false;
						}
					}
					
					function setStatusScreen(idStatus){
						if(idStatus > 0){
							if(idStatus > 1 && idStatus != 4){
							  $("#form-info-fiscal").hide();
							  $("#solicitante").prop("disabled",true);
				     		  $("#concepto").prop("disabled",true);
							  $("#track_asset").prop("disabled",true);
							  $(".subtotales").prop("disabled",true);
							  $(".trackAsset").prop("disabled",true);
							  $(".removerFila").prop("disabled",true);
							  $(".locaciones").prop("disabled",true);
							  $(".ccontable").prop("disabled",true);
							  $(".conceptogrid").prop("disabled",true);
							  
							  //restantes
							  disabledEnabledFields(true);
							  checkStatusBehavior(idStatus);
							  
							}
						}
					}
					
				
					function validaGrid(){
						
						var error = false;
						
					    $('.subtotales').each(function() {
					    	if($(this).val() == "" || $(this).val() == 0){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    $('.locaciones').each(function() {
					    	if($(this).val() == -1){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    
					    $('.ccontable').each(function() {
					    	if($(this).val() == -1){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    $('.conceptogrid').each(function() {
					    	if($(this).val() == ""){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    return error;
					}
					
					function check_numericos(){
						  $('.numerico').each(function(){
							  if($.isNumeric($(this).val()) == false){				  
								  $(this).val(0);
							  }
						  });
					}
					
				function cleanDesglose(){
					$('#tablaDesglose tr').each(function(i, row){
						if(i > 0){
						$(this).closest('tr').remove();
					    sumSbt();
					    calculateLine();
					    }
					});
					
					$("#track_asset").prop("checked",false);
					$("#par").prop('disabled', true);
					$("#id_compania_libro_contable").prop('disabled', true);
					$("#id_compania_libro_contable").val(-1);
					$('select.trackAsset').each(function() {
						$( this ).val(-1);
					});	
					$("#par").val(null);
					$("#sbt").val(null);
					$("#restante").val(null);
					
				}	
				
			     function verDetalleEstatus(id) {
			         
			  		if(id > 0){
			          	console.log(id);
			          	  loading(true);
			              $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/getEstatus",
			                  async: true,
			                  data: "intxnId=" + id,
			                  success: function(result) {
			                	loading(false);
			                  	$("#tablaDetalle").empty().append(result.lista);
			                  	//abrir ventana modal
			                  	$('#modalEstatus').modal('show'); 
			                  },
			                  error: function(e) {
			                	  loading(false);
			                      console.log('Error: ' + e);
			                  },
			              }); 
			              
			          }//if
			  	}
			     
			     //recibe el id del elemento
			     function actualizarCuentas(idLoc){
			    	 console.log("---idLocacion: "+idLoc);
			    	 
			    	if(tipoSolicitudGlobal != 2){
			    	 var id = idLoc;
			    	 var idLocacion = document.getElementById(id).value;
			    	 	loading(true);
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/seleccionLocacion",
			                  async: true,
								data : "idElemento=" + id + "&idLocacion=" + idLocacion+ "&tipoSolicitud=" + tipoSolicitudGlobal,
			                  success: function(result) {
			                	 loading(false);
			                  	 console.log(result.idElemento);
			                  	 document.getElementById(result.idElemento).innerHTML=result.options;
			                  	 document.getElementById(result.idElemento).disabled = false;
			                  	 console.log(result.options);
			                  },
			                  error: function(e) {
			                	  loading(false);
			                      console.log('Error: ' + e);
			                  },
			              }); 
			    	 }
			    	 
			     }			   
			     
			   
			     
			     function refreshTooltip(){
			    	 
			         var selectedOp = $("#proveedor option:selected").text();
			         var selectedOpComp = $("#compania option:selected").text();
			         var selectedOpLibroCont = $("#id_compania_libro_contable option:selected").text();
				         
				         $('#proveedorLabel').tooltip('hide')
				          .attr('data-original-title', selectedOp)
				          .tooltip('fixTitle');
				         
				         $('#companiasLabel').tooltip('hide')
				          .attr('data-original-title', selectedOpComp)
				          .tooltip('fixTitle');
				         
				         $('#libroContableLabel').tooltip('hide')
				          .attr('data-original-title', selectedOpLibroCont)
				          .tooltip('fixTitle');
			     }
			     
			     
			     function alSeleccionarAID(idAID){
			    	 
			    	 var id = idAID;
			    	 var idValor = document.getElementById(id).value;
			    	   loading(true);
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/seleccionAID",
			                  async: true,
								data : "idElemento=" + id + "&idAid=" + idValor,
			                  success: function(result) {
			                	     loading(false);
				                  	 document.getElementById(result.idCatMenor).innerHTML=result.optionsMenor;
				                  	 document.getElementById(result.idCatMayor).innerHTML=result.optionsMayor;
			                  },
			                  error: function(e) {
			                	  loading(false);
			                      console.log('Error: ' + e);
			                  },
			              });
			    	 
			    	 
			     }
			     
			   
			     
			     function actualizarSubtotal(){
					 $("#sppc").val($('#strSubTotal').val());
					 sumSbt();
			     }
			     
			     $(".form-control").on('change', function() {
			    	  console.log("cambio: "+$(this).val());
			    	  hasmod = true;
				 });
			     
			
				  function checkPar(){
					  if(!$.isNumeric($("#par").val())){				  
						  $("#par").val(null);
						}
				  }
				  
				  function cloneTotalAmount(){
					  $("#strSubTotal").val($("#total").val());
					  
					  if(tipoSolicitudGlobal == 2){
						  actualizarSubtotal();
					  }
				  }
				  
				  
				  function limpiarFactura(){
					    $("#cambiarxml").prop("checked",true);
					    $("#compania").val(-1);
			        	$("#proveedor").val(-1);
		        	    $("#moneda").val(-1);
					    $("#folioFiscal").val(null);
				        $("#total").val(null);
				        $("#strSubTotal").val(null);
				        $("#sppc").val(null);
				        $("#rfcEmisor").val(null);
				        $("#serie").val(null);
				        $("#folio").val(null);
				        $("#iva").val(null);
				        $("#ieps").val(null);
				        $("#iva_retenido").val(null);
				        $("#isr_retenido").val(null);
				        $("#fecha").val(null);
				        $("#concepto").val(null);
						$("#modal-solicitud").modal("hide");
						$("#track_asset").prop("checked",false);
						$("#id_compania_libro_contable").val(-1);
						$("#par").val(null);
						$("#par").prop('disabled', true);
						$("#id_compania_libro_contable").prop('disabled', true);
						$('select.trackAsset').each(function() {
							$( this ).val(-1);
						});	
						
						$('#tablaDesglose tr').each(function(i, row){
							if(i > 0){
							$(this).closest('tr').remove();
						    sumSbt();
						    calculateLine();
						    }
						});
						
						$("#sbt").val(0);
						$("#restante").val(0);
						$("#validarXMLb").prop("disabled", false);
						$("#file").prop("disabled",false);
						$("#pdf").prop("disabled",false);
				  }
			     
					
					</script>
	<!-- Scripts especificos ....  -->
	<script>
	
	 $('#conXMLForm').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});
	
	  $(document).ready(function(){
		  
		  url_server = '${pageContext.request.contextPath}';
		  hasmod = false;
	
	  });	  	  	
	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/manejarSolicitante.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
</body>
</html>
