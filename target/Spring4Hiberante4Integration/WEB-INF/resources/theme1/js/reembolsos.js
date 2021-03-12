/**
 * INICIA: document.ready()
 */
$(document).ready(function () {
	sumSbt();
	
	if(configCorrecta == "false"){
		$('.panel-body').find('input, textarea, file, select').prop("disabled", true);
		$("input[type=radio],[type=file]").prop("disabled", true);
	}
	
	$('#fecha-sandbox input').datepicker({
		  format: 'dd/mm/yyyy',
		  language: 'es',
		  endDate: '+0d',
		  autoclose: true,
		  todayHighlight: true
	});
	
	$('.currencyFormat').number(true, 2);
	$('#tablaDesglose').on('click', ".removerFila", function () {
		var numRow = $(this).closest('tr').find('td:first').text();
		$(this).closest('tr').remove();
		updTotalOnDelete(numRow);
		sumSbt();
		calculateLine();
		resetColResizable();
	});

	disabledEnabledFields(true);
	getCCByRowTable();

	$(window).bind('beforeunload', function () {
		return PERDERA_INFORMACION;
	});

	$("#pdf").change(function () {
		validaNombreDocumento($(this))
		var ext = $('#pdf').val().split('.').pop().toLowerCase();
		if ($.inArray(ext, ['pdf']) == -1 && ext != "") {
			$("#pdf").addClass("errorx");
			$("#error-head").text(ERROR);
			$("#error-body").text(VALIDA_ARCHIVO_PDF);
			error_alert();
			$('#pdf').val("");
			$(".file-selected").text("");
		}
	});
	
	$("#file").change(function () {
		validaNombreDocumento($(this))
		if($('#subTotal').val() != ''){
			limpiaInputs('formFactura');
			$("#monedaDet").val($("#moneda").val());
		}
	});
	
	$("#comp").change(function () {
		validaNombreDocumento($(this))
		var ext = $('#comp').val().split('.').pop().toLowerCase();
		if ($.inArray(ext, ['gif']) == 0 && ext != "") {
			$("#comp").addClass("errorx");
			$("#error-head").text(ERROR);
			$("#error-body").text(NOXML);
			error_alert();
			$('#comp').val("");
			$(".file-selected").text("");
		}
	});

	$("#solicitante").click(function () {
		solicitanteChecked();
	});

	//Set combos default
	$(function () {
		$("#moneda").val(monPesos);
		$("#monedaDet").val(monPesos);
		$("#formaPago").val(fPagoTrans);
	});

	//miguelrdz - Switch entre captura con comprobante y sin comprobante
	$('input[name="optradio"]').change(function () {
		if (valCambioTipoComp($(this).val())) {
			confPanelFacturas($(this).val());
		} else {
			$("#cambiarTipoComp").attr('onclick', 'confModal(' + $(this).val() + ')');
			$("#modalConfirm").modal('show');
		}
	})

	//miguelrdz - Valor espejo en combos "Moneda"
	var previous;
	$("#moneda").focus(function () {
		previous = this.value;
	}).change(function () {
		var length = $("#tablaDesglose > tbody > tr").not(':last').length;
		if (length > 0) {
			$("#mensaje-dialogo").text(MENSAJE_CAMBIO_MONEDA);
			$("#modal-solicitud").modal({
				backdrop : 'static',
				keyboard : false
			});
			$("#cambiar_moneda_button").show();
			$("#cancelCambio").attr('onclick', 'cancelCambioSol("mon",' + previous + ')');
		} else {
			$("#monedaDet").val(this.value);
		}
		previous = this.value;
	});

	//miguelrdz - Cuando se cambia solicitante
	$("#usuarioSolicitante").change(function () {
		updLocacionByUsr($("#usuarioSolicitante").val());
	});

	//miguelrdz - Cuando se cambia locacion
	(function () {
		var previous;
		$("#locacion").focus(function () {
			previous = this.value;
		}).change(function () {
			var length = $("#tablaDesglose > tbody > tr").not(':last').length;
			if (length > 0) {
				$("#mensaje-dialogo").text(MENSAJE_CAMBIO_LOCACION);
				$("#modal-solicitud").modal({
					backdrop : 'static',
					keyboard : false
				});
				$("#cambiar_locacion_button").show();
				$("#cancelCambio").attr('onclick', 'cancelCambioSol("loc",' + previous + ')');
			}
			previous = this.value;
		});
	})();

	//miguelrdz - Cuando se cambia beneficiario
	(function () {
		var previous;
		var locSelecc;
		$("#beneficiario").focus(function () {
			previous = this.value;
			locSelecc = $("#locacion").val();
		}).change(function () {
			updLocacionByUsr($("#beneficiario").val());
			var length = $("#tablaDesglose > tbody > tr").not(':last').length;
			if (length > 0) {
				$("#mensaje-dialogo").text(MENSAJE_CAMBIO_BENEFICIARIO);
				$("#modal-solicitud").modal({
					backdrop : 'static',
					keyboard : false
				});
				$("#cambiar_beneficiario_button").show();
				$("#cancelCambio").attr('onclick', 'cancelCambioSol("ben",' + previous + ',' + locSelecc + ')');
			}
			previous = this.value;
		});
	})();

	//Refresca campos despues de post
	$("#monedaDet").val($("#moneda").val());

	$('#modal-solicitud').on('hidden.bs.modal', function () {
		$("#cambiarxml_button").hide();
		$("#cancelar_button").hide();
		$("#cambiar_solicitante_button").hide();
		$("#cambiar_beneficiario_button").hide();
		$("#cambiar_moneda_button").hide();
		$("#cambiar_locacion_button").hide();
	})
	
	setStatusScreen(idStatus);

	if (isModificacion == 'true') {
		$("#ok-head").text(ACTUALIZACION);
		$("#ok-body").text(INFORMACION_ACTUALIZADA);
		ok_alert();
	}

	if (isCreacion == 'true') {
		$("#ok-head").text(NUEVA_SOLICITUD);
		$("#ok-body").text(SOLICITUD_CREADA);
		ok_alert();
	}

	$('.noMercanciasTooltips').on('change', function () {
		refreshTooltip();
	});

	/*PARA activar y refrescar tooltip*/
	refreshTooltip();
	activeToolTip();
	
});
/**
 * Termina: document.ready()
 */

function activeToolTip(){
	$("[data-toggle=tooltip]").tooltip({
		placement: $(this).data("placement") || 'top'
	});
}

function addRowToTable() {
	if($('#compTrue').is(':checked')){
		var uuid = $("#folioFiscal").val();
		$.ajax({
			url : url_server+"/validaXMLLocal",
			data : "uuid=" + uuid,
			cache : false,
			contentType : false,
			processData : false,
			type : 'GET',
			success : function (response) {
				log(response);
				if(response == true){
					if (valCapturaFact($('#compTrue').is(':checked'))) {
						guardarArchivos();
					} else {
						$("#error-head").text(ERROR);
						$("#error-body").text(COMPLETE);
						error_alert();
					}
				}else{
					var errmsj = ERROR_DOCUMENTO_CARGADO.replace("_filename", uuid);
					$("#file").addClass("errorx");
					$("#error-head").text(ERROR);
					$("#error-body").text(errmsj);
					error_alert();
					limpiaFormFact();
				}			
			},
			error : function (e) {
				console.log('Error: ' + e);
			},
		});
	}else{
		if (valCapturaFact($('#compTrue').is(':checked'))) {
			guardarArchivos();
		} else {
			$("#error-head").text(ERROR);
			$("#error-body").text(COMPLETE);
			error_alert();
		}
	}
}

function addRowReembolso(){
	var length = $("#tablaDesglose > tbody > tr").not(':last').length;
	var conComp = $("#compTrue").is(':checked');
	var prov = ($("#proveedor").val() != null ? $("#proveedor").val() : 0)
	var proveedorLibre = $("#proveedorLibre").val();
	if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
		var benef = $("#beneficiario").val();
	} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_REEMBOLSO) {
		var benef = $("#usuarioSolicitante").val();
	}
	
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/addRowReemConXML",
		async : true,
		data : "numrows=" + length +
		"&compania=" + $("#compania").val() +
		"&conComp=" + conComp +
		"&locacion=" + $("#locacion").val() +
		"&proveedor=" + prov +
		"&proveedorLibre=" + proveedorLibre +
		"&rfcEmisor=" + $("#rfcEmisor").val() +
		"&folio=" + $("#folio").val() +
		"&serie=" + $("#serie").val() +
		"&folioFiscal=" + $("#folioFiscal").val() +
		"&fecha=" + $("#fecha").val() +
		"&subTotal=" + $("#subTotal").val() +
		"&moneda=" + $("#moneda").val() +
		"&conRetenciones=" + $("#conRetenciones").prop('checked') +
		"&iva=" + $("#iva").val() +
		"&iva_retenido=" + $("#iva_retenido").val() +
		"&ieps=" + $("#ieps").val() +
		"&isr_retenido=" + $("#isr_retenido").val() +
		"&conceptoSol=" + encodeURIComponent($("#conceptoSol").val()) +
		"&total=" + $("#total").val() +
		"&beneficiario=" + benef +
		"&tasaIva=" + $("#tasaIva").val() +
		"&tasaIeps=" + $("#tasaIeps").val(),
		success : function (response) {
			//$("#tablaDesglose").append(response);
			$('#tablaDesglose tr:last').before(response);
			sumSbt();
			getTotalSol();
			if (conComp) {
				confPanelFacturas("true");
			} else {
				confPanelFacturas("false");
			}
			$tr = $("#tablaDesglose > tbody > tr").not(':last').last();
			resetColResizable();
			$(".currencyFormat",$tr).number(true,2);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
	limpiaFormFact();
	//disabledEnabledFields(true);
}

function valTodo() {
	$(window).unbind('beforeunload');
	var error = false;
	if ($('#solicitante').is(':checked')) {
		if ($("#usuarioSolicitante").val() == -1) {
			$("#usuarioSolicitante").addClass("errorx");
			error = true;
		} else {
			$("#usuarioSolicitante").removeClass("errorx");
		}
	}
	if ($("#moneda").val() == -1) {
		$("#moneda").addClass("errorx");
		error = true;
	} else {
		$("#moneda").removeClass("errorx");
	}
	if ($("#locacion").val() == -1) {
		$("#locacion").addClass("errorx");
		error = true;
	} else {
		$("#locacion").removeClass("errorx");
	}
	if ($("#formaPago").val() == -1) {
		$("#formaPago").addClass("errorx");
		error = true;
	} else {
		$("#formaPago").removeClass("errorx");
	}
	if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
		if ($("#beneficiario").val() == -1) {
			$("#beneficiario").addClass("errorx");
			error = true;
		} else {
			$("#beneficiario").removeClass("errorx");
		}
	}
	var length = $("#tablaDesglose > tbody > tr").not(':last').length;
	if (length > 0) {
		$('.ccontable').each(function () {
			if ($(this).val() == -1) {
				$(this).addClass("errorx");
				error = true;
			} else {
				$(this).removeClass("errorx");
			}
		});
		if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
			$('.locaciones').each(function () {
				if ($(this).val() == -1) {
					$(this).addClass("errorx");
					error = true;
				} else {
					$(this).removeClass("errorx");
				}
			});
		}
	}
	if ($("#tablaDesglose > tbody > tr").not(':last').length == 0) {
		$("#tablaDesglose").addClass("errorx");
		error = true;
	} else {
		$("#tablaDesglose").removeClass("errorx");
	}
	if (error) {
		$("#error-head").text(ERROR);
		$("#error-body").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#enviarSolicitud").prop("disabled", false);
		//ok_alert();
		disabledEnabledFields(false);
		return true;
	}
}

function valCapturaFact(conComprobante) {
	var error = false;
	if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
		if ($("#beneficiario").val() == -1) {
			$("#beneficiario").addClass("errorx");
			error = true;
		} else {
			$("#beneficiario").removeClass("errorx");
		}
	} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_REEMBOLSO) {
		if ($('#solicitante').is(':checked')) {
			if ($("#usuarioSolicitante").val() == -1) {
				$("#usuarioSolicitante").addClass("errorx");
				error = true;
			} else {
				$("#usuarioSolicitante").removeClass("errorx");
			}
		}
	}

	if ($("#formaPago").val() == -1) {
		$("#formaPago").addClass("errorx");
		error = true;
	} else {
		$("#formaPago").removeClass("errorx");
	}
	if ($("#moneda").val() == -1) {
		$("#moneda").addClass("errorx");
		error = true;
	} else {
		$("#moneda").removeClass("errorx");
	}
	if ($("#locacion").val() == -1) {
		$("#locacion").addClass("errorx");
		error = true;
	} else {
		$("#locacion").removeClass("errorx");
	}

	if (conComprobante) {
		if (document.getElementById("pdf").files.length == 0) {
			$("#pdf").addClass("errorx");
			error = true;
		} else {
			$("#pdf").removeClass("errorx");
		}
		if (document.getElementById("file").files.length == 0) {
			$("#file").addClass("errorx");
			error = true;
		} else {
			$("#file").removeClass("errorx");
		}
		if ($("#subTotal").val() == "") {
			$("#validarXMLb").addClass("errorx");
			error = true;
		} else {
			$("#validarXMLb").removeClass("errorx");
		}
		if ($("#monedaDet").val() == -1) {
			$("#monedaDet").addClass("errorx");
			error = true;
		} else {
			$("#monedaDet").removeClass("errorx");
		}
	} else {
		if (document.getElementById("comp").files.length == 0) {
			$("#comp").addClass("errorx");
			error = true;
		} else {
			$("#comp").removeClass("errorx");
		}
		if ($("#folio").val() == "") {
			$("#folio").addClass("errorx");
			error = true;
		} else {
			$("#folio").removeClass("errorx");
		}
		if ($("#fecha").val() == "") {
			$("#fecha").addClass("errorx");
			error = true;
		} else {
			$("#fecha").removeClass("errorx");
		}
		if ($("#subTotal").val() == "") {
			$("#subTotal").addClass("errorx");
			error = true;
		} else {
			$("#subTotal").removeClass("errorx");
		}
		if ($("#total").val() == "") {
			$("#total").addClass("errorx");
			error = true;
		} else {
			$("#total").removeClass("errorx");
		}
		if ($("#monedaDet").val() == -1) {
			$("#monedaDet").addClass("errorx");
			error = true;
		} else {
			$("#monedaDet").removeClass("errorx");
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

function setSbt() {
	if ($("#compFalse").is(':checked')) {
		$("#subTotal").val($("#total").val());
	}
}

function sumSbt() {
	var sum = 0;
	if (!$.isEmptyObject($.find('.subtotales'))) {
		$('.subtotales').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#sbt").val(sum);
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#sbt").val(sum);
	}
	var sum = 0;
	if (!$.isEmptyObject($.find('.ivas'))) {
		$('.ivas').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#ivatot").val(sum);
				//$("#ivatot").width($(".ivas:first").width());
				//$("#tdIvaTot").width($("#thIva").width());
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#ivatot").val(sum);
	}
	var sum = 0;
	if (!$.isEmptyObject($.find('.ieps'))) {
		$('.ieps').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#iepstot").val(sum);
				//$("#iepstot").width($(".ieps:first").width());
				//$("#tdIepsTot").width($("#thIeps").width());
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#iepstot").val(sum);
	}
}

function ccOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var idCtaCont = $(this).val();
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updCuentaCont",
		async : true,
		data : "numrow=" + numRow +
		"&idCtaCont=" + idCtaCont,
		success : function (response) {},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function locacionOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var idLocacion = $(this).val();
	var currentRow = $(this).closest('tr').find('td.ccontable');
	loading(true);
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updLocacion",
		async : true,
		data : "isFirst=" + false +
		"&numrow=" + numRow +
		"&idLocacion=" + idLocacion,
		success : function (response) {
			if (response != "") {
				currentRow.children().empty().append(response);
			}
			loading(false);
		},
		error : function (e) {
			console.log('Error: ' + e);
			loading(false);
		},
	});
}

function conceptoOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var concepto = $(this).val();
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updConceptoFact",
		async : true,
		data : "numrow=" + numRow +
		"&concepto=" + concepto,
		success : function (response) {},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function getCCByRowTable() {
	$('#tablaDesglose tr').not(':last').each(function (i, row) {
		if (i > 0) {
			var numRow = $(this).closest('tr').find('td:first').text();
			var idLocacion = $(this).closest('tr').find('td').eq(2).find('select').val();
			var currentRow = $(this).closest('tr').find('td.ccontable');
			$.ajax({
				type : "GET",
				cache : false,
				url : url_server+"/updLocacion",
				async : true,
				data : "isFirst=" + true +
				"&numrow=" + numRow +
				"&idLocacion=" + idLocacion,
				success : function (response) {
					if (response != "") {
						currentRow.children().empty().append(response);
					}
					loading(false);
				},
				error : function (e) {
					console.log('Error: ' + e);
					loading(false);
				},
			});
		}
	});
}

function getTotalSol() {
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/getTotalSol",
		async : true,
		data : "numrows=" + length,
		success : function (response) {
			$("#reembTot").val(response);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function updTotalOnDelete(numRow) {
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/updTotalOnDelete",
		async : true,
		data : "numrow=" + numRow,
		success : function (response) {
			$("#reembTot").val(response);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function clearGrid() {
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/resetSolicitud",
		async : true,
		success : function (response) {
			$("#reembTot").val(response);
			$("#sbt").val(response);
			$("#ivatot").val(response);
			$("#iepstot").val(response);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

$(window).load(function () {
	console.log("load................");
});

function cancelar() {
	$("#mensaje-dialogo").text(MENSAJE_CANCELACION_NOXML);
	//$("#modal-solicitud").modal("show");
	$("#modal-solicitud").modal({
		backdrop : 'static',
		keyboard : false
	});
	$("#cancelar_button").show();
}

function updLocacionByUsr(idUsuario) {
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/getLocacionSolicitante",
		async : true,
		data : "idSolicitante=" + idUsuario,
		success : function (response) {
			$("#locacion").empty().append(response);
			console.log(response);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

function solicitanteChecked() {
	if ($("#solicitante").is(":checked")) {
		$("#usuarioSolicitante").prop('disabled', false);
	} else {
		$("#usuarioSolicitante").prop('disabled', true);
		$("#usuarioSolicitante").val(-1);
		updLocacionByUsr(idUsuarioSolicitante);
	}
}

/*
* carga tooltip a combos
* Estatus: ?
*/
function refreshTooltip() {
	var usuarioSolicitante = $("#usuarioSolicitante option:selected").text();
	var beneficiario = $("#beneficiario option:selected").text();
	var moneda = $("#moneda option:selected").text();
	var formaPago = $("#formaPago option:selected").text();
	var compania = $("#compania option:selected").text();
	var locacion = $("#locacion option:selected").text();
	var proveedor = $("#proveedor option:selected").text();

	$('#solicitanteLabel').tooltip('hide')
	.attr('data-original-title', usuarioSolicitante)
	.tooltip('fixTitle');
	$('#beneficiarioLabel').tooltip('hide')
	.attr('data-original-title', beneficiario)
	.tooltip('fixTitle');
	$('#monedaLabel').tooltip('hide')
	.attr('data-original-title', moneda)
	.tooltip('fixTitle');
	$('#formaPagoLabel').tooltip('hide')
	.attr('data-original-title', formaPago)
	.tooltip('fixTitle');
	$('#companiaLabel').tooltip('hide')
	.attr('data-original-title', compania)
	.tooltip('fixTitle');
	$('#locacionLabel').tooltip('hide')
	.attr('data-original-title', locacion)
	.tooltip('fixTitle');
	$('#proveedorLabel').tooltip('hide')
	.attr('data-original-title', proveedor)
	.tooltip('fixTitle');
}

/*
* Valida el documento XML
* Estatus: ?
*/
function valXML(){
  

  if(document.getElementById("file").files.length != 0){
	  console.log("entro a valXML");
	  $("#error-alert").hide();
	  
	  var data = new FormData();
	  jQuery.each(jQuery('#file')[0].files, function(i, file) {
		  data.append('file-'+i, file);
	  });
	  
	  if(data){
		  loading(true);
		  jQuery.ajax({
				url : url_server+"/resolverXML",
				data: data,
				cache: false,
				contentType: false,
				processData: false,
				type: 'POST',
				success: function(data){
					loading(false);
					console.log(data);
					$("#loaderXML").hide();
					$("#file").removeClass("errorx");

					console.log(data.validxml);
					if(data.validxml == "true"){
						if(data.idMoneda == $("#monedaDet").val()){
							if($("#compania").val() == data.idCompania){
								$("#compania").val(data.idCompania);
								if(data.faltaProveedor == "true"){
									$("#proveedor").hide();
									$("#proveedorLibre").show();
									$("#proveedorLibre").val(data.proveedorLibre);
								}
								else{
									$("#proveedorLibre").hide();
									$("#proveedor").show();
									$("#proveedor").val(data.idProveedor);
								}
								$("#folioFiscal").val(data.folioFiscal);
								$("#total").val(data.total);
								$("#subTotal").val(data.subTotal);
								$("#iva").val(data.iva);
								$("#iva_retenido").val(data.ivaRetenido)
								$("#ieps").val(data.ieps);
								$("#isr_retenido").val(data.isrRetenido)
								$("#sppc").val(data.subTotal);
								$("#rfcEmisor").val(data.rfcEmisor);
								$("#serie").val(data.serie);
								$("#folio").val(data.folio);
								$("#fecha").val(data.fechaEmision);
								$("#tasaIva").val(data.tasaIva);
								$("#tasaIeps").val(data.tasaIeps);
								
						        if(data.incluyeRetenciones == 'true')
						        	$("#conRetenciones").prop("checked",true);
							    else
							    	$("#conRetenciones").prop("checked",false);

								if(data.wsmensaje != null){
									if(data.wscode == 0){
										$("#ok-head").text(FACTURA_VALIDA);
										$("#ok-body").text(data.wsmensaje);
										ok_alert();
									}else{
										$("#error-head").text(ERROR);
										$("#error-body").text(data.wsmensaje);
										error_alert();
									}
								}
								refreshTooltip();
							}
							else{
								$("#file").addClass("errorx");
								$("#error-head").text(ERROR);
								$("#error-body").text(DIF_RAZON_SOCIAL.replace("rsFactura", data.rsFactura).replace("rsSolicitante", $("#compania option:selected").text()));
								error_alert();
								$("#file").val(null);
								$("#pdf").val(null);
							}
						}
						else{
							$("#file").addClass("errorx");
							$("#error-head").text(ERROR);
							$("#error-body").text(DIF_MONEDA);
							error_alert();
							$("#file").val(null);
							$("#pdf").val(null);
						}								
					}
					else{
						if(data.wscode == 0){
//							if(data.faltaProveedor == "true"){
//								$("#file").addClass("errorx");
//								$("#error-alert").show();
//								$("#error-head").text(ERROR);
//								$("#error-body").text(NOPROVEEDOR);
//								$("#pdf").val(null);
//								$("#file").val(null);
//							}else{
//								$("#file").addClass("errorx");
//								$("#error-alert").show();
//								$("#error-head").text(ERROR);
//								$("#error-body").text(NOXML);
//								$("#pdf").val(null);
//								$("#file").val(null);
//							}
						}
						else{
							$("#file").addClass("errorx");
							$("#error-head").text(ERROR);
							$("#error-body").text(data.wsmensaje);
							error_alert();
							$("#file").val(null);
							$("#pdf").val(null);
						}
					}
				},
				error : function (e) {
					loading(false);
					console.log('Error: ' + e);
				},
			});
	  }
	  
  }else{
	  loading(false);
	  $("#loaderXML").hide();
	  $("#file").addClass("errorx");
	  $("#error-head").text(ERROR);
	  $("#error-body").text(COMPLETE);
	  error_alert();
  }
}

/*
* Calcula linea de grid
* Estatus: OK
*/
function calculateLine() {
	console.log("tr");
	
	$('#tablaDesglose tr').not(':last').each(function (i, row) {
		if (i > 0) {
			//console.log(i + "<- iteracion");
			var nlinea = $(this).find(".linea").text(i);
			//console.log(nlinea + "<- texto");
		}
	});
}

/*
* activa o desactiva componentes
* Estatus: ?
*/
function disabledEnabledFields(state) {
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
	if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
		$("#formaPago").prop('disabled', state);
		$("#beneficiario").prop('disabled', state);
		$("#locacion").prop('disabled', state);
	} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_REEMBOLSO) {
		if($("#solicitante").is(":checked"))
			$("#usuarioSolicitante").prop('disabled', false);
		else
			$("#usuarioSolicitante").prop('disabled', true);
		if ($("#espSolicitante").val() == 0) {
			$("#espSol").remove();
		} else {
			$("#espSol").show();
		}
	}
}

/*
* Envía a proceso de autorizacion
* Estatus: ?
*/
function enviarSolicitudProceso() {
	 enviaProceso(idSolicitud);
}

$("#cancelar_button").click(function () {
	loading(true);
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/cancelarSolicitud",
		async : false,
		data : "idSolicitud=" + idSolicitud,
		success : function (response) {
			loading(false);
			if (response.resultado == 'true') {
				$(window).unbind('beforeunload');
				location.reload(true)
			}
		},
		error : function (e) {
			loading(false);
			console.log('Error: ' + e);
		},
	});
});

/*
* Valida envío a proceso
* Estatus: ?
*/
function validaEnvioProceso() {
	var valido = true;
	var msj = null;
	//var length = $("#tablaDesglose > tbody > tr").length;
	var length = 0;
	if (length > 0) {
		if (validaGrid() == false) {
			if ($("#restante").val() == 0) {
				$("#sppc").removeClass("errorx");
				return true;
			} else {
				$("#error-head").text("Error: ");
				$("#error-body").text("Saldo pendiente por comprobar debe ser cero");
				$("#sppc").addClass("errorx");
				error_alert();
				return false;
			}
		} else {
			$("#error-head").text("Error: ");
			$("#error-body").text("Complete todos los campos.");
			error_alert();
			return false;
		}
	} else {
		$("#error-head").text("Error: ");
		$("#error-body").text("Debe tener por lo menos un desglose.");
		error_alert();
		return false;
	}
}

/*
* Set estatus solicitud
* Estatus: ?
*/
function setStatusScreen(idStatus) {
	if (idStatus > 0) {
		if (idStatus > 1 && idStatus !=4) {
			$("#solicitante").prop("disabled", true);
			$("#concepto").prop("disabled", true);
			$(".subtotales").prop("disabled", true);
			$(".removerFila").prop("disabled", true);
			$(".locaciones").prop("disabled", true);
			$(".ccontable").prop("disabled", true);
			$(".conceptogrid").prop("disabled", true);

			//restantes
			disabledEnabledFields(true);

			checkStatusBehavior(idStatus);
		}
	}
}

/*
* Cancela el reset de la solicitud, los combos vuelven a su valor original
* Estatus: OK
*/
function cancelCambioSol(tipo, prev, prev2) {
	if (tipo == "mon") {
		$("#moneda").val(prev);
	} else if (tipo == "loc") {
		$("#locacion").val(prev);
	} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
		if (tipo == "ben") {
			$("#beneficiario").val(prev);
			$("#locacion").val(prev2);
		}
	} else {
		if ($("#solicitante").prop('checked')) {
			$("#solicitante").prop('checked', false);
			$("#usuarioSolicitante").prop('disabled', true);
		} else {
			$("#solicitante").prop('checked', true);
			$("#usuarioSolicitante").prop('disabled', false);
		}
	}
	$("#cancelCambio").attr('onclick', 'cancelCambioSol()');
}

/*
* Limpia el parseo de factura capturada.
* Estatus: OK
*/
function limpiaFormFact() {
	limpiaInputs('formFactura');
	$("#monedaDet").val($("#moneda").val());
	$("#file").val(null);
	$("#pdf").val(null);
	$("#comp").val(null);
	$(".file-selected").text("");
}

/*
* Limpia inputs de un form (id) recibido
* Estatus: OK formFactura
*/
function limpiaInputs(idForm) {
	$(':input', '#'+idForm+'').each(function () {
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		$(this).removeClass('errorx');
		if (type == 'text')
			this.value = "";
		else if (type == 'checkbox')
			this.checked = false;
		else if (tag == 'select')
			this.selectedIndex = 0;
	});
}

/*
* Aplica configuración del formulario ocultar/mostrar componentes
* Estatus: OK
*/
function confPanelFacturas(val) {
	limpiaFormFact();
	if (val == "true") {
		$("#headerFactura").text(HEADER_CON_XML);
		$("#cargaComp").hide();
		$("#cargaDctos").show();
		$("#fieldProveedor").show();
		$("#fieldRFC").show();
		//$("#fieldFolFiscal").show();
		$("#fieldRetenciones").show();
		//$("#fieldIVA").show();
		//$("#fieldIEPS").show();
		$(".fieldFiscal").show();
		disabledEnabledFields(true);
		if($("#solicitante").is(":checked"))
			$("#usuarioSolicitante").prop('disabled', false);
		else
			$("#usuarioSolicitante").prop('disabled', true);
	} else {
		$("#headerFactura").text(HEADER_SIN_XML);
		$("#cargaComp").show();
		$("#cargaDctos").hide();
		$("#fieldProveedor").hide();
		$("#fieldRFC").hide();
		//$("#fieldFolFiscal").hide();
		$("#fieldRetenciones").hide();
		//$("#fieldIVA").hide();
		//$("#fieldIEPS").hide();
		$(".fieldFiscal").hide();
		disabledEnabledFields(false);
		$("#reembTot").prop('disabled', true);
		$("#compania").prop('disabled', true);
		if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
			$("#formaPago").prop('disabled', true);
			$("#beneficiario").prop('disabled', true);
			$("#locacion").prop('disabled', true);
		} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_REEMBOLSO) {
			if($("#solicitante").is(":checked"))
				$("#usuarioSolicitante").prop('disabled', false);
			else
				$("#usuarioSolicitante").prop('disabled', true);
		}
	}
}

/*
* Confirmación de modal
* Estatus: OK
*/
function confModal(res) {
	$('#modalConfirm').modal('hide');
	if (res) {
		confPanelFacturas("true");
	} else {
		confPanelFacturas("false");
	}
}

/*
* Limpia el grid de facturas capturadas
* Estatus: OK
*/
function resetSolicitud() {
	$('#modal-solicitud').modal('hide');
	clearGrid();
	$("#monedaDet").val($("#moneda").val());
	$('#tablaDesglose tr').not(':last').each(function (i, row) {
		if (i > 0) {
			$(this).closest('tr').remove();
			//calculateLine();
		}
	});
}

/*
 * Cancelación de ventana modal
 * Estatus: OK
 */
function cancelModal() {
	if ($('#proveedor').is(":visible")) {
		$('#compTrue').prop("checked", "checked")
		$("#fecha").removeClass("backBlank");
	} else {
		$('#compFalse').prop("checked", "checked")
		$("#fecha").addClass("backBlank");
	}
}

$('#modalConfirm').on('hidden.bs.modal', function () {
	cancelModal();
})

/*
 * Valida que no haya campos llenos cuando se cambia de tipo de comprobante
 * Estatus: OK
 */
function valCambioTipoComp(conComprobante) {
	var empty = true;
	if (conComprobante == "true") {
		if (document.getElementById("comp").files.length != 0) {
			empty = false;
		}
		if ($("#folio").val() != "") {
			empty = false;
		}
		if ($("#fecha").val() != "") {
			empty = false;
		}
		if ($("#subTotal").val() != "") {
			empty = false;
		}
		if ($("#total").val() != "") {
			empty = false;
		}
		if(empty == true){
			$("#fecha").removeClass("backBlank");
		}
	} else {
		if (document.getElementById("pdf").files.length != 0) {
			empty = false;
		}
		if (document.getElementById("file").files.length != 0) {
			empty = false;
		}
		if ($("#subTotal").val() != "") {
			empty = false;
		}
		if(empty == true){
			$("#fecha").addClass("backBlank");
		}		
	}

	if (empty) {
		return true;
	} else {
		return false;
	}
}

/*
 * Guarda temporalmente archivos cargados en grid
 */
function guardarArchivos() {
	var data = new FormData();
	var conComp = $("#compTrue").is(':checked');
	if (conComp) {
		jQuery.each(jQuery('#pdf')[0].files, function (i, file) {
			data.append('file-' + i, file);
		});
		jQuery.each(jQuery('#file')[0].files, function (i, file) {
			data.append('file-' + i, file);
		});
	} else {
		jQuery.each(jQuery('#comp')[0].files, function (i, file) {
			data.append('file-' + i, file);
		});
	}
	data.append('conComp', conComp);
	if (data) {
		jQuery.ajax({
			url : url_server+"/guardarArchivos",
			data : data,
			cache : false,
			contentType : false,
			processData : false,
			type : 'POST',
			success : function (data) {
				if(data > 0){
					addRowReembolso();
					return true;
				}
				else{
					return false;
				}		
			}
		});
	}
	else{
		return false;
	}
}

/*
 * Ver detalle de autorización
 * Estatus: OK
 */
function verDetalleEstatus(id) {
	if (id > 0) {
		console.log(id);
		$.ajax({
			type : "GET",
			cache : false,
			url : url_server+"/getEstatus",
			async : true,
			data : "intxnId=" + id,
			success : function (result) {
				$("#tablaDetalle").empty().append(result.lista);
				//abrir ventana modal
				$('#modalEstatus').modal('show');
			},
			error : function (e) {
				console.log('Error: ' + e);
			},
		});
	}
}

/*
 * Valida que no haya facturas agregadas con mismo nombre
 * Estatus: OK
 */
function validaNombreDocumento(document) {
	
	var filename= document.val().split(/[\\\/]/).pop();
	
	if(filename != ""){
		$.ajax({
			url : url_server+"/validaNombreDocumento",
			data : "filename=" + filename,
			cache : false,
			contentType : false,
			processData : false,
			type : 'GET',
			success : function (response) {
				if(response == true){
					log("File Validated");
				}else{
					log("File with same name");
					var errmsj = ERROR_DOCUMENTO_CARGADO.replace("_filename", filename);
					document.addClass("errorx");
					$("#error-head").text(ERROR);
					$("#error-body").text(errmsj);
					error_alert();
					document.val("");
					$(".file-selected").text("");
				}
			},
			error : function (e) {
				console.log('Error: ' + e);
				return false;
			},
		});
	}
}