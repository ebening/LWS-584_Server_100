function tablaDesglose () {
	$d = $('#desglose');
	$w = $('.desglose-tabla');
	$d.click(function () {
		showVentanaDesglose();
	});
	$('.close','.modal').click(function () {
		hideVentanaDesglose();
	});
	$('.close',$w).click(function () {
		hideVentanaDesglose();
	});
	// Mostrar ventana de desglose
	function showVentanaDesglose () {
		clearVentanaDesglose();
		$w.fadeIn();
	}
	
	// Ocultar ventana desglose
	function hideVentanaDesglose() {
		$w.fadeOut();
	}
	// Clear ventana desglose
	function clearVentanaDesglose () {
		console.log('limpiando ventana');
		//$('tbody',$w).empty();
	}
	// The end
}
tablaDesglose();