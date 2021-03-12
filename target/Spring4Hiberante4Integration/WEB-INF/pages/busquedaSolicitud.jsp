<%@page import="com.lowes.util.Etiquetas"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.lowes.util.Etiquetas"%>
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
/* Sobrecarga de estilo para exportar a excel */
.buttons-excel {
	float: right;
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #000;
	background-color: #FDFDFD;
	border-color: #DDDDDD;
	margin-bottom: 10px;
	cursor: pointer;
}
label {
    margin-bottom: 1px;
	}

.form-group {
    margin-bottom: 6px;
}

.custom-numero-solicitud {

	width: 80px;
}

.custom-importe {

	width: 220px;
}

.custom-moneda {
	width: 80px !important;
}

.custom-estatus-factura {
	width: 140px !important;
}
.custom-estatus-solicitud {
	width: 140px !important;
}

.custom-solicitud-solicitante {
	width: 285px !important;
}

.custom-tipoSolicitud {
	width: 260px !important;
}

.custom-fecha {
	width: 190px !important;
}
</style>
</head>

<body>
<c:set var="idMercanciaConXml"
		value="<%=Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML%>" />
	<c:set var="idMercanciaSinXml"
		value="<%=Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML%>" />
	<c:set var="idReembolsos" value="<%=Etiquetas.SOLICITUD_REEMBOLSOS%>" />
	<c:set var="idCajaChica" value="<%=Etiquetas.SOLICITUD_CAJA_CHICA%>" />
	<c:set var="idKilometraje" value="<%=Etiquetas.SOLICITUD_KILOMETRAJE%>" />
	<c:set var="idAnticipo" value="<%=Etiquetas.SOLICITUD_ANTICIPO%>" />
	<div id="wrapper">
		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%=etiqueta.BUSQUEDA%>
							<%=etiqueta.SOLICITUDES%></h1>
						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<div style="display: none;" id="error-alert"
				class="alert alert-danger fade in">
				<strong>
					<span id="error-head"><%=errorHead%></span></strong> 
					<span id="error-body"><%=errorBody%></span>
			</div>
			
			<div style="display: none;" id="error-alert-modal"
								class="alert alert-warning fade in">
								<strong><span id="error-head-modal"><%=etiqueta.ERROR%></span></strong>
								<span id="error-body-modal"><%=etiqueta.COMPLETE%></span>
			</div>
			
			<!-- /.container-fluid -->
			<form:form method="post" enctype="multipart/form-data; charset=UTF-8"
				action="busquedaSolicitudBuscar" modelAttribute="busquedaSolicitudDTO" class="img-rounded busquedaForm">
				<div class="row  header-search">
					<div class="col-md-12">
					
					<h4 style="" class="col-md-10">
							<%=etiqueta.ESPECIFIQUE_CRITERIOS_BUSQUEDA%>
						</h4>
						<button id="buscar" onclick="return validar();"
							style="" type="submit"
						class="btn btn-primary col-md-2">
							<%=etiqueta.BUSCAR%></button>

						<a id="mostrar-busqueda" onclick=""
							style="display:none"
							class="btn btn-primary col-md-2">
							<%=etiqueta.MOSTRAR%></a>
						</div>
					</div>
				<div class="row">
					<div class="col-md-12">
						<div class="col-md-4">
							<div class="form-group">
								<!--  combo compañía -->
								<label><%=etiqueta.COMPANIA%>:</label>
								<form:select path="idCompaniaFiltro"
									cssClass="form-control selectpicker" data-live-search="true"
									id="compania">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									<c:forEach items="${companiaList}" var="compania">
										<option ${compania.idcompania == filtros.idCompaniaFiltro  ? 'selected' : ''}
										value="${compania.idcompania}">${compania.descripcion}</option>
									</c:forEach>
									
								</form:select>
							</div>
						</div>
						<div class="col-md-4">
						
							<div id="lista_proveedor" class="form-group">
								<label><%=etiqueta.PROVEEDOR%>:</label>
								<img src="${pageContext.request.contextPath}/resources/images/loader.gif" />
							</div>
							
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<label><%=etiqueta.NUMERO_SOLICITUD%>:</label>
								<form:input cssClass="form-control custom-numero-solicitud" path="idSolicitud" style="text-align:center" value="${filtros.idSolicitud}" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="col-md-4">
							<!--  combo tipo solicitud -->
							<div class="form-group">
								<label for="tipoSolicitud_list"><%=etiqueta.COL_TIPO_SOLICITUD%>:</label><br>
								<form:select path="idTipoSolicitudFiltro"
									cssClass="form-control selectpicker custom-solicitud-solicitante" data-live-search="true" 
									id="tipoSolicitud_list">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									<c:forEach items="${tipoSolicitudList}" var="tipoSolicitud">
										<option ${tipoSolicitud.idTipoSolicitud == filtros.idTipoSolicitudFiltro  ? 'selected' : ''}
										value="${tipoSolicitud.idTipoSolicitud}">${tipoSolicitud.descripcion}</option>
									</c:forEach>
									
								</form:select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="tipoSolicitud_list"><%=etiqueta.COL_FECHA_SOLICITUD%>:</label>
								<div id="date-selector-from">
									<div class="input-daterange input-group custom-fecha" id="datepicker">
										
										<c:if test="${!first eq 'true'}">
											<c:if test="${filtros.fechaInicialFiltro ne null || empty filtros.fechaInicialFiltro}">
												<c:set var="fechaInicial" value="${filtros.fechaInicialFiltro}" />
											</c:if>
											
											<c:if test="${filtros.fechaFinalFiltro ne null || empty filtros.fechaFinalFiltro}">
												<c:set var="fechaFinal" value="${filtros.fechaFinalFiltro}" />
											</c:if>
											
										<form:input type="text" class="input-sm form-control small-date"
											path="fechaInicialFiltro"  name="start" id="start" />
										<span class="input-group-addon"><%=etiqueta.AL%></span>
										<form:input type="text" class="input-sm form-control small-date"
											path="fechaFinalFiltro" name="end" id="end" />
									
										</c:if>
										<c:if test="${first eq 'true'}">
											<form:input type="text" class="input-sm form-control small-date"
											path="fechaInicialFiltro" name="start" id="start" />
										<span class="input-group-addon"><%=etiqueta.AL%></span>
										<form:input type="text" class="input-sm form-control small-date"
											path="fechaFinalFiltro" name="end" id="end" />
										</c:if>
										
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<!--  combo estatus solicitud -->
								<label><%=etiqueta.ESTATUS_SOLICITUD%>:</label>
								<form:select path="idEstadoSolicitudFiltro" 
								cssClass="form-control selectpicker custom-estatus-solicitud" data-live-search="true"
									id="estadoSolicitud">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									<c:forEach items="${estadoSolicitudList}" var="estadoSolicitud">
										<option ${estadoSolicitud.idEstadoSolicitud == filtros.idEstadoSolicitudFiltro  ? 'selected' : ''}
										value="${estadoSolicitud.idEstadoSolicitud}">${estadoSolicitud.estadoSolicitud}</option>
									</c:forEach>
									
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-4"></div>
				</div>



				<div class="row">
					<div class="col-md-12">
						<div class="col-md-4">
							<div class="form-group">
								<label><%=etiqueta.IMPORTE_SOLICITUD%> <%=etiqueta.ENTRE%></label>
								<div class="input-group custom-importe">
									<form:input cssClass="form-control currencyFormatII importe-total" path="strImporteMenor" id="importeMenor" style="font-size: 15px; width:100%" value="${filtros.strImporteMenor}"/>
									<span class="input-group-addon"><%=etiqueta.Y%></span>
									<form:input cssClass="form-control currencyFormatII importe-total" path="strImporteMayor" id="importeMayor" style="font-size: 15px; width:100%" value="${filtros.strImporteMayor}"/>
								</div>
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<!--  combo moneda -->
								<label><%=etiqueta.MONEDA%>:</label><br>
								<form:select path="idMonedaFiltro"
									cssClass="form-control selectpicker custom-moneda " data-live-search="true"
									id="moneda">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									<%-- <form:options items="${monedaList}" itemValue="idMoneda"
										itemLabel="descripcion" /> --%>

									<c:forEach items="${monedaList}" var="moneda"> 
										<c:if test="${first eq 'true'}">
											<option ${moneda.idMoneda == monedaPesos  ? 'selected' : ''}
										value="${moneda.idMoneda}">${moneda.descripcion}</option>
										</c:if>
										<c:if test="${!first eq 'true'}">
											<option ${moneda.idMoneda == filtros.idMonedaFiltro  ? 'selected' : ''}
										value="${moneda.idMoneda}">${moneda.descripcion}</option>
										</c:if>
									</c:forEach> 
										
								</form:select>
							</div>
						</div>
						
						<div class="col-md-4"></div>
					</div>
					<div class="col-md-4"></div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<!--  combo locaciones -->
						<div class="col-md-4">
							<div class="form-group">
								<label for="locacion_list"><%=etiqueta.LOCACION_SOLICITANTE%>:</label><br>
								<form:select path="idLocacionFiltro" 
									cssClass="form-control selectpicker custom-solicitud-solicitante" data-live-search="true"
									id="locacion_list">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									
									<c:forEach items="${locacionList}" var="locacion">
										<option ${locacion.idLocacion == filtros.idLocacionFiltro  ? 'selected' : ''}
										value="${locacion.idLocacion}">${locacion.numeroDescripcionLocacion}</option>
									</c:forEach>
									
								</form:select>
							</div>
						</div>
						<c:if test="${usuarioActual.esAutorizador eq 1 || usuarioActual.puesto.idPuesto eq puestoAP}">
							<div class="col-md-4">
								<!--  combo solicitante -->
								<div class="form-group">
									<label><%=etiqueta.SOLICITANTE%>:</label><br>
									<form:select id="usuarioSolicitante"
										path="idUsuarioSolicitanteFiltro" 
										cssClass="form-control selectpicker custom-solicitud-solicitante" data-live-search="true">
										<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
										
										<c:forEach items="${solicitantesList}" var="solicitante">
											<option ${solicitante.idUsuario == filtros.idUsuarioSolicitanteFiltro  ? 'selected' : ''}
											value="${solicitante.idUsuario}"> ${solicitante.nombre} ${solicitante.apellidoPaterno} ${solicitante.apellidoMaterno} </option>
										</c:forEach>
										
									</form:select>
								</div>
							</div>
						</c:if>
						<c:if test="${usuarioActual.puesto.idPuesto eq puestoAP || usuarioActual.puesto.idPuesto eq puestoConfirmacionAP}">
							<div class="col-md-4">
								<!--  combo autorizador -->
								<div class="form-group">
									<label><%=etiqueta.AUTORIZADOR%>:</label>
									<form:select id="usuarioAutorizador"
										path="idUsuarioAutorizadorFiltro" 
										cssClass="form-control selectpicker" data-live-search="true">
										<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
										
										<c:forEach items="${autorizadoresList}" var="autorizador">
											<option ${autorizador.idUsuario == filtros.idUsuarioAutorizadorFiltro  ? 'selected' : ''}
											value="${autorizador.idUsuario}"> ${autorizador.nombre} ${autorizador.apellidoPaterno} ${autorizador.apellidoMaterno} </option>
										</c:forEach>
										
									</form:select>
								</div>
							</div>
						</c:if>
					</div>
				</div>
					

			</form:form>
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h4><%=etiqueta.LISTADO_SOLICITUDES%>:
						</h4>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>

			<!-- 				ROW TEMPLATE
				<div class="row">
					<div class="col-md-8">
						<div class="col-md-4">.col-md-4</div>
						<div class="col-md-4">.col-md-4</div>
						<div class="col-md-4">.col-md-4</div>
					</div>
					<div class="col-md-4">area de botones</div>
				</div> -->

			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="dataTable_wrapper scrollable">
					<form:form method="post"
						enctype="multipart/form-data; charset=UTF-8"
						action="autorizarSolicitudes" modelAttribute="">
						<table style="font-size: 90%; margin-top: 12px;" class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th style="min-width:102px"><label><%=etiqueta.COL_TIPO_SOLICITUD%></label></th>
									<th style="min-width:208px"><label><%=etiqueta.COL_COMPANIA%></label></th>
									<th style="min-width:208px"><label><%=etiqueta.COL_PROVEEDOR%></label></th>
									<th style="min-width:77px"><%=etiqueta.IMPORTE_SOLICITUD%></th>
									<th style="min-width:72px"><label><%=etiqueta.COL_MONEDA%></label></th>
									<th style="min-width:110px"><label><%=etiqueta.COL_FECHA_SOLICITUD%></label></th>
									<th style="min-width:100px"><%=etiqueta.ESTATUS_SOLICITUD%></th>
									<th style="min-width:160px"><%=etiqueta.LOCACION_SOLICITANTE%></th>
									<th style="min-width:160px"><%=etiqueta.COL_SOLICITANTE%></th>
									<th></th>
									<th style="min-width:278px"><label><%=etiqueta.COL_CONCEPTO%></label></th>
									<th style="min-width:75px"><%=etiqueta.COL_NUM_SOLICITUD%></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${solicitudesBusqueda}" var="solBusqueda"
									varStatus="status">

									<tr class="odd gradeX">
									
										<td style=" width: 9%;">
												<span  style="background-color: ${solBusqueda.tipoSolicitud.colorSolicitud};" class="label label-info fixLabel">
													<c:out value="${solBusqueda.tipoSolicitud.descripcion}" />
												</span>
										</td>
										
										<td><c:out value="${solBusqueda.compania.descripcion}" /></td>
										<td><c:choose>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml}">
													<c:out value="${solBusqueda.facturas[0].proveedor.numeroProveedor} - ${solBusqueda.facturas[0].proveedor.descripcion}" />
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml}">
													<c:out value="${solBusqueda.facturas[0].proveedor.numeroProveedor} - ${solBusqueda.facturas[0].proveedor.descripcion}" />
												</c:when>
												
												<c:when test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idReembolsos}">
													
													<c:if test="${empty solBusqueda.usuarioByIdUsuarioSolicita.numeroProveedor}"> 
														<c:out value="${solBusqueda.usuarioByIdUsuarioSolicita.nombre} ${solBusqueda.usuarioByIdUsuarioSolicita.apellidoPaterno}" />
													</c:if>
													<c:if test="${not empty solBusqueda.usuarioByIdUsuarioSolicita.numeroProveedor}"> 
														<c:out value="${solBusqueda.usuarioByIdUsuarioSolicita.numeroProveedor} - ${solBusqueda.usuarioByIdUsuarioSolicita.nombre} ${solBusqueda.usuarioByIdUsuarioSolicita.apellidoPaterno}" />
													</c:if>
												</c:when>
												
												<c:when test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idCajaChica}">
													
													<c:if test="${empty solBusqueda.usuarioByIdUsuarioAsesor.numeroProveedor}"> 
														<c:out value="${solBusqueda.usuarioByIdUsuario.nombre} ${solBusqueda.usuarioByIdUsuario.apellidoPaterno}" />
													</c:if>
													<c:if test="${not empty solBusqueda.usuarioByIdUsuarioAsesor.numeroProveedor}"> 
														<c:out value="${solBusqueda.usuarioByIdUsuarioAsesor.numeroProveedor} - ${solBusqueda.usuarioByIdUsuarioAsesor.nombre} ${solBusqueda.usuarioByIdUsuarioAsesor.apellidoPaterno}" />
													</c:if>
													
												</c:when>
												
 												<c:when test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml || solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml}">
													
													<c:if test="${empty solBusqueda.facturas[0].proveedor.idProveedor}"> 
														<c:out value="${solBusqueda.facturas[0].proveedor.numeroProveedor}" />
													</c:if>
													<c:if test="${not empty solBusqueda.usuarioByIdUsuarioAsesor.idUsuario}"> 
														<c:out value="${solBusqueda.facturas[0].proveedor.idProveedor} - ${solBusqueda.facturas[0].proveedor.numeroProveedor}" />
													</c:if>
													
												</c:when>
												
												
												<c:otherwise>
													<c:out value="${solBusqueda.facturas[0].proveedor.numeroProveedor} - ${solBusqueda.facturas[0].proveedor.descripcion}" />
												</c:otherwise>
											</c:choose></td>
											
										
										
										<c:choose>
											<c:when test="${solBusqueda.facturas[0].tipoFactura.idTipoFactura eq 2}">
												<td style="text-align:right" class="importe-rojo">
											</c:when>
											<c:otherwise>
												<td style="text-align:right">
											</c:otherwise>
										</c:choose>
										
										
											<c:choose>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml}">
													<a href="#"
														onclick="verDetalleMontoFacturas(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml}">
													<a href="#"
														onclick="verDetalleMontoFacturas(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idReembolsos}">
													<a href="#"
														onclick="verDetalleReembolsos(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idCajaChica}">
													<a href="#"
														onclick="verDetalleReembolsos(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idKilometraje}">
													<a href="#" onclick="verDetalleMontoKilometrajes(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
												
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idAnticipo}">
													<a href="#" onclick="verDetalleMontoAnticipos(${solBusqueda.idSolicitud});">
														$<span class="currencyFormat"><c:out
																value="${solBusqueda.montoTotal}" /></span>
													</a>
												</c:when>
														
												<c:otherwise>
													$<span class="currencyFormat"><c:out
														value="${solBusqueda.montoTotal}" /></span>
												</c:otherwise>
											</c:choose>

										</td>
										<td><c:out value="${solBusqueda.moneda.descripcion}" /></td>
										<td class="sort-date">
										<fmt:formatDate pattern="yyyy/MM/dd"
												value="${solBusqueda.creacionFecha}" /></td>
										<td><a href="#"
											onclick="verDetalleEstatus(${solBusqueda.idSolicitud});">
												<c:out
													value="${solBusqueda.estadoSolicitud.estadoSolicitud}" />
										</a></td>
										<td><c:out value="${solBusqueda.locacion.numeroDescripcionLocacion}" /></td>
										<td><c:out value="" />
											${solBusqueda.usuarioByIdUsuarioSolicita.nombre}
											${solBusqueda.usuarioByIdUsuarioSolicita.apellidoPaterno}</td>
										<td>
																					
											<button onclick="verDetalle(${solBusqueda.idSolicitud},${solBusqueda.tipoSolicitud.idTipoSolicitud})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-success">
														<span class="fa fa-files-o"></span>
											</button>
										</td>
										<td>
											
											<%-- <c:out value="${solicitante} ${solBusqueda.usuarioByIdUsuarioSolicita.idUsuario}" /> --%>
											<c:set var="solClass" value="solicitante" scope="page" />
											<c:if test="${solicitante eq solBusqueda.usuarioByIdUsuarioSolicita.idUsuario}">
												<c:set var="solClass" value="" scope="page" />
											</c:if>
											
											<c:choose>
												<c:when
													test="${solBusqueda.conceptoGasto.length() > 100}">
													<a href="#" class="${solClass}"
														onclick="verDetalleConcepto(${solBusqueda.idSolicitud});">
														<c:out
															value="${fn:substring(solBusqueda.conceptoGasto,0,100)}..." />
													</a>
												</c:when>
												<c:otherwise>
													<a href="#" class="${solClass}"
														onclick="verDetalleConcepto(${solBusqueda.idSolicitud});">
														<c:out value="${fn:substring(solBusqueda.conceptoGasto,0,100)}" />
													</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td><c:choose>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml && solBusqueda.urlVisible eq true}">
													<a target="_blank" href="conXML?id=${solBusqueda.idSolicitud}"  class="${solClass}">
													<span  style="background-color: ${solBusqueda.tipoSolicitud.colorSolicitud};" class="label label-info fixLabel">
													    <c:out value="${solBusqueda.idSolicitud}" />
													  </span>
															</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml && solBusqueda.urlVisible eq true}">
													<a target="_blank" href="sinXML?id=${solBusqueda.idSolicitud}"  class="${solClass}">
													<span  style="background-color: ${solBusqueda.tipoSolicitud.colorSolicitud};" class="label label-info fixLabel">
													    <c:out value="${solBusqueda.idSolicitud}" />
													  </span>
															</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idReembolsos && solBusqueda.urlVisible eq true}">
													<a target="_blank" href="reembolso?id=${solBusqueda.idSolicitud}"  class="${solClass}">
													  <span  style="background-color: ${solBusqueda.tipoSolicitud.colorSolicitud};" class="label label-info fixLabel">
													    <c:out value="${solBusqueda.idSolicitud}" />
													  </span>
													</a>
												</c:when>
												<c:when
													test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idCajaChica && solBusqueda.urlVisible eq true}">
													<a target="_blank" href="cajaChica?id=${solBusqueda.idSolicitud}"  class="${solClass}">
													<span  style="background-color: ${solBusqueda.tipoSolicitud.colorSolicitud};" class="label label-info fixLabel">
													    <c:out value="${solBusqueda.idSolicitud}" />
													  </span>
															</a>
												</c:when>
												<c:otherwise>
													<span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> 
														<c:out value="${solBusqueda.idSolicitud}" />
													</span>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form:form>
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- /.panel-body -->
</div>

		</div>
	</div>
	<!-- /#page-wrapper -->

	<jsp:include page="template/includes.jsp" />
	<jsp:include page="detalleMontoFacturas.jsp" />
	<jsp:include page="detalleMontoReembolso.jsp" />
	<jsp:include page="detalleMontoKilometraje.jsp" />
		<jsp:include page="detalleMontoAnticipo.jsp" />
	<jsp:include page="detalleConcepto.jsp" />
	<jsp:include page="modalDocumentos.jsp" />
    <jsp:include page="modalConsultarAutorizacion.jsp" />
	
	<!-- DataTables JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/usuario.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/locales/bootstrap-datepicker.es.min.js"
		charset="UTF-8"></script>

	<!-- Datatable para exportar a excel -->
	<script
		src="${pageContext.request.contextPath}/resources/js/buttons.html5.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/dataTables.buttons.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jszip.min.js"></script>

	<script>
	var tieneRegistros = '${tieneRegistros}';
	var fechaDefault = '${fechaDefault}';
	var first = '${first}';
	var fechaFinal = '${filtros.fechaFinalFiltro}';
	var fechaInicial = '${filtros.fechaInicialFiltro}';
	var proveedorAsesor = '${numeroProveedorAsesor}'

	$(document).ready(function() {
		console.log("<%=errorHead%>");
		ventanaBusqueda("busquedaSolicitud","#busquedaSolicitudDTO");
		var mensajeSuccess = '${mensajeSuccess}';
		
		console.log("mensaje: " + mensajeSuccess)
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
	
 		if(fechaDefault == 'true'){

			if (first != "false") {
				log('datepicker 0');
				var d = new Date();
			    var firstDayOfMonth = 1;
				var currMonth = d.getMonth();
				var currYear = d.getFullYear();
		        var startDate = new Date(currYear,currMonth,firstDayOfMonth);
					$('#start').datepicker('setDate',startDate);
		        	$('#end').datepicker('setDate', d);
				}
			else {
				$('#start').datepicker();
				$('#start').val(fechaInicial);
	        	$('#end').datepicker();
	        	$('#end').val(fechaFinal);
			}
		}
	
    	
		$('.currencyFormatII').number( true, 2 );
		$('.currencyFormat').number( true, 2 );
		
        $('#dataTables-example').DataTable({
                responsive: true,
                dom: 'Blfrtip',
                aaSorting: [],
                buttons: [
                    'excel'
                ]
        });
        
        
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	/* $("#descripcion").val(null);
        	$("#idPerfil").val(null); */
        });
        	
        if(mensajeSuccess != ""){
        	console.log(mensajeSuccess);
        	 $("#ok-head").text("Autorizado: ");
			 document.getElementById("ok-body").innerHTML = mensajeSuccess;
			   ok_alert();
        }
        
        // Si el grid está vacío, deshabilita el link
        if(tieneRegistros == 'primera'){
            $(".buttons-excel").unbind("click");
            $("#error-head-modal").text("<%=etiqueta.ATENCION%>");
			$("#error-body-modal").text("<%=etiqueta.SIN_RESULTADOS%>");

        }
        
        if(tieneRegistros == 'false'){
            $(".buttons-excel").unbind("click").click(function () {
            	log('no files');
                $("#error-head-modal").text("<%=etiqueta.ATENCION%>");
    			$("#error-body-modal").text("<%=etiqueta.SIN_RESULTADOS%>");
    			error_alert_modal();
            });
			//
        }
        fixDate();
        resetColResizable();
        
        //cargar proveedores 
        getProveedorAsesores(proveedorAsesor);
        
    });
	
		log('datepicker 1');
	     $('#date-selector-from .input-daterange').datepicker({
			    format: 'mm/dd/yyyy',
			    language: 'es'
		 });
	
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
     
     function verDetalleReembolsos(id) {
    	 console.log("REEMBOLSOS:"+id);
 		if(id > 0){
         	console.log(id);
         
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoReembolso",
                 async: true,
                 data: "intxnId="+id,
                 success: function(result) {
                	 
                 	$("#tablaDetalleReembolsos").empty().append(result.tabla);
                 	$("#totalesReembolsos").text(result.totales);
                 	$("#ivaReembolsos").text(result.iva);
                 	$("#iepsReembolsos").text(result.ieps);
                 	$("#rTotalReembolsos").text(result.rTotal);
                 	$('.currencyFormat').number( true, 2 );
                 	// mostrar modal
                 	$('#modalReembolsos').modal('show'); 
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}


//VALIDACION RANGO DE FECHAS 
	function validar(){
	   
		$("#error-alert").hide();
		var ban = false;
		var banImporte = false;
		
		if($('#start').val() == ""){
			ban = true;
		}
		
		if($('#end').val() == ""){
			ban = true;
		}
		
		var importeMayor = parseFloat($('#importeMayor').val());
		var importeMenor = parseFloat($('#importeMenor').val());
		if(((importeMenor >= 0 && importeMayor >= 0) && (importeMayor <= importeMenor)) || 
			((importeMenor >= 0 && $('#importeMayor').val() == '') || (importeMayor >= 0 && $('#importeMenor').val() == ''))){
			banImporte = true;
		}
		
		var banMoneda = validarMoneda();
		
		if((!ban || !banMoneda) && !banImporte){
			return true;
		}else{
			if(banImporte){
				if((importeMenor >= 0 && importeMayor >= 0) && (importeMayor <= importeMenor)){
					$("#error-head").text("<%=etiqueta.ATENCION%>");
					$("#error-body").text("<%=etiqueta.ERROR_RANGO_MONTO%>");
					$("#error-alert").show();
				}else if((importeMenor >= 0 && $('#importeMayor').val() == '') || (importeMayor >= 0 && $('#importeMenor').val() == '')){
					$("#error-head").text("<%=etiqueta.ATENCION%>");
					$("#error-body").text("<%=etiqueta.ERROR_LLENAR_MONTOS%>");
					$("#error-alert").show();
				}
			}else if(ban && !banMoneda){
				$("#error-head").text("<%=etiqueta.ATENCION%>");
				$("#error-body").text("<%=etiqueta.ERROR_ESPECIFICAR_FECHAS%>");
				$("#error-alert").show();
			}else if(banMoneda && !ban){
					$("#error-head").text("<%=etiqueta.ATENCION%>");
					$("#error-body").text("<%=etiqueta.ERROR_SELECCIONAR_MONEDA%>");
					$("#error-alert").show();
			}else if(ban && banMoneda){
				$("#error-head").text("<%=etiqueta.ATENCION%>");
				$("#error-body").text("<%=etiqueta.ERROR_SELECCIONAR_FECHA_MONEDA%>");
				$("#error-alert").show();
			} 
			
			console.log("false");
			return false;
		}
	}
	
	function validarMoneda(){
		$("#error-alert").hide();
		var ban = false;
		
		if($('#moneda').val() == -1){
			ban = true;
		}
		return ban;
	}
	
	
    function getProveedorAsesores(idProveedorAsesor) {
    	setTimeout(
    			  function() 
    			  {
    				  $.ajax({
    		                 type: "GET",
    		                 cache: false,
    		                 url: "${pageContext.request.contextPath}/getProveedoresBusquedaSolicitud",
    				    	 data : "idProveedorAsesor=" + idProveedorAsesor,
    		                 success: function(result) {
    		                 	$("#lista_proveedor").empty().append(result);
    		                 	$('#proveedor').selectpicker('refresh');
    		                 },
    		                 error: function(e) {
    		                     console.log('Error: ' + e);
    		                 },
    		             }); 
    			  }, 8000);
 	}
	
	 </script>

	<script>
		var rutaserver = '${pageContext.request.contextPath}';
	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/detalleDocumentos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/busquedaSolicitudFactura.js"></script>
</body>
</html>

