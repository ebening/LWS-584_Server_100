    <%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<%String errorHead = request.getParameter("errorHead");%>
<%String errorBody = request.getParameter("errorBody");%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    				
    				
  <style>

#anexarDocumentoForm .modal-header .close {
  color: white;
  opacity: .8;
}
  
#anexarDoc .modal-header {
  background-color: #004990; color: white;
}

#uploadFile {
  border: none;
  height: 28px;
  width: 185px;
  padding: 8px;
}

.fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
}
.fileUpload input.upload {
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
}
  </style>
    				
				<!-- Ventana modal anexar documento soporte -->
		<div class="modal fade" id="anexarDoc" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<form:form id="anexarDocumentoForm" enctype="multipart/form-data"
					cssClass="form-horizontal" modelAttribute="solicitudArchivo"
					method="post" action="saveSolicitudDocument">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" style="color: white; opacity: .8;">&times;</button>
							<h4 style="float: left; margin:0px;">
								<span id="titulo-dialogo"><%=etiqueta.DOCUMENTO_SOPORTE%></span>
							</h4>
						</div>

						<div class="modal-body">

							<div id="errorMsg" style="display: none;"
								class="alert alert-warning">
								<strong> <span id="error-head-files"><%=errorHead%></span>
								</strong> <span id="error-body-files"><%=errorBody%></span>
							</div>

							<div class="form-group">
								
									
									
									</div>
									
										<div class="col-xs-12">
											<form method="post" enctype="multipart/form-data" action="singleSave">
												<label class="cols-xs-12" style="margin-left: 10px;"><%=etiqueta.ANEXAR_DOCUMENTO_SOPORTE%></label><br />
												<div class="cols-xs-12">
												
													<div class="fileUpload btn btn-primary input-upload">
													    <span><%= etiqueta.SELECCIONAR_ARCHIVO %></span>
													    <input id="file-sol" type="file" name="file-sol" class="upload" />
													</div>
													<input id="uploadFile" placeholder="No se eligió archivo" disabled="disabled" class=" input-upload"/>

													<img id="loaderXML" style="display: none;" class=" class="input-upload"
															src="${pageContext.request.contextPath}/resources/images/loader.gif" />
													
													<div class="nuevo-nombre col-xs-8" style="margin: 10px; display:none">
														<input id="nombre-alterno" type="text" class="form-control" style="width:200px; float:left">
														<div class="nombre-extension col-xs-3" style="margin: 4px 0px 0px 4px; padding:0px;"></div>
													</div>

													<button onclick="anexarDoc()"
														style="margin: 10px; float: right;" type="button"
														class="btn btn-default"><%=etiqueta.ADJUNTAR_ARCHIVO%></button>
												
												</div>
											</form>
										</div>


							<div class="panel-body">
							
								
							
							
								<div class="dataTable_wrapper" style="display:hidden">
									<table class="table table-striped table-bordered table-hover"
										id="tablaAid">
										<thead>
											<tr>
												<th><%=etiqueta.DOCUMENTO%></th>
												<th width="20%"><%=etiqueta.ELIMINAR%></th>
											</tr>
										</thead>
										<tbody id="tablaDoc">
											<c:forEach items="${solicitudArchivoList}" var="salist">
												<tr class="odd gradeX">
													<td><c:out value="${salist.archivo}" /></td>
													<td class="center">
														<button onclick="eliminar(${salist.idSolicitudArchivo})"
															style="height: 22px;" type="button"
															class="btn btn-xs btn-danger">
															<span class="glyphicon glyphicon-trash"></span>
														</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- /.table-responsive -->
							</div>
						</div>
					</div>
				</form:form>

			</div>
		</div>
		
		<script>
		// Modal Upload
		document.getElementById("file-sol").onchange = function () {
	    document.getElementById("uploadFile").value = this.value;
	};
		</script>
		