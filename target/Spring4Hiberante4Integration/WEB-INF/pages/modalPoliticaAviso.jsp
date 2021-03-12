<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>

    				

    				
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
		<div class="modal fade" id="politica-aviso" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
					<div class="modal-content">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo"><%=etiqueta.POLITICA_GASTOS_VIAJE%></span>
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
									<div class="glyphicon glyphicon-alert" style=" padding-right: 8px; font-size: 75px; vertical-align: -1px; text-align: center; left: 19%; color:#ddd;"></div>
									</div>
									<div class="col-xs-9">
										<p style="text-align:justify"><%=etiqueta.AVISO_ANEXAR%></p>
										<p style="text-align:justify"><strong><%=etiqueta.NOTA%>:</strong> <%=etiqueta.PRESENTE_AUTORIZACION%></p>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12">
										<form:checkbox path="comprobacionAnticipoViajeDTO.preguntaExtranjero2" id="aceptar"/><label style="vertical-align: 2px;padding-left: 5px;"><%= etiqueta.ACEPTAR %></label>
									</div>
								</div>
								<div class="form-group col-xs-12" style="border-top: 2px dotted #ccc;" >
									<div class="blocker-acepta"></div>
									<div onclick="" id="acepta-aviso" class="btn btn-primary" style="float: right;margin-bottom: 1px;margin-top: 16px;">
										<span><%=etiqueta.GUARDAR%></span>
									</div>
								</div>
																		
							</div>
						</div>
					</div>

			</div>
		</div>
		
		<script>
		
		</script>
		
