<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
						<h1 class="page-header"><%= etiqueta.VIAJE_CONCEPTO %></h1>
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

			<!--  Estructura de contenido -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="boton_crear btn btn-primary" data-toggle="modal"
								data-target="#modal"><%= etiqueta.AGREGAR %> <%= etiqueta.VIAJE_CONCEPTO_LBL %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tabla">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.DOLARES_TARIFA %></th>
											<th><%= etiqueta.PESOS_TARIFA %></th>
											<th><%= etiqueta.ES_CALCULADO %></th>
											<th><%= etiqueta.CALCULO_IMPORTE_DIARIO %></th>
											<th><%= etiqueta.CALCULO_DIAS %></th>
											<th><%= etiqueta.CALCULO_PERSONAS %></th>
											<th><%= etiqueta.ES_OTRO %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${viajeConceptoList}" var="list">
											<tr class="odd gradeX">
												<td><c:out value="${list.idViajeConcepto}" /></td>
												<td><c:out value="${list.descripcion}" /></td>
												<td><c:out value="${list.dolaresTarifa}" /></td>
												<td><c:out value="${list.pesosTarifa}" /></td>
												<td><c:out value="${list.esCalculado}" /></td>
												<td><c:out value="${list.calculoImporteDiario}" /></td>
												<td><c:out value="${list.calculoDias}" /></td>
												<td><c:out value="${list.calculoPersonas}" /></td>
												<td><c:out value="${list.esOtro}" /></td>
												
												<td class="center">
													<button title="<%= etiqueta.ELIMINAR %>"
														onclick="eliminar(${list.idViajeConcepto})" style="height: 22px;"
														type="button" class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button title="<%= etiqueta.EDITAR %>"
														onclick="editar(${list.idViajeConcepto})" style="height: 22px;"
														type="button" class="boton_editar btn btn-xs btn-warning">
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
		</div>	<!-- /#page-wrapper -->
	</div>
	
	<!-- Ventana Modal Agregar -->
	<div class="modal fade" id="modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<form:form id="form" cssClass="form-horizontal"
				modelAttribute="viajeConcepto" method="post" action="saveViajeConcepto">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4>
					</div>

					<div class="modal-body">
						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%=etiqueta.ATENCION%></strong> <%=etiqueta.COMPLETE%>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%=etiqueta.DESCRIPCION%></form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden id="idViajeConcepto" path="idViajeConcepto" value="" />
								<form:input cssClass="form-control" id="descripcion" maxlength="100"
									path="descripcion" value="" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="dolaresTarifa"><%=etiqueta.DOLARES_TARIFA%></form:label>
							</div>
							<div class="col-xs-6">
								<form:input id="dolaresTarifa" path="dolaresTarifa" class="form-control currencyFormat"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="pesosTarifa"><%=etiqueta.PESOS_TARIFA%></form:label>
							</div>
							<div class="col-xs-6">
								<form:input id="pesosTarifa" path="pesosTarifa" class="form-control currencyFormat"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="esCalculadoB"><%=etiqueta.ES_CALCULADO%></form:label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="esCalculadoB" path="esCalculadoB" value="" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="calculoImporteDiarioB"><%=etiqueta.CALCULO_IMPORTE_DIARIO%></form:label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="calculoImporteDiarioB" path="calculoImporteDiarioB" value="" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="calculoDiasB"><%=etiqueta.CALCULO_DIAS%></form:label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="calculoDiasB" path="calculoDiasB" value="" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="calculoPersonasB"><%=etiqueta.CALCULO_PERSONAS%></form:label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="calculoPersonasB" path="calculoPersonasB" value="" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="esOtroB"><%=etiqueta.ES_OTRO%></form:label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="esOtroB" path="esOtroB" value="" />
							</div>
						</div>
						
						
						
						
					</div>
					<div class="modal-footer">
						<input type="submit" id="saveViajeConcepto" class="btn btn-primary"
							value="<%=etiqueta.GUARDAR%>" onclick="return submitForm();" />
						<button type="button" class="btn btn-default" data-dismiss="modal"><%=etiqueta.CERRAR%></button>
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<!--      Modal Warning Eliminar -->
	<div id="myModalEliminar" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<h4><%=etiqueta.ATENCION_ELIMINAR%></h4>
				</div>
				<div class="modal-footer">
					<a href="#" id="eliminarButton" class="btn btn-danger"
						role="button"><%=etiqueta.ELIMINAR%></a>
					<button type="button" class="btn btn-default" data-dismiss="modal"><%=etiqueta.CANCELAR%></button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="template/includes.jsp" />
  
  <!-- Scripts especificos ....  -->
  
  <!-- DataTables JavaScript -->
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<script>
	
	e_titulo = '<%= etiqueta.VIAJE_MOTIVO_LBL %>';
	
	function submitForm(){
		$("#error-sign").hide();
		var ban = false;
		
		
		if($('#descripcion').val() == ""){
			ban = true;
			$("#descripcion").addClass("errorx");
		}else{
			$("#descripcion").removeClass("errorx");
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
    	
        $('#tabla').DataTable({
                responsive: true
        });
        
        $('#modal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#descripcion").val(null);
        	$("#idViajeConcepto").val(0);
        	$("#dolaresTarifa").val(null);
        	$("#pesosTarifa").val(null);
        	$("#esCalculado").val(null);
        	$("#calculoImporteDiario").val(null);
        	$("#calculoDias").val(null);
        	$("#calculoPersonas").val(null);
        	$("#esOtro").val(null);
        });
        
    });
	
	function editar(id) {
        
		if(id > 0){
        	console.log(id);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getViajeConcepto",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion+" -> "+response.idViajeConcepto);
                	$("#descripcion").val(response.descripcion);
                	$("#idViajeConcepto").val(response.idViajeConcepto);
                	
                	$("#dolaresTarifa").val(response.dolaresTarifa);
                	$("#pesosTarifa").val(response.pesosTarifa);

                	if(response.esCalculado == 1){
                  		$("#esCalculadoB").prop("checked", true);
                  	}
                  	else{
                  		$("#esCalculadoB").prop("checked", false);
                  	} 
                	if(response.calculoImportediario == 1){
                  		$("#calculoImporteDiarioB").prop("checked", true);
                  	}
                  	else{
                  		$("#calculoImporteDiarioB").prop("checked", false);
                  	}
                	if(response.calculoDias == 1){
                  		$("#calculoDiasB").prop("checked", true);
                  	}
                  	else{
                  		$("#calculoDiasB").prop("checked", false);
                  	} 
                	if(response.calculoPersonas == 1){
                  		$("#calculoPersonasB").prop("checked", true);
                  	}
                  	else{
                  		$("#calculoPersonasB").prop("checked", false);
                  	} 
                	if(response.esOtro == 1){
                  		$("#esOtroB").prop("checked", true);
                  	}
                  	else{
                  		$("#esOtroB").prop("checked", false);
                  	} 
                	
                	//abrir ventana modal
                	$('#modal').modal('show'); 
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
			$("#eliminarButton").prop("href","deleteViajeConcepto?idViajeConcepto="+id);
			$("#myModalEliminar").modal('show');
			
		}
	
	</script>
	
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>

</body>
</html>
