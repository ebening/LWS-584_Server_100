<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%Etiquetas etiqueta = new Etiquetas("es");%>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>

<!-- Modal No Mercancias -->
<div id="modal-no-mercancias" class="modal fade" role="dialog">
	<div style="width:70%" class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-body">
				<div id="wrapper">
					<!-- Page Content -->
					<form:form method="post" 
						id="conXMLForm"
						enctype="multipart/form-data"
						action="saveFacturaXML" 
						modelAttribute="facturaConXML">
						
						<form:hidden disabled="true" value="" path="idSolicitudSession" />
						<form:hidden disabled="true" value="${tipoSolicitud}" path="tipoSolicitud" />
						<form:hidden disabled="true" value="" id="tipoFactura_modal" path="tipoFactura" />
						
							
						<div style="margin: 0;" id="page-wrapper">
							<div class="container-fluid">
								<div class="row">
									<div class="col-lg-12">
										<h1 class="page-header">
											<span id="head-con">
												<%=etiqueta.CONXML%>
											</span>
											<span style="display: none;" id="head-sin">
												<%=etiqueta.SINXML%>
											</span>
										</h1>
									</div>
									<!-- /.col-lg-12 -->
								</div>
								<!-- /.row -->
							</div>
							<!-- /.container-fluid -->
			
							<div style="display: none;" id="error-alert-modal"
								class="alert alert-danger fade in">
								<strong><span id="error-head-modal"><%=etiqueta.ERROR%></span></strong>
								<span id="error-body-modal"><%=etiqueta.COMPLETE%></span>
							</div>
			
			
							<div style="display: none;" id="ok-alert-modal"
								class="alert alert-success fade in">
								<strong> <span id="ok-head-modal"> </span>
								</strong> <span id="ok-body-modal"> <%=etiqueta.SOLICITUD_GUARDADA%>
								</span>
							</div>
			
							<div class="panel panel-default">
								<div style="height: 54px;" class="panel-heading">
			
			                        <c:if test="${estadoSolicitud != 7}">
			                        
								    <c:if test="${estadoSolicitud eq 0 or estadoSolicitud eq  1 or estadoSolicitud eq 4}">
										<button id="save_button" onclick="valTodoModal();"
											style="margin-left: 10px; float: right;" type="button"
											class="btn btn-default"><%=etiqueta.GUARDAR%></button>
									</c:if>
									
										
									</c:if>
								</div>
							
								<div class="panel-body">
									<c:if test="${tipoSolicitud eq 1}">
										<div id="form-info-fiscal" style="margin-left: 0px;" class="row">
									       <div class="row show-grid">
			                                <div style="margin: 4px;" class="col-md-8">
			                                    <div class="col-md-4">
			                                       <div class="form-group">
															<label><%=etiqueta.ADJUNTAR_PDF%></label> 
															<input	${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''} id="pdf_modal" name="file" type="file">
														</div>
			                                    </div>
			                                    <div class="col-md-4">
			                                    </div>
			                                    <div class="col-md-4"></div>
			                                </div>
			                                
			                                 <div style="margin: 4px;" class="col-md-8">
			                                    <div class="col-md-6">
			                                     	<div class="form-group">
															<label><%=etiqueta.ADJUNTAR_XML%></label> <input
																${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
																id="file_modal" name="file_modal" type="file">
														</div>
			                                    </div>
			                                    <div class="col-md-6">
			                                     <button
															${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
															id="validarXMLb" onclick="valXML_modal()"
															style="margin-left: 0px; float: left;" type="button"
															class="btn btn-default"><%=etiqueta.VALIDAR_XML%></button>
														
														<img id="loaderXML_nm" style="display: none;"
															src="${pageContext.request.contextPath}/resources/images/loader.gif" />
			                                    </div>
			                                 
			                                </div>
			                                
			                                <div style="margin-left: 41px;" class="col-md-4">
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
										<h4><%=etiqueta.REVISA_INFORMACION_FACTURA%>:</h4>
									</div>
									<div class="panel-body">
										<div class="row">
											<div class="col-lg-12">
												<div class="panel-body">
													<div class="row show-grid">
														<div class="col-md-4">
															<div class="form-group">
																<label id="companiasLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.COMPANIA%>:</label>
																<form:select path="compania.idcompania" id="compania_modal"
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
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4"></div>
													</div>
													<div class="row show-grid">
														<div class="col-md-4">
															<div title="titlss" class="form-group">
																<label id="proveedorLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.PROVEEDOR%>:</label>
																<form:select  title="<%=etiqueta.SELECCIONE%>"  path="proveedor.idProveedor" id="proveedor_modal"
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
														<div class="col-md-8">
															<div class="col-md-4">
																<div class="form-group">
																	<label><%=etiqueta.RFC%>:</label>
																	<form:input id="rfcEmisor_modal" path="rfcEmisor"
																		cssClass="form-control" disabled="false"
																		value="${facturaConXML.rfcEmisor}" />
																</div>
															</div>
														</div>
													</div>
													<div class="row show-grid">
														<div class="col-md-4">
															<div class="form-group">
																<label><%=etiqueta.FOLIO%>:</label>
																<form:input id="folio_modal" path="folio" cssClass="form-control"
																	disabled="false" value="${facturaConXML.serie}" />
															</div>
														</div>
														<div class="col-md-8">
															<div class="col-md-4">
																<c:if test="${tipoSolicitud eq 1}">
																	<div id="seriefiscal" class="form-group">
																		<label><%=etiqueta.SERIE%>:</label>
																		<form:input id="serie_modal" path="serie"
																			cssClass="form-control" disabled="false"
																			value="${facturaConXML.serie}" />
																	</div>
																</c:if>
															</div>
															<div class="col-md-5">
																<c:if test="${tipoSolicitud eq 1}">
																	<div id="foliofiscal" class="form-group">
																		<label><%=etiqueta.FOLIO_FISCAL%>:</label>
																		<form:input id="folioFiscal_modal" path="folioFiscal"
																			class="form-control" disabled="false"
																			value="${facturaConXML.folioFiscal}" />
			
																	</div>
																</c:if>
															</div>
														</div>
													</div>
													<div class="row show-grid">
														<div class="col-md-4">
															<div id="sandbox-container" class="form-group">
																<label><%=etiqueta.FECHA%>:</label>
																<form:input readonly="false" id="fecha_modal" path="fecha_factura"
																	cssClass="form-control" disabled="false"
																	value="${facturaConXML.fecha_factura}" />
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4"></div>
													</div>
			
													<div class="row show-grid">
														<div class="col-md-4">
														<label><%=etiqueta.SUBTOTAL%>:</label>
																<div class="form-group input-group">
																	<span class="input-group-addon">$</span>
																	<form:input onblur="actualizarSubtotalModal();" id="strSubTotal_modal" path="strSubTotal"
																		cssClass="form-control" disabled="false"
																		value="${facturaConXML.strSubTotal}" />
																</div>
														</div>
														<div class="col-xs-6 col-md-4">
															<div class="col-md-6">
																<div class="form-group">
																	<label><%=etiqueta.MONEDA%>:</label>
																	<form:select id="moneda_modal" path="moneda.idMoneda"
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
														<div class="col-xs-6 col-md-4">
															<div style="margin-bottom: 0px; margin-top: 34px;"
																class="form-group">
																<div class="checkbox">
																	<form:checkbox id="conRetenciones_modal" path="conRetenciones"
																		value="" /><%=etiqueta.CON_RETENCIONES%>
																</div>
															</div>
														</div>
													</div>
			
													<div class="row show-grid">
														<div class="col-md-4">
															<label><%=etiqueta.IVA%>:</label>
															<div class="form-group input-group">													
																<span class="input-group-addon">$</span>
																<form:input id="iva_modal" path="strIva"
																	cssClass="form-control numerico" disabled="false"
																	value="${facturaConXML.strIva}" />
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4">
															<label><%=etiqueta.IVA_RETENIDO%>:</label>
															<div class="form-group input-group">													
																<span class="input-group-addon">$</span>
																<form:input path="strIva_retenido" id="iva_retenido_modal"
																	disabled="false" cssClass="form-control numerico"
																	value="${facturaConXML.strIva_retenido}" />
															</div>
														</div>
													</div>
			
													<div class="row show-grid">
														<div class="col-md-4">
															<label><%=etiqueta.IEPS%>:</label>
															<div class="form-group input-group">													
																<span class="input-group-addon">$</span>
																<form:input path="strIeps" id="ieps_modal" disabled="false"
																	cssClass="form-control numerico"
																	value="${facturaConXML.strIeps}" />
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4">
															<label><%=etiqueta.ISR_RETENIDO%>:</label>
															<div class="form-group input-group">
																<span class="input-group-addon">$</span>
																<form:input path="strIsr_retenido" id="isr_retenido_modal"
																	disabled="false" cssClass="form-control numerico"
																	value="${facturaConXML.strIsr_retenido}" />
															</div>
														</div>
													</div>
			
													<div class="row show-grid">
														<div class="col-md-4">
														<label><%=etiqueta.TOTAL%>:</label>
															<div class="form-group input-group">
																<span class="input-group-addon">$</span>
																<form:input id="total_modal" path="strTotal"
																	class="form-control numerico" disabled="false"
																	value="${facturaConXML.strTotal}" />
															</div>
														</div>
														<div class="col-xs-6 col-md-4"></div>
														<div class="col-xs-6 col-md-4"></div>
													</div>
													
			
													<div class="row show-grid">
														<div class="col-md-8">
															<div class="form-group">
																<label style="margin-top: 5px;" for="comment"><%=etiqueta.CONCEPTO_DEL_GASTO2%></label>
																<form:textarea maxlength="500" id="concepto_modal" path="concepto" style="margin-bottom:10px; display: none;"
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
									<c:if test="${tipoSolicitud eq 99}">
										<div class="panel-body">
			
											<div style="margin-left: 20px; margin-bottom: 10px;" class="row">
												<div class="col-md-1">
													<div class="form-group">
														<div class="checkbox">
															<form:checkbox id="track_asset" path="track_asset" value="" /><%=etiqueta.TRACK_ASSET%>
														</div>
													</div>
												</div>
			
												<div class="col-md-4">
													<div class="form-group">
														<label id="libroContableLabel" data-toggle="tooltip"
															data-trigger="hover" data-placement="top" title=""><%=etiqueta.LIBRO_CONTABLE%></label>
														<form:select path="id_compania_libro_contable.idcompania"
															id="id_compania_libro_contable"
															cssClass="form-control trackAsset noMercanciasTooltips"
															disabled="true">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${lstCompanias}" var="lstCompanias">
																<option
																	${facturaConXML.id_compania_libro_contable.idcompania == lstCompanias.idcompania ? 'selected' : ''}
																	value="${lstCompanias.idcompania}">${lstCompanias.descripcion}
																</option>
															</c:forEach>
														</form:select>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label><%=etiqueta.PAR%></label>
														<form:input id="par" path="par"
															cssClass="form-control trackAsset" disabled="true" value="" />
													</div>
												</div>
											</div>
										</div>
									</c:if>
			
									<div style="height: 54px" class="panel-heading">
										<h4 style="float: left;"><%=etiqueta.INGRESE_INFORMACION_DESGLOSE_FACTURA%>:</h4>
										<c:if test="${estadoSolicitud eq 0 or estadoSolicitud eq  1 or estadoSolicitud eq 4}">
											<button onclick="addRowToTableModal()"
												style="margin-left: 10px; float: right;" type="button"
												class="btn btn-primary"><%=etiqueta.AGREGAR%></button>
										</c:if>
			
									</div>
									<div class="panel-body">
			
										<div class="dataTable_wrapper">
			
											<table class="table table-striped table-bordered table-hover"
												id="tablaDesgloseModal">
												<thead>
													<tr>
														<th><%=etiqueta.LINEA%></th>
														<th><%=etiqueta.SUBTOTAL%></th>
														<th><%=etiqueta.LOCACION%></th>
														<th><%=etiqueta.CUENTA_CONTABLE_LBL%></th>
														<th><%=etiqueta.CONCEPTO%></th>
														<c:if test="${tipoSolicitud eq 99}">
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
																	path="facturaDesgloseList[${status.index}].locacion.idLocacion"
																	cssClass="form-control locaciones">
																	<option value="-1"><%=etiqueta.SELECCIONE%></option>
																	<c:forEach items="${lcPermitidas}" var="llist">
																		<option
																			${facturaDesgloseList.locacion.idLocacion == llist.idLocacion ? 'selected' : ''}
																			value="${llist.idLocacion}">${llist.descripcion}
																		</option>
																	</c:forEach>
																</form:select></td>
			
															<td><form:select
																	path="facturaDesgloseList[${status.index}].cuentaContable.idCuentaContable"
																	cssClass="form-control ccontable">
																	<option value="-1"><%=etiqueta.SELECCIONE%></option>
																	<c:forEach items="${ccPermitidas}" var="cclist">
																		<option
																			${facturaDesgloseList.cuentaContable.idCuentaContable == cclist.idCuentaContable ? 'selected' : ''}
																			value="${cclist.idCuentaContable}">${cclist.descripcion}(${cclist.numeroCuentaContable})
																		</option>
																	</c:forEach>
																</form:select></td>
			
															<td><form:input maxlength="500" cssClass="form-control conceptogrid"
																	path="facturaDesgloseList[${status.index}].concepto"
																	value="${facturaDesgloseList.concepto}" /></td>
			
															<c:if test="${tipoSolicitud eq 99}">
																<td><form:select
																        onchange="alSeleccionarAIDModal(this.id)"
																		path="facturaDesgloseList[${status.index}].aid.idAid"
																		cssClass="form-control trackAsset">
																		<option value="-1"><%=etiqueta.SELECCIONE%></option>
																		<c:forEach items="${listAids}" var="aidlist">
																			<option
																				${facturaDesgloseList.aid.idAid == aidlist.idAid ? 'selected' : ''}
																				value="${aidlist.idAid}">${aidlist.descripcion}
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
																	class="btn btn-danger removerFilaDesglose"><%=etiqueta.REMOVER%></button></td>
			
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<table style="width: 40%;"
												class="table table-striped table-bordered table-hover"
												id="tablaDesgloseValores">
												<thead>
													<tr>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td style="width: 50%"><%=etiqueta.SALDO_COMPROBADO%></td>
														<td>
															<input value="0.00" class="form-control currencyFormat" id="sbt_modal"
																type="text" disabled="disabled">
														</td>
													</tr>
			
													<tr>
														<td style="width: 50%"><%=etiqueta.SALDO_PENDIENTE%></td>
														<td><input value="0.00" class="form-control currencyFormat"
															id="restante_modal" type="text" disabled="disabled"></td>
													</tr>
			
													<tr>
														<td style="width: 50%"><%=etiqueta.SUBTOTAL%>:</td>
														<td><input value="${facturaConXML.strSubTotal}"
															class="form-control currencyFormat" id="sppc_modal" type="text"
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
			
			
				</div>
			</div>
			
			<div class="modal-footer">
					 <a href="#" data-dismiss="modal"  id="cancelar_nomercancias" class="btn btn-default" role="button"><%=etiqueta.CANCELAR%></a>
			</div>
		</div>

	</div>
</div>