<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
						<h1 class="page-header">Plantilla...</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
            
           
          <!--  
           Estructura de contenido.
           divs-bootstrap, revisar layouts.
          -->
          <div style="width:200px; height:300px;">
          
          <a id="sel" href="#">selecciona el usuario 34</a>
           
			<form:form method="post" action="" modelAttribute="facturaConXML">
				<form:select data-live-search="true" path="idSolicitanteJefe" cssClass="form-control selectpicker">
					<option value="-1">Seleccione:</option>
					<c:forEach items="${usuarios}" var="lstUsuariosJefe">
						<option
							value="${lstUsuariosJefe.idUsuario}">${lstUsuariosJefe.nombre}
						</option>
					</c:forEach>
				</form:select>
			</form:form>
         </div>
		</div>
        <!-- /#page-wrapper -->
    </div>
    
  <jsp:include page="template/includes.jsp" />
  <script type="text/javascript">
  
     $(document).on("click","#sel",function(){
    	 
    	 console.log(34);
    	 $('#idSolicitanteJefe').selectpicker('val', '34');
     });
    	 
     
   
  </script>
  <!-- Scripts especificos ....  -->

</body>
</html>
