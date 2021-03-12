
<!-- Navigation -->
<div id="wrapper" class="toggled">
	<nav class="navbar navbar-default navbar-static-top" role="navigation"
		style="margin-bottom: 0">


		<div style="width: 225px;" class="menu-box">
			<img src="${pageContext.request.contextPath}/resources/images/adcontent.png" />
		</div>

		<div
			style="width: 225px;  padding-top: 15px; margin-right: 21px;"
			class="menu-box">

 <a onClick="menuRaw()" href="#" class="btn btn-default btn-md hide-menu"> <span
				class="	glyphicon glyphicon-transfer"></span> Esconder menu
			</a>
			<a href="logout" class="btn btn-default btn-md"> <span
				class="glyphicon glyphicon-log-out"></span> Salir
			</a> 
			
			

		</div>





		<div style="width: 149px; float: left; margin-left: 53px;"
			class="menu-box">
			<a href="."><img
				src="${pageContext.request.contextPath}/resources/images/lowes.png" />
				</a>

		</div>



		<div id="sidebarmenu1" class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<jsp:include page="menu.jsp" />

			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side -->
	</nav>
</div>