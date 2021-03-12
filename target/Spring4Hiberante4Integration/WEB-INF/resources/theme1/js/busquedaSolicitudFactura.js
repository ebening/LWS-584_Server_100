$(document).ready(function() {
	//fixDate();
	log('paginate_button '+$('.paginate_button').length);
	$('body').click(function () {
		log($('.sort-date').length,2);
		$('.currencyFormat').unbind('number').number(true,2);
	});
});
// Toggle ventana de formulario
function  ventanaBusqueda (dir,id) {
	$buscar = $("#buscar");
	$mostrar = $("#mostrar-busqueda");
	
	//toggleSearch(false);
	var path = window.location.href;
	var last = path.split("/").length - 1;
	var view = path.split("/")[last];
	var open = (view != dir);
	toggleSearch(open);
	
	// Función toggle
	function toggleSearch (turn) {
		if (!turn) {
			$(id).css('height','auto');
			$buscar.show();
			$mostrar.hide();
			$(id).css('overflow','');
		}
		else {
			
			$(id).css('height',42);
			$buscar.hide();
			$mostrar.show();
			$(id).css('overflow','hidden');
			
		}
	}
	// Fin de funcion toggleSearch
	
	$mostrar.click(function () {
		toggleSearch(false);
	})

}
// BÚSQUEDA DE SOLICITUDES ------------------------------------------
function busqueda() {
	$sol = $('#busquedaSolicitudDTO');
	
	realizaBusqueda();
	
	// Realizar búsqueda --------------------------------------------
	function realizaBusqueda () {
		var compania = $('#compania').val();
		var proveedor = $('#proveedor').val();
		var idSolicitud = $('#idSolicitud').val();
		var tipoSolicitud_list = $('#tipoSolicitud_list').val();
		var start = $('#start').val();
		var end = $('#end').val();
		var estadoSolicitud = $('#estadoSolicitud').val();
		var importeMenor = $('#importeMenor').val();
		var importeMayor = $('#importeMayor').val();
		var moneda = $('#moneda').val();
		var locacion_list = $('#locacion_list').val();
		
		
		log('compania: '+compania,1);
		log('proveedor: '+proveedor,1);
		log('idSolicitud: '+idSolicitud,1);
		log('tipoSolicitud_list: '+tipoSolicitud_list,1);
		log('start: '+start,1);
		log('end: '+end,1);
		log('estadoSolicitud: '+estadoSolicitud,1);
		log('importeMenor: '+importeMenor,1);
		log('importeMayor: '+importeMayor,1);
		log('moneda: '+moneda,1);
		log('locacion_list: '+locacion_list,1);
	}
	//---------------------------------------------------------------
}
// ------------------------------------------------------------------
ventanaBusqueda();