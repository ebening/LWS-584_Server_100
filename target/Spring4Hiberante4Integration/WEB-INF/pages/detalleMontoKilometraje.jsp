<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
	<!-- Modal detalleReembolsos -->

	<div class="modal fade" id="modalKilometrajes" role="dialog">
		<div style="width: 785px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_MONTO%></h4>
				</div>

				<div class="panel-body">

					<div class="form-group">
						<div class="col-xs-4">
							<label><%=etiqueta.DESGLOSE_SOLICITUD%> </label>
						</div>
					</div>
					<br />
					<div class="dataTable_wrapper scrollable">
						<table class="table table-striped table-bordered table-hover"
							id="tablaModal">
							<thead>
								<tr>
									<th><%=etiqueta.LINEA%></th>
									<th><%=etiqueta.FECHA%></th>
									<th><%=etiqueta.E_KM_MOTIVO%></th>
									<th><%=etiqueta.ORIGEN%></th>
									<th><%=etiqueta.DESTINO%></th>
									<th><%=etiqueta.E_KM_VIAJES%></th>
									<th><%=etiqueta.E_KM%></th>
									<th><%=etiqueta.COL_IMPORTE%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleKilometrajes">

							</tbody>
							<!-- </table> -->

							<br>
							<tr>
								<th style="border: 0;"></th>
								<td style="border: none;">&nbsp;</td>
								<td style="border: none;">&nbsp;</td>
								<td style="border: none;">&nbsp;</td>
								<td style="border: none;">&nbsp;</td>
								<th style="border: none"><%=etiqueta.TOTALES%></th>
								<th style="border: none;"><span id="kilometros"></span></th>
								<th style="border: 0; text-align: right">$<span class="currencyFormat" id="importeKilometraje"></span></th>
							</tr>
							<tr>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<th style="border: none"><%=etiqueta.IMPORTE_TOTAL%></th>
								<td style="border: none"></td>
								<th style="border: none; text-align: right">$<span class="currencyFormat"
									id="rTotalKilometrajes"></span></th>
							</tr>

						</table>
					</div>
					<!-- /.table-responsive -->
				</div>
			</div>
		</div>
	</div>
	
	<script>
	
	$(document).ready(function() {
        $('#tabla').DataTable({
                responsive: true
        });
        
    });
	
    function verDetalleMontoKilometrajes(id) {
        console.log("Kilometraje \t\t"+id);
 		if(id > 0){
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoKilometraje",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                 	$("#tablaDetalleKilometrajes").empty().append(result.tabla);
                 	$("#totalesViajes").text(result.totales);
                 	$("#kilometros").text(result.km);
                 	$("#importeKilometraje").text(result.importe);
                 	$("#rTotalKilometrajes").text(result.rTotal);
                 	$('.currencyFormat').number( true, 2 );
                 	// mostrar modal
                 	$('#modalKilometrajes').modal('show'); 
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
	</script>