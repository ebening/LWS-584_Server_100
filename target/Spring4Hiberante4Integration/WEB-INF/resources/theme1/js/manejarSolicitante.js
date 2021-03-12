
var ischanged = false;

$("#solicitante").click(function() {
	if ($("#solicitante").is(":checked")) {
		openModalCambiarSolicitante();
		$("#idSolicitanteJefe").prop('disabled', false);
	} else {
		$("#idSolicitanteJefe").prop('disabled', true);
		openModalCambiarSolicitante();
	}
});


$(document).on('change', '#idSolicitanteJefe', function() {
	openModalCambiarSolicitante();
	ischanged = true;
});

//acepto cambiar solicitante
$("#cambiar_solicitante_button").click(function() {
	cleanDesglose();
	$("#idSolicitanteJefe").val(-1);
	$("#modal-solicitud").modal("hide");
});

//cancelo cambiar solicitante
$("#solicitud_button_cancelar").click(function() {
	
	
	var solChecked = $("#solicitante").is(":checked")
	var selectedSol = $("#idSolicitanteJefe").val();
	
	// si cambian el estatus del check de solicitante
	if(solChecked == true && ischanged == false){
		$("#solicitante").prop("checked", false);
	}else{
		$("#solicitante").prop("checked", true);
	}
	
	// si el cambio viene de la seleccion de otra opcion en el select entonces
	if(solChecked == true && selectedSol == -1 && ischanged == true){
	    $("#idSolicitanteJefe").val($("#idSolicitanteJefe option:eq(1)").val());
		ischanged = false;
	}
	
	$("#cambiarxml_button_cancelar").show();
	$("#solicitud_button_cancelar").hide();
	$("#modal-solicitud").modal("hide");
});


function openModalCambiarSolicitante(){
	//Se exluye la linea de totales para caso reembolso y caja chica.
	var length = $("#tablaDesglose > tbody > tr").not('.tr-totales').length;
	if (length > 0) {
		$("#mensaje-dialogo").text(MENSAJE_CAMBIO_SOLICITANTE_NOXML);
		$("#modal-solicitud").modal({
			backdrop : 'static',
			keyboard : false
		});
		$("#cambiar_solicitante_button").show();
		$("#cambiarxml_button_cancelar").hide();
		$("#solicitud_button_cancelar").show();
	}
}



