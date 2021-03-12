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
			<form:form id="formAnticipo" modelAttribute="anticipoDTO" action="saveAnticipoViaje" method="post">
			
				<form:hidden cssClass="blockedInput" value="${anticipoDTO.idSolicitudSession}" path="idSolicitudSession" />
				<!-- Título de la sección ----------------------- -->
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<!-- <%= etiqueta.SOLICITUD_ANTICIPO_TITULO %> -->
								Anticipo de Gastos de Viaje
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
					   <button onclick="verDetalleEstatus(${anticipoDTO.idSolicitudSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button ${anticipoDTO.idSolicitudSession > 0 ? '' : 'disabled'}
							onclick="enviarSolicitudProceso()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%></button>
						</c:if>
							
						
						<c:if test="${idEstadoSolicitud == 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 0 || idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button id="save_button" onclick="valTodoAnticipoViaje();"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
						
						</c:if>
					</div>

					<div class="panel-body">
					
						<!-- ROW 1 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label><%= etiqueta.COMPANIA %>:</label> 
									<form:select id="compania" path="compania.idcompania" class="form-control blockedInput" disabled = "true">
										<option title="<%= etiqueta.SELECCIONE %>" value="-1"><%= etiqueta.SELECCIONE %></option>
										<c:forEach items="${companiaList}" var="companiaList">
											<option ${companiaList.idcompania == anticipoDTO.compania.idcompania ? 'selected' : ''}
											value="${companiaList.idcompania}">${companiaList.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="row">
								<div class="col-md-4">
								   <div class="form-group">
									<label><%= etiqueta.FORMA_PAGO %>:</label> 
									<form:select path="formaPago.idFormaPago" id="idFormaPago"  class="form-control blockedInput">

										<c:forEach items="${formaPagoList}" var="formaPagoList">
											<option ${formaPagoList.idFormaPago == anticipoDTO.formaPago.idFormaPago ? 'selected' : ''}
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
										class="form-control blockedInput" disabled = "true">
										  <option title="<%= etiqueta.SELECCIONE %>" value="-1"><%= etiqueta.SELECCIONE %></option>
										  <c:forEach items="${locacionesPermitidas}" var="locacion">
										  <option ${anticipoDTO.locacion.idLocacion == locacion.idLocacion ? 'selected' : ''}
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
										class="form-control blockedInput" disabled = "true">
										<c:forEach items="${monedaList}" var="monedas">
											<option ${monedas.idMoneda == anticipoDTO.moneda.idMoneda  ? 'selected' : ''}
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
									 <form:select path="viajeDestino.idViajeDestino" id="destinos" 
										class="form-control blockedInput">
										<option value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${viajeDestinoList}" var="destinos">
											<c:if test="${destinos.esOtro != 1}">
												<option ${destinos.idViajeDestino == anticipoDTO.viajeDestino.idViajeDestino  ? 'selected' : ''} 
												value="${destinos.idViajeDestino}">${destinos.descripcion}</option>
											</c:if>
										</c:forEach>
										<c:forEach items="${viajeDestinoList}" var="destinos">
											<c:if test="${destinos.esOtro == 1}">
												<option ${destinos.idViajeDestino == anticipoDTO.viajeDestino.idViajeDestino  ? 'selected' : ''} 
												value="${destinos.idViajeDestino}" otro="true">${destinos.descripcion}</option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<div class="form-group ${anticipoDTO.otroDestino eq null ? 'destino-oculto' : ''}">
										<label>Otro destino</label>
										<form:input maxlength="100" path="otroDestino" id="otroDestino" class="form-control" type="text" value="${otroDestino}"/>
									</div>
								</div>
							</div>
						</div>
						
						<!-- ROW 5 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label>Motivo de viaje:</label>
									 <form:select path="viajeMotivo.idViajeMotivo" id="motivos" 
										class="form-control blockedInput">
										<option value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${viajeMotivoList}" var="motivos">
											<c:if test="${motivos.esOtro != 1}">
												<option ${motivos.idViajeMotivo == anticipoDTO.viajeMotivo.idViajeMotivo  ? 'selected' : ''}
												value="${motivos.idViajeMotivo}">${motivos.descripcion}</option>
											</c:if>
										</c:forEach>
										<c:forEach items="${viajeMotivoList}" var="motivos">
											<c:if test="${motivos.esOtro == 1}">
												<option ${motivos.idViajeMotivo == anticipoDTO.viajeMotivo.idViajeMotivo  ? 'selected' : ''}
												value="${motivos.idViajeMotivo}" otro="true">${motivos.descripcion}</option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group ${anticipoDTO.otroMotivo eq null ? 'motivo-oculto' : ''}">
									<label>Otro motivo de viaje</label>
									<form:input maxlength="100" path="otroMotivo" id="otroMotivo" class="form-control" type="text" value="${otroMotivo}" />
								</div>
							</div>
						</div>
						
						<!-- ROW 6 -->
						<div class="row">
							<div class="input-daterange">
								<div class="col-md-4">
									<div class="form-group">
										<label>Fecha de salida:</label>
										<form:input id="fechaSalida" path="fechaSalida" class="form-control" readonly="readonly" type="text" value=""/>
									</div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
										<label>Fecha de regreso:</label>
										<form:input id="fechaRegreso" path="fechaRegreso" class="form-control" readonly="readonly" type="text" value=""/>
									</div>
								</div>
							</div>					
						</div>
						
						<!-- ROW 7 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label>Número de personas:</label>
									<form:input style="text-align:center; width:65px;" maxlength="2" path="numeroPersonas" id="numeroPersonas" class="form-control" type="number" value="" />
								</div>
							</div>
						</div>		
						
						<!-- ROW 9 -->
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label><%= etiqueta.CONCEPTO %>:</label> 
									<form:textarea  maxlength="500" cssClass="form-control" value="${anticipoDTO.concepto}" rows="5" id="concepto" path="concepto" />
								</div>
							</div>
						</div>
						
						<!-- Contenido de formulario de Transporte -->
						
						<div class="transporte row">
							<div class="col-md-12">
								
								<div class="col-md-6">
									<input id="enableAero" type="checkbox" name="transporteAereo" <c:if test="${anticipoDTO.transporteAereo == true}">checked</c:if> />
									<input type="hidden" name="transporteAereo" value="${anticipoDTO.transporteAereo}" class="filled"/>
									
									<label style="padding:6px">Transporte aereo</label>
								</div>
								
								<div class="col-md-6">
									<label style="padding:6px">Incluir en anticipo</label>
								</div>
								
								<div class="col-md-12 tablas-viaje">
									
									<!-- Transporte aereo -->
									<div class="col-md-6" style="display:block; border-right: 1px dotted #ccc;">
										<table id="transporte-aereo" style="" class="table table-striped table-bordered table-hover display nowrap dataTable no-footer" role="grid" aria-describedby="">
											
											<thead>
												<tr>
													<th>Seleccione</th>
													<th>Cotización</th>
													<th>Aerolinea</th>
													<th>Costo</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${anticipoDTO.aerolineas}" var="aerolineas" varStatus="status">
											        <tr>
														<td style="text-align:center">
															<input id="aerolinea${status.count}" type="radio" name="aerolineas[${status.index}].seleccionado" class="aeroCheck" <c:if test="${anticipoDTO.transporteAereo == false}">disabled</c:if> <c:if test="${aerolineas.seleccionado == true}">checked</c:if> />
															<input type="hidden" name="aerolineas[${status.index}].id" value="${aerolineas.id}" class="filled"/>
															<input type="hidden" name="aerolineas[${status.index}].seleccionado" value="${aerolineas.seleccionado}" class="hiddenSelec filled"/>
														</td>
														<td>${status.count}</td>
														
														<td class="td-input"><input maxlength="100" name="aerolineas[${status.index}].nombre" value="${aerolineas.nombre}" class="filled" <c:if test="${anticipoDTO.transporteAereo == false}">disabled</c:if>/></td>
														<td class="td-input"><input name="aerolineas[${status.index}].costo" value="${aerolineas.costo}" class="currencyFormat filled costo aerolinea${status.count}" <c:if test="${anticipoDTO.transporteAereo == false}">disabled</c:if>/></td>
													<tr>
											    </c:forEach>
											</tbody>
										</table>
										
										<div>
										<label>Motivo por el que no elegí la más económica</label>
										<form:textarea  maxlength="500" cssClass="form-control" value="${anticipoDTO.motivoAerolinea}" rows="5" id="motivoAerolinea"
											path="motivoAerolinea" style="font-size: 13px; resize: vertical; margin-bottom: 12px;" disabled="true" />
										</div>
											
									</div>
									
									<!-- Incluir anticipo -->
									
									<div class="incluir-anticipo col-md-6">
										<table style="" class="table table-striped table-bordered table-hover display nowrap dataTable no-footer" id="table-transporte-aereo" role="grid" aria-describedby="">
											
											<thead>
												<tr>
													<th>Seleccione</th>
													<th>Gasto correspondiente a</th>
													<th style="width: 30%;">Importe</th>
												</tr>
											</thead>
											<tbody>
											
											<c:forEach items="${anticipoDTO.conceptos}" var="conceptos" varStatus="status">
												<tr>
													<td style="text-align:center">
														<input id="conceptos${status.count}" type="checkbox" name="conceptos[${status.index}].seleccionado" class="conceptoCheck" <c:if test="${conceptos.seleccionado == true}">checked</c:if> />
														<input id="conceptoCheck${status.count}" type="hidden" name="conceptos[${status.index}].idViajeConcepto" class="conceptos${status.count}" value="${conceptos.idViajeConcepto}"/>
														<input type="hidden" name="conceptos[${status.index}].seleccionado" value="${conceptos.seleccionado}" class="hiddenSelec"/>
													</td>
													<c:if test="${conceptos.esOtro eq 0}">
														<td>${conceptos.descripcion}</td>
													</c:if>
													<c:if test="${conceptos.esOtro eq 1}">
														<td class="td-input">
															<input maxlength="100" name="conceptos[${status.index}].descripcion" placeholder="Otro" value="${conceptos.descripcion}" class="${conceptos.idViajeConceptoString} noCalculado" style="text.align:left;" <c:if test="${conceptos.seleccionado == false}">disabled</c:if>/>
															<input type="hidden" name="conceptos[${status.index}].esOtro" value="${conceptos.esOtro}"/>
														</td>
													</c:if>
													<td class="td-input">
														<input id="${conceptos.idViajeConceptoString}" class="conceptoCheck${status.count} currencyFormat importes ${conceptos.esCalculado == 1 ? '' : 'noCalculado'}" value="${conceptos.importe}" name="conceptos[${status.index}].importe" <c:if test="${conceptos.seleccionado == false || (conceptos.seleccionado == true && conceptos.esCalculado == 1)}">disabled</c:if>/>
													</td>
											</c:forEach>
											
											<tr class="tabla-fondo">
												<td colspan="2">Importe</td>
												<td>
													<div class="form-group input-group">
														<span class="input-group-addon">$</span>
														<form:input cssClass="form-control currencyFormat" style="text-align:right" value="${anticipoDTO.importeTotal}" id="importeTotal" path="importeTotal" disabled="true"/>
														
													</div>
												</td>
											<tr>
											
 											</tbody>
										</table>	
									</div>
									
									
									</div>
								
								</div>
		
							
						</div>
						
						<!-- Fin de contenido de formulario de Transporte -->
						
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
  
    <jsp:include page="template/includes.jsp" />
  	<jsp:include page="modalConsultarAutorizacion.jsp" />
  
	<script>
	var idSolicitud = '${solicitudDTO.idSolicitudSession}';
 	var url = '${pageContext.request.contextPath}';
	var idSolicitud = '${anticipoDTO.idSolicitudSession}';
	var idEstadoSolicitud = '${idEstadoSolicitud}';
	var idSolicitudGlobal = idSolicitud;
	var url_server = url;
	var isCreacion = '${anticipoDTO.creacion}';
	var isModificacion = '${anticipoDTO.modificacion}';
	var tipoProveedor = '${anticipoDTO.tipoProveedor.idTipoProveedor}'
	var idProveedor = '${anticipoDTO.proveedor.idProveedor}'
	var idBeneficiario = '${anticipoDTO.beneficiario}'
	var hasmod = false;

	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/anticipoViaje.js">	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	
	
</body>
</html>