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
						<h1 class="page-header"><%=etiqueta.DASHBOARD%></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->

			<c:if test="${esSolicitante eq 1 }">
				<div class="row">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-pencil-square-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${capturadas}</div>
										<div><%=etiqueta.CAPTURADAS%></div>
									</div>
								</div>
							</div>
							<a
								href="solicitudResumen?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_NUEVA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${autorizacion}</div>
										<div><%=etiqueta.EN_AUTORIZACION%></div>
									</div>
								</div>
							</div>
							<a
								href="solicitudResumen?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR %>&idEstadoSolicitud2=<%= Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					
					
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-red">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-exclamation-circle fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${rechazadas}</div>
										<div><%=etiqueta.RECHAZADAS%></div>
									</div>
								</div>
							</div>
							<a
								href="solicitudResumen?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					
				</div>
				
				
				<%-- <div class="row">
				
				<c:if test="${esAutorizador eq 1 }">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-pencil-square-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${autorizadas}</div>
										<div>Anticipos pendientes a comprobar</div>
									</div>
								</div>
							</div>
							<a
								href="solicitudAnticipo?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
					
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-pencil-square-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${autorizadas}</div>
										<div><%=etiqueta.AUTORIZADAS%></div>
									</div>
								</div>
							</div>
							<a
								href="solicitudResumen?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					
					
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${validadas}</div>
										<div><%=etiqueta.VALIDADAS%></div>
									</div>
								</div>
							</div>
							<a
								href="solicitudResumen?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA %>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</div> --%>
			</c:if>
			<!-- Comprobacion, Gasto de viaje y AMEX -->
			<div class="row">
				<c:if test="${esSolicitante eq 1}">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-share-square-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${comprobacionAnticipo}</div>
										<div><%=etiqueta.COMPROBACION_ANTICIPO%></div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenComprobacion?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_PAGADA %>&idTipoSolicitud=<%=Etiquetas.SOLICITUD_ANTICIPO%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
				<c:if test="${esSolicitante eq 1}">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-plane fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${comprobacionAnticipoViaje}</div>
										<div style="width: 145px;"><%=etiqueta.COMPROBACION_ANTICIPO_VIAJE%></div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenComprobacion?idEstadoSolicitud=<%= Etiquetas.ID_ESTADO_SOLICITUD_PAGADA %>&idTipoSolicitud=<%=Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
				
				
				<c:if test="${esSolicitante eq 1}">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-credit-card fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${comprobacionAmex}</div>
										<div><%=etiqueta.COMPROBACION_AMEX%></div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenComprobacion?idEstadoSolicitud=<%=Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
			</div>
			
			<div class="row">
				<c:if test="${esAutorizador eq 1 }">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-check-square-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${autorizar}</div>
										<div><%=etiqueta.PENDIENTES_AUTORIZAR%></div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenAutorizacion?idEstadoSolicitud=<%=Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
				<c:if test="${esAP eq 1 || esAP2 eq 1}">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-check fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${validar}</div>
										<div><%=etiqueta.PENDIENTES_VALIDAR%></div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenAutorizacion?idEstadoSolicitud=<%=Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
				
				
				<c:if test="${esAP2 eq 1 || puestoConfirmacionAP eq 1}">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-check fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${pendientesConfirmar}</div>
										<div>Pendientes de confirmar</div>
									</div>
								</div>
							</div>
							<a href="solicitudResumenAutorizacion?idEstadoSolicitud=<%=Etiquetas.ID_ESTADO_SOLICITUD_POR_CONFIRMAR%>">
								<div class="panel-footer">
									<span class="pull-left"><%=etiqueta.DETALLE%></span> <span class="pull-right"><i
										class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</c:if>
			</div>
		</div>
		<!-- /#page-wrapper -->
	</div>

	<jsp:include page="template/includes.jsp" />

	<!-- Scripts especificos ....  -->

</body>
</html>
