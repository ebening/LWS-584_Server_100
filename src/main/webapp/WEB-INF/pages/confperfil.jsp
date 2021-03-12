<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

.check{

}




.check-back{
  background-color:#fff !important;
}

</style>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jtree/src/css/jquery.tree.css" />

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
				<strong> <span id="error-head"> <%=errorHead%>
				</span>
				</strong> <span id="error-body"> <%=errorBody%>
				</span>
			</div>


			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="margin-right: 5px; float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#myModal"><%= etiqueta.AGREGAR_PERFIL %></button>

							<button id="guardarConf" disabled="disabled"
								style="margin-right: 5px; float: right;" type="button"
								class="btn btn-primary"><%= etiqueta.GUARDAR %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover " 
									id="tabla-conf">
									<thead>
										<tr>
											<th><%= etiqueta.ID_PERFIL %></th>
											<th><%= etiqueta.NOMBRE_PERFIL %></th>
											<th><%= etiqueta.DESCRIPCION %></th>
											<th><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${perfilList}" var="tlist">
											<tr id="${tlist.idPerfil}" class="odd gradeX">
												<td><c:out value="${tlist.idPerfil}" /></td>
												<td><c:out value="${tlist.descripcion}" /></td>
												<td><c:out value="${tlist.comentarios}" /></td>
												<td width="10%" class="center">
													<button
														title="<%= etiqueta.ELIMINAR %>" 
														onclick="eliminar(${tlist.idPerfil})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>

													<button
														title="<%= etiqueta.CONFIGURAR %>" 
														id="configButton" 
														style="height: 22px;" type="button"
														class="btn btn-xs btn-info">
														<span class="glyphicon glyphicon-cog"></span>
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

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<span><%=etiqueta.CONFIGURAR_PERFIL%></span> <span id="lbl_configurar_perfil"></span>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="dataTable_wrapper">
							
							


								<div id="arbol-lowes">
									<div id="tree-content">
									   <ul>
									   
									     <li><input class="check" id="3" type="checkbox"><span><%= etiqueta.NUEVA_SOLICITUD_PAGO %></span>
												<ul>
													<li><input class="check"  id="8" type="checkbox"><span><%= etiqueta.FACTURAS %></span>
														<ul>
															<li><input class="check"  id="9" type="checkbox"><span><%= etiqueta.CON_XML %></span>
															<li><input class="check"  id="10" type="checkbox"><span><%= etiqueta.SIN_XML %></span>
														</ul>
													</li>
													<li><input id="13" class="check"  type="checkbox"><span><%= etiqueta.REEMBOLSO %></span></li>
														
												</ul>
										</li>		
												
												
						
									   
									   </ul>
									   
									   <ul>
									     <li><input  class="check" id="11"  type="checkbox"><span><%= etiqueta.CONFIGURACION %></span>
												<ul>
													<li><input class="check" id="12" type="checkbox"><span><%= etiqueta.ADMINISTRACION %></span>
														<ul>
															<li><input class="check"  id="14" type="checkbox"><span><%= etiqueta.TIPOS_SOLICITUD %></span></li>
															<li><input class="check"  id="15" type="checkbox"><span><%= etiqueta.PROVEEDORES %></span></li>
															<li><input class="check"  id="16" type="checkbox"><span><%= etiqueta.ESTATUS %></span></li>
															<li><input class="check"  id="17" type="checkbox"><span><%= etiqueta.COMPANIA %></span></li>
															<li><input class="check"  id="18" type="checkbox"><span><%= etiqueta.LOCACION %></span></li>
		
														</ul>
													</li>
							
													<li><input class="check" id="19" type="checkbox"><span><%= etiqueta.SEGURIDAD %></span>
												        <ul>
															<li><input class="check"  id="26" type="checkbox"><span><%= etiqueta.USUARIOS %></span></li>
															<li><input class="check"  id="27" type="checkbox"><span><%= etiqueta.PERFILES %></span></li>
														</ul>
													</li>
												</ul>
										  </li>
									   </ul>
									</div>
								</div>

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
				modelAttribute="perfil" method="post" action="savePerfilConf">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %></strong>
							<%= etiqueta.INTRODUCIR_INFORMACION %>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="descripcion"><%= etiqueta.NOMBRE_PERFIL %></form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden id="idPerfil" path="idPerfil" value="" />
								<form:hidden id="flagButton" path="" value="false" />
								<form:hidden id="idPerfilClick" path="" value="false" />
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
							value="<%= etiqueta.GUARDAR %>"
							onclick="return submitPerfilForm();" />

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
	<script	src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/jtree/src/js/jquery.tree.js"></script>


	<script>
	
	e_titulo = '<%= etiqueta.PERFIL %>';
    
    $(document).ready(function(){
    	
    	console.log("<%= errorHead %>");
    	
    	if("<%= errorHead %>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
    	createTreeHTML();
   
     	$("#tabla-conf").find('tr').click( function(){
    		  $("#idPerfilClick").val($(this).closest('tr').attr('id'));
    		  var valb = $("#flagButton").val();
    		  $("tr").removeClass("remarcado");
    		  $(this).addClass("remarcado");
    		  
    		  //---
    		  getTree($(this).closest('tr').attr('id'));
    		  
    		  if(valb == "false"){
        		  $("#arbol-lowes").removeClass("check-back");
	    	      $(".check").prop("disabled", true);
	    	      $("#guardarConf").prop("disabled", true);
    		  }
    		  $("#flagButton").val(false);
    	});
     	
     	//config
     	$("#tabla-conf").find('tr #configButton').click( function(){
	  		var $row = $(this).closest('tr'); 
            var perfilName = $row.find('td:eq(1)').html();
	  		  console.log("click tr button" + perfilName);
	  		  $("#lbl_configurar_perfil").text(perfilName);
    		  $("#flagButton").val(true);
    		  $("#arbol-lowes").addClass("check-back");
	  	      $(".check").prop("disabled", false);
	  	      $("#guardarConf").prop("disabled", false);
  	   });
     	
     	
     	//guardar
     	$("#guardarConf").click(function(){
     		var selected = $('#arbol-lowes').tree('selected');
     		var selectedArr = [];
     		$('.check').each(function() {
     			if($(this).prop('checked')){
     				selectedArr.push(this.id);
     			}
     		});
     		
     			var stringVals = selectedArr.join(",");
     			var idPerfil = $("#idPerfilClick").val();
            	loading(true);
            	
     	 	    $.ajax({
                    type: "POST",
                    cache: false,
                    url: "${pageContext.request.contextPath}/saveConfig",
                    async: true,
                    data: "ids=" + stringVals + "&idPerfil=" + idPerfil,
                    success: function(response) {
                    	loading(false);
                    	console.log(response);
                    	$("#arbol-lowes").removeClass("check-back");
        	    	    $(".check").prop("disabled", true);
        	    	    $("#guardarConf").prop("disabled", true);
                   
                    },
                    error: function(e) {
                    	loading(false);
                        console.log('Error: ' + e);
                    },
                }); 
     	});
     	
    	
    });
    

	 $('#dataTables-example').on('click', 'tbody tr', function(event) {
         $(this).addClass('remarcado').siblings().removeClass('remarcado');
     });
	
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
    	
        $('#tabla-conf').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#descripcion").val(null);
        	$("#comentarios").val(null);
        	$("#idPerfil").val(null);
        });
        
    });
    
     
        function editar(idPerfil) {
        
        	
        if(idPerfil > 0){
        	
        console.log(idPerfil);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getPerfilConf",
                async: true,
                data: "intxnId=" + idPerfil,
                success: function(response) {
                	loading(false);
                	console.log(response.descripcion+" -> "+response.idPerfil);
                	$("#descripcion").val(response.descripcion);
                	$("#comentarios").val(response.comentarios);
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
        
        
        function getTree(idPerfil){
        	loading(true);
        	  $.ajax({
                  type: "GET",
                  cache: false,
                  url: "${pageContext.request.contextPath}/getTreeConfPerfil",
                  async: true,
                  data: "idPerfil=" + idPerfil,
                  success: function(response) {
                  	loading(false);
                  	if(response.ids){
                  		
                  		var array = response.ids.split('/');
                  		console.log(array);
                        $('#arbol-lowes').tree('uncheckAll');

                  		
                  		$('.check').each(function() {
              				//console.log(this.id);
              				if ($.inArray(this.id, array) != -1)
              				{
              					console.log("#"+this.id+"_");
              					$("#"+this.id).prop("checked",true);
              					
              				}else{
              					console.log(this.id);
              				}
                  		
                 		});

                  	}else{
                       $('#arbol-lowes').tree('uncheckAll');
                  	}
                  },
                  error: function(e) {
                  	  loading(false);
                      console.log('Error: ' + e);
                  },
              }); 
        }
        
        
        function createTreeHTML(){
          loading(true);
      	  $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/createTree",
                success: function(data) {
                loading(false);
                  if(data){
                	  $("#tree-content").html(data);
                	  $(".check").prop("disabled", true);
                  	  $('#arbol-lowes').tree(
                         {
                          dnd : false
                         }
                  	   );
                  }
                },
                error: function(e) {
                	loading(false);
                    console.log('Error: ' + e);
                },
            }); 
      }
        
        
        
        function eliminar(idPerfil){
        	
        	console.log(idPerfil);
        	$("#eliminarButton").prop("href","deletePerfilConf?idPerfil="+idPerfil);
        	$("#myModalEliminar").modal('show');
        	
        }
	</script>
</body>
</html>