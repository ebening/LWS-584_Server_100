<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
.remarcado {
	background-color: #eee !important;
}

.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
	background-color: #eee !important;
}

.check {
	
}

.check-back {
	background-color: #fff !important;
}
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

.check-back {
	background-color: #fff !important;
}

.modal .modal-body {
	/* max-height: 420px; */
	overflow-y: auto;
}

.fix_input_filter {
	float: right !important;
	width: 43% !important;
	height: 27px !important;
}

.table_responsive_fix {
	height: 146px;
	-ms-overflow-x: auto; /* IE8 */
	overflow-y: auto;
	overflow-x: hidden;
	white-space: nowrap;
}

#tabla_pdf {
	white-space: nowrap;
}

.downButton {
	height: 15px;
	margin: 1px;
	color: #000;
}


.fixLabel {
	font-size: 90% !important;
	line-height: 2 !important;
}

#fechaInicial, #fechaFinal {
	color:white;
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
	<c:set var="idAnticipoViaje" value="<%=Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE%>" />
	<c:set var="idComprobacionAnticipoViaje" value="<%=Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE%>" />
	<c:set var="idComprobacionAnticipo" value="<%=Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO%>" />
	<c:set var="idEstadoSolicitudNueva"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_NUEVA%>" />
	<c:set var="idEstadoSolicitudPorAutorizar"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR%>" />
	<c:set var="idEstadoSolicitudRechazada"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA%>" />
	<c:set var="idEstadoSolicitudAutorizada"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA%>" />
	<c:set var="idEstadoSolicitudValidada"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA%>" />
	<c:set var="idEstadoSolicitudPagada"
		value="<%=Etiquetas.ID_ESTADO_SOLICITUD_PAGADA%>" />

	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<c:choose>
							<c:when test="${idTipoSolicitud eq idAnticipo}">
								<h1 class="page-header"><%=etiqueta.COMPROBACION_ANTICIPO%></h1>
							</c:when>
							<c:when test="${idTipoSolicitud eq idAnticipoViaje}">
								<h1 class="page-header"><%=etiqueta.COMPROBACION_ANTICIPO_VIAJE%></h1>
							</c:when>
							<c:when test="${idTipoSolicitud eq 9}">
								<h1 class="page-header"><%=etiqueta.COMPROBACION_AMEX%></h1>
							</c:when>
							<c:otherwise>
								<h1 class="page-header">AMEX HARDCODE</h1>
							</c:otherwise>
						</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<div style="display: none;" id="error-alert"
				class="alert alert-danger fade in">
				<strong> <span id="error-head"><%=errorHead%></span></strong> <span
					id="error-body"><%=errorBody%></span>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body" style="padding-bottom: 0px;">
							<form:form method="post" enctype="multipart/form-data"
								action="iniciarBusqueda" modelAttribute="busquedaFechasIdDTO">
								<form:hidden value="${idEstadoSolicitud}" path="idBusqueda" />
								<div id="date-selector-from" class="col-md-5" style="padding: 0px;">
									<div class="input-daterange input-group" id="datepicker">
										<span class="input-group-addon" style="font-size: 11px; padding: 3px 7px;">Del:</span>
										<form:input type="text" class="input-sm form-control"
											path="fechaInicial"
											value="${busquedaFechasIdDTO.fechaInicialString}"
											name="start" />
										<span class="input-group-addon" style="font-size: 11px; padding: 3px 7px;"><%=etiqueta.AL%></span>
										<form:input type="text" class="input-sm form-control"
											path="fechaFinal"
											value="${busquedaFechasIdDTO.fechaFinalString}" name="end" />
									</div>
								</div>
								<div class="col-md-1" style="padding: 0px;">
									<button type="submit" class="btn btn-xs" id="buscarPorFecha"
										onclick="return validarRangoFechas()">
										<span class="fa fa-search fa-2x" style="font-size: 17px; padding: 0px;"></span>
									</button>
								</div>
							</form:form>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body tableGradient ">
							<div class="dataTable_wrapper scrollable " style="overflow-x:hidden">
								<table style="font-size: 90%;"
									class="table table-striped table-bordered table-hover display nowrap"
									id="dataTables-example">
									<thead>
										<tr>
											<th style="min-width:102px"><label><%=etiqueta.COL_TIPO_SOLICITUD%></label></th>
											<th style="min-width:208px"><label style="width:180px"><%=etiqueta.COL_PROVEEDOR%>/<%=etiqueta.SOLICITANTE%>/<%=etiqueta.BENEFICIARIO%></label></th>
											<th style="min-width:98px"><label><%=etiqueta.COL_FOLIO_FACTURA%></label></th>
											<th style="min-width:102px"><label><%=etiqueta.COL_FECHA_FACTURA%></label></th>
											<th style="min-width:70px"><label><%=etiqueta.COL_IMPORTE%></label></th>
											<th style="min-width:70px"><label><%=etiqueta.COL_MONEDA%></label></th>
											<th style="min-width:110px"><label><%=etiqueta.COL_FECHA_SOLICITUD%></label> <label><%=etiqueta.E_ANTICIPO%></label></th>
											<th style="min-width:94px"><label><%=etiqueta.COL_ESTATUS_DE%></label> <label><%=etiqueta.COL_AUTORIZACION%></label> <label><%=etiqueta.E_ANTICIPO%></label></th>
											<th style="min-width:278px"><label><%=etiqueta.COL_CONCEPTO%></label></th>
											<c:if test="${idTipoSolicitud eq 8}">
												<th style="min-width:278px"><label><%=etiqueta.COL_RESULTADO_VIAJE%></label></th>
											</c:if>
											<th><label>&zwnj;</label></th>
											<c:if
												test="${idEstadoSolicitud eq idEstadoSolicitudRechazada}">
												<th><%=etiqueta.COL_COMENTARIOS_RECHAZO%></th>
											</c:if>
											<th style="min-width:75px"><label><%=etiqueta.COL_NUM%></label><br><label><%=etiqueta.COL_SOLICITUD%></label> <label><%=etiqueta.E_ANTICIPO%></label></th>
											<th style="min-width:108px"><label><%=etiqueta.COL_NUM%></label><br><label><%=etiqueta.COL_SOLICITUD%></label> <label><%=etiqueta.E_COMPROBACION%></label></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vwSolicitudResumen}" var="facturaResumen">
											<tr class="odd gradeX">
												<td style="width: 9%;"><span
													style="background-color: ${facturaResumen.colorSolicitudComprobacion};"
													class="label label-info fixLabel"> <c:out
															value="${facturaResumen.tipoSolicitudComprobacion}" />
												</span></td>
												
												<!-- Nombre -->
												
												<c:set var="name" value="${facturaResumen.nombre} ${facturaResumen.apellidoPaterno} ${facturaResumen.apellidoMaterno}" />
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idMercanciaConXml || facturaResumen.idTipoSolicitud eq idMercanciaSinXml}">
												
													<c:if test="${empty facturaResumen.numeroProveedorProveedor}">
														<c:set var="conSinXML" value="${name}" />
													</c:if>
																						<c:if test="${not empty facturaResumen.numeroProveedorProveedor}">
														<c:set var="conSinXML" value="${facturaResumen.numeroProveedorProveedor} - ${facturaResumen.proveedor}" />
				</c:if>
													<td><c:out value="${conSinXML}" /></td>
													
												</c:if>
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo
																|| facturaResumen.idTipoSolicitud eq idComprobacionAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje || facturaResumen.idTipoSolicitud eq idComprobacionAnticipoViaje}">
													<td style="height: auto; width: auto; white-space: nowrap;"><c:out value="${name}" /></td>
												</c:if>
												
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idCajaChica}">
													<c:if test="${empty facturaResumen.numeroProveedorProveedorAsesor}">
														<c:set var="cajaChica" value="${usuarioAsesor}" />
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedorProveedorAsesor}">
														<c:set var="cajaChica" value="${facturaResumen.numeroProveedorProveedorAsesor} - ${facturaResumen.usuarioAsesor}" />
													</c:if>
													<td style="height: auto; width: auto; white-space: nowrap;"><c:out value="${cajaChica}" /></td>
												</c:if>
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
													<c:if test="${empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${name}" />
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${facturaResumen.numeroProveedor} - ${facturaResumen.nombre} ${facturaResumen.apellidoPaterno}" />
													</c:if>
													<td><c:out value="${reembolso}" /></td>
												</c:if>
												

												
												
												<!-- Folio Factura -->
												<c:set var="km" value="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje}" />
												<c:choose>
											    	<c:when test="${km}">
												        <td class="soft"><c:out value="<%=etiqueta.NO_APLICA%>" /></td> 
												    </c:when>    
												</c:choose>
												
												<!-- Fecha Factura -->
												
												<c:set var="fecha" value="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje}" />
												<c:choose>
											    	<c:when test="${fecha}">
												        <td class="soft"><c:out value="<%=etiqueta.NO_APLICA%>"/></td> 
												    </c:when>    
												    <c:otherwise>
												       <td><c:out value="${facturaResumen.factura}" /></td>
														<td class="sort-date"><fmt:formatDate pattern="yyyy/MM/dd"
														value="${facturaResumen.fechaFactura}" /></td>
												    </c:otherwise>
												</c:choose>
												
												<!-- Importe -->
												
												<c:choose>
													<c:when test="${facturaResumen.tipoFactura eq 2}">
														<td style="text-align:right" class="importe-rojo">
													</c:when>
													<c:otherwise>
														<td style="text-align:right">
													</c:otherwise>
												</c:choose>
												
												
												<c:choose>
												
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idMercanciaConXml}">
															<a href="#" onclick="verDetalleMontoFacturas(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idMercanciaSinXml}">
															<a href="#" onclick="verDetalleMontoFacturas(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
															<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idKilometraje}">
															<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idAnticipo}">
															<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idAnticipoViaje}">
															<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat"><c:out
																		value="${facturaResumen.montoTotal}" /></span>
															</a>
														</c:when>
														
														<c:otherwise>
														<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
															$<span class="currencyFormat"><c:out
																	value="${facturaResumen.montoTotal}" /></span>
																	</a>
														</c:otherwise>
														
													</c:choose>
												</td>
												
												<!-- Moneda -->
												<td>${facturaResumen.monedaSolicitud} </td>
												
												
												<%-- <td><c:out value="${facturaResumen.creacionFecha}" /></td> --%>
												<td class="sort-date"><fmt:formatDate pattern="yyyy/MM/dd"
														value="${facturaResumen.creacionFecha}" /></td>
												<td>
												<c:if test="${idEstadoSolicitud > idEstadoSolicitudNueva && facturaResumen.idSolicitudAutorizacionAP == null}">
													
													<a href="#"
														onclick="verDetalleEstatus(${facturaResumen.idSolicitud});">
															<c:out value="${facturaResumen.tipoCriterio}" />
													</a>
												</c:if>
												<c:if test="${facturaResumen.idSolicitudAutorizacionAP == 8}">
													
													<a href="#"
														onclick="verDetalleEstatus(${facturaResumen.idSolicitud});">
															<c:out value="${facturaResumen.solicitudAutorizacionAP}" />
													</a>
												</c:if>
												<c:if test="${idEstadoSolicitud == 1}">
													
															<c:out value="Capturada" />
												</c:if>
												</td>
												<td><c:choose>
														<c:when test="${facturaResumen.conceptoGasto.length() > 100}">
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.conceptoGasto,0,100)}..." />
															</a>
														</c:when>
														<c:otherwise>
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.conceptoGasto,0,100)}" />
															</a>
														</c:otherwise>
													</c:choose> 
												</td>
												
												<c:if test="${idTipoSolicitud eq 8}">
													<td><c:choose>
														<c:when test="${facturaResumen.resultadoViaje.length() > 100}">
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.resultadoViaje,0,100)}..." />
															</a>
														</c:when>
														<c:otherwise>
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.resultadoViaje,0,100)}" />
															</a>
														</c:otherwise>
													</c:choose> 
													</td>			
												</c:if>
												
												<td>
													<button
														onclick="verDetalle(${facturaResumen.idSolicitud},${facturaResumen.idTipoSolicitud})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-success">
														<span class="fa fa-files-o"></span>
													</button>
												</td>
												<c:if
													test="${idEstadoSolicitud eq idEstadoSolicitudRechazada}">
													<td><c:out value="${facturaResumen.motivoRechazo}" /></td>
												</c:if>
												<td><c:choose>
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idMercanciaConXml}">
															<a href="conXML?id=${facturaResumen.idSolicitud}" 
																class=""> <span
																style="background-color: ${facturaResumen.colorSolicitud};"
																class="label label-info fixLabel"> <c:out
																		value="${facturaResumen.idSolicitud}" />
															</span>
															</a>
														</c:when>
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idMercanciaSinXml}">
															<a href="sinXML?id=${facturaResumen.idSolicitud}" 
																class=""> <span
																style="background-color: ${facturaResumen.colorSolicitud};"
																class="label label-info fixLabel"> <c:out
																		value="${facturaResumen.idSolicitud}" />
															</span>
															</a>
														</c:when>
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
															<a href="reembolso?id=${facturaResumen.idSolicitud}" 
																class=""> <span
																style="background-color: ${facturaResumen.colorSolicitud};"
																class="label label-info fixLabel"> <c:out
																		value="${facturaResumen.idSolicitud}" />
															</span>
															</a>

														</c:when>
														<c:when
															test="${facturaResumen.idTipoSolicitud eq idCajaChica}">
															<a href="cajaChica?id=${facturaResumen.idSolicitud}" 
																class=""> <span
																style="background-color: ${facturaResumen.colorSolicitud};"
																class="label label-info fixLabel"> <c:out
																		value="${facturaResumen.idSolicitud}" />
															</span>
															</a>
														</c:when>
														
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idKilometraje}">
																<a href="kilometraje?id=${facturaResumen.idSolicitud}" 
																	class=""> <span
																	style="background-color: ${facturaResumen.colorSolicitud};"
																	class="label label-info fixLabel"> <c:out
																			value="${facturaResumen.idSolicitud}" />
																	</span>
																	</a>
															</c:when>
														
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idAnticipo}">
																<a href="anticipo?id=${facturaResumen.idSolicitud}" 
																	class=""> <span
																	style="background-color: ${facturaResumen.colorSolicitud};"
																	class="label label-info fixLabel"> <c:out
																			value="${facturaResumen.idSolicitud}" />
																	</span>
																	</a>
															</c:when>
															
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idAnticipoViaje}">
																<a href="anticipoViaje?id=${facturaResumen.idSolicitud}" 
																	class=""> <span
																	style="background-color: ${facturaResumen.colorSolicitud};"
																	class="label label-info fixLabel"> <c:out
																			value="${facturaResumen.idSolicitud}" />
																	</span>
																	</a>
															</c:when>
															
														<c:otherwise>
															<c:out value="${facturaResumen.idSolicitud}" />
														</c:otherwise>
													</c:choose></td>
													
													<td><c:choose>
														<c:when test="${facturaResumen.idSolicitudComprobacion eq 0}">
															<c:if test="${idTipoSolicitud eq 6}">
																<a href="comprobacionAnticipo?ida=${facturaResumen.idSolicitud}" 
																	class=""> 
																	<span
																		style="background-color: ${facturaResumen.colorSolicitudComprobacion};"
																		class="label label-info fixLabel"> 
																		<c:out value="Comprobar" />
																	</span>
																</a>
															</c:if>
															<c:if test="${idTipoSolicitud eq 8}">
																<a href="comprobacionAnticipoViaje?ida=${facturaResumen.idSolicitud}" 
																	class=""> 
																	<span
																		style="background-color: ${facturaResumen.colorSolicitudComprobacion};"
																		class="label label-info fixLabel"> 
																		<c:out value="Comprobar" />
																	</span>
																</a>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:if test="${idTipoSolicitud eq 6}">
																<a href="comprobacionAnticipo?id=${facturaResumen.idSolicitudComprobacion}" 
																	class=""> 
																	<span
																		style="background-color: ${facturaResumen.colorSolicitudComprobacion};"
																		class="label label-info fixLabel"> 
																		<c:out value="${facturaResumen.idSolicitudComprobacion}" />
																	</span>
																</a>
															</c:if>
															<c:if test="${idTipoSolicitud eq 8}">
																<a href="comprobacionAnticipoViaje?id=${facturaResumen.idSolicitudComprobacion}" 
																	class=""> 
																	<span
																		style="background-color: ${facturaResumen.colorSolicitudComprobacion};"
																		class="label label-info fixLabel"> 
																		<c:out value="${facturaResumen.idSolicitudComprobacion}" />
																	</span>
																</a>
															</c:if>
														</c:otherwise>
													</c:choose>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

		</div>

	</div>
	<!-- /#page-wrapper -->

	<jsp:include page="template/includes.jsp" />
	<jsp:include page="detalleMontoFacturas.jsp" />
	<jsp:include page="detalleMontoReembolso.jsp" />
	<jsp:include page="detalleConcepto.jsp" />
    <jsp:include page="modalDocumentos.jsp" />
    <jsp:include page="modalConsultarAutorizacion.jsp" />
	

	<!-- DataTables JavaScript -->


	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
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
	
	   var rutaserver = '${pageContext.request.contextPath}';

	
	/* 
	//VALIDACIONES
	function submitPerfilForm(){
		$("#error-sign").hide();
		var ban = false;
		
		
		if($('#descripcion').val() == ""){
			ban = true;
		}
		
		if(ban){
			$("#error-sign").show();
			console.log("false");
			return false;
		}else{
			console.log("true");
			return true;
		}
	}
	 */
	
    $(document).ready(function() {
    	
		if ('${busquedaFechasIdDTO.fechaInicialString}' == '01/01/2001') {
			log('Cambiamos?');
			log('Cambiamos?',$('#fechaInicial').val());
			$('#fechaInicial').val('');
			$('#fechaFinal').val('');
		}
		
		$('#fechaInicial').css('color','black');
		$('#fechaFinal').css('color','black');
    	
		console.log("<%=errorHead%>");
		
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	 
        $('#dataTables-example').DataTable({
        	 responsive: true,
             dom: 'Blfrtip',
             buttons: ['excel'],
         });
    	
    	$('.currencyFormat').number( true, 2 );
    		
    	
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	/* $("#descripcion").val(null);
        	$("#idPerfil").val(null); */
        });
        
        
        
        $('#date-selector-from .input-daterange').datepicker({
		    format: 'mm/dd/yyyy',
		    language: 'es'
		    
	 });
        
/*         d = new Date();
        var hoy = d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear();
        $('#fechaInicial').val(hoy);
        $('#fechaFinal').val(hoy); */
        
        fixDate();
        resetColResizable();
        
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
     
   //VALIDACION RANGO DE FECHAS 
 	function validarRangoFechas(){
 		$("#error-alert").hide();
 		var ban = false;
 		
 		var fechaInicial =  $('#fechaInicial').val();
 		var fechaFinal =  $('#fechaFinal').val();
 		
 		if($('#fechaInicial').val() == ""){
 			ban = true;
			$("#fechaInicial").addClass("errorx");
		}else{
			$("#fechaInicial").removeClass("errorx");
		}
 		
 		if($('#fechaFinal').val() == ""){
 			ban = true;
			$("#fechaFinal").addClass("errorx");
		}else{
			$("#fechaFinal").removeClass("errorx");
		}
 		
 		if (fechaInicial == "" && fechaFinal == "") {
 			$('#fechaInicial').css('color','white');
 			$('#fechaInicial').val('01/01/2001');
 			$('#fechaFinal').css('color','white');
 			$('#fechaFinal').val('01/01/3000');
 			ban = false;
 		}
 		
 		
 		if(ban){
 			
 			
 			$("#error-head").text("<%=etiqueta.ATENCION%>");
 			$("#error-body").text("<%=etiqueta.ERROR_ESPECIFICAR_FECHAS%>");
 			
 			$("#error-alert").show();
 			
 			console.log("false");
 			return false;
 		}else{
 			console.log("true");
 			return true;
 		}
 	}
    </script>

	<script	src="${pageContext.request.contextPath}/resources/js/detalleDocumentos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/busquedaSolicitudFactura.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	<script	src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
	
</body>
</html>

