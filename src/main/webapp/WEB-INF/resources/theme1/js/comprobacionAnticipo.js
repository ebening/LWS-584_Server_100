	idUpload = "";
	var hasmodComp = false;
	var xml=true;
	
	function error_alert_modal_comp() {
		$("#error-alert-modal-comp").fadeTo(2000, 500).slideUp(500, function () {
			$("#error-alert-modal-comp").hide();
		});
	}
	
    function enviaProcesoCancelar(idSolicitud){
   	 $.ajax({
	    		type : "GET",
	    		cache : false,
	    		url : url_server+"/cancelarSolicitudComprobacion",
	    		async : false,
	    		data : "idSolicitud=" + idSolicitud,
	    		success : function(response) {
	    	         if(response.resultado == 'true'){
	    	        	$(window).unbind('beforeunload');
		    	        location.reload(true);
	    	         }
	    		},
	    		error : function(e) {
	    			$("#error-head-anticipo").text(ERROR);
					 $("#error-body-anticipo").text(NO_SE_ENVIO);
				     error_alert_anticipo();
	    			
	    		},
	    	}); 
    }
    
    function sumSbt(){
    	var sum = 0;			   
    	$("#restante_modal").val(0);
		$("#sbt_modal").val(0);
    	$('.subtotales').each(function(){
    		if($.isNumeric($(this).val())){				  
    			sum += parseFloat($(this).val());
    			var rest = parseFloat($("#restante_modal").val());
    			var sppc = parseFloat($("#sppc_modal").val());
    			var restante = sppc - sum;
    			$("#restante_modal").val(restante);
    			$("#sbt_modal").val(sum);
    			$('#sbt_modal').number( true, 2 );
    			$('#restante_modal').number( true, 2 );
    			$('#sppc_modal').number( true, 2 );
    		}else{
    			$(this).val(0);
    		}
    	});
    	if(!xml){
    		$("#strSubTotal_modal").val($("#sbt_modal").val());
    		$("#total_modal").val($("#sbt_modal").val());
    		$("#restante_modal").val("0");
    		$("#sppc_modal").val($("#sbt_modal").val());
    	}
    }
    
    function verDetalleEstatus(id) {
		if(id > 0){
      	//console.log(id);
          $.ajax({
              type: "GET",
              cache: false,
              url: url+"/getEstatus",
              async: true,
              data: "intxnId=" + id,
              success: function(result) {
              	$("#tablaDetalle").empty().append(result.lista);
              	//abrir ventana modal
              	$('#modalEstatus').modal('show'); 
              },
              error: function(e) {
                  console.log('Error: ' + e);
              },
          }); 
          
      }//if
	}
    
    function valTodoModal(){
    	
		   $(window).unbind('beforeunload');
		   var error = false;
		

		   var length = $("#tablaDesgloseModal > tbody > tr").length;
		   
		   if(length > 0){
			   
		    $('.subtotales').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    $('.locaciones').each(function() {
		    	if($(this).val() == -1){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    
		    $('.ccontable').each(function() {
		    	if($(this).val() == -1){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    $('.conceptogrid').each(function() {
		    	if($(this).val() == ""){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		   }
		   
		
		   if($("#moneda_modal").val() == -1){
			   $("#moneda_modal").addClass("errorx");
			   error = true;
		   }else{
			   $("#moneda_modal").removeClass("errorx");
		   }
		   
		   if($("#form-info-fiscal").is(":visible")){
			   if(document.getElementById("file_modal").files.length == 0){
				   $("#file_modal").addClass("errorx");
				   error = true;
			   }else{
				   $("#file_modal").removeClass("errorx");
			   }
			   
			   
			   if(document.getElementById("pdf_modal").files.length == 0){
				   $("#pdf_modal").addClass("errorx");
				   error = true;
			   }else{
				   $("#pdf_modal").removeClass("errorx");
			   }
	      }
		   
		   
		   if($("#compania_modal").val() == -1){
			   $("#validarXMLb").addClass("errorx");
			   error = true;
		   }else{
			   $("#validarXMLb").removeClass("errorx");
		   }
		   
		   if(tipoSolicitudGlobal == 1){
				if(idSolicitudGlobal == 0){
					if($("#form-info-fiscal").is(":visible")){
						var ext = $('#pdf_modal').val().split('.').pop().toLowerCase();
						if(ext != "pdf"){
						   error = true;
						   $("#pdf_modal").addClass("errorx");
						}else{
						   $("#pdf_modal").removeClass("errorx");
						}
					}
				 }
		   }
		   
		   //validaciones sin
		   if(tipoSolicitudGlobal == 2){
			   
			 if($("#compania_modal").val() == - 1){
				  $("#compania_modal").addClass("errorx");
				   error = true;
			 }else{
				   $("#compania_modal").removeClass("errorx");
			 }  
			 
			 if($("#proveedor_modal").val() == - 1){
				 $("#proveedor_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#proveedor_modal").removeClass("errorx");
			 }
			 
			 if($("#rfcEmisor_modal").val() == ""){
				 $("#rfcEmisor_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#rfcEmisor_modal").removeClass("errorx");
			 }
			 
			 if($("#folio_modal").val() == ""){
				 $("#folio_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#folio_modal").removeClass("errorx");
			 }
			 
			 if($("#fecha_modal").val() == ""){
				 $("#fecha_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#fecha_modal").removeClass("errorx");
			 }
			 
			 if(xml && $("#strSubTotal_modal").val() == ""){
				 $("#strSubTotal_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#strSubTotal_modal").removeClass("errorx");
			 }

			 if(xml && $("#total_modal").val() == ""){
				 $("#total_modal").addClass("errorx");
				   error = true;
			 }else{
				 $("#total_modal").removeClass("errorx");
			 }
		   }
		   
		   if(error){
			   $("#error-head-modal").text(ERROR);
			   $("#error-body-modal").text(COMPLETE);
			   error_alert_modal();
		   }else{
			       if(validaEnvioProceso()){
					   $("#enviarSolicitud").prop("disabled", false);
					   disabledEnabledFieldsModal(false);
					   saveFacturaNM();
		          }
		      }
		   
	    }
    
       
        function saveFacturaNM(){
        	
		    var dataf = new FormData();
			var length = $("#tablaDesgloseModal > tbody > tr").length;
			//console.log("length->"+length);

				
			jQuery.each(jQuery('#pdf_modal')[0].files, function(i, file) {
				      dataf.append('pdf',file);
			});
			
			jQuery.each(jQuery('#file_modal')[0].files, function(i, file) {
			      dataf.append('xml',file);
		});
				  
			dataf.append("idCompania",$("#compania_modal").val());
			dataf.append("idProveedor",$("#proveedor_modal").val());
			dataf.append("rfcEmisor",$("#rfcEmisor_modal").val());
			dataf.append("folio",$("#folio_modal").val());
			dataf.append("serie",$("#serie_modal").val());
			dataf.append("fecha",$("#fecha_modal").val());
			dataf.append("strSubTotal",$("#strSubTotal_modal").val());
			dataf.append("moneda",$("#moneda_modal").val());
			dataf.append("iva",$("#iva_modal").val());
			dataf.append("folioFiscal",$("#folioFiscal_modal").val());
			dataf.append("conRetenciones",$("#conRetenciones_modal").is(":checked"));
			dataf.append("iva_retenido",$("#iva_retenido_modal").val());
			dataf.append("isr_retenido",$("#isr_retenido_modal").val());
			dataf.append("ieps",$("#ieps_modal").val());
			dataf.append("total",$("#total_modal").val());
			dataf.append("concepto",$("#concepto_modal").val());
			dataf.append("numrows",length);
			dataf.append("esEdicion",esEdicion);
			dataf.append("facturaEnEdicion",facturaEnEdicion);
			dataf.append("tipoSolicitud",tipoSolicitudGlobal);
	        	
	        jQuery.ajax({
				  
	    		url : url_server+"/saveFacturaNM",
			    data: dataf,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    success: function(data){
			    	$("#idHasChange").val(true);
			    	console.log("la factura en cuestion es la factura numero: "+data.index);
			    	
				    updateRowsDesglose(data.index);
			    	disabledEnabledFieldsModal(true);
			    	
			    	if(esEdicion == false){
					 $("#tablaDesglose").append(data.gridComprobacion);
					  calculateLine();
					  getTotalSol();
					  sumSbtComp()
			    	}else{
			          $("#"+data.index).replaceWith(data.gridComprobacion);
					  calculateLine();
					  getTotalSol();
					  sumSbtComp()
			    	}
			    	var importeActual2 = moneyFloat($('#idImporteTotal').val()); 
   	        	 	updateImporteTotal(importeActual2);
					limpiaFacturaNM();
					//$('.currencyFormat').number( true, 2 );
					cerrarModalNM();
			    },
		    	error : function(e) {
					console.log('Error: ' + e);
				},
		     }); 
       }
        
        
        function limpiaFacturaNM(){
        	
		    $("#compania_modal").val(-1);
		    $("#compania_modal").removeClass(".errorx");
		    
        	$("#proveedor_modal").val(-1);
        	$("#proveedor_modal").removeClass(".errorx");
        	
    	    $("#moneda_modal").val(-1);
    	    $("#moneda_modal").removeClass(".errorx");
    	    
		    $("#folioFiscal_modal").val(null);
		    $("#folioFiscal_modal").removeClass(".errorx");
		    
	        $("#total_modal").val(null);
	        $("#total_modal").removeClass(".errorx");
	        
	        $("#strSubTotal_modal").val(null);
	        $("#strSubTotal_modal").removeClass(".errorx");
	        
	        $("#sppc_modal").val(null);
	        $("#sppc_modal").removeClass(".errorx");
	        
	        $("#rfcEmisor_modal").val(null);
	        $("#rfcEmisor_modal").removeClass(".errorx");
	        
	        $("#serie_modal").val(null);
	        $("#serie_modal").removeClass(".errorx");
	        
	        $("#folio_modal").val(null);
	        $("#folio_modal").removeClass(".errorx");
	        
	        $("#iva_modal").val(null);
	        $("#iva_modal").removeClass(".errorx");
	        
	        $("#ieps_modal").val(null);
	        $("#ieps_modal").removeClass(".errorx");
	        
	        $("#iva_retenido_modal").val(null);
	        $("#iva_retenido_modal").removeClass(".errorx");
	        
	        $("#isr_retenido_modal").val(null);
	        $("#isr_retenido_modal").removeClass(".errorx");
	        
	        $("#fecha_modal").val(null);
	        $("#fecha_modal").removeClass(".errorx");
	        
	        $("#concepto_modal").val(null);
	        $("#concepto_modal").removeClass(".errorx");
	        
	        $("#file_modal").val(null);
	        $("#file_modal").removeClass(".errorx");
	        
	        $("#pdf_modal").val(null);
	        $("#pdf_modal").removeClass(".errorx");
	        
  		    $("#restante_modal").val(null);
  		    $("#restante_modal").removeClass(".errorx");
  		    
  		    $("#sbt_modal").val(null);
  		    $("#sbt_modal").removeClass(".errorx");
  		  
	        
	        $('#tablaDesgloseModal tr').each(function(i, row){
				if(i > 0){
				$(this).closest('tr').remove();
			    sumSbt();
			    calculateLine();
			    }
			});
        }
        
        function editarRowNoMercancias(num){
        	
        	facturaEnEdicion = num;
        	
        	 $.ajax({
 	            type: "GET",
 	        cache: false,
 	        url: url_server+"/editarGridComp",
 	        async: true,
 	        data: "index=" + num,
 	        success: function(data) {
 	        	
 	        	//console.log(data);
 	        	
	        	$("#compania_modal").val(data.idCompania);
	        	$("#proveedor_modal").val(data.idProveedor);
	            $("#rfcEmisor_modal").val(data.rfcEmisor);
	            $("#folio_modal").val(data.folio);
	            $("#serie_modal").val(data.serie);
	            $("#folioFiscal_modal").val(data.folioFiscal);
	            $("#fecha_modal").val(data.fechaEmision);
	            $("#strSubTotal_modal").val(data.subTotal);
	            $("#moneda_modal").val(data.idMoneda);
	            $("#iva_modal").val(data.iva);
	            $("#ieps_modal").val(data.ieps);
	            $("#total_modal").val(data.total);
	            $("#concepto_modal").val(data.concepto);
	            
	            if(data.incluyeRetenciones == 'true'){
			          $("#conRetenciones_modal").prop("checked",true);	
			        }	
	            
	            $("#iva_retenido_modal").val(data.ivaRetenido);
	            $("#isr_retenido_modal").val(data.isrRetenido);
	            $("#sppc_modal").val(data.subTotal);
	            $("#form-info-fiscal").hide();
	            esEdicion = true;
	            facturaEnEdicion = num;
	            cargarDesgloses(num);
	            
	            if(data.tipoSolicitud == '2'){
	            	modalnoXML();
	            }

 	        },
	        error: function(e) {
	            console.log('Error: ' + e);
	        },
	      });     
        }
        
        function cargarDesgloses(num){
        	
        	console.log("los desgloses se cargan para la factura: "+facturaEnEdicion);
        	
       	 $.ajax({
	            type: "GET",
	        cache: false,
	        url: url_server+"/editarDesgloses",
	        async: true,
	        data: "index=" + num,
	        success: function(data) {
	        	
	          	$("#tablaDesgloseModal").append(data.desgloses);
	            calculateLineModal();
				if(tipoSolicitudGlobal == 2){
					$(".ccontable").prop('disabled', true);
				}
	            $('#modal-no-mercancias').modal('show');
	        	console.log("Estado de la ventana esta en edicion? "+esEdicion);
	        	sumSbt();
	        	//$('.currencyFormat').number( true, 2 );
	        },
	        error: function(e) {
	            console.log('Error: ' + e);
	        },
	      });     
       	
       }
       
        
        
        
        function valTodoComprobacion(){
        	$(window).unbind('beforeunload');
        	var errorComp = false;
        	if($("#saldo").val() == ""  || $("#saldo").val() > 0){
        		//$('#comprobacion-anticipo').modal('show');
        		$("#saldo").addClass("errorx");
        		errorComp = true;
        	}else{
        		$("#saldo").removeClass("errorx");
        	}
        	
        	
        	if(errorComp){
        		$("#error-head-anticipo").text(ERROR);
    			$("#error-body-anticipo").text(COMPLETE);
    			error_alert_anticipo();
        	}else{
        		validaFacturasAnticipo();
        	}
        	
        }
        
 function validaFacturasAnticipo(){
		  $.ajax({
	    		type : "GET",
	    		cache : false,
	    		url : url_server+"/validaFacturasAnticipo",
	    		async : false,
	    		success : function(response) {
	    			if(response.status == "ok"){
	    				 disabledEnabledFieldsAsesor(false);
	    				 $("#formComprobacionAnticipo")[0].submit();
	    				 hasmodComp = false;
	    				 loading(true);
	    				 cerrarVentanaAnticipo();
	    			}else{
	    				//console.log("---- else valida -----");
	    				$("#error-head-anticipo").text(ERROR);
	        			$("#error-body-anticipo").text(response.mensaje);
	        			error_alert_anticipo();
	    			}
	    		},
	    		error : function(e) {
	    			console.log('Error: ' + e);
	    		},
	    	});
		  }
 
//	 function validaEstutusAnticipo(){
//		  var valor = false;
//		  $.ajax({
//	    		type : "GET",
//	    		cache : false,
//	    		url : url_server+"/validaEstatusAnticipo",
//	    		async : false,
//	    		success : function(response) {
//	    			if(response.status == "ok"){
//	    				valor = true;
//	    			}else{
//	    				$("#error-head-anticipo").text(ERROR);
//	        			$("#error-body-anticipo").text(response.mensaje);
//	        			error_alert_anticipo();
//	    			}
//	    		},
//	    		error : function(e) {
//	    			console.log('Error: ' + e);
//		    		},
//		    	}); 
//			  return valor;
//	 }

 function disabledEnabledFieldsAsesor(state) {
        	$("#locacion").prop('disabled', state);
        	$("#moneda").prop('disabled', state);
        	$("#importeTotal").prop('disabled', state);
        	$("#fecha_deposito").prop('disabled', state);
        	$("#formaPago").prop('disabled', state);
        	$("#beneficiario").prop('disabled', state);
        	$("#saldo").prop('disabled', state);
        	$("#reembTotComp").prop('disabled', state);
        	$("#proveedor").prop('disabled', state);
        	$("#rfcEmisor").prop('disabled', state);
        	$("#folioFiscal").prop('disabled', state);
        	$("#serie").prop('disabled', state);
        	$("#folio").prop('disabled', state);
        	$("#fecha").prop('disabled', state);
        	$("#iva").prop('disabled', state);
        	$("#ieps").prop('disabled', state);
        	$("#total").prop('disabled', state);
        	$("#conRetenciones").prop('disabled', state);
        	$("#iva_retenido").prop('disabled', state);
        	$("#isr_retenido").prop('disabled', state);
        	$("#idTipoSolicitud").prop('disabled', state);
        	$("#reembTot").prop('disabled', state);
        	$("#compania").prop('disabled', state);
        	$("#radioAsesor").prop('disabled', state);
        	$("#radioProveedor").prop('disabled', state);
        }
        
        
        
        function addRowsDesglose(){
        	
        	  jQuery.ajax({
				  
  	    		url : url_server+"/saveFacturaNM",
  			    data: dataf,
  			    cache: false,
  			    contentType: false,
  			    processData: false,
  			    type: 'POST',
  			    success: function(data){
  			    	addRowsDesglose();
  			    },
  		    	error : function(e) {
  					console.log('Error: ' + e);
  				},
  		     }); 
        	
        	
        }
        
        
        
		function validaEnvioProceso(){
			
			var valido = true;
			var msj = null;
			
			var length = $("#tablaDesgloseModal > tbody > tr").length;
			
			if(length > 0){
				if(validaGrid() == false){
				   if($("#restante_modal").val() == 0){
					 //$("#sppc_modal").removeClass("errorx");
					    $("#restante_modal").removeClass("errorx");
				     return true;
				   }else{
					    $("#error-head-modal").text(ERROR);
					    $("#error-body-modal").text(SALDO_PENDIENTE_CERO);
					 // $("#sppc_modal").addClass("errorx");
					    $("#restante_modal").addClass("errorx");
						error_alert_modal();
						hasmodComp = true;
						return false; 
				   }
				}else{
					$("#error-head-modal").text(ERROR);
				    $("#error-body-modal").text(COMPLETE);
					error_alert_modal();
					hasmodComp = true;
					return false;
				}
			}else{
				$("#error-head-modal").text(ERROR);
			    $("#error-body-modal").text(DESGLOSE_MINIMO);
				error_alert_modal();
				hasmodComp = true;
				return false;
			}
		}
		
		function validaGrid(){
			
			var error = false;
			
		    $('.subtotales').each(function() {
		    	if($(this).val() == "" || $(this).val() == 0){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    $('.locaciones').each(function() {
		    	if($(this).val() == -1){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    
		    $('.ccontable').each(function() {
		    	if($(this).val() == -1){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    $('.conceptogrid').each(function() {
		    	if($(this).val() == ""){
		    		$(this).addClass("errorx");
		    		error = true;
		    	}else{
		    		$(this).removeClass("errorx");
		    	}
			});
		    
		    return error;
		}
		
		
		
		function updateRowsDesglose(index){
			$('#tablaDesgloseModal tr').each(function (i, row) {
				if (i > 0) {
					
					var data = new FormData();
					var indice = $(this).find(".objectInput").val();
					
					/*subtotal*/
					var inputSubtotal = "facturaDesgloseList@.strSubTotal";
					inputSubtotal = getValueByElement(inputSubtotal,indice,1);

					/*Locacion*/
					var inputLocacion = "facturaDesgloseList@.locacion.idLocacion";
					inputLocacion = getValueByElement(inputLocacion,indice,2);
					
					/*cuenta contable*/
					var inputCuentaC = "facturaDesgloseList@.cuentaContable.idCuentaContable";
					inputCuentaC = getValueByElement(inputCuentaC,indice,2);

					/* concepto */
					var inputConcepto = "facturaDesgloseList@.concepto";
					inputConcepto = getValueByElement(inputConcepto,indice,1);

					 $.ajax({
			 	        type: "GET",
			 	        cache: false,
					    contentType: false,
					    processData: false,
			 	        url: url_server+"/updateDesgloseListNM",
			 	        async: true,
						data : "indice=" + indice + "&subtotal=" + inputSubtotal + "&locacion=" + inputLocacion + "&cuentaContable=" + inputCuentaC + "&concepto=" + inputConcepto+ "&indexfactura=" + index ,
			 	        success: function(data) {
			 	        	
			 	        	$("#tablaDesgloseModal").append(data.desgloses);
							calculateLineModal();
							if(tipoSolicitudGlobal == 2){
								$(".ccontable").prop('disabled', true);
							}
//							$('.currencyFormat').number( true, 2 );
							
			 	        },
				        error: function(e) {
				            console.log('Error: ' + e);
				        },
				      }); 
				}
			});
		}
		
		
		function getValueByElement(idElemento,num,tipo){
			
			var valor = null;
			
			/*tipo 1 input*/
			if(tipo == 1){
		      idElemento = idElemento.replace("@", num);
			  valor = document.getElementById(idElemento).value;
			}else{
			/*tipo 2 combo*/
			    idElemento = idElemento.replace("@", num);
			    var element = document.getElementById(idElemento);
				valor = element.options[element.selectedIndex].value;
			}
			  return valor;
		}
		
		function cerrarModalNM(){
			$("#cancelar_nomercancias").click();
		}
		
		function modalNoMercancias(){
			  $("#modal-no-mercancias").modal({backdrop: 'static', keyboard: false});
		}
		function cerrarVentanaAnticipo(){
			  $("#anticipos").modal("hide");
		}
	
		$(function() {
			$('#modal-no-mercancias').on('hidden.bs.modal', function () {
				limpiaFacturaNM();
	            $("#form-info-fiscal").show();
				esEdicion = false;
				limpiarDesgloses();
				
				$("#head-sin").hide();
				$("#head-con").show();
				disabledEnabledFieldsModal(true);
				 $("#foliofiscal").show();
		         $("#seriefiscal").show();
			})
		});
		
		
		//
		   function addRowToTableModal(){
			   //console.log("addRowToTableModal");
			   var length = $("#tablaDesgloseModal > tbody > tr").length;
			   var estaEnEdicion = $("#cambiarxml").is(":checked");
			   var solicitante = $("#solicitante").is(":checked");
			   
			   var solicitanteSeleccionado = true;
			   if(solicitante){
				  if($("#idSolicitanteJefe_modal").val() == -1){
					  solicitanteSeleccionado = false;
				  } 
			   }
			   
			  if(solicitanteSeleccionado){
			    $("#idSolicitanteJefe_modal").removeClass("errorx");
		      if(idSolicitud == 1){
				   if(idSolicitud == 0 && estaEnEdicion == false){
					   valFilesFiscales();
				   }else if(idSolicitud > 0 && estaEnEdicion == false){
					   callRow(length);
				   }else if(idSolicitud > 0 && estaEnEdicion == true){
					   valFilesFiscales()
				   }
		     }else{
		    	 if(xml && $("#strSubTotal_modal").val() == ""){
		    		 //console.log("----else-----");
		    		  $("#strSubTotal_modal").addClass("errorx");
					  $("#error-head-modal").text(ERROR);
					  $("#error-body-modal").text(CAPTURE_SUBTOTAL);
		    		  error_alert_modal();
		    	 }else{
					   callRow(length);
		    	 }
		     }
		   }else{
			   // especifico solicitante pero no selecciono ninguno.
			   $("#error-head-modal").text(ERROR);
			   $("#error-body-modal").text(ESPECIFICA_SOLICITANTE);
			   $("#idSolicitanteJefe_modal").addClass("errorx");
	    	   error_alert_modal();
		   }
			   
		   }//adrowtable
		   
		   function valFilesFiscales(){
			   var length = $("#tablaDesgloseModal > tbody > tr").length;

			   if(document.getElementById("file").files.length != 0 && $("#strSubTotal_modal").val() != ""){
				   callRow(length);
			   }  else{
					  $("#file").addClass("errorx");
					  $("#error-alert-modal").show();
					  $("#error-head-modal").text(ERROR);
					  $("#error-body-modal").text(COMPLETE);
			   }
		   }
		   
		 
		   function callRow(length){
			   
			  
			   //console.log(idSolicitante+"->");
			   
				$.ajax({
					type : "GET",
					cache : false,
					url : url_server+"/addRowConXMLComprobacion",
					async : true,
					data : "numrows=" + length + "&idSolicitud=" + tipoSolicitudGlobal + "&idSolicitante=" + idSolicitante + "&tipoSolicitud=" + tipoSolicitudGlobal ,
    				success : function(response) {
						$("#tablaDesgloseModal").append(response);
						calculateLineModal();
						if(tipoSolicitudGlobal == 2){
							$(".ccontable").prop('disabled', true);
						}
//						$('.currencyFormat').number( true, 2 );
					},
					error : function(e) {
						$("#error-head-modal").text(ERROR);
						$("#error-body-modal").text("No fue posible agregar desglose, contacte al administrador.");
						error_alert_modal();
						console.log('Error: ' + e);
					},
				});
		   }
		   

		  $(document).ready(function(){
			  
			  console.info("fechaCreacionAnticipo= "+fechaCreacionAnticipo);
			  $('#sandbox-container input').datepicker({
				  format: 'dd/mm/yyyy',
				  language: 'es',
				  startDate: fechaCreacionAnticipo,
				  endDate: '+0d',
				  autoclose: true,
				  todayHighlight: true
			  });
			  //console.log("tipo solicitud: "+tipoSolicitudGlobal);
			  //console.log("estado solicitud: "+idStatus);
			  //console.log("id solicitud: "+idSolicitudGlobal);
			  
			  $('#proveedor_modal').on('change', function() {
		    	    getRFC(this.value);
				});
			  
				  
			  $('.noMercanciasTooltips').on('change', function() {
		    	    refreshTooltipModal();
				});
			  
			  
//			  $('.currencyFormat').number( true, 2 );
			  $('#strSubTotal_modal').number( true, 2 );
			  $('#iva_modal').number( true, 2 );
			  $('#iva_retenido_modal').number( true, 2 );
			  $('#ieps_modal').number( true, 2 );
			  $('#isr_retenido_modal').number( true, 2 );
			  $('#total_modal').number( true, 2 );
			
			 	$('.sbtGrid').on('blur', function() {
				 $("#sppc_modal").val($('#strSubTotal_modal').val());
				 sumSbt();
				});	
			  
			  $( ".currencyFormat" ).keyup(function() {
				  sumSbt();
				});
			  			 			   
			   sumSbt();
	
			   if(idSolicitud > 0){
				   $("#wrapCambiarXML").show();
			   }else{
				   $("#wrapCambiarXML").hide();
			   }
							    
			   $(window).bind('beforeunload', function(){
					return PERDERA_INFORMACION;
				}); 
			  
			   $('#tablaDesgloseModal').on('click', ".removerFilaDesglose", function(){
				    sumSbt();
				    calculateLineModal();
				    eliminarDesgloseModal($(this).val());
				    //$(this).closest ('tr').remove ();
				}); 
			   
			   if(tipoSolicitudGlobal == 2){
					  disabledEnabledFieldsModal(false);
					  $("#idSolicitanteJefe_modal").prop('disabled',true);
				  }else{
					  //console.log("disable enable tryue");
			          disabledEnabledFieldsModal(true);
				  }
			 
				  $("#cambiarxml").click(function(){
					  $("#mensaje-dialogo").text(MENSAJE_DIALOGO_CON_XML);
					  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
					  $("#cambiarxml_button").show();
				  });
				 
				  $("#cambiarxml_button").click(function(){
					    
					    $("#cambiarxml").prop("checked",true);
					    $("#compania_modal").val(-1);
			        	$("#proveedor_modal").val(-1);
		        	    $("#moneda_modal").val(-1);
					    $("#folioFiscal_modal").val(null);
				        $("#total_modal").val(null);
				        $("#strSubTotal_modal").val(null);
				        $("#sppc_modal").val(null);
				        $("#rfcEmisor_modal").val(null);
				        $("#serie_modal").val(null);
				        $("#folio_modal").val(null);
				        $("#iva_modal").val(null);
				        $("#ieps_modal").val(null);
				        $("#iva_retenido_modal").val(null);
				        $("#isr_retenido_modal").val(null);
				        $("#fecha_modal").val(null);
				        $("#concepto").val(null);
						$("#modal-solicitud").modal("hide");
						//$("#track_asset").prop("checked",false);
						//$("#id_compania_libro_contable").val(-1);
						//$("#par").val(null);
						
						$('#tablaDesgloseModal tr').each(function(i, row){
							if(i > 0){
							$(this).closest('tr').remove();
						    sumSbt();
						    calculateLineModal();
						    }
						});
						
						$("#sbt").val(0);
						$("#restante").val(0);
						$("#validarXMLb").prop("disabled", false);
						$("#file").prop("disabled",false);
						$("#pdf").prop("disabled",false);
						
				  });
				  
				  $("#cancelar_button2").click(function(){
					  
					   
					     $.ajax({
					    		type : "GET",
					    		cache : false,
					    		url : url_server+"/cancelarSolicitud",
					    		async : false,
					    		data : "idSolicitud=" + idSolicitud,
					    		success : function(response) {
					    	        if(response.resultado == 'true'){
					    				$(window).unbind('beforeunload');
					    	        	location.reload(true)
					    	        } 
					    		},
					    		error : function(e) {
					    			console.log('Error: ' + e);
					    		},
					    	});  
				  });
				  
				  $("#cambiarxml_button_cancelar").click(function(){
					  $("#cambiarxml").prop("checked",false);
					  $("#modal-solicitud").modal("hide");
				  });
				  if(isModificacion == 'true'){
					  $("#ok-head-anticipo").text(ACTUALIZACION);
					  $("#ok-body-anticipo").text(INFORMACION_ACTUALIZADA);
					  ok_alert_modal();
				  }
				  if(isCreacion == 'true'){
					  $("#ok-head-anticipo").text(NUEVA_SOLICITUD);
					  $("#ok-body-anticipo").text(SOLICITUD_CREADA);
					  ok_alert_modal();
				  }
				  
				  
				  $('#modal-solicitud').on('hidden.bs.modal', function () {
					   $("#cambiarxml_button").hide();
					   $("#cancelar_button2").hide();
					   $("#cambiar_solicitante_button").hide();
					})
					  
				   setStatusScreenModal(idStatus);	
				  
				  /*PARA activar y refrescar tooltip*/
				  refreshTooltipModal();
				  activeToolTip();
				  
				  
				  $('.aidAll').on('change', function() {
			    	    console.log($(this).attr('id'));
					});
				  
				 
				  
				  
				  $("#conxml").click(function(){
					  modalnoXML();
				  });
				  
				
				$("#id-comprobacion-deposito").val($("#idComprobacionDeposito").val());
				$("#fecha-deposito").val($("#idFechaDeposito").val());
				$("#importe-deposito").val($("#idImporteDeposito").val());
				
				$("#btnDeposito").click(function(){
					console.info("id del deposito: "+$("#id-comprobacion-deposito").val());
					if($("#id-comprobacion-deposito").val() > 0){
						$("#elimina-deposito").show();
						$.ajax({type : "GET",
							cache : false,
							url : url_server+"/getDeposito",
							async : false,
							data : "idComprobacionDeposito=" + $("#id-comprobacion-deposito").val(),
							success : function(response) {
//								console.info(response);
								$("#id-comprobacion-deposito").val(response.idComprobacionDeposito);
								$("#fecha-deposito").val(response.fechaDeposito);
								$("#importe-deposito").val(response.montoDeposito);
								
							},
							error : function(e) {
								console.log('Error: ' + e);
							},
						});
					}else{
						$("#elimina-deposito").hide();
					}
				});
				
				$('#elimina-deposito').click(function(){ATENCION_ELIMINAR
					if($("#id-comprobacion-deposito").val() > 0 && confirm(DEPOSITO_ATENCION_ELIMINAR)){
						$("#elimina-deposito").show();
						$.ajax({type : "GET",
							cache : false,
							url : url_server+"/deleteDeposito",
							async : false,
							data : "idComprobacionDeposito=" + $("#id-comprobacion-deposito").val(),
							success : function(response) {
								set_alert(".deposito-body #errorMsg",ATENCION, DEPOSITO_ELIMINADO_CORRECTAMENTE);
								$("#id-comprobacion-deposito").val(0);
								$("#fecha-deposito").val($("#idFechaDeposito").val());
								$("#importe-deposito").val("0");
								var fecha = $("#fecha-deposito").val();
								var montoDeposito = $("#importe-deposito").val();
								actualizaDepositoComprobacionMonto(montoDeposito,fecha);
								$('#elimina-deposito').hide();
								
								if(tipoSolicitudGlobal == 9){
									$('#transfiere-deposito').text(montoDeposito);
									$('#transfiere-deposito').unbind('number').number(true,2);
									calculaTotales();
									depositoBoton();
									bloqueGastos();
									actualizaBloqueGastos();
								}
								
							},
							error : function(e) {
								console.log('Error: ' + e);
							},
						});
					}
				});
				
				$('#btnComprobar').click(function() {
					var idSolicitud = 0;
					var idComprobacion = 0;
					var idSolicitudAnticipoSession = $("#idSolicitudAnticipoSession").val();
					console.log("idSolicitudAnticipoSession: " + idSolicitudAnticipoSession);
					var idSolicitudComprobacionSession = $("#idSolicitudComprobacionSession").val();
					console.log("idSolicitudComprobacion: " + idSolicitudComprobacionSession);
					if(idSolicitudAnticipoSession!=""){
						idSolicitud = idSolicitudAnticipoSession;
					}
					if(idSolicitudComprobacionSession!="") {
						idComprobacion = idSolicitudComprobacionSession;
					}
					
					//console.log("id solicitud: " + idSolicitud);
					//console.log("idComprobacion: " + idComprobacion);
					
					var idSolicitudesALigar = "";
					var idSolicitudesADesligar = "";
					idSolicitudesALigar = getIdsChecked();
					console.info("idSolicitudesALigar : "+idSolicitudesALigar);
					idSolicitudesADesligar = getIdsNoChecked();
		  			console.info("idSolicitudesADesligar: "+idSolicitudesADesligar);
	  				cambiarEstatusSolicitud(idSolicitud, idComprobacion, idSolicitudesALigar, idSolicitudesADesligar);
			  	});
				
				$(":checkbox").change(function() {
				    if ($(this).attr("checked")) {
				        //unchecked to checked
				    } else {
				        //checked to unchecked
				    }
				});
				
				$(".form-control").on('change', function() {
			    	  hasmodComp = true;
				 });
				$('.currencyFormat').number( true, 2 );
			}); 
		  

		  
	/* 	  function valTrackAsset(){
			  if($("#track_asset").is(":checked")){
				    $("#cuenta_contable").prop('disabled', false);
				    $("#par").prop('disabled', false);
					$(".trackAsset").prop('disabled',false);
			  }else{
					$("#cuenta_contable").prop('disabled', true);
					$("#par").prop('disabled', true);
					$(".trackAsset").prop('disabled',true);
			  }
			  
		  } */
		  
		  function cancelar(){
			  $("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
			  $("#modal-solicitud2").modal({backdrop: 'static', keyboard: false});
			  $("#cancelar_button2").show();
		  }
		  
		  function actualizarSubtotalModal(){
				 $("#sppc_modal").val($('#strSubTotal_modal').val());
				 sumSbt();
		     }
		
		  function valXML_modal(){
			  
			  $("#loaderXML_nm").show();

			  if(document.getElementById("file_modal").files.length != 0){
				  $("#error-alert-modal").hide();
				  
				  var data = new FormData();
				  jQuery.each(jQuery('#file_modal')[0].files, function(i, file) {
				      data.append('file-'+i, file);
				  });
				  
				  
				  if(data){
					  jQuery.ajax({
						  
				    		url : url_server+"/resolverXML",
						    data: data,
						    cache: false,
						    contentType: false,
						    processData: false,
						    type: 'POST',
						    success: function(data){
								$("#loaderXML_nm").hide();
					      	    $("#file_modal").removeClass("errorx");

						        if(data.validxml == "true"){
						        	$("#compania_modal").val(data.idCompania);
						        	$("#proveedor_modal").val(data.idProveedor);
						        	
						        	if(data.idMoneda == "null"){
						        		$("#moneda_modal").prop("disabled",false);
									}else{
						        	  $("#moneda_modal").val(data.idMoneda);
									}
						        	
							        $("#folioFiscal_modal").val(data.folioFiscal);
							        $("#total_modal").val(data.total);
							        $("#strSubTotal_modal").val(data.subTotal);
							        $("#sppc_modal").val(data.subTotal);
							        $("#rfcEmisor_modal").val(data.rfcEmisor);
							        $("#serie_modal").val(data.serie);
							        $("#folio_modal").val(data.folio);
							        $("#iva_modal").val(data.iva);
							        $("#ieps_modal").val(data.ieps);
							        //console.log(data);
							        $("#tipoFactura_modal").val(data.tipoFactura);
							        
							        if(data.incluyeRetenciones == 'true'){
							          $("#conRetenciones").prop("checked",true);	
							        }
							        
							        $("#iva_retenido_modal").val(data.ivaRetenido);
							        $("#isr_retenido_modal").val(data.isrRetenido);
							        $("#fecha_modal").val(data.fechaEmision);
							        
							        if(data.wsmensaje != null){
							        	if(data.wscode == 0){
							        		$("#ok-head-modal").text(FACTURA_VALIDA);
										    $("#ok-body-modal").text(data.wsmensaje);
										    ok_alert_modal();
							        	}else{
							        	    $("#error-head-modal").text(ERROR);
											$("#error-body-modal").text(data.wsmensaje);
							        		error_alert_modal();
							        	}
							        }
							        
							        refreshTooltipModal();
							        
						        }else{
						      	    $("#file").addClass("errorx");
								    $("#error-alert-modal").show();
								    $("#error-head-modal").text(ERROR);
								    $("#error-body-modal").text(data.wsmensaje);
								    $("#file").val(null);
						        }
						    }
						});
				  }
				  
			  }else{
				  $("#loaderXML").hide();
  				  $("#file").addClass("errorx");
				  $("#error-alert-modal").show();
				  $("#error-head-modal").text(ERROR);
				  $("#error-body-modal").text(COMPLETE);
			  }
		  }
		  
		  
					function calculateLineModal() {
						$('#tablaDesgloseModal tr').each(function(i, row) {
							if (i > 0) {
								var nlinea = $(this).find(".linea").text(i);
							}
						});
					}
				
					function disabledEnabledFieldsModal(state){
					    $("#compania_modal").prop('disabled', state);
					    $("#proveedor_modal").prop('disabled', state);
					    $("#rfcEmisor_modal").prop('disabled', state);
					    $("#folioFiscal_modal").prop('disabled', state);
					    $("#serie_modal").prop('disabled', state);
					    $("#folio_modal").prop('disabled', state);
					    $("#fecha_modal").prop('disabled', state);
					    $("#strSubTotal_modal").prop('disabled', state);
					    $("#moneda_modal").prop('disabled', state);
					    $("#iva_modal").prop('disabled', state);
					    $("#ieps_modal").prop('disabled', state);
					    $("#total_modal").prop('disabled', state);
					    $("#conRetenciones_modal").prop('disabled', state);
					    $("#iva_retenido_modal").prop('disabled', state);
					    $("#isr_retenido_modal").prop('disabled', state);
					    $("#tipoSolicitud_modal").prop('disabled',state);
					    $("#idSolicitanteJefe_modal").prop('disabled',state);
						$(".ccontable").prop('disabled', state);
						$("#tipoFactura_modal").prop('disabled', state);
						
						$("#moneda_modal").val(ID_PESOS);//ID_PESOS = 8 --> Pesos
						
						if(!state){
							//Sin XML
							xml=false;
							$("#strSubTotal_modal").closest("div[class^='col-']").hide();
							$("#iva_modal").closest("div[class^='col-']").hide();
						    $("#ieps_modal").closest("div[class^='col-']").hide();
						    $("#total_modal").closest("div[class^='col-']").hide();
						    $("#conRetenciones_modal").closest("div[class^='col-']").hide();
						    $("#iva_retenido_modal").closest("div[class^='col-']").hide();
						    $("#isr_retenido_modal").closest("div[class^='col-']").hide();
						    $("#concepto_modal").closest("div[class^='col-']").hide();
						    $("#conceptoGastoLBL").hide();
						    adjustMonedaModal(true);
						}else{
							xml=true;
							$("#strSubTotal_modal").closest("div[class^='col-']").show();
							$("#iva_modal").closest("div[class^='col-']").show();
						    $("#ieps_modal").closest("div[class^='col-']").show();
						    $("#total_modal").closest("div[class^='col-']").show();
						    $("#conRetenciones_modal").closest("div[class^='col-']").show();
						    $("#iva_retenido_modal").closest("div[class^='col-']").show();
						    $("#isr_retenido_modal").closest("div[class^='col-']").show();
						    $("#concepto_modal").closest("div[class^='col-']").show();
						    $("#conceptoGastoLBL").hide();
						    adjustMonedaModal(false);
						}
					   /*  if($("#track_asset").is(":checked")){
					    	$("#par").prop('disabled',state);
					    	$("#id_compania_libro_contable").prop('disabled',state);
					    	
					    } */
					}
					
					
					$('#tablaDesgloseModal').on('click', ".removerFila", function(){
					    $(this).closest ('tr').remove ();
					    sumSbt();
					    calculateLineModal();
					}); 

					function enviarSolicitudProceso() {
						  //validar
						var estatus = false;
						console.info("hasmodComp = "+hasmodComp);
						if(hasmodComp == false){
							if(validaEnvio()){
//					        	if(esMultiple == 'false') {//Solicittud SIMPLE
//									console.info("Solicittud SIMPLE");
//									estatus = validaEstutusAnticipo();
//								} else if(esMultiple == 'true'){//Solicittud MULTIPLE
//									console.info("Solicittud MULTIPLE");
//									estatus = validaEstutusAnticipo();
//								}
//					        	if(estatus){
					        		enviaProceso(idSolicitud);
//					        	}
							}
						}else{
						    $("#error-head-anticipo").text(ERROR);
							$("#error-body-anticipo").text(GUARDE_ENVIAR);
							error_alert_anticipo();
						}
					}
					
					function getRFC(idProveedor){
						if(idProveedor != -1){
						     $.ajax({
						    		type : "GET",
						    		cache : false,
						    		url : url_server+"/getRFC",
						    		async : false,
						    		data : "idProveedor=" + idProveedor,
						    		success : function(response) {
						    			$("#rfcEmisor_modal").val(response);
						    		},
						    		error : function(e) {
						    			console.log('Error: ' + e);
						    		},
						    	}); 
						   }else{
							   $("#rfcEmisor_modal").val(null);
						   }
					 }
					
					
					function validaEnvioProcesoModal(){
						
						var valido = true;
						var msj = null;
						
						var length = $("#tablaDesgloseModal > tbody > tr").length;
						if(length > 0){
							if(validaGridModal() == false){
							   if($("#restante").val() == 0){
								// $("#sppc_modal").removeClass("errorx");
								 $("#restante_modal").removeClass("errorx");
							     return true;
							   }else{
								   $("#error-head-modal").text(ERROR);
								    $("#error-body-modal").text(SALDO_PENDIENTE_CERO);
								   // $("#sppc_modal").addClass("errorx");
								    $("#restante_modal").addClass("errorx");
									error_alert_modal();
									hasmodComp = true;
									return false; 
							   }
							}else{
								$("#error-head-modal").text(ERROR);
							    $("#error-body-modal").text(COMPLETE);
								error_alert_modal();
								hasmodComp = true;
								return false;
							}
						}else{
							$("#error-head-modal").text(ERROR);
						    $("#error-body-modal").text(DESGLOSE_MINIMO);
							error_alert_modal();
//							hasmodComp = true;
							return false;
						}

					
						
					}
					
					function setStatusScreenModal(idStatus){
						if(idStatus > 0){
							if(idStatus > 1){
							  $("#form-info-fiscal").hide();
							  $("#solicitante").prop("disabled",true);
				     		  $("#concepto").prop("disabled",true);
							  //$("#track_asset").prop("disabled",true);
							  $(".subtotales").prop("disabled",true);
							  //$(".trackAsset").prop("disabled",true);
							  $(".removerFila").prop("disabled",true);
							  $(".locaciones").prop("disabled",true);
							  $(".ccontable").prop("disabled",true);
							  $(".conceptogrid").prop("disabled",true);
							  
							  //restantes
							  disabledEnabledFieldsModal(true);
							  checkStatusBehaviorModal(idStatus);
							  
							}
						}
					}
					
				
					function validaGridModal(){
						
						var error = false;
						
					    $('.subtotales').each(function() {
					    	if($(this).val() == "" || $(this).val() == 0){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    $('.locaciones').each(function() {
					    	if($(this).val() == -1){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    
					    $('.ccontable').each(function() {
					    	if($(this).val() == -1){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    $('.conceptogrid').each(function() {
					    	if($(this).val() == ""){
					    		$(this).addClass("errorx");
					    		error = true;
					    	}else{
					    		$(this).removeClass("errorx");
					    	}
						});
					    
					    return error;
					}
					
					function check_numericos_modal(){
						  $('.numerico').each(function(){
							  if($.isNumeric($(this).val()) == false){				  
								  $(this).val(0);
							  }
						  });
					}
					
				function cleanDesgloseModal(){
					$('#tablaDesgloseModal tr').each(function(i, row){
						if(i > 0){
						$(this).closest('tr').remove();
					    sumSbt();
					    calculateLineModal();
					    }
					});
					
					//$("#track_asset").prop("checked",false);
					//$("#id_compania_libro_contable").val(-1);
					//$("#par").val(null);
				}	
				
			     function verDetalleEstatusModal(id) {
			         
			  		if(id > 0){
			          	//console.log(id);
			          
			              $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: url_server+"/getEstatus",
			                  async: true,
			                  data: "intxnId=" + id,
			                  success: function(result) {
			                  	$("#tablaDetalle").empty().append(result.lista);
			                  	//abrir ventana modal
			                  	$('#modalEstatus').modal('show'); 
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              }); 
			              
			          }//if
			  	}
			     
			     //recibe el id del elemento
			     function actualizarCuentas(idLoc){
			    	 var id = idLoc;
			    	 var idLocacion = document.getElementById(id).value;
			    	 
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: url_server+"/seleccionLocacion",
			                  async: true,
								data : "idElemento=" + id + "&idLocacion=" + idLocacion+ "&tipoSolicitud=" + tipoSolicitudGlobal,
			                  success: function(result) {
			                  	 //console.log(result.idElemento);
			                  	 document.getElementById(result.idElemento).innerHTML=result.options;
			                  	 //console.log(result.options);
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              }); 
			    	 
			     }			   
			     
			   
			     
			     function refreshTooltipModal(){
			    	 
			         var selectedOp = $("#proveedor_modal option:selected").text();
			         var selectedOpComp = $("#compania_modal option:selected").text();
			         //var selectedOpLibroCont = $("#id_compania_libro_contable option:selected").text();
				         
				         $('#proveedorLabel').tooltip('hide')
				          .attr('data-original-title', selectedOp)
				          .tooltip('fixTitle');
				         
				         $('#companiasLabel').tooltip('hide')
				          .attr('data-original-title', selectedOpComp)
				          .tooltip('fixTitle');
				         
				    /*      $('#libroContableLabel').tooltip('hide')
				          .attr('data-original-title', selectedOpLibroCont)
				          .tooltip('fixTitle'); */
			     }
			     
			 	function error_alert_anticipo(){
					console.log("eerror alert called anticipo");
					$("#error-alert-anticipo").fadeTo(5000, 500).slideUp(500, function(){
					    $("#error-alert-anticipo").hide();
					});
				}
			     
			     
			     function alSeleccionarAID(idAID){
			    	 
			    	 var id = idAID;
			    	 var idValor = document.getElementById(id).value;
			    	 
			    	   $.ajax({
			                  type: "GET",
			                  cache: false,
			                  url: url_server+"/seleccionAID",
			                  async: true,
								data : "idElemento=" + id + "&idAid=" + idValor,
			                  success: function(result) {
				                  	 document.getElementById(result.idCatMenor).innerHTML=result.optionsMenor;
				                  	 document.getElementById(result.idCatMayor).innerHTML=result.optionsMayor;
			                  },
			                  error: function(e) {
			                      console.log('Error: ' + e);
			                  },
			              });
			    	 
			    	 
			     }
			     
			     function enviaProcesoModal(idSolicitud){
			    	 enviaProceso(idSolicitud);
			     }
		
		
		// Anexar depósito ----------------------------------------------------
			     if (isComprobacionViaje == undefined) isComprobacionViaje = false;
			     if (isComprobacionViaje) {
			    	 idUpload = "uploadFile2";
			    	 $('#uploadFile','#capturaDeposito').remove();
			     }
			     else {
			    	 
			    	 idUpload = "uploadFile";
			    	 $('#uploadFile2','#capturaDeposito').remove();
			     }
			     
			     document.getElementById("anexo-deposito").onchange = function () {
					    document.getElementById(idUpload).value = this.value;
						};
						// validar campos depósito ----------------------------------
					    function valInputsDeposito(){
					    console.log("IDGlobal "+idSolicitudGlobal," IDS "+idSolicitud);
					    setActive(true);
						var error = false;
						if ($("#fecha-deposito").val() == "") {
							$("#fecha-deposito").addClass("errorx");
							error = true;
						} else {
							$("#fecha-deposito").removeClass("errorx");
						}
						if ($('#importe-deposito').val() == null || $('#importe-deposito').val() == "" || $('#importe-deposito').val() == 0) {
							$('#importe-deposito').addClass("errorx");
								error = true;
							} else {
							$('#importe-deposito').removeClass("errorx");
							}
						
						if (error) {
							set_alert(".deposito-body #errorMsg",ERROR, COMPLETE);
						}
						
						// Si no hay errores con los cambos revisar imagen seleccionada
						else {
							if ($(".upload-deposito #"+idUpload).val() == "") {
								$(".upload-deposito #"+idUpload).addClass("errorx");
								error = true;
							} else {
								$(".upload-deposito #"+idUpload).removeClass("errorx");
							}
							// Si hay archivo vacío
							if (error) {
								set_alert(".deposito-body #errorMsg",ERROR, ARCHIVO_VACIO);
							}
							else{
								anexarDeposito('#anexo-deposito');
							}
								
						}
						return error;
						}
						// función alternativa para desplegar alertas ---------------
						function set_alert(div,head,body,id) {
							$(div+" strong #error-head-files").text(head);
							$(div+" #error-body-files").text(body);
							if (id == 1) {
								$(div).removeClass("alert-warning").addClass("alert-success");
									display_alert(div,id);		
								}
							else {
								$(div).removeClass("alert-success").addClass("alert-warning");
								setActive(false)
								display_alert(div,id);
								}
							
							}
						
						
						function eliminarDesgloseModal(idDesglose){
							
							//dataf.append("esEdicion",esEdicion);
							//dataf.append("facturaEnEdicion",facturaEnEdicion);
							//console.log("esEdicion: "+esEdicion);
							//console.log("facturaEnEdicion: "+facturaEnEdicion);
							
							
							var idFactura = 0;
							if(esEdicion){
								
							}
							
							 $.ajax({
						    		type : "GET",
						    		cache : false,
						    		url : url_server+"/eliminarDesglose",
						    		async : false,
						    		data : "indice=" + idDesglose + "&indiceFactura=" + facturaEnEdicion,
						    		success : function(response) {
						    	         if(response.resultado == 'true'){
						    	        	 $(".removerFilaDesglose[value='"+response.indice+"']").closest('tr').remove();
						    	        	 sumSbt();
						    	        	 calculateLineModal();
						    	        	 var importeActual2 = moneyFloat($('#idImporteTotal').val()); 
						    	        	 updateImporteTotal(importeActual2);
						    	         }
						    		},
						    		error : function(e) {
						    			$("#error-head-modal").text(ERROR);
										 $("#error-body-modal").text(NO_SE_ENVIO);
									     error_alert_modal();
						    			
						    		},
						    	}); 
						}
						
						
						function limpiarDesgloses(){
							 $.ajax({
						    		type : "GET",
						    		cache : false,
						    		url : url_server+"/limpiarDesgloses",
						    		async : false,
						    		success : function(response) {
						    	       //console.log("desgloses clean");
						    		},
						    	
						    	}); 
						}
						
						function validaEnvio(){
							
							var error = false;
						    
				        	if($("#saldo").val() == "" || $("#saldo").val() > 0){
				        		$("#saldo").addClass("errorx");
				        		error = true;
				        	}else{
				        		$("#saldo").removeClass("errorx");
				        	}

						    if (error) {
								$("#error-head-anticipo").text(ERROR);
								$("#error-body-anticipo").text(COMPLETE);
								error_alert_anticipo();
								return false;
							} else {
								return true;
							}
						 
						}
						
						function modalnoXML(){
						
							 $("#head-con").hide();
							  $("#head-sin").show();
							  disabledEnabledFieldsModal(false);
							  tipoSolicitudGlobal = 2;
					          $("#form-info-fiscal").hide();
					          $("#foliofiscal").hide();
						      $("#seriefiscal").hide();
						      $("#concepto_modal").hide();
						}
						
						
						
						function display_alert(div,id) {
							$(div).fadeTo(5000, 500, function () {
								 if (id != 1) $(div).slideUp(500, function(){
									    $(div).hide();
									});
							})
						}
					 	// Fin validar campos depósito ------------------------------
						// Función para guardar la imagen ---------------------------
						//"file-sol"
						function anexarDeposito(uploader){
							  //$uploader = $("#anexo-deposito");
							 
							  idUploader = uploader.split("#")[1];
							  
							  if(document.getElementById(idUploader).files.length != 0){ // vacío
								 //log("#"+idUploader+": "+document.getElementById(idUploader).files.length);
								 if(validarSizeFile(uploader)){	// size	  

								  $("#errorMsg").hide();
								  $("#loaderXML").show();
								  
								  var data = new FormData();
								  jQuery.each(jQuery(uploader)[0].files, function(i, file) {
								      data.append(idUploader+'-'+i, file);
								  });
								  
								  
								  
								  
								  //log(data.anexo-deposito-0,2);
								  if(idSolicitud=='') idSolicitud=idAnticipo;
								  data.append("idSolicitud",idSolicitud);
								  if(data){ // data
									  jQuery.ajax({
										  
								    		url : url_server+"/saveDepositoArchivo",
										    data: data,
										    cache: false,
										    contentType: false,
										    processData: false,
										    type: 'POST',
										    success: function(data){
												$("#loaderXML").hide();
									      	    $(uploader).removeClass("errorx");
									      	    
									      	  if(data.anexado == "true"){
									      		 $(uploader).val(null);
									      		//log(JSON.stringify(data, null, 4));
									      		 guardaNuevoDeposito(data.file,data.descripcion);
										        }else{
										        	if(data.invalido == "true"){
										        		$(uploader).val(null);
														set_alert(".deposito-body #errorMsg",ATENCION, EXTENSION_INVALIDA);
														 
										        	}else{
											        	if(data.no_anexado == "true"){
											        		$(uploader).val(null);
											        		set_alert(".deposito-body #errorMsg",ATENCION, ARCHIVO_NO_ANEXADO);
											        	}else{
											        		if(data.vacio == "true"){
											        			$(uploader).val(null);
											        			set_alert(".deposito-body #errorMsg",ATENCION, ARCHIVO_VACIO);
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
								  set_alert(".deposito-body #errorMsg",ATENCION, ARCHIVO_NO_SELECCIONADO);
							}
						}
						// Fin Función para guardar la imagen -------------------------------------------
						// Función para validar el tamaño del archivo -----------------------------------
						function validarSizeFile(uploader){
							  if(jQuery(uploader)[0].files[0].size < 1000000){
								  return true;
							  }else{
								  $("#loaderXML").hide();
								  set_alert(".deposito-body #errorMsg",ATENCION, TAMANO_NO_PERMITIDO);
								  return false;
								  //mandar warning
							  }
						  };
						// Fin de Función para validar el tamaño del archivo ----------------------------
						
				    	// Guardar depósito
				    	var activeDeposito = false;
				        function guardaDeposito() {
				        	if (!activeDeposito) {
				        		tieneErrores = valInputsDeposito();
				        		}
				        	}
				        // Función que mantiene activo el botón -----------------------------------------
						function setActive(act) {
							activeDeposito = act;
							if (act) $("#guarda-deposito").addClass("inactivo");
							else $("#guarda-deposito").removeClass("inactivo");
						}
						// Función que borra los datos del formulario
						function clearDepositoForm() {
							$("#fecha-deposito").val("");
							$("#importe-deposito").val("");
							$("#"+idUpload).val("No se eligió archivo");
							setActive(false);
							$(".deposito-body #errorMsg").hide();
						}
						// Guarda nuevo depósito --------------------------------------------------------
						function guardaNuevoDeposito (fileName,descripcion) {
							// se agrega los datos
							var file = fileName;
							var idComprobacionDeposito = $("#id-comprobacion-deposito").val();
							var fecha = $("#fecha-deposito").val();
							var montoDeposito = $("#importe-deposito").val();
							
							//console.log("ID: "+idSolicitud);
							var data = new FormData();
							data.append("idSolicitud",idSolicitud);
							data.append("idComprobacionDeposito",idComprobacionDeposito);
							data.append("fecha",fecha);
							data.append("monto_deposito",montoDeposito);
							data.append("archivo",file);
							data.append("descripcion",descripcion);
							
							// Llamado al AJAX----------------------------------
							if(data){
								console.log("Llamado al AJAX deposito ---");
								jQuery.ajax({
									url : url_server+"/saveDeposito",
									data: data,
									cache: false,
									contentType: false,
									processData: false,
									type: 'POST',
								    success: function(response){
										if (response.guardado){
											set_alert(".deposito-body #errorMsg",ATENCION, DEPOSITO_GUARDADO_CORRECTAMENTE);
											$("#id-comprobacion-deposito").val(response.idComprobacionDeposito);
										}else{
											set_alert(".deposito-body #errorMsg",ATENCION, DEPOSITO_ACTUALIZADO_CORRECTAMENTE);
										}
										if ($('#saldo').length > 0) {
											actualizaDepositoComprobacionMonto(montoDeposito,fecha);
										}
										
										if(tipoSolicitudGlobal == 9){
											$('#transfiere-deposito').text(montoDeposito);
											$('#transfiere-deposito').unbind('number').number(true,2);
											calculaTotales();
											depositoBoton();
											bloqueGastos();
											actualizaBloqueGastos();
										}
										
									},
								    error: function(jqXHR, textStatus, errorThrown) {
								    		  //console.log(textStatus, errorThrown);
								    },
									}).fail( function( jqXHR, textStatus, errorThrown ) {

										  if (jqXHR.status === 0) {

										    alert('Not connect: Verify Network.');

										  } else if (jqXHR.status == 404) {

										    alert('Requested page not found [404]');

										  } else if (jqXHR.status == 500) {

										    alert('Internal Server Error [500].');

										  } else if (textStatus === 'parsererror') {

										    alert('Requested JSON parse failed.');

										  } else if (textStatus === 'timeout') {

										    alert('Time out error.');

										  } else if (textStatus === 'abort') {

										    alert('Ajax request aborted.');

										  } else {

										    alert('Uncaught Error: ' + jqXHR.responseText);

										  }
									});
							  	}
							}
						
	// Actualiza depósitos desde modal 
	function actualizaDepositoComprobacionMonto (montoDeposito,fecha) {
		var totalActual = moneyFloat($('#importeTotal').val());
		var tdMontoTotal = moneyFloat($('#reembTotComp').val());
		totalActual -= tdMontoTotal;
		console.info(totalActual);
		totalActual -= moneyFloat(montoDeposito);
		console.info(totalActual);
		$('#saldo').val(totalActual);
		console.info(fecha);
		$("#fecha-deposito").val(fecha)
		$('#saldo').number(true,2);
	}
	
	function moneyFloat (value) {
		numero = value.replace(',','');
		float = parseFloat(numero);
		//log(value+' Float-> '+float,3);
		return float;
	}
						
	function cambiarEstatusSolicitud(idSolicitud, idComprobacion, idSolicitudesALigar, idSolicitudesADesligar) {
		$.ajax({
			type : "GET",
			cache : false,
			url : url_server+"/cambiarEstatusSolicitudComprobacion",
			async : false,
			data : "idSolicitud=" + idSolicitud + "&idComprobacion=" + idComprobacion + "&idSolicitudesALigar=" + idSolicitudesALigar + "&idSolicitudesADesligar=" + idSolicitudesADesligar,
			success : function(response) {
	//			console.info(response);
				console.info(response.anticiposIncluidos);
				if(response.resultado == 'true'){
					var importeActual2 = moneyFloat($('#idImporteTotal').val());
					$("#tablaComprobadas").empty().append(response.anticiposIncluidos);
					updateImporteTotal(importeActual2);
//					var totalActual = moneyFloat($('#importeTotal').val());
//					var importeTotal = moneyFloat(response.importeTotal);
//					totalActual += importeTotal;
//					$('#importeTotal').val(totalActual);
//					$('#saldo').val(totalActual);
//					
//					$('#importeTotal').number( true, 2 );
//					$('#saldo').number( true, 2 );
				}
			},
			error : function(e) {
				$("#error-head-anticipo").text(ERROR);
				$("#error-body-anticipo").text(NO_SE_ENVIO);
				error_alert_anticipo();
					
			},
		});
	}

	function getIdsChecked(){
		idSolicitudes = "";
		$("input:checkbox[name=optradio]:checked").each(function() {
			var idAIncluir = $(this).val();
	    	idSolicitudes += idAIncluir + ",";
		});
		if(idSolicitudes==="")
			idSolicitudes="-1";
	//	console.info("id: "+idSolicitudes);
		return idSolicitudes;
	}
	function getIdsNoChecked(){
		idSolicitudes = "";
		$("input:checkbox:not([name=optradio]:checked)").each(function(index, value) {
			var idANoIncluir = $(this).val();
			if(idANoIncluir !== 'true')
				idSolicitudes += idANoIncluir + ",";
		});
		if(idSolicitudes==="")
			idSolicitudes="-1";
		console.info("id: "+idSolicitudes);
		return idSolicitudes;
	}
	
	function actualizaTotales(importe) {
		var totalActual = moneyFloat($('#importeTotal').val());
		totalActual -= importe;
		$('#importeTotal').val(totalActual);
	}
	
	var monedaModalParentClassName;
	function adjustMonedaModal(b){
		var parentDivMod = $("#moneda_modal").closest("div[class='noPaddingMoneda']");
		var parentDivOriginal = $("#moneda_modal").closest("div[class^='col-']");
		if(monedaModalParentClassName==null){
			monedaModalParentClassName = parentDivOriginal.attr("class");
		}
		if(b){
			if(parentDivOriginal.length>0){
				parentDivOriginal.attr("class","noPaddingMoneda");
			}
		}else{
			if(parentDivMod.length>0){
				parentDivMod.attr("class",monedaModalParentClassName);
			}
		}
	}