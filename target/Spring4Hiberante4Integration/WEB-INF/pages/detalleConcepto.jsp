<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%  String errorHead = request.getParameter("errorHead"); %>
<%  String errorBody = request.getParameter("errorBody"); %>

	<!-- Modal concepto gasto-->
	<div class="modal fade" id="modalConceptoGasto" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div style="width: 645px;" class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><%=etiqueta.DETALLE_CONCEPTO%></h4>
				</div>

				<div class="panel-body">
					<div id="error-sign" style="display: none;"
						class="alert alert-warning">
						<strong><%=etiqueta.ATENCION%></strong>
						<%=etiqueta.COMPLETE%>
					</div>

					<div class="form-group">
						<div class="control-label col-xs-3">
							<label><%=etiqueta.CONCEPTO_DEL_GASTO%>:</label>
						</div>
						<div class="col-xs-6">
							<textarea style="width: 391px; height: 169px;" id="conceptoGasto" style="margin-bottom: 10px;"
								disabled="true" class="form-control" rows="5" cols="1"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

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
	
	
    function verDetalleConcepto(idSolicitudResumen) {
        
 		if(idSolicitudResumen > 0){
         	console.log(idSolicitudResumen);
         	loading(true);
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/getSolicitud",
                 async: true,
                 data: "intxnId=" + idSolicitudResumen,
                 success: function(response) {
                	 loading(false);
                 	console.log(response);
                 	$("#conceptoGasto").val(response.conceptoGasto);
                 	$("#idSolicitud").val(response.idSolicitud);
                 	//abrir ventana modal
                 	$('#modalConceptoGasto').modal('show'); 
                 },
                 error: function(e) {
                	 loading(false);
                     console.log('Error: ' + e);
                 },
             }); 
             
         }//if
 	}
    
    
    function verDetalleConceptoFactura(idFactura) {
        
 		if(idFactura > 0){
         	console.log(idFactura);
         	loading(true);
             $.ajax({
                 type: "GET",
                 cache: false,
                 url: "${pageContext.request.contextPath}/conceptoFactura",
                 async: true,
                 data: "idFactura=" + idFactura,
                 success: function(response) {
                	 loading(false);
                 	console.log(response);
                 	$("#conceptoGasto").val(response.conceptoGasto);
                 	$("#idSolicitud").val(response.idSolicitud);
                 	//abrir ventana modal
                 	$('#modalConceptoGasto').modal('show'); 
                 },
                 error: function(e) {
                	 loading(false);
                     console.log('Error: ' + e);
                 },
             }); 
         }//if
 	}

	</script>