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
						<h1 class="page-header"><%= etiqueta.LOCACIONES %></h1>
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

			<!-- Estructura de contenido. Locación -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#myModal"><%= etiqueta.AGREGAR_LOCACION %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-locacion">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.NUMERO %></th>
											<th><%= etiqueta.TIPO_LOCACION %></th>
											<th>Director</th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${locacionList}" var="locList">
											<tr class="odd gradeX">
												<td><c:out value="${locList.idLocacion}" /></td>
												<td><c:out value="${locList.descripcion}" /></td>
												<td><c:out value="${locList.numero}" /></td>												
												<td><c:out value="${locList.tipoLocacion.idTipoLocacion} - ${locList.tipoLocacion.descripcion}" /></td>
												<td><c:out value="${locList.director.numeroNombreCompletoUsuario}" /></td>												
												
												<td class="center">
													<button 
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${locList.idLocacion})" 
														style="height: 22px;" type="button" 
														class="btn btn-xs btn-danger">
	                                                   <span class="glyphicon glyphicon-trash"></span>
	                                                 </button>
	                                                 
	                                                  <button 
	                                                  	title="<%= etiqueta.EDITAR %>" 
	                                                  	onclick="editar(${locList.idLocacion})" 
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
			<form:form id="locacionForm" cssClass="form-horizontal"
				modelAttribute="locacion" method="post" action="saveLocacion">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" onclick="tipoLocacion">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						</div>

						<div class="form-group">
							
							<div class="control-label col-xs-3">
								<form:label path="tipoLocacion"><%= etiqueta.TIPO_LOCACION %></form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden id="idLocacion" path="idLocacion" value="" />
								<div class="input-group-btn">
								
					                <form:select id ="idTipoLocacion" path ="idTipoLocacion" class="form-control" onChange="getValueLocacion();">
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
								<form:label path="descripcion"><%= etiqueta.DESCRIPCION %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="descripcion"
									path="descripcion" value="" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="numero" cssClass="control-label col-xs-3"><%= etiqueta.NUMERO %></form:label>
							<div class="col-xs-6">
								<form:input type="number" cssClass="form-control" id="numero" path="numero" value="" required='' />
							</div>
						</div>
						
						<div class="form-group">
							<form:label path="numero" cssClass="control-label col-xs-3">Director</form:label>
							<div class="col-xs-6">
								<form:select path="director.idUsuario" cssClass="form-control selectpicker" data-live-search="true"
									id="idDirector" >
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${lstDirectores}" var="ulist">
										<option class="jefe-id-${ulist.idUsuario}" value="${ulist.idUsuario}">${ulist.nombre} ${ulist.apellidoPaterno}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						
						

					</div>
					<div class="modal-footer">
						<input type="submit" id="saveLocacion" class="btn btn-primary"
							value="<%= etiqueta.GUARDAR %>"  />

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
					<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CANCELAR %></button>
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
	e_titulo = '<%= etiqueta.LOCACION %>';
	
	$(function(){
		
		$("#locacionForm").submit(function(event){
			$("#error-sign").hide();
			$("#error-pass").hide();
		    $("#mensaje-label").text("<%= etiqueta.COMPLETE %>");
		    
			var ban = false;
			
			
			if($('#descripcion').val() == ""){
				ban = true;
				$("#descripcion").addClass("errorx");
			}else{
				$("#descripcion").removeClass("errorx");
			}
			
			if($('#numero').val() == ""){
				ban = true;
				$("#numero").addClass("errorx");
			}else{
				$("#numero").removeClass("errorx");
			}
			
			//id tipo locacion
			if($("#idTipoLocacion").val() == '-1'){
				ban = true;
				$("#idTipoLocacion").addClass("errorx");
			}else{
				$("#idTipoLocacion").removeClass("errorx");
			}
			
			if(ban){
				$("#error-sign").show();
				console.log("false");
				event.preventDefault();
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
		$('#numero').val("");
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
        $('#dataTables-locacion').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#descripcion").val(null);
        	$("#numero").val(null);
        	$("#idTipoLocacion").val(-1);
        	$("#idTipoLocacion").removeClass("errorx");
        	$("#idLocacion").val(0);
			$("#error-sign").hide();
			$("#idDirector").val(-1);
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
                url: "${pageContext.request.contextPath}/getLocacion",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion +" -> "+response.numero+" -> "+response.id);
                	$("#descripcion").val(response.descripcion);
                	$("#numero").val(response.numero);
                	$("#idLocacion").val(response.id);
                	$("#idTipoLocacion").val(response.idTipoLocacion);
                	$("#idDirector").val(response.director);
                	// mostrando el modal
                	//abrir ventana modal
                	$('.selectpicker').selectpicker('refresh');
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
        	$("#eliminarButton").prop("href","deleteLocacion?id="+id);
        	$("#myModalEliminar").modal('show');
        	
        }
        
        function getValueLocacion() {
            var x = document.getElementById("idTipoLocacion").value;
            $("input[name='idTipoLocacion']").val(x);
        }

        
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>