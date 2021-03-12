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
		
        <div style="background: url('${pageContext.request.contextPath}/resources/images/loading.gif') no-repeat center center rgba(238, 238, 238, 0.72)" id="overlay-div" class="overlay-full">
        </div>

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%= etiqueta.PROVEEDORES %></h1>
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
								class="boton_crear btn btn-primary" data-toggle="modal"
								data-target="#myModal"><%= etiqueta.AGREGAR_PROVEEDOR %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-proveedor">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.NUMERO_PROVEEDOR %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.CONTACTO %></th>
											<th><%= etiqueta.RFC %></th>
											<th><%= etiqueta.CORREO %></th>
											<th><%= etiqueta.USUARIO %></th>
											<th><%= etiqueta.PROVEEDOR_RIESGO %></th>
											<th><%= etiqueta.TIPO_PROVEEDOR %></th>
											<th><%= etiqueta.LOCACION %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${modelo.proveedorList}" var="provList">
											<tr class="odd gradeX">
												<td><c:out value="${provList.idProveedor}" /></td>
												<td><c:out value="${provList.numeroProveedor}" /></td>
												<td><c:out value="${provList.descripcion}" /></td>
												<td><c:out value="${provList.contacto}" /></td>
												<td><c:out value="${provList.rfc}" /></td>
												<td><c:out value="${provList.correoElectronico}" /></td>
												<td><c:out value="${provList.usuario.nombre} ${provList.usuario.apellidoPaterno}" /></td>
												<c:choose>
												    <c:when test="${provList.proveedorRiesgo == 0}">
												       <td><c:out value="No" /></td>
												    </c:when>
												    <c:otherwise>
												        <td><c:out value="Si" /></td>
												    </c:otherwise>
												</c:choose>
												<td><c:out value="${provList.tipoProveedor.descripcion}" /></td>
												<td><c:out value="${provList.locacion.descripcion}" /></td>
												<td class="center">
													<button 
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${provList.idProveedor});"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button 
														title="<%= etiqueta.EDITAR %>" 
														onclick="editar(${provList.idProveedor})"
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


	<!-- Ventana Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<form:form id="proveedorForm" cssClass="form-horizontal"
				modelAttribute="proveedor" method="post" action="saveProveedor">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="proveedor">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						</div>
						<div id="alerta" style="display: none;"
							class="alert alert-warning">
							<strong><%=etiqueta.ATENCION%></strong>
							<%=etiqueta.RFC_INCORRECTO%>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="numeroProveedor"><%= etiqueta.NUMERO_PROVEEDOR %></form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden path="idProveedor" id="idProveedor" value="" />
								<form:input type="number" cssClass="form-control" id="numeroProveedor" path="numeroProveedor" value="" />
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%= etiqueta.DESCRIPCION %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="descripcion" path="descripcion" value=""/>
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="contacto"><%= etiqueta.CONTACTO %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="contacto" path="contacto" value="" />
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="correoElectronico"><%= etiqueta.CORREO %></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="correoElectronico"
									type="email"
									path="correoElectronico" value="" />
							</div>
						</div>
						
						<!-- RFC -->
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="rfc"><%=etiqueta.RFC%></form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" id="rfc" type="text" 
									path="rfc" value="" onblur="validaRfc(this.value)" />
							</div>
						</div>
						
						<!--  combo Usuario -->
						<div class="form-group">
							<div class="control-label col-xs-3">
								<label for="idUsuario"><%= etiqueta.USUARIO %></label>
							</div>
							<div class="col-xs-6">
								<form:select path="idUsuario" cssClass="form-control" id="idUsuario">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.usuarioList}" var="ulist">
										<option value="${ulist.idUsuario}">${ulist.nombre} ${ulist.apellidoPaterno}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>

						<!--  combo proveedor riesgo -->
						<input type="hidden" name="isProvRiesgo" />
						<div class="form-group">
							<div class="control-label col-xs-3">
								<label for="proveedorRiesgo"><%= etiqueta.PROVEEDOR_RIESGO %></label>
							</div>
							<div class="col-xs-6">
								<form:select path="proveedorRiesgo" id="proveedorRiesgo" class="form-control" onChange="getValueRiesgo();">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<option value="1"><%= etiqueta.SI %></option>
									<option value="0"><%= etiqueta.NO %></option>
								</form:select>
							</div>
						</div>
						
						<!--  combo tipo proveedor -->
						<div class="form-group">
							<div class="control-label col-xs-3">
								<label for="idUsuario"><%= etiqueta.TIPO_PROVEEDOR %></label>
							</div>
							<div class="col-xs-6">
								<form:select path="tipoProveedor.idTipoProveedor" cssClass="form-control" id="tipoProveedor">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.tipoProveedorList}" var="tplist">
										<option value="${tplist.idTipoProveedor}">${tplist.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>		
						
						<!--  combo locación -->
						<div class="form-group">
							<div class="control-label col-xs-3">
								<label for="idUsuario"><%= etiqueta.LOCACION %></label>
							</div>
							<div class="col-xs-6">
								<form:select path="locacion.idLocacion" cssClass="form-control" id="locacion">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.locacionList}" var="loclist">
										<option value="${loclist.idLocacion}">${loclist.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
						
						<!--  Permite anticipo múltiple -->
						<div class="form-group">
						<div class="control-label col-xs-3">
								<label for="permiteAnticipoMultipleB"><%= etiqueta.PERMITE_ANTICIPO_MULTIPLE %></label>
							</div>
							<div class="checkbox col-xs-6">
								<form:checkbox id="permiteAnticipoMultipleB" path="permiteAnticipoMultipleB" value="" />
							</div>
						</div>
						

					</div>
					<div class="modal-footer">
						<input type="submit" id="saveProveedor" class="btn btn-primary"
							value="<%= etiqueta.GUARDAR %>" 
							onclick="return submitProveedorForm();"/>

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
	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/validaRFC.js"></script>

	<script>
	
	e_titulo = '<%= etiqueta.PROVEEDOR %>';
	
    function submitProveedorForm(){
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
		
		if($('#numeroProveedor').val() == ""){
			ban = true;
			$("#numeroProveedor").addClass("errorx");
		}else{
			$("#numeroProveedor").removeClass("errorx");
		}
		
		if($('#contacto').val() == ""){
			ban = true;
			$("#contacto").addClass("errorx");
		}else{
			$("#contacto").removeClass("errorx");
		}
		
		if($('#correoElectronico').val() == ""){
			ban = true;
			$("#correoElectronico").addClass("errorx");
		}else{
			$("#correoElectronico").removeClass("errorx");
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
		
		//proveedor riesgo
		if($("#proveedorRiesgo").val() == '-1'){
			ban = true;
			$("#proveedorRiesgo").addClass("errorx");
		}else{
			$("#proveedorRiesgo").removeClass("errorx");
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
	
	$( window ).load(function() {
		  $("#overlay-div").hide();
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
    	
        $('#dataTables-proveedor').DataTable({
                responsive: true
        });

        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#idProveedor").val(0);
        	$("#numeroProveedor").val(null);
        	$("#descripcion").val(null);
        	$("#contacto").val(null);
        	$("#correoElectronico").val(null);
        	$("#proveedorRiesgo").val(-1);
        	$("#rfc").val(null);
        	$("#tipoProveedor").val(-1);
        	$("#locacion").val(-1);
        	$("#idUsuario").val(-1);
        	$("#error-sign").hide();
        	$("#alerta").hide();
        });

        $("#tipoProveedor").change(function(){
        	$("#permiteAnticipoMultipleB").prop("checked", false);
        	$("#permiteAnticipoMultipleB").attr('disabled',$(this).val()!='2');
		});
    });
	

    function editar(id) {
        if(id > 0){
        console.log(id);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getProveedor",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                /* 	console.log(response.id +" -> "+response.numeroProveedor+" -> "
                		+response.descripcion +" -> "+response.contacto+" -> "
                		+response.correoElectronico +" -> "+response.proveedorRiesgo
                		+" -> "+response.activo); */
                	
                	console.log(response.numeroProveedor + "<---");

                	$("#idProveedor").val(response.id);
                	$("#numeroProveedor").val(response.numeroProveedor);
                	$("#descripcion").val(response.descripcion);
                	$("#contacto").val(response.contacto);
                	$("#correoElectronico").val(response.correoElectronico);
                	$("#proveedorRiesgo").val(response.proveedorRiesgo);
                	$("#rfc").val(response.rfc);
                	$("#idUsuario").val(response.idUsuario);
                	$("#idProveedor").val(response.id);
                	$("#tipoProveedor").val(response.tipoProveedor);
                	$("#locacion").val(response.locacion);
                	$("#permiteAnticipoMultipleB").prop("checked", false);
                	$("#permiteAnticipoMultipleB").attr('disabled',true);
                	if(response.tipoProveedor=='2'){
                		$("#permiteAnticipoMultipleB").attr('disabled',false);
                		if(response.permiteAnticipoMultiple == 1){
                      		$("#permiteAnticipoMultipleB").prop("checked", true);
                      	}
                   	}
                	 

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
        	$("#eliminarButton").prop("href","deleteProveedor?id="+id);
        	$("#myModalEliminar").modal('show');
        	
        }

        function getValueRiesgo() {
            var x = document.getElementById("proveedorRiesgo").value;
            $("input[name='isProvRiesgo']").val(x);
        }
           
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>