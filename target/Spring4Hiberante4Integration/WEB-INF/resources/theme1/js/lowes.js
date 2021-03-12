//console.log('Iniciando cargador');
isComprobacionViaje = false;
function loading(flag,mensaje) {
	if (mensaje == undefined) mensaje = "Cargando";
//	console.log('Loading...');
	// C A R G A N D O ----------------------------------------------
	$cover = $('#loading-cover');
	if (flag) {
		if (!$cover.length > 0) $('#wrapper').before('<div id="loading-cover"><div class="loading-logo img-rounded">'+mensaje+'</div><div class="loading-bg"></div></div>');
		$cover = $('#loading-cover');
		$bg = $('.loading-bg');
		$logo = $('.loading-logo');
		$cover.css({
			'opacity':1,
			'display':'block'
		});
		$bg.css({
			'opacity':0,
			'display':'block'
		});
		
		$bg.animate({'opacity':.7},600, function () {});
		$logo.animate({'opacity':1},400, function () {});
		
		$(window).resize(function() {
			$cover.css('width','100%');
			$cover.css('height',$('html').css('height'));
			$bg.css('width','100%');
			$bg.css('height','100%');
			var logoX = ($(window).width() / 2) - ($logo.width()/2);
			var logoY = ($(window).height() / 2) - ($logo.height()/2);
//			console.log($(window).width()/2,($logo.width()));
			$logo.css({
				'left':logoX,
				'top':logoY
			});
			
			});
		$( window ).resize();
		
	}
	// N O - C A R G A N D O ----------------------------------------
	else {
		$cover.animate({'opacity':.0},300, function () {
			$cover.css({
				'display':'none'
			});
		});
	}
}

// DESCARGA DOCUMENTO -----------------------------------------------
function descargaDocumento(file) {
	console.log('Descargando '+file);
	 window.open(file,'_blank');
}

// Easy log ---------------------------------------------------------
function log (mensaje,tipo) {
	if (tipo == undefined) tipo = 0;
	color = new Array('black','#119eee','red','#89CC00','#CCC');
	console.log("%c "+mensaje,'color:'+color[tipo]);
}
// Archivo seleccionado label ---------------------------------------
function fileUploadedName (idUploader) {
  	$(idUploader).change(function() {
        var file = $(idUploader).val();
        fileName = new Array();
        fileName = file.split('\\');
        var name = fileName[fileName.length-1];
        $label = $('.file-selected',$(this).parent());
        $label.css({'opacity':0,'margin-left':28});
        $label.text(name);
        $label.animate({'opacity':1,'margin-left':8},450);
   		});
	}
// Fix de fecha------------------------------------------------------
function reverseFechas () {
	$columnasFecha = $('.sort-date');
	//log($columnasFecha.length,1);
	$.each($columnasFecha, function (index) {
		$f = $columnasFecha.eq(index);
		var fechaInicial = $f.text();
//		log('I:'+fechaInicial);
		var tmp = new Array();
		tmp = fechaInicial.split('/');
		var year = parseInt(tmp[0]).toString();
		tmp[0] = year;
		
		//log(year.length);
		if (year.length == 4) {
			tmp[0].replace(' ','');
			fechaFinal = tmp.reverse().join('/');
//			log('F:'+fechaFinal+'  ',1);
//			log('------------------',4);
			$f.text(fechaFinal);
		}
		
	});
}
//-------------------------------------------------------------------
function fixDate() {
	$('body').click(function () {
		//log('reverse',2);
		reverseFechas();
		resetColResizable();
	});
	reverseFechas();
}
//-------------------------------------------------------------------
function resetNumber ($id) {
	$id.unbind('number').number(true,2);
}
