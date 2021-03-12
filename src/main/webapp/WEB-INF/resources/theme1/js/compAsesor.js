/**
 * INICIA: document.ready()
 */
$(document).ready(function () {
	
	$("#rightPadding").width($("#headerTotal").width());
	$("#tdMontoTotal").width($("#headerTotal").width());
	$("#tdSbt").width($("#headerSbt").width());
	$("#tdIvaTot").width($("#headerIvas").width());
	
	$("#adjuntar_comp_asesor").click(function() {
		confPanelFacturas("true");
		$('#tablaDesgloseCompAsesor tr').removeClass('highlight');
		$("#cargaArchivos").show();
		$("#btn_agregar").show();
		$("#btn_guardar").hide();
	});


	$('#comprobacionAsesor').on('hide.bs.modal', function (e) {
		var error = false;
		var length = $("#tablaDesgloseCompAsesor > tbody > tr").length;
		if (length > 0) {
			$('.ccontable').each(function () {
				if ($(this).val() == -1) {
					$(this).addClass("errorx");
					error = true;
				} else {
					$(this).removeClass("errorx");
				}
			});
		}
		
		if (error) {
			e.preventDefault();
			$("#error-head-asesor").text(ERROR);
			$("#error-body-asesor").text(COMPLETE);
			error_alert();
			return false;
		}
		else{
			$('#compTrue').prop("checked", "checked");
			confPanelFacturas("true");
			$('#tablaDesgloseCompAsesor tr').removeClass('highlight');
		}
	});

	$("#fecha").datepicker().on('hide.bs.modal', function(event) {
	    // prevent datepicker from firing bootstrap modal "hide.bs.modal"
	    event.stopPropagation(); 
	});
	
	$('#fecha-sandbox input').datepicker({
		format : 'dd/mm/yyyy',
		language : 'es'
	});
//	$('.currencyFormat').number(true, 2);
	//Botón eliminar del grid
	$(document).on('click', ".removerFila", function () {
		var numRow = $(this).closest('tr').find('td:first').text();
		$('#tablaDesgloseCompAsesor tr').eq(numRow).remove();
		$('#tablaDesglose tr').eq(numRow).remove();
		updTotalOnDelete(numRow);
		sumSbtComp();
		calculateLine();
	});
	//Botón editar del grid
	$(document).on('click', ".editarFila", function () {
		var numRow = $(this).closest('tr').find('td:first').text();
		if(tipoComprobacion == 2){
		   editarRowNoMercancias(numRow);
		}else if(tipoComprobacion == 1){
		   editarGridComp(numRow);
		}
	});
	
	

	disabledEnabledFields(true);
	//getCCByRowTable();

	$(window).bind('beforeunload', function () {
		return PERDERA_INFORMACION;
	});

	$("#pdf").change(function () {
		var ext = $('#pdf').val().split('.').pop().toLowerCase();
		if ($.inArray(ext, ['pdf']) == -1 && ext != "") {
			$("#pdf").addClass("errorx");
			$("#error-head-asesor").text(ERROR);
			$("#error-body-asesor").text(EXTENSION_INVALIDA);
			error_alert();
			$('#pdf').val("");
		}
	});

	$("#solicitante").click(function () {
		solicitanteChecked();
	});

	//miguelrdz - Switch entre captura con comprobante y sin comprobante
	$('input[name="compradio"]').change(function () {
		console.log($('#folio').val())
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
		var length = $("#tablaDesgloseCompAsesor > tbody > tr").length;
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

	//miguelrdz - Cuando se cambia locacion
	(function () {
		var previous;
		$("#locacion").focus(function () {
			previous = this.value;
		}).change(function () {
			var length = $("#tablaDesgloseCompAsesor > tbody > tr").length;
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

	var idStatus = '${solicitudDTO.estatusSolicitud}';
	//setStatusScreen(idStatus);

	var isModificacion = '${solicitudDTO.modificacion}';
	var isCreacion = '${solicitudDTO.creacion}';

//	if (isModificacion == 'true') {
//		$("#ok-head-asesor").text(ACTUALIZACION);
//		$("#ok-body-asesor").text(INFORMACION_ACTUALIZADA);
//		ok_alert();
//	}
//
//	if (isCreacion == 'true') {
//		$("#ok-head-asesor").text(NUEVA_SOLICITUD);
//		$("#ok-body-asesor").text(SOLICITUD_CREADA);
//		ok_alert();
//	}

	$('.noMercanciasTooltips').on('change', function () {
		refreshTooltip();
	});

	/*PARA activar y refrescar tooltip*/
	refreshTooltip();
	activeToolTip();;
	sumSbtComp();
});/**
 * Termina: document.ready()
 */

function activeToolTip(){
	$("[data-toggle=tooltip]").tooltip({
		placement: $(this).data("placement") || 'top'
	});
}

function addRowToTable() {
	if (valCapturaFact($('#compTrue').is(':checked'))) {
		guardarArchivos();
	} else {
		$("#error-alert-asesor").show();
		$("#error-head-asesor").text(ERROR);
		$("#error-body-asesor").text(COMPLETE);
	}
}

function addRowReembolso(){
	var length = $("#tablaDesglose > tbody > tr").length;
	var conComp = $("#compTrue").is(':checked');
	var benef = $("#beneficiario").val();
	
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/addRowCompAsesor",
		async : true,
		data : "numrows=" + length +
		"&compania=" + $("#compania").val() +
		"&conComp=" + conComp +
		"&locacion=" + $("#locacion").val() +
		"&proveedor=" + $("#proveedor").val() +
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
		"&conceptoSol=" + $("#concepto").val() +
		"&total=" + $("#total").val() +
		"&beneficiario=" + benef +
		"&tasaIva=" + $("#tasaIva").val() +
		"&tasaIeps=" + $("#tasaIeps").val(),
		success : function (response) {
			$("#tablaDesgloseCompAsesor").append(response.gridFacturas);
			$("#tablaDesglose").append(response.gridComprobacion);
			sumSbtComp();
			getTotalSol();
			if (conComp) {
				confPanelFacturas("true");
			} else {
				confPanelFacturas("false");
			}
//			$('.currencyFormat').number(true, 2);
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
	limpiaFormFact();
	disabledEnabledFields(true);
}

function editRowToTable() {
	var selectedIndex = $("#selectedIndex").val();
	var conComp = $("#compTrue").is(':checked');
	
	if(!conComp){
		if (valCapturaFactEdit($('#compTrue').is(':checked'))) {
			var benef = 1;
				$.ajax({
					type : "GET",
					cache : false,
					url :  url_server+"/editRowCompAsesor",
					async : true,
					data : "selectedIndex=" + selectedIndex +
					"&folio=" + $("#folio").val() +
					"&fecha=" + $("#fecha").val() +
					"&subTotal=" + $("#subTotal").val() +
					"&moneda=" + $("#moneda").val() +
					"&total=" + $("#total").val(),
					success : function (response) {
						updateGrid(response);
						sumSbtComp();
						getTotalSol();
						if (conComp) {
							confPanelFacturas("true");
						} else {
							confPanelFacturas("false");
						}
//						$('.currencyFormat').number(true, 2);
					},
					error : function (e) {
						console.log('Error: ' + e);
					},
				});
				limpiaFormFact();
				disabledEnabledFields(true);
			
		} else {
			$("#error-alert-asesor").show();
			$("#error-head-asesor").text(ERROR);
			$("#error-body-asesor").text(COMPLETE);
		}
	}
	else{
		$("#btn_agregar").show();
		$("#btn_guardar").hide();
		$('#tablaDesgloseCompAsesor tr').eq($("#selectedIndex").val()).removeClass('highlight');
		$("#selectedIndex").val("");
		confPanelFacturas("true");
		$("#cargaArchivos").show();
	}
}

function updateGrid(response) {
	$("#btn_agregar").show();
	$("#btn_guardar").hide();
	$("#cargaArchivos").show();
	$('#tablaDesgloseCompAsesor tr').eq($("#selectedIndex").val()).removeClass('highlight');
	$("#selectedIndex").val("");
	var i = response.selectedIndex;
	$('#tablaDesgloseCompAsesor tr').eq(i).find('td').eq(1).html("<input type=\"text\" onchange=\"sumSbtComp()\" id=\"lstFactDto"+(i-1)+".subTotal\" name=\"lstFactDto["+(i-1)+"].subTotal\" class=\"form-control subtotales currencyFormat\" value=\""+response.subTotal+"\" disabled=\"\">");
	$('#tablaDesgloseCompAsesor tr').eq(i).find('td').eq(4).html(response.folio);
	$('#tablaDesglose tr').eq(i).find('td').eq(3).html(response.folio);
	$('#tablaDesglose tr').eq(i).find('td').eq(4).html("<input type=\"text\" id=\"lstFactDto"+(i-1)+".subTotal\" name=\"lstFactDto["+(i-1)+"].subTotal\" class=\"form-control currencyFormat\" value=\""+response.subTotal+"\" disabled=\"\">");
	$('#tablaDesglose tr').eq(i).find('td').eq(6).html("<input type=\"text\" id=\"lstFactDto"+(i-1)+".total\" name=\"lstFactDto["+(i-1)+"].total\" class=\"form-control currencyFormat\" value=\""+response.subTotal+"\" disabled=\"\">");
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
	var length = $("#tablaDesgloseCompAsesor > tbody > tr").length;
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
	if ($("#tablaDesgloseCompAsesor > tbody > tr").length == 0) {
		$("#tablaDesgloseCompAsesor").addClass("errorx");
		error = true;
	} else {
		$("#tablaDesgloseCompAsesor").removeClass("errorx");
	}
	if (error) {
		$("#error-head-asesor").text(ERROR);
		$("#error-body-asesor").text(COMPLETE);
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
		$("#error-head-asesor").text(ERROR);
		$("#error-body-asesor").text(COMPLETE);
		error_alert();
		return false;
	} else {
		$("#enviarSolicitud").prop("disabled", false);
		disabledEnabledFields(false);
		return true;
	}
}

function valCapturaFactEdit(conComprobante) {
	var error = false;
	
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
	
	if (error) {
		$("#error-head-asesor").text(ERROR);
		$("#error-body-asesor").text(COMPLETE);
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

function sumSbtComp() {
	var sum = 0;
	if (!$.isEmptyObject($.find('.subtotales'))) {
		$('.subtotales').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#sbt").val(sum);
				$("#sbtComp").val(sum);
				$("#sbtComp").width($(".sbts:first").width());
				$("#tdSbt").width(1);
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#sbt").val(sum);
	}
	
	if (!$.isEmptyObject($.find('.subtotalesNM'))) {
		$('.subtotalesNM').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#sbt").val(sum);
				$("#sbtComp").val(sum);
				$("#sbtComp").width($(".sbts:first").width());
				$("#tdSbt").width(1);
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
				$("#ivatot").width($(".ivas:first").width());
				$("#ivatotComp").val(sum);
				$("#ivatotComp").width($(".ivasComp:first").width());
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#ivatot").val(sum);
	}
	if(tipoComprobacion == 2){
		if (!$.isEmptyObject($.find('.ivasComp'))) {
			$('.ivasComp').each(function () {
				if ($.isNumeric($(this).val())) {
					sum += parseFloat($(this).val());
					$("#ivatot").val(sum);
					$("#ivatot").width($(".ivas:first").width());
					$("#ivatotComp").val(sum);
					$("#ivatotComp").width($(".ivasComp:first").width());
				} else {
					$(this).val(0);
				}
			});
		} else {
			//subtotal es cero.
			$("#ivatot").val(sum);
		}
	}
	
	var sum = 0;
	if (!$.isEmptyObject($.find('.ieps'))) {
		$('.ieps').each(function () {
			if ($.isNumeric($(this).val())) {
				sum += parseFloat($(this).val());
				$("#iepstot").val(sum);
				$("#iepstot").width($(".ieps:first").width());
			} else {
				$(this).val(0);
			}
		});
	} else {
		//subtotal es cero.
		$("#iepstot").val(sum);
	}
	
	console.log("width "+$("#headerTotal").width());
	$("#rightPadding").width($("#headerTotal").width());
}

function ccOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var idCtaCont = $(this).val();
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updCuentaContCompAsesor",
		async : true,
		data : "numrow=" + numRow +
		"&idCtaCont=" + idCtaCont,
		success : function (response) {},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

//function locacionOnChange() {
//	var numRow = $(this).closest('tr').find('td:first').text();
//	var idLocacion = $(this).val();
//	$.ajax({
//		type : "GET",
//		cache : false,
//		url : url_server+"/updLocacion",
//		async : true,
//		data : "isFirst=" + false +
//		"&numrow=" + numRow +
//		"&idLocacion=" + idLocacion,
//		success : function (response) {
//			if (response != "") {
//				$("#lstFactDtoCC" + (numRow - 1)).empty().append(response);
//			}
//		},
//		error : function (e) {
//			console.log('Error: ' + e);
//		},
//	});
//}

function conceptoOnChange() {
	var numRow = $(this).closest('tr').find('td:first').text();
	var concepto = $(this).val();
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/updConceptoFactCompAsesor",
		async : true,
		data : "numrow=" + numRow +
		"&concepto=" + concepto,
		success : function (response) {
			$('#tablaDesglose tr').eq(numRow).find('td').eq(2).html(concepto);
			$("#rightPadding").width($("#headerTotal").width());
			$("#sbtComp").width($(".sbts:first").width());
			$("#ivatotComp").width($(".ivasComp:first").width());
		},
		error : function (e) {
			console.log('Error: ' + e);
		},
	});
}

//function getCCByRowTable() {
//	$('#tablaDesgloseCompAsesor tr').each(function (i, row) {
//		if (i > 0) {
//			var numRow = $(this).closest('tr').find('td:first').text();
//			var idLocacion = $(this).closest('tr').find('td').eq(2).find('select').val();
//			$.ajax({
//				type : "GET",
//				cache : false,
//				url : url_server+"/updLocacion",
//				async : true,
//				data : "isFirst=" + true +
//				"&numrow=" + numRow +
//				"&idLocacion=" + idLocacion,
//				success : function (response) {
//					if (response != "") {
//						$("#lstFactDtoCC" + (numRow - 1)).empty().append(response);
//					}
//				},
//				error : function (e) {
//					console.log('Error: ' + e);
//				},
//			});
//		}
//	});
//}

function getTotalSol() {
	$.ajax({
		type : "GET",
		cache : false,
		url :  url_server+"/getTotalSolComp",
		async : true,
		data : "numrows=" + length,
		success : function (response) {
			$("#reembTot").val(response);
			$("#reembTotComp").val(response);
			$("#saldo").val($("#importeTotal").val() - response);
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
		url :  url_server+"/updTotalOnDeleteCompAsesor",
		async : true,
		data : "numrow=" + numRow,
		success : function (response) {
			$("#reembTot").val(response);
			$("#reembTotComp").val(response);
			$("#saldo").val($("#importeTotal").val() - response);
			$("#rightPadding").width($("#headerTotal").width());
			$("#sbtComp").width($(".sbts:first").width());
			$("#ivatotComp").width($(".ivasComp:first").width());
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
  $("#loaderXML").show();
  if(document.getElementById("file").files.length != 0){
	  console.log("entro a valXML");
	  $("#error-alert-asesor").hide();
	  
	  var data = new FormData();
	  jQuery.each(jQuery('#file')[0].files, function(i, file) {
		  data.append('file-'+i, file);
	  });
	  
	  if(data){
		  jQuery.ajax({
				url : url_server+"/resolverXML",
				data: data,
				cache: false,
				contentType: false,
				processData: false,
				type: 'POST',
				success: function(data){
					console.log(data);
					$("#loaderXML").hide();
					$("#file").removeClass("errorx");

					console.log(data.validxml);
					if(data.validxml == "true"){
						if(data.idMoneda == $("#monedaDet").val() || data.idMoneda == 'null'){
							if($("#compania").val() == data.idCompania){
								$("#compania").val(data.idCompania);
								$("#proveedor").val(data.idProveedor);
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

								if(data.wsmensaje != null){
									if(data.wscode == 0){
										$("#ok-head-asesor").text(FACTURA_VALIDA);
										$("#ok-body-asesor").text(data.wsmensaje);
										ok_alert();
									}else{
										$("#error-head-asesor").text(ERROR);
										$("#error-body-asesor").text(data.wsmensaje);
										error_alert();
									}
								}
								refreshTooltip();
							}
							else{
								$("#file").addClass("errorx");
								$("#error-alert-asesor").show();
								$("#error-head-asesor").text(ERROR);
								$("#error-body-asesor").text(DIF_RAZON_SOCIAL.replace("rsFactura", data.rsFactura).replace("rsSolicitante", $("#compania option:selected").text()));
								$("#file").val(null);
								$("#pdf").val(null);
							}
						}
						else{
							$("#file").addClass("errorx");
							$("#error-alert-asesor").show();
							$("#error-head-asesor").text(ERROR);
							$("#error-body-asesor").text(DIF_MONEDA);
							$("#file").val(null);
							$("#pdf").val(null);
						}								
					}
					else{
						if(data.wscode == 0){
							if(data.faltaProveedor == "true"){
								$("#file").addClass("errorx");
								$("#error-alert-asesor").show();
								$("#error-head-asesor").text(ERROR);
								$("#error-body-asesor").text(NOPROVEEDOR);
								$("#pdf").val(null);
								$("#file").val(null);
							}else{
								$("#file").addClass("errorx");
								$("#error-alert-asesor").show();
								$("#error-head-asesor").text(ERROR);
								$("#error-body-asesor").text(NOXML);
								$("#pdf").val(null);
								$("#file").val(null);
							}
						}
						else{
							$("#file").addClass("errorx");
							$("#error-alert-asesor").show();
							$("#error-head-asesor").text(ERROR);
							$("#error-body-asesor").text(data.wsmensaje);
							$("#file").val(null);
							$("#pdf").val(null);
						}
					}
				}
			});
	  }
	  
  }else{
	  $("#loaderXML").hide();
	  $("#file").addClass("errorx");
	  $("#error-alert-asesor").show();
	  $("#error-head-asesor").text(ERROR);
	  $("#error-body-asesor").text(COMPLETE);
  }
}

/*
* Calcula linea de grid
* Estatus: OK
*/
function calculateLine() {
	console.log("tr");
	$('#tablaDesgloseCompAsesor tr').each(function (i, row) {
		if (i > 0) {
			//console.log(i + "<- iteracion");
			var nlinea = $(this).find(".linea").text(i);
			//console.log(nlinea + "<- texto");
		}
	});
	$('#tablaDesglose tr').each(function (i, row) {
		if (i > 0) {
			//console.log(i + "<- iteracion");
			var nlinea = $(this).find(".linea").text(i);
			//console.log(nlinea + "<- texto");
		}
	});
}

function error_alert() {
	$("#error-alert-asesor").fadeTo(2000, 500).slideUp(500, function () {
		$("#error-alert-asesor").hide();
	});
}

function ok_alert() {
	$("#ok-alert-asesor").fadeTo(5000, 500).slideUp(500, function () {
		$("#ok-alert-asesor").hide();
	});
}

/*
* activa o desactiva componentes
* Estatus: ?
*/
function disabledEnabledFields(state) {
	$("#locacion").prop('disabled', state);
	$("#moneda").prop('disabled', state);
	$("#importeTotal").prop('disabled', state);
	$("#fecha_deposito").prop('disabled', state);
	$("#formaPago").prop('disabled', state);
	$("#beneficiario").prop('disabled', state);
	$("#saldo").prop('disabled', state);
	$("#reembTotComp").prop('disabled', state);
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
	$("#radioAsesor").prop('disabled', state);
	$("#radioProveer").prop('disabled', state);
}

$("#cancelar_button").click(function () {
	$.ajax({
		type : "GET",
		cache : false,
		url : url_server+"/cancelarSolicitud",
		async : false,
		data : "idSolicitud=" + idSolicitud,
		success : function (response) {
			if (response.resultado == 'true') {
				$(window).unbind('beforeunload');
				location.reload(true)
			}
		},
		error : function (e) {
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
	//var length = $("#tablaDesgloseCompAsesor > tbody > tr").length;
	var length = 0;
	if (length > 0) {
		if (validaGrid() == false) {
			if ($("#restante").val() == 0) {
				$("#sppc").removeClass("errorx");
				return true;
			} else {
				$("#error-head-asesor").text("Error: ");
				$("#error-body-asesor").text("Saldo pendiente por comprobar debe ser cero");
				$("#sppc").addClass("errorx");
				error_alert();
				return false;
			}
		} else {
			$("#error-head-asesor").text("Error: ");
			$("#error-body-asesor").text("Complete todos los campos.");
			error_alert();
			return false;
		}
	} else {
		$("#error-head-asesor").text("Error: ");
		$("#error-body-asesor").text("Debe tener por lo menos un desglose.");
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
		if (idStatus > 1) {
			$("#enviarSolicitud").hide();
			$("#solicitante").prop("disabled", true);
			$("#cancelar_boton").hide();
			$("#adjuntar_archivo").hide();
			$("#enviarSolicitud").hide();
			$("#concepto").prop("disabled", true);
			$(".subtotales").prop("disabled", true);
			$(".removerFila").prop("disabled", true);
			$(".locaciones").prop("disabled", true);
			$(".ccontable").prop("disabled", true);
			$(".conceptogrid").prop("disabled", true);

			//restantes
			disabledEnabledFields(true);

			//Enviada
			if (idStatus == 2) {
				$("#ok-head-asesor").text(ESTADO_SOLICITUD);
				$("#ok-body-asesor").text(ENVIADA_AUTORIZACION);
				ok_alert();
			}

			//Autorizada
			if (idStatus == 3) {
				$("#ok-head-asesor").text(ESTADO_SOLICITUD);
				$("#ok-body-asesor").text("Autorizada");
				ok_alert();
			}

			//rechazada
			if (idStatus == 4) {
				$("#error-head-asesor").text(ESTADO_SOLICITUD);
				$("#error-body-asesor").text("Rechazada");
				error_alert();
			}

			//validada pendiente de pago
			if (idStatus == 5) {
				$("#ok-head-asesor").text(ESTADO_SOLICITUD);
				$("#ok-body-asesor").text(VALIDADA_AUTORIZACION);
				ok_alert();
			}

			//pagada
			if (idStatus == 6) {
				$("#ok-head-asesor").text(ESTADO_SOLICITUD);
				$("#ok-body-asesor").text("Pagada");
				ok_alert();
			}

			//cancelada
			if (idStatus == 7) {
				$("#error-head-asesor").text(ESTADO_SOLICITUD);
				$("#error-body-asesor").text(CANCELADA);
				$("#consultar_auth").hide();
				error_alert();
			}
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
	$(':input', '#formFactura').each(function () {
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
	$("#monedaDet").val($("#moneda").val());
	$("#file").val(null);
	$("#pdf").val(null);
	$("#comp").val(null);
}

/*
* Aplica configuración del formulario ocultar/mostrar componentes
* Estatus: OK
*/
function confPanelFacturas(val) {
	limpiaFormFact();
	if (val == "true") {
		$("#cargaComp").hide();
		$("#cargaDctos").show();
		$("#fieldProveedor").show();
		$("#fieldRFC").show();
		$("#fieldFolFiscal").show();
		$("#fieldRetenciones").show();
		$("#fieldIVA").show();
		$("#fieldIEPS").show();
		disabledEnabledFields(true);
	} else {
		$("#cargaComp").show();
		$("#cargaDctos").hide();
		$("#fieldProveedor").hide();
		$("#fieldRFC").hide();
		$("#fieldFolFiscal").hide();
		$("#fieldRetenciones").hide();
		$("#fieldIVA").hide();
		$("#fieldIEPS").hide();
		disabledEnabledFields(false);
		$("#reembTot").prop('disabled', true);
		$("#reembTotComp").prop('disabled', true);
		$("#compania").prop('disabled', true);
		if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_CAJA_CHICA) {
			$("#formaPago").prop('disabled', true);
		} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_REEMBOLSO) {
			$("#usuarioSolicitante").prop('disabled', true);
		} else if ($("#idTipoSolicitud").val() == TIPO_SOLICITUD_COMPROBACION_ANTICIPO) {
//			$("#fecha").prop('disabled', true);
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
	$('#tablaDesgloseCompAsesor tr').each(function (i, row) {
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
	} else {
		$('#compFalse').prop("checked", "checked")
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
	}

	if (empty) {
		return true;
	} else {
		return false;
	}
}

/*
 * Guarda temporalmente archivos cargados en grid
 * Estatus: OK
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
			url : url_server+"/guardarArchivosComprobacion",
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
	return true;
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

function anexarDoc(){

	  if(document.getElementById("file-sol").files.length != 0){ // vacío
		  
		 if(validarSize()){	// size	  
		  console.log("entro a anexarDoc()");
		  $("#errorMsg").hide();
		  $("#loaderXML").show();
		  
		  var data = new FormData();
		  jQuery.each(jQuery('#file-sol')[0].files, function(i, file) {
		      data.append('file-sol-'+i, file);
		  });
		  
		  data.append("idSolicitud",idSolicitudGlobal);
		  
		  
		  if(data){ // data
			  
			  jQuery.ajax({
				  
		    		url : url_server+"/saveSolicitudDocument",
				    data: data,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data){
				        console.log(data);
						$("#loaderXML").hide();
			      	    $("#file-sol").removeClass("errorx");
			      	    
			      	  if(data.anexado == "true"){
			      		 $("#file-sol").val(null);
			      		 $("#errorMsg").show();
						 $("#error-head-files").text(ATENCION);
						 $("#error-body-files").text(ARCHIVO_ANEXADO);
						 
						 //cargar lista
						 cargarLista();
						 
				        }else{
				        	if(data.invalido == "true"){
				        		$("#file-sol").val(null);
				        		$("#errorMsg").show();
								$("#error-head-files").text(ERROR);
								$("#error-body-files").text(EXTENSION_INVALIDA);  
				        	}else{
					        	if(data.no_anexado == "true"){
					        		$("#file-sol").val(null);
					        		$("#errorMsg").show();
									$("#error-head-files").text(ERROR);
									$("#error-body-files").text(ARCHIVO_NO_ANEXADO);  
					        	}else{
					        		if(data.vacio == "true"){
					        			$("#file-sol").val(null);
						        		$("#errorMsg").show();
										$("#error-head-files").text(ERROR);
										$("#error-body-files").text(ARCHIVO_VACIO);  
						        	}
					        	}
					        }
				        }
				    }
				});
		  } // data
		 } // size
	  }else{ // vacío
		  //$("#file-sol").addClass("errorx");
		  $("#errorMsg").show();
		  console.log("false");
		  $("#error-head-files").text(ERROR);
		  $("#error-body-files").text(ARCHIVO_NO_SELECCIONADO);
	}
};;


function editarGridComp(id) {
	$("#cargaArchivos").hide();
	$("#selectedIndex").val(id);
	$("#btn_agregar").hide();
	$("#btn_guardar").show();
	
	$('#tablaDesgloseCompAsesor tr').eq(id).addClass('highlight');
	
    if(id > 0){
    	//$('#comprobacionAsesor').modal('show'); 
	    console.log(id);
	        $.ajax({
	            type: "GET",
	        cache: false,
	        url: url_server+"/editarGridComp",
	        async: true,
	        data: "index=" + id,
	        success: function(data) {	        	
	        	if(data.conCompFiscal == "true"){
	        		confPanelFacturas("true");
	        		$('#compTrue').prop("checked", "checked");
		        	if($("#compania").val() == data.idCompania){
						$("#compania").val(data.idCompania);
						$("#proveedor").val(data.idProveedor);
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
		        	}
	        	}
	        	else{
	        		confPanelFacturas("false");
	        		$('#compFalse').prop("checked", "checked");
					$("#compania").val(data.idCompania);
					$("#proveedor").val(data.idProveedor);
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
	        	}

	        	
//	        	$("#descripcion").val(response.descripcion);
//	        	$("#numero").val(response.numero);
//	        	$("#idLocacion").val(response.id);
//	        	$("#idTipoLocacion").val(response.idTipoLocacion);
	        	// mostrando el modal
	        	//abrir ventana modal
	        	$('#comprobacionAsesor').modal('show'); 
	        },
	        error: function(e) {
	            console.log('Error: ' + e);
	        },
	    }); 
    
	}//if
}

function calculaSaldo() {	
	var length = $("#tablaDesgloseComp > tbody > tr").length;
	if (length > 0) {
	        $.ajax({
	            type: "GET",
	        cache: false,
	        url: url_server+"/calculaSaldoComp",
	        async: true,
	        data: "index=" + id,
	        success: function(data) {
	        	
	        },
	        error: function(e) {
	            console.log('Error: ' + e);
	        },
	    }); 
	}
}