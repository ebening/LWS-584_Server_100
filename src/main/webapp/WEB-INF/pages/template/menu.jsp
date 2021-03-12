<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.lowes.util.Etiquetas"%>
<%
	Etiquetas etiqueta = new Etiquetas("es");
%>
<!-- <span class="label label-primary">Personal</span>
 -->


<div id="usuario_logged">
	<div class="center_login_img">
	
	<c:choose>
    <c:when test="${model.usuario.fotoPerfil eq 'default.png'}">
         <img class="circular" src="${pageContext.request.contextPath}/resources/images/${model.usuario.fotoPerfil}" />
    </c:when>
    <c:otherwise>
         <img class="circular" src="${pageContext.request.contextPath}/archivos/pp/${model.usuario.fotoPerfil}" />
    </c:otherwise>
</c:choose>

		
	</div>
	   <div class="center_login">
      <h4>${model.usuario.nombre}  ${model.usuario.apellidoPaterno}  ${model.usuario.apellidoMaterno}</h4>
      <span class="label label-success">${model.usuario.perfil.descripcion}</span>
	</div>
	
	
<%-- 	<div class="row">
		<div class="col-md-6"><span class="label label-warning">${model.usuario.locacion.descripcion}</span></div>
	</div> --%>
</div>

<ul class="nav collapse" id="side-menu">
	<li><a href="/LWS-584/"><i class="fa fa-dashboard fa-fw"></i>
			<%=etiqueta.DASHBOARD%></a></li>
	<c:forEach items="${model.menuList}" var="menuPadre">
		<li><a><i class="fa ${menuPadre.classIcon} fa-fw"></i> ${menuPadre.nombre}
				<span class="fa arrow"></span> </a>
			<ul class="nav nav-second-level">
				<c:forEach items="${menuPadre.menus}" var="menuPrimerHijo">
					<li><c:choose>
							<c:when test="${fn:length(menuPrimerHijo.menus) == 0}">
								<a href="${menuPrimerHijo.ruta}"><i
									class="nav nav-second-level"></i> ${menuPrimerHijo.nombre} </a>
							</c:when>
							<c:otherwise>
								<a><i class="fa ${menuPrimerHijo.classIcon} fa-fw"></i>
									${menuPrimerHijo.nombre} <span class="fa arrow"></span> </a>
								<ul class="nav nav-third-level">
									<c:forEach items="${menuPrimerHijo.menus}"
										var="menuSegundoHijo">
										<li><a href="${menuSegundoHijo.ruta}"> <i
												class="nav nav-third-level"> </i> ${menuSegundoHijo.nombre}
										</a></li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose></li>
				</c:forEach>
			</ul></li>
	</c:forEach>
</ul>