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
	</style>

</head>

<body>

	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%= etiqueta.USUARIO_CONFIGURACION %></h1>
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
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="boton_crear btn btn-primary" data-toggle="modal"
								data-target="#myModal"><%= etiqueta.AGREGAR_USUARIO_CONFIGURACION %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-usuarioConfSolicitante">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.USUARIO %></th>
											<th><%= etiqueta.TIPO_SOLICITUD %></th>
											<th><%= etiqueta.LOCACION %></th>
											<th><%= etiqueta.CUENTA_CONTABLE %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${usuarioConfSolicitanteList}"
											var="uConfList">
											<tr class="odd gradeX">
												<td><c:out value="${uConfList.idUsuarioConfSolicitante}" /></td>
												<td><c:out value="${uConfList.usuario.nombre} ${uConfList.usuario.apellidoPaterno}" /></td>
												<td><c:out value="${uConfList.tipoSolicitud.descripcion}" /></td>
												<td><c:out value="${uConfList.locacion.numero}" /></td>
												<td><c:out value="${uConfList.cuentaContable.numeroCuentaContable}" /></td>
												<td class="center">
													<button
														title="<%= etiqueta.ELIMINAR %>"
														onclick="eliminar(${uConfList.idUsuarioConfSolicitante})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button
														title="<%= etiqueta.EDITAR %>"
														onclick="editar(${uConfList.idUsuarioConfSolicitante})"
														style="height: 22px;" type="button"
														class="boton_editar btn btn-xs btn-warning">
														<span class="glyphicon glyphicon-pencil"></span>
													</button>
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
	</div>




	<!-- Ventana Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<form:form id="usuarioConfSolicitanteForm" cssClass="form-horizontal"
				modelAttribute="usuarioConfSolicitante" method="post"
				action="saveUsuarioConfSolicitante">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="tipoUsuarioConfSolicitante">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						</div>

						<!--  id usuario configuracion -->
						<form:hidden id="idUsuarioConfSolicitante" path="idUsuarioConfSolicitante" value="" />

						<!--  combo usuario -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="usuario_list"><%= etiqueta.USUARIO %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idUsuario" 
									cssClass="form-control selectpicker" 
									data-live-search="true"
									id="usuario_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${usuarioList}" var="usuarioList">
										<option value="${usuarioList.idUsuario}">${usuarioList.nombre} ${usuarioList.apellidoPaterno} ${usuarioList.apellidoMaterno}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
						
						<!--  combo tipo solicitud -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="tipoSolicitud_list"><%= etiqueta.TIPO_SOLICITUD %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idTipoSolicitud" cssClass="form-control" id="tipoSolicitud_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${tipoSolicitudList}" var="tipoSolist">
										<option value="${tipoSolist.idTipoSolicitud}">${tipoSolist.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
						
						<!--  combo locaciones -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="locacion_list"><%= etiqueta.LOCACION %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idLocacion" cssClass="form-control" id="locacion_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${locacionList}" var="locList">
										<option value="${locList.idLocacion}">${locList.numero} - ${locList.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
						
						<!--  combo cuenta contable -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="cuentaContable_list"><%= etiqueta.CUENTA_CONTABLE %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idCuentaContable" 
									cssClass="form-control selectpicker" 
									data-live-search="true"
									id="cuentaContable_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${cuentaContableList}" var="cuentaContList">
										<option value="${cuentaContList.idCuentaContable}">${cuentaContList.numeroCuentaContable} - ${cuentaContList.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" id="saveUsuarioConfSolicitante"
							class="btn btn-primary" value="<%= etiqueta.GUARDAR %>" />

						<button type="button" class="btn btn-default" data-dismiss="modal">
							<%= etiqueta.CERRAR %></button>
					</div>
				</div>
			</form:form>

		</div>
	</div>

	<!-- Modal Warning Eliminar -->
	<div id="myModalEliminar" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<h4><%= etiqueta.ATENCION_ELIMINAR %></h4>
				</div>
				<div class="modal-footer">
					<a href="#" id="eliminarButton" class="btn btn-danger"
						role="button"><%= etiqueta.ELIMINAR %></a>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<%= etiqueta.CANCELAR %></button>
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

	<script>
	
	e_titulo = '<%= etiqueta.CONFIGURACION_SOLICITANTE %>';
	    
	$(function(){
		
		$("#usuarioConfSolicitanteForm").submit(function(event){
			$("#error-sign").hide();
			var ban = false;
			
			
			//USUARIO
			if($("#usuario_list").val() == '-1'){
				ban = true;
				$('[data-id="usuario_list"]').addClass("errorx");
			}else{
				$('[data-id="usuario_list"]').removeClass("errorx");
			}
			
			//tipo solicitud
			if($("#tipoSolicitud_list").val() == '-1'){
				ban = true;
				$("#tipoSolicitud_list").addClass("errorx");
			}else{
				$("#tipoSolicitud_list").removeClass("errorx");
			}
			
			//locacion
			if($("#locacion_list").val() == '-1'){
				ban = true;
				$("#locacion_list").addClass("errorx");
			}else{
				$("#locacion_list").removeClass("errorx");
			}
			
			//cuenta contable
			if($("#cuentaContable_list").val() == '-1'){
				ban = true;
				$('[data-id="cuentaContable_list"]').addClass("errorx");
			}else{
				$('[data-id="cuentaContable_list"]').removeClass("errorx");
			}
			
			
			if(ban){
				$("#error-sign").show();
				console.log("false");
				loading(false);
				return false;
			}else{
				console.log("true");
				return true;
			}
		});
	});
	
    $(document).ready(function() {
    
		console.log("<%=errorHead%>");
    	
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
        $('#dataTables-usuarioConfSolicitante').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#idUsuarioConfSolicitante").val(0);
        	$("#usuario_list").val(-1);
        	$("#tipoSolicitud_list").val(-1);
        	$("#locacion_list").val(-1);
        	$("#cuentaContable_list").val(-1);
        	$('.selectpicker').selectpicker('refresh');
        });
        
    });
    
    function editar(id) {
        if(id > 0){
        console.log(id);
       		 loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getUsuarioConfSolicitante",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion +" -> "+response.numero+" -> "+response.id);
                	$("#usuario_list").val(response.idUsuario);
                	$("#tipoSolicitud_list").val(response.idTipoSolicitud);
                	$("#locacion_list").val(response.idLocacion);
                	$("#cuentaContable_list").val(response.idCuentaContable);
                	//$('select[id=cuentaContable_list]').val(response.idCuentaContable);
                	$('.selectpicker').selectpicker('refresh')
                	$("#idUsuarioConfSolicitante").val(response.idUsuarioSolicitante);
                	// mostrando el modal
                	//abrir ventana modal
                	$('#myModal').modal('show'); 
                },
                error: function(e) {
                	loading(false);
                    console.log('Error: ' + e);
                },
            }); 
            
        	}//if
        }
        
        
        function eliminar(id){
        	console.log(id);
        	$("#eliminarButton").prop("href","deleteUsuarioConfSolicitante?id="+id);
        	$("#myModalEliminar").modal('show');
        	
        }
        
        function getValue() {
            var x = document.getElementById("cmbTipoUsuarioConfSolicitante").value;
            $("input[name='idTipoUsuarioConfSolicitante']").val(x);
        }

        
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>