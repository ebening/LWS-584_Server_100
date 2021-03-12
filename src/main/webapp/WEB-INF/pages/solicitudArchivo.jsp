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
	
	.modal .modal-body {
    max-height: 420px;
    overflow-y: auto;
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
						<h1 class="page-header"><%=etiqueta.DOCUMENTO_SOPORTE%></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<!--  Estructura de contenido. -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary" data-toggle="modal"
								data-target="#anexarDoc"><%=etiqueta.DOCUMENTO_SOPORTE%></button>
						</div>

					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<!-- Ventana modal anexar documento soporte -->
			<div class="modal fade" id="anexarDoc" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<form:form id="anexarDocumentoForm" enctype="multipart/form-data"
						cssClass="form-horizontal" modelAttribute="solicitudArchivo"
						method="post" action="saveSolicitudDocument">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title"><%=etiqueta.DOCUMENTO_SOPORTE%>
									<img id="loader-lowes" style="display: none;"
										src="${pageContext.request.contextPath}/resources/images/loader.gif" />
								</h4>
							</div>
								
							<div class="modal-body">

								<div id="errorMsg" style="display: none;"
									class="alert alert-warning">
									<strong>
										<span id="error-head"><%=errorHead%></span>
									</strong>
									<span id="error-body"><%=errorBody%></span>
								</div>

								<div class="form-group">
									<div class="control-label col-xs-4">
										<label><%=etiqueta.ANEXAR_DOCUMENTO_SOPORTE%></label><br/>
									</div>
									<div class="col-xs-7">
										<form method="post" enctype="multipart/form-data"
											action="singleSave">
											
											<input id="file" type="file" name="file"> <br/> 
											
											<button onclick="anexarDoc()" style="margin-left: 10px; float: right;" type="button"
											class="btn btn-default"><%=etiqueta.ADJUNTAR_ARCHIVO%></button>
												 
												<img id="loaderXML" style="display: none;"
												src="${pageContext.request.contextPath}/resources/images/loader.gif" />
										</form>
									</div>
								</div>

								<div class="panel-body">
									<div class="dataTable_wrapper">
										<table class="table table-striped table-bordered table-hover"
											id="tablaAid">
											<thead>
												<tr>
													<th><%=etiqueta.DOCUMENTO%></th>
													<th width="20%"><%=etiqueta.ELIMINAR%></th>
												</tr>
											</thead>
											<tbody id="tablaDoc">
												<c:forEach items="${solicitudArchivoList}" var="salist">
													<tr class="odd gradeX">
														<td><c:out value="${salist.archivo}" /></td>
														<td class="center">
															<button onclick="eliminar(${salist.idSolicitudArchivo})"
																style="height: 22px;" type="button"
																class="btn btn-xs btn-danger">
																<span class="glyphicon glyphicon-trash"></span>
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
		</div>
		<!-- /#page-wrapper -->
	</div>

	<jsp:include page="template/includes.jsp" />
	<script>
	var url_server = null;
	var ATENCION =  '<%= etiqueta.ATENCION %>';
	var ARCHIVO_ANEXADO =  '<%= etiqueta.ARCHIVO_ANEXADO %>';
	var ERROR = '<%= etiqueta.ERROR %>';
	var EXTENSION_INVALIDA = '<%= etiqueta.EXTENSION_INVALIDA %>';
	var ARCHIVO_NO_ANEXADO = '<%= etiqueta.ARCHIVO_NO_ANEXADO %>';
	var ARCHIVO_VACIO = '<%= etiqueta.ARCHIVO_VACIO %>';
	var ARCHIVO_NO_SELECCIONADO = '<%=etiqueta.ARCHIVO_NO_SELECCIONADO%>';
	var ARCHIVO_ELIMINADO = '<%= etiqueta.ARCHIVO_ELIMINADO %>';
	var ARCHIVO_NO_ELIMINADO = '<%= etiqueta.ARCHIVO_NO_ELIMINADO %>';
	var ERROR_DEPENDENCIAS = '<%= etiqueta.ERROR_DEPENDENCIAS %>';
	var ERROR_DELETE = '<%= etiqueta.ERROR_DELETE %>';
	var TAMANO_NO_PERMITIDO = '<%=etiqueta.TAMANO_NO_PERMITIDO%>';
	
	
	  $(document).ready(function(){
		  url_server = '${pageContext.request.contextPath}';
	  });
	  
	  
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	
	

	<!-- Scripts especificos ....  -->



</body>
</html>
