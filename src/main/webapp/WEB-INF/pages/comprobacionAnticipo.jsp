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

#anexarDocumentoForm .modal-header .close {
  color: white;
  opacity: .8;
}
  
#anexarDoc .modal-header {
  background-color: #004990; color: white;
}

#uploadFile {
  border: none;
  height: 28px;
  width: 185px;
  padding: 8px;
}

.fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
}
.fileUpload input.upload {
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
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

</style>
</head>

<body>


	<div id="wrapper">
		
		
		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">
			<form:form id="formComprobacionAnticipo" modelAttribute="comprobacionAnticipoDTO" action="saveComprobacionAnticipo" method="post">
			
				<form:hidden id="idSolicitudAnticipoSession" cssClass="blockedInput" value="${comprobacionAnticipoDTO.idSolicitudAnticipoSession}" path="idSolicitudAnticipoSession" />
				<form:hidden id="idSolicitudComprobacionSession" cssClass="blockedInput" value="${comprobacionAnticipoDTO.idSolicitudComprobacionSession}" path="idSolicitudComprobacionSession" />
				
				<form:hidden id="idComprobacionDeposito" value="${comprobacionAnticipoDTO.idComprobacionDeposito}" path="idComprobacionDeposito"/>
				<form:hidden id="idFechaDeposito" value="${comprobacionAnticipoDTO.fecha_deposito}" path="fecha_deposito"/>
				<form:hidden id="idImporteDeposito" value="${comprobacionAnticipoDTO.importeDeposito}" path="importeDeposito"/>
				<form:hidden id="idImporteTotal" value="${comprobacionAnticipoDTO.importeTotal}" path="importeTotal"/>
				
				<form:hidden id="idHasChange" value="${comprobacionAnticipoDTO.hasChange}" path="hasChange"/>
				
				<!-- Título de la sección ----------------------- -->
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<%= etiqueta.COMPROBACION_ANTICIPO_TITULO %>
							</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
				
				<!-- Alertas ----------------------- -->
				<div style="display: none;" id="error-alert-anticipo" class="alert alert-danger fade in">
					<strong><span id="error-head-anticipo"><%= etiqueta.ERROR %>:</span></strong>
					<span id="error-body-anticipo"><%= etiqueta.COMPLETE %></span>
				</div>
				<div style="display: none;" id="ok-alert-modal" class="alert alert-success fade in">
					<strong> <span id="ok-head-anticipo"> </span>
					</strong> <span id="ok-body-anticipo"><%= etiqueta.SOLICITUD_GUARDADA %>
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
					   <button onclick="verDetalleEstatus(${comprobacionAnticipoDTO.idSolicitudComprobacionSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button ${comprobacionAnticipoDTO.idSolicitudComprobacionSession > 0 ? '' : 'disabled'}
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
						<button id="save_button" onclick="valTodoComprobacion();"
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
									<form:select id="compania" path="compania.idcompania" class="form-control">
										<option title="<%= etiqueta.SELECCIONE %>" value="-1"><%= etiqueta.SELECCIONE %></option>
										<c:forEach items="${companiaList}" var="companiaList">
											<option ${companiaList.idcompania == comprobacionAnticipoDTO.compania.idcompania ? 'selected' : ''}
											value="${companiaList.idcompania}">${companiaList.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="row">
								<div class="col-md-4">
								   <div class="form-group">
									<label><%= etiqueta.FORMA_PAGO %>:</label> 
									<form:select id="formaPago" path="formaPago.idFormaPago" class="form-control blockedInput">

										<c:forEach items="${formaPagoList}" var="formaPagoList">
											<option ${formaPagoList.idFormaPago == comprobacionAnticipoDTO.formaPago.idFormaPago ? 'selected' : ''}
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
									<form:select onchange="refreshBeneficiario()" path="locacion.idLocacion" id="locacion" 
										class="form-control blockedInput">
										<c:forEach items="${locacionesPermitidas}" var="locacion">
										  <option ${comprobacionAnticipoDTO.locacion.idLocacion == locacion.idLocacion ? 'selected' : ''}
										   value="${locacion.idLocacion}">${locacion.descripcion}</option>
										</c:forEach>
										</form:select>
								</div>
							</div>
							<div class="col-md-8">
								<div id="radio-beneficiario" class="form-group radio" style="margin-top: 28px;">
									<label style="padding-left: 0px;"><strong><%= etiqueta.TIPO_DE_BENEFICIARIO %>:</strong></label>
									<label class="radio-inline"><form:radiobutton id="radioAsesor" onclick="getProveedoresAnticipo(1,0)" path="tipoProveedor.idTipoProveedor" value="1" disabled="true"/> <%= etiqueta.ASESOR %> </label>
									<label class="radio-inline"><form:radiobutton id="radioProveedor" onclick="getProveedoresAnticipo(2,0)" path="tipoProveedor.idTipoProveedor" value="2" disabled="true"/> <%= etiqueta.PROVEEDOR %> </label>
  								</div>
							</div>
						</div>
						
						<!-- ROW 3 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label><%= etiqueta.MONEDA %>:</label>
									 <form:select path="moneda.idMoneda" id="moneda" 
										class="form-control blockedInput">
										<c:forEach items="${monedaList}" var="monedas">
											<option ${monedas.idMoneda == comprobacionAnticipoDTO.moneda.idMoneda  ? 'selected' : ''}
											 value="${monedas.idMoneda}">${monedas.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label><%= etiqueta.BENEFICIARIO %>:</label> 
									<form:select  path="beneficiario" id="beneficiario" 
										class="form-control blockedInput">
										<option title="Pesos" value="-1" selected><%= etiqueta.SELECCIONE %></option>
									</form:select>
								</div>
							</div>
						</div>
						
						
						<!-- ROW 4 -->
						<div class="row">
							<div class="col-md-4">
							<label><%= etiqueta.IMPORTE_TOTAL%>:</label> 
								<div class="form-group input-group">
									<span class="input-group-addon" style="font-size: 10px;">$</span>
								<!-- 	<form:input cssClass="form-control currencyFormat" value="${comprobacionAnticipoDTO.importeTotal}" id="importeTotal" path="importeTotal"/> -->
									<input class="form-control currencyFormat" value="${comprobacionAnticipoDTO.importeTotal}" id="importeTotal"/>
								</div>
							</div>
								<div class="col-md-4">
									<label><%= etiqueta.SALDO%>:</label> 
								<div class="form-group input-group">
										<span class="input-group-addon">$</span>
										<form:input  cssClass="form-control currencyFormat" value="${comprobacionAnticipoDTO.saldo}" id="saldo" path="saldo"/> 
								</div>
						   </div>
						</div>
						
							<!-- ROW 4 -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
							    <label><%= etiqueta.FECHA_DEPOSITO%>:</label> 
								<form:input disabled="true" cssClass="form-control" value="${comprobacionAnticipoDTO.fecha_deposito}" id="fecha_deposito" path="fecha_deposito"/>
								</div>
							</div>
					
						</div>
						
						
						
						<!-- ROW 5 -->
						<div class="row">
							<div class="${comprobacionAnticipoDTO.compMultiple eq true ? 'col-md-6' : 'col-md-12'}">
								<div class="form-group">
									<label><%= etiqueta.CONCEPTO %>:</label> 
									<form:textarea maxlength="500" cssClass="form-control" value="${comprobacionAnticipoDTO.concepto}" rows="5" id="concepto" path="concepto" />
								</div>
							</div>
							
							<c:if test="${comprobacionAnticipoDTO.compMultiple eq true}">
								<div class="col-md-6">	
									<div class="dataTable_wrapper">
										<label><%= etiqueta.ANTICIPOS_INCLUIDOS %>:</label> 
										<table class="table table-striped table-bordered table-hover" id="tablaComprobadas">
											<thead>
												<tr>
													<th><%=etiqueta.NUMERO_DE_ANTICIPO%></th>
													<th><%=etiqueta.IMPORTE_TOTAL%></th>
													<th><%=etiqueta.IMPORTE_COMPROBADO%></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${comprobacionAnticipoDTO.lstAnticiposIncluidos}"
													var="lstAnticiposIncluidos" varStatus="status">
													<tr idSolicitud="${lstAnticiposIncluidos.idSolicitud}">
														<td align="center">${lstAnticiposIncluidos.idSolicitud}</td>
														
														<td><form:input cssClass="form-control sbts currencyFormat importeTotalIncluido"
																path="lstAnticiposIncluidos[${status.index}].montoTotal"
																value="${lstAnticiposIncluidos.montoTotal}" disabled="true"/>
														</td>
														
														<td><form:input
																cssClass="form-control currencyFormat montoComprobado"
																path="lstAnticiposIncluidos[${status.index}].montoComprobado"
																value="${lstAnticiposIncluidos.montoComprobado}" disabled="true"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>							
									</div>
								</div>
							</c:if> 
						</div>
					</div>
				</div> 
				<!-- Fin de Botones de cabecera -->
            
            
           <div style="border-color: #e7e7e7;" class="panel panel-default">

					<div style="height: 54px" class="panel-heading">
						  <h4 style="float: left;">Ingrese la información de los recorridos:</h4>
						  
						  <button id="anticipos"
						  	style="margin-left: 10px; float: right; display: true;" type="button"
							class="btn btn-primary" data-toggle="modal"
							data-target="#comprobacion-anticipo">
							Comp Anticipos</button>
						  
						  <c:if test="${tipoProveedor == 1}">
						  	<button id="adjuntar_comp_asesor"
								style="margin-left: 10px; float: right;" type="button"
								class="btn btn-primary" data-toggle="modal"
								data-target="#comprobacionAsesor">
								Comprobantes</button>
						  </c:if>
						  
						  <c:if test="${tipoProveedor == 2}">
						  	<button id="conxml" data-toggle="modal" data-target="#modal-no-mercancias" style="margin-left: 10px; float: right;" type="button" class="btn btn-primary"><%=etiqueta.SIN_XML%></button>	
						  	<button data-toggle="modal" data-target="#modal-no-mercancias" style="margin-left: 10px; float: right;" type="button" class="btn btn-primary"><%=etiqueta.CON_XML%></button>	
						  </c:if>
						   			
						  <button id="btnDeposito" onclick="" style="margin-left: 10px; float: right;" type="button" class="btn btn-default" data-toggle="modal" data-target="#deposito"><%=etiqueta.DEPOSITO%></button>
					</div>
					<div class="panel-body">

						<div class="dataTable_wrapper">
						
							<table class="table table-striped table-bordered table-hover" id="tablaDesglose">
								<thead>
									<tr>
										<th><%=etiqueta.LINEA%></th>
										<th></th>
										<th><%=etiqueta.CONCEPTO%></th>
										<th><%=etiqueta.FACTURA_FOLIO%></th>
										<th id="headerSbt"><%=etiqueta.SUBTOTAL%></th>
										<th id="headerIvas"><%=etiqueta.IVA%></th>
										<th id="headerTotal"><%=etiqueta.TOTAL%></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${comprobacionAnticipoDTO.lstFactDto}"
										var="lstFactDto" varStatus="status">
										<tr id="${status.index}">
											<td align="center">${status.count}</td>
											
											<td style="width: 10%;"class="center">
												<button type="button" style="height: 22px; margin-right: 10%;" class="boton_editar btn btn-xs btn-warning editarFila"><span class="glyphicon glyphicon-pencil"></span></button>
												<button type="button" style="height: 22px;" class="btn btn-xs btn-danger removerFila"><span class="glyphicon glyphicon-trash"></span></button>
											</td>
											
											<td>
												${lstFactDto.concepto}
											</td>
											
											<td>
												${lstFactDto.razonSocial} ${lstFactDto.serie} ${lstFactDto.folio}
											</td>
											
											<td><form:input cssClass="form-control sbts currencyFormat"
													path="lstFactDto[${status.index}].subTotal"
													value="${lstFactDto.subTotal}" disabled="true"/>
											</td>
											
											<td>
												<form:input onchange=""
													cssClass="form-control ivasComp currencyFormat"
													path="lstFactDto[${status.index}].IVA"
													value="${lstFactDto.IVA}" disabled="true"/>
											</td>
											
											<td>
												<form:input
													cssClass="form-control currencyFormat"
													path="lstFactDto[${status.index}].total"
													value="${lstFactDto.total}" disabled="true"/>
											</td>
											
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<table class="table table-striped table-hover" id="tblSumatorias">
								<tbody>
									<tr>
										<td style="width: 5%; padding-top: 17px;"><%=etiqueta.TOTALES%>:</td>
										<td>
<%-- 										<input value="${solicitudDTO.subTotal}" class="form-control currencyFormat" id="sbtComp" type="text" disabled="disabled"> --%>
										</td>
										<c:if test="${tipoProveedor == 1}">
										  	<td style="width: 40%"></td>
										</c:if>
										
										<c:if test="${tipoProveedor == 2}">
										  	<td style="width: 26%"></td>
										</c:if>
										
										<td id ="tdSbt">
<!-- 										<input value="0.00" class="form-control currencyFormat" id="ivatotComp" type="text" disabled="disabled"> -->
											<input value="${comprobacionAnticipoDTO.subTotal}" class="form-control currencyFormat" id="sbtComp" type="text" disabled="disabled"/>
										</td>
										<td id ="tdIvaTot">
											<input value="0.00" class="form-control currencyFormat" id=ivatotComp type="text" disabled="disabled">
										</td>
										<td id ="rightPadding">
										</td>
											
									</tr>
								</tbody>
							</table>
							
							<table class="table">
								<tbody>
									<tr>
									    <td style="width: 80%"></td>
										<td style="width: 5%; padding-top: 17px;"><%=etiqueta.TOTAL%>:</td>
										<td id="tdMontoTotal" style="width: 15%">
											<form:input value="${comprobacionAnticipoDTO.montoTotal}"
											class="form-control currencyFormat" path="strMontoTotal" id="reembTotComp" type="text"
											disabled="false"/>
										</td>
									</tr>
								</tbody>
							</table>
							
						</div>
						<!-- /.table-responsive -->
					</div>
				</div> 
    
           </form:form>
           <!-- Fin de formAnticipo -->
        </div>
        <!-- /#page-wrapper -->
        
        <!-- Modal de comprobacion Asesor -->
        <c:if test="${tipoProveedor == 1}">
        	<!-- Modal de comprobacion Asesor -->
    		<jsp:include page="modalCompAsesor.jsp" />
        </c:if>
        
        <!-- Modal de comprobacion No Mercancias -->
        <c:if test="${tipoProveedor == 2}">
        	<!-- Modal de comprobacion Asesor -->
    		<jsp:include page="modalNoMercancias.jsp" />
        </c:if>
    	
    	<!-- Modal de Captura Depósito -->
    	<jsp:include page="modalDeposito.jsp" />
    	
    	<!-- Modal de Comprobación de Anticipo -->
    	<jsp:include page="modalComprobarAnticipo.jsp" />
    	
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
									<strong>
										<span id="error-head"><%=errorHead%></span>
									</strong>
									<span id="error-body"><%=errorBody%></span>
								</div>

								<div class="form-group">
									<div class="control-label col-xs-4">
										<label><%=etiqueta.ANEXAR_DOCUMENTO_SOPORTE%></label><br/>
									</div>
									<div class="col-xs-7">
										<form method="post" enctype="multipart/form-data"
											action="singleSave">
											
											<!-- <input id="file" type="file" name="file"> <br/>  -->
											 <input id="file-sol" type="file" name="file-sol" class="upload" /> 
											
											<button onclick="anexarDoc()" style="margin-left: 10px; float: right;" type="button"
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
											<tbody id="tablaDoc2">
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
    

    	<!-- Modal Cancelar -->
		<div id="modal-solicitud2" class="modal fade" role="dialog">
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
							<a onclick="cancelar_solicitud()" style="display: none;" href="#" id="cancelar_button2"
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
	
	<script	src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/locales/bootstrap-datepicker.es.min.js"charset="UTF-8"></script>
  
	<script>
	var url = '${pageContext.request.contextPath}';
	var idAnticipo = '${comprobacionAnticipoDTO.idSolicitudAnticipoSession}';
	var idSolicitud = '${comprobacionAnticipoDTO.idSolicitudComprobacionSession}';
	var idEstadoSolicitud = '${idEstadoSolicitud}';
	var idSolicitudGlobal = idSolicitud;
	var url_server = url;
	var isCreacion = '${comprobacionAnticipoDTO.creacion}';
	var isModificacion = '${comprobacionAnticipoDTO.modificacion}';
	var tipoProveedor = '${comprobacionAnticipoDTO.tipoProveedor.idTipoProveedor}'
	var idProveedor = '${comprobacionAnticipoDTO.proveedor.idProveedor}'
	var idBeneficiario = '${comprobacionAnticipoDTO.beneficiario}'
	var tipoComprobacion = '${tipoProveedor}';
	var esEdicion = false;
	var facturaEnEdicion = -1;

	//var idSolicitudGlobal = '${facturaConXML.idSolicitudSession}';
	var tipoSolicitudGlobal = '${tipoSolicitud}';
	var idStatus = '${estadoSolicitud}';
	var hasmod = false;
	var especificaSol = '${isSolicitante}';
	
	var idSolicitante = '${usuarioSession.idUsuario}';
	
	//var isModificacion = '${facturaConXML.modificacion}';
	//var isCreacion = '${facturaConXML.creacion}';
	
	var esMultiple = '${esMultiple}';
	var fechaCreacionAnticipo = '${fechaCreacionAnticipo}';
	
	
	var TIPO_SOLICITUD_COMPROBACION_ANTICIPO = '<%=etiqueta.SOLICITUD_COMPROBACION_ANTICIPO%>'
	var TIPO_SOLICITUD_REEMBOLSO = '<%=etiqueta.SOLICITUD_REEMBOLSOS%>'
	var TIPO_SOLICITUD_CAJA_CHICA = '<%=etiqueta.SOLICITUD_CAJA_CHICA%>'
	var monPesos = '<%=etiqueta.ID_PESOS%>';
	var fPagoTrans = '<%=etiqueta.ID_TRANSFERENCIA%>';
	var ATENCION =  '<%=etiqueta.ATENCION%>';
	var ARCHIVO_ANEXADO =  '<%=etiqueta.ARCHIVO_ANEXADO%>';
	var ERROR = '<%=etiqueta.ERROR%>';
	var EXTENSION_INVALIDA = '<%=etiqueta.EXTENSION_INVALIDA%>';
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
	var MENSAJE_CAMBIO_MONEDA = '<%=etiqueta.MENSAJE_CAMBIO_MONEDA%>';
	var MENSAJE_CAMBIO_LOCACION = '<%=etiqueta.MENSAJE_CAMBIO_LOCACION%>';
	var MENSAJE_CANCELACION_NOXML = '<%=etiqueta.MENSAJE_CANCELACION_NOXML%>';
	var ESPECIFICA_SOLICITANTE = '<%=etiqueta.ESPECIFICA_SOLICITANTE%>'
	var DIF_MONEDA = '<%= etiqueta.DIF_MONEDA%>'
	var DIF_RAZON_SOCIAL = '<%= etiqueta.DIF_RAZON_SOCIAL%>'
	var NOPROVEEDOR = '<%= etiqueta.NOPROVEEDOR%>'
	var saved = '${param.saved}';
	var DEPOSITO_ATENCION_ELIMINAR = '<%=etiqueta.DEPOSITO_ATENCION_ELIMINAR%>';
	var ATENCION_ELIMINAR = '<%=etiqueta.ATENCION_ELIMINAR%>';
	var DEPOSITO_GUARDADO_CORRECTAMENTE = '<%=etiqueta.DEPOSITO_GUARDADO_CORRECTAMENTE%>';
	var DEPOSITO_ACTUALIZADO_CORRECTAMENTE = '<%=etiqueta.DEPOSITO_ACTUALIZADO_CORRECTAMENTE%>';
	var DEPOSITO_ELIMINADO_CORRECTAMENTE = '<%=etiqueta.DEPOSITO_ELIMINADO_CORRECTAMENTE%>';
	var ID_PESOS = '<%=etiqueta.ID_PESOS%>';
	</script>
	
	<script	src="${pageContext.request.contextPath}/resources/js/compAsesor.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/anticipo.js">	</script>
	<script src="${pageContext.request.contextPath}/resources/js/comprobacionAnticipo.js">	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>

</body>
</html>
