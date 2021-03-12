$row = $('.t-row');
$lineas = $('.lineas-table');
lines = new Object();
line = new Object();
errores = 0;
times = 0;
importeAnticipo = 0;
transfiereDeposito = 0;
contador = 0;
var subTotal,fechaEmision,folio,folioFiscal,serie,iva,tua,ish,yri,ieps,moneda,otrosImpuestos,sumaOtrosImpuestos,total,comercio,fechaGasto,proveedor,proveedorLibre;

//$row.hide();
// Función principal ------------------------------------------------
function comprobacionAnticipoViaje() {
	$(document).ready(function() {
		
		$('#comprobante-subtotal').unbind('number').number(true,2);
		$('#comprobante-total').number(true,2);
		calculaTotales();
//		console.log("Comrpobación anticipo viaje JS");
		//agregaLineaAjax();
		
		$rowsCargadas = $('.t-row');
		totalCargadas = $rowsCargadas.length;
		log("Habemus "+totalCargadas,1);
		$.each($rowsCargadas,function (index) {
			$rowCargada = $rowsCargadas.eq(index);
			checkReacciones(index,false);
		});
		
		$('.linea-locacion select').change();
		callFactura();
		$("#pdf").change(function () {
			var ext = $('#pdf').val().split('.').pop().toLowerCase();
			if ($.inArray(ext, ['pdf']) == -1 && ext != "") {
				$('.file-pdf').removeClass('validado-pdf');
				$("#pdf").addClass("errorx");
			}
			else {
				$('.file-pdf').addClass('validado-pdf');
				$("#pdf").removeClass("errorx");
				}
		});
		
		depositoBoton();
		bloqueGastos();
		actualizaBloqueGastos();
		
		//Si tiene anticipo se deshabilitan los inputs
		if(conAnticipo == 'true')
			disabledEnabledFields(true);
		else
			disabledEnabledFields(false);
		
	});
}
// Call Factura -----------------------------------------------------
function callFactura() {
	$facturas = $('.linea-factura-comprobante');
	$.each($facturas, function (index) {
		$f = $('a',$facturas.eq(index)).first();
		$c = $('a',$facturas.eq(index)).last();
		
		$f.unbind('click');
		$c.unbind('click');
		$f.click(function () {
			console.log("Factura");
			//sendFactura($f.parent().parent());
//			console.log($(this).index());
			//Clean data modal-factura
			$('input','#modal-factura').val('');
			$('#factura-moneda').attr('disabled','disabled');
			$('.palomita').css('display','none');
			$('.validado-xml').removeClass('validado-xml');
			$('.validado-pdf').removeClass('validado-pdf');
			$('#guarda-factura').show();
			$('#factura-ok-alert').hide();
			
			$('#file').val('');
			$('#guarda-factura').unbind('click').click(function () {
				guardaFactura();
				//guardaFactura();
			});
			$rowSelected = $(this).parent().parent().parent();
		});
		
		$c.click(function () {
			console.log("Comprobante");
			$rowSelected = $(this).parent().parent().parent();
			validacionesComprobante();
			$('#guarda-comprobante').unbind('click').click(function () {
				guardaComprobante();
			});
			
			$("#comprobante-total").blur(function () {
				$("#comprobante-subtotal").val($("#comprobante-total").val());
			});
			
			$total = $('.linea-total',$rowSelected);
			$total.removeClass('moneyFormat').unbind('number').number(true,2);
			$total.text($total.text());
			$total.number(true,2);
			log ("L: "+$total.length,1);
		
		});
	});
	
	// Botón eliminar row
	$deletes = $('.linea-eliminar button', '#gastos-viaje-data');
	$.each($deletes, function (index) {
		$d = $deletes.eq(index);
		$d.unbind('click').click(function (index) {
			eliminaLineaAjax($(this).parent().parent().index());
		});
	});
	
//	console.log('Actualizado:  '+$facturas.length+' elementos');
}

// Nueva Línea ------------------------------------------------------
//function agregaLinea(data) {
//	// console.log("[Creando nueva línea]");
//	var trs = $('tr', $lineas).length - 2;
//	var ultimo = trs - 1;
//	// console.log("Ultimo TR",ultimo);
//	$last = $('.t-row', $lineas).eq(ultimo);
//
//	$linea = $last.clone();
//	$last.after($linea);
//	$linea.fadeIn();
//	$linea.removeClass('.t-row').addClass('linea-row');
//	$row.remove();
//}
// -----------------------------------------------------------------
function agregaLineaAjax () {
	
	data = {
			'compania': $("#compania").val(),
			'formaPago': $("#formaPago").val(),
			'locacion': $("#locacion").val(),
			'moneda': $("#moneda").val(),
			'viajeDestino': $("#destinos").val(),
			'viajeMotivo': $("#motivos").val(),
			'otroDestino': $("#otroDestino").val(),
			'otroMotivo': $("#otroMotivo").val(),
			'fechaInicio': $("#fecha-salida").val(),
			'fechaRegreso': $("#fecha-regreso").val(),
			'numeroPersonas': $("#numero-personas").val(),
			'importe': $("#importe-reembolso").val(),
//			'preguntaExtranjero1':ieps,
//			'preguntaExtranjero2':iva,
//			'idViajeTipoAlimentos':idLocacion,
//			'idMoneda':moneda,
			};
	
	var datos = JSON.stringify(data);
	
	var tipoSolicitud = 8;
	var length = $("#gasto-viaje-desglose > tbody > .t-row").length;
	
	if(validaInfoAnticipo()){
		 $.ajax({
		 		type : "GET",
		 		cache : false,
		 		url : url_server+"/agregaLineaGastosViaje",
		 		async : true,
		 		data : "numrows="+length+"&tipoSolicitud=" + tipoSolicitud + "&datos=" + datos,
		 		success : function(data) {
		 			$('.totales','#gastos-viaje-data').before(data);
		 			newRow = $('.t-row','#gastos-viaje-data').length-1;
		 			
		 			checkReacciones(newRow,false);
		 			$('.linea-locacion select').eq(newRow).change();
		 			
		 			callFactura();
		 			recalcula();
		 			actualizaBloqueGastos();
		 			
		 			$creado = $('.t-row').eq(newRow);
		 			$('.date-grid-container input',$creado).datepicker({
		 				  format: 'dd/mm/yyyy',
		 				  language: 'es',
		 				 // endDate: '+2d',
		 				  autoclose: true,
		 				  todayHighlight: true,
		 			});
		 			
		 			resetColResizable();
		 		},
		 		error : function(e) {
		 			$("#error-head").text(ERROR);
						 $("#error-body").text(ERROR);
					     error_alert();
		 			
		 		},
		 	});
	}
}

//------------------------------------------------------------------
function eliminaLineaAjax (id) {
	log('DELETE '+id,2);
	loading(true);
	$.ajax({
		type : "POST",
		cache : false,
		url : url_server+"/eliminaLineaGastosViaje?id="+id,
		async : true,
		//data : data,
		success : function(data) {
			log('Elemento borrado, quedan '+data.total+","+data.pre,1);
			$('.t-row','#gastos-viaje-data').eq(id).fadeOut(300,function () {
				loading(false);
				$(this).remove();
				recalcula();
				resetColResizable();
				calculaTotales();
			});
			
		},
		error : function(e) {
			loading(false);
			log('No se pudo borrar el elemento '+id,2);
		},
	}); 
	
}
//------------------------------------------------------------------
function recalcula () {
	$linea = $('.linea-linea','#gastos-viaje-data');
	$.each($linea,function (index) {
		$linea.eq(index).text(index+1);
	});
}
//------------------------------------------------------------------
comprobacionAnticipoViaje();

//------------------------------------------------------------------
// recibe el id del elemento
function actualizarCuentas(idLoc) {
	var id = idLoc;
	var idLocacion = document.getElementById(id).value;
	$.ajax({
				type : "GET",
				cache : false,
				url : path+"/seleccionLocacion",
				async : true,
				data : "idElemento=" + id + "&idLocacion="+idLocacion
						+ "&tipoSolicitud=" + tipoSolicitudGlobal,
				success : function(result) {
                 	document.getElementById(result.idElemento).innerHTML=result.options;
                 	document.getElementById(result.idElemento).disabled = false;
                 	$s = $("select[id='"+idLoc+"']");
                 	idRow = $s.parent().parent().index();
                 	contador++;
                 	if(contador == totalCargadas)
                 		getCCByRowTable();
                 	
				},
				error : function(e) {
					console.log('Error: ' + e);
				},
			});
}
//------------------------------------------------------------------
/*
 * Manda el objeto con datos al controlador para que sea guardado
 * en sesión.
 * */ 
function enviaLinea(data,tipo) {
	log('E N V Í A   L Í N E A -----------------------------------',1);
	//var idLocacion = document.getElementById(id).value;
	loading(true,'Enviando línea');
	var datos = JSON.stringify(data);
	var numLinea = $rowSelected.index();
	
	var numLinea = $rowSelected.index();
	
	//log('Se procede a enviar la línea');
	loading(true);
	$.ajax({
			url : path+"/enviaLinea?idSolicitud="+idSolicitud+"&tipoSolicitud="+tipoSolicitudGlobal+"&row="+numLinea+"&conComp="+tipo,
			data: data,
			cache: false,
			processData: false,
			type: 'POST',
			
			contentType : 'application/json; charset=utf-8',
		    dataType : 'json',

            data:datos,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
			//async : true,
			success : function(result) {
				loading(false);
				//log('Resultado '+result,1);
	          	//document.getElementById(result.idElemento).innerHTML=result.options;
	          	//document.getElementById(result.idElemento).disabled = false;
	          	//log('Checando reacciones');
				calculaTotales();
				$td = $('.linea-factura-comprobante',$rowSelected);
				
				
				if (tipo == 1) { // F A C T U R A
	          		//log($('a',$td).first().text());
	          		$td.text($('a',$td).first().text());
	          		$("#guarda-factura").hide();
	          		$f = $("#modal-factura");
					$("#factura-ok-alert",$f).fadeIn();
					$("#factura-ok-body",$f).text("Línea guardada correctamente");
	          	}
				if (tipo == 2) { // C O M P R O B A N T E
	          		//log($('a',$td).eq(1).text());
	          		$td.text($('a',$td).eq(1).text());
	          		$("#guarda-comprobante").hide();
	          		$f = $("#modal-comprobante");
					$("#comprobante-ok-alert",$f).fadeIn();
					$("#comprobante-ok-body",$f).text("Línea guardada correctamente");
	          	}
	          	
				//$('.close','#modal-factura').click();
				$('.linea-desglosar input[type=checkbox]',$rowSelected).prop("disabled", false);
				$('.linea-factura-comprobante').addClass("completed");
	          	//log(result+' Rows guardadas',3);
				
			},
			error : function(e) {
				loading(false);
	          	log('No se envió la línea',2);
				console.log('Error: ' + e);
			},
		});
}
//------------------------------------------------------------------
function checkReacciones(id,saveData) {
	
	// Comprobar BLD
	$row = $('.t-row','#gastos-viaje-data').eq(id);
	
	$( ".linea-concepto select",$row).unbind('change').change(function() {
		valor = $('option:selected',$(this)).val();
		$bld = $('.linea-bld select',$(this).parent().parent());
	    $desglose = $('.linea-desglosar input[type=checkbox]',$(this).parent().parent());
	      
	      // BLD
	      if (valor == conceptoAlimentos) {
	    	  $bld.prop('disabled', false);
	      }
	      else {
	    	  $bld.val(-1);
	    	  $bld.prop('disabled', 'disabled');
	      }
	      
	      if (valor == -1) {
	    	  //$desglose.prop('disabled', 'disabled');
	    	  $desglose.attr('checked', false);
	    	  $checkDesglose.change();
	      }
	      else {
	    	  //$desglose.prop('disabled', false);
	      }
	     save(id, "Guarda concepto");
	});
	
	$('.date-grid-container input', $row).change(function () {
		save(id, "Guarda Fecha Gasto");
	});
	
	$('.linea-ciudad input',$row).blur(function () {
		save(id, "Guarda Ciudad Texto");
	});
	
	$('.linea-numero-personas input',$row).blur(function () {
		save(id, "Guarda número de personas");
	});
	
	$('.linea-segunda-autorizacion input[type=checkbox]',$row).click(function () {
		save(id, "Guarda autorización");
	});
	
	$desglose = $('.linea-desglose input[type=checkbox]',$row);
	$desglose.click(function () {
		if ($desglose.is(':enabled')) {
			save(id, "Guarda desglose");
		}
	});
	
	
	
	
	// Comprobar "Tiene desglose"
	$checkDesglose = $(".linea-desglosar input");
	
	$checkDesglose.unbind('change').change(function() {
		checkId = $(this).parent().parent().index();
//		log("["+checkId+"]Memento mori",1);
		save(checkId, "Guarda desglosar");
		$personas = $('.linea-numero-personas input',$(this).parent().parent());
		$btnIr = $('.linea-ir-desglose button',$(this).parent().parent());
		$link = $('.linea-ir-desglose a',$(this).parent().parent());
		$label = $('.linea-ir-desglose label',$(this).parent().parent());
//		console.log($personas.length);
		
        if($(this).is(":checked")) {
        	$personas.attr('name',$personas.val());
        	$personas.val($("#numero-personas").val());
//        	console.log("Tiene desglose");
        	$personas.prop('disabled', false);
        	$btnIr.prop('disabled', false);
        	$link.show();
        	$label.hide();
        	$('.palomita',$(this).parent().parent()).show();
        }
        else {
//        	console.log("Desdesglosado...");
        	$personas.prop('disabled', 'disabled');
        	$btnIr.prop('disabled', 'disabled');
        	$personas.val($personas.attr('name'));
        	$link.unbind('click');
        	$link.hide();
        	$label.show();
        	$('.palomita',$(this).parent().parent()).hide();
        }
        $('#textbox1').val($(this).is(':checked'));    
    });
	
	$( ".linea-cuenta-contable select",$row).change(function () {
		save(id, "Guarda cuenta contable");
	});
	
	$( ".linea-bld select",$row).change(function () {
		save(id, "Guarda BLD");
	});
	
	
	if (saveData) { 
		
		$checkDesglose.change();
		}
	$( ".linea-concepto select",$row).change();
//	$('.linea-linea').unbind('click').click(function (index) {
//		save($(this).parent().index());
//	});
	
}
//valXML -----------------------------------------------------------
function valXML(){
	//log("Comenzando a validar XML");
	
	//$("#loaderXML").show(); // poner el loader.gif

	if(document.getElementById("file").files.length != 0){
		$("#error-alert").hide();
		var data = new FormData();
		jQuery.each(jQuery('#file')[0].files, function(i, file) {
			data.append('file-'+i, file);
			});
		
		loading(true,"Validando XML");
		  
		if(data){
			jQuery.ajax({
				url : path+"/resolverXML",
				data: data,
				cache: false,
				contentType: false,
				processData: false,
				type: 'POST',
				success: function(data){
					loading(false);
					$("#loaderXML").hide();
					$("#file").removeClass("errorx");
					if(data.validxml == "true"){
						$("#compania").val(data.idCompania);
						//$("#proveedor").val(data.idProveedor);
						if(data.idMoneda == "null"){
							$("#factura-moneda").prop("disabled",false);
						}else{
							$("#factura-moneda").val(data.idMoneda);
						}
						
						$("#folioFiscal").val(data.folioFiscal);
						$("#total").val(data.total);
						$("#strSubTotal").val(data.subTotal);
						$("#sppc").val(data.subTotal);
						$("#rfcEmisor").val(data.rfcEmisor);
						$("#serie").val(data.serie);
						$("#folio").val(data.folio);
						$("#iva").val(data.iva);
						$("#otrosCargos").val(data.otrosCargosImpuestos);
						$("#ieps").val(data.ieps);
						$("#tipoFactura").val(data.tipoFactura);
						if(data.incluyeRetenciones == 'true'){
							$("#conRetenciones").prop("checked",true);	
						}
					        
						$("#iva_retenido").val(data.ivaRetenido);
						$("#isr_retenido").val(data.isrRetenido);
						$("#fecha").val(data.fechaEmision);	
						
						if(data.wsmensaje != 0){
							if(data.wscode == 0){
								$("#ok-head-factura").text(FACTURA_VALIDA);
								$("#ok-body-factura").text(data.wsmensaje);
								ok_alert_modal();
								
								addXMLData(data);
								$('.file-xml').addClass('validado-xml');
								$('.palomita').css('display','inline');
								$("#validarXMLb").removeClass("errorx");
								log('✓ XML validado',3);
							}else{
								log("No se pudo cargar XML ",2);
								$(".file-xml").addClass("errorx");
								$("#error-head-factura").text(ERROR);
								$("#error-body-factura").text(data.wsmensaje);
								error_alert_modal();
								errores++;
							}
							refreshTooltip();
						}else{
							$(".file-xml").addClass("errorx");
							$("#error-head-factura").text(ERROR);
							$("#error-body-factura").text(data.wsmensaje);
							error_alert_modal();
							$("#file").val(null);
						}
					}else{
			      	    $(".file-xml").addClass("errorx");
					    $("#error-head-factura").text(ERROR);
					    $("#error-body-factura").text(data.wsmensaje);
					    error_alert_modal();
					    $("#file").val(null);
			        }
				}
			});
		}
		// ------ 
		}else{
			$("#loaderXML").hide();
			$(".file-xml").addClass("errorx");
			$("#error-head-factura").text(ERROR);
			$("#error-body-factura").text(COMPLETE);
			error_alert_modal();
		}
	}
//  FIN valXML ------------------------------------------------------

//Add data from XML -------------------------------------------------
function addXMLData (xmlData) {
	subTotal = xmlData.subTotal;
	fechaEmision = xmlData.fechaEmision;
	folio = xmlData.folio;
	folioFiscal = xmlData.folioFiscal;
	serie = xmlData.serie;
	iva = xmlData.iva;
	tua = xmlData.tua;
	ish = xmlData.ish;
	yri = xmlData.yri;
	otrosImpuestos = xmlData.otrosCargos;
	sumaOtrosImpuestos = xmlData.otrosCargosImpuestos;
	ieps = xmlData.ieps;
	moneda = xmlData.idMoneda;
	total = xmlData.total;
	comercio = folio+"/"+folioFiscal;
	
	if(xmlData.faltaProveedor == "true"){
		proveedorLibre = xmlData.idProveedorLibre;
	}
	else{
		proveedor = xmlData.idProveedor;
	}
	
	$('.factura-comercio input').val(comercio);
	$('.factura-folio-fiscal input').val(folioFiscal);
	$('.factura-serie input').val(serie);
	$('.factura-folio input').val(folio);
	$('.factura-fecha input').val(fechaEmision);
	$('.factura-subtotal input').val(subTotal);
	$('.factura-moneda select').val(moneda);
	$('.factura-iva input').val(iva);
	$('.factura-eps input').val(ieps);
	$('.factura-otros-impuestos input').val(sumaOtrosImpuestos);
	$('.factura-total input').val(total);
	line.subTotal = subTotal;
	line.fechaGasto = fechaEmision;
	line.iva = iva;
	line.tua = tua;
	line.ish = ish;
	line.yri = yri;
	line.otrosImpuestos = otrosImpuestos;
	line.ieps = ieps;
	line.total = total;
	line.comercio = comercio;
	lineData($rowSelected);
	
	$('.linea-fecha-factura',$rowSelected).text($('.factura-fecha input').val());
	$('.linea-comercio',$rowSelected).text($('.factura-comercio input').val());
	$('.linea-subtotal',$rowSelected).text($('.factura-subtotal input').val());
	$('.linea-iva',$rowSelected).text($('.factura-iva input').val());
	$('.linea-ips',$rowSelected).text($('.factura-eps input').val());
	$('.linea-otros-impuestos',$rowSelected).text($('.factura-otros-impuestos input').val());
	$('.linea-total',$rowSelected).text($('.factura-total input').val());
	$('.linea-total',$rowSelected).unbind('number').number(true,2);
	
	resetNumber($('.linea-subtotal',$rowSelected));
	resetNumber($('.linea-iva',$rowSelected));
	resetNumber($('.linea-ips',$rowSelected));
	resetNumber($('.linea-otros-impuestos',$rowSelected));
	
	calculaTotales();
	
	log('Cargados datos del XML');
	actualizaBloqueGastos();
//	console.log("Datos del XML cargados");
}
// ------------------------------------------------------------------
function lineData (rowData) {
	//log("Leyendo datos de la row");
	locacion = $('.linea-locacion select',$rowSelected).val();
	concepto = $('.linea-concepto select',$rowSelected).val();
	viajeTipoAlimento = $('.linea-bld select',$rowSelected).val();
	cuentaContable = $('.linea-cuenta-contable select',$rowSelected).val();
	ciudad = $('.linea-ciudad input',$rowSelected).val();
	segundaAprobacion = $('.linea-segunda-aprobacion input',$rowSelected).is(":checked");
	desglosar = $('.linea-desglosar input',$rowSelected).is(":checked");
	numeroPersonas = $('.linea-numero-personas input',$rowSelected).val();
	//var concepto = $('.linea-ciudad input',$rowSelected).val();
	line.locacion = locacion;
	line.concepto = concepto;
	line.viajeTipoAlimento = viajeTipoAlimento;
	line.cuentaContable = cuentaContable;
	line.ciudad = ciudad;
	line.segundaAprobacion = segundaAprobacion;
	line.desglosar = desglosar;
	if (numeroPersonas == null || numeroPersonas == "") numeroPersonas = 0;
	line.numeroPersonas = numeroPersonas;
	
//	log("Datos del row cargados");
//	log("Datos enviados");
	//log(line);
	//enviaLinea(formateaObjeto());
}
// ------------------------------------------------------------------
function formateaObjeto() {
	dataLine = {
		'ciudadTexto':ciudad,
		'comercio':comercio,
		'idViajeConcepto':concepto,
		'idViajeTipoAlimento':viajeTipoAlimento,
		'idCuentaContable':cuentaContable,
		'desglosar':desglosar,
		'fecha_gasto':fechaEmision,
		'folio':folio,
		'folioFiscal':folioFiscal,
		'ieps':ieps,
		'iva':iva,
		'tua':tua,
		'ish':ish,
		'yri':yri,
		'idLocacion':locacion,
		'idMoneda':moneda,
		'numeroPersonas':numeroPersonas,
		'otrosImpuestos':otrosImpuestos,
		'segundaAprobacion':segundaAprobacion,
		'serie':serie,
		'subTotal':subTotal,
		'total':total,
		'proveedor':proveedor,
		'proveedorLibre':proveedorLibre
		};
	return dataLine;
	
}
// ------------------------------------------------------------------
// G U A R D A - L I N E A ------------------------------------------
function guardaFactura () {
	errores = 0;
	
	//Validación
	if (document.getElementById("pdf").files.length == 0) {
		$("#pdf").addClass("errorx");
		error = true;
		//log('PDF no seleccionado ?',2);
		errores++;
	} else {
		$("#pdf").removeClass("errorx");
		//log('PDF seleccionado',1);
	}
	if (document.getElementById("file").files.length == 0) {
		$("#file").addClass("errorx");
		//log('XML no seleccionado ?',2);
		error = true;
		errores++;
	} else {
		$("#file").removeClass("errorx");
		//log('XML seleccionado',1);
	}
	
	if ($("#factura-moneda").val() < 1) {
		$("#factura-moneda").addClass("errorx");
		error = true;
		errores++;
	} else {
		$("#factura-moneda").removeClass("errorx");
	}
	
	if ($('.validado-xml').length > 0) {
		//log('Validación del XML ha sido verificada',3);
	}
	else {
		$("#validarXMLb").addClass("errorx");
		//log('No ha sido validado el XML',2);
		errores++;
		}
	
	if (errores > 0) {
		log(errores+" Errores",2);
		$("#error-head-factura").text(ERROR);
		$("#error-body-factura").text(COMPLETE);
		error_alert_modal();
		return false;
	}
	else {
		//log('Procede a ejecutar proceso final');
		guardarArchivos(true);
	}
}
//-------------------------------------------------------------------

// C O M P R O B A N T E --------------------------------------------
function validacionesComprobante () {
	
	$('#file-comprobante').val('');
	$('#comprobante-comercio').val('');
	$('#comprobante-folio').val('');
	$('#comprobante-fecha').val('');
	$('#comprobante-subtotal').val('');
	$('#comprobante-moneda').val(-1);
	$('#comprobante-total').val('');
	$('#guarda-comprobante').show();
	$('#comprobante-ok-alert').hide();
	$('#tiene-comprobante').attr('checked', false);
	
	$file = $('#file-comprobante');
	$file.removeAttr('disabled','true');
	$('#upload-comprobante').css('opacity',1);
	
	
	$('#tiene-comprobante').change(function() {
		if($("#tiene-comprobante").is(":checked")){
			//log('Tiene comprobante',1);
			$file.attr('disabled','false');
			$('#upload-comprobante').css('opacity',.5);
			$('#file-comprobante').removeClass('errorx');
			$('#file-comprobante').val('');
		}else{
			//log('No tiene comprobante',2);
			$file.removeAttr('disabled','true');
			$('#upload-comprobante').css('opacity',1);
			}
		});
	}
	$('#tiene-comprobante').change();
//-------------------------------------------------------------------
	function guardaComprobante () {
		errores = 0;
		
		if(!$("#tiene-comprobante").is(":checked")) {
			if (document.getElementById("file-comprobante").files.length == 0) {
				$("#file-comprobante").addClass("errorx");
				//log('Archivo no seleccionado ?',2);
				error = true;
				errores++;
			} else {
				$("#file-comprobante").removeClass("errorx");
				
				//log('Archivo seleccionado',1);
			}
			
		}
		
		// Valida nulo -------------------------------
		function validaNulo(id) {
			$item = $(id);
			var eva = $item.val();
			if (eva == "" || eva == 0) {
				$item.addClass("errorx");
				errores ++;
				//log(id+' no capturado',2);
			}
			else {
				$item.removeClass("errorx");
				//log(id+' correcto',1);
			}
		}
		// Fin valida nulo ---------------------------
		validaNulo('#comprobante-comercio');
		validaNulo('#comprobante-folio');
		validaNulo('#comprobante-fecha');
		validaNulo('#comprobante-subtotal');
		validaNulo('#comprobante-comercio');
		validaNulo('#comprobante-total');
		
		if ($("#comprobante-total").val() == 0) {
			$("#comprobante-total").addClass("errorx");
			errores ++;
		}
		else {
			$("#comprobante-total").removeClass("errorx");
		}
		
		
		if (errores == 0) {
			//log('Proceder con el guardado',3);
			addFormData();
			guardarArchivos(false);
		}
		else {
			//log(errores+' errores',2);
		}
	}
	
//Add Form from XML -------------------------------------------------
	function addFormData () {
		
		subTotal = $('#comprobante-subtotal').val();
		//log($('#comprobante-fecha').val(),1);
		fechaEmision = $('#comprobante-fecha').val();
		folio =  $('#comprobante-folio').val();
		moneda = $('#comprobante-moneda').val();
		total = $('#comprobante-total').val();
		comercio = $('#comprobante-comercio').val();
		
		locacion = $('.linea-locacion select',$rowSelected).val();
		bld = $('.linea-bld select',$rowSelected).val();
		concepto = $('.linea-concepto select',$rowSelected).val();
		cuentaContable = $('.linea-cuenta-contable select',$rowSelected).val();
		ciudad = $('.linea-ciudad input',$rowSelected).val();
		segundaAprobacion = $('.linea-segunda-aprobacion input',$rowSelected).is(":checked");
		desglosar = $('.linea-desglosar input',$rowSelected).is(":checked");
		numeroPersonas = $('.linea-numero-personas input',$rowSelected).val();
		folioFiscal = '';
		ieps = 0;
		iva = 0;
		otrosImpuestos = 0;
		lineData($rowSelected);
			
		$('.linea-fecha-factura',$rowSelected).text(fechaEmision);
		$('.linea-fecha-gasto input',$rowSelected).val(fechaEmision);
		$('.linea-comercio',$rowSelected).text(comercio);
		$('.linea-subtotal',$rowSelected).text(subTotal);
		$('.linea-total',$rowSelected).text(total);
		
		//$('.currencyFormat').unbind('number').number( true, 2 );
		log('S: '+ieps,3);
		$('.linea-subtotal',$rowSelected).unbind('number').number( true, 2 );
		$('.linea-total',$rowSelected).unbind('number').number( true, 2 );
		$('.linea-otros-impuestos',$rowSelected).unbind('number').number( true, 2 );
		$('.linea-ips',$rowSelected).unbind('number').number( true, 2 );
		$('.linea-iva',$rowSelected).unbind('number').number( true, 2 );
		
		//console.log("Datos del XML cargados");
	}
// ------------------------------------------------------------------
// G U A R D A D O - D E - A R C H I V O S
	
function guardarArchivos(conComprobante) {
	log('G U A R D A N D O   A R C H I V O S ---------------------',1);
	//log('Iniciando guardar archivos. Factura = '+conComprobante);
	var data = new FormData();
	var conComp = conComprobante;
	if (conComp) {
		log("PDF: "+$('#pdf').length);
		$.each($('#pdf')[0].files, function (i, file) {
			data.append('file-' + i, file);
			//log('file-' + i, 1);
		});
		log("FILE: "+$('#file').length);
		$.each($('#file')[0].files, function (i, file) {
			data.append('file-' + i, file);
			//log('file-' + i, 3);
		});
	} else {
		if(!$("#tiene-comprobante").is(":checked")){
			log("COMPROBANTE: "+$('#file-comprobante').length);
			$.each($('#file-comprobante')[0].files, function (i, file) {
				data.append('file-' + i, file);
				//log('file-' + i, 2);
			});
		}
		else {
			log('Sin archivos que guardar',3);
			enviaLinea(formateaObjeto(),2);
		}
	}
	
	data.append('conComp', conComp);
	loading(true);
	if (data) {
		jQuery.ajax({
			url : url_server+"/guardaArchivosComprobacion",
			data : data,
			cache : false,
			contentType : false,
			processData : false,
			type : 'POST',
			success : function (data) {
				if(data > 0){
					log('✓ '+data+' archivo(s) guardado(s)',3);
		 			if (conComp) {
		 				enviaLinea(formateaObjeto(),1);
		 			}
		 			else {
		 				enviaLinea(formateaObjeto(),2);
		 			}
		 			
					return true;
				}
				else{
					return false;
				}		
			},
	 		error : function(e) {
	 			log('Arvchivos no enviados',2);
	 			loading(false);
//	 			$("#error-head").text(ERROR);
//	 			$("#error-body").text(ERROR);
//				error_alert();
	 			
	 		}
		});
	}
	else{
		return false;
	}
}
// Fin de guardado de archivos --------------------------------------

// G U A R D A - C A M B I O ----------------------------------------
function save(idRow, mensaje) {
	log('---------------------------',4);
	if (mensaje == undefined ||  mensaje == "") {mensaje = "Actualizar"}
	//var data = new FormData();
	$row = $('.t-row','#gastos-viaje-data').eq(idRow);
	
	// Carga de datos
	fc = $('.linea-factura-comprobante',$row).text();
	if (fc == 'Factura') { tipo = true; }
	else if (fc == 'Comprobante') { tipo = false; }
	else tipo = undefined;
	
	fecha_factura = $('.linea-fecha-factura',$row).text();//Temporalmente la otra
	fecha_gasto = $('.linea-fecha-gasto input',$row).val();
 	comercio = $('.linea-comercio',$row).text();
	idViajeConcepto = parseInt($('.linea-concepto select',$row).val());
	idViajeTipoAlimento = parseInt($('.linea-bld select',$row).val());
	ciudad = $('.linea-ciudad input',$row).val();
	subTotal = moneyFloat($('.linea-subtotal',$row).text());
	iva = moneyFloat($('.linea-iva',$row).text());
	//tua = parseFloat($('.linea-tua',$row).text());
	//ish = parseFloat($('.linea-ish',$row).text());
	//yri = parseFloat($('.linea-yri',$row).text());
	//ieps = parseFloat($('.linea-ips',$row).text());
	sumaOtrosImpuestos = moneyFloat( $('.linea-otros-impuestos',$row).text());
	total = moneyFloat($('.linea-total',$row).text());
	idLocacion = parseInt($('.linea-locacion select',$row).val());

	//idMoneda = $('.',$row);
	idCuentaContable = parseInt($('.linea-cuenta-contable select',$row).val());
	//idBld = $('.linea-bld select',$row).val();
	desglosar = $('.linea-desglosar input',$row).is(":checked");
	numeroPersonas = parseInt($('.linea-numero-personas input',$row).val());
	//log(numeroPersonas,1)
	segundaAutorizacion = $('.linea-segunda-autorizacion  input',$row).is(":checked");

	if (iva == '' || iva == undefined) iva = 0;
//	if (tua == '' || tua == undefined) tua = 0;
//	if (ish == '' || ish == undefined) ish = 0;
//	if (yri == '' || yri == undefined) yri = 0;
	if (ieps == '' || ieps == undefined) ieps = 0;
	if (total == '') total = 0;
	if (subTotal == '') subTotal = 0;
	if (otrosImpuestos == '') otrosImpuestos = 0;
	if (numeroPersonas == '' || numeroPersonas == null || numeroPersonas == undefined || numeroPersonas == ' ' || !$.isNumeric(numeroPersonas) ) numeroPersonas = $("#numero-personas").val();
	//console.log(comercio+",",idViajeConcepto+",",ciudad+",",subTotal+","+iva+",",ieps+",",otrosImpuestos+",",total+",",idLocacion+",",idCuentaContable+",",desglosar+",",numeroPersonas+",",segundaAutorizacion+",",fecha_gasto+",",fecha_factura);
	
//	{"ciudadTexto":"",
//		"comercio":"146/110A8A81-BA19-45F9-95FC-BDE49669656E",
//		"idViajeConcepto":"-1",
//		"idCuentaContable":"-1",
//		"desglosar":false,
//		"fecha_gasto":"05/12/2015",
//		"folio":"146",
//		"folioFiscal":"110A8A81-BA19-45F9-95FC-BDE49669656E",
//		"ieps":"0.00",
//		"iva":"480.00",
//		"idLocacion":"1",
//		"idMoneda":"8",
//		"numeroPersonas":0,
//		"otrosImpuestos":0,
//		"segundaAprobacion":false,
//		"subTotal":"3000.00",
//		"total":"3360.00"
//			}
	
	data = {
			'comercio':comercio,
			'ciudadTexto':ciudad,
			'idViajeConcepto':idViajeConcepto,
			'idViajeTipoAlimento':idViajeTipoAlimento,
			'idCuentaContable':idCuentaContable,
			'desglosar':desglosar,
			'fecha_gasto':fecha_gasto,
			'fecha_factura':fecha_factura,
			'folio':folio,
			'folioFiscal':folioFiscal,
			'ieps':ieps,
			'iva':iva,
			'idLocacion':idLocacion,
			'idMoneda':moneda,
			'numeroPersonas':numeroPersonas,
			'otrosImpuestos':otrosImpuestos,
			'segundaAprobacion':segundaAutorizacion,
			'subTotal':subTotal,
			'total':total,
			'proveedor':proveedor,
			'proveedorLibre':proveedorLibre,

//			'idViajeConcepto':idViajeConcepto,
//			'serie':serie,
			};

	
	//log(datos);
	var datos = JSON.stringify(data);
	
	var css = "background-color:#119eee; color:white; padding:0px 2px;";
	console.log("%c"+idRow, css, mensaje);
	
//	console.log("%c["+idRow+"] %cActualizar",'color:blue','color:red');
	
	$.ajax({
		url : path+"/actualizaLineaGastosViaje?idSolicitud="+idSolicitud+"&tipoSolicitud="+tipoSolicitudGlobal+"&row="+idRow,
		//data: data,
		cache: false,
		processData: false,
		type: 'POST',
		
		contentType : 'application/json; charset=utf-8',
	    dataType : 'json',

        data:datos,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
		success : function(result) {
			//Dato que se manda y se recibe
			//log('Subtotal: '+data.subTotal,1);
			//log("Subtotal: "+result.subTotal,2);
			log ("✓ Línea "+idRow+" guardada",3);
			
			$row = $('.t-row').eq(idRow);
			log(idRow+" "+result.segundaAprobacion);
			if(result.segundaAprobacion == 'true')
				$('.linea-segunda-autorizacion input',$row).prop('checked', true);
			else
				$('.linea-segunda-autorizacion input',$row).prop('checked', false);
			//log(result.mensaje);
		},
		error : function(e) {
          	log("✘ Línea "+idRow+" no guardada",2);
			//console.log('Error: ' + e);
		},
	});
}
// Fin de Guarda cambio ---------------------------------------------

// Bloque gastos ----------------------------------------------------

function depositoBoton () {
	$('#agrega-deposito').click(function () {
		//log('Modal depósito',4);
		$d = $('#deposito');
		$("#fecha-deposito").val("");
		$("#importe-deposito").val("");
		$("#guarda-deposito").removeClass('disabled');
		//$("#uploadFile2").val("");
		$("#uploadFile2").change(function () {$(this).val("")});
		//$("#uploadFile2").replaceWith($("#uploadFile2").val('').clone(true));
	});
}

function bloqueGastos() {
	importeAnticipo = $('#importe-reembolso').val();
	//log('Importe: $'+importeAnticipo);
	$("#transfiere-lowes").text("$"+importeAnticipo);
}

function actualizaBloqueGastos() {
	log('Actualiza bloque gastos',4);
	$totales = $('.linea-total');
	var totalActual = 0;
	var importeActual = 0; 
	$.each($totales,function (index) {
		//log('['+index+']-------------');
		$total = $totales.eq(index);
		totalNum = $total.text().replace(',','');
		totalNum = moneyFloat(totalNum);
		//log(totalNum);
		//log(totalNum > 0);
		
		if (totalNum > 0) {
			totalActual += parseFloat(totalNum);
		}
	});
	var deposito = moneyFloat($('#transfiere-deposito').text());
	totalActual += deposito;
	//log('Depósito: $'+deposito);
	$('#total-reembolso').text(totalActual);
	resetNumber($('#total-reembolso'));
	
	totalesMoneyFormat();
	
	//log('Total sumado:'+totalActual,2);
	importeActual = parseFloat(importeAnticipo - totalActual).toFixed(2);
	setDataGastos(importeActual);
}
// Calcula subtotales -----------------------------------------------
function calculaTotales() {
	function extraeSuma (clase) {
		sumaTotal = 0;
		$.each($(clase), function (index) {
			var num = $(clase).eq(index).text().toString();
			//log(num,1);
			if (num != "") {
				num = num.replace(',','');
				//log(num);
				sumaTotal += parseFloat(num);
			}
		});
		return sumaTotal;
	}
	$('#totalSubtotal').text(extraeSuma('.linea-subtotal'));
	$('#totalIva').text(extraeSuma('.linea-iva'));
	$('#totalIeps').text(extraeSuma('.linea-ips'));
	$('#totalOtrosImpuestos').text(extraeSuma('.linea-otros-impuestos'));
	actualizaBloqueGastos();
}
//-------------------------------------------------------------------
function totalesMoneyFormat () {
	resetNumber($("#totalSubtotal"));
	resetNumber($("#totalIva"));
	resetNumber($("#totalOtrosImpuestos"));
	resetNumber($("#totalIeps"));
	resetNumber($("#total-reembolso"));
}
//-------------------------------------------------------------------
function moneyFloat (value) {
	numero = value.replace(',','');
	float = parseFloat(numero);
	//log(value+' Float-> '+float,3);
	return float;
}
// Actualiza depósitos desde modal ----------------------------------
function actualizaDepositoComprobacion (montoDeposito) {
	//log('Actualizando datos de gastos desde Depósito',4);
	transfiereDeposito = montoDeposito;
	$transfiere = $('#transfiere-deposito');
	$transfiere.text(transfiereDeposito);
	$transfiere.unbind('number').number(true,2);
	$('#deposito').animate({'opacity':1},2000, function () {
		$('.close','#deposito').click();
	});
	//log('Actualiza depósito',4);
	actualizaBloqueGastos();
	
}
//-------------------------------------------------------------------
function setDataGastos(importeActual) {
	$transLowes = $("#transfiere-lowes");
	$transName = $("#transfiere-a");
	$transLowes.text(importeActual);
	//log("[Gasto: "+importeActual+"]",1);
	
	if (importeActual < 0) {
		$transLowes.text(0);
		//$transLowes.parent().addClass('negativo');
		//$('.signo',$transLowes.parent()).text('$-');
		$transName.text(importeActual*-1);
		}
	else {
		$transLowes.parent().removeClass('negativo');
		$('.signo',$transLowes.parent()).text('$');
		$transName.text(0.00);
		}
	
	$transLowes.unbind('number').number(true,2);
	$transName.unbind('number').number(true,2);
}
// Guarda Objeto ----------------------------------------------------

function guardaObjetoGeneral () {
	preguntaExtranjero1 = $('#compTrue1').is(":checked");
	preguntaExtranjero2 = $('#compTrue2').is(":checked");
}

// ------------------------------------------------------------------

//-----MODAL DETALLE DESGLOCE MIGUELR---------
//Botón modal detalle desglose
$(document).on('click', ".detalleDesglose", function () {
	var id = $(this).closest('tr').find('td:first').text();
	if(id > 0){
		//console.log(id);
	    $.ajax({
	    	type: "GET",
	        cache: false,
	        url: url_server+"/getDetalleDesglose",
	        async: true,
	        data: "index=" + id,
	        success: function(data) {
	        	$("#detIndex").text(id);
	        	$("#detComercio").text(data.comercio);
				$("#detSubTotal").text(data.subTotal);
				$("#detOtrosImpuestos").text(data.otrosImpuestos);
				$("#detNumeroPersonas").text(data.numeroPersonas);
	        	$('#desglose-comprobante').modal('show');
	        	
	        	if(data.desgloseCompleto == "true")
	        		$('#esDesgCompleto').show();
	        	else
	        		$('#esDesgCompleto').hide();
	        	
	        	$('#talbaDesgloseDetalle tbody > tr').remove();
	        	$("#talbaDesgloseDetalle").append(data.gridDesgloseDetalle);
	        	
	        	$("#detSaldoSubtotal").text(data.saldoSubtotal);
	        	$("#detSaldoOtrosImpuestos").text(data.saldoOtros);
	        	$("#detPendSubtotal").text(data.pendienteSubtotal);
	        	$("#detPendOtrosImpuestos").text(data.pendienteOtros);
	        	
	        	$(".currencyFormat", "#talbaDesgloseDetalle").unbind("number").number(true,2);
	        	resetNumber($("#detSubTotal"));
	        	resetNumber($("#detSaldoSubtotal"));
	        	resetColResizable();
	        },
	        error: function(e) {
	            console.log('Error: ' + e);
	        },
	    }); 
	}
});

$(document).on('blur', '.detSbts, .detOtrosImp', function() {
	calculaSaldos();
});

//Botón eliminar del grid
$(document).on('click', ".removerFila", function () {
	var numRow = $(this).closest('tr').find('td:first').children().text();
	$('#talbaDesgloseDetalle tr').eq(numRow).remove();	
	updDesgloseDetalleOnDelete(numRow);
	calculaSaldos();
	calculateLine();
});

$('#desglose-comprobante').on('hide.bs.modal', function (e) {
	
	var isCancel = $(this).data('cancel');
	var error = false;
	
	if(isCancel == 'true'){
		$('#desglose-comprobante').data('cancel','false');
	}
	else{
		var detIndex = $("#detIndex").text();	
		if(validaDesgloseDetalle()){
			$.ajax({
				type : "GET",
				cache : false,
				url :  url_server+"/saveDesgloseDetalle",
				async : true,
				data : "detIndex=" + detIndex,
				success : function (response) {
					if(response == "true"){
						$("#esDesgCompleto").show();
						$(".palomita").eq(detIndex-1).addClass("desglosado-completo");
					}
					else{
						$("#esDesgCompleto").hide();
					}
				},
				error : function (e) {
					$("#esDesgCompleto").hide();
					console.log('Error: ' + e);
				},
			});
		}else{
			$("#esDesgCompleto").hide();
			error = true;
		}
	}
	
	if (error) {
		e.preventDefault();
		$("#error-head-detalle").text(ERROR);
		$("#error-body-detalle").text(COMPLETE);
		error_alert();
		return false;
	}
	else{

	}
});

//function setNumberFormatModalDesglose () {
//	$(".currencyFormat", "#talbaDesgloseDetalle").unbind("number").number(true,2);
//
//
////	$("#detSubTotal", "#talbaDesgloseDetalle").unbind("number").number(true,2);
////	$("#detOtrosImpuestos", "#talbaDesgloseDetalle").unbind("number").number(true,2);
////	$("#detSaldoSubtotal", "#talbaDesgloseDetalle").unbind("number").number(true,2);
////	$("#detSaldoOtrosImpuestos", "#talbaDesgloseDetalle").unbind("number").number(true,2);
////	$("#detPendSubtotal", "#talbaDesgloseDetalle").unbind("number").number(true,2);
////	$("#detPendOtrosImpuestos", "#talbaDesgloseDetalle").unbind("number").number(true,2);
//}

function calculaSaldos() {
	var tmpSaldoSubtotal = 0;
	var tmpSaldoOtrosImpuestos = 0;
	var tmpPendSubtotal = 0;
	var tmpPendOtrosImpuestos = 0;

	$("#detSaldoSubtotal").val("");
	$("#detSaldoOtrosImpuestos").val("");
	$("#detPendSubtotal").val("");
	$("#detPendOtrosImpuestos").val("");

	$('.detSbts').each(function (index) {
		if($(this).val() != "")
			tmpSaldoSubtotal+= parseFloat($(this).val());
	}) 

	$('.detOtrosImp').each(function (index) {
		if($(this).val() != "")
			tmpSaldoOtrosImpuestos+= parseFloat($(this).val());
	})
	
	var detalleSubTotal = parseFloat(Number($(detSubTotal).text().replace(/[^0-9\.]+/g,"")));
	var detalleOtrosImpuestos = parseFloat(Number($(detOtrosImpuestos).text().replace(/[^0-9\.]+/g,"")));
	
	tmpPendSubtotal = detalleSubTotal - tmpSaldoSubtotal.toFixed(2);
	tmpPendOtrosImpuestos = detalleOtrosImpuestos - tmpSaldoOtrosImpuestos.toFixed(2);

	$("#detSaldoSubtotal").text(tmpSaldoSubtotal.toFixed(2));
	$("#detSaldoOtrosImpuestos").text(tmpSaldoOtrosImpuestos.toFixed(2));
	$("#detPendSubtotal").text(tmpPendSubtotal.toFixed(2));
	$("#detPendOtrosImpuestos").text(tmpPendOtrosImpuestos.toFixed(2));
}

function addRowDesgloseDetalle() {
	var length = $("#talbaDesgloseDetalle > tbody > tr").length;
	var detIndex = $("#detIndex").text();
	
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/addRowDesgloseDetalle",
		async : true,
		data : "numrows=" + length +
		"&detIndex=" + detIndex,
		success : function (response) {
			$("#talbaDesgloseDetalle").append(response.rowDetalle);
			$tr = $("#talbaDesgloseDetalle > tbody > tr").last();
			resetColResizable();
			$(".currencyFormat",$tr).number(true,2);
			//setNumberFormatModalDesglose();
			
			
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function cancelDesgloseDetalle() {
	var detIndex = $("#detIndex").text();
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/cancelDesgloseDetalle",
		async : true,
		data : "detIndex=" + detIndex,
		success : function (response) {
			if(response == "true"){
				$('#desglose-comprobante').data('cancel','true');
				$('#desglose-comprobante').modal('hide');
			}
			else{
				
			}
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function validaDesgloseDetalle() {
var error = false;
	if ($("#talbaDesgloseDetalle > tbody > tr").length == 0) {
		$("#talbaDesgloseDetalle").addClass("errorx");
		error = true;
	} else {
		$("#talbaDesgloseDetalle").removeClass("errorx");
	}

	if ($("#detSaldoSubtotal").text() == "") {
		$("#detSaldoSubtotal").addClass("errorx");
		error = true;
	} else {
		$("#detSaldoSubtotal").removeClass("errorx");
	}
	if ($("#detSaldoOtrosImpuestos").text() == "") {
		$("#detSaldoOtrosImpuestos").addClass("errorx");
		error = true;
	} else {
		$("#detSaldoOtrosImpuestos").removeClass("errorx");
	}
	if ($("#detPendSubtotal").text() != "0.00") {
		$("#detPendSubtotal").parent().addClass("errorx");
		error = true;
	} else {
		$("#detPendSubtotal").parent().removeClass("errorx");
	}
	if ($("#detPendOtrosImpuestos").text() != "0.00") {
		$("#detPendOtrosImpuestos").parent().addClass("errorx");
		error = true;
	} else {
		$("#detPendOtrosImpuestos").parent().removeClass("errorx");
	}
	
	$('.detSbts').each(function (index) {
		if ($(this).val() == "") {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  	}
	})

	$('.detOtrosImp ').each(function (index) {
		if ($(this).val() == "") {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  	}
	})
	
	$('.detConcepto').each(function (index) {
		if ($(this).val() == -1) {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  		var bldRowElement = $(this).closest('tr').find('.detBld');
	  		if($(this).val() == conceptoAlimentos){
	  			if (bldRowElement.val() == -1) {
	  				bldRowElement.addClass("errorx");
	  		  		error = true;
	  		  	} else {
	  		  	bldRowElement.removeClass("errorx");
	  		  	}
	  		}else{
	  			bldRowElement.removeClass("errorx");
	  		}
	  	}
	})

	$('.detLoc').each(function (index) {
		if ($(this).val() == -1) {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  	}
	})

	$('.detCuentasCont').each(function (index) {
		if ($(this).val() == -1) {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  	}
	})

	$('.nombresCaptura').each(function (index) {
		if ($(this).val() == "") {
	  		$(this).addClass("errorx");
	  		error = true;
	  	} else {
	  		$(this).removeClass("errorx");
	  	}
	})

	if (error) {
		$("#error-head-detalle").text(ERROR);
		$("#error-body-detalle").text(COMPLETE);
		error_alert_detalle();
		return false;
	} else {
//		$("#enviarSolicitud").prop("disabled", false);
//		disabledEnabledFields(false);
		return true;
	}
}

function error_alert_detalle(){
	$("#error-alert-detalle").fadeTo(5000, 500).slideUp(500, function(){
	    $("#error-alert-detalle").hide();
	});
}

function updDesgloseDetalleOnDelete(numRow) {
	var detIndex = $("#detIndex").text();
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/updDesgloseDetalleOnDelete",
		async : true,
		data : "numrow=" + numRow +
		"&detIndex=" + detIndex,
		success : function (response) {
//			$("#reembTot").val(response);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

/*
* Calcula linea de grid
* Estatus: OK
*/
function calculateLine() {
	console.log("tr");
	$('#talbaDesgloseDetalle tr').each(function (i, row) {
		if (i > 0) {
			//console.log(i + "<- iteracion");
			var nlinea = $(this).find(".linea").text(i);
			//console.log(nlinea + "<- texto");
		}
	});
}

function detSbtsOnChange() {
	gridDetalleOnChange($(this), "updStbsDetalle");
}

function detOtrosImpOnChange() {
	gridDetalleOnChange($(this), "updOtrosImpDetalle");
}

function detConceptoOnChange() {
	var bldRowElement = $(this).closest('tr').find('.detBld');
	if($(this).val() == conceptoAlimentos)
		bldRowElement.prop("disabled", false);
	else{
		bldRowElement.val(-1);
		bldRowElement.prop("disabled", true);
	}
	gridDetalleOnChange($(this), "updConceptoDetalle");
}

function detBldOnChange() {
	gridDetalleOnChange($(this), "updBldDetalle");
}

function detLocacionOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var detIndex = $("#detIndex").text();
	var idLocacion = $(this).val();
	var ctaContableOpt = $(this).closest('tr').find('td.ccontable');
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updLocacionDesgloseDetalle",
		async : true,
		data : "detIndex=" + detIndex +
		"&numrow=" + numRow +
		"&idLocacion=" + idLocacion,
		success : function (response) {
			if (response != "") {
				ctaContableOpt.children().empty().append(response);
			}
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function detCuentaContOnChange() {
	gridDetalleOnChange($(this), "updCuentaContDetalle");
}

function detNombreOnChange() {
	gridDetalleOnChange($(this), "updNombreDetalle");
}

function gridDetalleOnChange(row, path){
	var numRow = row.closest('tr').find('td:first').text();
	var value = encodeURIComponent(row.val());
	var detIndex = $("#detIndex").text();
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/"+path,
		async : true,
		data : "detIndex=" + detIndex +
		"&numrow=" + numRow +
		"&value=" + value,
		success : function (response) {
			//setNumberFormatModalDesglose();
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

$("#numero-personas").change(function() {
    if(parseInt($(this).val()) <= 0)
		$(this).val(1);
});

function validaComprobacionAnticipoViaje() {
	$(window).unbind('beforeunload');
	
	
	var error = false;
	
	$files = $(".linea-factura-comprobante");
	$conceptos = $(".linea-concepto select");
	$locaciones = $(".linea-locacion select");
	$cuentasContables = $(".linea-cuenta-contable select");
	$viajeTipoAlimentos = $(".linea-bld select");
	$fechaGasto = $('.date-grid-container input');
	$comboBoxes = $("#panel-form-solicitud select");
	$inputs = $("#panel-form-solicitud input").not("#otroDestino, #otroMotivo");
	
	var rows = $('.t-row','#gastos-viaje-data').length;
	
	$fechaGasto.each(function () {
		if ($(this).val() == "") {
			$(this).addClass("errorx");
			error = true;
		} else {
			$(this).removeClass("errorx");
		}
	});
	
	$comboBoxes.each(function () {
		if ($(this).val() == -1) {
			$(this).addClass("errorx");
			error = true;
		} else {
			$(this).removeClass("errorx");
		}
	});
	
	$inputs.each(function () {
		if ($(this).val() == "") {
			$(this).addClass("errorx");
			error = true;
		} else {
			$(this).removeClass("errorx");
		}
	});
	
	if(rows > 0){
		$conceptos.each(function () {
			if ($(this).val() == -1) {
				$(this).addClass("errorx");
				error = true;
			} else {
				$(this).removeClass("errorx");
			}
		});
		
		$viajeTipoAlimentos.each(function () {
			var concepto = $('.linea-concepto', $(this).parent().parent()).val();
			if(concepto == conceptoAlimentos){
				if ($(this).val() == -1) {
					$(this).addClass("errorx");
					error = true;
				} else {
					$(this).removeClass("errorx");
				}
			}
		});
		
		$locaciones.each(function () {
			if ($(this).val() == -1) {
				$(this).addClass("errorx");
				error = true;
			} else {
				$(this).removeClass("errorx");
			}
		});
		
		$cuentasContables.each(function () {
			if ($(this).val() == -1) {
				$(this).addClass("errorx");
				error = true;
			} else {
				$(this).removeClass("errorx");
			}
		});
		
		$files.each(function () {
			if (!$(this).hasClass("completed")) {
				$(this).children().addClass("errorx");
				error = true;
			} else {
				$(this).children().removeClass("errorx");
			}
		});
	}
	
	if (rows == 0) {
		$(".scrollable").addClass("errorx");
		error = true;
	} else {
		$(".scrollable").removeClass("errorx");
	}
	
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#enviarSolicitud").prop("disabled", false);
		disabledEnabledFields(false);
		return true;
	}
}

function getCCByRowTable() {	
	$('.t-row','#gastos-viaje-data').each(function (i, row) {
		var idLocacion = $(".linea-locacion select" , row).val();
		var currCuentaCont = $(".linea-cuenta-contable" , row);
		$.ajax({
			type : "GET",
			cache : false,
			url : url_server+"/updLocacionDesgloseGV",
			async : true,
			data : "numrow=" + i +
			"&idLocacion=" + idLocacion,
			success : function (response) {
				if (response != "") {
					currCuentaCont.children().empty().append(response);
				}
			},
			error : function (e) {
				console.log('Error: ' + e);
			},
		});
	});
}

function validaInfoAnticipo() {	
	var error = false;
	$comboBoxes = $("#panel-form-solicitud select");
	$inputs = $("#panel-form-solicitud input").not("#otroDestino, #otroMotivo");
	
	$comboBoxes.each(function () {
		if ($(this).val() == -1) {
			$(this).addClass("errorx");
			error = true;
		} else {
			$(this).removeClass("errorx");
		}
	});
	
	$inputs.each(function () {
		if ($(this).val() == "") {
			$(this).addClass("errorx");
			error = true;
		} else {
			$(this).removeClass("errorx");
		}
	});
	
	if($('#otroDestino').is(':enabled')){
		if ($('#otroDestino').val() == "") {
			$('#otroDestino').addClass("errorx");
			error = true;
		} else {
			$('#otroDestino').removeClass("errorx");
		}
	}
	
	if($('#otroMotivo').is(':enabled')){
		if ($('#otroMotivo').val() == "") {
			$('#otroMotivo').addClass("errorx");
			error = true;
		} else {
			$('#otroMotivo').removeClass("errorx");
		}
	}
	
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#enviarSolicitud").prop("disabled", false);
		disabledEnabledFields(false);
		return true;
	}
}

$("#motivos").change(function() {
	var selected = $(this).find('option:selected')
	if(selected.attr('otro') == "true"){
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
	var selected = $(this).find('option:selected')
	if(selected.attr('otro') == "true"){
    	$("#otroDestino").prop("disabled", false);
    	$("#otroDestino").parent().removeClass( "destino-oculto" )
	}
	else{
		$("#otroDestino").prop("disabled", true);
		$("#otroDestino").val("");
		$("#otroDestino").parent().addClass( "destino-oculto" )
	}
});

/*
* Envía a proceso de autorizacion
* Estatus: ?
*/
function enviarSolicitudProceso() {
	 enviaProceso(idSolicitud);
}

