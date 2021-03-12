	
	function viewPDF(url){
		$("#display_pdf").html("<object id=\"pdf_object\" data=\""+url+"\" type=\"application/pdf\" width=\"100%\"><a id=\"pdf_link\" href=\""+url+"\"></a></object>");
		$("#display_pdf").show();
	}
	
	function viewIMG(url){
		$("#display_pdf").html("<img src=\""+url+"\" />");
		$("#display_pdf").show();
	}
	
	$(document).ready(function () {
		
	    $('#modal').on('hidden.bs.modal', function () {
	    	$("#display_pdf").html(null);    
        });

	    (function ($) {

	        $('#filter_pdf').keyup(function () {

	            var rex = new RegExp($(this).val(), 'i');
	            $('.searchable_pdf tr').hide();
	            $('.searchable_pdf tr').filter(function () {
	                return rex.test($(this).text());
	            }).show();

	        });
	        
	        $('#filter_xml').keyup(function () {

	            var rex = new RegExp($(this).val(), 'i');
	            $('.searchable_xml tr').hide();
	            $('.searchable_xml tr').filter(function () {
	                return rex.test($(this).text());
	            }).show();

	        });
	        
	        
	        $('#filter_comprobante').keyup(function () {

	            var rex = new RegExp($(this).val(), 'i');
	            $('.searchable_comprobante tr').hide();
	            $('.searchable_comprobante tr').filter(function () {
	                return rex.test($(this).text());
	            }).show();

	        });
	        
	        
	        $('#filter_soporte').keyup(function () {

	            var rex = new RegExp($(this).val(), 'i');
	            $('.searchable_soporte tr').hide();
	            $('.searchable_soporte tr').filter(function () {
	                return rex.test($(this).text());
	            }).show();

	        });
	        
	        
	        

	    }(jQuery));

	});
	
	$(document).ready(function() {
        $('#tabla').DataTable({
                responsive: true
        });
        
        $('#tablaPDF').DataTable({
            responsive: true
    });
        
    });
	function verDetalle(id,idTipoSolicitud) {
		
		if(idTipoSolicitud > 2){
			$("#documentos_comprobante").show();
		}else{
			$("#documentos_comprobante").hide();
		}

        
		if(id > 0){
        	console.log(id);
        
            $.ajax({
                type: "GET",
                cache: false,
                url: rutaserver+"/getDetalleDocumentos",
                async: true,
                data: "idSolicitudActual=" + id,
                success: function(result) {
                	$("#solicitud").text(result.solicitud);   					
                	$(".searchable_pdf").empty().append(result.tablaPDF);
                	$(".searchable_xml").empty().append(result.tablaXML);
               		$(".searchable_comprobante").empty().append(result.tablaComprobante);                	
                	$(".searchable_soporte").empty().append(result.tablaSoporte);       

                	// mostrar modal
                	$('#modal').modal('show'); 
                },
                error: function(e) {
                    console.log('Error: ' + e);
                },
            }); 
            
        }//if
	}