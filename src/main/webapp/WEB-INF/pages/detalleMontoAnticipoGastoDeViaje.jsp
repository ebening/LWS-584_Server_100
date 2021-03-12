<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>
	<!-- Modal detalleReembolsos -->

	<div class="modal fade" id="modalAnticiposGastosDeViaje" role="dialog">
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
						<div align="right">
							<label id="motivoCotizacionLBL" style="display: none;"><%=etiqueta.VIAJE_MOTIVO_COTIZACION%>: </label> <label id="motivoCotizacion" style="font-weight: normal;"></label>
						</div>
					</div>
					<br />
					<div class="dataTable_wrapper scrollable">
						<table class="table table-striped table-bordered table-hover"
							id="tablaModal">
							<thead>
								<tr>
									<th width="30px"><%=etiqueta.LINEA%></th>
									<th><%=etiqueta.AEROLINEA_GASTO%></th>
									<th><%=etiqueta.COL_IMPORTE%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleAnticiposGastoDeViaje">

							</tbody>
							<tr>
								<td style="border: none"></td>
								<th style="border: none"><%=etiqueta.IMPORTE_TOTAL%></th>
								<th style="border: none; text-align: right">$<span class="currencyFormat"
									id="rTotalGastoDeViaje"></span></th>
							</tr>
							<!-- </table> -->

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
	
    function verDetalleMontoAnticipoGastodeViaje(id) {
        console.log("Gasto de viaje \t\t"+id);
 		if(id > 0){
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoAnticipoGastoDeViaje",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                 	$("#tablaDetalleAnticiposGastoDeViaje").empty().append(result.tabla);
                 	$("#motivoCotizacion").text(result.motivoCotizacion);
                 	if(result.motivoCotizacion != null){
                 		$("#motivoCotizacionLBL").show();
                 	}
                 	$("#rTotalGastoDeViaje").text(result.rTotal);
                 	// mostrar modal
                 	$('#modalAnticiposGastosDeViaje').modal('show'); 
                 	$('.currencyFormat').number( true, 2 );
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
	</script>