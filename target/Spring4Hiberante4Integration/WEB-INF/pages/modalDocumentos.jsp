	<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
	
		<!-- MODAL CONSULTA DE DOCUMENTOS -->
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
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
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
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
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
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_soporte" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_soporte">

											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div id="documentos_comprobante" class="panel panel-yellow">
								<div style="overflow: auto;" class="panel-heading">

									<label><%=etiqueta.ARCHIVOS_COMPROBANTE%> </label> <input
										id="filter_comprobante" type="text"
										class="form-control fix_input_filter"
										placeholder="Busca Comprobantes">
								</div>
								<div class="panel-body">
									<div class="table-responsive table_responsive_fix">
										<table id="tabla_comprobante" class="table table-hover">
											<thead>
												<tr>
													<th><%=etiqueta.NUM%></th>
													<th><%=etiqueta.ARCHIVO%></th>
												</tr>
											</thead>
											<tbody class="searchable_comprobante">

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div style="min-height: 715px;"
							class="col-xs-12 col-sm-6 col-md-8">
							<!-- 16:9 aspect ratio -->
							<div id="display_pdf" style="min-height: 715px; display: none;"
								class="embed-responsive embed-responsive-16by9"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
