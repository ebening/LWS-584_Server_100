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

<style>
.panel-body {
	padding: 0px;
}

.show-grid {
	margin: 0px 0;
}

.hackCss {
	width: 100px;
	margin-left: 78px;
	margin-top: 13px;
}

.show-grid [class^=col-] {
	padding-top: 0;
	padding-bottom: 0px;
	border: 1px solid #FFF;
	background-color: #FFF !important;
}

.form-group {
	margin-bottom: 3px;
}

.trackAsset{
  
}

</style>
</head>

<body>


	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">
		<form:form id="uploadForm" enctype="multipart/form-data"  method="post" action="saveFacturaXML">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><%= etiqueta.CONXML %></h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
			
			<div style="display:none;" id="error-alert" class="alert alert-danger fade in">
			  <strong><span id="error-head"></span></strong> <span id="error-body"></span>
			</div>


			<div class="panel panel-default">
				<div style="height: 54px;" class="panel-heading">

					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-primary">Anexar documento de soporte</button>
					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-success">Consultar autorización</button>
					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-info">Especificar solicitante</button>
					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-warning">Cancelar</button>
					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-danger">Enviar a autorización</button>
					<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-default">Guardar</button>

				</div>
				<div class="panel-body">
					<div style="margin-left: -6px;" class="row">
						<div class="col-lg-6">
							


						

						</div>
					</div>
					<!-- /.row (nested) -->
				</div>


				<!-- /.panel-body -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4>Revisa la información de la factura</h4>
						<button style="margin-left: 10px; float: right;" type="button"
						class="btn btn-default">Guardar</button>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
							</div>
						</div>
						<!-- /.row (nested) -->
					</div>
				</div>

				<div style="border-color: #fff;" class="panel panel-default">
					<div class="panel-body">
					
						<div class="row show-grid">
						
					       
					       <div class="row show-grid">
                                <div class="col-xs-12 col-md-8">
                                   
                                   <div class="col-md-4">
                                   <div class="col-md-6">
                                   <div class="form-group">
										<div class="checkbox">
											<label> 
											<input id="track_asset" type="checkbox" value="">Trackas Asset
											</label>
										</div>
									</div>
                                   </div>
                                   <div class="col-md-6">
                                   <div class="form-group">
										<label>Cuenta Contable</label> 
										<select id="cuenta_contable" disabled="disabled" class="form-control">
											<option>Selecciona:</option>
										</select>
									</div>
                                   </div>
                                   </div>
                                   <div class="col-md-4">
                                   <div class="form-group">
										<label>PAR</label> <input id="par" name="par"
											class="form-control" disabled="disabled" value="">
									</div>
                                   </div>
                                   <div class="col-md-4"></div>
                                
                                </div>
                                <div class="col-xs-6 col-md-4"></div>
                            </div>

							

							
						</div>

					</div>



					<div class="panel-heading">
						<h4>Ingrese la información para desglose de factura</h4>
						
					</div>
					<div class="panel-body">

						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover"
								id="tablaUsuarios">
								<thead>
									<tr>
										<th>Linea</th>
										<th>Subtotal</th>
										<th>Locacion</th>
										<th>Cuenta contable</th>
										<th>Concepto</th>
										<th>AID</th>
										<th>Categoría Mayor</th>
										<th>Categoría Menor</th>
										<th></th>

									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										<td>1</td>
										<td><input class="form-control" value=""></td>

										<td>
											<div class="form-group">
												<select class="form-control">
													<option>Selecciona:</option>
												</select>
											</div>
										</td>

										<td><select class="form-control">
												<option>Selecciona:</option>
										</select></td>

										<td><input class="form-control" value=""></td>

										<td>
											<select class="form-control trackAsset">
													<option>Selecciona:</option>
											</select>
										</td>
										<td>
											<select class="form-control trackAsset">
													<option>Selecciona:</option>
											</select>
										</td>
										<td>
											<select class="form-control trackAsset">
													<option>Selecciona:</option>
											</select>
										</td>
											<td>REMOVER</td>
								   </tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
				</div>
			</div>
			<!-- /#page-wrapper -->
										</form:form>
			
		</div>

		<jsp:include page="template/includes.jsp" />
		
		<script>
		  
		  $(document).ready(function(){
			  
			  $(".trackAsset").prop('disabled',true);
			  
			  $("#track_asset").click(function(){
				  if($("#track_asset").is(":checked")){
					    $("#cuenta_contable").prop('disabled', false);
					    $("#par").prop('disabled', false);
						$(".trackAsset").prop('disabled',false);
				  }else{
						$("#cuenta_contable").prop('disabled', true);
						$("#par").prop('disabled', true);
						$(".trackAsset").prop('disabled',true);
				  }
			  });
		  }); 
		
		
		  function valXML(){
			  $("#loaderXML").show();

			  if(document.getElementById("file").files.length != 0){
				  console.log("entro a valXML");
				  $("#error-alert").hide();
				  
				  var data = new FormData();
				  jQuery.each(jQuery('#file')[0].files, function(i, file) {
				      data.append('file-'+i, file);
				  });
				  
				  
				  if(data){
					  jQuery.ajax({
						  
				    		url : "${pageContext.request.contextPath}/resolverXML",
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
							        $("#folioFiscal").val(data.folioFiscal);
							        $("#total").val(data.total);
							        $("#subTotal").val(data.subTotal);
							        $("#rfcEmisor").val(data.rfcEmisor);
							        $("#serie").val(data.serie);
							        $("#folio").val(data.folio);
							        $("#moneda").val(data.moneda);
						        }else{
						      	    $("#file").addClass("errorx");
								    $("#error-alert").show();
								    $("#error-head").text("<%= etiqueta.ERROR %>");
								    $("#error-body").text("<%= etiqueta.NOXML %>");
						        }
						    }
						});
				  }
				  
			  }else{
				  $("#loaderXML").hide();
  				  $("#file").addClass("errorx");
				  $("#error-alert").show();
				  $("#error-head").text("<%= etiqueta.ERROR %>");
				  $("#error-body").text("<%= etiqueta.COMPLETE %>");
			  }
		  }
		  
		  
		  
		</script>

		<!-- Scripts especificos ....  -->
</body>
</html>
