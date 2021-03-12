var c = 0;
function fixHeader (fix) {
	c ++;
	if (fix == null) fix = false;
	$navbar = $('.navbar');
	$sidebar = $('#sidebarmenu1');
	var diferencia = 0;
	var anchoTop = 0;
	$('.hide-menu').click(function () {
		$(window).unbind('resize');
		$(window).resize();
		});
	$(window).resize(function() {

		  if ($sidebar.css("display") == "none") {
			  diferencia = 6;
			  if (fix) diferencia = -260;
		  }
		  else {
			  diferencia = -17;
			  if (fix) diferencia = -17;
		  }
		  anchoTop = window.innerWidth + parseInt(diferencia);
		  $navbar.css("width",anchoTop);
		  
		  
			
//			 console.log('Win-W:',$(window).width());
//			 console.log('NavbW:',$navbar.width());
//			 console.log('DIF.W:',($(window).width()-$navbar.width()));
			
		  
		});
	$(window).resize();
}
fixHeader(true);
