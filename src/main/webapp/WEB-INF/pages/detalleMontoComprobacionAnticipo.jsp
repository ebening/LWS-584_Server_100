<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
	<!-- Modal detalleReembolsos -->

	<div class="modal fade" id="modalComprobacionAnticipos" role="dialog">
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
									<th><%=etiqueta.CONCEPTO%></th>
									<th><%=etiqueta.FACTURA_FOLIO%></th>
									<th><%=etiqueta.SUBTOTAL%></th>
									<th><%=etiqueta.IVA%></th>
									<th><%=etiqueta.TOTAL%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleComprobacionAnticipos">

							</tbody>
							<!-- </table> -->
							<tr>
								<th style="border: 0;"><%=etiqueta.TOTALES%>:</th>
								<td style="border: none;">&nbsp;</td>
								<td style="border: none;">&nbsp;</td>
								<td style="border: none; text-align: right">$<span class="currencyFormat" id="subtotales"></span></td>
								<th style="border: none; text-align: right">$<span class="currencyFormat" id="iva"></span></th>
								<td style="border: none;">&nbsp;</td>
							</tr>
							<tr>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<td style="border: none"></td>
								<th style="border: none"><%=etiqueta.TOTAL%></th>
								<td style="border: none"></td>
								<th style="border: none; text-align: right">$<span class="currencyFormat"
									id="rTotalComprobacion"></span></th>
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
	
    function verDetalleMontoComprobacionAnticipos(id) {
        console.log("Comprobacion Anticipos \t\t"+id);
 		if(id > 0){
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoComprobacionAnticipo",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                 	$("#tablaDetalleComprobacionAnticipos").empty().append(result.tabla);
                 	$("#subtotales").text(result.subtotales);
                 	$("#iva").text(result.iva);
                 	//$("#totales").text(result.totales);
                 	$("#rTotalComprobacion").text(result.rTotal);
                 	// mostrar modal
                 	$('#modalComprobacionAnticipos').modal('show'); 
                 	$('.currencyFormat').number( true, 2 );
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
	</script>