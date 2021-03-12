<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%
	Etiquetas etiqueta = new Etiquetas("es");
%>
<%
	String errorHead = request.getParameter("errorHead");
%>
<%
	String errorBody = request.getParameter("errorBody");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<jsp:include page="template/head.jsp" />

<style>
.remarcado {
	background-color: #eee !important;
}

.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
	background-color: #eaeaea !important;
}

.check {
	
}

.check-back {
	background-color: #fff !important;
}

.modal .modal-body {
	/* max-height: 420px; */
	overflow-y: auto;
}

.fix_input_filter {
	float: right !important;
	width: 43% !important;
	height: 27px !important;
}

.table_responsive_fix {
	height: 146px;
	-ms-overflow-x: auto; /* IE8 */
	overflow-y: auto;
	overflow-x: hidden;
	white-space: nowrap;
}

#tabla_pdf {
	white-space: nowrap;
}

.downButton{
    height: 15px;
    margin: 1px;
    color: #000;
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
						<h1 class="page-header"><%=etiqueta.DETALLE_DOCUMENTOS%></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<!--  Estructura de contenido.-->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading"></div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tabla">
									<thead>
										<tr>
											<th><%=etiqueta.ID%></th>
											<th><%=etiqueta.CONCEPTO_DEL_GASTO%></th>
											<th width="10%"><%=etiqueta.VER_DETALLE%></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${solicitudList}" var="list">
											<tr class="odd gradeX">
												<td><c:out value="${list.idSolicitud}" /></td>
												<td><c:out value="${list.conceptoGasto}" /></td>
												<td class="center">
													<button onclick="verDetalle(${list.idSolicitud})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-warning">
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
		<div style="width: 1100px; min-height: 650px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_DOCUMENTOS%></h4>
				</div>

				<div class="panel-body">

					<div class="row show-grid">
						<div class="col-xs-6 col-md-4">
							<div class="panel panel-red">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_PDF%> </label> <input
										id="filter_pdf" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca PDF's">
								</div>
								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_pdf" class="table table-hover">
											<thead>
												<tr>
													<th>#</th>
													<th>Archivo</th>
												</tr>
											</thead>
											<tbody class="searchable_pdf">

											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="panel panel-green">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_XML%> </label> <input
										id="filter_xml" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca XML's">
								</div>

								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_xml" class="table table-hover">
											<thead>
												<tr>
													<th>#</th>
													<th>Archivo</th>
												</tr>
											</thead>
											<tbody class="searchable_xml">
											
											</tbody>
										</table>
									</div>
								</div>
							</div>
							
								<div class="panel panel-primary">
								<div style="overflow: auto;" class="panel-heading">
									<label><%=etiqueta.ARCHIVOS_SOPORTE%> </label> <input
										id="filter_soporte" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca Comprobantes">
								</div>
								<div class="panel-body">
									<div 
										class="table-responsive table_responsive_fix">
										<table id="tabla_soporte" class="table table-hover">
											<thead>
												<tr>
													<th>#</th>
													<th>Archivo</th>
												</tr>
											</thead>
											<tbody class="searchable_soporte">
										
											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="panel panel-yellow">
								<div style="overflow: auto;" class="panel-heading">

									<label><%=etiqueta.ARCHIVOS_COMPROBANTE%> </label> <input
										id="filter_comprobante" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca Comprobantes">
								</div>
								<div class="panel-body">
									<div 
										class="table-responsive table_responsive_fix">
										<table id="tabla_comprobante" class="table table-hover">
											<thead>
												<tr>
													<th>#</th>
													<th>Archivo</th>
												</tr>
											</thead>
											<tbody class="searchable_comprobante">
												
											</tbody>
										</table>
									</div>
								</div>
							</div>

						





						</div>
						<div style="min-height: 715px;" class="col-xs-12 col-sm-6 col-md-8">
						
							<!-- 16:9 aspect ratio -->
							<div id="display_pdf" style="min-height: 715px; display: none; " class="embed-responsive embed-responsive-16by9">
								
							</div>
							
							
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<jsp:include page="template/includes.jsp" />

	<!-- Scripts especificos ....  -->
	<!-- DataTables JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
		
		<script>
		   var rutaserver = '${pageContext.request.contextPath}';
		</script>
		
		<script	src="${pageContext.request.contextPath}/resources/js/detalleDocumentos.js"></script>

</body>
</html>