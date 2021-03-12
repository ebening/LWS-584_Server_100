<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
.remarcado {
	background-color: #eee !important;
}

.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
	background-color: #eee !important;
}

.check {
	
}

.fixLabel{
  font-size:90% !important;
 line-height: 2 !important;
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
/* .panel-default {
	overflow-x: scroll;
   } */
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
	<c:set var="idAnticipo" value="<%=Etiquetas.SOLICITUD_ANTICIPO%>" />
	<c:set var="idKilometraje" value="<%=Etiquetas.SOLICITUD_KILOMETRAJE%>" />
	<c:set var="idAnticipoViaje" value="<%=Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE%>" />
	<c:set var="idComprobacionAnticipoViaje" value="<%=Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE%>" />
	<c:set var="idComprobacionAnticipo" value="<%=Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO%>" />
	
	<c:set var="idEstadoSolicitudPorAutorizar" value="<%=Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR%>" />
	<c:set var="idEstadoSolicitudPorValidar" value="<%=Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA%>" />
	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div style="" id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<c:choose>
							<c:when test="${idEstadoSolicitud eq idEstadoSolicitudPorAutorizar}">
								<h1 class="page-header"><%=etiqueta.TITULO_SOLICITUDES_POR_AUTORIZAR%></h1>
							</c:when>
							<c:when test="${idEstadoSolicitud eq idEstadoSolicitudPorValidar}">
								<h1 class="page-header"><%=etiqueta.TITULO_SOLICITUDES_POR_VALIDAR%></h1>
							</c:when>
							<c:otherwise>
								<h1 class="page-header"><%=etiqueta.SOLICITUD%></h1>
							</c:otherwise>
						</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<div style="display: none;" id="error-alert" class="alert alert-danger fade in">
				<strong>
					<span id="error-head"><%=errorHead%></span>
				</strong>
				<span id="error-body"><%=errorBody%></span>
			</div>


			<div style="display: none;" id="ok-alert" class="alert alert-success fade in row">
				<div class="col-lg-1">
				<strong>
					<span id="ok-head"></span>
				</strong>
				</div>
				<div class="col-lg-11">
				<span id="ok-body"></span>
				</div>
			</div>

			<div class="row" style="margin-right: 0px; margin-left: 0px;">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body panel-main tableGradient">
						<div class="row">
							<form:form method="post" enctype="multipart/form-data; charset=UTF-8" action="iniciarBusquedaAutorizacion" modelAttribute="busquedaFechasIdDTO" style="padding: 0px 15px;">
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
						
							<div style="margin-top: 17px; overflow-x:hidden" class="dataTable_wrapper scrollable" id="pre-position">
								<form:form method="post"
									enctype="multipart/form-data; charset=UTF-8"
									action="autorizarSolicitudes"
									id="autorizarSolicitudes"
									modelAttribute="vwSolicitudResumenAutorizacionDTO">
									
									<table style="font-size: 90%; padding-top: 11px;" class="table table-striped table-bordered table-hover"
										id="dataTables-example">
										<thead>
											<tr>
												<th style="min-width:102px"><label><%=etiqueta.COL_TIPO_SOLICITUD%></label></th>
												<th style="min-width:208px"><label><%=etiqueta.COL_COMPANIA%></label></th>
												<th style="min-width:208px"><label style="width:180px"><%=etiqueta.COL_PROVEEDOR%>/<%=etiqueta.SOLICITANTE%>/<%=etiqueta.BENEFICIARIO%></label></th>
												<th style="min-width:98px"><label><%=etiqueta.COL_FOLIO_FACTURA%></label></th>
												<th style="min-width:102px"><label><%=etiqueta.COL_FECHA_FACTURA%></label></th>
												<th style="min-width:70px"><label><%=etiqueta.COL_IMPORTE%></label></th>
												<th style="min-width:70px"><label><%=etiqueta.COL_MONEDA%></label></th>
												<th style="min-width:110px"><label><%=etiqueta.COL_FECHA_SOLICITUD%></label></th>
												<th style="min-width:160px"><label><%=etiqueta.COL_SOLICITANTE%></label></th>
												<th style="min-width:100px"><label><%=etiqueta.COL_ESTATUS%></label></th>
												<c:if test="${idEstadoSolicitud == 2 || idEstadoSolicitud == 3}">
													<th style="min-width:95px"><label><%=etiqueta.COL_FECHA_ULTIMA%></label> <label><%=etiqueta.COL_AUTORIZACION%></label></th>
												</c:if>
												<th style="min-width:278px"><label><%=etiqueta.COL_CONCEPTO%></label></th>
												<th><label>&zwnj;</label></th>
												<th style="min-width:78px"><label><%=etiqueta.COL_AUTORIZAR%></label></th>
												<th style="min-width:78px"><label><%=etiqueta.COL_RECHAZAR%></label></th>
												<th style="min-width:278px"><label><%=etiqueta.COL_COMENTARIOS_RECHAZO%></label></th>
												<th style="min-width:75px"><label><%=etiqueta.COL_NUM%></label> <label><%=etiqueta.COL_SOLICITUD%></label></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach
												items="${vwSolicitudResumenAutorizacionDTO.vwSolicitudResumenAutorizaciones}"
												var="facturaResumen" varStatus="status">

												<tr class="odd gradeX">
													<td style=" width: 9%;"><form:hidden value="${facturaResumen.idSolicitud}"
															path="vwSolicitudResumenAutorizaciones[${status.index}].idSolicitud" />
														<form:hidden value="${facturaResumen.idEstadoSolicitud}"
															path="vwSolicitudResumenAutorizaciones[${status.index}].idEstadoSolicitud" />
														<form:hidden
															value="${facturaResumen.idEstadoAutorizacion}"
															path="vwSolicitudResumenAutorizaciones[${status.index}].idEstadoAutorizacion" />
														<span  style="background-color: ${facturaResumen.colorSolicitud};" class="label label-info fixLabel">
												             <c:out value="${facturaResumen.tipoSolicitud}" />
												         </span>
														
														</td>
													<td><c:out value="${facturaResumen.compania}" /></td>
													
												<c:set var="name" value="${facturaResumen.usuarioSolicita} " />
												
												 <c:if test="${facturaResumen.idTipoSolicitud eq idMercanciaConXml || facturaResumen.idTipoSolicitud eq idMercanciaSinXml}">
													<c:if test="${empty facturaResumen.numeroProveedorProveedor}">
														<c:set var="conSinXML" value="${facturaResumen.proveedor}" />
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedorProveedor}">
														<c:set var="conSinXML" value="${facturaResumen.numeroProveedorProveedor} - ${facturaResumen.proveedor}" />
													</c:if>
													<td><c:out value="${conSinXML}" /></td>
													
												</c:if> 
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo
																|| facturaResumen.idTipoSolicitud eq idComprobacionAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje}">
													<td><c:out value="${facturaResumen.numeroProveedor} - ${name}" /></td>
												</c:if>
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idCajaChica}">
													<c:if test="${empty facturaResumen.numeroProveedorProveedorAsesor || facturaResumen.numeroProveedorProveedorAsesor eq null}">
														<c:set var="cajaChica" value="${name}" />
														
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedorProveedorAsesor}">
														<c:set var="cajaChica" value="${facturaResumen.numeroProveedorProveedorAsesor} - ${facturaResumen.usuarioAsesor}" />
													</c:if>
													<td><c:out value="${cajaChica}" /></td>
												</c:if>
												
												<c:if test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
													<c:if test="${empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${name}" />
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${facturaResumen.numeroProveedor} - ${facturaResumen.usuarioSolicita}" />
													</c:if>
													<td><c:out value="${reembolso}" /></td>
												</c:if> 
													<!-- Folio factura -->
													<c:choose>
														<c:when test="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje}">
													        <td class="soft"><c:out value="<%=etiqueta.NO_APLICA%>" /></td> 
													    </c:when>
													    
														<c:otherwise>
													        <td><c:out value="${facturaResumen.factura}" /></td>
													    </c:otherwise>
												    </c:choose>   
													
													<!-- Fecha factura -->
													<c:choose>
														<c:when test="${facturaResumen.idTipoSolicitud eq idKilometraje || facturaResumen.idTipoSolicitud eq idAnticipo || facturaResumen.idTipoSolicitud eq idAnticipoViaje}">
													        <td class="soft"><c:out value="<%=etiqueta.NO_APLICA%>" /></td> 
													    </c:when>
													    
														<c:otherwise>
													        <td class="fecha-col sort-date" style="width:110px"><fmt:formatDate pattern="yyyy/MM/dd" value="${facturaResumen.fechaFactura}" /></td>
													    </c:otherwise>
												    </c:choose>  
												    
													
													
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
																<a href="#"
																	onclick="verDetalleMontoFacturas(${facturaResumen.idSolicitud});">
																	$<span class="currencyFormat">
																		<c:out value="${facturaResumen.montoTotal}" />
																	</span>
																</a>
															</c:when>
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idMercanciaSinXml}">
																<a href="#"
																	onclick="verDetalleMontoFacturas(${facturaResumen.idSolicitud});">
																	$<span class="currencyFormat">
																		<c:out value="${facturaResumen.montoTotal}" />
																	</span>
																</a>
															</c:when>
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
																<a href="#"
																	onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																	$<span class="currencyFormat">
																		<c:out value="${facturaResumen.montoTotal}" />
																	</span>
																</a>
															</c:when>
															
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idAnticipo}">
																<a href="#"
																	onclick="verDetalleMontoAnticipos(${facturaResumen.idSolicitud});">
																	$<span class="currencyFormat">
																		<c:out value="${facturaResumen.montoTotal}" />
																	</span>
																</a>
															</c:when>
															
															<c:when
																test="${facturaResumen.idTipoSolicitud eq idKilometraje}">
																<a href="#"
																	onclick="verDetalleMontoKilometrajes(${facturaResumen.idSolicitud});">
																	$<span class="currencyFormat">
																		<c:out value="${facturaResumen.montoTotal}" />
																	</span>
																</a>
															</c:when>
															
															
															
															<c:otherwise>
															<a href="#"
																onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
																$<span class="currencyFormat">
																	<c:out value="${facturaResumen.montoTotal}" />
																</span>
															</a>
															</c:otherwise>
															
															
														</c:choose></td>
													<td><c:out value="${facturaResumen.monedaSolicitud}" /></td>
													<%-- <td><c:out value="${facturaResumen.creacionFecha}" /></td> --%>
													<td class="sort-date"><fmt:formatDate pattern="yyyy/MM/dd" value="${facturaResumen.creacionFecha}" /></td>
													<td><c:out value="${facturaResumen.usuarioSolicita}" /></td>
													<td><a href="#"
														onclick="verDetalleEstatus(${facturaResumen.idSolicitud});">
															<c:out value="${facturaResumen.estadoAutorizacion}" />
													</a></td>
													<%-- <td><c:out value="${facturaResumen.ultimaFechaAutoriza}" /></td> --%>
													
													<c:if test="${idEstadoSolicitud == 2 || idEstadoSolicitud == 3}">
													  <td class="sort-date"><fmt:formatDate pattern="yyyy/MM/dd" value="${facturaResumen.ultimaFechaAutoriza}" /></td>
													</c:if>
													<td><c:choose>
															<c:when
																test="${facturaResumen.conceptoGasto.length() > 100}">
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
														</c:choose></td>
													<td>
															<button onclick="verDetalle(${facturaResumen.idSolicitud})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-success">
														<span class="fa fa-files-o"></span>
													</button>
													</td>
													<td style="text-align:center"><form:checkbox
															id="ckcAutorizar${facturaResumen.idSolicitud}"
															value="${facturaResumen.autorizar}"
															path="vwSolicitudResumenAutorizaciones[${status.index}].autorizar"
															onchange="validarCboAutorizar(${facturaResumen.idSolicitud})" /></td>
													<td style="text-align:center"><form:checkbox
															id="ckcRechazo${facturaResumen.idSolicitud}"
															value="${facturaResumen.rechazar}"
															path="vwSolicitudResumenAutorizaciones[${status.index}].rechazar"
															onchange="validarCboRechazar(${facturaResumen.idSolicitud})"
															disabled="${facturaResumen.autorizar}" /></td>
													<td><form:input
															id="rechazo${facturaResumen.idSolicitud}" type="text"
															value="${facturaResumen.motivoRechazo}"
															class="form-control input-sm" style="width:100%"
															path="vwSolicitudResumenAutorizaciones[${status.index}].motivoRechazo"
															disabled="${!facturaResumen.rechazar}" /></td>
													
													<td>
														<c:choose>
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
																<span  style="background-color: ${facturaResumen.colorSolicitud};" class="label label-info fixLabel">
												             		<c:out value="${facturaResumen.idSolicitud}" />
														        </span>
															</c:otherwise>
														</c:choose>
													</td>
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
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

		</div>

	</div>
	<!-- /#page-wrapper -->

	
	<!-- Ventana modal Confirma Autorización -->
		<div class="modal fade" id="confirma-autorizacion" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
					<div class="modal-content">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo">Confirma autorización</span>
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
							<!-- Fin de alertas -->


							<div class="panel-body">
								<!-- Aviso -->
								<div class="row">
									<div class="col-xs-3">
									<div class="glyphicon glyphicon-alert" style="margin-bottom: 15px; padding-right: 8px; font-size: 75px; vertical-align: -1px; text-align: center; left: 19%; color:#ddd;"></div>
									</div>
									<div class="col-xs-9">
										<p style="text-align:justify">¿Está seguro de procesar las Solicitudes especificadas?</p>
									</div>
								</div>

								<div class="form-group col-xs-12" style="border-top: 2px dotted #ccc;" >

									<div onclick="" id="cancelar" class="btn btn-default" style="float: right;margin-bottom: 1px;margin-top: 16px;">
										<span><%=etiqueta.CANCELAR%></span>
									</div>
								
									<div onclick="" id="acepta-aviso" class="btn btn-danger" style="float: right;margin-bottom: 1px;margin-top: 16px; margin-right:14px;">
										<span><%=etiqueta.DE_ACUERDO%></span>
									</div>
									
								</div>
																		
							</div>
						</div>
					</div>

			</div>
			<a href="" class="aviso-ventana" data-toggle="modal" data-target="#confirma-autorizacion" style="display:none">ventana</a>
		</div>


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
    	
    	console.log("test");
		console.log("<%=errorHead%>");
		var mensajeSuccess = '${mensajeSuccess}';
		
		
		//console.log("mensaje: " + mensajeSuccess)
    	
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
	    
    	}

		$('.currencyFormat').number( true, 2 );    	
        $('#dataTables-example').DataTable({
            responsive: true,
            dom: 'Blfrtip',
            buttons: ['excel']
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
        
        
        // Botón dinámico
        btnProcesa = '<a class="buttons-excel" id="procesador" style="margin-left: 8px;"><%=etiqueta.BTN_PROCESAR%></a>';
          btnProcesa += '<button class="boton-submit" type="submit" style="display:none"></button>';
         $('.buttons-excel').before(btnProcesa);
        
       // $('.dt-buttons').detach().before('.panel-main .row');
        
        //$('.dt-buttons').eq(1).remove();
/*         console.log('2',$('.dt-buttons').length); */
        
        validarComentarios();
        
        
/*         d = new Date();
        var hoy = d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear();
        $('#fechaInicial').val(hoy);
        $('#fechaFinal').val(hoy); */
        
        
        $('.dataTables_filter').css('position','relative');
        $('.dt-buttons').css('position','relative');
        $('#dataTables-example_info').css('position','relative');
        $('#dataTables-example_paginate').css('position','relative');
        $("#dataTables-example_wrapper").scroll(function (e) {
        	console.log("Scrollleft:",$("#dataTables-example_wrapper").scrollLeft());
        	var left = $("#dataTables-example_wrapper").scrollLeft();
        	$('.dataTables_filter').css('left',left);
        	$('.dt-buttons').css('left',left);
        	$('#dataTables-example_info').css('left',left);
        	$('#dataTables-example_paginate').css('left',left);
        });
        fixDate();	
        resetColResizable();
    });

     $('#date-selector-from .input-daterange').datepicker({
		    format: 'mm/dd/yyyy',
		    language: 'es',
	 });
     

     
     function verDetalleConcepto(idSolicitudResumen) {
         
 		if(idSolicitudResumen > 0){
         	console.log(idSolicitudResumen);
         	loading(true);
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getSolicitud",
                 async: true,
                 data: "intxnId=" + idSolicitudResumen,
                 success: function(response) {
                	 loading(false);
                 	console.log(response);
                 	$("#conceptoGasto").val(response.conceptoGasto);
                 	$("#idSolicitud").val(response.idSolicitud);
                 	//abrir ventana modal
                 	$('#modalConceptoGasto').modal('show'); 
                 },
                 error: function(e) {
                	 loading(false);
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
     
     function verDetalleMontoFacturas(id) {
         alert(id);
 		if(id > 0){
         	console.log(id);
         
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoFactura",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                	console.log(result);
                 	$("#tablaDetalleEstatus").empty().append(result.tabla);
                 	$("#libro_contable").text(result.libro_contable);
                 	$("#par").text(result.par);
                 	$("#totales").text(result.totales);
                 	if(result.track_asset== "true"){
                 		$("#checkbox").show();
                 		$("#parDiv").show();
                 		$("#libro_contable").show();
                		$("#libro_contable_lbl").show();
                 		//$("#track_asset").show();
                 	}else{
                 		$("#checkbox").hide();
                 		$("#parDiv").hide();
                 		$("#libro_contable").hide();
                		$("#libro_contable_lbl").hide();
                 		//$("#track_asset").hide();
                 	}
                 	$('.currencyFormat').number( true, 2 );
                 	// mostrar modal
                 	$('#modalMontoFacturas').modal('show'); 
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
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
     function verDetalleReembolsos(id) {
         
 		if(id > 0){
         	console.log(id);
         
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoReembolso",
                 async: true,
                 data: "intxnId=" + id,
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
     
     function validarCboAutorizar(idSolicitud){
    	 
    	 var checked = $("#ckcAutorizar"+idSolicitud).is(":checked");
		 $("#ckcRechazo"+idSolicitud).attr("disabled",checked);
    	 if (checked){  
       	 	$("#rechazo"+idSolicitud).attr("disabled",checked);
    	 }
    	
     }
     
function validarCboRechazar(idSolicitud){
    	 
    	 var checked = $("#ckcRechazo"+idSolicitud).is(":checked");
    	 $("#rechazo"+idSolicitud).attr("disabled",!checked);
    	 $("#ckcAutorizar"+idSolicitud).attr("disabled", checked);  
    	
    	 if (!checked){
    		 $("#rechazo"+idSolicitud).val(null);
    	 }
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
			
			
			$("#error-head").text("<%= etiqueta.ATENCION %>");
			$("#error-body").text("<%=etiqueta.ERROR_ESPECIFICAR_FECHAS%>");
			
			$("#error-alert").show();
			
			console.log("false");
			return false;
		}else{
			console.log("true");
			return true;
		}
		
	}
	
	
    //----------------------------------------------------------------------
	function validarComentarios () {
		//console.log('[1]:Iniciado validación de comentarios. '+$('#procesador').length);
		 $('#procesador').click(function () {
			 //console.log('[2]:Inicia validación');
			 $rows = $('tbody .gradeX');
			 //console.log('rows',$rows.length);
			 //console.log('[3]:Checar rechazo');
			 var validado = true;
			 var total = $("input:checkbox:checked",'#dataTables-example').length;
			 $.each($rows,function (index) {
				 //console.log('['+index+']');
				 $row = $rows.eq(index);
				 $checkboxRechazo = $("input:checkbox",$row).eq(1);
				 //console.log('check',$checkboxRechazo.length);
				 $comentario = $("input:text",$row).eq(0);
				 
				 var check = $checkboxRechazo.is(":checked");
				 //console.log('total',total);
				 
				 //console.log('check',$checkboxRechazo.is(":checked"));
				 if (check == true) {
					// console.log('[4]:Validar comentario:',check);
					 if ($comentario.val() != "") {
						 //console.log('validado el comentario',$comentario.val());
						 $comentario.removeClass('errorx');
						
					 }
					 else {
						 $comentario.addClass('errorx');
						 $("#error-head").text(ERROR);
							$("#error-body").text("Es necesario capturar el campo: Comentarios de Rechazo");
							error_alert();
						 validado = false;
					 }
				 }
				 else {
					 $comentario.removeClass('errorx');
				 }
			 });
			 
			 if (total > 0) {
				 if (validado) {
					 //console.log(validado);
					 $('.aviso-ventana').click();
					 
					 
					 $('#acepta-aviso').click(function (){
						 $('#confirma-autorizacion').modal('toggle');
						 $('#acepta-aviso').unbind('click');
						 $(".boton-submit").click();
					 });
					 
					 
					 $('#cancelar').click(function (){
						 $('.close').click();
						 $('#cancelar').unbind('click');
					 });
					 
					 
					 
				 }
			 }
			 
			 else {
					$("#error-head").text(ERROR);
					$("#error-body").text("Es necesario seleccionar al menos un elemento para autorizar o rechazar");
					error_alert();
					}
			 
			 //console.log('[5]:Validación final');
			 //console.log('[6]:Mandar Submit');
			 
		 });
	}
	//----------------------------------------------------------------------
	
    </script>

	<!-- Scripts especificos ....  -->
<script	src="${pageContext.request.contextPath}/resources/js/detalleDocumentos.js"></script>
<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
</body>
</html>
