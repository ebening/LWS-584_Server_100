    <%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AdContent</title>
    
    <!-- Loading page -->
	<div id="loading-cover" style="margin: -8px; padding: 8px 0px 8px 8px; width: 100%; height: 100%; position: absolute; z-index: 2000;opacity: 1; display: block; width: 100%; height: 100%;">
		<div class="" style="left: 50%; top: 50%; width: 80px; height: 36px; position: absolute;">
			<div class="loading-logo img-rounded" style="line-height: 1.42857143; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; color: #FFFFFF; position: fixed; text-align: center; z-index: 1050; font-size: 14px; padding: 8px; background: rgba(0,73,144,0.70); border-radius: 6px; opacity: 1; width: 77px; height: 36px; position: relative; left: -23px; top: -2px;">Cargando</div>
		</div>
		<div class="loading-bg" style=" padding: 10px 10px 10px 10px !important; background-color: #004990; background-color: #333;opacity: 0.7; position: fixed; display: block; width: 100%; height: 100%;"></div>
	</div>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/dist/css/sb-admin-2.css" rel="stylesheet">

	
	
    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker3.min.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/resources/dist/css/lowes.css" rel="stylesheet">
    

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>

.errorx {
	border: 1px solid red !important;
}

.errorchk{
	outline: 1px solid red
}

.boton_crear {
	
}

.boton_editar {
	
}

.currencyFormat{
}

.backBlank{
    background-color: #fff !important;
}


#usuario_logged {
	width: 90%;
	min-height: 180px;
	background-color: #F8F8F8;
	border: 1px solid #F8F8F8;
	margin: auto;
}

.circular {
	margin-top: 10px;
	width: 100px;
	height: 100px;
	border-radius: 150px;
	-webkit-border-radius: 150px;
	-moz-border-radius: 150px;
	border: 2px solid #004990;
	/* 	box-shadow: 0 0 8px rgba(0, 0, 0, .8);
	-webkit-box-shadow: 0 0 8px rgba(0, 0, 0, .8);
	-moz-box-shadow: 0 0 8px rgba(0, 0, 0, .8); */
}

.center_login {
	margin-top: 15px;
	text-align: center;
	vertical-align: middle;
	height: 15px;
	line-height: 15px;
}

.boton-accion-menu{
  float: right;
  width:30px;
  height: 30px;
  background-color: #eaeaea;
}

.center_login_img {
	width: 100px;
	height: 100px;
	margin: auto;
}


.menu-box{
  width: 150px;
  height: 68px;
  float: right;
  margin: 6px 10px 0 0;
}

    .page-wrapper1{
       margin: 0px !important;
       padding: 0px !important;
    }
    
    
    .overlay-full{
    background-color:#eee;
    position:fixed;
    width:100%;
    height:100%;
    top:0px;
    left:0px;
    z-index:1000;
}

/* .overlay-full img{
    display: block;
    margin-left: auto;
    margin-right: auto;
} */

.fixLabel{
  font-size:90% !important;
 line-height: 2 !important;
}

</style>
    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-select.css">
    <script	src="${pageContext.request.contextPath}/resources/js/lowes.js"></script>
	<script>
    var e_agregar = '<%= etiqueta.AGREGAR %>';
    var e_editar = '<%= etiqueta.EDITAR %>';
    var e_titulo = '';
	var ATENCION =  '<%=etiqueta.ATENCION%>';
	var ARCHIVO_ANEXADO =  '<%=etiqueta.ARCHIVO_ANEXADO%>';
	var ERROR = '<%=etiqueta.ERROR%>';
	var EXTENSION_INVALIDA = '<%=etiqueta.EXTENSION_INVALIDA%>';
	var ARCHIVO_NO_ANEXADO = '<%=etiqueta.ARCHIVO_NO_ANEXADO%>';
	var ARCHIVO_VACIO = '<%=etiqueta.ARCHIVO_VACIO%>';
	var ARCHIVO_NO_SELECCIONADO = '<%=etiqueta.ARCHIVO_NO_SELECCIONADO%>';
	var ARCHIVO_ELIMINADO = '<%=etiqueta.ARCHIVO_ELIMINADO%>';
	var ARCHIVO_NO_ELIMINADO = '<%=etiqueta.ARCHIVO_NO_ELIMINADO%>';
	var ERROR_DEPENDENCIAS = '<%=etiqueta.ERROR_DEPENDENCIAS%>';
	var ERROR_DELETE = '<%=etiqueta.ERROR_DELETE%>';
	var TAMANO_NO_PERMITIDO = '<%=etiqueta.TAMANO_NO_PERMITIDO%>';
	var COMPLETE = '<%=etiqueta.COMPLETE%>';
	var PERDERA_INFORMACION = '<%=etiqueta.PERDERA_INFORMACION%>';
	var CAPTURE_SUBTOTAL = '<%=etiqueta.CAPTURE_SUBTOTAL%>';
	var INFORMACION_ACTUALIZADA = '<%=etiqueta.INFORMACION_ACTUALIZADA%>';
	var NUEVA_SOLICITUD = '<%=etiqueta.NUEVA_SOLICITUD%>';
	var SOLICITUD_CREADA = '<%=etiqueta.SOLICITUD_CREADA%>';
	var SALDO_PENDIENTE_CERO = '<%=etiqueta.SALDO_PENDIENTE_CERO%>';
	var DESGLOSE_MINIMO = '<%=etiqueta.DESGLOSE_MINIMO%>';
	var ESTADO_SOLICITUD = '<%=etiqueta.ESTADO_SOLICITUD%>';
	var CANCELADA = '<%=etiqueta.CANCELADA%>';
	var MENSAJE_DIALOGO_CON_XML = '<%=etiqueta.MENSAJE_DIALOGO_CON_XML%>';
	var NOXML = '<%=etiqueta.NOXML%>';
	var ENVIADA_AUTORIZACION = '<%=etiqueta.ENVIADA_AUTORIZACION%>';
	var VALIDADA_AUTORIZACION = '<%=etiqueta.VALIDADA_AUTORIZACION%>';
	var FACTURA_VALIDA = '<%=etiqueta.FACTURA_VALIDA%>';
	var MENSAJE_CAMBIO_SOLICITANTE_NOXML = '<%=etiqueta.MENSAJE_CAMBIO_SOLICITANTE_NOXML%>';
	var MENSAJE_CANCELACION_NOXML = '<%=etiqueta.MENSAJE_CANCELACION_NOXML%>';
	var ESPECIFICA_SOLICITANTE = '<%=etiqueta.ESPECIFICA_SOLICITANTE%>'
	var ACTUALIZACION = '<%=etiqueta.ACTUALIZACION%>';
	var ERROR_FECHA_RANGO = '<%=etiqueta.ERROR_FECHA_RANGO%>';
	var IMPORTE_CERO = '<%=etiqueta.IMPORTE_CERO%>';
	var ESPECIFIQUE_LOCACION = '<%=etiqueta.ESPECIFIQUE_LOCACION%>';
	var GUARDE_ENVIAR = '<%=etiqueta.GUARDE_ENVIAR%>';
	var NO_SE_ENVIO = '<%=etiqueta.NO_SE_ENVIO%>';
	var AUTORIZACION_AUTORIZADA = '<%=etiqueta.AUTORIZACION_AUTORIZADA%>';
	var AUTORIZACION_RECHAZADA = '<%=etiqueta.AUTORIZACION_RECHAZADA%>';
	var SOLICITUD_PAGADA = '<%=etiqueta.SOLICITUD_PAGADA%>';
	var ERROR_PENDIENTE = '<%=etiqueta.ERROR_PENDIENTE%>';
	var DOCUMENTO_SOPORTE_ANEXAR = '<%=etiqueta.DOCUMENTO_SOPORTE_ANEXAR%>';
	var COMPROBACION_ANTICIPO_TITULO = '<%=etiqueta.COMPROBACION_ANTICIPO_TITULO%>';
	var NOPROVEEDOR = '<%= etiqueta.NOPROVEEDOR %>';
	var VALIDA_ARCHIVO_PDF = '<%= etiqueta.VALIDA_ARCHIVO_PDF %>'
	var VALIDA_LIBRO_CONTABLE = '<%= etiqueta.VALIDA_LIBRO_CONTABLE %>'
	var MENSAJE_EXISTE = '<%= etiqueta.MENSAJE_EXISTE %>'
       
    </script>
    