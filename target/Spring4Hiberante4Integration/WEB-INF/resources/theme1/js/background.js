$( document ).ready(function() {
	
	console.log(window.location.pathname+window.location.search);
	
   var imgID = Math.floor(Math.random() * 6) + 1;

	 var nameImg = "${pageContext.request.contextPath}/resources/images/bk";
     nameImg = nameImg.concat(imgID).concat(".png");
     $('body').css("background","url('${pageContext.request.contextPath}/resources/images/bk.png'),url("+nameImg+")");
   
   
});

