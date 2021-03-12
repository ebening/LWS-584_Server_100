
$(document).ready(function(){
	
	startDatePicker();
	hasmod = false;
	
	 $(".form-control").on('change', function() {
   	  console.log("cambio: "+$(this).val());
   	  hasmod = true;
	 });
	
	$('#fechaSalida').datepicker();
	$('#fechaRegreso').datepicker();

	//$("#transporte-aereo :input").attr("disabled", true);
	
	$('.currencyFormat').number( true, 2 );
	$('.numberFormat').number( true,0 );
	
	$('.sandbox-container input').change(function () {
	});
	
	function startDatePicker(){
		  $('.input-daterange').datepicker({
			  format: 'dd/mm/yyyy',
			  language: 'es',
			  startDate: '+0d',

		  });
	  }

    $('.aeroCheck').each(function (index) {
    	if($(this).is(":checked")) {
    		if(checkEconomica($(this).attr('id')))
            	$("#motivoAerolinea").prop("disabled", true);
            else
            	$("#motivoAerolinea").prop("disabled", false);
        }
    })

    $('.hiddenSelec').each(function (index) {
        $(this).val(false);
    })
    $("#idFormaPago").prop("disabled", true);
    
    checkStatusBehavior(idEstadoSolicitud);
    
});

$("#enableAero").change(function() {
    if(!this.checked){
    	$('#transporte-aereo').find('input:text').val('');
 		$("#motivoAerolinea").val('');
    	$('.aeroCheck').each(function (index) {
        	$(this).attr("checked", false);
        })
        sumTotal();
 	}
    $("#motivoAerolinea").removeClass("errorx");
   	$("#transporte-aereo :input").removeClass("errorx");
    $("#transporte-aereo :input").removeClass("errorchk");
    $("#transporte-aereo :input").attr("disabled", !this.checked);
    $("#motivoAerolinea").attr("disabled", true);
});

$("#motivos").change(function() {
	var selected = $(this).find('option:selected')
	if(selected.text() == "OTRO" || selected.text() == "otro"){
    	$("#otroMotivo").prop("disabled", false);
		$("#otroMotivo").parent().removeClass( "motivo-oculto" )
	}
	else{
		$("#otroMotivo").prop("disabled", true);
		$("#otroMotivo").val("");
		$("#otroMotivo").parent().addClass( "motivo-oculto" )
	}
});

$("#destinos").change(function() {
	var selected = $(this).find('option:selected');
	if(selected.text() == "OTRO" || selected.text() == "otro"){
    	$("#otroDestino").prop("disabled", false);
    	$("#otroDestino").parent().removeClass( "destino-oculto" )
	}
	else{
		$("#otroDestino").prop("disabled", true);
		$("#otroDestino").val("");
		$("#otroDestino").parent().addClass( "destino-oculto" )
	}
});

$('.conceptoCheck').change(function() {
    if($(this).is(":checked")) {
        var returnVal = validaAnticipoViaje();
        var input = $("."+this.id).attr("id");
        if(returnVal){
            if($("."+input).hasClass('noCalculado')){
                
            	$("."+input).prop("disabled", false);
            	var otroConcepto = $("."+input).attr("id");
            	$("."+otroConcepto).prop("disabled", false);
            }
            calculaImporte($("."+this.id).val());
        }
        $(this).attr("checked", returnVal);
    }
    else{
        $('.conceptoCheck').each(function (index) {
        	if(!$(this).is(":checked")) {
        		var input = $("."+this.id).attr("id");
            	$("."+input).val("");
            	$("."+input).prop("disabled", true);
            	var otroConcepto = $("."+input).attr("id");
            	$("."+otroConcepto).prop("disabled", true);
            	sumTotal();
        	}
        })
    }
    
    
});

$('.aeroCheck').change(function() {
    if($(this).is(":checked")) {
        var returnVal = validaAerolineas();
        var anterior = null;
        var seleccionado = $(this);
        var costoOld = 0;
        var costoNew = 0;

        if(returnVal){
            if(checkEconomica($(this).attr('id'))){
            	$("#motivoAerolinea").prop("disabled", true);
            	$("#motivoAerolinea").val('');
            }
            else
            	$("#motivoAerolinea").prop("disabled", false);

            $('.aeroCheck').each(function (index) {
            	if($(this).is(":checked")) {
            		if(seleccionado.attr("id") != $(this).attr("id")){
	                	anterior = $(this);
	                	costoOld = $("."+anterior.attr("id")).val();
	                	$(this).attr("checked", false);
            		}
                }
            })
			costoNew = $("."+seleccionado.attr("id")).val();
            sumTransporteAereo(costoOld, costoNew);
            seleccionado.attr("checked", returnVal);
        }
        else{
        	seleccionado.attr("checked", returnVal);
        }
    }
});

$(".costo").blur(function() {
    $('.aeroCheck').each(function (index) {
    	if($(this).is(":checked")) {
    		if(checkEconomica($(this).attr('id')))
            	$("#motivoAerolinea").prop("disabled", true);
            else
            	$("#motivoAerolinea").prop("disabled", false);
        }
    })
	sumTotal();
});

function checkEconomica(check) {
    var result = true;
    var selected = $("."+check+"").val();
	$('.costo').each(function (index) {
		if(parseFloat(selected) <= parseFloat($(this).val())){
			result = true;
		}
		else{
			result = false;
			return false;
		}

    })
    return result; 
}

function validaAerolineas() {
	var error = false;

	$('.filled').each(function (index) {
		if ($(this).val() == "") {
			$(this).addClass("errorx");
    		error = true;
    	} else {
    		$(this).removeClass("errorx");
    	}
    })
    
    $('.costo').each(function (index) {
		if ($(this).val() == "0" || $(this).val() == "") {
			$(this).addClass("errorx");
    		error = true;
    	} else {
    		$(this).removeClass("errorx");
    	}
    })
	
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		return true;
	}
}

function validaAnticipoViaje() {
	var error = false;
	
	if ($("#moneda").val() == -1) {
		$("#moneda").addClass("errorx");
		error = true;
	} else {
		$("#moneda").removeClass("errorx");
	}
	
	if ($("#fechaSalida").val() == "") {
		$("#fechaSalida").addClass("errorx");
		error = true;
	} else {
		$("#fechaSalida").removeClass("errorx");
	}
	if ($("#fechaRegreso").val() == "" ) {
		$("#fechaRegreso").addClass("errorx");
		error = true;
	} else {
		$("#fechaRegreso").removeClass("errorx");
	}
	if ($("#numeroPersonas").val() == "") {
		$("#numeroPersonas").addClass("errorx");
		error = true;
	} else {
		$("#numeroPersonas").removeClass("errorx");
	}
	
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		return true;
	}
}

function calculaImporte(idConcepto) {
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/calculaImporteConcepto",
		async : true,
		data : "moneda=" + $("#moneda").val() +
		"&fechaSalida=" + $("#fechaSalida").val() +
		"&fechaRegreso=" + $("#fechaRegreso").val() +
		"&numPersonas=" + $("#numeroPersonas").val() +
		"&concepto=" + idConcepto,
		success : function (response) {
    		if(response.esCalculado == 1)
				$("#"+response.idConcepto+"").val(response.importe);
    		else{
        		if($("#"+response.idConcepto+"").val() == "")
        			$("#"+response.idConcepto+"").val(0)
        	}
        		
			sumTotal();
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

$("#numeroPersonas").change(function() {
    if(parseInt($(this).val()) <= 0)
		$(this).val(1);
	else{
		$('.conceptoCheck').each(function (index) {
	    	if($(this).is(":checked")) {
	    		var returnVal = validaAnticipoViaje();
	            if(returnVal){
	            	calculaImporte($("."+this.id).val());
	            }
	    	}
	    }) 
	}
});

$("#fechaSalida").change(function() {
	$('.conceptoCheck').each(function (index) {
    	if($(this).is(":checked")) {
    		var returnVal = validaAnticipoViaje();
            if(returnVal){
            	calculaImporte($("."+this.id).val());
            }
    	}
    }) 
});

$("#fechaRegreso").change(function() {
	$('.conceptoCheck').each(function (index) {
    	if($(this).is(":checked")) {
    		var returnVal = validaAnticipoViaje();
            if(returnVal){
            	calculaImporte($("."+this.id).val());
            }
    	}
    }) 
});

$(".importes").blur(function() {
	sumTotal();
});

function sumTotal() {
    var total = 0;
    $("#importeTotal").val("");
	$('.conceptoCheck').each(function (index) {
    	if($(this).is(":checked")) {
    		var returnVal = validaAnticipoViaje();
            if(returnVal){
            	var input = $("."+this.id).attr("id");
            	total += parseFloat($("."+input+"").val());
            }
    	}
    })
    $('.aeroCheck').each(function (index) {
    	if($(this).is(":checked")) {
            var input = this.id;
            total += parseFloat($("."+input+"").val());
    	}
    })
    $("#importeTotal").val(total.toFixed(2));
	$("#importeTotal").removeClass("errorx");
}

function sumTransporteAereo(costoA, costoB) {
    var total = $("#importeTotal").val();
    total -= parseFloat(costoA);
    total += parseFloat(costoB);
    $("#importeTotal").val(total.toFixed(2));
}

function valTodoAnticipoViaje() {
	var error = false;
    error = valInputsAnticipoViaje();
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#save_button").prop('disabled', true);
		$("#importeTotal").prop("disabled", false);
		$("#compania").prop("disabled", false);
		$("#locacion").prop("disabled", false);
		$("#moneda").prop("disabled", false);
		$(".importes").prop("disabled", false);
		$("#formAnticipo")[0].submit();
		loading(true);
	}
}

function valInputsAnticipoViaje(){
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

	if ($('#importeTotal').val() == null || $('#importeTotal').val() == "" || $('#importeTotal').val() == 0) {
		$('#importeTotal').addClass("errorx");
		error = true;
	} else {
		$('#importeTotal').removeClass("errorx");
	}

	if ($("#motivos").find('option:selected').attr('otro') == "true") {
    	if ($("#otroMotivo").val() == "") {
    		$("#otroMotivo").addClass("errorx");
    		error = true;
    	} else {
    		$("#otroMotivo").removeClass("errorx");
    	}
	} else {
		$("#otroMotivo").removeClass("errorx");
	}
	if ($("#motivos").val() == -1) {
		$("#motivos").addClass("errorx");
		error = true;
	} else {
		$("#motivos").removeClass("errorx");
	}
   	if ($("#destinos").find('option:selected').attr('otro') == "true") {
    	if ($("#otroDestino").val() == "") {
    		$("#otroDestino").addClass("errorx");
    		error = true;
    	} else {
    		$("#otroDestino").removeClass("errorx");
    	}
	} else {
		$("#otroDestino").removeClass("errorx");
	}
	if ($("#destinos").val() == -1) {
		$("#destinos").addClass("errorx");
		error = true;
	} else {
		$("#destinos").removeClass("errorx");
	}
	if ($("#fechaSalida").val() == "") {
		$("#fechaSalida").addClass("errorx");
		error = true;
	} else {
		$("#fechaSalida").removeClass("errorx");
	}
	if ($("#fechaRegreso").val() == "") {
		$("#fechaRegreso").addClass("errorx");
		error = true;
	} else {
		$("#fechaRegreso").removeClass("errorx");
	}
	if ($("#numeroPersonas").val() == "" || parseInt($("#numeroPersonas").val()) < 1) {
		$("#numeroPersonas").addClass("errorx");
		error = true;
	} else {
		$("#numeroPersonas").removeClass("errorx");
	}

	$('.noCalculado').each(function (index) {
        if($(this).is(':enabled')){
    		if ($(this).val() == "") {
        		$(this).addClass("errorx");
        		error = true;
        	} else {
        		$(this).removeClass("errorx");
        	}
        }
        else {
    		$(this).removeClass("errorx");
    	}
    })

	if($("#enableAero").is(":checked")) {
		error = valTransporteAereo(error);
    }
	
	return error;
}

function valTransporteAereo(error){

	$('.filled').each(function (index) {
    	if ($(this).val() == "") {
    		$(this).addClass("errorx");
    		error = true;
    	} else {
    		$(this).removeClass("errorx");
    	}
    })

    if($('.aeroCheck:checked').length == 0){
    	$('.aeroCheck').each(function (index) {
        	$(this).addClass("errorchk");
        	error = true;
        })
    }
    else {
    	$('.aeroCheck').each(function (index) {
    		$(this).removeClass("errorchk");
        })    		
	}
    
    if($('#motivoAerolinea').is(':enabled')){
		if ($("#motivoAerolinea").val() == "") {
    		$("#motivoAerolinea").addClass("errorx");
    		error = true;
    	} else {
    		$("#motivoAerolinea").removeClass("errorx");
    	}
    }
    else {
		$("#motivoAerolinea").removeClass("errorx");
	}
	
	return error;
}

// ----------------------------------------------
function anticipoViaje () {
	// Función para aceptar el aviso de política para gastos de viaje ---------
	$acepta = ('#aceptar');
	function aceptaAviso () {
		$btn = $('.blocker-acepta');
		$acepta = $('#aceptar');
	
		$acepta.change(function() {
	        if($(this).is(":checked")) {
	        	enableButton(true);
	        }
	        else {enableButton(false);}
	    });
		
		function enableButton (val) {
			if (!val) {
				$btn.show();
				}
			else {
				$btn.hide();
			}
		}
		
		enableButton(false);
	}

//-----------------------------------------------------------------------------

	// Aparece otro ---------------------------------------
	function apareceOtro (otro, active) {
		active ? $(otro).fadeIn() : $(otro).fadeOut(); 
	}
	// Fin ------------------------------------------------
	aceptaAviso();
}


function enviarSolicitudProceso(){
	var error = false;
    error = valInputsAnticipoViaje();
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		if(hasmod == false){
			enviaProceso(idSolicitud);
		}else{
			$("#error-head").text(ERROR);
			$("#error-body").text(GUARDE_ENVIAR);
		    error_alert();
		}
	}
}

function verDetalleEstatus(id) {
    
		if(id > 0){
      	console.log(id);
      	  loading(true);
          $.ajax({
              type: "GET",
              cache: false,
              url: url_server+"/getEstatus",
              async: true,
              data: "intxnId=" + id,
              success: function(result) {
            	loading(false);
              	$("#tablaDetalle").empty().append(result.lista);
              	//abrir ventana modal
              	$('#modalEstatus').modal('show'); 
              },
              error: function(e) {
            	  loading(false);
                  console.log('Error: ' + e);
              },
          }); 
          
      }//if
	}








