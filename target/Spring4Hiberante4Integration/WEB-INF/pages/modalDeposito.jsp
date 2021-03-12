<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    				

    				
  <style>

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
		<div class="modal fade" id="deposito" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<form:form id="capturaDeposito" enctype="multipart/form-data"
					cssClass="form-horizontal" modelAttribute="singleSave"
					method="post" action="guardarDeposito">
					<div class="modal-content">
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
							<span id="titulo-dialogo"><%=etiqueta.DEPOSITO%></span>
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

							<div class="col-xs-12">
								<!-- Inicio de formulario Deposito -->
								<form method="post" enctype="multipart/form-data" action="guardarDeposito" class="form-horizontal">
									<div>
										<input id="id-comprobacion-deposito" type="hidden" >
									</div>
									<div class="">
										<!-- FECHA  -->
										<div class="form-group" style="margin-bottom: 12px;">
											<label class="col-xs-4 control-label"><%=etiqueta.FECHA%>:</label>
											<div class="col-xs-4">
												<input id="fecha-deposito" class="form-control input-group date"
													type="text" data-provide="datepicker" value="">
											</div>
												<form:input type="text" cssClass="form-control" value="${comprobacionAnticipoDTO.fecha_deposito}" id="fecha_deposito" path="fecha_deposito"/>
											</div>
											
									</div>
										
									<!-- IMPORTE A DEPOSITAR  -->
										
									<div class="form-group " >
										<label class="col-xs-4 control-label" style="text-align:right"><%=etiqueta.IMPORTE_A_DEPOSITAR%></label>
										<div class="col-xs-4">
											<input id="importe-deposito" type="text" class="form-control currencyFormat importe-total">
										</div>
									</div>
										
									<!-- ADJUNTAR ARCHIVO  -->
									
									<div class="form-group upload-deposito" >
										<label class="cols-xs-4"style="margin-left: 0px;width: 174px;text-align: right;margin-right: 19px;"><%=etiqueta.ANEXAR_DOCUMENTO%></label>
										
										<div class="fileUpload btn btn-primary" style="margin-bottom: 10px;">
											<span><%=etiqueta.SELECCIONAR_ARCHIVO%></span>
											<input id="anexo-deposito" type="file" name="anexo-deposito" class="upload" style="width: 170px;"/>
										</div>
										<input id="uploadFile" placeholder="No se eligió archivo" disabled="disabled" style="width: 160px;"/>
										<input id="uploadFile2" placeholder="No se eligió archivo" disabled="disabled" style="width: 160px;"/>
	
										<img id="loaderXML" style="display: none;"
											src="${pageContext.request.contextPath}/resources/images/loader.gif" />

									</div>		
										
									</div>

								
							
							
								<div class="dataTable_wrapper" style="display:none">
									<table class="table table-striped table-bordered table-hover" id="tablaAid">
										<thead>
											<tr>
												<th><%=etiqueta.DOCUMENTO%></th>
												<th width="20%"><%=etiqueta.ELIMINAR%></th>
											</tr>
										</thead>
										<tbody id="tablaDoc">
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
											
											<div class="form-group col-xs-12" style="border-top: 2px dotted #ccc;" >
												<div onclick="valInputsDeposito()" id="guarda-deposito" class="btn btn-primary" style="float: right;margin-bottom: 10px;margin-top: 16px;">
													<span><%=etiqueta.GUARDAR%></span>
												</div>
											</div>
											</form>
											
										</div>

							<div class="panel-body">
							
								
							
							
								
							</div>
						</div>
					</div>
				</form:form>

			</div>
		</div>
		
		<script>
		
			
			
		
		</script>
		
