<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas" %>
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
						<h1 class="page-header"><%= etiqueta.MONEDAS %></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
           
           <div style="display: none;" id="error-alert"
				class="alert alert-danger fade in">
				<strong> <span id="error-head"> <%=errorHead%>
				</span>
				</strong> <span id="error-body"> <%=errorBody%>
				</span>
			</div>
           
          <!--  Estructura de contenido. Moneda -->
            <div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#agregarMoneda"><%= etiqueta.AGREGAR_MONEDA %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tablaMoneda">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.DESCRIPCION_CORTA %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${monedaList}" var="mlist">
											<tr class="odd gradeX">
												<td><c:out value="${mlist.idMoneda}" /></td>
												<td><c:out value="${mlist.descripcion}" /></td>
												<td><c:out value="${mlist.descripcionCorta}" /></td>
												<td class="center"> 
													<button 
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${mlist.idMoneda})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button 
														title="<%= etiqueta.EDITAR %>" 
														onclick="editar(${mlist.idMoneda})" 
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
			
			<!-- Ventana Modal Agregar Moneda -->
					<div class="modal fade" id="agregarMoneda" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<form:form id="monedaForm" cssClass="form-horizontal"
								modelAttribute="moneda" method="post" action="saveMoneda">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title"><%=etiqueta.AGREGAR_MONEDA%></h4>
									</div>
									<div class="modal-body">

										<div id="error-sign" style="display: none;"
											class="alert alert-warning">
											<strong><%=etiqueta.ATENCION%></strong>
											<%=etiqueta.COMPLETE%>
										</div>


										<div class="form-group">
											<div class="control-label col-xs-3">
												<form:label path="descripcion"><%= etiqueta.DESCRIPCION %></form:label>
											</div>
											<div class="col-xs-6">
												<form:select  onchange="setDescripcion(this)"
												path="descripcion"
													cssClass="form-control selectpicker" 
													data-live-search="true"
													id="idTipoMoneda">
													<option value="-1"><%=etiqueta.SELECCIONE%></option>
													<c:forEach items="${tipoMonedaList}"
														var="moneda">
														<option value="${moneda.descripcion}"> ${moneda.descripcion} </option>
													</c:forEach>
												</form:select>
												 <form:hidden id="idMoneda" path="idMoneda" value="" />
											</div>
										</div>

										<div class="form-group">
											<div class="control-label col-xs-3">
												<form:label path="descripcionCorta"><%= etiqueta.DESCRIPCION_CORTA %></form:label>
											</div>
											<div class="col-xs-6">
												<form:input cssClass="form-control" id="descripcionCorta"
													path="descripcionCorta" value="" type="text"
													required="true"
													title="La longitud máxima es de 10 caracteres"
													maxlength="10" />
											</div>
										</div>


									</div>
									<div class="modal-footer">
										<input type="submit" id="saveMoneda" class="btn btn-primary"
											value="<%= etiqueta.GUARDAR %>"
											onclick="return submitMonedaForm();" />
										<button type="button" class="btn btn-default"
											data-dismiss="modal"><%= etiqueta.CERRAR %></button>
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
									<button type="button" class="btn btn-default"
										data-dismiss="modal"><%=etiqueta.CANCELAR%></button>
								</div>
							</div>

						</div>
					</div>

				</div>
        <!-- /#page-wrapper -->
    </div>
    
  <jsp:include page="template/includes.jsp" />
  
  <!-- Scripts especificos ....  -->

	<!-- DataTables JavaScript -->
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<script>
	
	e_titulo = '<%= etiqueta.MONEDA_LBL %>';
	
	function submitMonedaForm(){
		$("#error-sign").hide();
		var ban = false;

		
		if($('#descripcion').val() == ""){
			ban = true;
			$("#descripcion").addClass("errorx");
		}else{
			$("#descripcion").removeClass("errorx");
		}
		
		if($('#descripcionCorta').val() == ""){
			ban = true;
			$("#descripcionCorta").addClass("errorx");
		}else{
			$("#descripcionCorta").removeClass("errorx");
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
    	
        $('#tablaMoneda').DataTable({
                responsive: true
        });
        
        $('#agregarMoneda').on('hidden.bs.modal', function () {
        	// limpiar formulario.
//         	$("#descripcion").val(null);
        	$("#descripcionCorta").val(null);
        	$("#idMoneda").val(0);
        });
        


        
    });
	
	function editar(idMoneda) {
        
    	
        if(idMoneda > 0){
        	
        console.log(idMoneda);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getMoneda",
                async: true,
                data: "intxnId=" + idMoneda,
                success: function(response) {
                	loading(false);
                	$("#idTipoMoneda").val(response.descripcion).change();
                	$("#descripcionCorta").val(response.descripcionCorta);

                	$("#idMoneda").val(response.idMoneda);
                	//abrir ventana modal
                	$('#agregarMoneda').modal('show'); 
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
			$("#eliminarButton").prop("href","deleteMoneda?idMoneda="+id);
			$("#myModalEliminar").modal('show');
			
		}
		
		 function setDescripcion(sel){
// 			 console.log($("#idTipoMoneda :selected").text());
// 			$("#descripcion").val($("#idTipoMoneda :selected").text());
		 }
	
	</script>

</body>
</html>
