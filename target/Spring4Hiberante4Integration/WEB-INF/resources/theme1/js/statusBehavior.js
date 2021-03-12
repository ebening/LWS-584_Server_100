function checkStatusBehavior(idStatus){
	
	 //Enviada
	  if(idStatus == 2){
		  disableInputs();
		  $("#ok-head").text(ESTADO_SOLICITUD);
		  $("#ok-body").text(ENVIADA_AUTORIZACION);
		  ok_alert();
	   }
	  
	  //Autorizada
	  if(idStatus == 3){
		  disableInputs();
		  $("#ok-head").text(ESTADO_SOLICITUD);
		  $("#ok-body").text(AUTORIZACION_AUTORIZADA);
		  ok_alert();
	  }
	  
	//En confirmacion
	  if(idStatus == 8){
		  disableInputs();
		  $("#ok-head").text(ESTADO_SOLICITUD);
		  $("#ok-body").text("EN CONFIRMACION");
		  ok_alert();
	   }
	  
	  //rechazada
	  if(idStatus == 4){
		  
		  if(tipoSolicitudGlobal == 2){
			   $(".ccontable").prop("disabled",true);
			   $(".removerFila").prop("disabled",false);
			   $("#sbt").prop("disabled",true);
			   $("#restante").prop("disabled",true);
			   $("#sppc").prop("disabled",true);
			   $(".form-control").prop('disabled', false);
		  }
		  
		  if(tipoSolicitudGlobal == 1){
			  $("#form-info-fiscal").show();
			  $("#solicitante").prop("disabled",false);
			  $(".sbtGrid").prop("disabled",false);
			  $(".locaciones").prop("disabled",false);
			  $(".ccontable").prop("disabled",false);
			  $(".conceptogrid").prop("disabled",false);
			  $("#track_asset").prop("disabled",false);
		  }
		  
		  $("#error-head").text(ESTADO_SOLICITUD);
		  $("#error-body").text(AUTORIZACION_RECHAZADA);
		   error_alert();
	  }
	  
	  //validada pendiente de pago
	  if(idStatus == 5){
		  disableInputs();
		  $("#ok-head").text(ESTADO_SOLICITUD);
		   $("#ok-body").text(VALIDADA_AUTORIZACION);
		   ok_alert();
	  }
	  
	  //pagada
	  if(idStatus == 6){
		  disableInputs();
		  $("#ok-head").text(ESTADO_SOLICITUD);
		  $("#ok-body").text(SOLICITUD_PAGADA);
		  ok_alert();
	  }
	  
	  //cancelada
	  if(idStatus == 7){
		    disableInputs();
		  	$("#error-head").text(ESTADO_SOLICITUD);
		   	$("#error-body").text(CANCELADA);
		    error_alert();
		  }
	  
	  if(idStatus == 9){
		  disableInputs();
	  }
	
}

function disableInputs(){
	$(".form-control").prop('disabled', true);
	$(".radio-inline").prop('disabled', true);
	$('.panel-body').find('input, textarea, file, select').prop("disabled", true);
	$("input[type=radio],[type=file]").prop("disabled", true);
	//Disable all buttons except 'consultar_auth'.
	$('#page-wrapper').find(':button').not("#consultar_auth").prop('disabled', true);
	//Remove white background in date inputs.
	$("div.sandbox-container").find("input").removeAttr('style');
}