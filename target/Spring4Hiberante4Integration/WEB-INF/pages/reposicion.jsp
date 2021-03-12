<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%Etiquetas etiqueta = new Etiquetas("es");%>

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
			<form:form id="formReposicion" modelAttribute="reposicionDTO" action="saveReposicion" method="post">
			
				<form:hidden cssClass="blockedInput" value="${reposicionDTO.idSolicitudSession}" path="idSolicitudSession" />
				<!-- Título de la sección ----------------------- -->
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
								<%= etiqueta.SOLICITUD_REPOSICION_TITULO %>
							</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
				
				<!-- Alertas ----------------------- -->
				<div style="display: none;" id="error-alert" class="alert alert-danger fade in">
					<strong><span id="error-head"><%= etiqueta.ERROR %>:</span></strong>
					<span id="error-body"><%= etiqueta.COMPLETE %></span>
				</div>
				<div style="display: none;" id="ok-alert" class="alert alert-success fade in">
					<strong> <span id="ok-head"> </span>
					</strong> <span id="ok-body"><%= etiqueta.SOLICITUD_GUARDADA %>
					</span>
				</div>
			
				<!--botones de cabecera -->
				<div class="panel panel-default" id="panel-anticipo">
					<div class='blocker' style='position: absolute; background-color: white;z-index: 10;display: none;'></div>
					<div style="" class="panel-heading">
					   <c:if test="${idEstadoSolicitud != 7}">
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
							<button id="adjuntar_archivo"
								style="margin-left: 10px; float: right;" type="button"
								class="btn btn-primary" data-toggle="modal"
								data-target="#anexarDoc">
								<%=etiqueta.DOCUMENTO_SOPORTE%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud > 1}">
					   <button onclick="verDetalleEstatus(${reposicionDTO.idSolicitudSession})" id="consultar_auth" 
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-success"><%=etiqueta.CONSULTAR_AUTORIZACION%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button ${reposicionDTO.idSolicitudSession > 0 ? '' : 'disabled'}
							onclick="tieneAnticipoPendiente()" id="enviarSolicitud"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-danger"><%=etiqueta.ENVIAR_AUTORIZACION%></button>
						</c:if>
							
						
						<c:if test="${idEstadoSolicitud == 1}">
						<button id="cancelar_boton" onclick="cancelar()"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-warning"><%=etiqueta.CANCELAR%></button>
						</c:if>
						
						<c:if test="${idEstadoSolicitud == 0 || idEstadoSolicitud == 1 || idEstadoSolicitud == 4}">
						<button id="save_button" onclick="valTodo();"
							style="margin-left: 10px; float: right;" type="button"
							class="btn btn-default"><%=etiqueta.GUARDAR%></button>
						</c:if>
						
						</c:if>
					</div>

					<div class="panel-body">
					
						<!-- ROW 1 -->
						
					</div> 

				</div> 
				<!-- Fin de Botones de cabecera -->
            
           
          <!--  
           Estructura de contenido.
           divs-bootstrap, revisar layouts.
          -->
    
           </form:form>
           <!-- Fin de formAnticipo -->
        </div>
        <!-- /#page-wrapper -->
        
    </div>
    	<!-- Modal Documentos de soporte -->
    	<jsp:include page="modalArchivosSolicitud.jsp" />
    	<!-- Modal Cancelar -->
		<div id="modal-solicitud" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 style="float: left;">
							<span id="titulo-dialogo"><%=etiqueta.ATENCION%></span>
						</h4>
					</div>
					<div class="modal-body">

						<p id="mensaje-dialogo">
							<%=etiqueta.MENSAJE_DIALOGO_CON_XML%>
						</p>
					</div>
					<div class="modal-footer">

						<a style="display: none;" href="#" id="cambiarxml_button"
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO%></a> 
							<a onclick="cancelar_solicitud()" style="display: none;" href="#" id="cancelar_button"
							class="btn btn-danger" role="button"><%=etiqueta.DE_ACUERDO_CANCELAR%>
						</a> <a style="display: none;" href="#"
							id="cambiar_solicitante_button"  class="btn btn-danger"
							role="button"><%=etiqueta.DE_ACUERDO_CAMBIAR_SOLICITANTE%></a> <a
							href="#" id="cambiarxml_button_cancelar"
							onclick="cerrar_ventana()" class="btn btn-default" role="button"><%=etiqueta.SALIR%></a> <a href="#" style="display: none;" id="solicitud_button_cancelar"
							class="btn btn-default" role="button"><%=etiqueta.CANCELAR%></a>

					</div>
				</div>

			</div>
		</div>
		
  <jsp:include page="modalConsultarAutorizacion.jsp" />
    
  <jsp:include page="template/includes.jsp" />
  
	<script>
	var url = '${pageContext.request.contextPath}';
	var idSolicitud = '${reposicionDTO.idSolicitudSession}';
	var idEstadoSolicitud = '${idEstadoSolicitud}';
	var idSolicitudGlobal = idSolicitud;
	var url_server = url;
	var isCreacion = '${reposicionDTO.creacion}';
	var isModificacion = '${reposicionDTO.modificacion}';
	
	
	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/solicitudArchivos.js"></script>
	<script	src="${pageContext.request.contextPath}/resources/js/jquery.number.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/statusBehavior.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/anticipo.js">	</script>
	<script	src="${pageContext.request.contextPath}/resources/js/enviarAProceso.js"></script>
</body>
</html>
