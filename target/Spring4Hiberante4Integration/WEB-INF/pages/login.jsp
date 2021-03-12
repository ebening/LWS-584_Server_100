
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.lowes.util.Etiquetas"%>
<%  Etiquetas etiqueta = new Etiquetas("es"); %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%= etiqueta.LOWES %></title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style>
    
		body {
		  /* Location of the image */
            background-image: url("${pageContext.request.contextPath}/resources/images/bk11.png");
		  
		  /* Background image is centered vertically and horizontally at all times */
		  background-position: center center;
		  
		  /* Background image doesn't tile */
		  background-repeat: no-repeat;
		  
		  /* Background image is fixed in the viewport so that it doesn't move when 
		     the content's height is greater than the image's height */
		  background-attachment: fixed;
		  
		  /* This is what makes the background image rescale based
		     on the container's size */
		  background-size: cover;
		  
		  /* Set a background color that will be displayed
		     while the background image is loading */
		  background-color: #192e65;
		  
		}		
		
		#wrapper-login{
		 width:424px;
		 height: 360px;
		 margin: auto;
		 margin-top: 100px;

		}
		
		#head-login{
		width: 100%;
		height: 53px;
/*         height: 199px;
 */		}
 
 
		
		#content-login{
/* 		border-top: solid 5px #d12a33;
		background-image: url("${pageContext.request.contextPath}/resources/images/bklogin.png"); */
		width: 100%;
		height:250px;
		}
        
        #info-login{
           width: 87%;
           margin: auto;
           height: 230px;
           margin-top: 26px;
        }
        
      #boton1{
         background-color: #9fcc00 !important;
      }
        
       #boton1 a:hover{
         background-color: #eaeaea !important;
        }
       
       #logo-login{

        }
        
        .titulo-login{
		    color: #fff;
		    font-stretch: extra-condensed;
		    font-weight: bold;
		    font-size: 18px;
		    text-transform: uppercase;
		    text-shadow: -1px 1px #000;
        }    
        
        .errorx{
         border: 1px solid red;
        }
      .modal {display:block}
      .modal-dialog {
          width: 424px;
          }
       .modal-content {border-radius: 0px 0px 6px 6px;}
       .modal-header {
       	background-color:#004990;
       	/* background: url("${pageContext.request.contextPath}/resources/images/lowes.png") no-repeat  center 8px; */
       	height:50px;
       	border-radius: 6px 6px 0px 0px;
       }
    </style>

</head>

<body>













<div  style="float: right; display: none;" id="error-alert" class="alert alert-danger fade in">
			  <strong><span id="error-head"><%= etiqueta.ERROR_LOGIN_HEAD %>: </span></strong> <span id="error-body"><%= etiqueta.ERROR_LOGIN %></span>
			</div>
			
				<div id="wrapper-login" class="modal">
				<div class="modal-dialog">
	               <div style="" id="head-login" class="login-header">
	               <div class="modal-header" style="height: 50px; padding: 0px;background-color: #004990; border-bottom:0px;">
	               	<h3 style="    color: white;
    font-family: 'Segoe Ui', Arial;
    font-size: 15px;
    text-align: center;
    margin-top: 19px;
    font-weight: bold;"><%=etiqueta.SISTEMA_NOMBRE%></h3>
	               </div>
	               <%-- <img id="logo-login"  alt="logo" src="${pageContext.request.contextPath}/resources/images/head_title.png"> --%>
	             <div class="modal-content" style="background-color: #ddd; height: 200px; padding-top: 1px; border:0px;">
	               <div id="content-login">
	                  <div id="info-login">
					<form id="loginForm" name="loginForm" action="<c:url value='/login.do' />" method="POST">
	
						<c:if test="${not empty param.out}">
							<%-- <div><%= etiqueta.LOGOUT %></div> --%>
						</c:if>
						<c:if test="${not empty param.time}">
	<%-- 				<div><%= etiqueta.LOGOUT_TIEMPO %></div>
	 --%>					</c:if>
	
						<div class="form-group">
	                                   <div></div>
	                               </div>
	                            <fieldset>
	                                <div class="form-group">
	                                    <input class="form-control"   placeholder="Usuario" id="username" name="username" type="text" >
	                                </div>
	                                <div class="form-group">
	                                    <input class="form-control"  placeholder="Contraseña" id="password" name="password" type="password" value="" >
	                                </div>
	                                <input onclick="return validate();" name="submit" type="submit" class="btn btn-lg btn-primary btn-block" value="Login">
	                            </fieldset>
	                
	                	<input  type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                </form>
	               </div>
	               </div>
	               </div>
	              </div>
            </div>
    

    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/dist/js/sb-admin-2.js"></script>
    
  <script>
   
    var error = '${param.err}';
    
    // random para fondos
    var rand = Math.floor(Math.random() * 3) + 1;
    rand = rand+".png";
    var fondo = '${pageContext.request.contextPath}/resources/images/fondos/'
    fondo = fondo+rand;
    
    
    
    function validate(){
        
        var noError = true;
        var usr = $("#username").val();
        var pwd = $("#password").val();
        
        if( usr == ""){
        	noError = false;
        	$("#username").addClass("errorx");
        }else{
        	$("#username").removeClass("errorx");
        }
        
        if(pwd == ""){
        	noError = false
        	$("#password").addClass("errorx");
        }else{
        	$("#password").removeClass("errorx");

        }
        
        var faltaUsr =  '<%= etiqueta.COMPLETE_USUARIO  %>';
        var faltaPwd =  '<%= etiqueta.COMPLETE_PASSWORD %>';
        var ERROR =  '<%= etiqueta.ERROR %>';
        var mensaje = "";
        
        
        
        if(usr == "" && pwd == ""){
        	mensaje = faltaUsr + " e " + faltaPwd;
        }else{
        	if(usr == ""){
        		mensaje = faltaUsr;
        	}
        	if(pwd == ""){
        		mensaje = faltaPwd;
        		console.log(faltaPwd);
        	}
        }
        
        
        if(noError){
        	return true;
        }else{
        	console.log("error false");
        	$("#error-head").text(ERROR);
			$("#error-body").text(mensaje);
        	error_alert();
        	return false;
        }
    	
    }
        
    
  
    $(document).ready(function(){
    	
    	$('body').css('background-image', 'url(' + fondo + ')');
    	
    	if(error != ""){
    		error_alert();
    		//$("#error-alert").show();
    		console.log(error);
    	}
    });
    
	function error_alert(){
		$("#error-alert").fadeTo(5000, 500).toggle(500, function(){
		    $("#error-alert").hide();
		});
	}
	

   
	
	
	
  </script>

</body>

</html>
