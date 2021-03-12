<%@page import="com.lowes.util.Etiquetas"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<jsp:include page="template/head.jsp" />

</head>

<body>


	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%= etiqueta.USUARIOS %></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div style="height: 54px;" class="panel-heading">
							<button style="float: right;" type="button"
								class="btn btn-primary boton_crear" data-toggle="modal"
								data-target="#agregarUsuario"><%= etiqueta.AGREGAR_USUARIO %></button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="tablaUsuarios">
									<thead>
										<tr>
											<th><%= etiqueta.ID %></th>
											<th><%= etiqueta.NUMERO %></th>
											<th><%= etiqueta.NOMBRE %></th>
											<th><%= etiqueta.APELLIDO_PATERNO %></th>
											<th><%= etiqueta.APELLIDO_MATERNO %></th>
											<th><%= etiqueta.COMPANIA %></th>
											<th><%= etiqueta.PERFIL %></th>
											<th><%= etiqueta.LOCACION %></th>
											<th><%= etiqueta.CORREO %></th>
											<th><%= etiqueta.CUENTA %></th>
											<th width="10%"><%= etiqueta.ACCIONES %></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${modelo.usuariosList}" var="ulist">
											<tr class="odd gradeX">
												<td><c:out value="${ulist.idUsuario}" /></td>
												<td><c:out value="${ulist.numeroEmpleado}" /></td>
												<td><c:out value="${ulist.nombre}" /></td>
												<td><c:out value="${ulist.apellidoPaterno}" /></td>
												<td><c:out value="${ulist.apellidoMaterno}" /></td>
												<td><c:out value="${ulist.compania.descripcion}" /></td>
												<td><c:out value="${ulist.perfil.descripcion}" /></td>
												<td><c:out value="${ulist.locacion.descripcion}" /></td>
												<td><c:out value="${ulist.correoElectronico}" /></td>
												<td><c:out value="${ulist.cuenta}" /></td>
												<td width="10%" class="center">
													<button title="<%= etiqueta.ELIMINAR %>" onclick="eliminar(${ulist.idUsuario})"
														style="height: 22px;" type="button"
														class="btn btn-xs btn-danger">
														<span class="glyphicon glyphicon-trash"></span>
													</button>
													<button title="<%= etiqueta.EDITAR %>" onclick="editar(${ulist.idUsuario})"
														style="height: 22px;" type="button"
														class="boton_editar btn btn-xs btn-warning">
														<span class="glyphicon glyphicon-pencil"></span>
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		<!-- /#page-wrapper -->
	</div>

	<!-- modal agregar usuario -->
	<div class="modal fade" id="agregarUsuario" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<form:form enctype="multipart/form-data" id="usuariosForm" cssClass="form-horizontal"
				modelAttribute="usuario" method="post" action="saveUsuario">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">

						<div id="error-sign" style="display: none;"
							class="alert alert-warning">
							<strong><%= etiqueta.ATENCION %> :</strong> <span
								id="mensaje-label"></span>
						</div>

						<div id="error-rpt" style="display: none;"
							class="alert alert-danger">
							<strong><%=  etiqueta.DUPLICADOS %>: </strong>
							<%= etiqueta.ATENCION_DUPLICADOS %> , <span id="mensaje-label-rpt"></span>
						</div>


						<!--  numero de empleado -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.NUMERO_EMPLEADO %></form:label>
							</div>
							<div class="col-xs-7">
								<form:hidden id="idUsuario" path="idUsuario" value="0" />
								<form:hidden id="globalBan" path="" value="" />
								<form:input type="number" cssClass="form-control" id="numeroEmpleado" path="numeroEmpleado" value="" />
							</div>
						</div>

						<!--  nombre -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.NOMBRE %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input cssClass="form-control" id="nombre" path="nombre"
									value="" />
							</div>
						</div>

						<!--  apellido paterno -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.APELLIDO_PATERNO %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input cssClass="form-control" id="apellidoPaterno"
									path="apellidoPaterno" value="" />
							</div>
						</div>

						<!--  apellido materno -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.APELLIDO_MATERNO %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input cssClass="form-control" id="apellidoMaterno"
									path="apellidoMaterno" value="" />
							</div>
						</div>

						<!--  email -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.CORREO %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input type="email" cssClass="form-control"
									id="correoElectronico" path="correoElectronico" value="" />
							</div>
						</div>


						<!--  cuenta -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.CUENTA %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input type="text" cssClass="form-control" id="cuenta"
									path="cuenta" value="" />
							</div>
						</div>

						<!--  password -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.CONTRASENA %></form:label>
							</div>
							<div class="col-xs-7">
								<form:input type="password" cssClass="form-control"
									id="contrasena" path="contrasena" value=""/>
							</div>
						</div>

						<!--  password2 -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.CONTRASENA_CONFIRMAR %></form:label>
							</div>
							<div class="col-xs-7">
								<input type="password" class="form-control" id="contrasena2"
									value=""/>
							</div>
						</div>


						<!--  combo compañias -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="compania"><%= etiqueta.COMPANIA %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idCompania" cssClass="form-control selectpicker" data-live-search="true"
									id="compania">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.companiaList}" var="clist">
										<option value="${clist.idcompania}">${clist.descripcion}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<!--  combo locaciones -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="locaciones_list"><%= etiqueta.LOCACION %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idLocacion" cssClass="form-control selectpicker" data-live-search="true"
									id="locaciones_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.locacionList}" var="llist">
										<option value="${llist.idLocacion}">${llist.numero} - ${llist.descripcion}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<!--  combo perfiles -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="perfil_list"><%= etiqueta.PERFIL %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idPerfil" cssClass="form-control selectpicker" data-live-search="true"
									id="perfil_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.perfilList}" var="plist">
										<option value="${plist.idPerfil}">${plist.descripcion}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>


						<!--  combo puesto -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="puesto_list"><%= etiqueta.PUESTO %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idPuesto" 
									cssClass="form-control selectpicker" 
									data-live-search="true"
									id="puesto_list">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.puestoList}" var="pslist">
										<option value="${pslist.idPuesto}">${pslist.descripcion}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						
						<!--  combo Jefe -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="puesto_list"><%= etiqueta.JEFE %></label>
							</div>
							<div class="col-xs-7">
								<form:select path="idUsuarioJefe" cssClass="form-control selectpicker" data-live-search="true"
									id="idUsuarioJefe" onChange="getValue();">
									<option value="-1"><%= etiqueta.SELECCIONE %></option>
									<c:forEach items="${modelo.usuariosJefeList}" var="jlist">
										<option class="jefe-id-${jlist.idUsuario}" value="${jlist.idUsuario}">${jlist.nombre} ${jlist.apellidoPaterno}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						
							<!--  FOTO PERFIL -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<form:label path=""><%= etiqueta.IMAGEN_PERFIL %></form:label><br>
								(jpg,png: 1mb max)
							</div>
							<div class="col-xs-7">
								<input value="" id="imagen_perfil" name="imagenp" type="file">
								<form:checkbox cssStyle="display:none;" id="tieneFotoPerfil" path="tieneFotoPerfil" value="" />
								<span id="fotoNombre"></span>
							</div>
						</div>

						<!--  solicitante -->
						<div class="form-group">
						<div class="control-label col-xs-4">
								<label for="esSolicitanteB"><%= etiqueta.SOLICITANTE %></label>
							</div>
							<div class="checkbox col-xs-7">
								<form:checkbox id="esSolicitanteB" path="esSolicitanteB" value="" />
							</div>
						</div>
						
						<!--  AUTORIZADOR -->
						<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="esAutorizadorB"><%= etiqueta.AUTORIZADOR %></label>
							</div>
							<div class="checkbox col-xs-7">
								<form:checkbox id="esAutorizadorB" path="esAutorizadorB" value="" />
							</div>
						</div>
						
						<!--  FIGURA CONTABLE -->
					<%-- 	<div class="form-group">
							<div class="control-label col-xs-4">
								<label for="esFiguraContableB"><%= etiqueta.FIGURA_CONTABLE %></label>
							</div>
							<div class="checkbox col-xs-7">
								<form:checkbox id="esFiguraContableB" path="esFiguraContableB" value="" />
							</div>
						</div>
						 --%>
						<!--  ESPECIFICA SOLICITANTE -->
						<div class="form-group">
						<div class="control-label col-xs-4">
								<label for="especificaSolicitanteB"><%= etiqueta.ESPECIFICA_SOLICITANTE %></label>
							</div>
							<div class="checkbox col-xs-7">
								<form:checkbox id="especificaSolicitanteB" path="especificaSolicitanteB" value="" />
							</div>
						</div>
						
						<!--  BENEFICIARIO CAJA CHICA -->
						<div class="form-group">
						<div class="control-label col-xs-4">
								<label for="esBeneficiarioCajaChicaB"><%= etiqueta.BENEFICIARIO_CAJA_CHICA %></label>
							</div>
							<div class="checkbox col-xs-7">
								<form:checkbox id="esBeneficiarioCajaChicaB" path="esBeneficiarioCajaChicaB" value="" />
							</div>
						</div>


					</div>
					<div class="modal-footer">
						<input id="enviarUsuario" type="submit"
							data-loading-text="Validando..." id="saveUsuarios"
							class="btn btn-primary" value="<%= etiqueta.GUARDAR %>" />
						<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CERRAR %></button>
					</div>
				</div>
			</form:form>

		</div>
	</div>


	<!-- Modal Warning Eliminar -->
	<div id="myModalEliminar" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<h4><%= etiqueta.ATENCION_ELIMINAR %></h4>
				</div>
				<div class="modal-footer">
					<a href="#" id="eliminarButton" class="btn btn-danger"
						role="button"><%= etiqueta.ELIMINAR %></a>
					<button type="button" class="btn btn-default" data-dismiss="modal"><%= etiqueta.CANCELAR %></button>
				</div>
			</div>

		</div>
	</div>

	<jsp:include page="template/includes.jsp" />

	<!-- DataTables JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/usuario.js"></script>
			

	<script>
    
	e_titulo = '<%= etiqueta.USUARIO %>';
	
	$(document).ready(function () {
		
		// Función para ordenar select --------------------
		function ordenaSelect (id) {
			console.log("Iniciando ordenaSelect");
			$select = $('option',id);
			
			var ops = $select.length;
			console.log("OPS:",ops);
			textos = new Array();
			
			for (i=1; i<ops; i++) {
				var $s = $select.eq(i);
				textos[i] = $s.text()+"%%"+$s.val();
				console.log(textos[i]);
			}
			
			textos.sort();
			
			for (i=0; i<ops-1; i++) {
				var $s = $select.eq(i+1);
				var name = textos[i].split("%%")[0];
				var id = textos[i].split("%%")[1];
				$s.text(name);
				$s.val(id);
			}
			
			 $.each($select, function (index) {
				 console.log($select.eq(index).val());
				 $select.eq(index).select(function () {
					 console.log("d");
				 });
				 $select.eq(index).hover(function () {
					 console.log("h");
				 });
				 $select.eq(index).click(function () {
					 console.log("c");
				 });

			 });
			 
			 $('#agregarUsuario').on('hidden', function () {
				 
				 
		     })
			 
			 
			 // Comprobar VALUE
			/*
			name = "";
			 $('#compania').change(function () {
				 $( "#compania option:selected" ).each(function() {
				      console.log($( this ).val() );
				    });
			 }); */
				
			
			}
		// Fin de función para ordenar select -------------
		ordenaSelect('#compania');
		});
		
	
    $(function(){
    	
    	  $("#usuariosForm").submit(function(event){
    		  
    		  console.log( $("#usuariosForm").serialize() );
    		  
    		//inicializadores  
    	/* 	$("#enviarUsuario").button('loading');
    		$("#loader-lowes").show(); */
		    $("#error-sign").hide();
			$("#error-pass").hide();
			$("#imagen_perfil").removeClass("errorx");
		    $("#mensaje-label").text("<%= etiqueta.COMPLETE %>");
			
	        var ban = false;
			var mensaje = null;
			
		   
			// validar nombre
			if($("#nombre").val() == ""){
				ban = true;
				$("#nombre").addClass("errorx");
			}else{
				$("#nombre").removeClass("errorx");
			}
			
			//appellido p
			if($("#apellidoPaterno").val() == ""){
				ban = true;
				$("#apellidoPaterno").addClass("errorx");
			}else{
				$("#apellidoPaterno").removeClass("errorx");
			}
			
			//apellido m
			if($("#apellidoMaterno").val() == ""){
				ban = true;
				$("#apellidoMaterno").addClass("errorx");
			}else{
				$("#apellidoMaterno").removeClass("errorx");
			}
			
			//email
		   if($("#correoElectronico").val() == ""){
		   	ban = true;
				$("#correoElectronico").addClass("errorx");
		   }else{
				$("#correoElectronico").removeClass("errorx");
		   }
		   
			//cuenta
			if($("#cuenta").val() == ""){
				ban = true;
				$("#cuenta").addClass("errorx");
			}else{
				$("#cuenta").removeClass("errorx");
			}
			
			//contrasena
			if($("#contrasena").val() == ""){
				 ban = true;
				$("#contrasena").addClass("errorx");
			}else{
				$("#contrasena").removeClass("errorx");
			}
		
			//compania
			if($("#compania").val() == '-1'){
				ban = true;
				$('[data-id="compania"]').addClass("errorx");
			}else{
				$('[data-id="compania"]').removeClass("errorx");
			}
			
			//locacion
			if($("#locaciones_list").val() == '-1'){
				 ban = true;
				$('[data-id="locaciones_list"]').addClass("errorx");
			}else{
				$('[data-id="locaciones_list"]').removeClass("errorx");
			}
			
			
			//validar numero de empleado
			if($('#numeroEmpleado').val() <= 0){
				ban = true;
				$("#mensaje-label").text("<%= etiqueta.ATENCION_NUMERICO %>");
				$('#numeroEmpleado').addClass("errorx");
			}else{
				$('#numeroEmpleado').removeClass("errorx");
			}

			//Puesto
			if($("#puesto_list").val() == '-1'){
				ban = true;
				$('[data-id="puesto_list"]').addClass("errorx");
			}else{
				$('[data-id="puesto_list"]').removeClass("errorx");
			}
			
			//perfil
			if($("#perfil_list").val() == '-1'){
				ban = true;
				$('[data-id="perfil_list"]').addClass("errorx");
			}else{
				$('[data-id="perfil_list"]').removeClass("errorx");
			}

			//jefe			
			if($("#idUsuarioJefe").val() == '-1'){
				ban = true;
				$('[data-id="idUsuarioJefe"]').addClass("errorx");
			}else{
				$('[data-id="idUsuarioJefe"]').removeClass("errorx");
			}
			

			if(ban == false){
				if($("#contrasena").val() != $("#contrasena2").val()){
					ban = true;
					$("#mensaje-label").text("<%= etiqueta.ATENCION_CONTRASENAS %>");
					$("#contrasena").addClass("errorx");
					$("#contrasena2").addClass("errorx");
		
				}else{
					$("#contrasena2").removeClass("errorx");
					$("#contrasena2").removeClass("errorx");
				}
			}

			if(ban){
				
				//unset
	    		/* $("#enviarUsuario").button('reset');
	    		$("#loader-lowes").hide(); */
	    		
				$("#error-sign").show();
				loading(false);
				
				  event.preventDefault();
			      return false;
			      
			}else{
				
				//unset
	    		
				/* event.preventDefault();
			      return false; */
			    var id =      $('#idUsuario').val();
				var numero =  $('#numeroEmpleado').val();
			    var correo =  $("#correoElectronico").val();
			    var cuenta =  $("#cuenta").val();
			    var idLocacion =  $("#locaciones_list").val();
			    console.log("IdLocacion ---- >  " +idLocacion );
			      
			    loading(true);
			      $.ajax({
			    		type : "GET",
			    		cache : false,
			    		url : "${pageContext.request.contextPath}/checkUsuario",
			    		async : false,
			    		data : "id=" + id + "&numeroEmpleado=" + numero	+ "&cuenta=" + cuenta + "&idLocacion="+ idLocacion,
			    		success : function(response) {
			    			loading(false);
			    			var rpt = false;
			    			$("#globalBan").val(rpt);

			    			console.log(response);
			    			$("#enviarUsuario").button('reset');
			    			$("#loader-lowes").hide();

			    			if (response.numeroEmpleado == "true") {
			    				rpt = true;
			    				$("#numeroEmpleado").addClass("errorx");
			    				mensaje = "Numero de empleado, ";
			    			}

			    			if (response.cuenta == "true") {
			    				rpt = true;
			    				$("#cuenta").addClass("errorx");
			    				mensaje = mensaje + "Cuenta, ";

			    			}
			    			
			    			
			    			if($("#esBeneficiarioCajaChicaB").is(":checked")){
				    			if(response.locacion == "true"){
				    				rpt = true;
				    				$("#locaciones_list").addClass("errorx");
				    				$('.selectpicker').selectpicker('refresh');
				    				mensaje =  "El usuario "+response.usuarioLocacion+" ya es beneficiario de Caja Chica para la locación seleccionada,debes cambiar esa configuración antes de asignar un nuevo beneficiario. ";
				    				$("#mensaje-label-rpt").text(mensaje);
				    			}
			    			}

			    			/* if (response.correoElectronico == "true") {
			    				rpt = true;
			    				$("#correoElectronico").addClass("errorx");
			    				mensaje = mensaje + "Correo electronico, ";
			    			} */
			    			
			    			

			    			if (rpt == false) {
			    				
			    				
			 				   if(document.getElementById("imagen_perfil").files.length > 0){
			 					   // si tiene anexada una imagen la valida.
			 					   console.log("suaurios sumit tiene filees");
			 						var data = new FormData();
					  				  jQuery.each(jQuery('#imagen_perfil')[0].files, function(i, file) {
					  				      data.append('file-'+i, file);
					  				  });
					  				  
					    				$.ajax({
					    					url : "${pageContext.request.contextPath}/validarIMG",
										    data: data,
										    cache: false,
										    contentType: false,
										    processData: false,
										    type: 'POST',
								    		success : function(response) {
								    			if(response.valido == true){
								    				// imagen valida se envia
								    				$("#usuariosForm")[0].submit();
								    				loading(true);
								    			}else{
								    				$("#imagen_perfil").addClass("errorx");
								    				$("#mensaje-label").text("<%= etiqueta.ERROR_IMAGEN %>");
								    				$("#error-sign").show();
								    			}
								    		},
								    		 error: function(e) {
								    			   $("#imagen_perfil").addClass("errorx");
								    				$("#mensaje-label").text("<%= etiqueta.ERROR_VALIDA_IMAGEN %>");
								    				$("#error-sign").show();
							                  }
					    				});  
			 				   }else{
			 					   console.log("suaurios sumit ");
			 					    // sino tiene cumple con todas la validaciones y hace submit.
			 					    console.log($("#usuariosForm").serialize());
				    				$("#usuariosForm")[0].submit();
				    				loading(true);
			 				   }

			    			} else {
			    				$("#error-rpt").show();
			    				event.preventDefault();
			    				return false; 
			    			}

			    		},
			    		error : function(e) {
			    			loading(false);
			    			console.log('Error: ' + e);
			    		},
			    	});  
			      
				
				event.preventDefault();
				return false; 
 		}

		 }); 
    	
    });
    
    
    
    
    function editar(id){
  	  if(id > 0){
  		  
  		    $.ajax({
                  type: "GET",
                  cache: false,
                  url: "${pageContext.request.contextPath}/getUsuarioEdit",
                  async: true,
                  data: "id=" + id,
                  success: function(response) {

                  	console.log(response);
                  	$("#idUsuario").val(response.idUsuario);
                  	$("#numeroEmpleado").val(response.numeroEmpleado);
                  	$("#nombre").val(response.nombre);
                  	$("#apellidoPaterno").val(response.apellidoPaterno);
                  	$("#apellidoMaterno").val(response.apellidoMaterno);
                  	$("#correoElectronico").val(response.correoElectronico);
                  	$("#cuenta").val(response.cuenta);
                  	$("#contrasena").val(response.contrasena);
                  	$("#contrasena2").val(response.contrasena);
                  	$("#compania").val(response.idCompania);
                  	$("#locaciones_list").val(response.idLocacion);
                  	$("#perfil_list").val(response.idPerfil);
                  	$("#puesto_list").val(response.idPuesto);
                  	
                  	if (response.idJefe == 0){
                  		$('select[id=idUsuarioJefe]').val(-1);
                  		$('.selectpicker').selectpicker('refresh');
                  	}
                  	else{
                  		$('select[id=idUsuarioJefe]').val(response.idJefe);
                  		$('.selectpicker').selectpicker('refresh')
                  	}
                  	
              		// Se eleimina el elemento que corresponde al usuario editado
              		// para que no pueda ser seleccionado como jefe
              		$('.jefe-id-'+id,'.dropdown-menu').parent().remove();
                  	
                  	
                  	if(response.solicitante == 1){
                  		$("#esSolicitanteB").prop("checked", true);
                  	}
                  	else{
                  		$("#esSolicitanteB").prop("checked", false);
                  	}              	
                  	if(response.autorizador == 1){
                  		$("#esAutorizadorB").prop("checked", true);
                  	}
                  	else{
                  		$("#esAutorizadorB").prop("checked", false);
                  	}
                  	
                  	if(response.figuraContable == 1){
                  		$("#esFiguraContableB").prop("checked", true);
                  	}
                  	else{
                  		$("#esFiguraContableB").prop("checked", false);
                  	}
                  	if(response.especificaSolicitante == 1){
                  		$("#especificaSolicitanteB").prop("checked", true);
                  	}
                  	else{
                  		$("#especificaSolicitanteB").prop("checked", false);
                  	}
                  	if(response.beneficiarioCajaChica == 1){
                  		$("#esBeneficiarioCajaChicaB").prop("checked", true);
                  	}
                  	else{
                  		$("#esBeneficiarioCajaChicaB").prop("checked", false);
                  	}
                  	
                  	$('#agregarUsuario').modal('show'); 
                  	
                  },
                  error: function(e) {
                      console.log('Error: ' + e);
                  },
              });  
  	  }
    }
    
    function eliminar(id){
		console.log(id);

	if(id > 0){  
		console.log(id);
	  	$("#eliminarButton").prop("href","eliminarUsuario?id="+id);
	  	$("#myModalEliminar").modal('show');
     }
  } 
    
    function submitUsuarioForm(){
  	    console.log("submitUsuarioForm");
    }

    function getValue() {
        var x = document.getElementById("idUsuarioJefe").value;
        $("input[name='idUsuarioJefe']").val(x);
    }

    </script>
</body>
</html>
