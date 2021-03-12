<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
	<!-- Modal detalleReembolsos -->

	<div class="modal fade" id="modalAnticiposComprobacionGastosDeViaje" role="dialog">
		<div style="width: 805px;" class="modal-dialog">
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
									<th></th>
									<th><%=etiqueta.COL_FECHA_FACTURA%> / <%=etiqueta.COMPROBANTE%></th>
									<th style="min-width:85px;"><%=etiqueta.FECHA_GASTO%></th>
											<th><%=etiqueta.COMERCIO%></th>
											<th><%=etiqueta.CONCEPTO%></th>
											<th><%=etiqueta.CIUDAD%></th>
											<th><%=etiqueta.SUBTOTAL%></th>
											<th><%=etiqueta.IVA%></th>
											<th><%=etiqueta.IEPS%></th>
											<th><%=etiqueta.OTROS_IMPUESTOS%></th>
											<th><%=etiqueta.TOTAL%></th>
											<th><%=etiqueta.LOCACION%></th>
											<th><%=etiqueta.CUENTA_CONTABLE%></th>
											<th><%=etiqueta.NUMERO_DE_PERSONAS%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleAnticiposComprobacionGastoDeViaje">

							</tbody>
							<!-- </table> -->
							<tr>
								<td  colspan="7" style="border: 0;"><%=etiqueta.TOTALES%>:</td>
								<td style="border: none; text-align: right">$<span class="currencyFormat" id="totalSubtotal"></span></td>
								<td style="border: none; text-align: right">$<span class="currencyFormat" id="totalIva"></span></td>
								<td style="border: none; text-align: right">$<span class="currencyFormat" id="totalIeps"></span></td>
								<td style="border: none; text-align: right">$<span class="currencyFormat" id="totalOtrosImpuestos"></span></td>
								<td style="border: none;">&nbsp;</td>
							</tr>
							<tr>
								<th colspan="11" style="border: none"><%=etiqueta.TOTAL_REEMBOLSO%></th>
								<th style="border: none; text-align: right">$<span class="currencyFormat"
									id="rTotalComprobacionGastoViaje"></span></th>
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
	
    function verDetalleMontoComprobacionAnticipoGastodeViaje(id) {
        console.log("Gasto de viaje \t\t"+id);
 		if(id > 0){
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoAnticipoComprobacionGastoDeViaje",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                 	$("#tablaDetalleAnticiposComprobacionGastoDeViaje").empty().append(result.tabla);
                 	$("#totalSubtotal").text(result.subtotales);
                 	$("#totalIva").text(result.iva);
                 	$("#totalIeps").text(result.totalIeps);
                 	$("#totalOtrosImpuestos").text(result.totalOtrosImpuestos);
                 	$("#rTotalComprobacionGastoViaje").text(result.rTotal);
                 	// mostrar modal
                 	$('#modalAnticiposComprobacionGastosDeViaje').modal('show'); 
                 	$('.currencyFormat').number( true, 2 );
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
	</script>