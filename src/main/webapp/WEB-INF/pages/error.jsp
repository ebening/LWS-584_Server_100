<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error ${error}</title>
<jsp:include page="template/head.jsp" />

	<style>
	#content {
	    width: 610px;
	    margin: auto;
	    text-align: center;
	    z-index: 2;
	    position: relative;
	    top: 10%;
	}
	
	#content h1 {
	    color: #333;
	    font-family: "MuseoSans-700",Helvetica,Arial,Verdana,sans-serif;
	    font-size: 55px;
	    font-weight: normal;
	    line-height: 65px;
	    text-shadow: inset 0px 1px 3px rgba(0,0,0,0.85);
	}
	
	#content p {
	    color: #333;
	    font-size: 18px;
	    font-weight: normal;
	    width: 512px;
	    margin: 0 auto;
	    line-height: 26px;
	    text-shadow: inset 0px 1px 3px rgba(0,0,0,0.85);
    }
    
    #wrapper {
	    padding: 0;
	    overflow: hidden;
	    position: relative;
    }
	</style>
	
</head>
<body id="errorpage">
        <div id="pagewrap">
            <!--Header-->
            <div id="header" class="header">
                <div class="container">
                    <img class="logo" src="" alt="">
					<a href="#" title="logo" class="link">HEADER</a>
                </div>
            </div><!--Header End-->

			<!--page content-->
            <div id="wrapper" class="clearfix">     
                <div style="height: 739px; left: 50%;">    
                    <div id="content">
                        <h1>${msg1}<br>${msg2}</h1>
                        <p>${msg3}</p>
                        <a href="${pageContext.request.contextPath}/" title="" class="button">Ir a inicio</a>
                    </div>
                </div>
            </div>

        </div><!-- end pagewrap -->
		
		<!--page footer-->
        <div id="footer">  
            <div class="container">
                
            </div>
        </div><!--end page footer-->
	</body>
	<jsp:include page="template/includes.jsp" />
</html>