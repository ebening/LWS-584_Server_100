<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    				

    				
  <style>
  // Ventana modal estilos 
#anexarDocumentoForm .modal-header .close {
  color: white;
  opacity: .8;
}
  
#capturaDeposito .modal-header {
  background-color: #004990; color: white;
}

#capturaDeposito {
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
    				
		<!-- Ventana modal Depósito -->
<div class="modal fade" data-cancel="false" id="desglose-comprobante" role="dialog">
	<div class="modal-dialog" style="width: 90%;">

		<!-- Modal content-->
			<form:form method="post" id="desgloseDetalleForm" enctype="multipart/form-data" action="" modelAttribute="comprobacionAnticipoViajeDTO">
			<div class="modal-content" style="width: 100%;">
				<!-- Modal header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
					<h4 style="float: left; margin:0px;">
					<span id="titulo-dialogo"><%=etiqueta.DESGLOSE_COMPROBANTE%></span>
					</h4>
				</div>
				<!-- Fin Modal Header -->
				<!-- Modal Body -->
				<div class="modal-body deposito-body">
					<!-- Alertas ----------------------- -->
					<div style="display: none;" id="error-alert-detalle" class="alert alert-danger fade in">
						<strong><span id="error-head-detalle"><%= etiqueta.ERROR %>:</span></strong>
						<span id="error-body-detalle"><%= etiqueta.COMPLETE %></span>
					</div>
					<div style="display: none;" id="ok-alert-detalle" class="alert alert-success fade in">
						<strong> <span id="ok-head-detalle"> </span>
						</strong> <span id="ok-body-detalle"><%= etiqueta.SOLICITUD_GUARDADA %>
						</span>
					</div>


					<div id="#desgloseDetalle" class="panel-body desglose-modal">
						<!-- Header -->
						<div class="row">
							<div class="col-xs-12 ">
								<h3><label id="detComercio"></label></h3>
								<h4>(<label id="detNumeroPersonas"></label> <%=etiqueta.PERSONAS%>)</h4>
							</div>
						</div>
						<div class="row import-desglose-fijo" style="padding:0px;margin-top: 10px;">
							
							<span style="display: none;" id="detIndex"></span>
							
							<div class="col-xs-4">
								<label class="col-xs-7"><%=etiqueta.SUBTOTAL%>:</label>
								<div class="col-xs-3 money-data-container"><span class="importe-total money-data">
									$<span id="detSubTotal" class="moneyFormat"></span></span></div>
							</div>
							
							<div class="col-xs-4">
								<label class="col-xs-7"><%=etiqueta.OTROS_IMPUESTOS%>:</label>
								<div class="col-xs-3 money-data-container"><span class="importe-total money-data">
									$<span id="detOtrosImpuestos" class="moneyFormat"></span></span></div>
							</div>
						</div>
						
						<!-- Tabla -->
						<div class="row" style="padding-top:15px;">
							<div class="col-xs-9"><label style="margin-top:8px"><%=etiqueta.INGRESE_INFO_DESGLOSE%>:</label></div>
							<div class="col-xs-3" style="text-align: right;">
								<button type="button" onclick="addRowDesgloseDetalle()" class="btn btn-default"><span class="glyphicon glyphicon-plus" style="color:#717171;; padding-right:4px;"></span>Agregar</button>
							</div>
						</div>
							<div class="panel-body" style="padding: 20px 0px 0px 0px">
								<div class="dataTable_wrapper">
									<table style="font-size: 90%;"
										class="table table-striped table-bordered table-hover display nowrap"
										id="talbaDesgloseDetalle">
										<thead>
											<tr>
												<th><%=etiqueta.LINEA%></th>
												<th></th>
												<th><%=etiqueta.SUBTOTAL%></th>
												<th><%=etiqueta.OTROS_IMPUESTOS%></th>
												<th><%=etiqueta.CONCEPTO%></th>
												<th><%=etiqueta.BLD%></th>
												<th><%=etiqueta.LOCACION%></th>
												<th><%=etiqueta.CUENTA_CONTABLE%></th>
												<th><%=etiqueta.NOMBRE%></th>
											</tr>
										</thead>
										<tbody>										
									
										</tbody>
									</table>
								</div> <!-- Fin datatable wrapper -->
								
								<div class="col-xs-12  import-desglose-fijo" style="padding:0px">
									<div class="col-xs-9">
										<label class="col-xs-4"><%=etiqueta.SALDOS_DESGLOSADOS%>:</label>
										<div class="col-xs-1 money-data-container"><span class="importe-total money-data">
											$<span id="detSaldoSubtotal" class="moneyFormat"></span></span></div>
										<div class="col-xs-3 col-xs-offset-1 money-data-container"><span class="importe-total money-data">
											$<span id="detSaldoOtrosImpuestos" class="moneyFormat"></span></span></div>
									</div>
									
									<div class="col-xs-3" style="text-align: right; padding-right:0px;">
										<button onclick="cancelDesgloseDetalle()" type="button" class="btn btn-default"><%=etiqueta.CANCELAR%></button>
										<button data-dismiss="modal" type="button" class="btn btn-primary"><%=etiqueta.GUARDAR%></button>
									</div>
									
								</div>
								
								<div class="col-xs-12  import-desglose-fijo" style="margin-top:6px; padding:0px">
									<div class="col-xs-9">
										<label class="col-xs-4"><%=etiqueta.PENDIENTE_POR_DESGLOSAR%>:</label>
										<div class="col-xs-1 money-data-container"><span class="importe-total money-data">
											$<span id="detPendSubtotal" class="moneyFormat"></span></span></div>
										<div class="col-xs-3 col-xs-offset-1 money-data-container"><span class="importe-total money-data">
											$<span id="detPendOtrosImpuestos" class="moneyFormat"></span></span></div>
										
									</div>
									
									<div id="esDesgCompleto" class="indicador" style="text-align: right;margin-top: 3px; display:none;">
										<div style="display: inline-block;padding: 4px 8px 2px 8px;padding-right:0px;overflow: auto;">
											<p class="glyphicon glyphicon-ok-circle palomita desglosado desglosado-completo" style="display: inline-block;float: left;"></p>
											<strong style="font-weight: bold; font-size: 15px; margin-left: 8px;">Desglose completo</strong>
										</div>
									</div>
									
									
								</div>
								
								
							</div>
					</div>
				</div>
			</div>
		<!-- /#page-wrapper -->
		</form:form>
		<!-- /Page Content -->
	</div>
</div>

<script>

	

</script>
		