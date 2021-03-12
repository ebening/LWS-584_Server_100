function enviaProceso(idSolicitud) {
	loading(true);
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server + "/enviarAProceso",
		async : false,
		data : "idSolicitud=" + idSolicitud,
		success : function(response) {
			loading(false);
			if (response.resultado == 'true') {
				$(window).unbind('beforeunload');
				location.reload(true);
			} else if (response.resultado == 'false') {
				$("#error-head").text(ERROR);
				$("#error-body").text(response.mensaje);
				error_alert();
			}
		},
		error : function(e) {
			loading(false);
			$("#error-head").text(ERROR);
			$("#error-body").text(NO_SE_ENVIO);
			error_alert();

		},
	});
}