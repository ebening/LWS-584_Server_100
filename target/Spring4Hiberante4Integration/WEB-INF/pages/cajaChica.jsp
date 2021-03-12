<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<jsp:include page="template/head.jsp" />

<style>

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

.form-group-parse {
	margin-bottom: 3px;
}

.trackAsset {
	
}

.removerFila {
	
}

.linea {
	font-weight: bold;
}

.deleterow {
	cursor: pointer
}

</style>
</head>

<body>

	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />,

		<!-- Page Content -->
		<form:form id="saveSol" method="post" enctype="multipart/form-data"
			action="saveSolicitud" modelAttribute="solicitudDTO">
			<form:hidden disabled="true" value="${solicitudDTO.idTipoSolicitud}" path="idTipoSolicitud"/>
			<div id="page-wrapper">

				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header"><%= etiqueta.NUEVA_SOLICITUD_CAJA_CHICA %></h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->

				<div style="display: none;" id="error-alert"
					class="alert alert-danger fade in">
					<strong><span id="error-head"><%=etiqueta.ERROR%></span></strong> <span
						id="error-body"><%=etiqueta.COMPLETE%></span>
				</div>


				<div style="display: none;" id="ok-alert"
					class="alert alert-success fade in">
					<strong> <span id="ok-head"> </span>
					</strong> <span id="ok-body"> <%=etiqueta.SOLICITUD_GUARDADA%>
					</span>
				</div>

				<div class="panel panel-default">
					<div style="height: 54px;" class="panel-heading">
						<c:if test="${(solicitudDTO.estatusSolicitud eq  1 or solicitudDTO.estatusSolicitud eq 4) && configCorrecta}">						<button
							id="adjuntar_archivo"  style="margin-left: 10px; float: right;" 
							type="button" class="btn btn-primary" data-toggle="modal" 
							data-target="#anexarDoc">
							<%=etiqueta.DOCUMENTO_SOPORTE%>
						</button>
						</c:if>	
										
						<c:if test="${solicitudDTO.estatusSolicitud > 1 && configCorrecta}">
						<button onclick="verDetalleEstatus(${solicitudDTO.idSolicitudSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>
						
						<c:if test="${(solicitudDTO.estatusSolicitud eq  1 or solicitudDTO.estatusSolicitud eq 4) && configCorrecta}">
						<button 
							onclick="enviarSolicitudProceso()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%>
						</button>
						</c:if>
						
						<c:if test="${solicitudDTO.estatusSolicitud eq 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							${solicitudDTO.idSolicitudSession > 0 ? '' : 'disabled'}
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%>
						</button>
						</c:if>
						
						<c:if test="${(solicitudDTO.estatusSolicitud eq null or solicitudDTO.estatusSolicitud eq  1 or solicitudDTO.estatusSolicitud eq 4) && configCorrecta}">
							<button id="save_button" onclick="return valTodo();"
								style="margin-left: 10px; float: right;" type="submit"
								class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
					</div>
					
					<!-- FORM SOLICITUD -->
					<div class="panel-body">
					
						<!-- ROW 1 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label id="beneficiarioLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.BENEFICIARIO%>:</label>
										<form:select path="beneficiario" 
											id="beneficiario"
										cssClass="form-control noMercanciasTooltips" disabled="true">
											<option value="-1"><%=etiqueta.SIN_BENEFICIARIO%></option>
											<c:forEach items="${lstUsrBenef}" var="lstUsrBenef">
												<option
												${solicitudDTO.beneficiario == lstUsrBenef.idUsuario ? 'selected' : ''}
												value="${lstUsrBenef.idUsuario}">${lstUsrBenef.nombre}
																	${lstUsrBenef.apellidoPaterno}
																	${lstUsrBenef.apellidoMaterno}
											</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
					
							

						<!-- ROW 2 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label id="companiaLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.COMPANIA%>:</label>
									<form:select path="compania.idcompania" id="compania"
										cssClass="form-control noMercanciasTooltips" disabled="true">
										<c:forEach items="${lstCompanias}" var="lstCompanias">
											<option value="${lstCompanias.idcompania}">${lstCompanias.descripcion}
											</option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label id="formaPagoLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.FORMA_PAGO%>:</label>
										<form:select path="formaPago.idFormaPago" id="formaPago"
											cssClass="form-control noMercanciasTooltips">
											<c:forEach items="${lstFormaPago}" var="lstFormaPago">
												<option 
													${solicitudDTO.formaPago.idFormaPago == lstFormaPago.idFormaPago ? 'selected' : ''}
													value="${lstFormaPago.idFormaPago}">${lstFormaPago.descripcion}
												</option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
						</div>

						<!-- ROW 3 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label id="locacionLabel" data-toggle="tooltip" data-trigger="hover" data-placement="right" title=""><%=etiqueta.LOCACION%>:</label>
									<form:select path="locacion.idLocacion" id="locacion"
										disabled="true" cssClass="form-control noMercanciasTooltips">
										<option value="-1"><%=etiqueta.SIN_LOCACION%></option>
										<c:forEach items="${solicitudDTO.lcPermitidas}" var="lcPermitidas">
											<option 
												${solicitudDTO.locacion.idLocacion == lcPermitidas.idLocacion ? 'selected' : ''}
												value="${lcPermitidas.idLocacion}">${lcPermitidas.numeroDescripcionLocacion}
											</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>

						<!-- ROW 4 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label id="monedaLabel" data-toggle="tooltip"
										data-trigger="hover" data-placement="right" title=""><%=etiqueta.MONEDA%>:</label>
									<form:select path="moneda.idMoneda" id="moneda"
										cssClass="form-control noMercanciasTooltips">
										<option value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${lstMonedas}" var="lstMonedas">
											<option
												${solicitudDTO.moneda.idMoneda == lstMonedas.idMoneda ? 'selected' : ''}
												value="${lstMonedas.idMoneda}">${lstMonedas.descripcion}
											</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>

						<!-- ROW 5 -->
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label style="margin-top: 5px;" for="comment"><%=etiqueta.CONCEPTO_DEL_GASTO%>:</label>
									<form:textarea path="concepto" style="margin-bottom:10px;"
										value="${reembolso.concepto}" cssClass="form-control" rows="5"
										id="conceptoSol" maxlength="500"></form:textarea>
								</div>
							</div>
						</div>

					</div>
					<!-- FORM SOLICITUD -->

					<!-- FORM FACTURAS -->
					<div style="height: 54px;" class="panel-heading">
						<h4><%=etiqueta.ADJUNTA_ARCHIVOS%>:</h4>
					</div>

					<div id=formFactura>
						<div class="">
							<div style="margin-left: 0px;" class="row">
							
							<div class="row show-grid">
							
								<div class="col-lg-12">
									<div style="margin: 5px 0;" class="row show-grid col-md-12">
										<div class=".col-xs-6">
											<div class="form-group">
												<label><%=etiqueta.CUENTA_CON_COMPROBANTE_FISCAL%></label>
												<div class="radio">
													<label><input id="compTrue" type="radio"
														name="optradio" value="true" checked><%=etiqueta.SI%></label>
													<label><input id="compFalse" type="radio"
														name="optradio" value="false"><%=etiqueta.NO%></label>
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
								
                                <div class="row">
                                	
                                <div class="col-md-5">
                                    <div class="col-md-12">
                                       <div class="form-group">
												<label><%=etiqueta.ADJUNTAR_PDF%></label> 
												<input ${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''} id="pdf" name="file" type="file" style="width: 100%">
												<label class="file-selected"></label> 
											</div>
                                    </div>
                                    <div class="col-md-4">
                                    </div>
                                    <div class="col-md-4"></div>
                                </div>
                                
                                 <div class="col-md-7">
                                    <div class="col-md-6">
                                     	<div class="form-group">
												<label><%=etiqueta.ADJUNTAR_XML%></label> <input
													${facturaConXML.idSolicitudSession > 0 ? 'disabled' : ''}
													id="file" name="file" type="file" style="width: 100%">
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
                                
                                </div>
                                
                                </div>
                                <div class="col-md-4">
                                </div>
                            </div>
							
							

							</div>
							<!-- /.row (nested) -->
						</div>


						<!-- /.panel-body -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 id="headerFactura"><%=etiqueta.REVISA_INFORMACION_FACTURA%>:</h4>
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
														<form:input style="display:none;" id="proveedorLibre" path=""
															cssClass="form-control" disabled="true"
															value="${reembolso.proveedorLibre}" maxlength="100" />
													</div>
												</div>
												<div id="fieldRFC" class="col-xs-6 col-md-4 fieldFiscal">
													<div class="form-group-parse">
														<label><%=etiqueta.RFC%>:</label>
														<form:input id="rfcEmisor" path=""
															cssClass="form-control" disabled="false" style="width:146px"
															value="${reembolso.rfcEmisor}" />
													</div>
												</div>
												<div class="col-xs-6 col-md-4"></div>
											</div>
											
											
											
																						<div class="row show-grid">
												<div class="col-xs-5 col-md-2">
													<div class="form-group-parse">
														<label><%=etiqueta.FOLIO%>:</label>
														<form:input id="folio" path="" style="min-width:120px"
															cssClass="alfanumerico form-control" disabled="false"
															value="${reembolso.folio}" maxlength="10" />
													</div>
												</div>
												<div class="col-xs-1 col-md-2 fieldFiscal">
													<div class="form-group-parse">
														<label><%=etiqueta.SERIE%>:</label>
														<form:input id="serie" path=""
															cssClass="form-control" disabled="false"
															value="${reembolso.serie}" />
													</div>
												</div>
												
												<div class="col-xs-4 col-md-2">
													<div id="fecha-sandbox" class="form-group-parse">
														<label><%=etiqueta.FECHA%>:</label>
														<form:input readonly="true" id="fecha" path="" 
															cssClass="form-control" disabled="false"
															style="text-align:center"
															value="${reembolso.fecha}" />
													</div>
												</div>
											</div>
											
											
											
											<div class="row show-grid fieldFiscal">
												<div class="col-xs-6 col-md-4">
													<div class="form-group-parse">
														<label><%=etiqueta.FOLIO_FISCAL%>:</label>
														<form:input id="folioFiscal" path=""
															class="form-control" disabled="false"
															value="${reembolso.folioFiscal}" />
													</div>
												</div>
											</div>
																						
											<div class="row show-grid">
												<div class="col-xs-12 col-md-2">
													<label><%=etiqueta.SUBTOTAL%>:</label>
													<div class="form-group-parse input-group">
														<span class="input-group-addon">$</span>
														<form:input id="subTotal" path=""
															cssClass="form-control currencyFormat" disabled="true"
															value="${reembolso.strSubTotal}" />
													</div>
												</div>
												
												<div class="col-xs-12 col-md-2 fieldFiscal" style="">
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
												
												<div class="col-xs-12 col-md-2 fieldFiscal" style="">												
													<label><%=etiqueta.IEPS%>:</label>
													<div class="form-group-parse input-group">													
														<span class="input-group-addon">$</span>
														<form:input path="" id="ieps" disabled="false"
															cssClass="form-control currencyFormat" value="${reembolso.ieps}" />
														<form:input type="hidden" path="" id="tasaIeps" disabled="false"
															cssClass="form-control" value="${reembolso.tasaIeps}" />
													</div>
												</div>												
												
												<div class="col-xs-12 col-md-2">												
													<label><%=etiqueta.MONEDA%>:</label>
													<div class="form-group">
														<form:select path="moneda.idMoneda" disabled="true" style="height: 24px;"
															id="monedaDet" cssClass="form-control">
															<option value="-1"><%=etiqueta.SELECCIONE%></option>
															<c:forEach items="${lstMonedas}" var="lstMonedas">
																<option value="${lstMonedas.idMoneda}">${lstMonedas.descripcion}
																</option>
															</c:forEach>
														</form:select>
													</div>
												</div>
											</div>

											<div class="row show-grid fieldFiscal">
												<div id="fieldRetenciones" class="col-xs-12 col-md-2">
													<div style="margin-bottom: 0px; margin-top: 34px;"
														class="form-group-parse">														
														<div class="checkbox fix-check">
															<form:checkbox disabled="false" id="conRetenciones" path=""
																value="" /><label><%=etiqueta.CON_RETENCIONES%></label>
														</div>
													</div>
												</div>
												
							                    <div class="col-xs-3 col-md-2">							                    
													<label><%=etiqueta.IVA_RETENIDO%>:</label>
													<div class="form-group-parse input-group">													
														<span class="input-group-addon">$</span>
														<form:input path="" id="iva_retenido"
															disabled="false" cssClass="form-control currencyFormat" value=""/>
													</div>
												</div>
											
												<div class="col-xs-3 col-md-2" style="">
													<label><%=etiqueta.ISR_RETENIDO%>:</label>
													<div class="form-group-parse input-group">
														<span class="input-group-addon">$</span>
														<form:input path="" id="isr_retenido"
															disabled="false" cssClass="form-control currencyFormat" value="" />
													</div>
												</div>
											</div>
										</div>

										<div class="row show-grid">
											<div class="col-md-4">
												<label><%=etiqueta.TOTAL%>:</label>
												<div class="form-group-parse input-group">
													<span class="input-group-addon">$</span>
													<form:input id="total" path="" class="form-control currencyFormat importe-total"
														onblur="setSbt()" disabled="false" value="${reembolso.total}" />
												</div>
											</div>
											<div class="col-xs-6 col-md-4"></div>
											<div class="col-xs-6 col-md-4"></div>
										</div>

									</div>
								</div>
							</div>
							<!-- /.row (nested) -->
						</div>
					</div>



				</div>

				<!-- PANEL INFERIOR -->

				<div style="border-color: #fff;" class="panel panel-default">

					<div style="height: 54px" class="panel-heading">
						<h4 style="float: left;"><%=etiqueta.INGRESE_INFORMACION_DESGLOSE_CONTABLE%>:</h4>
						<button onclick="addRowToTable()"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-primary"><%=etiqueta.AGREGAR%></button>
					</div>
					<div class="panel-body tableGradient">

						<div class="dataTable_wrapper scrollable">

							<table class="table table-striped table-bordered table-hover"
								id="tablaDesglose">
								<thead>
									<tr>
										<th style="width: 2%;"><%=etiqueta.LINEA%></th>
										<th style="width: 10%;"><%=etiqueta.SUBTOTAL%></th>
										<th><%=etiqueta.LOCACION%></th>
										<th><%=etiqueta.CUENTA_CONTABLE%></th>
										<th><%=etiqueta.FACTURA_FOLIO%></th>
										<th><%=etiqueta.CONCEPTO%></th>
										<th style="width: 8%;" id="thIva"><%=etiqueta.IVA%></th>
										<th style="width: 8%;" id="thIeps"><%=etiqueta.IEPS%></th>
										<th style="width: 5%;"></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${solicitudDTO.lstFactDto}"
										var="lstFactDto" varStatus="status">
										<tr>
											<td align="center" class="linea">${status.count}</td>
											<td><form:input onchange="sumSbt()"
													cssClass="form-control subtotales currencyFormat"
													path="lstFactDto[${status.index}].subTotal"
													value="${lstFactDto.subTotal}" disabled="true"/></td>

											<td><form:select
													id="lstFactDto[${status.index}].locacion"
													onChange="locacionOnChange.call(this)"
													name="lstFactDto[${status.index}].locacion"
													path="lstFactDto[${status.index}].locacion"
													disabled="${!solicitudDTO.esLocacionCSC}"
													cssClass="form-control">
													<option value="-1"><%=etiqueta.SELECCIONE%></option>
													<c:forEach items="${solicitudDTO.lcPermitidas}" var="llist">
														<option 
															${solicitudDTO.lstFactDto[status.index].locacion == llist.idLocacion ? 'selected' : ''}
															value="${llist.idLocacion}">${llist.numeroDescripcionLocacion}
														</option>
													</c:forEach>
												</form:select></td>

											<td class="ccontable"><form:select
												    id="lstFactDtoCC${status.index}"
													onChange="ccOnChange.call(this)"
													name="lstFactDto[${status.index}].cuentaContable"
													path="lstFactDto[${status.index}].cuentaContable"
													disabled="${!solicitudDTO.lstFactDto[status.index].conCompFiscal}"
													cssClass="form-control ccontable">
													<option value="-1"><%=etiqueta.SELECCIONE%></option>
													
													<c:if test="${solicitudDTO.lstFactDto[status.index].cuentaContable ne ccNoComprobante.idCuentaContable}">
														<c:forEach items="${solicitudDTO.ccPermitidas}" var="cclist">
															<option 
																${solicitudDTO.lstFactDto[status.index].cuentaContable == cclist.idCuentaContable ? 'selected' : ''}
																value="${cclist.idCuentaContable}">${cclist.numeroDescripcionCuentaContable}
															</option>
														</c:forEach>
													</c:if>

													<c:if test="${solicitudDTO.lstFactDto[status.index].cuentaContable eq ccNoComprobante.idCuentaContable}">
														<option selected
															value="${ccNoComprobante.idCuentaContable}">${ccNoComprobante.numeroDescripcionCuentaContable}
														</option>
													</c:if>
													
												</form:select></td>

											<td>
												<c:if test="${lstFactDto.razonSocial ne null && lstFactDto.serie ne null}">
													${lstFactDto.razonSocial} - ${lstFactDto.serie} - ${lstFactDto.folio}
												</c:if>
												<c:if test="${lstFactDto.razonSocial eq null && lstFactDto.serie eq null}">
													${lstFactDto.folio}
												</c:if>
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

											<td><button type="button"
													class="btn btn-danger removerFila"><%=etiqueta.REMOVER%></button></td>

										</tr>
									</c:forEach>
									<tr class="tr-totales">
										<td style="width: 5%"><%=etiqueta.TOTALES%>:</td>
										<td>
											<input value="${solicitudDTO.subTotal}" class="form-control currencyFormat" id="sbt" type="text" disabled="disabled">
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td id="tdIvaTot">
											<input value="0.00" class="form-control currencyFormat" id="ivatot" type="text" disabled="disabled">
										</td>
										<td id="tdIepsTot">
											<input value="0.00" class="form-control currencyFormat" id="iepstot" type="text" disabled="disabled">
										</td>
										<td></td>
									</tr>
								</tbody>
							</table>
							
<!-- 							<table class="table table-striped table-hover" id="tblSumatorias"> -->
<!-- 								<tbody> -->
<!-- 									<tr> -->
<%-- 										<td style="width: 5%"><%=etiqueta.TOTALES%>:</td> --%>
<!-- 										<td> -->
<%-- 											<input value="${solicitudDTO.subTotal}" class="form-control currencyFormat" id="sbt" type="text" disabled="disabled"> --%>
<!-- 										</td> -->
<!-- 										<td style="width: 50%"></td> -->
<!-- 										<td id="tdIvaTot"> -->
<!-- 											<input value="0.00" class="form-control currencyFormat" id="ivatot" type="text" disabled="disabled"> -->
<!-- 										</td> -->
<!-- 										<td id="tdIepsTot"> -->
<!-- 											<input value="0.00" class="form-control currencyFormat" id="iepstot" type="text" disabled="disabled"> -->
<!-- 										</td> -->
										
<!-- 										<td style="width: 101px" ></td> -->
										
<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
							
							<table class="table">
								<tbody>
									<tr>
									    <td style="width: 68%"></td>
										<td style="width: 12%"><%=etiqueta.REEMBOLSO_TOTAL%>:</td>
										<td style="width: 14%">
											<form:input value="${solicitudDTO.montoTotal}"
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
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO_CANCELAR%></a>
						<button style="display: none;" onclick="resetSolicitud()"type="button" id="cambiar_beneficiario_button" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_BENEFICIARIO%>
						</button>
						<button style="display: none;" onclick="resetSolicitud()"type="button" id="cambiar_solicitante_button" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_SOLICITANTE%>
						</button>
						<button style="display: none;" onclick="resetSolicitud()" type="button" id="cambiar_moneda_button" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_MONEDA%>
						</button>
						<button style="display: none;" onclick="resetSolicitud()" type="button" id="cambiar_locacion_button" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_LOCACION%>
						</button>
						<button 
							type="button" id="cancelCambio" onclick="cancelCambioSol()" class="btn btn-default" data-dismiss="modal"><%=etiqueta.CANCELAR%>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal Warning cambio tipo comprobante -->
		<div id="modalConfirm" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<h4><%=etiqueta.MENSAJE_INFORMACION_SIN_GUARDAR%></h4>
					</div>
					<div class="modal-footer">
						<button type="button" id="cambiarTipoComp" class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO%></button>
						<button type="button" onclick="cancelModal()" class="btn btn-default" 
						data-dismiss="modal"><%=etiqueta.CANCELAR%></button>
					</div>
				</div>
	
			</div>
		</div>
		
		<!-- Ventana modal anexar documento soporte -->
		<div class="modal fade" id="anexarDoc" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<form:form id="anexarDocumentoForm" enctype="multipart/form-data"
					cssClass="form-horizontal" modelAttribute="solicitudArchivo"
					method="post" action="saveSolicitudDocument">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title"><%=etiqueta.DOCUMENTO_SOPORTE%>
								<img id="loader-lowes" style="display: none;"
									src="${pageContext.request.contextPath}/resources/images/loader.gif" />
							</h4>
						</div>

						<div class="modal-body">

							<div id="errorMsg" style="display: none;"
								class="alert alert-warning">
								<strong> <span id="error-head-files"><%=errorHead%></span>
								</strong> <span id="error-body-files"><%=errorBody%></span>
							</div>

							<div class="form-group">
								<div class="control-label col-xs-4">
									<label><%=etiqueta.ANEXAR_DOCUMENTO_SOPORTE%></label><br />
								</div>
								<div class="col-xs-7">
									<form method="post" enctype="multipart/form-data"
										action="singleSave">

										<input id="file-sol" type="file" name="file-sol"> <br />

										<button onclick="anexarDoc()"
											style="margin-left: 10px; float: right;" type="button"
											class="btn btn-default"><%=etiqueta.ADJUNTAR_ARCHIVO%></button>

										<img id="loaderXML" style="display: none;"
											src="${pageContext.request.contextPath}/resources/images/loader.gif" />
									</form>
								</div>
							</div>

							<div class="panel-body">
								<div class="dataTable_wrapper">
									<table class="table table-striped table-bordered table-hover"
										id="tablaAid">
										<thead>
											<tr>
												<th><%=etiqueta.DOCUMENTO%></th>
												<th width="20%"><%=etiqueta.ELIMINAR%></th>
											</tr>
										</thead>
										<tbody id="tablaDoc">
											<c:forEach items="${solicitudArchivoList}" var="salist">
												<tr class="odd gradeX">
													<td><c:out value="${salist.archivo}" /></td>
													<td class="center">
														<button onclick="eliminar(${salist.idSolicitudArchivo})"
															style="height: 22px;" type="button"
															class="btn btn-xs btn-danger">
															<span class="glyphicon glyphicon-trash"></span>
														</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- /.table-responsive -->
							</div>
						</div>
					</div>
				</form:form>

			</div>
		</div>


	
        </div>
		<jsp:include page="template/includes.jsp" />
		<jsp:include page="modalConsultarAutorizacion.jsp" />
		
		
			<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/locales/bootstrap-datepicker.es.min.js"
		charset="UTF-8"></script>
		
	<script>
		var HEADER_CON_XML = '<%=etiqueta.REVISA_INFORMACION_FACTURA%>:'
		var HEADER_SIN_XML = '<%=etiqueta.INGRESE_INFORMACION_FACTURA%>:'
		var TIPO_SOLICITUD_REEMBOLSO = '<%=etiqueta.SOLICITUD_REEMBOLSOS%>'
		var TIPO_SOLICITUD_CAJA_CHICA = '<%=etiqueta.SOLICITUD_CAJA_CHICA%>'
		var url_server = null;
		var monPesos = '<%=etiqueta.ID_PESOS%>';
		var fPagoTrans = '<%=etiqueta.ID_TRANSFERENCIA%>';
		var ATENCION =  '<%=etiqueta.ATENCION%>';
		var ARCHIVO_ANEXADO =  '<%=etiqueta.ARCHIVO_ANEXADO%>';
		var ERROR = '<%=etiqueta.ERROR%>';
		var EXTENSION_INVALIDA = '<%=etiqueta.EXTENSION_INVALIDA%>';
		var EXTENSION_INVALIDA_COMP = '<%=etiqueta.EXTENSION_INVALIDA_COMP%>';
		var ARCHIVO_NO_ANEXADO = '<%=etiqueta.ARCHIVO_NO_ANEXADO%>';
		var ARCHIVO_VACIO = '<%=etiqueta.ARCHIVO_VACIO%>';
		var ARCHIVO_NO_SELECCIONADO = '<%=etiqueta.ARCHIVO_NO_SELECCIONADO%>';
		var ARCHIVO_ELIMINADO = '<%=etiqueta.ARCHIVO_ELIMINADO%>';
		var ARCHIVO_NO_ELIMINADO = '<%=etiqueta.ARCHIVO_NO_ELIMINADO%>';
		var ERROR_DEPENDENCIAS = '<%=etiqueta.ERROR_DEPENDENCIAS%>';
		var ERROR_DELETE = '<%=etiqueta.ERROR_DELETE%>';
		var TAMANO_NO_PERMITIDO = '<%=etiqueta.TAMANO_NO_PERMITIDO%>';
		var COMPLETE = '<%=etiqueta.COMPLETE%>';
		var PERDERA_INFORMACION = '<%=etiqueta.PERDERA_INFORMACION%>';
		var CAPTURE_SUBTOTAL = '<%=etiqueta.CAPTURE_SUBTOTAL%>';
		var ACTUALIZACION = '<%=etiqueta.ACTUALIZACION%>';
		var INFORMACION_ACTUALIZADA = '<%=etiqueta.INFORMACION_ACTUALIZADA%>';
		var NUEVA_SOLICITUD = '<%=etiqueta.NUEVA_SOLICITUD%>';
		var SOLICITUD_CREADA = '<%=etiqueta.SOLICITUD_CREADA%>';
		var SALDO_PENDIENTE_CERO = '<%=etiqueta.SALDO_PENDIENTE_CERO%>';
		var DESGLOSE_MINIMO = '<%=etiqueta.DESGLOSE_MINIMO%>';
		var ESTADO_SOLICITUD = '<%=etiqueta.ESTADO_SOLICITUD%>';
		var CANCELADA = '<%=etiqueta.CANCELADA%>';
		var MENSAJE_DIALOGO_CON_XML = '<%=etiqueta.MENSAJE_DIALOGO_CON_XML%>';
		var NOXML = '<%=etiqueta.NOXML%>';
		var ENVIADA_AUTORIZACION = '<%=etiqueta.ENVIADA_AUTORIZACION%>';
		var VALIDADA_AUTORIZACION = '<%=etiqueta.VALIDADA_AUTORIZACION%>';
		var FACTURA_VALIDA = '<%=etiqueta.FACTURA_VALIDA%>';
		var MENSAJE_CAMBIO_SOLICITANTE_NOXML = '<%=etiqueta.MENSAJE_CAMBIO_SOLICITANTE%>';
		var MENSAJE_CAMBIO_BENEFICIARIO = '<%=etiqueta.MENSAJE_CAMBIO_BENEFICIARIO%>';
		var MENSAJE_CAMBIO_MONEDA = '<%=etiqueta.MENSAJE_CAMBIO_MONEDA%>';
		var MENSAJE_CAMBIO_LOCACION = '<%=etiqueta.MENSAJE_CAMBIO_LOCACION%>';
		var MENSAJE_CANCELACION_NOXML = '<%=etiqueta.MENSAJE_CANCELACION_NOXML%>';
		var ESPECIFICA_SOLICITANTE = '<%=etiqueta.ESPECIFICA_SOLICITANTE%>'
		var DIF_MONEDA = '<%= etiqueta.DIF_MONEDA%>'
		var DIF_RAZON_SOCIAL = '<%= etiqueta.DIF_RAZON_SOCIAL%>'
		var NOPROVEEDOR = '<%= etiqueta.NOPROVEEDOR%>'
		var ERROR_DOCUMENTO_CARGADO = '<%= etiqueta.ERROR_DOCUMENTO_CARGADO%>'
		var idSolicitudGlobal = '${solicitudDTO.idSolicitudSession}';
		var idUsuarioSolicitante = '${solicitudDTO.usuarioSolicitante.idUsuario}';
		var idStatus = '${solicitudDTO.estatusSolicitud}';
		var isModificacion = '${solicitudDTO.modificacion}';
		var isCreacion = '${solicitudDTO.creacion}';
		var configCorrecta = '${configCorrecta}';
		var VALIDA_ARCHIVO_PDF = '<%= etiqueta.VALIDA_ARCHIVO_PDF%>';

	</script>
	<!-- Scripts especificos ....  -->
	<script>
	  $(document).ready(function(){
		  url_server = '${pageContext.request.contextPath}';
		  fileUploadedName('#pdf');
		  fileUploadedName('#file');
	  });
	  var idSolicitud = '${solicitudDTO.idSolicitudSession}';
	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/reembolsos.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/manejarSolicitante.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
</body>
</html>
