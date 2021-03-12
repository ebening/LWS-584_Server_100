<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
	<!-- Modal detalleEstatus -->
	<div class="modal fade" id="modalEstatus" role="dialog">
		<div style="width: 759px;" class="modal-dialog">

			<!-- Modal content-->
			<form:form id="detalleConceptoForm" cssClass="form-horizontal"
				modelAttribute="solicitud" method="post" action="">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"><%=etiqueta.DETALLE_ESTATUS%></h4>
					</div>

					<div class="panel-body" style="padding:15px;">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover"
								id="tablaModal">
								<thead>
									<tr>
										<th><%=etiqueta.NIVEL%></th>
										<th><%=etiqueta.NOMBRE%></th>
										<th><%=etiqueta.PUESTO%></th>
										<th><%=etiqueta.ESTATUS%></th>
										<th><%=etiqueta.FECHA_AUTORIZACION_RECHAZO%></th>
										<th><%=etiqueta.COMENTARIOS_RECHAZO%></th>
										
									</tr>
								</thead>
								<tbody id="tablaDetalle">

								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
