	
	  $(document).ready(function() {
		  //jtable
		    $('#tablaUsuarios').DataTable({
	                responsive: true
	        });
	        
		  
	        $('#agregarUsuario').on('hidden.bs.modal', function () {
	        	console.log("modal cerro");
	        	// limpiar formulario.
	        	console.log("limpiar");
	        	$("#error-sign").hide();
	        	$("#error-rpt").hide();
	        	$("#numeroEmpleado").val(null).removeClass("errorx");
	        	$("#idUsuario").val(0).removeClass("errorx");
	        	$("#nombre").val(null).removeClass("errorx");
	        	$("#apellidoPaterno").val(null).removeClass("errorx");
	        	$("#apellidoMaterno").val(null).removeClass("errorx");
	        	$("#correoElectronico").val(null).removeClass("errorx");
	        	$("#cuenta").val(null).removeClass("errorx");
	        	$("#contrasena").val(null).removeClass("errorx");
	        	$("#contrasena2").val(null).removeClass("errorx");
	        	$("#compania").val('-1').removeClass("errorx");
	        	$("#locaciones_list").val('-1').removeClass("errorx");
	        	$("#perfil_list").val('-1').removeClass("errorx");
	        	$("#puesto_list").val('-1').removeClass("errorx");
	        	$("#idUsuarioJefe").val('-1').removeClass("errorx");
	
	        	$("#esSolicitanteB").prop("checked", false);
	        	$('[name="_esSolicitanteB"]').val(null);
	        	
	        	$("#esAutorizadorB").prop("checked", false);
	        	$('[name="_esAutorizadorB"]').val(null);

	        	$("#esFiguraContableB").prop("checked", false);
	        	$('[name="_esFiguraContableB"]').val(null);

	        	$("#especificaSolicitanteB").prop("checked", false);
	        	$('[name="_especificaSolicitanteB"]').val(null);

	        	$("#esBeneficiarioCajaChicaB").prop("checked", false);
	        	$('[name="_esBeneficiarioCajaChicaB"]').val(null);

	        	$('.selectpicker').selectpicker('refresh');
				$("#imagen_perfil").removeClass("errorx");
				$("#imagen_perfil").val(null);
	        });
	   });
