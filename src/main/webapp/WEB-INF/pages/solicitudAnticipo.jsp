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

	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">

						<h1 class="page-header">Listado de Solicitudes</h1>

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
						<div class="panel-body">
							<form:form method="post" enctype="multipart/form-data"
								action="iniciarBusqueda" modelAttribute="busquedaFechasIdDTO">
								<form:hidden value="${idEstadoSolicitud}" path="idBusqueda" />
								<div id="date-selector-from" class="col-md-5" style="padding: 0px;">
									<div class="input-daterange input-group" id="datepicker">
										<form:input type="text" class="input-sm form-control"
											path="fechaInicial"
											value="${busquedaFechasIdDTO.fechaInicialString}"
											name="start" />
										<span class="input-group-addon"><%=etiqueta.AL%></span>
										<form:input type="text" class="input-sm form-control"
											path="fechaFinal"
											value="${busquedaFechasIdDTO.fechaFinalString}" name="end" />
									</div>
								</div>
								<div class="col-md-1" style="padding: 0px;">
									<button type="submit" class="btn btn-xs" id="buscarPorFecha"
										onclick="return validarRangoFechas()">
										<span class="fa fa-search fa-2x" style="font-size: 22px;"></span>
									</button>
								</div>
							</form:form>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table style="font-size: 90%;"
									class="table table-striped table-bordered table-hover display nowrap"
									id="dataTables-example">
									<thead>
										<tr>
											<th>Comprobar</th>
											<th><%=etiqueta.BENEFICIARIO%></th>
											<th><%=etiqueta.FECHA_SOLICITUD%></th>
											<th><%=etiqueta.IMPORTE%></th>
											<th><%=etiqueta.FECHA_DEPOSITO%></th>
											<th>Saldo a comprobar</th>
											<th><%=etiqueta.MONEDA%></th>
											<th><%=etiqueta.CONCEPTO%></th>
											<th><%=etiqueta.NUMERO_SOLICITUD%></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vwSolicitudResumen}" var="facturaResumen">
											<tr class="odd gradeX">
												<!-- Comprobar -->
												<td><a href="">Ir</a></td>
												
												<!-- Beneficiario -->
												<c:set var="name" value="${facturaResumen.nombre} ${facturaResumen.apellidoPaterno} ${facturaResumen.apellidoMaterno}" />
													<td><c:out value="${name}" /></td>
												
												<!-- Fecha de Solicitud -->
												<c:set var="fecha" value="${facturaResumen.idTipoSolicitud eq idKilometraje}" />
												<td><fmt:formatDate pattern="dd-MM-yyyy" value="${facturaResumen.fechaFactura}" /></td>
												
												<!-- Importe -->
												<td>
													<c:out value="${facturaResumen.montoTotal}" /></span>
												</td>
												
												<!-- Fecha de Solicitud -->
												<c:set var="fecha" value="${facturaResumen.idTipoSolicitud eq idKilometraje}" />
												<td><fmt:formatDate pattern="dd-MM-yyyy" value="${facturaResumen.fechaFactura}" /></td>
												
												<!-- Importe -->
												<td>
													<a href="#" onclick="verDetalleReembolsos(${facturaResumen.idSolicitud});">
														$<span class="currencyFormat">
														<c:out value="${facturaResumen.montoTotal}" /></span>
													</a>
												</td>
												
												<!-- Moneda -->
												<td>Pesos</td>
												
												<!-- Concepto -->
												
												
			
												
												<td><c:choose>
														<c:when test="${facturaResumen.conceptoGasto.length() > 8}">
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.conceptoGasto,0,8)}..." />
															</a>
														</c:when>
														<c:otherwise>
															<a href="#"
																onclick="verDetalleConcepto(${facturaResumen.idSolicitud});">
																<c:out
																	value="${fn:substring(facturaResumen.conceptoGasto,0,8)}" />
															</a>
														</c:otherwise>
													</c:choose> 
												</td>


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
														
														<c:otherwise>
															<c:out value="${facturaResumen.idSolicitud}" />
														</c:otherwise>
													</c:choose></td>
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






	<!-- Modal concepto gasto-->
	<div class="modal fade" id="modalConceptoGasto" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div style="width: 645px;" class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_CONCEPTO%></h4>
				</div>

				<div class="panel-body">
					<div id="error-sign" style="display: none;"
						class="alert alert-warning">
						<strong><%=etiqueta.ATENCION%></strong>
						<%=etiqueta.COMPLETE%>
					</div>

					<div class="form-group">
						<div class="control-label col-xs-3">
							<label><%=etiqueta.CONCEPTO_DEL_GASTO%>:</label>
						</div>
						<div class="col-xs-6">
							<textarea style="width: 391px; height: 169px;" id="conceptoGasto" style="margin-bottom: 10px;"
								disabled="true" class="form-control" rows="5" cols="1"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- modal montoFacturas -->
	<div class="modal fade" id="modalMontoFacturas" role="dialog">
		<div style="width: 1075px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_MONTO%></h4>
				</div>

				<div class="panel-body">

					<div class="form-group">
						<div class="col-xs-3">
							<div class="checkbox" style="display: none;" id="checkbox">
								<input type="checkbox" checked disabled="disabled"
									id="track_asset" /><%=etiqueta.TRACK_ASSET%>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="col-xs-4">
							<label id="libro_contable_lbl"><%=etiqueta.LIBRO_CONTABLE%>: </label> <span
								id="libro_contable"></span>
						</div>
					</div>

					<div class="form-group" id="parDiv">
						<label><%=etiqueta.PAR%>: </label> <span id="par"></span>
					</div>

					<br />
					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="tablaModal">
							<thead>
								<tr>
									<th><%=etiqueta.LINEA%></th>
									<th><%=etiqueta.SUBTOTAL%></th>
									<th><%=etiqueta.LOCACION%></th>
									<th><%=etiqueta.CUENTA_CONTABLE%></th>
									<th><%=etiqueta.CONCEPTO%></th>
									<th><%=etiqueta.AID%></th>
									<th><%=etiqueta.CATEGORIA_MAYOR%></th>
									<th><%=etiqueta.CATEGORIA_MENOR%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleEstatus">

							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->

<%-- 					<div class="form-group">
						<label><%=etiqueta.TOTALES%>: </label> $<span
							class="currencyFormat" id="totales"></span>
					</div> --%>

				</div>
			</div>
		</div>
	</div>
	<!-- Modal detalleEstatus -->
	<div class="modal fade" id="modalEstatus" role="dialog">
		<div style="width: 759px;" class="modal-dialog">

			<!-- Modal content-->
			<form:form id="detalleConceptoForm" cssClass="form-horizontal"
				modelAttribute="solicitud" method="post" action="">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"><%=etiqueta.DETALLE_ESTATUS%></h4>
					</div>

					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover"
								id="tablaModal">
								<thead>
									<tr>
										<th><%=etiqueta.NIVEL%></th>
										<th><%=etiqueta.NOMBRE%></th>
										<th><%=etiqueta.PUESTO%></th>
										<th><%=etiqueta.ESTATUS%></th>
										<th><%=etiqueta.FECHA_AUTORIZACION_RECHAZO%></th>
									</tr>
								</thead>
								<tbody id="tablaDetalle">

								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<!-- Modal detalleReembolsos -->

	<div class="modal fade" id="modalReembolsos" role="dialog">
		<div style="width: 785px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_MONTO%></h4>
				</div>

				<div class="panel-body">

					<div class="form-group">
						<div class="col-xs-4">
						<button id="desglose">Ver desglose</button>
							<label><%=etiqueta.DESGLOSE_SOLICITUD%> </label>
						</div>
						
						<div class="desglose-tabla img-rounded" id="desglose-tabla" role="dialog"style="display:none;">
						<table class="table table-striped table-bordered table-hover"
							id="tabla-desglose-emergente">
							<div class="row">
								<h4 class="col-xs-5 col-offset-xs-6">Título del desglose</h4>
								<div class="col-xs-1 close">×</div>
							</div>
							<thead>
								<tr>
									<th><%=etiqueta.LINEA%></th>
									<th><%=etiqueta.SUBTOTAL%></th>
									<th><%=etiqueta.LOCACION%></th>
									<th><%=etiqueta.CUENTA_CONTABLE%></th>
									<th><%=etiqueta.CONCEPTO%></th>
									<th><%=etiqueta.AID%></th>
									<th><%=etiqueta.CATEGORIA_MAYOR%></th>
									<th><%=etiqueta.CATEGORIA_MENOR%></th>
								</tr>
							</thead>
							<tbody id="tabla-desglose-data">
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Congdrgrdh sumibles</td>
									<td>dfgr  gf dr</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>fsdgsrv </td>
									<td>8564974</td>
									<td>gdfgr noacidos</td>
									<td>23</td>
									<td>grg </td>
									<td>sdfger</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$20.00</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoasdfsacidos</td>
									<td>23</td>
									<td>Consa  a sdaumibles</td>
									<td>Ali asd fasffementos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$5.18</td>
									<td>Monterrey</td>
									<td>454545</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$256.68</td>
									<td>Monterrey</td>
									<td>8564974</td>
									<td>Aminoasdfsacidos</td>
									<td>23</td>
									<td>Consa  a sdaumibles</td>
									<td>Ali asd fasffementos</td>
								</tr>
								<tr>
									<td>1</td>
									<td>$5.18</td>
									<td>Monterrey</td>
									<td>454545</td>
									<td>Aminoacidos</td>
									<td>23</td>
									<td>Consumibles</td>
									<td>Alimentos</td>
								</tr>
								
								
							</tbody>
						</table>
					</div>
						
						
					</div>
					<br />
					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="tablaModal">
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
								</tr>
							</thead>
							<tbody id="tablaDetalleReembolsos">

							</tbody>
							<!-- </table> -->

							<br>
							<!-- <table FRAME="void" RULES="rows"> -->
							<!-- <table border="0" class="table "> -->
							<tr>
								<th><%=etiqueta.TOTALES%>:</th>
								<td><span class="currencyFormat" id="totalesReembolsos"></span></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<th><span class="currencyFormat" id="ivaReembolsos"></span></th>
								<th><span class="currencyFormat" id="iepsReembolsos"></span></th>
							</tr>
							<tr>
								<td style="border: hidden"></td>
								<td style="border: hidden"></td>
								<td style="border: hidden"></td>
								<td style="border: hidden"></td>
								<td style="border: hidden"></td>
								<th style="border: hidden"><%=etiqueta.REEMBOLSO_TOTAL%></th>
								<td style="border: hidden"></td>
								<th style="border: hidden"><span class="currencyFormat"
									id="rTotalReembolsos"></span></th>
							</tr>

						</table>
					</div>
					<!-- /.table-responsive -->
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="modal" role="dialog">
		<div style="width: 1100px; min-height: 650px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_DOCUMENTOS%></h4>
				</div>

				<div class="panel-body">

					<div class="row show-grid">
						<div class="col-xs-6 col-md-4">
							<div class="panel panel-red">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_PDF%> </label> <input
										id="filter_pdf" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca PDF's">
								</div>
								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_pdf" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_pdf">

											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="panel panel-green">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_XML%> </label> <input
										id="filter_xml" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca XML's">
								</div>

								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_xml" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_xml">

											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div id="documentos_comprobante" class="panel panel-yellow">
								<div style="overflow: auto;" class="panel-heading">

									<label><%=etiqueta.ARCHIVOS_COMPROBANTE%> </label> <input
										id="filter_comprobante" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca Comprobantes">
								</div>
								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_comprobante" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_comprobante">

											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="panel panel-primary">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_SOPORTE%> </label> <input
										id="filter_soporte" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca Comprobantes">
								</div>
								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_soporte" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_soporte">

											</tbody>
										</table>
									</div>
								</div>
							</div>









						</div>
						<div style="min-height: 715px;"
							class="col-xs-12 col-sm-6 col-md-8">

							<!-- 16:9 aspect ratio -->
							<div id="display_pdf" style="min-height: 715px; display: none;"
								class="embed-responsive embed-responsive-16by9"></div>


						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<jsp:include page="template/includes.jsp" />

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
		console.log("<%=errorHead%>");
    	
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
    	$('.currencyFormat').number( true, 2 );
        $('#dataTables-example').dataTable({
                responsive: true,
                dom: 'Bfrtip',
                buttons: [
                    'excel'
                ]
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	/* $("#descripcion").val(null);
        	$("#idPerfil").val(null); */
        });
    });

     $('#date-selector-from .input-daterange').datepicker({
		    format: 'mm/dd/yyyy',
		    language: 'es'
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
         
 		if(id > 0){
         	console.log(id);
         
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoFactura",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
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
     
   //VALIDACION RANGO DE FECHAS 
 	function validarRangoFechas(){
 		$("#error-alert").hide();
 		var ban = false;
 		
 		
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
	<script src="${pageContext.request.contextPath}/resources/js/tablaDesglose.js"></script>
	
</body>
</html>
