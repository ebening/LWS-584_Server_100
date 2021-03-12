<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%Etiquetas etiqueta = new Etiquetas("es");%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <jsp:include page="template/head.jsp" />
</head>

<body>


	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">
		    <form:form method="post" enctype="multipart/form-data"
			action="saveComprobacionAnticipoViaje" modelAttribute="comprobacionAnticipoViajeDTO">
				<form:hidden cssClass="enableInput" value="${comprobacionAnticipoViajeDTO.idSolicitudSession}" path="idSolicitudSession" />
				<!-- Título de la sección ----------------------- -->
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<%=etiqueta.COMPROBACION_ANTICIPO_DE_GASTOS_DE_VIAJE%>
							</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
				
				<!-- Alertas ----------------------- -->
				<div style="display: none;" id="error-alert" class="alert alert-danger fade in">
					<strong><span id="error-head"><%= etiqueta.ERROR %>:</span></strong>
					<span id="error-body"><%= etiqueta.COMPLETE %></span>
				</div>
				<div style="display: none;" id="ok-alert" class="alert alert-success fade in">
					<strong> <span id="ok-head"> </span>
					</strong> <span id="ok-body"><%= etiqueta.SOLICITUD_GUARDADA %>
					</span>
				</div>
			
				<!--botones de cabecera -->
				<div class="panel panel-default" id="panel-anticipo">
					<div class='blocker' style='position: absolute; background-color: white;z-index: 10;display: none;'></div>
					<div style="height: 54px;" class="panel-heading">
					   <c:if test="${idEstadoSolicitud != 7}">
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
							<button id="adjuntar_archivo"
								style="margin-left: 10px; float: right;" type="button"
								class="btn btn-primary" data-toggle="modal"
								data-target="#anexarDoc">
								<%=etiqueta.DOCUMENTO_SOPORTE%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud > 1}">
					   <button onclick="verDetalleEstatus(${comprobacionAnticipoViajeDTO.idSolicitudSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>
						
						<c:if test="${(idEstadoSolicitud eq  1 or idEstadoSolicitud eq 4)}">
						<button 
							onclick="enviarSolicitudProceso()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%>
						</button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 0 || idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button id="save_button" onclick="return validaComprobacionAnticipoViaje();"
							style="margin-left: 10px; float: right;" type="submit"
							class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
						
						</c:if>
					</div>

					<div class="panel-body">
						<div id="panel-form-solicitud">
							<!-- ROW 1 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%= etiqueta.COMPANIA %>: </label> 
										<form:select  id="compania" path="compania.idcompania" class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${companiaList}" var="companiaList">
												<option ${companiaList.idcompania == comprobacionAnticipoViajeDTO.compania.idcompania ? 'selected' : ''}
												value="${companiaList.idcompania}">${companiaList.descripcion}</option>
											</c:forEach>
										</form:select>
									</div>
								</div>
	
								<div class="row">
									<div class="col-md-4">
									   <div class="form-group">
										<label><%= etiqueta.FORMA_PAGO %>:</label> 
										<form:select id="formaPago" path="formaPago.idFormaPago" class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${formaPagoList}" var="formaPagoList">
												<option ${formaPagoList.idFormaPago == comprobacionAnticipoViajeDTO.formaPago.idFormaPago ? 'selected' : ''}
												value="${formaPagoList.idFormaPago}">${formaPagoList.descripcion}</option>
											</c:forEach>
										</form:select>
									</div>
									</div>
								</div>
							</div>
							
							<!-- ROW 2 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%= etiqueta.LOCACION %>:</label> 
										<form:select onchange="" path="locacion.idLocacion" id="locacion" 
											class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${locacionesPermitidas}" var="locacion">
											  <option ${comprobacionAnticipoViajeDTO.locacion.idLocacion == locacion.idLocacion ? 'selected' : ''}
											   value="${locacion.idLocacion}">${locacion.descripcion}</option>
											</c:forEach>
											</form:select>
									</div>
								</div>
								<div class="col-md-8">
	
								</div>
							</div>
							
							<!-- ROW 3 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%= etiqueta.MONEDA %>:</label>
										 <form:select path="moneda.idMoneda" id="moneda" 
											class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${monedaList}" var="monedas">
												<option ${monedas.idMoneda == comprobacionAnticipoViajeDTO.moneda.idMoneda  ? 'selected' : ''}
												 value="${monedas.idMoneda}">${monedas.descripcion}</option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-8">
	
								</div>
							</div>
							
							
							<!-- ROW 4 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.DESTINO%>:</label>
										 
										 <form:select path="viajeDestino.idViajeDestino" id="destinos" class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${destinos}" var="destinos">
												<c:if test="${destinos.esOtro != 1}">
													<option ${destinos.idViajeDestino == comprobacionAnticipoViajeDTO.viajeDestino.idViajeDestino  ? 'selected' : ''} 
													value="${destinos.idViajeDestino}">${destinos.descripcion}</option>
												</c:if>
											</c:forEach>
											<c:forEach items="${destinos}" var="destinos">
												<c:if test="${destinos.esOtro == 1}">
													<option ${destinos.idViajeDestino == comprobacionAnticipoViajeDTO.viajeDestino.idViajeDestino  ? 'selected' : ''} 
													value="${destinos.idViajeDestino}" otro="true">${destinos.descripcion}</option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<div class="form-group ${comprobacionAnticipoViajeDTO.otroDestino eq null ? 'destino-oculto' : ''}">
											<label>Otro destino</label>
											<form:input maxlength="100" path="otroDestino" id="otroDestino" class="form-control" type="text" value="${otroDestino}" disabled="true"/>
										</div>
									</div>
								</div>
							</div>
							
							<!-- ROW 5 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.MOTIVO_DE_VIAJE%>:</label>
											<form:select path="viajeMotivo.idViajeMotivo" id="motivos" 
											class="form-control enableInput">
											<option value="-1"><%=etiqueta.SELECCIONE%></option>
											<c:forEach items="${motivos}" var="motivos">
												<c:if test="${motivos.esOtro != 1}">
													<option ${motivos.idViajeMotivo == comprobacionAnticipoViajeDTO.viajeMotivo.idViajeMotivo  ? 'selected' : ''}
													value="${motivos.idViajeMotivo}">${motivos.descripcion}</option>
												</c:if>
											</c:forEach>
											<c:forEach items="${motivos}" var="motivos">
												<c:if test="${motivos.esOtro == 1}">
													<option ${motivos.idViajeMotivo == comprobacionAnticipoViajeDTO.viajeMotivo.idViajeMotivo  ? 'selected' : ''}
													value="${motivos.idViajeMotivo}" otro="true">${motivos.descripcion}</option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group ${comprobacionAnticipoViajeDTO.otroMotivo eq null ? 'motivo-oculto' : ''}">
										<label><%=etiqueta.OTRO%> <%=etiqueta.MOTIVO_DE_VIAJE%></label>
										<form:input maxlength="100" path="otroMotivo" id="otroMotivo" class="form-control" type="text" value="${otroMotivo}" disabled="true"/>
									</div>
								</div>
							</div>
							
							<!-- ROW 6 -->
							<div class="row input-daterange">
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.FECHA_DE_SALIDA%>:</label>
										<form:input id="fecha-salida" path="fechaInicio" class="form-control enableInput" type="text" value="${comprobacionAnticipoViajeDTO.fechaInicio}"/>
									</div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.FECHA_DE_REGRESO%>:</label>
										<form:input id="fecha-regreso" path="fechaRegreso" class="form-control enableInput" type="text" value="${comprobacionAnticipoViajeDTO.fechaRegreso}"/>
									</div>
								</div>
								
							</div>
							
							<!-- ROW 7 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.NUMERO_DE_PERSONAS%>:</label>
										<form:input path="numeroPersonas" id="numero-personas" class="form-control numberFormat importe-total enableInput" 
											style="text-align:center; width:65px;" maxlength="2" type="number" value="${comprobacionAnticipoViajeDTO.numeroPersonas}"/>
									</div>
								</div>
							</div>		
							
							<!-- ROW 8 -->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label><%=etiqueta.IMPORTE_REEMBOLSO%>:</label>
										<div class="form-group input-group">
											<span class="input-group-addon">$</span>
											<form:input path="strImporte" id="importe-reembolso" class="form-control importe-total enableInput" type="text" value="${comprobacionAnticipoViajeDTO.importe}"/>
										</div>
									</div>
								</div>
							</div>	
							<!-- ROW 9 -->
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label><%=etiqueta.RESULTADO_DEL_VIAJE%>:</label>
										<form:textarea  maxlength="500" cssClass="form-control" value="${comprobacionAnticipoViajeDTO.resultadoViaje}" rows="5" id="concepto" path="resultadoViaje"/>
									</div>
								</div>
							</div>
							
							<!-- ROW 10 -->
							<div class="row">
								<div class="col-md-12">
									<div class="form-group col-xs-10 pregunta-check">
										<label style="top: 10px; position: relative;"><%=etiqueta.PREGUNTA_UNO%></label>
									</div>
									<div class="radio col-xs-2">
										<label class="radio-inline"><form:radiobutton id="preguntaExtranjero1Si" path="preguntaExtranjero1" value="true"/><%=etiqueta.SI%></label>
										<label class="radio-inline"><form:radiobutton id="preguntaExtranjero1No" path="preguntaExtranjero1" value="false"/><%=etiqueta.NO%></label>
									</div>
										
									<div class="form-group col-xs-10 pregunta-check">
										<label style="top: 10px; position: relative;"><%=etiqueta.PREGUNTA_DOS%></label>
									</div>
									<div class="radio col-xs-2" style="top: 0px; position: relative;">
										<label class="radio-inline"><form:radiobutton id="preguntaExtranjero2Si" path="preguntaExtranjero2" value="true"/><%=etiqueta.SI%></label>
										<label class="radio-inline"><form:radiobutton id="preguntaExtranjero2No" path="preguntaExtranjero2" value="false"/><%=etiqueta.NO%></label>
									</div>
										
									<%-- <div class="form-group img-rounded pregunta-check">
										<form:checkbox path="preguntaExtranjero2" value="" style="top: 12px; position: relative; margin-right: 9px" /><%=etiqueta.PREGUNTA_DOS%>
									</div> --%>
									
								</div>
							</div>
							
							<!-- ROW 11 -->
							<div class="row" style="margin-top:20px;">
								<div class="col-md-12 bloque-gastos">
									<div class="row">
										<div class="col-md-4 col-lg-4" style="overflow: auto; margin-bottom: 16px;">
											<div class="col-xs-12"><%=etiqueta.TRANSFIERE_A%>: ${nombreSolicitante}</div>
											<div class="col-xs-12" style="width:auto">
												<div class="importe-total" style="display: inline;">$</div>
												<div id="transfiere-a" style="display: inline;" class="importe-total moneyFormat">0</div>
											</div>
										</div>
										<div class="col-md-6" style="overflow: auto; margin-bottom: 6px;">
											<div class="col-xs-12"><%=etiqueta.TRANSFIERE_A%>: LOWES COMPANIES MEXICO</div>
											<div class="col-xs-12" style="width:auto">
												<div class="importe-total signo" style="display: inline;"></div>
												<div id="transfiere-lowes" style="display: inline;" class="importe-total moneyFormat">0</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-md-4 col-lg-2" style="overflow: auto; margin-bottom: 6px;">
											<div class="col-xs-12 transfiere-a"><%=etiqueta.DEPOSITO%></div>
											<div class="col-xs-12" style="width:auto">
												<div class="importe-total" style="display: inline;">$</div>
												<div id="transfiere-deposito" style="display: inline;" class="importe-total moneyFormat">${deposito.montoDeposito}</div>
											</div>
										</div>
										<div class="col-md-4" >
											<!-- Cuando tiene ID o IDA se mostrará la opción de cargar depósito -->
											<c:if test="${idSolicitud eq 0}">
												<button id="agrega-deposito" class="col-xs-5 col-md-5 btn btn-default " disabled="true"
													data-toggle="modal" data-target="#deposito" type="button">
													<%=etiqueta.DEPOSITO%>
												</button>
												<div class="col-xs-5 col-md-5 col-md-offset-1 col-xs-offset-2 btn btn-primary"onclick="agregaLineaAjax()"><%=etiqueta.AGREGAR%></div>
											</c:if>	
											<c:if test="${idSolicitud ne 0}">
												<button id="agrega-deposito" class="col-xs-5 col-md-5 btn btn-default" 
													data-toggle="modal" data-target="#deposito" type="button">
													<%=etiqueta.DEPOSITO%>
												</button>
												<div class="col-xs-5 col-md-5 col-md-offset-1 col-xs-offset-2 btn btn-primary"  onclick="agregaLineaAjax()"><%=etiqueta.AGREGAR%></div>
											</c:if>
											
													<!-- data-toggle="modal" data-target="#politica-aviso">Agregar</div> -->
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<!-- Tabla de líneas -->
						<div class="row" style="overflow-x:auto;">
							<div class="panel-body tableGradient">
							<div class="dataTable_wrapper scrollable">
								<table style="font-size: 90%;"
									class="table table-striped table-bordered table-hover display nowrap"
									id="gasto-viaje-desglose">
									<thead>
										<tr>
											<th style="width: 2%;"><%=etiqueta.LINEA%></th>
											<th></th>
											<th></th>
											<th><%=etiqueta.COL_FECHA_FACTURA%> / Comprobante</th>
											<th style="min-width:85px;"><%=etiqueta.FECHA_GASTO%></th>
											<th><%=etiqueta.COMERCIO%></th>
											<th><%=etiqueta.CONCEPTO%></th>
											<th><%=etiqueta.CIUDAD%></th>
											<th><%=etiqueta.SUBTOTAL%></th>
											<th><%=etiqueta.IVA%></th>
											<th><%=etiqueta.IEPS%></th>
											<th><%=etiqueta.OTROS_IMPUESTOS%></th>
											<th><%=etiqueta.TOTAL%></th>
											<th><%=etiqueta.LOCACION%></th>
											<th><%=etiqueta.CUENTA_CONTABLE%></th>
											<th><%=etiqueta.BLD%></th>
											<th><%=etiqueta.SEGUNDA_AUTORIZACION%></th>
											<th><%=etiqueta.DESGLOSAR%></th>
											<th><%=etiqueta.NUMERO_DE_PERSONAS%></th>
											<th><label style="width:60px"><%=etiqueta.IR_A_DESGLOSE%></label></th>
											
										</tr>
									</thead>
									<tbody id="gastos-viaje-data" class="lineas-table">
										<c:forEach items="${comprobacionAnticipoViajeDTO.comprobacionAntDesglose}"
											var="comprobacionAntDesglose" varStatus="status">
											
											<!-- Línea -->
											<tr class="gradeX table-row t-row">
												<td class="centro linea-linea">${status.count}</td>
												<td class="linea-eliminar"><button title="Eliminar" onclick="" style="height: 22px;" type="button" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-trash"></span></button></td>
												<!-- Factura / Comprobante -->
												<td class="linea-factura-comprobante completed">
													<div class="center" style="min-width:85px;">
														<c:if test="${comprobacionAntDesglose.conCompFiscal eq true}">
															Factura
														</c:if>
														<c:if test="${comprobacionAntDesglose.conCompFiscal eq false}">
															Comprobante
														</c:if>
													</div>
												</td>
												<!-- Fecha factura / comprobante -->
												<td class="linea-fecha-factura">${comprobacionAntDesglose.fecha_factura}</td>
												<!-- Fecha Gasto -->
												<td class="linea-fecha-gasto">
													<div class="date-grid-container form-group-parse">
														<input type="text" class="form-control" class="form-control" value="${comprobacionAntDesglose.fecha_gasto}"/>
													</div>
												</td>
												<!-- Comercio -->
												<td class="linea-comercio">${comprobacionAntDesglose.comercio}</td>
												<!-- Concepto -->
												<td class="linea-concepto">
												   <form:select path="comprobacionAntDesglose[${status.index}].viajeConcepto.idViajeConcepto"  style="min-width:120px;" class="form-control">
													   <option title="<%= etiqueta.SELECCIONE %>" value="-1"><%= etiqueta.SELECCIONE %></option>
														<c:forEach items="${viajesConceptoList}" var="conceptos">
															<option
																${comprobacionAnticipoViajeDTO.comprobacionAntDesglose[status.index].viajeConcepto.idViajeConcepto == conceptos.idViajeConcepto ? 'selected' : ''} 
																value="${conceptos.idViajeConcepto}">${conceptos.descripcion}
															</option>
														</c:forEach>
												   </form:select> 
												</td>
												<td class="linea-ciudad">
													<form:input path="" class="form-control centro" style="min-width:120px;" value="${comprobacionAntDesglose.ciudadTexto}"/>
												</td>
												<!-- Subtotal -->
												<td class="moneyFormat linea-subtotal">${comprobacionAntDesglose.subTotal}</td>
												<!-- IVA -->
												<td class="linea-iva">${comprobacionAntDesglose.iva}</td>
												<!-- IEPS -->
												<td class="linea-ips">${comprobacionAntDesglose.ieps}</td>
												<!-- Otros impuestos -->
												<td class="moneyFormat linea-otros-impuestos">${comprobacionAntDesglose.sumaOtrosImpuestos}</td>
												<!-- Total -->
												<td class="moneyFormat linea-total" style="font-weight:bold; min-width: 66px;">${comprobacionAntDesglose.total}</td>
												<!-- Locación -->
												<td class="linea-locacion">
													 <form:select path="comprobacionAntDesglose[${status.index}].locacion.idLocacion" 
													 				onchange="actualizarCuentas(this.id)" class="form-control" style="min-width:120px;">
														<option title="<%= etiqueta.SELECCIONE %>" value="-1"><%= etiqueta.SELECCIONE %>
															<c:forEach items="${locacionesPermitidas}" var="locaciones">
																<option
																	${comprobacionAnticipoViajeDTO.comprobacionAntDesglose[status.index].idLocacion == locaciones.idLocacion ? 'selected' : ''} 
																	value="${locaciones.idLocacion}">${locaciones.numeroDescripcionLocacion}
																</option>
															</c:forEach>
														</option>
													</form:select> 
												</td>
												<!-- Cuenta contable -->
												<td class="linea-cuenta-contable">
													<form:select path="comprobacionAntDesglose[${status.index}].cuentaContable.idCuentaContable"  class="form-control ccontable" style="min-width:120px;">
														<option title="<%= etiqueta.SELECCIONE %>" value="-1" disabled><%= etiqueta.SELECCIONE %>
															<c:forEach items="${ccPermitidas}" var="cuenta">
																<option
																	${comprobacionAnticipoViajeDTO.comprobacionAntDesglose[status.index].cuentaContable.idCuentaContable == cuenta.idCuentaContable ? 'selected' : ''} 
																	value="${cuenta.idCuentaContable}">${cuenta.numeroDescripcionCuentaContable}
																</option>
															</c:forEach>
														</option>
													</form:select> 
												</td>
												<!-- B/L/D -->
												<td class="linea-bld">
													<form:select path="comprobacionAntDesglose[${status.index}].viajeTipoAlimentos.idViajeTipoAlimentos" class="form-control" style="min-width:90px;">
														<option title="<%= etiqueta.SELECCIONE %>" value="-1" disabled><%= etiqueta.SELECCIONE %>
														<c:forEach items="${tipoAlimentosList}" var="tipoAlimentos">
															<option
																${comprobacionAnticipoViajeDTO.comprobacionAntDesglose[status.index].viajeTipoAlimentos.idViajeTipoAlimentos == tipoAlimentos.idViajeTipoAlimentos ? 'selected' : ''} 
																value="${tipoAlimentos.idViajeTipoAlimentos}">${tipoAlimentos.descripcion}
															</option>
														</c:forEach>
													</form:select>
												</td>
												<!-- 2da Aprobación -->
												<td class="centro linea-segunda-autorizacion">
													<input type="checkbox" value="" disabled <c:if test="${comprobacionAntDesglose.segundaAprobacion eq true}">checked</c:if>>
												</td>
												<!-- Desgloses -->
												<td class="centro linea-desglosar"><input type="checkbox" value="" <c:if test="${comprobacionAntDesglose.desglosar eq true}">checked</c:if>></td>
												<!-- No. de personas -->
												<td class="linea-numero-personas">
													<input class="form-control centro" name="${comprobacionAntDesglose.numeroPersonas}" value="${comprobacionAntDesglose.numeroPersonas}" <c:if test="${comprobacionAntDesglose.desglosar eq false}">disabled</c:if> />
												</td>
												<!-- Ir a desglose -->
												<td class="centro linea-ir-desglose">
													<c:if test="${comprobacionAntDesglose.desglosar eq true}">
														<button type="button" style="height: 22px; margin-right: 10%;" class="detalleDesglose"><span>Ir</span></button>
														<span class="glyphicon glyphicon-ok-circle palomita desglosado desglosado-completo" style="display: inline;"></span>
													</c:if>
													<c:if test="${comprobacionAntDesglose.desglosar eq false}">
														<button type="button" style="height: 22px; margin-right: 10%;" class="detalleDesglose" disabled><span>Ir</span></button>
														<span class="glyphicon glyphicon-ok-circle palomita desglosado" style="display: none;"></span>
													</c:if>
												</td>
											</tr>
										</c:forEach>
										<!-- Totales  -->
							          	<tr class="totales">
								            <td colspan="8" style="text-align:right">Totales:</td>
								            <td id="totalSubtotal" class="moneyFormat">0</td>
								            <td id="totalIva" class="moneyFormat">0</td>
								            <td id="totalIeps" class="moneyFormat">0</td>
								            <td id="totalOtrosImpuestos" class="moneyFormat">0</td>
								            <td ></td>
								        </tr>
								        <!-- Total reembolso  -->
								        <tr class="importe-total-reembolso">
							    	        <td colspan="9" style="border-bottom-color: #FFFFFF; border-left-color: white;"></td>
							        	    <td class="importe-total" colspan="3" style="text-align:right"><%=etiqueta.TOTAL_REEMBOLSO%>:</td>
							            	<td id="total-reembolso" class="importe-total moneyFormat" rowspan="1">0</td>
							          	</tr>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
							 
						</div>
						
						<!-- Fin de Tabla de líneas -->
						
					</div>


				</div> 
				<!-- Fin de Botones de cabecera -->
            
           
          <!--  
           Estructura de contenido.
           divs-bootstrap, revisar layouts.
          -->
    
           </form:form>
           <!-- Fin de formAnticipo -->
        </div>
        <!-- /#page-wrapper -->
        
    </div>
    	<!-- Modal Documentos de soporte -->
    	<jsp:include page="modalArchivosSolicitud.jsp" />
    	<!-- Modal de Captura Depósito -->
    	<jsp:include page="modalDeposito.jsp" />
    	<!-- Modal Cancelar -->
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
							<a onclick="cancelar_solicitud()" style="display: none;" href="#" id="cancelar_button"
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO_CANCELAR%>
						</a> <a style="display: none;" href="#"
							id="cambiar_solicitante_button"  class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_SOLICITANTE%></a> <a
							href="#" id="cambiarxml_button_cancelar"
							onclick="cerrar_ventana()" class="btn btn-default" role="button"><%=etiqueta.SALIR%></a> <a href="#" style="display: none;" id="solicitud_button_cancelar"
							class="btn btn-default" role="button"><%=etiqueta.CANCELAR%></a>

					</div>
				</div>

			</div>
		</div>
		
  <%-- <jsp:include page="modalConsultarAutorizacion.jsp" /> --%>
	<jsp:include page="modalPoliticaAviso.jsp" />
	<jsp:include page="modalFactura.jsp" />
	<jsp:include page="modalDesgloseComprobante.jsp" />
	<jsp:include page="modalComprobante.jsp" />

  <jsp:include page="template/includes.jsp" />
  
	<script>
	$(document).ready(function(){
		
		//$('.modalita').click();
		//anticipoViaje();
		startDatePicker();
		$('#fecha-salida').datepicker();
		$('#fecha-regreso').datepicker();
		
		$('.currencyFormat').number( true, 2 );
		$('.numberFormat').number( true,0 );
		$('.moneyFormat').number( true, 2 );
		
		
		
		$(".totalFormat", "#desglose-comprobante").number(true,2);
		
		//console.log($('.numberFormat').length);
		
		$('.sandbox-container input').change(function () {
		});
		
		function startDatePicker(){
			  $('.input-daterange').datepicker({
				  format: 'dd/mm/yyyy',
				    language: 'es',
					 autoclose: true,
					 todayHighlight: true,
			  });
		}
		
		$('.date-grid-container input').datepicker({
			  format: 'dd/mm/yyyy',
			  language: 'es',
			 // endDate: '+2d',
			  autoclose: true,
			  todayHighlight: true,
		  });

		$('.sandbox-container input').datepicker({
			  format: 'dd/mm/yyyy',
			  language: 'es',
			 // endDate: '+2d',
			  autoclose: true,
			  todayHighlight: true,
		  });
		
		resetNumber($("#importe-reembolso"));
		
		});
	
	/* V A L I D A - T O D O ------------------------------------*/
	
	
	
// function valTodo(){
// 			   $(window).unbind('beforeunload');
// 			   var error = false;
// 			   var idSolicitud = '${facturaConXML.idSolicitudSession}';
// 			   if(idSolicitudGlobal == 1){
// 			     $("#par").removeClass("errorx");
// 			     $("#id_compania_libro_contable").removeClass("errorx");
// 		        }
// 				// ------
// 			   var length = $("#tablaDesglose > tbody > tr").length;
			   
// 			   if(length > 0){
				   
// 			    $('.subtotales').each(function() {
// 			    	if($(this).val() == "" || $(this).val() == 0){
// 			    		$(this).addClass("errorx");
// 			    		error = true;
// 			    	}else{
// 			    		$(this).removeClass("errorx");
// 			    	}
// 				});
// 			 // ------
// 			    $('.locaciones').each(function() {
// 			    	if($(this).val() == -1){
// 			    		$(this).addClass("errorx");
// 			    		error = true;
// 			    	}else{
// 			    		$(this).removeClass("errorx");
// 			    	}
// 				});
			    
// 			 // ------
// 			    $('.ccontable').each(function() {
// 			    	if($(this).val() == -1){
// 			    		$(this).addClass("errorx");
// 			    		error = true;
// 			    	}else{
// 			    		$(this).removeClass("errorx");
// 			    	}
// 				});
// 			 // ------
// 			    $('.conceptogrid').each(function() {
// 			    	if($(this).val() == ""){
// 			    		$(this).addClass("errorx");
// 			    		error = true;
// 			    	}else{
// 			    		$(this).removeClass("errorx");
// 			    	}
// 				});
// 			 // ------
// 			   }
			   
// 			   //VALIDACIONES ----------------------------------------------------
// 			   if($("#solicitante").is(":checked")){
// 				   if($("#idSolicitanteJefe").val() == -1){
// 					   $("#idSolicitanteJefe").addClass("errorx");
// 					   error = true;
// 				   }else{
// 					   $("#idSolicitanteJefe").removeClass("errorx");
// 				   }
// 			   }
// 			// ------
// 			   if($("#moneda").val() == -1){
// 				   $("#moneda").addClass("errorx");
// 				   error = true;
// 			   }else{
// 				   $("#moneda").removeClass("errorx");
// 			   }
// 			// ------
// 			   if(tipoSolicitudGlobal == 0){
// 				   if(document.getElementById("file").files.length == 0){
// 					   $("#file").addClass("errorx");
// 					   error = true;
// 				   }else{
// 					   $("#file").removeClass("errorx");
// 				   }
				   
				   
// 				   if(document.getElementById("pdf").files.length == 0){
// 					   $("#pdf").addClass("errorx");
// 					   error = true;
// 				   }else{
// 					   $("#pdf").removeClass("errorx");
// 				   }
// 		      }
// 			// ------
			   
// 			   if($("#compania").val() == -1){
// 				   $("#validarXMLb").addClass("errorx");
// 				   error = true;
// 			   }else{
// 				   $("#validarXMLb").removeClass("errorx");
// 			   }
// 			// ------
			
// 			   //validarPDF
// 			  /*  if(validarPDF() == false){
				   
// 				   $("#pdf").addClass("errorx");
// 			   }else{
// 				   $("#pdf").removeClass("errorx");
// 			   } */
		
			   
// 			   if(tipoSolicitudGlobal == 1){
// 				if(idSolicitudGlobal == 0){   
// 					var ext = $('#pdf').val().split('.').pop().toLowerCase();
// 					if(ext != "pdf"){
// 					   error = true;
// 					   $("#pdf").addClass("errorx");
// 					}else{
// 					   $("#pdf").removeClass("errorx");
// 					}
// 				}
// 				// ------   
// 			   if($("#track_asset").is(":checked")){
				   
// 				   if($("#id_compania_libro_contable").val() == -1){
// 					   $("#id_compania_libro_contable").addClass("errorx");
// 					   error = true;
// 				   }else{
// 					   $("#id_compania_libro_contable").removeClass("errorx");
// 				   }
				   
// 				   if($("#par").val() == ""){
// 					   $("#par").addClass("errorx");
// 					   error = true;
// 				   }else{
// 					   $("#par").removeClass("errorx");
// 				   }
// 			   }
// 				// ------
// 			}
// 			// ------
			
			
// 			   //validaciones sin-----------------------------------
// 			   if(tipoSolicitudGlobal == 2){
				   
// 				 if($("#compania").val() == - 1){
// 					  $("#compania").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					   $("#compania").removeClass("errorx");
// 				 }  
				 
// 				 if($("#proveedor").val() == - 1){
// 					 $("#proveedor").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#proveedor").removeClass("errorx");
// 				 }
				 
// 				 if($("#rfcEmisor").val() == ""){
// 					 $("#rfcEmisor").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#rfcEmisor").removeClass("errorx");
// 				 }
				 
// 				 if($("#folio").val() == ""){
// 					 $("#folio").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#folio").removeClass("errorx");
// 				 }
				 
// 				 if($("#fecha").val() == ""){
// 					 $("#fecha").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#fecha").removeClass("errorx");
// 				 }
				 
// 				 if($("#strSubTotal").val() == ""){
// 					 $("#strSubTotal").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#strSubTotal").removeClass("errorx");
// 				 }

// 				 if($("#total").val() == ""){
// 					 $("#total").addClass("errorx");
// 					   error = true;
// 				 }else{
// 					 $("#total").removeClass("errorx");
// 				 }
// 			   }
// 			// ------
// 			   if(error){
// 				   $("#error-head").text(ERROR);
// 				   $("#error-body").text(COMPLETE);
// 				   error_alert();
// 				   return false;
// 			   }else{
// 				   $("#enviarSolicitud").prop("disabled", false);
// 				   disabledEnabledFields(false);
// 				   return true;
// 			   }
// 			// ------
// 		   }
// 		   // FIN VALTODO-----------------------------------------------------
		
		   
		   function sumSbt(){
			   var sum = 0;			   
				  $('.subtotales').each(function(){
					  if($.isNumeric($(this).val())){				  
				      sum += parseFloat($(this).val());
				      var rest = parseFloat($("#restante").val());
				      var sppc = parseFloat($("#sppc").val());
				      var restante = sppc - sum;
				      $("#restante").val(restante);
				      $("#sbt").val(sum);
				      $('#sbt').number( true, 2 );
				      //$('#restante').number( true, 2 );
				      $('#sppc').number( true, 2 );
							  }else{
						  $(this).val(0);
					  }
				  });
		   }
		// ------
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
			   
		   } //adrowtable
		// ------
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
		// -------------------------------------------------
		 
		   function callRow(length){
			   
			   var idSolicitante = -1;
			   if(especificaSol == '1'){
				  idSolicitante = $('#idSolicitanteJefe option:selected').val();
			   }
			   
			   var tipoSolicitud = '${tipoSolicitud}';
			   loading(true);
				$.ajax({
					type : "GET",
					cache : false,
					url : "${pageContext.request.contextPath}/addRowConXML",
					async : true,
					data : "numrows=" + length + "&idSolicitud=" + tipoSolicitudGlobal + "&idSolicitante=" + idSolicitante + "&tipoSolicitud=" + tipoSolicitud ,
    				success : function(response) {
    					loading(false);
						$("#tablaDesglose").append(response);
						valTrackAsset();
						calculateLine();
						if(tipoSolicitudGlobal == 2){
							$(".ccontable").prop('disabled', true);
						}
						$('.currencyFormat').number( true, 2 );
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
		// ----------------------------------------------------------

		  $(document).ready(function(){
			  hasmod = false;
			  idStatus = false;
			  $('#sandbox-container input').datepicker({
				  format: 'dd/mm/yyyy',
				  language: 'es',
				  endDate: '+0d',
				  autoclose: true,
				  todayHighlight: true
			  });
			// ------
/* 			  console.log("tipo solicitud: "+tipoSolicitudGlobal);
			  console.log("estado solicitud: "+idStatus);
			  console.log("id solicitud: "+idSolicitudGlobal); */

			  
			  $('#proveedor').on('change', function() {
		    	    getRFC(this.value);
				});
			// ------
				  
			  $('.noMercanciasTooltips').on('change', function() {
		    	    refreshTooltip();
				});
			  
			// ------
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
			 // ------
			  $( ".currencyFormat" ).keyup(function() {
				  sumSbt();
				});
			// ------		 			   
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
				if (typeof hasmod != undefined && hasmod != null && hasmod != undefined) {
				   if(hasmod == true){
					   console.log("hasmod: "+hasmod);
					   $(window).bind('beforeunload', function(){
							return PERDERA_INFORMACION;
						}); 
				   }
				}
			// ------
			   $('#tablaDesglose').on('click', ".removerFila", function(){
				    $(this).closest ('tr').remove ();
				    sumSbt();
				    calculateLine();
				}); 
			// ------
			   if(tipoSolicitudGlobal == 2){
					  disabledEnabledFields(false);
					  $("#idSolicitanteJefe").prop('disabled',true);
				  }else{
			          disabledEnabledFields(true);
				  }
			// ------
				  $("#cambiarxml").click(function(){
					  $("#mensaje-dialogo").text(MENSAJE_DIALOGO_CON_XML);
					  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
					  $("#cambiarxml_button").show();
				  });
				// ------
				  $("#cambiarxml_button").click(function(){
					    
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
						
				  });
				// ------
				  $("#cancelar_button").click(function(){
					  
					  var id = '${facturaConXML.idSolicitudSession}';
					   
					     $.ajax({
					    		type : "GET",
					    		cache : false,
					    		url : "${pageContext.request.contextPath}/cancelarSolicitud",
					    		async : false,
					    		data : "idSolicitud=" + id,
					    		success : function(response) {
					    	        if(response.resultado == 'true'){
					    				$(window).unbind('beforeunload');
					    	        	location.reload(true)
					    	        } 
					    		},
					    		error : function(e) {
					    			console.log('Error: ' + e);
					    		},
					    	});  
				  });
				// ------
				 
				  
				  $("#cambiarxml_button_cancelar").click(function(){
					  $("#cambiarxml").prop("checked",false);
					  $("#modal-solicitud").modal("hide");
				  });
				// ------
				  
				  var isModificacion = '${facturaConXML.modificacion}';
				  var isCreacion = '${facturaConXML.creacion}';
				  
				  if(isModificacion == 'true'){
					  $("#ok-head").text(ACTUALIZACION);
					  $("#ok-body").text(INFORMACION_ACTUALIZADA);
					  ok_alert();
				  }
				// ------
				  if(isCreacion == 'true'){
					  $("#ok-head").text(NUEVA_SOLICITUD);
					  $("#ok-body").text(SOLICITUD_CREADA);
					  ok_alert();
				  }
				// ------
				  
				  $('#modal-solicitud').on('hidden.bs.modal', function () {
					   $("#cambiarxml_button").hide();
					   $("#cancelar_button").hide();
					   $("#cambiar_solicitante_button").hide();
					})
				// ------  
				  
				  /*PARA activar y refrescar tooltip*/
				  refreshTooltip();
				  activeToolTip();
				  
				  
				  $('.aidAll').on('change', function() {
			    	    console.log($(this).attr('id'));
					});
				// ------
				   setStatusScreen(idStatus);	
				}); 
		  
		  function valTrackAsset(){
			  if($("#track_asset").is(":checked")){
				    $("#cuenta_contable").prop('disabled', false);
				    $("#par").prop('disabled', false);
					$(".trackAsset").prop('disabled',false);
			  }else{
					$("#cuenta_contable").prop('disabled', true);
					$("#par").prop('disabled', true);
					$(".trackAsset").prop('disabled',true);
			  }
			  
		  }
		// ------
		  function cancelar(){
			  $("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
			  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
			  $("#cancelar_button").show();
		  }
		// ------
		
		// valXML -------------------------------------------------------------
		  function valXML(){
			
			  $("#loaderXML").show();
		
			  if(document.getElementById("file").files.length != 0){
				  $("#error-alert").hide();
				  
				  var data = new FormData();
				  jQuery.each(jQuery('#file')[0].files, function(i, file) {
				      data.append('file-'+i, file);
				  });
				  
				  
				  if(data){
					  jQuery.ajax({
						  
				    		url : "${pageContext.request.contextPath}/resolverXML",
						    data: data,
						    cache: false,
						    contentType: false,
						    processData: false,
						    type: 'POST',
						    success: function(data){
								$("#loaderXML").hide();
					      	    $("#file").removeClass("errorx");
								console.log('Si YO');
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
							        console.log(data);
							        $("#tipoFactura").val(data.tipoFactura);
							        
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
						    }
						});
				  }
			// ------ 
			  }else{
				  $("#loaderXML").hide();
  				  $("#file").addClass("errorx");
				  $("#error-alert").show();
				  $("#error-head").text(ERROR);
				  $("#error-body").text(COMPLETE);
			  }
			// ------
		  }
		//  FIN valXML --------------------------------------------------------
					function calculateLine() {
						$('#tablaDesglose tr').each(function(i, row) {
							if (i > 0) {
								var nlinea = $(this).find(".linea").text(i);
							}
						});
					}
				
					function disabledEnabledFields(state){
						$(".enableInput").prop('disabled', state);
					}
					
// 					function enviarSolicitudProceso(){
// 						 var idSolicitud = '${facturaConXML.idSolicitudSession}';
// 						if(validaEnvioProceso()){
// 							if(hasmod == false){
// 						 	 if(tipoSolicitudGlobal == 2){
// 								    $.ajax({
// 							    		type : "GET",
// 							    		cache : false,
// 							    		url : "${pageContext.request.contextPath}/revisarArchivosEnSolicitud",
// 							    		async : false,
// 							    		data : "idSolicitud=" + idSolicitud,
// 							    		success : function(response) {
// 							    			 console.log(response);
// 							    	         if(response.respuesta == 'true'){
// 							    	        	 enviaProceso(idSolicitud);
// 							    	         }else{
// 							    	        	 $("#error-head").text(ERROR);
// 												 $("#error-body").text(DOCUMENTO_SOPORTE_ANEXAR);
// 											     error_alert();
// 							    	         }
// 							    		},
// 							    		error : function(e) {
// 							    			$("#error-head").text(ERROR);
// 											 $("#error-body").text(NO_SE_ENVIO);
// 										     error_alert();
							    			
// 							    		},
// 							    	}); 
// 							 }else{
// 								 enviaProceso(idSolicitud);
// 							 } 
							 
// 						}else{
// 							$("#error-head").text(ERROR);
// 							 $("#error-body").text(GUARDE_ENVIAR);
// 						     error_alert();
// 						}
// 						}
// 					 }
					
					function getRFC(idProveedor){
						if(idProveedor != -1){
							 var idSolicitud = '${facturaConXML.idSolicitudSession}';
						     $.ajax({
						    		type : "GET",
						    		cache : false,
						    		url : "${pageContext.request.contextPath}/getRFC",
						    		async : false,
						    		data : "idProveedor=" + idProveedor,
						    		success : function(response) {
						    			$("#rfcEmisor").val(response);
						    		},
						    		error : function(e) {
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
								    $("#sppc").addClass("errorx");
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
					$("#id_compania_libro_contable").val(-1);
					$("#par").val(null);
				}	
				
			     function verDetalleEstatus(id) {
			         
			  		if(id > 0){
			          	console.log(id);
			          
			              $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/getEstatus",
			                  async: true,
			                  data: "intxnId=" + id,
			                  success: function(result) {
			                  	$("#tablaDetalle").empty().append(result.lista);
			                  	//abrir ventana modal
			                  	$('#modalEstatus').modal('show'); 
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              }); 
			              
			          }//if
			  	}
			     
			     //recibe el id del elemento
			     function actualizarCuentas(idLoc){
			    	 console.log("---idLocacion: "+idLoc);
			    	 
			    	 var id = idLoc;
			    	 var idLocacion = document.getElementById(id).value;
			    	 
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/seleccionLocacion",
			                  async: true,
								data : "idElemento=" + id + "&idLocacion=" + idLocacion+ "&tipoSolicitud=" + tipoSolicitudGlobal,
			                  success: function(result) {
			                  	 console.log(result.idElemento);
			                  	 document.getElementById(result.idElemento).innerHTML=result.options;
			                  	 document.getElementById(result.idElemento).disabled = false;
			                  	 console.log(result.options);
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              }); 
			    	 
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
			    	 
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: "${pageContext.request.contextPath}/seleccionAID",
			                  async: true,
								data : "idElemento=" + id + "&idAid=" + idValor,
			                  success: function(result) {
				                  	 document.getElementById(result.idCatMenor).innerHTML=result.optionsMenor;
				                  	 document.getElementById(result.idCatMayor).innerHTML=result.optionsMayor;
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              });
			    	 
			    	 
			     }
			     
// 			     function enviaProceso(idSolicitud){
// 			    	 $.ajax({
// 				    		type : "GET",
// 				    		cache : false,
// 				    		url : "${pageContext.request.contextPath}/enviarAProceso",
// 				    		async : false,
// 				    		data : "idSolicitud=" + idSolicitud,
// 				    		success : function(response) {
// 				    	         if(response.resultado == 'true'){
// 				    	        	$(window).unbind('beforeunload');
// 					    	        location.reload(true);
// 				    	         }
// 				    		},
// 				    		error : function(e) {
// 				    			$("#error-head").text(ERROR);
// 								 $("#error-body").text(NO_SE_ENVIO);
// 							     error_alert();
				    			
// 				    		},
// 				    	}); 
// 			     }
			     
			     function actualizarSubtotal(){
					 $("#sppc").val($('#strSubTotal').val());
			     }
			     
			     $(".form-control").on('change', function() {
			    	  /* console.log("cambio: "+$(this).val()); 
			    	  hasmod = true;*/
				 });
			     
			
				  function checkPar(){
					  if(!$.isNumeric($("#par").val())){				  
						  $("#par").val(null);
						}
				  }
	
	
	
	/* F I N - V A L I D A - T O D O ----------------------------*/
	

 	var url = '${pageContext.request.contextPath}';
	var idSolicitud = '${idSolicitud}';
	var idEstadoSolicitud = '${idEstadoSolicitud}';
	var idSolicitudGlobal = '${idSolicitud}';
	var tipoSolicitudGlobal = '${tipoSolicitud}'
	var conAnticipo = '${comprobacionAnticipoViajeDTO.conAnticipo}';
	var url_server = url;
	var path = '${pageContext.request.contextPath}';
	var conceptoAlimentos = '<%= Etiquetas.CONCEPTO_ALIMENTOS%>';
	var isComprobacionViaje = true;
	isModificacion = false;
	isCreacion = false;
	
	</script>
	<%-- <script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script> --%>
	<script src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<%-- <script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script> --%>
	<script src="${pageContext.request.contextPath}/resources/js/comprobacionAnticipo.js">	</script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/comprobacionAnticipoViaje.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
	
</body>
</html>
