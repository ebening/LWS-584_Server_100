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
						<h1 class="page-header">Ejemplo de CRUD</h1>
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
                            <button style="float:right;"  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Agregar Test Hibernate</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nombre</th>
                                            <th>Precio</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach items="${testHibernateList}" var="tlist">
	                                        <tr class="odd gradeX">
	                                            <td><c:out value="${tlist.id}"/></td>
	                                            <td><c:out value="${tlist.nombre}"/></td>
	                                            <td><c:out value="${tlist.precio}"/></td>
	                                            <td width="2%" class="center">
		                                             <button onclick="eliminar(${tlist.id})" style="height: 22px;" type="button" class="btn btn-xs btn-danger">
	                                                   <span class="glyphicon glyphicon-trash"></span>
	                                                 </button>
	                                                 
	                                                  <button onclick="editar(${tlist.id})" style="height: 22px;" type="button" class="btn btn-xs btn-warning">
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

		</div>
		<!-- /#page-wrapper -->
	</div>
	
	
	
	
	  <!-- Ventana Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
     <form:form id="testHibernateForm" cssClass="form-horizontal" modelAttribute="testHibernate" method="post" action="saveTestHibernate">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Agregar Test Hibernate</h4>
        </div>
        <div class="modal-body">
        
        <div  id ="error-sign"  style="display:none;" class="alert alert-warning">
               <strong>¡Atención!</strong> Complete todos los campos
        </div>
        
            
            
            <div class="form-group">
                        <div class="control-label col-xs-3"> <form:label path="nombre" >Nombre</form:label> </div>
                        <div class="col-xs-6">
                            <form:hidden id="idtest" path="id" value=""/>
                            <form:input cssClass="form-control" id="nombre" path="nombre" value=""/>
                            
                        </div>
                    </div>
    
                    <div class="form-group">
                        <form:label path="precio" cssClass="control-label col-xs-3">Precio</form:label>
                        <div class="col-xs-6">
                            <form:input cssClass="form-control" type="number" id="precio" path="precio" value=""/>
                        </div>
                    </div>

        </div>
        <div class="modal-footer">
          <input type="submit" id="saveTestHibernate" class="btn btn-primary" value="Save" onclick="return submitTestHibernateForm();"/>
          
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
        <h4>¿Esta seguro de eliminar este registro?</h4>
      </div>
      <div class="modal-footer">
        <a href="#" id="eliminarButton" class="btn btn-danger" role="button">Eliminar</a>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
      </div>
    </div>

  </div>
</div>
  
  
  
  
  

	<jsp:include page="template/includes.jsp" />
	
	 <!-- DataTables JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<script>
	
	
	
	//VALIDACIONES
	function submitTestHibernateForm(){
		$("#error-sign").hide();
		var ban = false;
		
		
		if($('#nombre').val() == ""){
			ban = true;
		}
		
		if($('#precio').val() == ""){
			ban = true;
		}
		
		if(ban){
			$("#error-sign").show();
			console.log("false");
			return false;
		}else{
			console.log("true");
			return true;
		}
	}
	
	
    $(document).ready(function() {
    	
        $('#dataTables-example').DataTable({
                responsive: true
        });
        
        $('#myModal').on('hidden.bs.modal', function () {
        	// limpiar formulario.
        	$("#nombre").val(null);
        	$("#precio").val(null);
        	$("#idtest").val(null);
        });
        
    });
    
   
     
     
  function editar(idtest) {
        
        	
        if(idtest > 0){
        	
        console.log(idtest);
      	  loading(true);
            $.ajax({
                type: "GET",
                cache: false,
                url: "${pageContext.request.contextPath}/getTestHibernate",
                async: true,
                data: "intxnId=" + idtest,
                success: function(response) {
                	loading(false);
                	console.log(response.precio +" -> "+response.nombre+" -> "+response.id);
                	$("#nombre").val(response.nombre);
                	$("#precio").val(response.precio);
                	$("#idtest").val(response.id);
                	// mostrando el modal
                	//abrir ventana modal
                	$('#myModal').modal('show'); 
                },
                error: function(e) {
                	loading(false);
                    console.log('Error: ' + e);
                },
            }); 
            
        	}//if
        }
        
        
        function eliminar(id){
        	
        	console.log(id);
        	$("#eliminarButton").prop("href","deleteTestHibernate?id="+id);
        	$("#myModalEliminar").modal('show');
        	
        }
        
    </script>

	<!-- Scripts especificos ....  -->

</body>
</html>
