
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script	src="${pageContext.request.contextPath}/resources/js/jquery-1.12.0.min.js"></script>
		
    <script src="${pageContext.request.contextPath}/resources/bower_components/jquery/dist/jquery.min.js"></script>
	
	<!-- colResisable -->
	<script	src="${pageContext.request.contextPath}/resources/js/colResizable-1.6.min.js"></script>
	
    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/dist/js/sb-admin-2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/fixInterface.js"></script>
    
    
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-select.js"></script>
    
    
    
    <script>
    
    var menuIsHide = false;
    
    $(document).ready(function(){
    	
    	 $(".boton_crear").on("click",function(){
    	    $(".modal-title").text(e_agregar+" "+e_titulo);
		}); 
		
		$(".boton_editar").on("click",function(){
			$(".modal-title").text(e_editar+" "+e_titulo);
		});

    	loading(false);
		$("#loading-cover").remove();

		$(".scrollable table").colResizable({
		    fixed:false,
		    liveDrag:true,
		    draggingClass:"dragging",
		    disabledColumns: [0],
		    resizeMode:'overflow'
		});
		
    });

	function resetColResizable(){
		$(".scrollable table").colResizable({ disable : true });
		$(".scrollable table").colResizable({
		    fixed:false,
		    liveDrag:true,
		    draggingClass:"dragging",
		    disabledColumns: [0],
		    resizeMode:'overflow'
		});
	}
    
	function error_alert(){
		console.log("error alert called");
		$("#error-alert").fadeTo(30000, 500).slideUp(500, function(){
		    $("#error-alert").hide();
		});
	}
	
	function error_alert_modal(){
		$("#ok-alert-modal").hide();
		$("#error-alert-modal").fadeTo(30000, 500).slideUp(500, function(){
		    $("#error-alert-modal").hide();
		});
	}
	
	function ok_alert(){
		$("#ok-alert").fadeTo(30000, 500).slideUp(500, function(){
		    $("#ok-alert").hide();
		});
	}
	
	function ok_alert_modal(){
		$("#error-alert-modal").hide();
		$("#ok-alert-modal").fadeTo(30000, 500).slideUp(500, function(){
		    $("#ok-alert-modal").hide();
		});
	}
	
	function menuRaw(){
		console.log("menu raw");
		
		if(menuIsHide){
			$("#sidebarmenu1").show();
			$("#page-wrapper").removeClass("page-wrapper1");
			menuIsHide = false;
		}else{
			$("#sidebarmenu1").hide();
			$("#page-wrapper").addClass("page-wrapper1");
			menuIsHide = true;
		}
		
	}
	
	  function activeToolTip(){
    	  $("[data-toggle=tooltip]").tooltip({
				placement: $(this).data("placement") || 'top'
		  });
     }

	// SOLO ALFANUMERICOS -----------------------------------------------
	  $('.alfanumerico').keypress(function(e) {
	      var a = [];
	      var k = e.which;
	      var regex=/^[\s0-9A-Za-z\b]+$/;
	      var str = String.fromCharCode(k);
	      if(!regex.test(str)){
	        e.preventDefault();
	      }
	  });

	//Loadings on save solicitud
	$("form").submit(function( event ) {
		loading(true);
	});
  
    </script>
    
    
    
    

   