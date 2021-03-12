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
						<h1 class="page-header"><%= etiqueta.COMPANIAS %></h1>
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

			<!-- Estructura de contenido. Compañía -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#agregarCompania">
								<%= etiqueta.AGREGAR_COMPANIA %>
								</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tablaCompania">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.NUMERO_COMPANIA %></th>
											<th><%= etiqueta.RFC %></th>
											<th><%= etiqueta.CLAVE_VALIDACION_FISCAL %></th>
											<th><%= etiqueta.ID_ORGANIZACION %></th>
											<th><%= etiqueta.DESCRIPCION_EBS %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${companiaList}" var="clist">
											<tr class="odd gradeX">
												<td><c:out value="${clist.idcompania}" /></td>
												<td><c:out value="${clist.descripcion}" /></td>
												<td><c:out value="${clist.numeroCompania}" /></td>
												<td><c:out value="${clist.rfc}" /></td>
												<td><c:out value="${clist.claveValidacionFiscal}" /></td>
												<td><c:out value="${clist.idOrganizacion}" /></td>
												<td><c:out value="${clist.descripcionEbs}" /></td>
												<td class="center"> 
													<button 
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${clist.idcompania})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button 
														title="<%= etiqueta.EDITAR %>" 
														onclick="editar(${clist.idcompania})" 
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
						</div>
							<!-- /.table-responsive -->
					</div>
				</div>
		</div>

	  <!-- Ventana Modal Agregar Compañia -->
  		<div class="modal fade" id="agregarCompania" role="dialog">
    		<div class="modal-dialog">
    
      <!-- Modal content-->
     <form:form id="companiaForm" cssClass="form-horizontal" modelAttribute="compania" method="post" action="saveCompania">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"></h4>
        </div>
        <div class="modal-body">
        
	        <div  id ="error-sign"  style="display:none;" class="alert alert-warning">
	        	<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
	        </div>
	        <div  id ="alerta"  style="display:none;" class="alert alert-warning">
	        	<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.RFC_INCORRECTO %>
	        </div>
        
			<div class="form-group">
	        	<div class="control-label col-xs-4"> 
	            	<form:label path="descripcion" ><%= etiqueta.DESCRIPCION %></form:label>
	            </div>
	                <div class="col-xs-7">
	                   	<form:hidden id="idcompania" path="idcompania" value=""/>
						<form:input cssClass="form-control" id="descripcion" path="descripcion" value=""/>
					</div>
			</div>			
                    
			<!--  Número compañía -->
			<div class="form-group">
				<div class="control-label col-xs-4">
					<form:label path="numeroCompania"><%= etiqueta.NUMERO_COMPANIA %></form:label>
				</div>
				<div class="col-xs-7">
					<form:input cssClass="form-control" id="numeroCompania" type="text"
						path="numeroCompania" value=""
						title="La longitud máxima es de 2 caracteres" maxlength="2"/>
				</div>
			</div>
			
			<!-- RFC -->
			<div class="form-group">
				<div class="control-label col-xs-4">
					<form:label path="rfc"><%= etiqueta.RFC %></form:label>
				</div>
				<div class="col-xs-7">
					<form:input cssClass="form-control" id="rfc" type="text" 
						path="rfc" value="" onblur="validaRfc(this.value)" />
				</div>
			</div>
			
			<!--  Clave Validación Fiscal -->
			<div class="form-group">
				<div class="control-label col-xs-4">
					<form:label path="claveValidacionFiscal"><%= etiqueta.CLAVE_VALIDACION_FISCAL %></form:label>
				</div>
				<div class="col-xs-7">
					<form:input cssClass="form-control" id="claveValidacionFiscal" type="text"
						path="claveValidacionFiscal" value=""
						title="La longitud máxima es de 36 caracteres" maxlength="36"/>
				</div>
			</div>
			
			<!--  ID_Organizacion -->
			<div class="form-group">
				<div class="control-label col-xs-4">
					<form:label path="idOrganizacion"><%= etiqueta.ID_ORGANIZACION %></form:label>
				</div>
				<div class="col-xs-7">
					<form:input cssClass="form-control" id="idOrganizacion"
						type="number"
						path="idOrganizacion" value=""/>
				</div>
			</div>
			
			<!--  Descripción EBS -->
			<div class="form-group">
				<div class="control-label col-xs-4">
					<form:label path="descripcionEbs"><%= etiqueta.DESCRIPCION_EBS %></form:label>
				</div>
				<div class="col-xs-7">
					<form:input cssClass="form-control" id="descripcionEbs" type="text"
						path="descripcionEbs" value=""
						title="La longitud máxima es de 20 caracteres" maxlength="20"/>
				</div>
			</div>

        </div>
        <div class="modal-footer">
          <input type="submit" id="saveCompania" class="btn btn-primary" value="<%= etiqueta.GUARDAR %>" onclick="return submitCompaniaForm();"/>
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
					
	</div> <!-- /#page-wrapper -->
</div>

	<jsp:include page="template/includes.jsp" />

	<!-- DataTables JavaScript -->
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    	<script	src="${pageContext.request.contextPath}/resources/js/validaRFC.js"></script>
	
	<script>
	
	e_titulo = '<%= etiqueta.COMPANIA %>';
	
	function submitCompaniaForm(){
		$("#error-sign").hide();
		$("#alerta").hide();
		var ban = false;
		var str =$("#rfc").val();
		
		
		if($('#descripcion').val() == ""){
			ban = true;
			$("#descripcion").addClass("errorx");
		}else{
			$("#descripcion").removeClass("errorx");
		}

		if($('#rfc').val() == ""){
			ban = true;
			$("#rfc").addClass("errorx");
		}else{
			$("#rfc").removeClass("errorx");
		}

		if($('#claveValidacionFiscal').val() == ""){
			ban = true;
			$("#claveValidacionFiscal").addClass("errorx");
		}else{
			$("#claveValidacionFiscal").removeClass("errorx");
		}

		if($('#idOrganizacion').val() == ""){
			ban = true;
			$("#idOrganizacion").addClass("errorx");
		}else{
			$("#idOrganizacion").removeClass("errorx");
		}
		
		if($('#numeroCompania').val() == ""){
			ban = true;
			$("#numeroCompania").addClass("errorx");
		}else{
			$("#numeroCompania").removeClass("errorx");
		}

		if($('#descripcionEbs').val() == ""){
			ban = true;
			$("#descripcionEbs").addClass("errorx");
		}else{
			$("#descripcionEbs").removeClass("errorx");
		}
		
		if($('#rfc').val() == ""){
			ban = true;
		}else{
			if(!validaRfc(str)){
				$("#alerta").show();
				console.log("false");
				return false;
			}	
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
    	
        $('#tablaCompania').DataTable({
                responsive: true
        });
        
        
        $('#agregarCompania').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#descripcion").val(null);
        	$("#idcompania").val(0); 
        	$("#numeroCompania").val(null);
        	$("#rfc").val(null);
        	$("#claveValidacionFiscal").val(null);
        	$("#idOrganizacion").val(null);
        	$("#descripcionEbs").val(null);
        	$("#error-sign").hide();
        	$("#alerta").hide();
        });
        
    });
	
	function editar(idcompania) {
    	
        if(idcompania > 0){
        	
        console.log(idcompania);
        	loading(false);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getCompania",
                async: true,
                data: "intxnId=" + idcompania,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion +" -> "+response.idcompania);
                	$("#descripcion").val(response.descripcion);
                	$("#idcompania").val(response.idcompania);
                	$("#numeroCompania").val(response.numeroCompania);
                	$("#rfc").val(response.rfc);
                	$("#claveValidacionFiscal").val(response.claveValidacionFiscal);
                	$("#idOrganizacion").val(response.idOrganizacion);
                	$("#descripcionEbs").val(response.descripcionEbs);
                	//abrir ventana modal
                	$('#agregarCompania').modal('show'); 
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
			$("#eliminarButton").prop("href","deleteCompania?id="+id);
			$("#myModalEliminar").modal('show');
			
		}
	
	</script>
</body>
</html>
