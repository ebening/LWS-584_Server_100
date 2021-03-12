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

</head>

<body>

	<div id="wrapper">
		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%= etiqueta.PUESTO %></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
			
			 <div style="display: none;" id="error-alert"
				class="alert alert-danger fade in">
				<strong>
					<span id="error-head"><%=errorHead%></span></strong> 
					<span id="error-body"><%=errorBody%></span>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="boton_crear btn btn-primary" 
								data-toggle="modal"
								data-target="#myModal"><%= etiqueta.AGREGAR_PUESTO %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-puesto">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.TIPO_LOCACION %></th>
											<th><%= etiqueta.NIVEL_AUTORIZACION %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${puestoList}" var="puestoList">
											<tr class="odd gradeX">
												<td><c:out value="${puestoList.idPuesto}" /></td>
												<td><c:out value="${puestoList.descripcion}" /></td>
												<td><c:out value="${puestoList.tipoLocacion.idTipoLocacion} - ${puestoList.tipoLocacion.descripcion}" /></td>
												<td>
													<c:choose>													
														<c:when test="${puestoList.nivelAutoriza.idNivelAutoriza > 0}">
															<c:out value="${puestoList.nivelAutoriza.idNivelAutoriza} - $${puestoList.nivelAutoriza.pesosLimite}M.N. / $${puestoList.nivelAutoriza.dolaresLimite} USD" />
														</c:when>
													</c:choose>
												</td>
												<td class="center">
													<button 
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${puestoList.idPuesto})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button 
														title="<%= etiqueta.EDITAR %>" 
														onclick="editar(${puestoList.idPuesto})"
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
			<form:form id="puestoForm" cssClass="form-horizontal"
				modelAttribute="puesto" method="post" action="savePuesto">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="tipoLocacion">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						</div>

						<form:hidden id="idPuesto" path="idPuesto" value="" />

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%= etiqueta.DESCRIPCION %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="descripcion"
									path="descripcion" value="" />
							</div>
						</div>

						<div class="form-group">
							
							<div class="control-label col-xs-3">
								<form:label path="tipoLocacion"><%= etiqueta.TIPO_LOCACION %></form:label>
							</div>
							<div class="col-xs-6">
								<div class="input-group-btn">
									<form:select id ="idTipoLocacion" path ="idTipoLocacion" class="form-control"
										onChange="getValue();">
										<option value="-1"><%= etiqueta.SELECCIONE %></option>
										<c:forEach items="${tipoLocacionList}" var="tipoLoclist">
											<option value="${tipoLoclist.idTipoLocacion}">${tipoLoclist.descripcion}</option>
										</c:forEach>
									</form:select>
								</div>

							</div>
						</div>

						<div class="form-group">
							
							<div class="control-label col-xs-3">
								<form:label path="nivelAutoriza"><%= etiqueta.NIVEL_AUTORIZA %></form:label>
							</div>
							<div class="col-xs-6">
								<div class="input-group-btn">
									<form:select id ="idNivelAutoriza" path ="idNivelAutoriza" class="form-control"
										onChange="getValue();">
										<option value="-1"><%= etiqueta.SELECCIONE %></option>
										<c:forEach items="${nivelAutorizaList}" var="nivAutlist">
											<option value="${nivAutlist.idNivelAutoriza}">
												${nivAutlist.pesosLimite} <%= etiqueta.MN %> / ${nivAutlist.dolaresLimite} <%= etiqueta.USD %>
											</option>
										</c:forEach>
									</form:select>
								</div>

							</div>
						</div>

					</div>
					<div class="modal-footer">
						<input type="submit" id="savePuesto" class="btn btn-primary"
							value="<%= etiqueta.GUARDAR %>" />

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
					<button type="button" class="btn btn-default" 
					data-dismiss="modal"><%= etiqueta.CANCELAR %></button>
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
	
	e_titulo = '<%= etiqueta.PUESTO_LBL %>';
	    
		$(function(){
		
			$("#puestoForm").submit(function(event){
				$("#error-sign").hide();
				var ban = false;
				
				
				if($('#descripcion').val() == ""){
					ban = true;
					$("#descripcion").addClass("errorx");
				}else{
					$("#descripcion").removeClass("errorx");
				}
				
				//tipo locacion
				if($("#idTipoLocacion").val() == '-1'){
					ban = true;
					$("#idTipoLocacion").addClass("errorx");
				}else{
					$("#idTipoLocacion").removeClass("errorx");
				}
				
				if(ban){
					$("#error-sign").show();
					console.log("false");
					loading(false);
					return false;
				}else{
					console.log("true");
					loading(false);
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
    	
        $('#dataTables-puesto').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#idPuesto").val(0);
        	$("#descripcion").val(null);
        	$("#idTipoLocacion").val(-1);
        	$("#idTipoLocacion").removeClass("errorx");
        	$("#idNivelAutoriza").val(-1);
			$("#error-sign").hide();

        });
        
    });
    
    function editar(id) {
        if(id > 0){
        console.log(id);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getPuesto",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion +" -> "+response.numero+" -> "+response.id);
                	$("#descripcion").val(response.descripcion);
                	$("#idNivelAutoriza").val(response.idNivelAutoriza);
                	$("#idTipoLocacion").val(response.idTipoLocacion);
                	$("#idPuesto").val(response.idPuesto);

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
        	$("#eliminarButton").prop("href","deletePuesto?id="+id);
        	$("#myModalEliminar").modal('show');
        	
        }
        
        function getValue() {
            var x = document.getElementById("idTipoLocacion").value;
            $("input[name='idTipoLocacion']").val(x);
        }     
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>