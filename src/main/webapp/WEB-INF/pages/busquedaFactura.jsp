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

.custom-folio {

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

#dataTables-example_wrapper {
	overflow-x: scroll;
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

	<jsp:include page="template/nav_superior.jsp" />

	<!-- Page Content -->
	<div id="page-wrapper">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header"><%=etiqueta.BUSQUEDA%>
						<%=etiqueta.FACTURAS%></h1>

				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<div style="display: none;" id="error-alert"
			class="alert alert-danger fade in">
			<strong> <span id="error-head"><%=errorHead%></span></strong> <span
				id="error-body"><%=errorBody%></span>
		</div>

		<div style="display: none;" id="error-alert-modal"
			class="alert alert-warning fade in">
			<strong><span id="error-head-modal"><%=etiqueta.ERROR%></span></strong>
			<span id="error-body-modal"><%=etiqueta.COMPLETE%></span>
		</div>

		<!-- /.container-fluid -->
		<form:form method="post" enctype="multipart/form-data; charset=UTF-8"
			action="busquedaFacturaBuscar" modelAttribute="busquedaFacturaDTO"
			class="img-rounded busquedaForm">
			<div class="row header-search">
				<div class="col-md-12">

					<h4 style="" class="col-md-10">
						<%=etiqueta.ESPECIFIQUE_CRITERIOS_BUSQUEDA%>
					</h4>

					<button id="buscar" onclick="return validar();"
						style="float: left;" type="submit"
						class="btn btn-primary col-md-2">
						<%=etiqueta.BUSCAR%></button>

					<a id="mostrar-busqueda" onclick="" style="display: none"
						class="btn btn-primary col-md-2"> <%=etiqueta.MOSTRAR%></a>

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
									<option
										${compania.idcompania == filtros.idCompaniaFiltro  ? 'selected' : ''}
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
					<div class="col-md-4">
						<div class="form-group">
							<label><%=etiqueta.COL_FOLIO_FACTURA%>:</label>
							<form:input cssClass="form-control custom-folio" path="facturaFiltro"
								value="${filtros.facturaFiltro}" />
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
									<option
										${tipoSolicitud.idTipoSolicitud == filtros.idTipoSolicitudFiltro  ? 'selected' : ''}
										value="${tipoSolicitud.idTipoSolicitud}">${tipoSolicitud.descripcion}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<!--  combo estatus solicitud -->
							<label><%=etiqueta.ESTATUS_SOLICITUD%>:</label><br>
							<form:select path="idEstadoSolicitudFiltro"
								cssClass="form-control selectpicker custom-estatus-solicitud" data-live-search="true"
								id="estadoSolicitud">
								<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
								<c:forEach items="${estadoSolicitudList}" var="estadoSolicitud">
									<option
										${estadoSolicitud.idEstadoSolicitud == filtros.idEstadoSolicitudFiltro  ? 'selected' : ''}
										value="${estadoSolicitud.idEstadoSolicitud}">${estadoSolicitud.estadoSolicitud}</option>
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
					<div class="col-md-4">
						<!--  combo locaciones -->
						<div class="form-group">
							<label for="locacion_list"><%=etiqueta.LOCACION_SOLICITANTE%>:</label><br>
							<form:select path="idLocacionFiltro"
								cssClass="form-control selectpicker custom-solicitud-solicitante " data-live-search="true"
								id="locacion_list">
								<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
								<c:forEach items="${locacionList}" var="locacion">
									<option
										${locacion.idLocacion == filtros.idLocacionFiltro  ? 'selected' : ''}
										value="${locacion.idLocacion}">${locacion.numeroDescripcionLocacion}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<c:if
						test="${usuarioActual.esAutorizador eq 1 || usuarioActual.puesto.idPuesto eq puestoAP}">
						<div class="col-md-4">
							<!--  combo solicitante -->
							<div class="form-group">
								<label><%=etiqueta.SOLICITANTE%>:</label><br>
								<form:select id="usuarioSolicitante"
									path="idUsuarioSolicitanteFiltro"
									cssClass="form-control selectpicker custom-solicitud-solicitante" data-live-search="true">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
									<c:forEach items="${solicitantesList}" var="solicitante">
										<option
											${solicitante.idUsuario == filtros.idUsuarioSolicitanteFiltro  ? 'selected' : ''}
											value="${solicitante.idUsuario}">
											${solicitante.nombre} ${solicitante.apellidoPaterno}
											${solicitante.apellidoMaterno}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</c:if>
					<div class="col-md-4">
						<c:if
							test="${usuarioActual.puesto.idPuesto eq puestoAP || usuarioActual.puesto.idPuesto eq puestoConfirmacionAP}">
							<!--  combo autorizador -->
							<div class="form-group">
								<label><%=etiqueta.AUTORIZADOR%>:</label>
								<form:select id="usuarioAutorizador"
									path="idUsuarioAutorizadorFiltro"
									cssClass="form-control selectpicker" data-live-search="true">
									<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />

									<c:forEach items="${autorizadoresList}" var="autorizador">
										<option
											${autorizador.idUsuario == filtros.idUsuarioAutorizadorFiltro  ? 'selected' : ''}
											value="${autorizador.idUsuario}">
											${autorizador.nombreCompletoUsuario}</option>
									</c:forEach>

								</form:select>
							</div>
						</c:if>
					</div>
				</div>
				<div class="col-md-4"></div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="col-md-4">
						<div class="form-group">
							<label for="tipoSolicitud_list"><%=etiqueta.COL_FECHA_FACTURA%>:</label>
							<div id="date-selector-from">
								<div class="input-daterange input-group custom-fecha" id="datepicker">

									<c:if
										test="${filtros.fechaFacturaInicialFiltro ne null || empty filtros.fechaFacturaInicialFiltro}">
										<c:set var="fechaInicial"
											value="${filtros.fechaFacturaInicialFiltro}" />
									</c:if>

									<c:if
										test="${filtros.fechaFacturaFinalFiltro ne null || empty filtros.fechaFacturaFinalFiltro}">
										<c:set var="fechaFinal"
											value="${filtros.fechaFacturaFinalFiltro}" />
									</c:if>

									<form:input type="text"
										class="input-sm form-control small-date"
										path="fechaFacturaInicialFiltro" value="${fechaInicial}"
										name="start" id="start" />
									<span class="input-group-addon"><%=etiqueta.AL%></span>
									<form:input type="text"
										class="input-sm form-control small-date"
										path="fechaFacturaFinalFiltro" value="${fechaFinal}"
										name="end" id="end" />
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="form-group">
							<label><%=etiqueta.IMPORTE_FACTURA%> <%=etiqueta.ENTRE%></label>
							<div class="input-group custom-importe">
								<form:input cssClass="form-control currencyFormatII importe-total" path="strImporteFinalFiltro" id="importeMenor" style="font-size: 15px; width:100%" value="${filtros.strImporteFinalFiltro}"/>
								<span class="input-group-addon"><%=etiqueta.Y%></span>
								<form:input cssClass="form-control currencyFormatII importe-total custom-importe" path="strImporteInicialFiltro" id="importeMayor" style="font-size: 15px; width:100%" value="${filtros.strImporteInicialFiltro}"/>
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<!--  combo moneda -->
							<label><%=etiqueta.MONEDA%>:</label><br>
							<form:select path="idMonedaFiltro" id="moneda"
								cssClass="form-control selectpicker custom-moneda" data-live-search="true">
								<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
								<%-- <form:options items="${monedaList}" itemValue="idMoneda" itemLabel="descripcion" /> --%>

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
				</div>
				<div class="col-md-4"></div>
			</div>

			<div class="row">
				<div class="col-md-12">
				<!-- Se Oculta combo estatus factura  -->
					 <div class="col-md-4" style="display: none;"> 
						<div class="form-group ">
							<!-- combo estatus factura -->
							<label><%=etiqueta.ESTATUS_FACTURA%>:</label><br>
							<form:select path="idEstadoSolicitudFiltro"
								cssClass="form-control selectpicker custom-estatus-factura" data-live-search="true"
								id="estadoSolicitud">
								<form:option value="-1" label="<%=etiqueta.SELECCIONE%>" />
								<form:options items="${estatusList}"
									itemValue="idEstadoSolicitud" itemLabel="estadoSolicitud" />
							</form:select>
						</div>
					</div> 
					<div class="col-md-4">
						<div class="form-group">
							<label for="tipoSolicitud_list"><%=etiqueta.FECHA_PAGO_FACTURA%></label>
							<div id="date-selector-from">
								<div class="input-daterange input-group custom-fecha" id="datepicker">

									<c:if
										test="${filtros.fechaPagoFacturaInicialFiltro ne null || empty filtros.fechaPagoFacturaInicialFiltro}">
										<c:set var="fechaInicial"
											value="${filtros.fechaPagoFacturaInicialFiltro}" />
									</c:if>

									<c:if
										test="${filtros.fechaPagoFacturaFinalFiltro ne null || empty filtros.fechaPagoFacturaFinalFiltro}">
										<c:set var="fechaFinal"
											value="${filtros.fechaPagoFacturaFinalFiltro}" />
									</c:if>

									<form:input type="text"
										class="input-sm form-control small-date"
										path="fechaPagoFacturaInicialFiltro" value="${fechaInicial}"
										name="startPago" id="startPago" />
									<span class="input-group-addon"><%=etiqueta.AL%></span>
									<form:input type="text"
										class="input-sm form-control small-date"
										path="fechaPagoFacturaFinalFiltro" value="${fechaFinal}"
										name="endPago" id="endPago" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4"></div>
				</div>
				<div class="col-md-4"></div>
			</div>

		</form:form>
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<h4><%=etiqueta.LISTADO_FACTURAS%>:
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
			<div class="dataTable_wrapper scrollable" style="overflow-x: hidden;">
				<form:form method="post"
					enctype="multipart/form-data; charset=UTF-8" action=""
					modelAttribute="">
					<table class="table table-striped table-bordered table-hover"
						id="dataTables-example" style="font-size: 90%; margin-top: 12px;">
						<thead>
							<tr>
								<th style="min-width: 102px"><label><%=etiqueta.COL_TIPO_SOLICITUD%></label></th>
								<th style="min-width: 208px"><label><%=etiqueta.COL_COMPANIA%></label></th>
								<th style="min-width: 208px"><label><%=etiqueta.COL_PROVEEDOR%></label></th>
								<th style="min-width: 77px"><%=etiqueta.FACTURA%></th>
								<th style="min-width: 70px"><%=etiqueta.IMPORTE_FACTURA%></th>
								<th style="min-width: 72px"><label><%=etiqueta.COL_MONEDA%></label></th>
								<th style="min-width: 104px"><label><%=etiqueta.COL_FECHA_FACTURA%></label></th>
								<th style="min-width: 100px"><%=etiqueta.ESTATUS_SOLICITUD%></th>
								<th style="min-width: 160px"><%=etiqueta.LOCACION_SOLICITANTE%></th>
								<th style="min-width: 160px"><%=etiqueta.COL_SOLICITANTE%></th>
								<th style="min-width: 98px"><%=etiqueta.DOCUMENTOS%></th>
								<th style="min-width: 278px"><label><%=etiqueta.COL_CONCEPTO%></label></th>
								<th style="min-width: 97px"><%=etiqueta.FECHA_VENCIMIENTO%></th>
								<th style="min-width: 75px"><%=etiqueta.COL_NUM_SOLICITUD%></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${facturasBusqueda}" var="busqueda"
								varStatus="status">

								<tr class="odd gradeX">
									<td style="width: 9%;"><span
										style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
										class="label label-info fixLabel"> <c:out
												value="${busqueda.solicitud.tipoSolicitud.descripcion}" />
									</span></td>

									<td><c:out
											value="${busqueda.companiaByIdCompania.descripcion}" /></td>
									<td><c:choose>
											<%-- 												<c:when
													test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml}">
													<c:out value="${busqueda.proveedor.numeroProveedor} - ${busqueda.proveedor.descripcion}" />
												</c:when>
												<c:when
													test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml}">
													<c:out value="${busqueda.proveedor.numeroProveedor} - ${busqueda.proveedor.descripcion}" />
												</c:when>
												<c:when
													test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idReembolsos}">
													<c:out value="${busqueda.solicitud.usuarioByIdUsuarioSolicita.nombre} ${busqueda.solicitud.usuarioByIdUsuarioSolicita.apellidoPaterno}" />
												</c:when>
												<c:when
													test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idCajaChica}">
													<c:out value="${busqueda.solicitud.usuarioByIdUsuarioAsesor.nombre} ${busqueda.solicitud.usuarioByIdUsuarioAsesor.apellidoPaterno}" />
												</c:when> --%>


											<%-- <c:if test="${facturaResumen.idTipoSolicitud eq idReembolsos}">
													<c:if test="${empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${name}" />
													</c:if>
													<c:if test="${not empty facturaResumen.numeroProveedor}">
														<c:set var="reembolso" value="${facturaResumen.numeroProveedor} - ${facturaResumen.nombre} ${facturaResumen.apellidoPaterno}" />
													</c:if>
													<td><c:out value="${reembolso}" /></td>
												</c:if> --%>


											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idReembolsos}">

												<c:if
													test="${empty busqueda.solicitud.usuarioByIdUsuarioSolicita.numeroProveedor}">
													<c:out
														value="${busqueda.solicitud.usuarioByIdUsuarioSolicita.nombre} ${solBusqueda.usuarioByIdUsuario.apellidoPaterno}" />
												</c:if>
												<c:if
													test="${not empty busqueda.solicitud.usuarioByIdUsuarioSolicita.numeroProveedor}">
													<c:out
														value="${busqueda.solicitud.usuarioByIdUsuarioSolicita.numeroProveedor} - ${busqueda.solicitud.usuarioByIdUsuarioSolicita.nombre} ${busqueda.solicitud.usuarioByIdUsuarioSolicita.apellidoPaterno}" />
												</c:if>
											</c:when>

											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idCajaChica}">

												<c:if
													test="${empty busqueda.solicitud.usuarioByIdUsuarioAsesor.numeroProveedor}">
													<c:out
														value="${busqueda.solicitud.usuarioByIdUsuarioAsesor.nombre} ${busqueda.solicitud.usuarioByIdUsuarioAsesor.apellidoPaterno}" />
												</c:if>
												<c:if
													test="${not empty busqueda.solicitud.usuarioByIdUsuarioAsesor.numeroProveedor}">
													<c:out
														value="${busqueda.solicitud.usuarioByIdUsuarioAsesor.numeroProveedor} - ${busqueda.solicitud.usuarioByIdUsuarioAsesor.nombre} ${busqueda.solicitud.usuarioByIdUsuarioAsesor.apellidoPaterno}" />
												</c:if>

											</c:when>

											<c:when
												test="${solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml || solBusqueda.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml}">

												<c:if
													test="${empty solBusqueda.facturas[0].proveedor.idProveedor}">
													<c:out
														value="${solBusqueda.facturas[0].proveedor.numeroProveedor}" />
												</c:if>
												<c:if
													test="${not empty solBusqueda.usuarioByIdUsuarioAsesor.idUsuario}">
													<c:out
														value="${solBusqueda.facturas[0].proveedor.idProveedor} - ${solBusqueda.facturas[0].proveedor.numeroProveedor}" />
												</c:if>

											</c:when>


											<c:otherwise>
												<c:out
													value="${busqueda.proveedor.numeroProveedor} - ${busqueda.proveedor.descripcion}" />
											</c:otherwise>
										</c:choose></td>
									<td><c:out value="${busqueda.factura}" /></td>

									<c:choose>
										<c:when test="${busqueda.tipoFactura.idTipoFactura eq 2}">
											<td style="text-align: right" class="importe-rojo">$<span
												class="currencyFormat"><c:out
														value="${busqueda.total}" /></span></td>
										</c:when>
										<c:otherwise>
											<td style="text-align: right">$<span
												class="currencyFormat"><c:out
														value="${busqueda.total}" /></span></td>
										</c:otherwise>
									</c:choose>



									<td><c:out value="${busqueda.moneda.descripcion}" /></td>
									<td class="sort-date"><c:if
											test="${busqueda.fechaFactura != null}">
											<fmt:formatDate pattern="yyyy/MM/dd"
												value="${busqueda.fechaFactura}" />
										</c:if></td>
									<td><c:out
											value="${busqueda.solicitud.estadoSolicitud.estadoSolicitud}" /></td>
									<td><c:out
											value="${busqueda.solicitud.locacion.numeroDescripcionLocacion}" /></td>
									<td><c:out
											value="${busqueda.solicitud.usuarioByIdUsuarioSolicita.nombre} ${busqueda.solicitud.usuarioByIdUsuarioSolicita.apellidoPaterno}" /></td>
									<td><c:if test="${busqueda.archivoXML != null}">
											<button
												style="padding: 3px 4px; font-size: 9px; letter-spacing: 1px"
												type="button" class="btn btn-xs btn-success file-icon"
												title="<%=etiqueta.DESCARGAR%>"
												onclick="descargaDocumento('${busqueda.archivoXML}')">
												<span><%=etiqueta.XML%></span>
											</button>
										</c:if> <c:if test="${busqueda.archivoPDF != null}">
											<button
												style="padding: 3px 4px; font-size: 9px; letter-spacing: 1px"
												type="button" class="btn btn-xs btn-danger"
												title="<%=etiqueta.DESCARGAR%>"
												onclick="descargaDocumento('${busqueda.archivoPDF}')">
												<span><%=etiqueta.PDF%></span>
											</button>
										</c:if> <c:if test="${busqueda.archivoLibre != null}">
											<button
												style="padding: 3px 4px; font-size: 9px; letter-spacing: 1px"
												type="button" class="btn btn-xs btn-primary"
												title="<%=etiqueta.DESCARGAR%>"
												onclick="descargaDocumento('${busqueda.archivoLibre}')">
												<span><%=etiqueta.ARCHIVO_DOC%></span>
											</button>
										</c:if></td>
									<td><c:choose>
											<c:when test="${busqueda.conceptoGasto.length() > 100}">
												<a href="#"
													onclick="verDetalleConceptoFactura(${busqueda.idFactura});">
													<c:out
														value="${fn:substring(busqueda.conceptoGasto,0,100)}..." />
												</a>
											</c:when>
											<c:otherwise>
												<a href="#"
													onclick="verDetalleConceptoFactura(${busqueda.idFactura});">
													<c:out value="${fn:substring(busqueda.conceptoGasto,0,100)}" />
												</a>
											</c:otherwise>
										</c:choose></td>
									<%-- <td><c:if test="${busqueda.fechaFactura != null}"> <fmt:formatDate pattern="dd/MM/yyyy" value="${busqueda.fechaFactura}" /></c:if></td> --%>
									<td class="sort-date">${busqueda.fechaVencimiento}</td>
									<td><c:choose>
											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idMercanciaConXml && busqueda.solicitud.urlVisible eq true}">
												<a target="_blank" href="conXML?id=${busqueda.solicitud.idSolicitud}" > <span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> <c:out
															value="${busqueda.solicitud.idSolicitud}" />
												</span>
												</a>
											</c:when>
											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idMercanciaSinXml && busqueda.solicitud.urlVisible eq true}">
												<a target="_blank" href="sinXML?id=${busqueda.solicitud.idSolicitud}" > <span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> <c:out
															value="${busqueda.solicitud.idSolicitud}" />
												</span>
												</a>
											</c:when>
											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idReembolsos && busqueda.solicitud.urlVisible eq true}">
												<a target="_blank" href="reembolso?id=${busqueda.solicitud.idSolicitud}" > <span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> <c:out
															value="${busqueda.solicitud.idSolicitud}" />
												</span>


												</a>
											</c:when>
											<c:when
												test="${busqueda.solicitud.tipoSolicitud.idTipoSolicitud eq idCajaChica && busqueda.solicitud.urlVisible eq true}">
												<a target="_blank" href="cajaChica?id=${busqueda.solicitud.idSolicitud}" > <span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> <c:out
															value="${busqueda.solicitud.idSolicitud}" />
												</span>
												</a>
											</c:when>
											<c:otherwise>
												<span
													style="background-color: ${busqueda.solicitud.tipoSolicitud.colorSolicitud};"
													class="label label-info fixLabel"> <c:out
														value="${busqueda.solicitud.idSolicitud}" />
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
	<!-- /#page-wrapper -->


	<jsp:include page="template/includes.jsp" />
	<jsp:include page="detalleConcepto.jsp" />
	<jsp:include page="modalDocumentos.jsp" />

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
		var fechaFacturaFinal = '${filtros.fechaFacturaFinalFiltro}';
		var fechaFacturaInicial = '${filtros.fechaFacturaInicialFiltro}';
		var fechaPagoFacturaFinal = '${filtros.fechaPagoFacturaFinalFiltro}';
		var fechaPagoFacturaInicial = '${filtros.fechaPagoFacturaInicialFiltro}';
		var proveedorAsesor = '${numeroProveedorAsesor}';

	
	$(document).ready(function() {
		ventanaBusqueda("busquedaFactura","#busquedaFacturaDTO");
		console.log("<%=errorHead%>");
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
				var d = new Date();
		        var firstDayOfMonth = 1;
				var currMonth = d.getMonth();
				var currYear = d.getFullYear();
		        var startDate = new Date(currYear,currMonth,firstDayOfMonth);
				$('#start').datepicker('setDate',startDate);
				$('#startPago').datepicker('setDate',startDate);
		        $('#end').datepicker('setDate', d);
		        $('#endPago').datepicker('setDate', d);
 			}
 			else {
 				$('#start').datepicker();
				$('#start').val(fechaFacturaInicial);
	        	$('#end').datepicker();
	        	$('#end').val(fechaFacturaFinal);
	        	
 				$('#startPago').datepicker();
				$('#startPago').val(fechaPagoFacturaInicial);
	        	$('#endPago').datepicker();
	        	$('#endPago').val(fechaPagoFacturaFinal);
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
                $("#error-head").text("<%=etiqueta.ATENCION%>");
    			$("#error-body").text("<%=etiqueta.SIN_RESULTADOS%>");
    			error_alert_modal();
            });

        }
        fixDate();
        resetColResizable();
        
        //cargar proveedores 
        getProveedorAsesores(proveedorAsesor);
    });

     $('#date-selector-from .input-daterange').datepicker({
		    format: 'mm/dd/yyyy',
		    language: 'es'
	 });

     
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
				$("#error-body").text("<%=etiqueta.ERROR_SELECCIONAR_FECHA_MONEDA_FACTURA%>");
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
            async: true,
            success: function(result) {
            	$("#lista_proveedor").empty().append(result);
            	$('#proveedor').selectpicker('refresh');
            },
            error: function(e) {
                console.log('Error: ' + e);
            },
        }); }, 8000);
        
    	
        
        
        
}
	 </script>

	<script>
		   var rutaserver = '${pageContext.request.contextPath}';
	 </script>

	<script
		src="${pageContext.request.contextPath}/resources/js/detalleDocumentos.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/busquedaSolicitudFactura.js"></script>
</body>
</html>
