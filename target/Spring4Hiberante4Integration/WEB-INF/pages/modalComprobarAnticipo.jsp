<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    				

    				
  <style>
  // Ventana modal estilos 
#comprobacion-anticipo .modal-header .close {
  color: white;
  opacity: .8;
}
  
.modal-fade .modal-header {
  background-color: #004990; color: white;
}

.modal-fade {
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
.inactivo {
	opacity:.4;
	cursor:default;
}
// Fin de ventana modal estilos
  </style>
    				
		<!-- Ventana modal Comprobación de Anticipo -->
		<div class="modal fade" id="comprobacion-anticipo" role="dialog">
			<form:form method="post" id="compAnticipoForm" enctype="multipart/form-data" action="" modelAttribute="comprobacionAnticipoDTO">
			<div class="modal-dialog" style="width: 840px;">

				<!-- Modal content-->
					<div class="modal-content" style="width: 840px;">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">×</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo">Selecciona el anticipo a comprobar</span>
							</h4>
						</div>
						<!-- Fin Modal Header -->
						<!-- Modal Body -->
						<div class="modal-body deposito-body">
							<!-- Alertas -->
							<div style="display: none;" id="error-alert-modal-comp" class="alert alert-danger fade in">
								<strong><span id="error-head-modal-comp"><%=etiqueta.ERROR%></span></strong>
								<span id="error-body-modal-comp"><%=etiqueta.COMPLETE%></span>
							</div>
					
							<div style="display: none;" id="ok-alert-modal-comp" class="alert alert-success fade in">
								<strong> <span id="ok-head-modal-comp"> </span>
								</strong> <span id="ok-body-modal-comp"> <%=etiqueta.SOLICITUD_GUARDADA%> </span>
							</div>
							<!-- Fin de alertas -->

							<div class="col-xs-12">
								<!-- Inicio de formulario Deposito -->
								</div>
								<div class="dataTable_wrapper">
									
									<table class="table table-striped table-bordered table-hover" id="depositos-comprobar">
										<thead>
											<tr>
												<th> No. de Solicitud </th>
												<th> Seleccionar </th>
												<th> Fecha Depósito </th>
												<th> Fecha Solicitud </th>
												<th> Importe </th>
												<th> Comprobado </th>
												<th> Saldo </th>
												<th> Moneda </th>
												<th> Concepto </th>
												
											</tr>
										</thead>
										<tbody id="tablaDoc">
											<c:forEach items="${comprobacionAnticipoDTO.lstAnticipos}"
											var="lstAnticipos" varStatus="status">
												<tr idSolicitud="${lstAnticipos.idSolicitud}">
													<td >${lstAnticipos.idSolicitud}</td>														
													<td class="center">
														<c:if test="${lstAnticipos.idEstadoSolicitud eq comprobacionAnticipoDTO.estatusSolicitud }"> 
															<input class="anticiposAComprobar" type="checkbox"  name="optradio" value="${lstAnticipos.idSolicitud}" checked="checked"> 
														</c:if>
														<c:if test="${lstAnticipos.idEstadoSolicitud ne comprobacionAnticipoDTO.estatusSolicitud }"> 
															<input class="anticiposAComprobar" type="checkbox"  name="optradio" value="${lstAnticipos.idSolicitud}"> 
														</c:if>
													</td>
													<td>${lstAnticipos.fechaPagoStr}</td>
													<td>${lstAnticipos.creacionFechaStr}</td>
													<td class="currencyFormat">${lstAnticipos.montoTotal}</td>
													<td class="currencyFormat">${lstAnticipos.montoComprobado}</td>
													<td class="currencyFormat">${lstAnticipos.montoSaldo}</td>
													<td>${lstAnticipos.moneda.descripcion}</td>
													<td>${lstAnticipos.concepto}</td>
												</tr>
											</c:forEach>											
										</tbody>
									</table>
								</div>
											<!-- /.table-responsive -->
											
											<div class="form-group col-xs-12" style="border-top: 2px dotted #ccc;">
												<div style="float: right;margin-bottom: 10px;margin-top: 16px;">
													<button data-dismiss="modal" style="margin-left: 10px; float: right;" id="btnComprobar"
													type="button" class="btn btn-primary"><%=etiqueta.COMPROBAR%></button>
												</div>
											</div>
										</div>
							<div class="panel-body">
							</div>
						</div>
				</div>
				</form:form>
			</div>
		
		<script>
		

	
		
		// Modal Anticipo de comprobar --------------------------------------------------
		
		
		

		// ------------------------------------------------------------------------------
		
		// función alternativa para desplegar alertas ---------------
		function set_alert(div,head,body,id) {
			$(div+" strong #error-head-files").text(head);
			$(div+" #error-body-files").text(body);
			if (id == 1) {
				$(div).removeClass("alert-warning").addClass("alert-success");
					display_alert(div,id);		
				}
			else {
				$(div).removeClass("alert-success").addClass("alert-warning");
				setActive(false)
				display_alert(div,id);
				}
			
			}

		
		function display_alert(div,id) {
			$(div).fadeTo(5000, 500, function () {
				 if (id != 1) $(div).slideUp(500, function(){
					    $(div).hide();
					});
			})
		}
		
		// Fin de 
		
		</script>
		
