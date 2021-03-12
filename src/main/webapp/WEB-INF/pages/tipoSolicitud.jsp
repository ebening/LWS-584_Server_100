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
						<h1 class="page-header"><%= etiqueta.TIPO_SOLICITUD %></h1>
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

			<!--  Estructura de contenido. Tipo Solicitud -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#agregarTipoSolicitud"><%= etiqueta.AGREGAR_TIPO_SOLICITUD %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tablaTipoSolicitud">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.MONTO_SOLICITUD_PESOS %></th>
											<th><%= etiqueta.MONTO_SOLICITUD_DOLARES %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${tipoSolicitudList}" var="tslist">
											<tr class="odd gradeX">
												<td><c:out value="${tslist.idTipoSolicitud}" /></td>
												<td><c:out value="${tslist.descripcion}" /></td>
												<td>$<input disabled="disabled" style="background-color: transparent; 
													border-color: white; border: none;" type="text" class="currencyFormat" 
													value="${tslist.montoConfirmacionPesos}" />
												</td>
												<td>$<input disabled="disabled" style="background-color: transparent; 
													border-color: white; border: none;" type="text" class="currencyFormat" 
													value="${tslist.montoConfirmacionDolares}" />
												</td>
												<td width="10%" class="center">
													<button title="<%= etiqueta.ELIMINAR %>"
														onclick="eliminar(${tslist.idTipoSolicitud})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button title="<%= etiqueta.EDITAR %>"
														onclick="editar(${tslist.idTipoSolicitud})"
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
					</div>
				</div>
			</div>
		</div><!-- /#page-wrapper -->
	</div>

	<!-- Ventana Modal Agregar Tipo Solicitud -->
  	<div class="modal fade" id="agregarTipoSolicitud" role="dialog">
    	<div class="modal-dialog">
    
      		<!-- Modal content-->
     				<form:form id="tipoSolicitudForm" cssClass="form-horizontal" modelAttribute="tipoSolicitud" method="post" action="saveTipoSolicitud">
      					<div class="modal-content">
        					<div class="modal-header">
          						<button type="button" class="close" data-dismiss="modal">&times;</button>
          							<h4 class="modal-title"></h4>
        					</div>
        					<div class="modal-body">
        						<div  id ="error-sign"  style="display:none;" class="alert alert-warning">
               						<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
        						</div>
            
                     			<div class="form-group">
                        			<div class="control-label col-xs-3"> 
                        				<form:label path="descripcion" ><%= etiqueta.DESCRIPCION %></form:label> </div>
                        					<div class="col-xs-6">
                            					<form:hidden id="idTipoSolicitud" path="idTipoSolicitud" value=""/>
                            					<form:input cssClass="form-control" id="descripcion" path="descripcion" value=""/>
                        					</div>
                    			</div>
                    			
                    			<div class="form-group">
                        			<div class="control-label col-xs-3"> 
                        				<form:label path="strMontoConfirmacionPesos"><%= etiqueta.MONTO_SOLICITUD_PESOS %></form:label> </div>                        				
                        				<div class="col-xs-6">
											<form:input cssClass="form-control currencyFormat" 
                            					id="strMontoConfirmacionPesos" 
                            					path="strMontoConfirmacionPesos" value=""/>														
										</div>                        				                        				                        				
                    			</div>
                    			
                    			<div class="form-group">
                        			<div class="control-label col-xs-3"> 
                        				<form:label path="strMontoConfirmacionDolares"><%= etiqueta.MONTO_SOLICITUD_DOLARES %></form:label> </div>
                        					<div class="col-xs-6">
                            					<form:input cssClass="form-control currencyFormat" id="strMontoConfirmacionDolares" 
                            						path="strMontoConfirmacionDolares" value=""/>
                        					</div>
                    			</div>                    			
        					</div>
        					<div class="modal-footer">
          						<input type="submit" id="saveTipoSolicitud" class="btn btn-primary" value="Guardar" onclick="return submitTipoSolicitudForm();"/>
          						<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CERRAR %></button>
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
							<h4><%= etiqueta.ATENCION_ELIMINAR %></h4>
						</div>
						<div class="modal-footer">
							<a href="#" id="eliminarButton" class="btn btn-danger" role="button"><%= etiqueta.ELIMINAR %></a>
								<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CANCELAR %></button>
						</div>
					</div>
				</div>
			</div>
    
	<jsp:include page="template/includes.jsp" />
  	
  	<!-- Scripts especificos ....  -->

	<!-- DataTables JavaScript -->
		<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
		<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
		
		<script>
		
		e_titulo = '<%= etiqueta.TIPO_SOLICITUD_LBL %>';
	
	function submitTipoSolicitudForm(){
		$("#error-sign").hide();
		var ban = false;
		
		
		if($('#strMontoConfirmacionPesos').val() == ""){
			ban = true;
			$("#strMontoConfirmacionPesos").addClass("errorx");
		}else{
			$("#strMontoConfirmacionPesos").removeClass("errorx");
		}
		if($('#strMontoConfirmacionDolares').val() == ""){
			ban = true;
			$("#strMontoConfirmacionDolares").addClass("errorx");
		}else{
			$("#strMontoConfirmacionDolares").removeClass("errorx");
		}
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
                
        $('#agregarTipoSolicitud').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#strMontoConfirmacionPesos").val("");
        	$("#strMontoConfirmacionDolares").val("");
        	$("#descripcion").val(null);
        	$("#idTipoSolicitud").val(0);
        	$("#error-sign").hide();
        });
        
    });
	
	$('.currencyFormat').number( true, 2 );	
    $('#tablaTipoSolicitud').DataTable({
            responsive: true
    });
	
	function editar(idTipoSolicitud) {
    	
        if(idTipoSolicitud > 0){
        	
        	console.log(idTipoSolicitud);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getTipoSolicitud",
                async: true,
                data: "intxnId=" + idTipoSolicitud,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion +" -> "+response.idTipoSolicitud);
                	$("#descripcion").val(response.descripcion);
                	$("#strMontoConfirmacionPesos").val(response.montoConfirmacionPesos);
                	$("#strMontoConfirmacionDolares").val(response.montoConfirmacionDorales);
                	$("#idTipoSolicitud").val(response.idTipoSolicitud);
                	//abrir ventana modal
                	$('#agregarTipoSolicitud').modal('show'); 
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
			$("#eliminarButton").prop("href","deleteTipoSolicitud?idTipoSolicitud="+id);
			$("#myModalEliminar").modal('show');
			
		}
	
	</script>
	
</body>
</html>
