
	$(document).ready(function(){
		$('#anexarDoc').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#file-sol").val(null);
        	$("#errorMsg").hide();
        	$("#loaderXML").hide();
        });
		
	});
		
		  function anexarDoc(){
			  if(document.getElementById("file-sol").files.length != 0){ // vacío
				  
				 if(validarSize()){	// size	  
				  console.log("entro a anexarDoc()");
				  $("#errorMsg").hide();
				  $("#loaderXML").show();
				  
				  
//				  url = "/saveSolicitudDocument";
//				  if ($('.nuevo-nombre').css('display') != 'none') {
//					  alternativo = $('.nuevo-nombre').val();
//					  if (!alternativo.length > 0) {
//						  url+"?alt="+alternativo;
//						  }
//					  else {
//						  $("#errorMsg").show();
//						  $("#error-head-files").text(ERROR);
//						  $("#error-body-files").text("Debe capturar un nombre de archivo válido"); 
//					  }
//				  }

				  
				  var data = new FormData();
				  jQuery.each(jQuery('#file-sol')[0].files, function(i, file) {
				      data.append('file-sol-'+i, file);
				  });

				  data.append("idSolicitud",idSolicitudGlobal);
				  
				  if(data){ // data
					  
					  jQuery.ajax({
						  
				    		url : url_server+"/saveSolicitudDocument",
						    data: data,
						    cache: false,
						    contentType: false,
						    processData: false,
						    type: 'POST',
						    success: function(data){
						        console.log(data);
								$("#loaderXML").hide();
					      	    $("#file-sol").removeClass("errorx");
					      	    
					      	  if(data.anexado == "true"){
					      		 $("#file-sol").val(null);
					      		 $("#errorMsg").show();
								 $("#error-head-files").text(ATENCION);
								 $("#error-body-files").text(ARCHIVO_ANEXADO);
								 
								 //cargar lista
								 cargarLista();
								 
						        }else{
						        	if(data.invalido == "true"){
						        		$("#file-sol").val(null);
						        		$("#errorMsg").show();
										$("#error-head-files").text(ERROR);
										$("#error-body-files").text(EXTENSION_INVALIDA);  
						        	}else{
							        	if(data.no_anexado == "true"){
							        		$("#file-sol").val(null);
							        		$("#errorMsg").show();
											$("#error-head-files").text(ERROR);
											$("#error-body-files").text(ARCHIVO_NO_ANEXADO);  
							        	}else{
							        		if(data.vacio == "true"){
							        			$("#file-sol").val(null);
								        		$("#errorMsg").show();
												$("#error-head-files").text(ERROR);
												$("#error-body-files").text(ARCHIVO_VACIO);  
							        		}
							        		else{
								        		if(data.existe == "true"){
								        			$("#file-sol").val(null);
									        		$("#errorMsg").show();
													$("#error-head-files").text('');
													$("#error-head-files").text(ERROR);
													$("#error-body-files").text(MENSAJE_EXISTE+":");
									        	}
							        		}
								        }
							        }
						        }
						    }
						});
				  } // data
				 } // size
			  }else{ // vacío
				  //$("#file-sol").addClass("errorx");
				  $("#errorMsg").show();
				  console.log("false");
				  $("#error-head-files").text(ERROR);
				  $("#error-body-files").text(ARCHIVO_NO_SELECCIONADO);
			}
		};
		  
		

		
 		  function cargarLista(){
console.log(idSolicitudGlobal+" <---");
			  jQuery.ajax({
				  type: "GET",
	                cache: false,
	                url: url_server+"/solicitudArchivoLista",
	                async: true,
 	                data: "idSolicitud=" + idSolicitudGlobal,				
 				  success: function(result){
					  $("#loaderXML").hide();					  
					  $("#file-sol").removeClass("errorx");
					  $("#tablaDoc").empty().append(result.listaArchivos);
					  $("#tablaDoc2").empty().append(result.listaArchivos);
				  }						
			  });
		  };
		  
		  
		  
		  function eliminar(id){

			  if(id > 0){
			        	
			        console.log(id);
			        
			            $.ajax({
			                type: "GET",
			                cache: false,
			                url: url_server+"/deleteSolicitudArchivo",
			                async: true,
			                data: "intxnId=" + id,
			                success: function(response) {
			                	
			                	if(response.eliminado == "true"){
						        	$("#file-sol").val(null);
						      		$("#errorMsg").show();
									$("#error-head-files").text(ATENCION);
									$("#error-body-files").text(ARCHIVO_ELIMINADO);
									
									 cargarLista();
									
						        }else{
						        	$("#file-sol").val(null);
						      		$("#errorMsg").show();
									$("#error-head-files").text(ERROR);
									$("#error-body-files").text(ARCHIVO_NO_ELIMINADO);
						        }
						        
						        if(response.error_dependencia == "true"){
						        	$("#file-sol").val(null);
						      		$("#errorMsg").show();
									$("#error-head-files").text(ERROR);
									$("#error-body-files").text(ERROR_DEPENDENCIAS);
						        }else{
						        	if(response.error_delete == "true"){
							        	$("#file-sol").val(null);
							      		$("#errorMsg").show();
										$("#error-head-files").text(ERROR);
										$("#error-body-files").text(ERROR_DELETE);
							        }
						        }
			                },
			                error: function(e) {
			                    console.log('Error: ' + e);
			                },
			            }); 
			            
			        	}//if
			        };
		  
		  
		  function validarSize(){
			  //var size = $("#file-sol")[0].size;
			  //alert(jQuery('#file-sol')[0].file-sols[0].size);

			  if(jQuery('#file-sol')[0].files[0].size < 1000000){
				  return true;
			  }else{
				  //alert("Archivo demasiado grande");
				  $("#loaderXML").hide();
				  $("#file-sol").val(null);
				  $("#errorMsg").show();
				  $("#error-head-files").text(ERROR);
				  $("#error-body-files").text(TAMANO_NO_PERMITIDO);
				  return false;
				  //mandar warning
			  }
		  };

