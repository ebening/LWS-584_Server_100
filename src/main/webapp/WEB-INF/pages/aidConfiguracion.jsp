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
						<h1 class="page-header"><%= etiqueta.AID_CONFIGURACION %></h1>
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
            
           <!-- Estructura de contenido. AID Configuracion -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float:right;"  type="button" 
							class="boton_crear btn btn-primary" data-toggle="modal" 
							data-target="#agregarAidConfiguracion"><%= etiqueta.AGREGAR_AID_CONFIGURACION %></button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="tablaAidConfiguracion">
									<thead>
										<tr>
											<th><%= etiqueta.ID_AID_CONFIGURACION %></th>
                                            <th><%= etiqueta.AID %></th>
                                            <th><%= etiqueta.CATEGORIA_MAYOR %></th>
                                            <th><%= etiqueta.CATEGORIA_MENOR %></th>
                                            <th><%= etiqueta.LIBRO_CONTABLE %></th>
                                            <th width="10%"><%= etiqueta.ACCIONES %></th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${modelo.aidConfiguracionList}" var="aclist">
	                                        <tr class="odd gradeX">
	                                            <td><c:out value="${aclist.idAidConfiguracion}"/></td>
	                                            <td><c:out value="${aclist.aid.descripcion}"/></td>
	                                            <td><c:out value="${aclist.categoriaMayor.descripcion}"/></td>
	                                            <td><c:out value="${aclist.categoriaMenor.descripcion}"/></td>
	                                            <td><c:out value="${aclist.compania.descripcion}"/></td>
	                                            <td width="10%" class="center">
		                                             <button
		                                             	title="<%= etiqueta.ELIMINAR %>" 
		                                             	onclick="eliminar(${aclist.idAidConfiguracion})" 
		                                             	style="height: 22px;" type="button" 
		                                             	class="btn btn-xs btn-danger">
	                                                   <span class="glyphicon glyphicon-trash"></span>
	                                                 </button>
	                                                 
	                                                  <button
	                                                  	title="<%= etiqueta.EDITAR %>" 
	                                                  	onclick="editar(${aclist.idAidConfiguracion})" 
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
    
    
    
    <!-- Ventana modal agregar aid configuracion -->
    <div class="modal fade" id="agregarAidConfiguracion" role="dialog">
    	<div class="modal-dialog">
    
      <!-- Modal content-->
     <form:form id="aidConfiguracionForm" cssClass="form-horizontal" modelAttribute="aidConfiguracion" method="post" action="saveAidConfiguracion">
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


				    <!--  combo AID -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="aid"><%= etiqueta.AID %></label>
							</div>
							<div class="col-xs-7">
								<form:hidden id="idAidConfiguracion" path="idAidConfiguracion" value="" />
								<form:select path="idAid" cssClass="form-control" id="aid">
										<option value="-1"><%= etiqueta.SELECCIONE %></option>
										<c:forEach items="${modelo.aidList}" var="alist">
											<option value="${alist.idAid}">${alist.descripcion}</option>
										</c:forEach>
								</form:select>
							</div>
						</div>
						
							<!--  combo Categoría Mayor -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="catMayor_list"><%= etiqueta.CATEGORIA_MAYOR %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idCategoriaMayor" cssClass="form-control" id="catMayor_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.categoriaMayorList}" var="cmlist">
										<option value="${cmlist.idCategoriaMayor}">${cmlist.descripcion}</option>
									</c:forEach>
								</form:select>	
							</div>
						</div>

						
						<!--  combo Categoria  menor -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="categoriaMenor"><%= etiqueta.CATEGORIA_MENOR %></label>
							</div>
							<div class="col-xs-7">
							<form:select path="idCategoriaMenor" cssClass="form-control" id="idCategoriaMenor">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.categoriaMenorList}" var="cmlist">
										<option value="${cmlist.idCategoriaMenor}">${cmlist.descripcion}</option>
									</c:forEach>
							</form:select>
							</div>
						</div>
						
						<!--  combo compañias -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="compania"><%= etiqueta.COMPANIA %></label>
							</div>
							<div class="col-xs-7">
							<form:select path="idCompania" cssClass="form-control" id="compania">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.companiaList}" var="clist">
										<option value="${clist.idcompania}">${clist.descripcion}</option>
									</c:forEach>
							</form:select>
							</div>
						</div>
						
				

					</div>
        <div class="modal-footer">
          <input onclick="return submitAidConfiguracionForm();"  type="submit" id="saveAidConfiguracion" class="btn btn-primary" value="<%= etiqueta.GUARDAR %>" />
			<button type="button" class="btn btn-default" data-dismiss="modal">
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
    
    <script>
    
    e_titulo = '<%= etiqueta.AID_CONFIGURACION_LBL %>';
    
	$(document).ready(function() {
		
		console.log("<%=errorHead%>");
    	
    	if("<%=errorHead%>" != "null"){
	    	if(true){
	    		$("#error-alert").fadeTo(2000, 500).slideUp(500, function(){
	    		    $("#error-alert").alert('close');
	    		    
	    		});
	    	}
    	}
    	
    	$('#agregarAidConfiguracion').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#idAidConfiguracion").val(0);
        	$("#aid").val(-1);
        	$("#catMayor_list").val(-1);
        	$("#idCategoriaMenor").val(-1);
        	$("#compania").val(-1);
        	$("#error-sign").hide();

        });
    	
	});
	
	$('#tablaAidConfiguracion').DataTable({
        responsive: true
});
    
    
    
    
    function editar(id){
  	  if(id > 0){
  			loading(true);
  		    $.ajax({
                  type: "GET",
                  cache: false,
                  url: "${pageContext.request.contextPath}/getAidConfiguracionEdit",
                  async: true,
                  data: "id=" + id,
                  success: function(response) {
                	loading(false);
                  	console.log(response);
                  	$("#idAidConfiguracion").val(response.idAidConfiguracion);
                  	$("#compania").val(response.idCompania);
                  	$("#aid").val(response.idAid);
                  	$("#catMayor_list").val(response.idCategoriaMayor);
                  	$("#idCategoriaMenor").val(response.idCategoriaMenor);
                  	$('#agregarAidConfiguracion').modal('show'); 
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
	  	$("#eliminarButton").prop("href","deleteAidConfiguracion?idAidConfiguracion="+id);
	  	$("#myModalEliminar").modal('show');
     }
  } 
    
    
    
    function submitAidConfiguracionForm(){
    	
    	
        var ban = false;
		var mensaje = null;
    	
    	var ban = false;

		//aid
		if($("#aid").val() == '-1'){
			ban = true;
			$("#aid").addClass("errorx");
		}else{
			$("#aid").removeClass("errorx");
		}
    	
    	if($("#catMayor_list").val() == '-1'){
			ban = true;
			$("#catMayor_list").addClass("errorx");
		}else{
			$("#catMayor_list").removeClass("errorx");
		}
    	
    	if($("#idCategoriaMenor").val() == '-1'){
			ban = true;
			$("#idCategoriaMenor").addClass("errorx");
		}else{
			$("#idCategoriaMenor").removeClass("errorx");
		}
    	
    	if($("#compania").val() == '-1'){
			ban = true;
			$("#compania").addClass("errorx");
		}else{
			$("#compania").removeClass("errorx");
		}
    	
    	if(ban){
    		$("#error-sign").show();
    		return false;
    	}else{
    		return true;
    	}
  }
    
    </script>

</body>
</html>
