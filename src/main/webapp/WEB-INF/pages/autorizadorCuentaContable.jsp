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
						<h1 class="page-header"><%= etiqueta.AUTORIZADOR_CUENTA_CONTABLE %></h1>
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
            
           <!-- Estructura de contenido. -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float:right;"  type="button" 
							class="boton_crear btn btn-primary" data-toggle="modal" 
							data-target="#modal"><%= etiqueta.AGREGAR_AUTORIZADOR_CUENTA_CONTABLE %></button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" 
									id="tabla">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.USUARIO %></th>
                                            <th><%= etiqueta.CUENTA_CONTABLE %></th>
                                            <th><%= etiqueta.NIVEL_AUTORIZA %></th>
                                            <th width="10%"><%= etiqueta.ACCIONES %></th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${modelo.autorizadorCuentaContableList}" var="aclist">
	                                        <tr class="odd gradeX">
	                                            <td><c:out value="${aclist.idAutorizadorCuentaContable}"/></td>
	                                            <td><c:out value="${aclist.usuario.nombre} ${aclist.usuario.apellidoPaterno}"/></td>
	                                            <td><c:out value="${aclist.cuentaContable.numeroCuentaContable}"/></td>
	                                            <td><span class=""><c:out value="${aclist.nivelAutoriza.idNivelAutoriza}"/></span></td>	                                            
	                                            <td width="10%" class="center">
		                                             <button
		                                             	title="<%= etiqueta.ELIMINAR %>" 
		                                             	onclick="eliminar(${aclist.idAutorizadorCuentaContable})" 
		                                             	style="height: 22px;" type="button" 
		                                             	class="btn btn-xs btn-danger">
	                                                   <span class="glyphicon glyphicon-trash"></span>
	                                                 </button>
	                                                 
	                                                  <button
	                                                  	title="<%= etiqueta.EDITAR %>" 
	                                                  	onclick="editar(${aclist.idAutorizadorCuentaContable})" 
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
        <!-- /#page-wrapper -->
    </div>
    
    
    
    <!-- Ventana modal -->
    <div class="modal fade" id="modal" role="dialog">
    	<div class="modal-dialog">
    
      <!-- Modal content-->
     <form:form id="accForm" cssClass="form-horizontal" modelAttribute="autorizadorCuentaContable" 
     	method="post" action="saveAutorizadorCuentaContable">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"></h4>
           <img id="loader-lowes"  style="display:none;" src="${pageContext.request.contextPath}/resources/images/loader.gif" />
          </h4>
        </div>
        <div class="modal-body">
        
        <div  id ="error-sign"  style="display:none;" class="alert alert-warning">
			<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
        </div>
        
        <div  id ="error-sign-existe"  style="display:none;" class="alert alert-warning">
			<strong><%= etiqueta.ERROR %></strong> <%= etiqueta.EXISTE_NIVEL %> </div>

						<!--  combo Usuario -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="usuario_list"><%=etiqueta.USUARIO%></label>
							</div>
							<div class="col-xs-7">
								<form:hidden id="idAutorizadorCuentaContable" path="idAutorizadorCuentaContable" value="0" />
								<form:select 
									path="usuario.idUsuario"
									cssClass="form-control selectpicker" 
									data-live-search="true"
									id="usuario_list">
									<option value="-1"><%=etiqueta.SELECCIONE%></option>
									<c:forEach items="${modelo.usuariosList}" var="usuarioList">
										<option value="${usuarioList.idUsuario}">${usuarioList.nombre}
											${usuarioList.apellidoPaterno} ${usuarioList.apellidoMaterno}</option>
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
								<form:select path="cuentaContable.idCuentaContable" 
									cssClass="form-control selectpicker" 
									data-live-search="true"
									id="cuentaContable_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.cuentaContableList}" var="cuentaContList">
										<option value="${cuentaContList.idCuentaContable}">${cuentaContList.numeroCuentaContable} - ${cuentaContList.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
						
							<!--  combo Nivel Autoriza -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="idNivelAutoriza"><%= etiqueta.NIVEL_AUTORIZA %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="nivelAutoriza.idNivelAutoriza" cssClass="form-control" id="idNivelAutoriza">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.nivelAutorizaList}" var="nalist">
										<option value="${nalist.idNivelAutoriza}">${nalist.idNivelAutoriza}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>
					</div>
        <div class="modal-footer">
          <input type="submit" data-loading-text="Validando..." 
          	id="saveAutorizadorCuentaContable" class="btn btn-primary" value="<%= etiqueta.GUARDAR %>" />
			<button type="button" class="btn btn-default close-modal" data-dismiss="modal">
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
    <script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
    
    <script>
    
    e_titulo = '<%= etiqueta.AUTORIZADOR_CUENTA_CONTABLE_LBL %>';
    
    $(function(){
    	
    	  $("#accForm").submit(function(event){
    		//inicializadores  
    	/* 	$("#enviarUsuario").button('loading');
    		$("#loader-lowes").show(); */
		    $("#error-sign").hide();
			$("#error-pass").hide();
			
	        var ban = false;
			var mensaje = null;
			
			//Locación
			if($("#cuentaContable_list").val() == '-1'){
				ban = true;
				$("#cuentaContable_list").addClass("errorx");
			}else{
				$("#cuentaContable_list").removeClass("errorx");
			}
			
			// Nivel Autoriza
			if($("#idNivelAutoriza").val() == '-1'){
				ban = true;
				$("#idNivelAutoriza").addClass("errorx");
			}else{
				$("#idNivelAutoriza").removeClass("errorx");
			}
			
			// Usuario
			if($("#usuario_list").val() == '-1'){
				 ban = true;
				$("#usuario_list").addClass("errorx");
			}else{
				$("#usuario_list").removeClass("errorx");
			}
			
			$('.selectpicker').selectpicker('refresh')
			
			
			if(ban){
				$("#error-sign").show();
				console.log("false");
				loading(false);
				return false;
			}else{
				
				event.preventDefault();
				
              	var cuentac = $("#cuentaContable_list").val();
              	var nivel = $("#idNivelAutoriza").val();
              	var idRegistro = $("#idAutorizadorCuentaContable").val();
				
				loading(true);
    		    $.ajax({
                    type: "GET",
                    cache: false,
                    url: "${pageContext.request.contextPath}/existeNivelCuentaContable",
                    async: true,
		    		data : "idCuenta=" + cuentac + "&idNivel=" + nivel	+ "&idRegistro=" + idRegistro,
                    success: function(response) {
                    	loading(false);
                    	if(response.existe == 'false'){
                        	$("#accForm")[0].submit();
                        	loading(true);
                    	}else{
    					   $("#error-sign-existe").show();
                    	}
                    },
                    error: function(e) {
                  	  loading(false);
                        console.log('Error: ' + e);
                    },
                });
    		    
				return false;
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
    	
    	
    	$('#modal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#idAutorizadorCuentaContable").val(0);
        	$("#cuentaContable_list").val(-1);
        	$("#idNivelAutoriza").val(-1);
        	$("#usuario_list").val(-1);
			$("#error-sign").hide();
			$("#error-sign-existe").hide();

        	$('.selectpicker').selectpicker('refresh')
        });
    	
    	
    	$('.close-modal').click(function () {
    		$(".alert-warning").hide();
    	});
    	
    	$('.close').click(function () {
    		$(".alert-warning").hide();
    	});
    	
    	
    	
	});
	
	$('.currencyFormat').number( true, 2 );
	$('#tabla').DataTable({
        responsive: true
});
    
    
    
    
    function editar(id){
  	  if(id > 0){
  			loading(true);
  		    $.ajax({
                  type: "GET",
                  cache: false,
                  url: "${pageContext.request.contextPath}/getAutorizadorCuentaContableEdit",
                  async: true,
                  data: "id=" + id,
                  success: function(response) {
                	loading(false);
                  	console.log(response);
                  	$("#idAutorizadorCuentaContable").val(response.idAutorizadorCuentaContable);
                  	$("#cuentaContable_list").val(response.idCuentaContable);
                  	$("#idNivelAutoriza").val(response.idNivelAutoriza);
                  	$("#usuario_list").val(response.idUsuario);
                  	$('.selectpicker').selectpicker('refresh')
                  	$('#modal').modal('show'); 
                  },
                  error: function(e) {
                	  loading(false);
                      console.log('Error: ' + e);
                  },
              });  
  	  }
    }
    
    function eliminar(id){
		console.log(id);

	if(id > 0){  
		console.log(id);
	  	$("#eliminarButton").prop("href","deleteAutorizadorCuentaContable?idAutorizadorCuentaContable="+id);
	  	$("#myModalEliminar").modal('show');
     }
  }  
    
   

    </script>

</body>
</html>
