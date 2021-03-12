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
						<h1 class="page-header"><%= etiqueta.DETALLE_CONCEPTO %></h1>
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
           
          <!--  Estructura de contenido.-->
<!--           <iframe width="600" height="550" src="solicitudArchivo"  ></iframe> -->
            <div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary" data-toggle="modal"
								data-target="#modal"><%= etiqueta.DETALLE_CONCEPTO %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tabla">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.CONCEPTO_DEL_GASTO %></th>
											<th width="10%"><%= etiqueta.VER_DETALLE %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${solicitudList}" var="list">
											<tr class="odd gradeX">
												<td><c:out value="${list.idSolicitud}" /></td>
												<td><c:out value="${list.conceptoGasto}" /></td>
												<td class="center"> 
													<button onclick="verDetalle(${list.idSolicitud})" style="height: 22px;" type="button" class="btn btn-xs btn-warning">
	                                                   <span class="glyphicon glyphicon-eye-open"></span>
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
           <!-- Ventana Modal -->              
				  <div class="modal fade" id="modal" role="dialog">
					<div class="modal-dialog">
				    
				      <!-- Modal content-->
						<form:form id="detalleConceptoForm" cssClass="form-horizontal" modelAttribute="solicitud" method="post" action="">
					     	<div class="modal-content">
					        	<div class="modal-header">
					          		<button type="button" class="close" data-dismiss="modal">&times;</button>
					          		<h4 class="modal-title"><%= etiqueta.DETALLE_CONCEPTO %></h4>
					        	</div>
					        	
					        	<div class="modal-body">
						        	<div  id ="error-sign"  style="display:none;" class="alert alert-warning">
						            	<strong><%= etiqueta.ATENCION %></strong> <%= etiqueta.COMPLETE %>
						        	</div>
					        
						            <div class="form-group">
						            	<div class="control-label col-xs-3"> 
						                	<form:label path="conceptoGasto" ><%= etiqueta.CONCEPTO_DEL_GASTO %></form:label> </div>
										<div class="col-xs-6">
											<form:hidden id="idSolicitud" path="idSolicitud" value=""/>
						                	<form:textarea id="conceptoGasto" path="conceptoGasto" style="margin-bottom:10px;" 
						                	value="${solicitud.conceptoGasto}" cssClass="form-control" rows="5" />
										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>      
    
  <jsp:include page="template/includes.jsp" />
  
  <!-- Scripts especificos ....  -->
  
  <!-- DataTables JavaScript -->
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    	<script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<script>
	
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
        
    });
	
	function verDetalle(id) {
        
		if(id > 0){
        	console.log(id);
        	loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getPrueba",
                async: true,
                data: "intxnId=" + id,
                success: function(response) {
                	loading(false);
                	console.log(response);
                	$("#conceptoGasto").val(response.conceptoGasto);
                	$("#idSolicitud").val(response.idSolicitud);
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
	</script>

</body>
</html>
