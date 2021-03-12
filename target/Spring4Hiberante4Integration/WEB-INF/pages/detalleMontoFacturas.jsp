<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>

<!-- modal montoFacturas -->
	<div class="modal fade" id="modalMontoFacturas" role="dialog">
		<div style="width: 959px;" class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_MONTO%></h4>
				</div>

				<div class="panel-body">

					 <div class="form-group">
						<div class="col-xs-2">
							<div class="checkbox" style="display: none;" id="checkbox">
								<input type="checkbox" checked disabled="disabled" id="track_asset" /><%=etiqueta.TRACK_ASSET%>
							</div>
						</div>
					</div> 

					<div class="form-group">
						<div id="librocontable" class="col-xs-8">
						
							<label><%=etiqueta.LIBRO_CONTABLE%>: </label> <span id="libro_contable"></span>
						</div>
					</div>

					<div class="form-group" id="parDiv">
						<label><%=etiqueta.PAR%>: </label> <span id="par"></span>
					</div>

					<br />
					<div class="dataTable_wrapper scrollable">
						<table class="table table-striped table-bordered table-hover"
							id="tablaModal">
							<thead>
								<tr>
									<th><%=etiqueta.LINEA%></th>
									<th><%=etiqueta.SUBTOTAL%></th>
									<th><%=etiqueta.LOCACION%></th>
									<th><%=etiqueta.CUENTA_CONTABLE%></th>
									<th><%=etiqueta.CONCEPTO%></th>
									<th><%=etiqueta.AID%></th>
									<th><%=etiqueta.CATEGORIA_MAYOR%></th>
									<th><%=etiqueta.CATEGORIA_MENOR%></th>
								</tr>
							</thead>
							<tbody id="tablaDetalleEstatus">

							</tbody>
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
	
	     function verDetalleMontoFacturas(id) {
         
 		if(id > 0){
         	console.log(id);
         
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getMontoFactura",
                 async: true,
                 data: "intxnId=" + id,
                 success: function(result) {
                	 
                	 console.log(result);
                	 
                 	$("#tablaDetalleEstatus").empty().append(result.tabla);
                 	$("#libro_contable").text(result.libro_contable);
                 	$("#par").text(result.par);
                 	$("#totales").text(result.totales);
                 	if(result.trackAsset== 1){
                 		$("#checkbox").show();
                 		$("#parDiv").show();
                 		$("#librocontable").show();
                 		$("#libro_contable").text(result.libroContable);
                 	}else{
                 		$("#checkbox").hide();
                 		$("#parDiv").hide();
                 		$("#librocontable").hide();
                 	}
                 	$('.currencyFormat').number( true, 2 );
                 	// mostrar modal
                 	$('#modalMontoFacturas').modal('show'); 
                 },
                 error: function(e) {
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
 	</script>