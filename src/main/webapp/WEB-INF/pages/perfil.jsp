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
						<h1 class="page-header"><%= etiqueta.PERFILES %></h1>
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
								data-target="#myModal"><%= etiqueta.AGREGAR_PERFIL %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example">
									<thead>
										<tr>
											<th><%= etiqueta.ID_PERFIL %></th>
											<th><%= etiqueta.NOMBRE_PERFIL %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${perfilList}" var="tlist">
											<tr class="odd gradeX">
												<td><c:out value="${tlist.idPerfil}" /></td>
												<td><c:out value="${tlist.descripcion}" /></td>
												<td><c:out value="${tlist.comentarios}" /></td>
												<td width="2%" class="center">
													<button 
														title="<%= etiqueta.ELIMINAR %>"
														onclick="eliminar(${tlist.idPerfil})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button
														title="<%= etiqueta.EDITAR %>" 
														onclick="editar(${tlist.idPerfil})"
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
			<form:form id="perfilForm" cssClass="form-horizontal"
				modelAttribute="perfil" method="post" action="savePerfil">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						</div>



						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%= etiqueta.NOMBRE_PERFIL %></form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden id="idPerfil" path="idPerfil" value="" />
								<form:input cssClass="form-control" id="descripcion"
									path="descripcion" value="" />

							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%= etiqueta.DESCRIPCION %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="comentarios"
									path="comentarios" value="" maxlength="100"/>

							</div>
						</div>

					</div>
					<div class="modal-footer">
						<input type="submit" id="savePerfil" class="btn btn-primary"
							value="<%= etiqueta.GUARDAR %>" onclick="return submitPerfilForm();" />

						<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CERRAR %></button>
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
	
	e_titulo = '<%= etiqueta.PERFIL %>';	
	
	//VALIDACIONES
	function submitPerfilForm(){
		$("#error-sign").hide();
		var ban = false;
		
		
		if($('#descripcion').val() == ""){
			ban = true;
			$("#descripcion").addClass("errorx");
		}else{
			$("#descripcion").removeClass("errorx");
		}
		
		if($('#comentarios').val() == ""){
			ban = true;
			$("#comentarios").addClass("errorx");
		}else{
			$("#comentarios").removeClass("errorx");
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
	
	
    $(document).ready(function() {
    	
		console.log("<%=errorHead%>");
    	
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
        $('#dataTables-example').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#comentarios").val(null);
        	$("#descripcion").val(null);
        	$("#idPerfil").val(null);
        	$("#error-sign").hide();
        });
        
    });
    
   
     
     
        function editar(idPerfil) {
        
        	
        if(idPerfil > 0){
        	
        console.log(idPerfil);
        loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getPerfil",
                async: true,
                data: "intxnId=" + idPerfil,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion+" -> "+response.idPerfil);
                	$("#comentarios").val(response.comentarios);
                	$("#descripcion").val(response.descripcion);
                	$("#idPerfil").val(response.idPerfil);
                	$('#myModal').modal('show'); 
                },
                error: function(e) {
                	loading(false);
                    console.log('Error: ' + e);
                },
            }); 
            
        	}//if
        }
        
        
        function eliminar(idPerfil){
        	
        	console.log(idPerfil);
        	$("#eliminarButton").prop("href","deletePerfil?idPerfil="+idPerfil);
        	$("#myModalEliminar").modal('show');
        	
        }
        
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>
