function getProveedoresAnticipo(id,idBeneficiario) {

	var idTipoProveedor = id;
	$panel = $('#panel-anticipo');
	$blocker = $('.blocker');
	$blocker.css('display','block');
	$blocker.css('display','block');
	
	$blocker.css('height',$panel.css('height'));
	$blocker.css('width',$panel.css('width'));
	$blocker.css({'display':'block','opacity':.5 });
	$blocker.css('cursor','wait');
	
	
	$.ajax({
		type : "GET",
		cache : false,
		url : url + "/getProveedoresAnticipo",
		//async : false,
		data : "idTipoProveedor=" + idTipoProveedor+"&locacion="+$("#locacion").val(),
		success : function(response) {
			$("#beneficiario").empty().append(response.beneficiarioList);
			$panel.css({'opacity':1}).prop('disabled',false);
			$blocker.css('display','none');
			$('#beneficiario').focus();
			if(idBeneficiario != 0){
			    $("#beneficiario").val(idBeneficiario);
			}else{
				$("#beneficiario").val(-1);
			}
			
		},
		error : function(e) {
			$panel.css({'opacity':1}).prop('disabled',false);
			$blocker.css('display','none');
			
			
		},
	});
}

function valTodo() {

	var error = false;
    error = valInputs();

	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#save_button").prop('disabled', true);
		$("#formAnticipo")[0].submit();
		loading(true);
	}
}

function cancelar(){
	  $("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
	  $("#modal-solicitud").modal({backdrop: 'static', keyboard: false});
	  $("#cancelar_button").show();
}

function cerrar_ventana(){
	  $("#modal-solicitud").modal("hide");
}

function cancelar_solicitud(){
	    $.ajax({
  		type : "GET",
  		cache : false,
  		url : url + "/cancelarSolicitud",
  		async : false,
  		data : "idSolicitud=" + idSolicitud,
  		success : function(response) {
  	        if(response.resultado == 'true'){
  				$(window).unbind('beforeunload');
  	        	location.reload(true)
  	        } 
  		},
  		error : function(e) {
  			$("#error-head").text(ERROR);
			 $("#error-body").text(NO_SE_ENVIO);
		     error_alert();
  		},
  	});  
}

$(document).ready(function () {
	checkStatusBehavior(idEstadoSolicitud);
	if(idSolicitudGlobal > 0){
	 cargarLista();
	}
	
	$('#importeTotal').number( true, 2 );
	$('#saldo').number( true, 2 );
		
	if(isCreacion == 'true'){
		  $("#ok-head").text(NUEVA_SOLICITUD);
		  $("#ok-body").text(SOLICITUD_CREADA);
		  ok_alert();
	  }
	
	if(isModificacion == 'true') {
		  $("#ok-head").text(ACTUALIZACION);
		  $("#ok-body").text(INFORMACION_ACTUALIZADA);
		  ok_alert();
	  }
	if(tipoProveedor != "" && idBeneficiario != ""){
		setTipoProveedor();
	}
	
	if(esMultiple == 'true') {
		$("#anticipos").click();
		var idSolicitudComprobacion = $("#idSolicitudComprobacionSession").val();
		console.info("idSolicitudComprobacion = "+idSolicitudComprobacion);
//		if(idSolicitudComprobacion == 0)
		var importeActual = moneyFloat($('#idImporteTotal').val());
			updateImporteTotal(importeActual);
	}
	
	hasmod = false;
});

function updateImporteTotal(importeActual) {
	var saldo = 0;
	var montoTotalSum = 0;
	
	console.info("importeActual: "+importeActual);
	$('#tablaComprobadas tr').each(function(i, row) {
		if (i > 0) {
			var montoTotal = $(this).find(".importeTotalIncluido").text(i);
			console.info("montoTotal: "+montoTotal.val());
			if(montoTotal.val() > 0)
				montoTotalSum += moneyFloat(montoTotal.val());
		}
	});
	
	var importeDeposito = moneyFloat($('#idImporteDeposito').val());
	console.info("importeDeposito: "+importeDeposito);
//	if(importeDeposito > 0)
//		importeActual -=importeDeposito;
//	var importeDeposito = $('#importe-deposito').val();
	var totalComprobado =  $('#reembTotComp').val();
	console.info("totalComprobado: "+totalComprobado);
	//if(totalComprobado > 0)
		//importeActual -=totalComprobado;
	
	console.info("montoTotalSum: "+montoTotalSum);
	importeActual = importeActual + montoTotalSum;
	saldo = importeActual - totalComprobado - importeDeposito;
	console.info("importeActual: "+importeActual);
	$('#importeTotal').val(importeActual);
	$('#importeTotal').number( true, 2 );
	$('#saldo').number( importeActual );
	
	$('#saldo').val(saldo);
	$('#saldo').number( true, 2 );
}

function setTipoProveedor(){
		var tipo = parseInt(tipoProveedor);
		$(".radio-inline input[type=radio]").eq(tipo-1).attr("checked", "checked");
		getProveedoresAnticipo(tipo,idBeneficiario);
}

function enviarSolicitudProceso(){
	  //validar
	  if(validaEnvio()){
		  enviaProceso(idSolicitudGlobal);
	  }
}


function tieneAnticipoPendiente(){

		    $.ajax({
	    		type : "GET",
	    		cache : false,
	    		url : url+"/checkComprobada",
	    		async : false,
	    		data : "idProveedor=" + $("#beneficiario").val(),
	    		success : function(response) {
	    			 console.log(response);
	    	         if(response.resultado == 'false'){
	    	        	 enviarSolicitudProceso();
	    	         }
	    	         else {
	    	        	 // Alerta de mensaje por
	    	        	 $("#error-head").text(ERROR);
						 $("#error-body").text(ERROR_PENDIENTE);
					     error_alert();
	    	         }
	    		},
	    		error : function(e) {
	    			$("#error-head").text(ERROR);
					 $("#error-body").text(NO_SE_ENVIO);
				     error_alert();
	    		},
	    	}); 
}

function refreshBeneficiario () {
	var op = $('#locacion').val();
	$("#beneficiario").empty().html('<option value="-1" selected>Selecciona:</option>');
	$("#beneficiario").val(-1);
	$('.radio-inline input[name="tipoProveedor.idTipoProveedor"]:checked').click();
	
}


function validaEnvio(){
	
	var error = false;
    error = valInputs();
    console.log("error validacxion:"+ error);
    
    if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		return true;
	}
 
}



function valInputs(){
	
	var error = false;
		
	if ($("#compania").val() == -1) {
		$("#compania").addClass("errorx");
		error = true;
	} else {
		$("#compania").removeClass("errorx");
	}
	if ($("#concepto").val() == "") {
		$("#concepto").addClass("errorx");
		error = true;
	} else {
		$("#concepto").removeClass("errorx");
	}
	
	if ($("#locacion").val() == -1) {
		$("#locacion").addClass("errorx");
		error = true;
	} else {
		$("#locacion").removeClass("errorx");
	}

	if ($("#beneficiario").val() == 0 || $("#beneficiario").val() == -1) {
		$("#beneficiario").addClass("errorx");
		error = true;
	} else {
		$("#beneficiario").removeClass("errorx");
	}

	if ($('#importeTotal').val() == null || $('#importeTotal').val() == "" || $('#importeTotal').val() == 0) {
		$('#importeTotal').addClass("errorx");
			error = true;
		} else {
			$('#importeTotal').removeClass("errorx");
		}
	
	return error;
}



function verDetalleEstatus(id) {
		if(id > 0){
      	console.log(id);
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