<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%Etiquetas etiqueta = new Etiquetas("es");%>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<jsp:include page="template/head.jsp" />

<style type="text/css">
.form-group {
	margin-bottom: 3px;
}

.errory{
 	border: 1px solid red;
}

.blockedInput{
}
</style>

</head>

<body>


	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<form:form method="post" id="formKilometraje"
				action="saveKilometraje" modelAttribute="kilometrajeDTO">
				
				<form:hidden cssClass="blockedInput" value="${kilometrajeDTO.idSolicitud}" path="idSolicitud" />

				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<%=etiqueta.KILOMETRAJE_SOLICITUD%>
							</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				
				<div style="display: none;" id="error-alert"
					class="alert alert-danger fade in">
					<strong><span id="error-head"><%=etiqueta.ERROR%></span></strong>
					<span id="error-body"><%=etiqueta.COMPLETE%></span>
				</div>


				<div style="display: none;" id="ok-alert"
					class="alert alert-success fade in">
					<strong> <span id="ok-head"> </span>
					</strong> <span id="ok-body"> <%=etiqueta.SOLICITUD_GUARDADA%>
					</span>
				</div>
				
				<!--botones de cabecera -->
				<div class="panel panel-default">
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
						
					   <button onclick="verDetalleEstatus(${kilometrajeDTO.idSolicitud})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
							
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button onclick="enviarSolicitudProceso()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%></button>
						</c:if>
							
						
						<c:if test="${idEstadoSolicitud == 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%></button>
						</c:if>

						<c:if test="${idEstadoSolicitud == 0 || idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button id="save_button" onclick="valTodo();"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
							
						</c:if>
						
					</div>
					
					<div class="panel-body">
				
						<div class="row">
							<div class="col-md-4">
							<div class="form-group">
									<label><%=etiqueta.COMPANIA%>:</label>
									<form:select path="compania.idcompania" cssClass="form-control blockedInput" disabled="true">
										<option title="<%=etiqueta.SELECCIONE%>" value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${companiaList}" var="companiaList">
										<option value="${companiaList.idcompania}" 
										${kilometrajeDTO.idCompania == companiaList.idcompania ? 'selected' : ''}>
										 ${companiaList.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-8"></div>
						</div>
						<div class="row">
							<div class="col-md-4">	
							<div class="form-group">
									<label><%=etiqueta.LOCACION%>:</label>
									<form:select id="locacion" path="locacion.idLocacion" cssClass="form-control" disabled="false">
										<option title="<%=etiqueta.SELECCIONE%>" value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${locacionList}" var="locacionList">
										 <option ${kilometrajeDTO.locacion.idLocacion == locacionList.idLocacion ? 'selected' : ''}
										  value="${locacionList.idLocacion}">${locacionList.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-8"></div>
						</div>
						
								<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label><%=etiqueta.MONEDA%>:</label>
									<form:select path="moneda.idMoneda" cssClass="form-control blockedInput" disabled="true">
										<option title="<%=etiqueta.SELECCIONE%>" value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${monedaList}" var="monedaList">
										 <option
										  ${kilometrajeDTO.idMoneda == monedaList.idMoneda ? 'selected' : ''}
										  value="${monedaList.idMoneda}"> ${monedaList.descripcion} </option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-md-8">
							 <div class="col-md-3">
							  <div class="form-group">
									<label><%=etiqueta.FORMA_PAGO%>:</label>
									<form:select path="formaPago.idFormaPago" cssClass="form-control blockedInput" disabled="true">
										<option title="<%=etiqueta.SELECCIONE%>" value="-1"><%=etiqueta.SELECCIONE%></option>
										<c:forEach items="${formaPagoList}" var="formaPagoList">
										<option ${kilometrajeDTO.idFormaPago == formaPagoList.idFormaPago ? 'selected' : ''}
										value="${formaPagoList.idFormaPago}"> ${formaPagoList.descripcion} </option>
										</c:forEach>
									</form:select>
							  </div>
							 </div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-4">
								<label><%=etiqueta.IMPORTE_AUTO_PROPIO%>:</label>
								<div class="form-group input-group">
									<span class="input-group-addon">$</span>
									<form:input  path="importeAutoPropio" cssClass="form-control blockedInput currencyFormat importe-total" disabled="true" value="${kilometrajeDTO.importeAutoPropio}" />
								</div>
							</div>
							<div class="col-md-8"></div>
						</div>
						<div class="row">
							<div class="col-md-8">
								<div class="form-group">
									<label style="margin-top: 5px;" for="comment"><%=etiqueta.CONCEPTO_DEL_GASTO%>:</label>
									<form:textarea maxlength="500" path="concepto" style="margin-bottom:10px;"
									value="${kilometrajeDTO.concepto}" cssClass="form-control" rows="5" />
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div style="border-color: #e7e7e7;" class="panel panel-default">

					<div style="height: 54px" class="panel-heading">
						<h4 style="float: left;"><%=etiqueta.INFORMACION_DE_RECORRIDO%></h4>
						<c:if test="${idEstadoSolicitud != 7}">
						  <button onclick="addRowToTable()" style="margin-left: 10px; float: right;" type="button" class="btn btn-primary"><%=etiqueta.AGREGAR%></button>
						</c:if>
					</div>
					<div class="panel-body">

						<div class="dataTable_wrapper">

							<table class="table table-striped table-bordered table-hover" id="tablaDesglose">
								<thead>
									<tr>
										<th><%=etiqueta.LINEA%></th>
										<th><%=etiqueta.FECHA%></th>
										<th><%=etiqueta.E_KM_MOTIVO%></th>
										<th style="min-width: 200px;"><%=etiqueta.ORIGEN%></th>
										<th style="min-width: 200px;"><%=etiqueta.DESTINO%></th>
										<th><%=etiqueta.E_KM_VIAJES%></th>
										<th><%=etiqueta.KILOMETRAJE_RECORRIDOS%></th>
										<th><%=etiqueta.IMPORTE%></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${kilometrajeDTO.kilometrajeDesgloseList}"
											var="kilometrajeDesgloseList" varStatus="status">
											<tr>
												<td class="linea" align="center">${status.count}</td>
											<td>
												<div  class="form-group sandbox-container">
													<form:input cssStyle="background-color:white" readonly="true" onchange="verificarFecha(value,${status.index})" path="kilometrajeDesgloseList[${status.index}].fecha"
														cssClass="form-control fechaKm" 
														value="${kilometrajeDesgloseList.fecha}" />
												</div>
											</td>
											<td>
										        <form:input maxlength="100" cssClass="form-control motivoKm"
												path="kilometrajeDesgloseList[${status.index}].motivo"
												value="${kilometrajeDesgloseList.motivo}" />
											</td>
											<td>
											    <form:select
														path="kilometrajeDesgloseList[${status.index}].origen.idUbicacion"
														onchange="getDestinos(${status.index})"
														cssClass="form-control origenKm">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${kilometrajeUbicacionList}" var="klist">
															<option
																${kilometrajeDesgloseList.origen.idUbicacion == klist.idUbicacion ? 'selected' : ''}
																value="${klist.idUbicacion}">${klist.descripcion}
															</option>
														</c:forEach>
												 </form:select>
											</td>
											<td>
												<form:select
												        onchange="setKilometraje(${status.index})"
														path="kilometrajeDesgloseList[${status.index}].destino.idUbicacion"
														cssClass="form-control destinoKm">
														<option value="-1"><%=etiqueta.SELECCIONE%></option>
														<c:forEach items="${kilometrajeDesgloseList.destinos}" var="klistDestino">
															<option
																${kilometrajeDesgloseList.destino.idUbicacion == klistDestino.idUbicacion ? 'selected' : ''}
																value="${klistDestino.idUbicacion}">${klistDestino.descripcion}
															</option>
														</c:forEach>
												 </form:select>
											</td>
											<td>
											    <form:input cssStyle="text-align:right;" type="number" cssClass="form-control viajesKm"
											    onkeyup="suma(${status.index})"
												path="kilometrajeDesgloseList[${status.index}].viajes"
												value="${kilometrajeDesgloseList.viajes}" />
											</td>
											<td>
											    <form:input cssStyle="text-align:right;" type="number" cssClass="form-control kilometraje"
											    onkeyup="suma(${status.index})"
												path="kilometrajeDesgloseList[${status.index}].kilometros_recorridos"
												value="${kilometrajeDesgloseList.kilometros_recorridos}" />
											</td>
											<td>
											   <form:input cssClass="form-control importeKm currencyFormat"
											   	disabled="true"
												path="kilometrajeDesgloseList[${status.index}].importe"
												value="${kilometrajeDesgloseList.importe}" />
											</td>
											<td>
											<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
											   <button type="button" class="btn btn-danger removerFila"><%=etiqueta.REMOVER%></button>
											</c:if>
											</td>
										</tr>
										
									</c:forEach>
								</tbody>
							</table>
							
							
							<div id="tblSumatorias" style="width:1250px;">
								<div id="km-totales">Totales: </div>
								<div id="km-totalKm"><input value="0" class="form-control"style="text-align:right" id="kmtotales" type="text" disabled="disabled"></td></div>
								<div id="km-importe"><div class="form-group input-group">
													<span class="input-group-addon">$</span>
													<input value="0.00"	class="form-control currencyFormat" id="total_importe" type="text" disabled="disabled">
												</div></div>
							</div>
							
							
							<%-- <div class="dataTable_wrapper">
								<table class="table table-striped table-hover"
									id="tblSumatorias">
									<tbody>
										<tr>
											<td style="width: 5%"></td>
											<td style="width: 15%"></td>
											<td style="width: 45%"></td>
											<td style="width: 15%">
											<label style="margin-top: 5px;" for="comment"><%=etiqueta.KM_TOTAL_KM%></label>
											<input value="0" class="form-control"style="text-align:right" id="kmtotales" type="text" disabled="disabled"></td>
											<td style="width: 14%">
												<label style="margin-top: 5px;" for="comment"><%=etiqueta.KM_TOTAL_IMPORTE%></label>
												<div class="form-group input-group">
													<span class="input-group-addon">$</span>
													<input value="0.00"	class="form-control currencyFormat" id="total_importe" type="text" disabled="disabled">
												</div>
											</td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div> --%>

						</div>
						<!-- /.table-responsive -->
					</div>
				</div>

			</form:form>
		</div>
			<!-- Modal anexar archivos -->
		<jsp:include page="modalArchivosSolicitud.jsp" />
		<jsp:include page="modalConsultarAutorizacion.jsp" />
		
		
		
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
		
		
		
	</div>

	<jsp:include page="template/includes.jsp" />
	<script	src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/locales/bootstrap-datepicker.es.min.js"charset="UTF-8"></script>
	
	
	<script>
	
	var idSolicitudGlobal = '${kilometrajeDTO.idSolicitudSession}';
	var tipoSolicitudGlobal = '${tipoSolicitud}';
	var url_server = '${pageContext.request.contextPath}';
	var hasmod = false;
	
	var otro = '<%=etiqueta.OTRO%>';
	var rangoDias = '${rangoDias}';
	rangoDiasAlias = '${rangoDiasAlias}';


	$(document).ready(function(){
		
		hasmod = false;
		var origen = "";
		//-----------------------------------------------------------
		$(window).resize(function() {
			fixTotales();
		});
		
		function fixTotales () {
			log('Fixing',2);
			var totalesWidth = 0;
			var kilometrosWidth = 0;
			var importeWidth = 0;
			
			// Totales texto ----------------------------------------
			$ths = $("#tablaDesglose th");
			$.each($ths, function (index) {
				if (index < 6) {
					$th = $ths.eq(index);
					log ('TH'+index+": "+parseInt($th.css('width')) );
					totalesWidth += parseInt($th.css('width'));
				}
			});
			// Kilómetros totales -----------------------------------
			kilometrosWidth = parseInt($("#tablaDesglose th").eq(6).css('width'))-8;
			// Importe total ----------------------------------------
			importeWidth = parseInt($("#tablaDesglose th").eq(7).css('width'));
			// Importe total ----------------------------------------
			margenDerecho = parseInt($("#tablaDesglose th").eq(8).css('width'));
			
			// Ajuste -----------------------------------------------
			$('#km-totales').css('width',totalesWidth);
			$('#km-totalKm').css('width',kilometrosWidth);
			$('#km-importe').css('width',importeWidth);
			
		}	
		
		$( window ).resize();
		// Fin Resize -----------------------------------------------
		
		
		  startDatePicker()
		  
		  //remover fila
		  $('#tablaDesglose').on('click', ".removerFila", function(){
			    $(this).closest ('tr').remove ();
			    calculateLine();
			    totalKm();
			    totalImporte();
		  });
		  
		  var isModificacion = '${kilometrajeDTO.modificacion}';
		  var isCreacion = '${kilometrajeDTO.creacion}';
		  var idEstadoSol =  '${idEstadoSolicitud}';
		  
		  if(isModificacion == 'true'){
			  $("#ok-head").text(ACTUALIZACION);
			  $("#ok-body").text(INFORMACION_ACTUALIZADA);
			  ok_alert();
		  }
		  
		  if(isCreacion == 'true'){
			  $("#ok-head").text(NUEVA_SOLICITUD);
			  $("#ok-body").text(SOLICITUD_CREADA);
			  ok_alert();
		  }
		 
		  $('.currencyFormat').number( true, 2 );
		  
		 //status solicitud 
		 checkStatusBehavior(idEstadoSol);
		 
		 if(idSolicitudGlobal > 0){
		   cargarLista();
		   totalKm();
		   totalImporte();
		 }
		 
		 // Valida si destino es igual a "OTRO" para habilitar el input "kilometraje"
		 $rows = $('tbody tr',"#tablaDesglose");
		 $.each($rows, function (index) {
		 
			$row = $rows.eq(index);
			var origen = $.trim($('.origenKm option:selected',$row).text()); 
			var destino = $.trim($('.destinoKm option:selected',$row).text()); 
			if (origen == otro.toUpperCase() | destino == otro.toUpperCase()) {
				if($('#concepto').prop('enabled'))
					$('.kilometraje',$row).prop( "disabled", false );
				
				var idViaje = 'kilometrajeDesgloseList' + index + '.viajes';
				document.getElementById(idViaje).disabled = true;
		
			} else {
				var idKilometros = 'kilometrajeDesgloseList' + index + '.kilometros_recorridos';
				document.getElementById(idKilometros).disabled = true;
			}
			
		 });
		 
		
		 
		 
	});
	
	 function addRowToTable(){
		 var length = $("#tablaDesglose > tbody > tr").length;
		 callRow(length)
		 
	 }
	 
	 $(".form-control").on('change', function() {
	   	  console.log("cambio: "+$(this).val());
	   	  hasmod = true;
	  });

	  function callRow(length){
		  	loading(true);
			$.ajax({
				type : "GET",
				cache : false,
				url : "${pageContext.request.contextPath}/addRowKilometraje",
				async : true,
				data : "numrows=" + length,
				success : function(response) {
					loading(false);
					$('.currencyFormat').number( true, 2 );
					$("#tablaDesglose").append(response);
					calculateLine();
					startDatePicker();
					$(window).resize();
					resetNumber($('.viajesKm'));
					resetNumber($('.kilometraje'));
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
	  
	  function calculateLine() {
			$('#tablaDesglose tr').each(function(i, row) {
				if (i > 0) {
					var nlinea = $(this).find(".linea").text(i);
				}
			});
		}
	  
	  function valTodo(){
		  
		  var error = false;
		  
		  if($("#locacion").val() == -1){
			   $("#locacion").addClass("errorx");
			   error = true;
		   }else{
			   $("#locacion").removeClass("errorx");
		   }
		  
		  $('.fechaKm').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  $('.motivoKm').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  $('.origenKm').each(function() {
		    	if($(this).val() == -1 || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  
		  $('.destinoKm').each(function() {
		    	if($(this).val() == -1 || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  $('.viajesKm').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  $('.kilometraje').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  
		  $('.importeKm').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		  

		  if(error){
			  $("#error-head").text(ERROR);
			  $("#error-body").text(COMPLETE);
    		  error_alert();
			  return false;
		  }else{
			     var error = false;
			     
				 $('.fechaKm').each(function() {
					
					 var clases = $(this).attr('class');
					 var clase = "errory";
					 
					 if(clases.indexOf(clase) != -1){
						 error  = true;
					  }
				 });

			  
			  if(error){
					$("#error-head").text(ERROR);
					$("#error-body").text(ERROR_FECHA_RANGO);
		    		error_alert();
				}else{
					enableDisable(false);
					$(".viajesKm").prop('disabled', false);
					$("#formKilometraje")[0].submit();
					loading(true);
				} 
		  }
	  }
	  
	  function enableDisable(state){
		  $(".blockedInput").prop('disabled', state);
		  $(".kilometraje").prop('disabled', state);
		  $(".importeKm").prop('disabled', state);
	  }
	  
	  function startDatePicker(){
		  $('.sandbox-container input').datepicker({
			  format: 'dd/mm/yyyy',
			    language: 'es'
		  });
	  }
	  
	  
	  function setKilometraje(idNum){
			
			
			var id2 = "kilometrajeDesgloseList@.destino.idUbicacion";
			id2 = id2.replace("@",idNum);
			var e = document.getElementById(id2);
			var idDestino = e.options[e.selectedIndex].value;
			// Cambios para desabilitar el campo viajes al seleccionar en el combo origen y destino los valores de OTRO "5 de Julio de 2016 3:51
			var destino = e.options[e.selectedIndex].text;
			var idViajes2 = "kilometrajeDesgloseList@.viajes";
			var idElementoViajes2 = idViajes2.replace("@", idNum);
			
			
			
			var idElementoOrigen = id2.replace("destino", "origen");
			var e2 = document.getElementById(idElementoOrigen);
			var idOrigen = e2.options[e2.selectedIndex].value;
			origen = e2.options[e2.selectedIndex].text;
			
			if(origen === otro.toUpperCase() || destino === otro.toUpperCase()){
				document.getElementById(idElementoViajes2).disabled = true;
				document.getElementById(idElementoViajes2).value = 1;
			}else{
				document.getElementById(idElementoViajes2).disabled = false;
			}
			
		     $.ajax({
				type : "GET",
				cache : false,
				url : "${pageContext.request.contextPath}/getKilometraje",
				async : true,
				data : "idOrigen=" + idOrigen + "&idDestino=" + idDestino+ "&idElemento=" + id2,
				success : function(result) {
					if(result.kilometrajeRecorrido == 0){
						document.getElementById(result.idKilometraje).disabled = false;
					}else{
						document.getElementById(result.idKilometraje).disabled = true;
					}
					    document.getElementById(result.idKilometraje).value=result.kilometrajeRecorrido;
					    //recalcular
					    suma(idNum);
					    totalKm();
					    totalImporte();
				},
				error : function(e) {
					console.log('Error: ' + e);
				},
			}); 
		     
		     
	  }
	  

		function getDestinos(idNum) {

			var id = "kilometrajeDesgloseList@.origen.idUbicacion";
			id = id.replace("@",idNum);
			var e = document.getElementById(id);
			var idOrigen = e.options[e.selectedIndex].value;
			// Cambios para desabilitar el campo viajes al seleccionar en el combo origen y destino el valor OTRO "5 de Julio de 2016 3:51
			origen = e.options[e.selectedIndex].text;
			var idViajes2 = "kilometrajeDesgloseList@.viajes";
			var idElementoViajes2 = idViajes2.replace("@", idNum);
			
			
			console.log("id Elemento: "+id);
			console.log("id origen get destinos : "+idOrigen);
			
			//reset kilometros
			var idKilometros = "kilometrajeDesgloseList@.kilometros_recorridos";
			var idElementoKilometros = idKilometros.replace("@",idNum);
			var kilometros = document.getElementById(idElementoKilometros).value = 0;
			suma(idNum);
			

			//if(idOrigen == 29){  //Se valida usando la descripcion del combo envez del ID -- cambios por EDGAR el 5 de Julio de 2016 3:51
			if(origen === otro.toUpperCase()){
				document.getElementById(idElementoKilometros).disabled = false;
				//desabilita campo viajes
				document.getElementById(idElementoViajes2).disabled = true;
				document.getElementById(idElementoViajes2).value = 1;
			}else{
				document.getElementById(idElementoKilometros).disabled = true;
				document.getElementById(idElementoViajes2).disabled = false;
			}
				        $.ajax({
							type : "GET",
							cache : false,
							url : "${pageContext.request.contextPath}/getDestinos",
							async : true,
							data : "idElemento=" + id + "&idOrigen=" + idOrigen,
							success : function(result) {
								document.getElementById(result.idDestino).innerHTML = result.optionsDestino;
							},
							error : function(e) {
								console.log('Error: ' + e);
							},
						});
		  }
		
		
		function suma(idNum){
			
			console.log("SUMA NUM: "+idNum);
			
			  //var id = idElemento;
			  
			  //valor dinamico de los viajes
			  var idViajes = "kilometrajeDesgloseList@.viajes";
		      var idElementoViajes = idViajes.replace("@", idNum);
		      console.log("idElementoViajes: "+idElementoViajes);
			  var viajes = document.getElementById(idElementoViajes).value;
			  console.log("viajes: "+viajes);
			  
			  //valor dinamico de kilometros recorridos
			  var idKilometros = "kilometrajeDesgloseList@.kilometros_recorridos";
			  var idElementoKilometros = idKilometros.replace("@",idNum);
			  var kilometros = document.getElementById(idElementoKilometros).value;
			  console.log("kilometros: "+kilometros);
			  
			  var idOrigenElementoGeneral = "kilometrajeDesgloseList@.origen.idUbicacion";
			  var idOrigenElementoEspecifico = idOrigenElementoGeneral.replace("@", idNum);
			  var idOrigen = document.getElementById(idOrigenElementoEspecifico).value;
		      console.log("idOrigen: "+viajes);
		      
		      
		      var idDestinoElementoGeneral = "kilometrajeDesgloseList@.destino.idUbicacion";
			  var idDestinoElementoEspecifico = idDestinoElementoGeneral.replace("@", idNum);
			  var idDestino = document.getElementById(idDestinoElementoEspecifico).value;
		      console.log("idDestino: "+viajes);

			   $.ajax({
					type : "GET",
					cache : false,
					url : "${pageContext.request.contextPath}/getImporte",
					async : true,
					data : "idElemento=" + idElementoViajes + "&kilometros=" + kilometros + "&viajes=" + viajes+ "&idOrigen=" + idOrigen + "&idDestino=" + idDestino,
					success : function(result) {						
						document.getElementById(result.idElementoImporte).value = result.importe;
						document.getElementById(idElementoKilometros).value = result.kmtotales;
						totalKm();
					    totalImporte();
					    //$('.currencyFormat').number( true, 2 );
					},
					error : function(e) {
						console.log('Error: ' + e);
					},
			 });
			   
			   
			
		}
		
		
		function verificarFecha(fecha,idnum){
			    
			     //console.log(fecha+" <-> "+idnum);
				 var  idE = "kilometrajeDesgloseList@.fecha";
				 idE = idE.replace("@",idnum);
			     
				 
				 var idSolicitud = $("#idSolicitud").val();
				 if(idSolicitud == ""){
					 idSolicitud = 0;
				 }
			
				 $.ajax({
						type : "GET",
						cache : false,
						url : "${pageContext.request.contextPath}/verificarFecha",
						data : "fecha=" + fecha + "&idSolicitud=" + idSolicitud,
						success : function(result) {
							console.log(result);
							if(result.respuesta == 'no-valida'){
								markFecha(idE);
								hasmod = false;
							}else if(result.respuesta == 'valida'){
								unmarkFecha(idE);
								hasmod = false;
							}
						},
						error : function(e) {
							console.log('Error: ' + e);
						},
				 }); 
		}
		
	
		
		function markFecha(idElmento){
			document.getElementById(idElmento).className += " errory";
			$("#error-head").text(ERROR);
			$("#error-body").text(ERROR_FECHA_RANGO+". "+rangoDias+" "+rangoDiasAlias);
    		error_alert();
		}
		
		function unmarkFecha(idElmento){
			document.getElementById(idElmento).className = document.getElementById(idElmento).className.replace( /(?:^|\s)errory(?!\S)/g , '' );
		}
		
		  function cancelar(){
			  $("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
			  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
			  $("#cancelar_button").show();
		  }
		  
		  function cerrar_ventana(){
			  $("#modal-solicitud").modal("hide");
		  }
		  
		  function cancelar_solicitud(){
			    $.ajax({
		    		type : "GET",
		    		cache : false,
		    		url : "${pageContext.request.contextPath}/cancelarSolicitud",
		    		async : false,
		    		data : "idSolicitud=" + idSolicitudGlobal,
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
		  }
		  
	
		  function enviarSolicitudProceso(){
			  //validar
			  console.log("has mod :" + hasmod);
			  if(hasmod == false){
			  if(validaEnvio()){
				  if(checkOtro()){
				    $.ajax({
			    		type : "GET",
			    		cache : false,
			    		url : "${pageContext.request.contextPath}/revisarArchivosEnSolicitud",
			    		async : false,
			    		data : "idSolicitud=" + idSolicitudGlobal,
			    		success : function(response) {
			    			 console.log(response);
			    	         if(response.respuesta == 'true'){
			    	        	 enviaAProceso();
			    	         }else{
			    	        	 $("#error-head").text(ERROR);
								 $("#error-body").text(DOCUMENTO_SOPORTE_ANEXAR);
							     error_alert();
			    	         }
			    		},
			    		error : function(e) {
			    			$("#error-head").text(ERROR);
							 $("#error-body").text(NO_SE_ENVIO);
						     error_alert();
			    			
			    		},
			    	});  
			     }else{
			    	 enviaAProceso();
			     }
			  }
		    }else{
		    	$("#error-head").text(ERROR);
				$("#error-body").text(GUARDE_ENVIAR);
			    error_alert();
		    }
		  }
		  
		  function totalKm(){
			   var kms = 0;
				 $(".kilometraje").each(function(){
					 if(isNaN(parseFloat($(this).val())) == false){
						 kms += parseFloat($(this).val());
					 }
					 $(this).unbind('number').number(true,2);
				 });
				 $("#kmtotales").val(kms).unbind('number').number(true,2);
				 $(".importeKm").unbind('number').number(true,2);
				
				 
				 
		  }
		  
		  function totalImporte(){
			   var kms = 0;
				 $(".importeKm").each(function(){
					 if(isNaN(parseFloat($(this).val())) == false){
						 kms += parseFloat($(this).val());
					 }
				 });
				 $("#total_importe").val(kms).unbind('number').number(true,2); 
		  }
		  
		  
		  function validaEnvio(){
			  var length = $("#tablaDesglose > tbody > tr").length;
				if(length > 0){
					
					var valorImporte = $("#total_importe").val();
					if(valorImporte != "" && valorImporte > 0){
						$("#total_importe").removeClass("errorx");
						
						if($("#locacion").val() != -1){
							$("#locacion").removeClass("errorx");
							
							if(hasmod == false){
								return true;	
							}else{
								 $("#error-head").text(ERROR);
								 $("#error-body").text(GUARDE_ENVIAR);
							     error_alert();
							}
							
							
						}else{
							   $("#error-head").text(ERROR);
							   $("#error-body").text(ESPECIFIQUE_LOCACION);
							   $("#locacion").addClass("errorx");
							   error_alert();
							   hasmod = true;
							   return false; 
						}
					}else{
						   $("#error-head").text(ERROR);
						   $("#error-body").text(IMPORTE_CERO);
						   $("#total_importe").addClass("errorx");
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
		  
		  function enviaAProceso(){
			  enviaProceso(idSolicitudGlobal); 
		  }
		  
		  function checkOtro(){
			  
			  var isOtro = false;
			
			  $('.origenKm').each(function() {
				  if($(this).val() == 29){
					isOtro = true;  
				  }
			  });
			  
			  $('.destinoKm').each(function() {
				  if($(this).val() == 29){
					isOtro = true;  
				  }
			  });
			  
			  console.log("isOtro="+isOtro);
			  
			  return isOtro;
		  }
		  
		  $(".viajesKm").on('change', function() {
			  if(!$.isNumeric($(this).val())){				  
				  $(this).val(0);
				}
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
		
	</script>
		<script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
		<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
		<script	src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
		<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
</body>
</html>
